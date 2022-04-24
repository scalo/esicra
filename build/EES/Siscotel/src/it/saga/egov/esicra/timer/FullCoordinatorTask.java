package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import java.net.*;

public class FullCoordinatorTask extends CoordinatorTask {

  /**
   *  override dell'attributo per rendere la sincronizzazione completa
   */
  
  public FullCoordinatorTask() {
    super();
    completo=true;
  }

  public FullCoordinatorTask(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
    completo=true;
  }

  public static void main(String[] args) {
    EsicraConf.configura();
    FullCoordinatorTask test = new FullCoordinatorTask();
    System.out.println("** completo "+ test.completo);
    System.out.println("FullCoordinator "+test.calcolaUrlPat());
  }
    
}