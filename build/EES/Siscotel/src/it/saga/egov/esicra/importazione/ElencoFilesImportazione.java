package it.saga.egov.esicra.importazione;

import it.saga.egov.esicra.*;
import java.io.*;
import java.util.*;
import org.apache.regexp.*;
import org.apache.log4j.*;
import java.math.*;

/**
 *   Fornisce la lista delle tipologie dei files di importazione.
 *   I files di importazione possono essere definiti attraverso la seguente espressione
 *   regolare
 *   (<SCHEMA>)_(T|A)_(yyyy)(MM)(dd)(hh)(mm)(SS)
 *   dove
 *   <SCHEMA> è la sigla dello schema 
 *   T --> Totale A --> Aggiornamento
 *   timestamp nel classico formato anno,mese , etc..
 *   
 *   E' stata utilizzata la classe regexp di apache per le espressioni regolari,
 *   poichè classi analoghe sono presenti nell' JDK a partire dall' 1.4 , mentre
 *   quello utilizzato è l'1.3.1
 *   ATTENZIONE , la classe RE non è SINCRONIZZATA , occorre sincronizzare a mano
 *    dove occorre.
 */
public class ElencoFilesImportazione  {

  Logger logger = null;
  RE rex = null;
  File importDir = null;
  File[] arrayFiles=null;
  String[] dirList = null;

  private Map map = null;
  private List idx =null;
  private InputStream is = null;
  
  public ElencoFilesImportazione() {
    // recupera directory di importazione
    String nomeDir = System.getProperty("esicra.import.dir");
    logger = EsicraConf.configuraLogger(this.getClass());
    importDir = new File(nomeDir);
    dirList = importDir.list();
    map = Collections.synchronizedMap(new HashMap());
    idx = Collections.synchronizedList(new ArrayList());
    // legge file di properties e ricostruisce indice per gli schemi di import
    is = this.getClass().getResourceAsStream("importazione_schemi.properties");
    Properties prop = new Properties();
    // carica schemi
    try{
      prop.load(is);
      Enumeration enu = prop.keys();
      while(enu.hasMoreElements()){
        String schema = (String) enu.nextElement();
        String classname =(String) prop.getProperty(schema);
        // considera solo le properties con valori non vuoti!
        if((classname!=null)&&(classname.length()>0)){
          map.put(schema.toUpperCase(),classname.trim());
        }
      }
      is.close();
      is = this.getClass().getResourceAsStream("importazione_ordine.properties");
      prop = null;
      prop = new Properties();
      prop.load(is);
      enu = prop.keys();
      SortedMap tree = Collections.synchronizedSortedMap(new TreeMap());
      while(enu.hasMoreElements()){
        String schema = (String) enu.nextElement();
        //String pos =(String) prop.getProperty(schema);
        // considera solo le properties con valori non vuoti!
        Integer  pos = new Integer(prop.getProperty(schema));
        //idx.add(schema);
        tree.put(pos,schema);
      }
      idx.addAll(tree.values());
      //System.out.println(idx);
      //System.out.println(map);
    }catch(NumberFormatException ne){
        logger.error("Errore nel file di properties import_ordine.properties , formato numero errato");
    }catch(Exception e){
        logger.error("Errore I/O");
    }
  }

  /**
   *    Lista dei files di importazione presenti nella directory definita dalla
   *    property "esicra.import.dir"
   *    L'espressione regolare considera solo il tipo di file di importazione,
   *    è possibile estenderla recuperando il timestamp.
   */
  synchronized public String[] getListaFiles(){
    String[] array=null;
    Vector listaFiles = new Vector();
    Vector listaTermine = new Vector();
    for(int j=0;j<idx.size();j++){
      String schema = (String) idx.get(j);
      String pattern = "^("+schema+")_(T|A)_(\\d{14})\\.XML$";
      try{
        rex = new RE(pattern);
      }catch(Exception e){
        logger.error("Rex error");
        return array;
      }
      for(int i=0; i<dirList.length;i++){
        String filename = dirList[i];
        // considere i filename CASE INSENSITIVE !
        if(rex.match(filename.toUpperCase()))
          listaFiles.add(filename);
      }
    }
    // cerca files di termina esportazione
    String terminePattern = "^TERMINATO_(\\d{14})\\.OK$";
    try{
      rex = new RE(terminePattern);
    }catch(Exception e){
        logger.error("Rex error");
        return array;
    }
    for(int i=0; i<dirList.length;i++){
        String filename = dirList[i];
        // considere i filename CASE INSENSITIVE !
        if(rex.match(filename.toUpperCase()))
          listaTermine.add(rex.getParen(1));
      }
    // toglie dalla lista dei files quelli che  non hanno il termine esportazione
    synchronized(listaFiles){
      Iterator ite = listaFiles.iterator();
      while(ite.hasNext()){
          String fileImport = (String) ite.next();
          boolean found = false;
          for(int j = 0 ; j<listaTermine.size();j++){
              String data = (String) listaTermine.get(j);
              if(fileImport.indexOf(data)>=0)
                  found = true;    
          }
          if (!found) ite.remove();
      }
    }
    // la lista va ordinata sulla data contenuta nel filename
    Comparatore compa = new Comparatore();
    Collections.sort(listaFiles,compa);
    array = new String[listaFiles.size()];
    array = (String[])listaFiles.toArray(array);
    return array;
  }
  
  /**
   *  Classe di test che simula un elenco di files d'importazione in una dir
   */
  public void testSort(){

    Object[] array= {
      "SCH_T_20031120165545.XML",
      "STC_T_20031120165545.XML",
      "SOG_T_20031120165545.XML",

      "SCH_T_20031120145545.XML",
      "STC_T_20031120145545.XML",
      "SOG_T_20031120145545.XML",

      "STC_T_20021120145545.XML",
      "SOG_T_20021120145545.XML",
      "SCH_T_20021120145545.XML"
    };
    Comparatore compa = new Comparatore();
    List list = Arrays.asList(array);
    System.out.println("Disordinata");
    for(int i=0 ; i< list.size();i++){
      System.out.println(list.get(i));
    }
    Collections.sort(list,compa);
    System.out.println("Ordinata");
    for(int i=0 ; i< list.size();i++){
      System.out.println(list.get(i));
    }
  }

  /**
   *  Restituisce la classe java deputata all'importazione di un flusso xml
   *  @param siglaSchema , sigla dello schema utilizzata nel nome del file
   *  @return classe associata al flusso, in caso di ClassNotFoundException è 
   *    restituito "null" e segnalato nel log
   */
  public Class getClasseImportazione (String siglaSchema){
    //String className = System.getProperty(siglaSchema);
    String className = (String) map.get(siglaSchema);
    Class classe = null;
    try{
      classe=Class.forName(className);
    }catch(ClassNotFoundException ce){
      logger.error("Classe "+ className + ": non trovata per la sigla "+ siglaSchema);
      //classe è null
    }
    return classe;
  }

  /**
   *  Restituisce la sigla del file di importazione , ovvero i primi
   *  3 caratteri del nome file.
   */
  public static String getSigla(String nomefile){
    if(nomefile.length()>3){
      return nomefile.substring(0,3).toUpperCase();
    }
    return "";
  }
  
  public static void main(String[] args) {
    EsicraConf.configura();
    // forza la directory di prova
    String dir = "c:/esicra/import";
    System.setProperty("esicra.import.dir",dir);
    System.out.println("esicra.import.dir = "+dir);
    ElencoFilesImportazione test = new ElencoFilesImportazione();
    String[] lista = test.getListaFiles();
    if (lista==null) return;
    for(int i=0 ; i< lista.length;i++){
      System.out.println(lista[i] + "      sigla "+ getSigla(lista[i]));
    }
    
    test.testSort();
  }

  /**
   *  classe anonima per realizzare il confronto tra filename
   */
  class Comparatore implements Comparator{
    RE rex = null ;
    String pat = "(\\w{3})_(A|T)_(\\d+).XML";

    /**
     *  metodo compare su chiave doppia, prima .
     *  @todo  eventalmente ottimizzare il metodo.
     */
    synchronized public int compare(Object o1 , Object o2){
      try{
        rex = new RE(pat);
      }catch(Exception e){
        logger.error("Rex error");
        return 0;
      }
      //System.out.println((String)o1);
      //System.out.println((String)o2);
      rex.match(((String)o1).toUpperCase());
      String sch1 = rex.getParen(1);
      String val1= rex.getParen(3);
      rex.match(((String)o2).toUpperCase());
      String val2=rex.getParen(3);
      String sch2 = rex.getParen(1);
      int res1 = val1.compareTo(val2);  
      if(val1.compareTo(val2)>0){
        return 1;
      }else if(val1.compareTo(val2)<0){
        return -1;
      }// altrimenti uguale , si confronta l'ordine di importazione     
      int pos1 = idx.indexOf(sch1.toUpperCase());
      int pos2 = idx.indexOf(sch2.toUpperCase());
      if(pos1<pos2){
        return -1;
      }else if(pos1>pos2){
        return 1;
      }
      return 0;
    }
  }
}

