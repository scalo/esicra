package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;
 
/**
 *  Classe di test che utilizza implementazione EesTaskGen
 */
public class EesTaskTest  {

  public static void main(String[] args) throws Exception {

    EesTask4Test1 test1 = new EesTask4Test1("cucù");
    System.out.println("id task :"+ test1.getId());
    test1.start();
    EesTask4Test1 test2 = new EesTask4Test1("cuciù");
    System.out.println("id task :"+ test2.getId());
    test2.start();
    
  }
  
}