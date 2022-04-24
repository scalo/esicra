package it.saga.egov.esicra.timer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.Hashtable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.naming.*;
import java.util.*;
import org.apache.log4j.*;
//<< PER TEST
import it.saga.egov.esicra.timer.test.*;
import it.saga.egov.esicra.*;
//

/**
 *  Classe di configurazione del Timer
 *  Registra il timer e le properties di questo nel Naming Service Jndi.
 *  
 *  esicra.timer              EesTimer
 *  
 *  Properties caricate
 *  
 *   !!! CONTROLLARE !!!
 *  esicra.timer.WorkingDir   path relativo della working del timer
 *  esicra.timer.ImportDir    nome dir dove saranno cercati files di importazione
 *  esicra.timer.ArchDir      dir dove saranno spostati files una volta caricati
 *  esicra.timer.ExportDir    dir dove preparare i files di esportazione
 *  
 *    @todo spostare  il metodo statico in EesTimer e rimuovere la classe
 */
 
public class EesTimerConf{

  /**
   *  logger condiviso da tutte le classi inerenti al Timer
   */
  static Logger logger=null;
  
  /**
   *  Nome con cui il timer viene riregistrato nel Naming Service jndi
   *  nel contesto java:comp/env/esicra
   */
  public static final String JNDI_TIMER_NAME="esicra.timer";

  
  //public static final String[] tipi={"ImportTask","CoordinatorTask","FullCoordinatorTask","AttachmentsTask","ExportPagamentiTask","ExportPraticheTask","DummyTask"};
 
  public static long DELAY = 300000; // 300 s

  /**
   *  Tasks disponibili
   * 
   */
  public static Hashtable lista_tasks= new Hashtable();
  
  /**
   *  Restituisce la lista dei task disponibili
   */
  public static Hashtable listaTasks(){
    return lista_tasks;
  }
  
  /**
   *  @deprecated usare invece la servlet di ContextListener
   */
  public static void configura(){
    EsicraConf.configura();
    logger=EsicraConf.getLogger(EesTimer.class);
    try{
      Context esicraCtx = EsicraConf.getInitialContext(EsicraConf.PATH);
      logger.debug("Context: "+esicraCtx);
      EesTimer timer = null;
      Object obj =  cercaRisorsa(esicraCtx,JNDI_TIMER_NAME);
      logger.debug("Risorsa obj: "+obj);
      if(obj==null){
        // crea oggetto timer e lo registra attraverso in naming service jndi
        String pDelay = System.getProperty("esicra.timer.delay");
        long delay = DELAY;
        if(pDelay!=null){
          // ATTENZIONE LE PROPERTY NUMERICHE VANNO TRIMMATE , altrimenti 
          // NumberFormatException !
          pDelay=pDelay.trim();
          try{
            delay = Long.parseLong(pDelay);
          }catch(Exception e){
            // number format exception
            delay=DELAY;
          }
        }
        timer = new EesTimer(delay);
        esicraCtx.bind(JNDI_TIMER_NAME,timer);
        // carica la lista dei tasks
        timer.carica();
        //System.out.println("Timer registrato");
        // sarebbe buona cosa anche farlo partire il timer !!
        timer.start();
        logger.debug("timer  = "+timer+ " delay = "+delay + " ms");
      }else{
        //Timer già registrato
        timer =(EesTimer) obj;
        logger.error("Timer già registrato");
      }
      logger.debug("carica moduli task");
      loadProperties();
    }catch(NamingException ne){
      System.out.println("Errore Naming");
      System.out.println(ne.toString());
      logger.error(ne);
    }catch(Exception e){
      System.out.println("Errore Generico :" + e);
      e.printStackTrace();
      logger.error(e);
    }
  }

  /**
   *    Carica i task relativi ai moduli abilitati dai files
   *    di properties.
   */
  public static void loadProperties() throws Exception{
    String mod= System.getProperty("esicra.timer.modulo.importazione");
    if (mod != null) {
      boolean res = Boolean.valueOf(mod.trim()).booleanValue();
      if(res){
        // carica properties
        //System.out.println(lista_tasks);
        String propFile=("task_importazione.properties");
        InputStream is=EesTimerConf.class.getResourceAsStream(propFile);
        //System.out.println(is);
        Properties prop = new Properties();
        prop.load(is);
        is.close();
        Enumeration enume = prop.keys();
        while(enume.hasMoreElements()){
          String key=(String)enume.nextElement();
          //System.out.println(key);
          String s=prop.getProperty(key);
          lista_tasks.put(key,s);
        }
      }
    }
    mod= System.getProperty("esicra.timer.modulo.esportazione");
    if (mod != null) {
      boolean res = Boolean.valueOf(mod.trim()).booleanValue();
      if(res){
        // carica properties
        //System.out.println(lista_tasks);
        String propFile=("task_esportazione.properties");
        InputStream is=EesTimerConf.class.getResourceAsStream(propFile);
        //System.out.println(is);
        Properties prop = new Properties();
        prop.load(is);
        is.close();
        Enumeration enume = prop.keys();
        while(enume.hasMoreElements()){
          String key=(String)enume.nextElement();
          //System.out.println(key);
          String s=prop.getProperty(key);
          lista_tasks.put(key,s);
        }
      }
    }
    mod= System.getProperty("esicra.timer.modulo.allegati");
    if (mod != null) {
      boolean res = Boolean.valueOf(mod.trim()).booleanValue();
      if(res){
        // carica properties
        //System.out.println(lista_tasks);
        String propFile=("task_allegati.properties");
        InputStream is=EesTimerConf.class.getResourceAsStream(propFile);
        //System.out.println(is);
        Properties prop = new Properties();
        prop.load(is);
        is.close();
        Enumeration enume = prop.keys();
        while(enume.hasMoreElements()){
          String key=(String)enume.nextElement();
          //System.out.println(key);
          String s=prop.getProperty(key);
          lista_tasks.put(key,s);
        }
      }
    }
  }
    
  /**
   *    Restituisce l'istanza del timer registrato nel contesto jndi di esicra.
   *  
   **/
  public static EesTimer getInstance() {
      //Hashtable env = new  Hashtable();
      EesTimer timer=null;
    try{
      /*
      //env.put(Context.INITIAL_CONTEXT_FACTORY,EsicraConf.TYREX_NS);
      Context initialContext = new InitialContext(env);
      System.out.println("initial:"+initialContext);
      Context esicraCtx = (Context) cercaRisorsa(initialContext,"java:comp/env/esicra");
      System.out.println("esicra:"+esicraCtx);
      */
      Context esicraCtx = EsicraConf.getInitialContext(EsicraConf.PATH);
      //logger.debug("esicraCtx = "+esicraCtx);
      timer = (EesTimer) cercaRisorsa(esicraCtx,JNDI_TIMER_NAME);
      //logger.debug("timer  = "+timer);
    }catch(NamingException e){
      logger.error(e);
    }
    return timer;
  }

  /**
   *    Metodo di test che registra nel timer un paio di task, e
   **/
  public static void test(){
    try{
      /*
      Hashtable env=new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY,EsicraConf.TYREX_NS);
      Context initialContext = new InitialContext(env);
      Context esicraCtx = (Context) initialContext.lookup("java:comp/env/esicra");
      */
      Context esicraCtx = EsicraConf.getInitialContext(EsicraConf.PATH);
      EesTimer timer = (EesTimer) esicraCtx.lookup(JNDI_TIMER_NAME);
      if(timer!=null){
        timer.inserisci(DummyTask.test1());
        timer.inserisci(DummyTask.test2());
        timer.inserisci(DummyTask.test3());
        Iterator ite= timer.list().iterator();
        /*
        while(ite.hasNext()){
          System.out.println(ite.next());
        }
        */
      }else{
        System.out.println("Contesto [java:comp/env/esicra] non disponibile");
      }
    }catch(Exception e){
      //nop
    }
  }
  
  /**
   *  Metodo statico per evitare di utilizzare try/catch annidati
   *  
   *  @param    ctx contesto in cui cercare la risorsa
   *  @param    nome  nome della risorsa
   *  @return   Oggetto risorsa oppure null se non trovata
   *
   *  @todo   Da spostare nella servlet EsicraConf
   *
   */
  private static Object cercaRisorsa(Context ctx ,String nome ) {
    Object o = null;
    try{
      return ctx.lookup(nome);
    }catch(NamingException e){
      //e.printStackTrace();
      logger.error(e);
      return o;
    }
  }
  
  public static void main(String[] args) throws Exception{
    /*
    try{
      EesTimerConf.configura();
      //la seconda volta è per testare il factory
      EesTimerConf.configura();
      EesTimer timer =(EesTimer) EesTimerConf.getInstance();
      EesTimerConf.test();
    }catch(Exception e){
      e.printStackTrace();
    }
    */
    EsicraConf.configura();
    EesTimerConf.loadProperties();
    System.out.println(EesTimerConf.listaTasks());
  }
}

