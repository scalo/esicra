package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.*;
import it.saga.egov.esicra.timer.EesTask;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.*;
import java.net.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *    Task che innesca la sincronizzazione , richiamando la servlet
 *    /esicra/EesInvia .
 *    Le tabelle sincronizzate sono quelle specificate nella tabella
 *    ser_coordinator.
 */
 
public class CoordinatorTask extends EesTask {

  protected boolean completo = false;
  
  /** 
   *  costruttore senza parametri necessario per utilizzare la reflection
   **/
  public CoordinatorTask(){
    super();
  }

  private boolean getCompleto(){
    return this.completo;
  }
  
  public CoordinatorTask(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
  }
  
  public void process(){
    try{
      logger.info("Task "+this.getId()+" partito ");
      URL url = calcolaUrlPat();
      if (url==null) {
        logger.error("Sincronizzazione fallita");
        return; // ritorna in caso di errore
      }  
      logger.debug("Sincronizzazione "+url.getQuery() );
      InputStream in = url.openStream( );
      in = new BufferedInputStream(in);
      Reader r = new InputStreamReader(in);
      // legge tutto l' http stream
      int c;
      while ((c = r.read( )) != -1) {
        System.out.print((char) c);
      }
      logger.info("Task "+ this.getId()+ " terminato" );
    }catch (Exception e){
      logger.error(e);
    }
  }

  
  /**
   *  Metodo statico che calcola l'url del PAT attraverso le properties 
   *  specificate nel file di configurazione di esicra
   */
  public URL calcolaUrlPat() {
    URL url=null;
    try{
    String ente = System.getProperty("esicra.id_ente");
    String proto = "http://";
    String pat_host  = System.getProperty("esicra.pat.host");
    String pat_port  = System.getProperty("esicra.pat.port");
    String nodo_host = System.getProperty("esicra.nodo.host");
    String nodo_port = System.getProperty("esicra.nodo.port");
    // !!! TODO !!
    // forse è meglio spostare questi controlli in EsicraConf
    if (nodo_host==null || nodo_host.equals("")){
      logger.error("Errore nella configurazione: host nodo non definito");
      return null; // non lancia la sincronizzazione ma logga solo l'errore
    }
    if (pat_host==null || pat_host.equals("")){
      logger.error("Errore nella configurazione: host pat non definito");
      return null ; // non lancia la sincronizzazione ma logga solo l'errore
    }
    if( nodo_port==null || nodo_port.equals("") ){
      logger.error("Errore nella configurazione: porta nodo non definita");
      return null; // non lancia la sincronizzazione ma logga solo l'errore
    }
    if(( pat_port==null || pat_port.equals("") )){
      logger.error("Errore nella configurazione: porta pat non definita");
      return null;
    }
    String nomeApplicazione = System.getProperty("esicra.context");
    if(nomeApplicazione==null) nomeApplicazione="esicra";
    String servletInvia = "/"+nomeApplicazione+"/EesInvia";
    String servletRicevi ="/"+nomeApplicazione+"/EesRicevi";
    String risorsa =proto+nodo_host+":"+nodo_port+servletInvia+"?uri=http://"+pat_host+":"+pat_port+servletRicevi+
      "&id_ente="+ente+"&completo="+completo;
    url = new URL(risorsa);
    }catch(MalformedURLException e){
      logger.error(e);
    }
    return url;
  }
  
  public static CoordinatorTask test(){
    return new CoordinatorTask(
      System.currentTimeMillis()+10000,
      1000*10 , // 10  sec ,
      new boolean[]{true,true,true,true,true,true,true}
    );
  }

  public static void main(String[] args) throws Exception{
    EsicraConf.configura();
    // crea un timer per il test
    EesTimer timer = new EesTimer(3000); // polling ogni 3 sec
    timer.start();
    EesTask task = CoordinatorTask.test();
    timer.inserisci(task);
    task.start();
    // aspetta 1 min
    Thread.currentThread().sleep(1000*60);
  }  
}

