package it.saga.egov.esicra.importazione;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.importazione.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import org.xml.sax.*;
import org.apache.xerces.jaxp.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import java.io.*;
import java.util.*;
import it.saga.egov.esicra.utilita.*;

import it.saga.siscotel.db.SerImport;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import it.saga.egov.esicra.bc4j.*;
//import it.saga.egov.esicra.bc4j.common.*;


public abstract class  ImportazioneFile extends ImportParser  {
  
  public int STEP=500;
  public SAXBuilder builder = null;
  long start = System.currentTimeMillis();
  private String nomeFile="";

  public int doc_letti =0; 
  public int doc_saltati =0;
  public int doc_old = 0;
  public DbUtilita Utilita;
  
  protected String id_ente;
  
// private Row serImportRow=null; 
// private ViewObject serImportView=null;
 
  private SerImport serImport=null;
  //private Session session= null;
  private Transaction transaction = null;
 
  public ImportazioneFile(File file, String elementoLista ,String[] elemento, String modulo, String pathPackage) throws FileNotFoundException,SAXException{
     super(file);
     id_ente = System.getProperty("esicra.id_ente");
     if (id_ente == null) {
        logger.error("id ente non trovato");
        return;
     }
     builder = null;
     doc_letti = 0;
     nomeFile=file.getName();
     String pathPack = pathPackage+"."+ modulo;
     //super.am = EsicraConf.creaApplicationModule(pathPack);
     /*
     if(am == null) {
      // può essere messo qualsiasi cosa nel package
      am = Configuration.createRootApplicationModule(pathPackage,modulo);
      am.getTransaction().setClearCacheOnCommit(true);
      if (am==null)
        logger.error("Application Module non trovato: "+pathPack);
      else
        logger.info(" Application Module trovato: "+pathPack);
      }
      */
    // properties Sax factory oracle per JDOM
    
    //System.setProperty("javax.xml.parsers.SAXParserFactory","oracle.xml.jaxp.JXSAXParserFactory");
    System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl");
    
    // configura il parser
    listaElementi=elementoLista;
    for (int i=0; i<elemento.length; i++)
       elementi.add(elemento[i]); 
    // elementi.add(elemento);      
    // am = EsicraConf.configuraApplicationModule("module.soggetto");
    builder = new SAXBuilder();
    builder.setValidation(false);
    builder.setIgnoringElementContentWhitespace(true);

    // funzione di utilita per caricamento e ricerca DB
    //Utilita = new DbUtilita(am);

     if (System.getProperty("esicra.import.commit") != null) {
        STEP = Integer.parseInt(System.getProperty("esicra.import.commit"));
     } else {
        logger.warn("Proprietà: esicra.import.commit non valorizzata");         
     }
     
     //§  RECUPERA IMPORTAZIONE O CREA NUOVA
     
     session=HibernateUtil.currentSession();
     transaction = session.beginTransaction();
     serImport = (SerImport) DbCommon.cercaRiga("it.saga.siscotel.db.SerImport","nome_file",fileImportato());
     if (serImport==null){
       serImport = new SerImport();
       serImport.setNomeFile(fileImportato());
     }else{
       doc_old =  serImport.getInElaborazione().intValue();
     }
 
      start = System.currentTimeMillis();
  }

  public ImportazioneFile(File file, String elementoLista ,String[] elemento, String modulo) throws FileNotFoundException,SAXException{        
         this(file, elementoLista ,elemento, modulo,"it.saga.egov.esicra.bc4j");
  }


  public void statistica() {
     long stop = System.currentTimeMillis();   
     Long tempo = new Long (stop-start);
     logger.info("Tempo di elaborazione :"+(tempo)+" ms"); 
     logger.info("Documenti letti: "+documentiLetti());
     logger.info("Documenti elaborati: "+documentiElaborati());
     logger.info("Documenti rigettati: "+rigettati() );
     logger.info("Documenti non validi: "+getNonValidi() );
     
     if (documentiSaltati() > 0)
         logger.info("Documenti Ignorati: "+documentiSaltati());
         
     //§  AGGIORNA IMPORTAZIONE
     
     serImport.setElaborati(new Integer(documentiLetti()));
     serImport.setIgnorati(new Integer(documentiSaltati()));
     serImport.setRigettati(new Integer(rigettati()));
     serImport.setNonValidi(new Integer(getNonValidi()));
     serImport.setTempo(tempo.toString());
     serImport.setDtMod(dtmod);
     serImport.setInElaborazione(new Integer(0));
     serImport.setIdEnte(new Integer(id_ente));
     session.saveOrUpdate(serImport);
     transaction.commit();
     
  }


  public int documentiLetti(){
    return doc_letti;
  }

   public int documentiElaborati(){
    return doc_letti-doc_old;
  }

  public int documentiSaltati(){
    return doc_saltati;
  }

  public String fileImportato(){
    return nomeFile;
  }

  public void setInElaborazione(int letto){
    /*  
    if (serImportRow != null ){
       serImportRow.setAttribute("InElaborazione",new Integer (letto));      
    }
    */
  }

  protected void finalize() throws Throwable {
    try {
      // am.getTransaction().disconnect(false);
      // logger.debug("disconnect() AM "+am.getName());
    }catch(Exception e){
      logger.debug("Errore disconnect() "+e.getMessage());   
    }finally {
      super.finalize();
    }
  }
}
