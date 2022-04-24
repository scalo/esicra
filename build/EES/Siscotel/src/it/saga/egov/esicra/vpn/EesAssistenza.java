package it.saga.egov.esicra.vpn;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.StreamHandler;
import org.apache.log4j.*;
import java.util.*;
import java.io.*;

/**
 *
 *    Classe utili zzata dalla jsp per l'assistenza remota.
 *    Richiama gli script di attivazione della vpn
 *    
     STATI VPN
      
    0) down
    1) startup
    2) up
    3) shutdown
 
*/
public class EesAssistenza {
    String[] cmd = new String[3];
    Logger logger;
    
    public static final int DOWN = 0 ;
    public static final int STARTUP = 1 ;
    public static final int UP = 2 ;
    public static final int SHUTDOWN = 3 ;
    
    // script linux per vpn
    public static final String CISCO_START = "/usr/local/bin/ciscovpn-up-assistenza";
    public static final String CISCO_STOP = "/usr/local/bin/ciscovpn-down";
    public static final String CISCO_STATUS = "/usr/local/bin/ciscovpn-status";
    public static final String CISCO_IP = "/usr/local/bin/ciscovpn-ip";
    // controllare esistenza script
    public static final String OPENVPN_START = "/usr/local/bin/openvpn-up-assistenza";
    public static final String OPENVPN_STOP = "/usr/local/bin/openvpn-down";
    public static final String OPENVPN_STATUS = "/usr/local/bin/openvpn-status";
    public static final String OPENVPN_IP = "/usr/local/bin/openvpn-ip";
    private String SCRIPT_START="";
    private String SCRIPT_STOP="";
    private String SCRIPT_STATUS="";
    private String SCRIPT_IP="";
    
    private static int vpn_status=0;
    
    public EesAssistenza() {
        // configura in base a os
        logger = EsicraConf.getLogger(this.getClass());
        String osName = System.getProperty("os.name");
        //logger.debug("os.name="+osName);
        if (osName.startsWith("Windows")) {
            cmd[0] = "cmd.exe";
            cmd[1] = "/C";
            //cmd[2] = arg;
        } else { // linux
            cmd[0] = "/bin/sh";
            cmd[1] = "-c";
            //cmd[2] = arg;
        }
        // configura in base a tipo vpn
        String type = System.getProperty("esicra.vpn.assistenza");
        //logger.debug("vpn type="+type);
        if ((type != null) && type.equalsIgnoreCase("cisco")) {
            SCRIPT_START = CISCO_START;
            SCRIPT_STOP = CISCO_STOP;
            SCRIPT_STATUS = CISCO_STATUS;
            SCRIPT_IP = CISCO_IP;
        } else if ((type != null) && type.equalsIgnoreCase("openvpn")) {
            SCRIPT_START = OPENVPN_START;
            SCRIPT_STOP = OPENVPN_STOP;
            SCRIPT_STATUS = OPENVPN_STATUS;
            SCRIPT_IP = OPENVPN_IP;
        }
    }

    private int exec(String arg) {
        cmd[2]=arg;
        int  exitVal;
        try {
            Runtime rt = Runtime.getRuntime();
            
            Process proc = rt.exec(cmd);
            StreamHandler errorHandler = new StreamHandler(proc.getErrorStream(),
                    "ERROR");
            StreamHandler outputHandler = new StreamHandler(proc.getInputStream(),
                    "OUTPUT");
            errorHandler.start();
            outputHandler.start();
            exitVal = proc.waitFor();
            
        } catch (Throwable t) {
            logger.error(t);
            return 1;
        }
        return exitVal;
    }
    
    public static int getStatus(){
      return  vpn_status;
    }
    
    public static void setStatus(int status){
      vpn_status=status;
    }
    
    public void startup() {
      vpn_status=1;
      logger.info("Vpn Assistenza startup " +cmd[0] + " " +cmd[1] + " "+cmd[2]);
      int exitVal = exec(SCRIPT_START);
      logger.info("Valore di uscita: " + exitVal);
    }

    public void shutdown() {
      logger.info("Vpn Assistenza shutdown " +cmd[0] + " " +cmd[1] + " "+cmd[2]);
      vpn_status=3;
      int exitVal = exec(SCRIPT_STOP);
      logger.info("Valore di uscita: " + exitVal);
    }
    
    // test della vpn esplicito
    public void test() {
      // richiama lo status e va ad aggiornare vpn_status;
      int res = exec(SCRIPT_STATUS);
      // secondo lo standard di uscita  degli script unix 0 ok , 1 error  
      if (res==0){
        vpn_status=2;
      }else{
        vpn_status=0;
      }
    }
    
    public String getVpnIp() {
        if (vpn_status != UP) return "";
        StringBuffer ip =new StringBuffer();
        cmd[2]=SCRIPT_IP;
        int  exitVal;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            InputStream is = proc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=br.readLine())!=null){
              ip.append(line);
            }
            exitVal = proc.waitFor();
            // logger.info("Vpn IP: " + ip);
        } catch (Throwable t) {
            logger.error(t);
            return "indirizzo sconosciuto";
        }
        return ip.toString();
    }
}

