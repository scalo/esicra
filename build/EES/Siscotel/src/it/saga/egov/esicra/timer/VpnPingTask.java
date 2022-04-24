package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import java.net.*;

public class VpnPingTask extends CoordinatorTask {
    
    // script linux per vpn
    public final String CISCO_PRE="/usr/local/bin/cvpnup";
    public final String CISCO_POST="/usr/local/bin/cvpndown";
    public final String OPENVPN_PRE="/usr/local/bin/ovpnup";
    public final String OPENVPN_POST="/usr/local/bin/ovpndown";
    
    public String SCRIPT_PRE="no";
    public String SCRIPT_POST="no";
    
    public VpnPingTask() {
        super();
        scriptVpn();
    }

    public VpnPingTask(long dataInizio, long frequenza, boolean[] settimana) {
        super(dataInizio, frequenza, settimana);
        scriptVpn();
    }
    
    public void scriptVpn(){
      String type = System.getProperty("esicra.vpn");
      if(type!=null && type.equalsIgnoreCase("cisco")){
          SCRIPT_PRE=CISCO_PRE;
          SCRIPT_POST=CISCO_POST;
      }else if(type!=null && type.equalsIgnoreCase("openvpn")){
          SCRIPT_PRE=OPENVPN_PRE;
          SCRIPT_POST=OPENVPN_POST;
      }
    }
    
    public void pre() {
        logger.debug("pre start vpn up");
        ShellTask pre = new ShellTask(SCRIPT_PRE);
        pre.process();
        logger.debug("vpn up");
    }

    public void process() {
        ShellTask ping = new ShellTask("ping -c 2 10.10.10.10 ");
        ShellTask ping_tiru = new ShellTask("ping -c 2 10.77.1.147 ");
        ping.process();
        ping_tiru.process();
    }

    public void post() {
        logger.debug("post vpn down");
        ShellTask post = new ShellTask(SCRIPT_POST);
        post.process();
        logger.debug("vpn down down");
    }

    public static void main(String[] args) {
        EsicraConf.configura();
        VpnPingTask test = new VpnPingTask();
        System.out.println("** completo " + test.completo);
        System.out.println("VpnPingTask " + test.calcolaUrlPat());
        test.process();
    }
}
