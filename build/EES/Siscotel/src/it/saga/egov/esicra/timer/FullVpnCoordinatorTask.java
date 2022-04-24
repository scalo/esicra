package it.saga.egov.esicra.timer;

public class FullVpnCoordinatorTask extends VpnCoordinatorTask {

  /**
   *  override dell'attributo per rendere la sincronizzazione completa
   */
  
  public FullVpnCoordinatorTask() {
    super();
    completo=true;
  }

  public FullVpnCoordinatorTask(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
    completo=true;
  }

}