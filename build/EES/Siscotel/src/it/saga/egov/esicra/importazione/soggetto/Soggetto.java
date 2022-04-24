package it.saga.egov.esicra.importazione.soggetto;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import java.util.Date;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;
import java.util.Hashtable;

/**
 *   Importazione soggetto
 *   
 *   TODO distinguere quando è fisico e quando è giuridico
 */

public class Soggetto extends ImportazioneFile{
  
  //private static final Integer COD_NATURA= new Integer("0"); // cod soggetto fisico
  private TerComune comuneImport;
  private Hashtable serProvenienzaCache ;
  private boolean fine=false;
  
  public Soggetto(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
    super(file,"ListaSoggetto",new String []{"Soggetto"},"SoggettoModule");
    versione = "2.0";
    if (System.getProperty("esicra.id_istat") != null) {
         String cod =  System.getProperty("esicra.id_istat");
         //§ carica il comune
         comuneImport = (TerComune) DbCommon.cercaRiga("it.saga.siscotel.db.TerComune","cod_comune",cod);
    }else{
       comuneImport = null;
       logger.warn("CODICE ISTAT COMUNE NON VALORIZZATO !!!!!!!");
    }

    //§ pulisce provenienza
    DbCommon.serProvenienzaCache.clear();
    
  }

  protected void  process(String str) throws Exception{
      Document doc = builder.build(new StringReader(str));
      Element soggetto = doc.getRootElement();
      String tipo = soggetto.getName();
      if (tipo.equalsIgnoreCase("Soggetto")) {
          if (doc_letti >= doc_old) {
              //System.out.println("--------------------------------------------");
              processPF(soggetto);
              if (((doc_letti)%STEP)==0){
                  logger.debug("Documenti letti: "+doc_letti);
                  System.out.println(new Date(System.currentTimeMillis()) +" Documenti letti: "+doc_letti);
                  setInElaborazione(doc_letti);
                  committBlock();
                  session.flush();
                  /*
                  if(fine){
                    session.flush();
                    session.close();
                    System.exit(0);
                  }else
                    fine=true;
                  */
               }
          } else {
              doc_letti++;
          }
      } else{
        logger.warn(" Tipo Documento '"+tipo+"' non gestito");
      }
  }

 /**
  *  Processa Soggetto
  */
  private void processPF( Element sgXml ) throws Exception{
      AnaSoggetto sg;
      String cod;
      //§ ATTENZIONE la provenienza nel AnaSoggetto è integer mentre in SerProvenienza è String !!!
      Integer provenienza;
      doc_letti ++;
      cod = CV.text(sgXml.getChild("CodSoggetto",namespace));
      if (cod != null){
          cod= cod.toUpperCase();
      }
      provenienza = CV.intero(sgXml.getChild("Provenienza",namespace));
      if (provenienza == null){
          logger.warn("Provenienza non specificata soggetto cod: "+cod+" progressivo: "+progressivo);
          doc_saltati ++;
          return;
      }
      //§  carica AnaSoggetto
      sg = (AnaSoggetto) DbCommon.cercaRigaCorrente("it.saga.siscotel.db.AnaSoggetto","cod_soggetto",cod);       
      if(sg!=null){
        int provOrig=sg.getProvenienza().intValue();
        int provNew=provenienza.intValue();
        if(provNew<=provOrig){
          // soggetto presente
          // aggiorna solo se cod provenienza è maggiore della presente
          // ma in ogni caso aggiorna partita iva
          caricaSoggetto(sgXml,sg);
        }else{
          sg.setPartitaIva(CV.text(sgXml.getChild("PartitaIva",namespace)));
        }
      }else{
        // soggetto nuovo
        sg =new AnaSoggetto();
        caricaSoggetto(sgXml,sg);
      }
      // aggiorna provenienza
      DbCommon.aggiornaProvenienzaSogg(sg,provenienza.toString(),getDtMod(),id_ente);
  }
  
  /**
   *  metodo usato per caricare
   *  AnaSoggetto nel db  partendo dall'elemento  xml
   * 
   * @param sogXml documento xml
   * @param sogg  soggetto persistente
   * @throws Exception 
   */
  private void caricaSoggetto(Element sogXml, AnaSoggetto sogg) throws Exception{
    try{
      sogg.setCodSoggetto(CV.text(sogXml.getChild("CodSoggetto",namespace)));
      sogg.setProvenienza(CV.intero(sogXml.getChild("Provenienza",namespace)));
      String denominazione = CV.text(sogXml.getChild("Denominazione",namespace));
      Integer codNatura = CV.intero(sogXml.getChild("CodNatura",namespace));
      if(codNatura==null){
        if (denominazione!=null && denominazione.trim().length()>0)
          codNatura=new Integer(1);
        else
          codNatura=new Integer(0);
      }
      sogg.setNatura(codNatura); // natura del soggetto
      // ATTENZIONE !!! il cod_soggetto 
      String cf = CV.text(sogXml.getChild("CodSoggetto",namespace));
      if(cf!=null)
        sogg.setCodiceFiscale(cf.toUpperCase());
      String piva = CV.text(sogXml.getChild("PartitaIva",namespace));
      if(piva!=null)
        sogg.setPartitaIva(piva.toUpperCase());
      sogg.setCognome(CV.text(sogXml.getChild("Cognome",namespace)));
      sogg.setNome(CV.text(sogXml.getChild("Nome",namespace)));
      sogg.setDenominazione(denominazione);
      sogg.setAltriNomi(CV.text(sogXml.getChild("AltriNomi",namespace)));
      sogg.setSesso(CV.intero(sogXml.getChild("Sesso",namespace)));
      sogg.setDtNascita(CV.data(sogXml.getChild("DataNascita",namespace)));
      sogg.setPrecisioneDtNascita(CV.intero(sogXml.getChild("PrecisioneDtNascita",namespace)));
      sogg.setNote(CV.text(sogXml.getChild("Note",namespace)));
      sogg.setDtMod(getDtMod());
      Element comuneXml = sogXml.getChild("ComuneNascita",namespace);
      Element localitaXml = sogXml.getChild("LocalitaNascita",namespace);
      if(comuneXml!=null){
        String codcomuneXml = CV.text(comuneXml.getChild("CodComune",namespace));
        if (codcomuneXml != null){
            //  TODO MIGLIORARE OTTIMIZZAZIONE DEL COMUNE (magari usare hashtable )
            if((comuneImport != null) && codcomuneXml.equals(comuneImport.getCodComune())){
                  sogg.setTerComune(comuneImport);
            } else {
               TerComune comune = (TerComune) 
                  DbCommon.cercaRiga("it.saga.siscotel.db.TerComune","cod_comune",codcomuneXml);
               if (comune!=null){
                  sogg.setTerComune(comune);
               }
            }
        }
        sogg.setDesComuneNascita(CV.text(comuneXml.getChild("DesComune",namespace)));
        sogg.setDesProvinciaNascita(CV.text(comuneXml.getChild("DesProvincia",namespace)));
      }else if(localitaXml!=null){
           String codStato = null;
           sogg.setDesLocalitaNascita(CV.text(localitaXml.getChild("DesLocalita",namespace)));
           sogg.setDesStatoNascita(CV.text(localitaXml.getChild("DesStato",namespace)));
           codStato = CV.text(localitaXml.getChild("CodStato",namespace));
           if (codStato != null && !codStato.equals("0")) {
              //§ carica Stato
              //System.out.println("STATO: "+codStato);
              TerStato stato = (TerStato) DbCommon.cercaRiga("it.saga.siscotel.db.TerStato","cod_stato",codStato);
              if (stato!=null){
                sogg.setTerStato(stato);
              }
           }
      }
      
      AnaRappresentante rapp_Row=null;
      if(codNatura.intValue()==1) {
      
        // eventuale rappresentante
        String codRappresentante=CV.text(sogXml.getChild("CodRappresentante",namespace));
        String desrapp = CV.text(sogXml.getChild("DesRappresentante",namespace));
        
        //System.out.println("codRappresentante="+codRappresentante);
        // ATTENZIONE!! CONTROLLARE
        if (codRappresentante != null){     
          AnaSoggetto srap = (AnaSoggetto) DbCommon.cercaRigaCorrente("it.saga.siscotel.db.AnaSoggetto","cod_soggetto",codRappresentante);
          if(srap != null) {
              // recupera AnaRappresentante
              session.saveOrUpdate(sogg);
              rapp_Row = DbCommon.cercaRappresentante(sogg,srap);
              if (rapp_Row==null) {
                // non trova l'associazione cercata
                // crea l'associazione che probabilmente mancava  
                rapp_Row = new AnaRappresentante();
                rapp_Row.setAnaSoggetto(sogg);
                rapp_Row.setAnaRappresentante(srap);                    
            } 
          }
          //System.out.println("RAPPRE="+ rapp_Row.getCognome()+" "+rapp_Row.getNome());
        }
        // se il rappresentante non è residente mette solo descrizione
        if (rapp_Row == null) {
           if (desrapp != null){
             rapp_Row = new AnaRappresentante();
             rapp_Row.setAnaSoggetto(sogg);
             rapp_Row.setDesRappresentante(desrapp);
           }
        }
       // inserisce dati
       if (rapp_Row != null){
          rapp_Row.setDesTitoloRapp(CV.text(sogXml.getChild("DesTitoloRapp",namespace)));    
          rapp_Row.setCodiceFiscale(CV.text(sogXml.getChild("CodRappresentante",namespace)));
          rapp_Row.setCognome(CV.text(sogXml.getChild("CognomeRapp",namespace)));
          rapp_Row.setDtNascita(CV.data(sogXml.getChild("DtNascitaRapp",namespace)));
          rapp_Row.setLocNascita(CV.text(sogXml.getChild("LoacalitaNascitaRapp",namespace)));
          rapp_Row.setNome(CV.text(sogXml.getChild("NomeRapp",namespace)));
          rapp_Row.setRecapito(CV.text(sogXml.getChild("RecapitoRapp",namespace)));
          rapp_Row.setFlgRappresentante(new Integer(1));
          rapp_Row.setDtMod(dtmod);
        }
      }
      // campi standard
      sogg.setIdEnte(new Integer(id_ente));
      session.saveOrUpdate(sogg);
      if (rapp_Row!=null)
        session.saveOrUpdate(rapp_Row);
    }catch(Exception e){
      doc_saltati ++;
      //cod=null; CONTROLLARE!!
      System.out.println(e);
      throw e;
    }
  }
  
  public static void test1(){
    File file = new File("c://esicra/test/siscotel/SOG_A_20040317112600.XML");
    Soggetto sogg = null;
    try{
      sogg = new Soggetto(file);
      sogg.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void test2(){
    File file = new File("c://esicra/test/stress/SOG_A_20050721161221.XML");
    Soggetto sogg = null;
    try{
      sogg = new Soggetto(file);
      sogg.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   *   metodo di test
   *    
   */
  public static void main(String[] args){
    
    //§ impostazioni per configurazione
    //--
    String xsltPath = "c://esicra/tomcat/webapps/siscotel/xslt";
    String xsdPath =  "c://esicra/tomcat/webapps/siscotel/xsd/2.0";
    System.setProperty("esicra.xslt.dir",xsltPath);
    System.setProperty("esicra.xsd.dir",xsdPath);
   
    //--
    EsicraConf.configura();
    
    System.out.println("**** START *** "+new Date(System.currentTimeMillis()));
    
    System.setProperty("esicra.id_ente","8240");
    System.setProperty("esicra.import.commit","500");
    test1();
    //test2();
    System.out.println("**** FINE *** "+new Date(System.currentTimeMillis()));
  }
  
}

