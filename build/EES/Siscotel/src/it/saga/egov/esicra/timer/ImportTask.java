package it.saga.egov.esicra.timer;

import java.io.*;
import java.util.*;
import it.saga.egov.esicra.*;
import it.saga.egov.esicra.importazione.*;
import java.lang.reflect.*;
import org.apache.regexp.*;

/**
 * TimerTask per l'importazione di file 
 */
 
public class ImportTask extends EesTask  {

 /**
  *   Costanti riguardanti i path delle dir di importazione etc.
  */

  protected static final String ARCH ="arch";

  protected static File importDir=null;
  protected static File archDir=null;
  protected static String dirName;
  
  public ImportTask(){
    super();
    dirName  = System.getProperty("esicra.import.dir");
    if(dirName.length()==0)
      logger.error("Directory per le importazioni non configurata");
    importDir = new File(dirName);
    archDir = new File(importDir,ARCH);
    if(!archDir.exists()){
      archDir.mkdir();
      logger.info("Directory "+archDir.toString()+ " creata");
    }  
  }

  /*
  public String nome(){
    return "ImportTask";
  }
  */
  
  public void process() {
    ElencoFilesImportazione elenco = new ElencoFilesImportazione();
    // scandisce la directory di importazione alla ricerca di nuovi file
    String[]  listaNomiFiles = elenco.getListaFiles();
    // importazione
    if(listaNomiFiles==null){
      logger.info("Nessun file da importare");
      return;
    }
    for(int i=0; i<listaNomiFiles.length ;i++){
      String nomefile = listaNomiFiles[i];
      String sigla = ElencoFilesImportazione.getSigla(nomefile);
      File sorgente = new File(importDir, nomefile);
      logger.debug("sorgente: "+ sorgente.toString());
      try{
        //<<
        Class classe = elenco.getClasseImportazione(sigla);
        if(classe==null){
          logger.error("Importazione del file "+ nomefile+" non possibile non esiste classe di importazione");
        }
        Constructor costruttore = classe.getDeclaredConstructor(
          new Class[]{ sorgente.getClass() });
        // istanza la classe tramite reflection 
        ImportParser imp = (ImportParser )costruttore.newInstance(new Object[]{sorgente});
        imp.parse(); 
        //>>
        // sposta il file nella dir dei files importati
        // dell' oggetto File 
        logger.debug("File "+ nomefile+" importato");
        File destinazione = new File(archDir,nomefile);
        boolean res = sorgente.renameTo(destinazione);
        logger.debug("destinazione: "+ destinazione.toString()+" rename="+res);
      }catch(Exception e){
          e.printStackTrace();  
          logger.error(e);
      }
    }
    
    spostaFilesOk();
  }

  private void spostaFilesOk(){
    //logger.info("Fine Importazione dati");
    // Sposta anche i files di terminazione
    File[] elencoOk = null;
    File[] elencoXml = null;
    File path = new File(dirName);
    RE rex = null;
    String terminePattern = "^TERMINATO_(\\d{14})\\.OK$";
    try{
      rex = new RE(terminePattern);
    }catch(Exception e){
        logger.error("Rex error");
        return ;
      }
    FilenameFilter filterOk = new FilenameFilter() {
      public boolean accept(File dir, String name) {
            name = name.toLowerCase();
            return name.endsWith(".ok");
      }
    };
    elencoOk = path.listFiles(filterOk);
    FilenameFilter filterXml = new FilenameFilter() {
      public boolean accept(File dir, String name) {
            name = name.toLowerCase();
            return name.endsWith(".xml");
      }
    };
    elencoXml = path.listFiles(filterXml);
    for(int i=0 ; i<elencoOk.length;i++){
        boolean completo = true;
        File sorgente = elencoOk[i];
        String name = sorgente.getName();
        // se non esiste alcun xml
        String data="XXX";
        if(rex.match(name.toUpperCase())){
            data= rex.getParen(1);
            //System.out.println("DATA="+data);
        }
        for(int j=0;j<elencoXml.length;j++){
            String nameXml = elencoXml[j].getName();
            //System.out.println("NAME: "+nameXml);
            String end=data+".xml";
            //System.out.println("end "+end);
            if(nameXml.toLowerCase().endsWith(end)){    
                completo=false;
                //System.out.println("comp");
            }    
        }      
        if(completo){   //sposta file di ok
            File destinazione = new File(archDir,sorgente.getName());
            boolean res=sorgente.renameTo(destinazione);
            logger.debug("destinazione: "+ destinazione.toString()+" rename="+res);
        }
    }
  }

  private void test1(){
    ElencoFilesImportazione elenco = new ElencoFilesImportazione();
    String[]  listaNomiFiles = elenco.getListaFiles();
    String nomefile = "SOG_A_20031120165545.XML";
    //String nomefile = "TEST.XML";
    String sigla = ElencoFilesImportazione.getSigla(nomefile);
    File sorgente = new File(importDir, nomefile);
    logger.debug("sorgente: "+ sorgente.toString());
   
    try{
        
        // la parte seguente utilizza la reflection per istanziare l'oggetto per l'importazione
        Class classe = elenco.getClasseImportazione("SOG");
        if(classe==null){
          logger.error("Importazione del file "+ nomefile+" non possibile non esiste classe di importazione");
        }
        
        Constructor costruttore = classe.getDeclaredConstructor(
          new Class[]{ sorgente.getClass() }); 
        // istanza la classe tramite reflection 
        ImportParser imp = (ImportParser)costruttore.newInstance(new Object[]{sorgente});
        
        imp.parse(); 
        //>>
        
        System.out.println("SRC: "+sorgente);
        // sposta il file nella dir dei files importati
        // dell' oggetto File 
        //logger.info("File "+ nomefile+" importato");
        File newDir = new File(importDir,ARCH);
        File destinazione = new File(newDir,sorgente.getName());
        System.out.println("DEST: "+destinazione);
        boolean res = sorgente.renameTo(destinazione);
        if(res==false){
          System.out.println("File "+ sorgente + " non spostato");
        }else{
          System.out.println("File "+ sorgente + " spostato correttamente" );
        }
      }catch(Exception e){
          e.printStackTrace();
          logger.error("Errore nel Task di Import "+e.getMessage());
      }
  }

  private void test2(){
    ElencoFilesImportazione elenco = new ElencoFilesImportazione();
    String[]  listaNomiFiles = elenco.getListaFiles();
    for(int i=0;i<listaNomiFiles.length;i++){
        System.out.println(listaNomiFiles[i]);
    }
    spostaFilesOk();
  }
     
  /**
   *    Metodo di test per verificare lo spostamento
   */
  public static void main(String[] args) {
    EsicraConf.configura();
    ImportTask test = new ImportTask();
    test.test2();
  }
  
}

