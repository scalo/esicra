package it.saga.egov.esicra.vpn;

import it.saga.egov.esicra.timer.*;

public class EesVpn {
  
  private static boolean configurato=false;

  public static final String CISCO_PRE="/usr/local/bin/ciscovpn-up-coordinazione";
  public static final String CISCO_POST="/usr/local/bin/ciscovpn-down";
  public static final String OPENVPN_PRE="/usr/local/bin/openvpn-up-coordinazione";
  public static final String OPENVPN_POST="/usr/local/bin/openvpn-down";
    
  static public String SCRIPT_PRE="no";
  static public String SCRIPT_POST="no";

  static public void  up(){
    if(!configurato){
      // configura gli script in base al tipo di vpn
      scriptVpn();
    }
    ShellTask pre = new ShellTask(SCRIPT_PRE);
    pre.process();
  }
  
  static public void down(){
    ShellTask post = new ShellTask(SCRIPT_POST);
    post.process();
  }
  
  static private void scriptVpn(){
      String type = System.getProperty("esicra.vpn");
      if(type!=null && type.equalsIgnoreCase("cisco")){
          SCRIPT_PRE=CISCO_PRE;
          SCRIPT_POST=CISCO_POST;
      }else if(type!=null && type.equalsIgnoreCase("openvpn")){
          SCRIPT_PRE=OPENVPN_PRE;
          SCRIPT_POST=OPENVPN_POST;
      }
  }
  
}