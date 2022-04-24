package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;

/**
 *    Task Generico di esempio per la classe EesTask usato nel test
 */

public class EesThreadGen extends EesThread {

  public EesThreadGen(){
    super();
  }

  public EesThreadGen(long ms){
    super(ms);
  }
  
  public void process(){
    System.out.println("Task "+this.getId()+" ms: "+ System.currentTimeMillis());
  }

  public void stop(){
    super.stop();
    System.out.println("Thread "+ this.getId()+ " fermato" );
  }
}