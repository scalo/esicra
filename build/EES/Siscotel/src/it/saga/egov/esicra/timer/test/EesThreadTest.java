package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;

/**
 *  Classe di test che utilizza implementazione EestaskGen
 */
public class EesThreadTest  {

  public static void main(String[] args) throws Exception {
    EesThreadGen test1 = new EesThreadGen(100);
    System.out.println("id thread :"+ test1.getId());
    Thread.currentThread().sleep(1);
    test1.start();
    EesThreadGen test2 = new EesThreadGen(100);
    System.out.println("id thread :"+ test2.getId());
    test2.start();
    Thread.currentThread().sleep(5000);
    
    test1.stop();
    test2.stop();
  }
  
}