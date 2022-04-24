package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;
import it.saga.egov.esicra.timer.EesTask;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.*;

/**
 *    Task di Test 2 per la classe EesTask usato nel test
 *    non fa null'altro che aspettare N ms e terminare
 */
public class EesTask4Test2 extends EesTask {

  private  long  N = 7000 ; // 7 sec
  
  private static final SimpleDateFormat sdf = new SimpleDateFormat();

  /* solo per TEST */
  public EesTask4Test2(long n){
    N=n;
  }

  public EesTask4Test2(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
    
  }

  public String nome(){
    return "EesTask2Test2";
  }
  
  public void process(){
    try{
      logger.info("Task "+this.getId()+" partito ");
      Thread.currentThread().sleep(N);
      //termina
      logger.info("Task "+this.getId()+" terminato" );
    }catch (Exception e){
    }
  }

 /**
   *  Metodo statico che istanzia un task di test()
   *  task tra 10 sec
   */
  public static EesTask4Test2 test1(){
    return new EesTask4Test2(
      System.currentTimeMillis()+10000, // tra 10 sec
      1000*60*60*24 , // 1 volta al dì,
      null
    );
  }

 /**
   *  Altro metodo statico che istanzia un altro task di test()
   *  task immediato , e ripetuto ogni giorno
   */
  public static EesTask4Test2 test2(){
    return new EesTask4Test2(
      System.currentTimeMillis(),
      1000*60*60*24 , // 1 volta al dì,
      new boolean[]{false,false,false,false,false,false,false}
    );
  }

  /**
   *  Altro metodo statico che istanzia un altro task di test()
   *  task immediato , e ripetuto ogni giorno
   */
  public static EesTask4Test2 test3(){
    return new EesTask4Test2(
      System.currentTimeMillis()+10000,
      1000*30 , // 20 sec ,
      new boolean[]{true,true,true,true,true,true,true}
    );
  }
    
  
  public static void main(String[] args) {
    EesTask4Test2 t1 = new EesTask4Test2(6000);
    t1.start();
    EesTask4Test2 t2 = new EesTask4Test2(3000);
    t2.start();
  }
  
}