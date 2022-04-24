package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;
import java.util.*;

public class EesTimerTest  {

  public EesTimerTest() {
  }

  public static void main(String[] args) throws Exception {
    EesTimer timer = new EesTimer(1000); // intervallo di un sec
   
    // riempie il timer con dei task
    EesTask4Test2 et1 = EesTask4Test2.test1();
    EesTask4Test2 et2 = EesTask4Test2.test2();
    EesTask4Test2 et3 = EesTask4Test2.test3();
    // inserisci
    System.out.println("* inserisci");
    //et2.setUltimaEsecuzione(System.currentTimeMillis()-(60*1000*60*24));
    timer.inserisci(et1);
    timer.inserisci(et2);
    timer.inserisci(et3);
    show(timer);
    // sospendi
    System.out.println("* sospendi "+et2.getId());
    timer.sospendi(et2.getId());
    show(timer);
    // riprendi
    System.out.println("* riprendi "+ et2.getId());
    timer.riprendi(et2.getId());
    show(timer);
    //cancella
    System.out.println("* cancella "+ et1.getId());
    timer.cancella(et1.getId());
    show(timer);
    //salva
    System.out.println("* salva");
    timer.salva();
    show(timer);
    // rimuovi brutalmente simulando interruzione 
    System.out.println("* simulazione rimozione forzata");
    timer.taskMap.clear();
    show(timer);
    //carica
    System.out.println("* carica");
    timer.carica();
    //esegui
    timer.start();
    System.out.println("* esegui");
    show(timer);
    //aspetta 
    Thread.sleep(100000);
    timer.stop();
  }
  
  /**
   *    Metodo statico di utilità che visualizza l'elenco dei task memorizzati
   */
  static void show(EesTimer timer){
    Iterator ite =  timer.list().iterator();
    if (!ite.hasNext()){
      System.out.println("Lista task: [");
      System.out.println("<vuota>");
      System.out.println("]");
    }else{
      System.out.println("Lista task: [");
      synchronized(ite){
        while(ite.hasNext()){
          EesTask t = (EesTask) ite.next();
          System.out.println(t.toString());
        }
      }
      System.out.println("]");
    }
  }
  
}
