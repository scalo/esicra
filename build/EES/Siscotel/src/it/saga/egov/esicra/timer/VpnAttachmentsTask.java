package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.vpn.EesVpn;

public class VpnAttachmentsTask extends AttachmentsTask {
  
  public VpnAttachmentsTask() {
    super();
  }
  
  public void pre(){
    logger.debug("pre allegati vpn up");
    EesVpn.up();
    logger.debug("allegati vpn up");
  }
  
  public void post(){
    logger.debug("post allegati vpn down");
    EesVpn.down();
    logger.debug("allegati vpn down");
  }
  
}