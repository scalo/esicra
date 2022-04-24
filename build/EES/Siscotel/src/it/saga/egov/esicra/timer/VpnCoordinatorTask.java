package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.vpn.EesVpn;

public class VpnCoordinatorTask extends CoordinatorTask {

 /**
  *  override dell'attributo per rendere la sincronizzazione completa
  */
  
  // script linux per vpn
  public final String CISCO_PRE="/usr/local/bin/ciscovpn-up-coordinazione";
  public final String CISCO_POST="/usr/local/bin/ciscovpn-down";
  public final String OPENVPN_PRE="/usr/local/bin/openvpn-up-coordinazione";
  public final String OPENVPN_POST="/usr/local/bin/openvpn-down";
    
  public String SCRIPT_PRE="no";
  public String SCRIPT_POST="no";
  
  public VpnCoordinatorTask() {
    super();
  }

  public VpnCoordinatorTask(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
  }
  
  public void pre(){
    logger.debug("pre coordinator vpn up");
    EesVpn.up();
    logger.debug("coordinator vpn ");
  }
  
  public void post(){
    logger.debug("post coordinator vpn down");
    EesVpn.down();
    logger.debug("coordinator vpn down");
  }
  
  public static void main(String[] args) {
    EsicraConf.configura();
    VpnCoordinatorTask test = new VpnCoordinatorTask();
    System.out.println("** completo "+ test.completo);
    System.out.println("VpnCoordinator "+test.calcolaUrlPat());
    test.process();
  }
    
}
