package it.saga.egov.esicra.importazione.soggetto;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import java.sql.Timestamp;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;

/**
 *   Importazione soggetto fisico
 */

public class SoggettoFisico extends ImportazioneFile{
  
  private static final Integer COD_NATURA= new Integer("0"); // cod soggetto fisico
  private TerComune comuneImport;
  
  public SoggettoFisico(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
    super(file,"ListaSoggettoFis",new String []{"SoggettoFis"},"SoggettoModule");
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
      if (tipo.equalsIgnoreCase("SoggettoFis")) {
          if (doc_letti >= doc_old) {
              processPF(soggetto);
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
      //aggiornaProvenienzaSogg(sg,provenienza.toString(),getDtMod());
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
      sogg.setNatura(COD_NATURA); // natura del soggetto
      // ATTENZIONE !!! il cod_soggetto 
      String cf = CV.text(sogXml.getChild("CodSoggetto",namespace));
      if(cf!=null)
        sogg.setCodiceFiscale(cf.toUpperCase());
      String piva = CV.text(sogXml.getChild("PartitaIva",namespace));
      if(piva!=null)
        sogg.setPartitaIva(piva.toUpperCase());
      sogg.setCognome(CV.text(sogXml.getChild("Cognome",namespace)));
      sogg.setNome(CV.text(sogXml.getChild("Nome",namespace)));
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
            // OTTIMIZZAZIONE DEL COMUNE
            if((comuneImport != null) && codcomuneXml.equals(comuneImport.toString())){
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
      // campi standard
      sogg.setIdEnte(new Integer(id_ente));
      session.save(sogg);
      
    }catch(Exception e){
      doc_saltati ++;
      //cod=null; CONTROLLARE!!
      throw e;
    }
  }
  
  public void aggiornaProvenienzaSogg(AnaSoggetto sogg, String  codProvenienza,Timestamp dtmod)   
         throws ImportazioneException {

      AnaSoggettoProv sp = null;
      SerProvenienza prov = null;
        
      if (sogg != null) {
          // cerca provenienza
          prov = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","codProvenienza",codProvenienza);
          sp =(AnaSoggettoProv) DbCommon.cercaProvenienza(sogg.getPkid(),prov.getPkid());
          if(prov==null){
            System.out.println("Codice provenienza non valido");
          }
          if(sp==null) {
              //res = (AnaSoggettoProvViewRowImpl) (((AnaSoggettoProvViewImpl) anaProvenienzaView).createRow(new BigDecimal(step)));                
              sp = new AnaSoggettoProv();
              sp.setAnaSoggetto(sogg);
              sp.setSerProvenienza(prov);
              sp.setDtMod(dtmod);
              sp.setIdEnte(new Integer(id_ente));
          } else{
             // aggiorna solo dt_mod
             sp.setDtMod(dtmod);
          }
    }
    session.save(sp);
  }
  
  /*
  public  Object cercaSerProvenienza(String classname,String campo,String valore){
      Object  obj  = null;
      String q = "from "+classname +" where "+campo+"= ?";
      Query query = session.createQuery(q);
      //System.out.println("query = "+q);
      query.setString(0,valore);
      List list = query.list();
      if(list.size()>0){
        obj = list.get(0); 
      }
      return obj;
    }
  
  
  public  AnaSoggettoProv cercaProvenienza(AnaSoggetto sg,SerProvenienza prov){
      //SerProvenienza prov = (SerProvenienza) DbUtilita.cercaSerProvenienza("it.saga.siscotel.db.SerProvenienza","codProvenienza",cod_provenienza);
      //AnaSoggetto sg = (AnaSoggetto) session.get(AnaSoggetto.class,fkid_soggetto);
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
  
  
  // cerca row dato il codice 
  public Object cercaRiga(String classname, String campo ,String codice){
    //§ controllo sui parametri di ingresso
    Object db_Row = null;
    Query query = session.createQuery("from "+classname +" where "+campo+"='"+codice+"'");
    //§ istanzia pojo e recupera
    List list = query.list();
    //System.out.println("SIZE="+list.size());
    if(list.size()>0){
      db_Row = list.get(0);
    }
    return db_Row;
  }
    
  public Object cercaRigaCorrente(String  classname, String campo ,String codice){
    Object db_Row = null;
    Query query = session.createQuery("from "+classname +" where "+campo+"='"+codice+"' and dt_mod is not null" );
    //§ istanzia pojo e recupera
    List list = query.list();
    //System.out.println("SIZE="+list.size());
    if(list.size()>0){
      db_Row = list.get(0); 
    }
    return db_Row;
  } 
  
  /*
  public static void importaSoggettoFisico(SoggettoFisico sogg){
    try{
      sogg.parse();
    }catch(Exception e){
      e.printStackTrace();
      logger.warn(e.getMessage());
    }
  }
  */
  
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
    System.setProperty("esicra.id_ente","8240");
    //--
    EsicraConf.configura();
    File file = new File("c://esicra/test/siscotel/SFI_A_20040317112600.XML");
    SoggettoFisico sogg = null;
    try{
      sogg = new SoggettoFisico(file);
      sogg.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
    //System.out.println("*** FINE ****");
  }
}

