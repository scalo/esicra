package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;
import it.saga.egov.esicra.timer.EesTask;

/**
 *    Task di Test 1 per la classe EesTask usato nel test
 */

public class EesTask4Test1 extends EesTask {

  String msg;

  public EesTask4Test1(String m){
    super();
    msg=m;
  }

  public String nome(){
    return "EesTask2Test1";
  }
  
  public void process(){
    try{
      System.out.println("Task "+this.getId()+" ms: "+ System.currentTimeMillis());
      //Thread.currentThread().sleep(4000);
      for(int i=0 ; i<10 ;i++){
        System.out.println(msg);
        Thread.currentThread().sleep(700);
      }
      System.out.println("Task "+ this.getId()+ " terminato" );
    }catch (Exception e){
      
    }
  }
  
}