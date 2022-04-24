package it.saga.egov.esicra.importazione.soggetto;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;

/**
 *  Importazione soggetto Giuridico
 */
 
public class SoggettoGiuridico extends ImportazioneFile{
  
  private static final Integer COD_NATURA= new Integer("1"); // cod soggetto giuridico
  private TerComune comuneImport;
  
  public SoggettoGiuridico(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
    super(file,"ListaSoggettoGiur",new String []{"SoggettoGiur"},"SoggettoModule");
    versione = "1.0";
    if (System.getProperty("esicra.id_istat") != null) {
         String cod =  System.getProperty("esicra.id_istat");
         //§ carica il comune
         comuneImport = (TerComune) DbCommon.cercaRiga("it.saga.siscotel.db.TerComune","cod_comune",cod);
     }  else {
         comuneImport = null;
         logger.warn("CODICE ISTAT COMUNE NON VALORIZZATO !!!!!!!");
     }
  
  }

  protected void  process(String str) throws Exception{
      Document doc = builder.build(new StringReader(str));
      Element soggetto = doc.getRootElement();
      String tipo = soggetto.getName();
      if (tipo.equalsIgnoreCase("SoggettoGiur")) {
          if (doc_letti >= doc_old) {
              processPG(soggetto);
              if (((doc_letti)%STEP)==0){
                  logger.debug("Documenti letti: "+doc_letti);
                  setInElaborazione(doc_letti);
                  committBlock();
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
  private void processPG( Element sgXml ) throws Exception{
      AnaSoggetto sg;
      String cod;
      //§ ATTENZIONE 
      // la provenienza nel AnaSoggetto è integer mentre in SerProvenienza è String !!!
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
      session.saveOrUpdate(sg);
  }
  
  /**
   *  metodo usato per caricare
   *  AnaSoggetto nel db  partendo dall'elemento xml
   * 
   * @param sogXml documento xml
   * @param sogg  soggetto persistente
   * @throws Exception 
   */
  private  void caricaSoggetto(Element sogXml, AnaSoggetto sogg) throws Exception{
    try{
      sogg.setCodSoggetto(CV.text(sogXml.getChild("CodSoggetto",namespace)));
      sogg.setProvenienza(CV.intero(sogXml.getChild("Provenienza",namespace)));
      sogg.setNatura(COD_NATURA); // natura del soggetto
      String cf = CV.text(sogXml.getChild("CodSoggetto",namespace));
      if(cf!=null)
        sogg.setCodiceFiscale(cf.toUpperCase());
      String piva = CV.text(sogXml.getChild("PartitaIva",namespace));
      if(piva!=null)
        sogg.setPartitaIva(piva.toUpperCase());
      sogg.setDenominazione(CV.text(sogXml.getChild("Denominazione",namespace)));
      /*
      sogg.setCognome(CV.text(sogXml.getChild("Cognome",namespace)));
      sogg.setNome(CV.text(sogXml.getChild("Nome",namespace)));
      sogg.setAltriNomi(CV.text(sogXml.getChild("AltriNomi",namespace)));
      sogg.setSesso(CV.intero(sogXml.getChild("Sesso",namespace)));
      sogg.setDtNascita(CV.data(sogXml.getChild("DtNascita",namespace)));
      sogg.setPrecisioneDtNascita(CV.intero(sogXml.getChild("PrecisioneDtNascita",namespace)));
      */
      sogg.setNote(CV.text(sogXml.getChild("Note",namespace)));
      sogg.setDtMod(getDtMod());
      // eventuale rappresentante 
      String codRappresentante=CV.text(sogXml.getChild("CodRappresentante",namespace));
      String desrapp = CV.text(sogXml.getChild("DesRappresentante",namespace));
      AnaRappresentante rapp_Row=null;
      //System.out.println("codRappresentante="+codRappresentante);
      // TODO  ATTENZIONE!! CONTROLLARE
      if (codRappresentante != null){     
        AnaSoggetto srap = (AnaSoggetto) DbCommon.cercaRigaCorrente("it.saga.siscotel.db.AnaSoggetto","cod_soggetto",codRappresentante);
        
      if(srap != null) {
            // salva oggetto transient
            session.saveOrUpdate(sogg);
            // recupera AnaRappresentante
            rapp_Row = DbCommon.cercaRappresentante(sogg,srap);
            if (rapp_Row==null) {
              // non trova l'associazione cercata
              // crea l'associazione che probabilmente mancava  
              rapp_Row = new AnaRappresentante();
              rapp_Row.setAnaSoggetto(sogg);
              rapp_Row.setAnaRappresentante(srap);                    
          }
        }
      }
     // se il rappresentante non è residente mette solo descrizione
     if (rapp_Row == null) {
        if (desrapp != null){
          rapp_Row = new AnaRappresentante();
          rapp_Row.setAnaSoggetto(sogg);
          rapp_Row.setDesRappresentante(desrapp);  
        }
      }
      //  inserisce dati dati
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
        rapp_Row.setIdEnte(new Integer(id_ente));
      }
      // campi standard
      sogg.setIdEnte(new Integer(id_ente));
      session.saveOrUpdate(sogg);
      session.saveOrUpdate(rapp_Row);
    }catch(Exception e){
      doc_saltati ++;
      //cod=null; CONTROLLARE!!
      throw e;
    }
  }
  
  /*
  public void aggiornaProvenienzaSogg(AnaSoggetto sogg, String  codProvenienza,Timestamp dtmod)   
         throws ImportazioneException {

      AnaSoggettoProv sp = null;
      SerProvenienza prov = null;
        
      if (sogg != null) {
          // cerca provenienza
          prov = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","codProvenienza",codProvenienza);
          sp =(AnaSoggettoProv) cercaProvenienza(sogg,prov);
          if(prov==null){
            System.out.println("Codice provenienza non valido");
          }
          if(sp==null) {
              sp = new AnaSoggettoProv();
              sp.setAnaSoggetto(sogg);
              sp.setSerProvenienza(prov);
              sp.setDtMod(dtmod);
              session.save(sp);
          } else{
             // aggiorna solo dt_mod
             sp.setDtMod(dtmod);
             session.update(sp);
          }
      }
  }
  
  public  AnaSoggettoProv cercaProvenienza(AnaSoggetto sg,SerProvenienza prov){
       String q = "FROM AnaSoggettoProv sog  WHERE sog.anaSoggetto = ? "+
        "AND sog.serProvenienza = ? ";
      Query query = session.createQuery(q);
      query.setEntity(0,sg);
      query.setEntity(1,prov);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      if(ite.hasNext())
        return (AnaSoggettoProv) ite.next();
      return null;
  }
  
  public AnaRappresentante cercaRappresentante(AnaSoggetto sog,AnaSoggetto rap){
      String q = "FROM AnaRappresentante sog  WHERE sog.anaSoggetto = ? "+
        "AND sog.anaRappresentante = ? ";
      Query query = session.createQuery(q);
      query.setEntity(0,sog);
      query.setEntity(1,rap);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      if(ite.hasNext())
        return (AnaRappresentante) ite.next();
      return null;
  }
  */
  
 /**
  *   metodo di test
  */
  public static void main(String[] args){
    //§ impostazioni per configurazione
    //--
    String xsltPath = "c://esicra/tomcat/webapps/siscotel/xslt";
    String xsdPath =  "c://esicra/tomcat/webapps/siscotel/xsd/2.0";
    System.setProperty("esicra.xslt.dir",xsltPath);
    System.setProperty("esicra.xsd.dir",xsdPath);
    System.setProperty("esicra.id_ente","8240");
    //--
    EsicraConf.configura();
    File file = new File("c://esicra/test/siscotel/SGI_A_20040317112600.XML");
    SoggettoGiuridico sogg = null;
    try{
      sogg = new SoggettoGiuridico(file);
      sogg.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
    //System.out.println("*** FINE ****");
  }
  
}

