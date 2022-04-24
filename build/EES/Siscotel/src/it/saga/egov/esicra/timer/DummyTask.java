package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.*;
import it.saga.egov.esicra.timer.EesTask;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.*;

/**
 *    Task di Test Generico per la classe EesTask 
 *    non fa null'altro che aspettare N ms e terminare
 */
public class DummyTask extends EesTask {

  private  long  N=15000 ; // 15 sec
  
  private static final SimpleDateFormat sdf = new SimpleDateFormat();

  public DummyTask(){
    super();
  }
  
  public DummyTask(long n){
    N=n;
  }

  public DummyTask(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
  }

  /*
  public String nome(){
    return "EesTaskGen";
  }
  */
  
  public void process(){
    try{
      logger.info("Task "+this.getId()+" partito "+ GregorianCalendar.getInstance().getTime());
      Thread.currentThread().sleep(N);
      //termina
      logger.info("Task "+ this.getId()+ " terminato" );
    }catch (Exception e){
    }
  }

 /**
   *  Metodo statico che istanzia un task di test()
   *  task tra 10 sec
   */
  public static DummyTask test1(){
    return new DummyTask(
      System.currentTimeMillis()+10000, // tra 10 sec
      0, // una sola volta
      null
    );
  }

 /**
   *  Altro metodo statico che istanzia un altro task di test()
   *  task immediato , e ripetuto ogni giorno
   */
  public static DummyTask test2(){
    return new DummyTask(
      System.currentTimeMillis(),
      1000*60*60*24 , // 1 volta al dì,
      null
    );
  }

  /**
   *  Altro metodo statico che istanzia un altro task di test()
   *  task immediato , e ripetuto ogni giorno
   */
  public static DummyTask test3(){
    return new DummyTask(
      System.currentTimeMillis()+10000,
      1000*60 , // 1 min ,
      new boolean[]{true,true,true,true,true,true,true}
    );
  }
    
  
  public static void main(String[] args) {
    EsicraConf.configura();
    DummyTask t1 = new DummyTask(6000);
    t1.start();
    DummyTask t2 = new DummyTask(3000);
    t2.start();
  }  
}

