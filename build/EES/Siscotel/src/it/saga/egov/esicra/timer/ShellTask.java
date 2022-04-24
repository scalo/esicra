package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import org.apache.log4j.*;

public class ShellTask extends EesThread{

    String[] cmd = new String[3];
    Logger logger;
    
    public ShellTask(String arg ) {
        logger=EsicraConf.getLogger(this.getClass());
        String osName = System.getProperty("os.name");
        //System.out.println("OS:"+osName);
        if (osName.startsWith("Windows")) {
            cmd[0] = "cmd.exe";
            cmd[1] = "/C";
            cmd[2] = arg;
        }
        else { // linux
            cmd[0] = "/bin/sh";
            cmd[1] ="-c";
            cmd[2] = arg;
        }
    }

    public void process() {
      try {
          Runtime rt = Runtime.getRuntime();
          logger.info("ShellTask " + cmd[0] + " " + cmd[1]);
          Process proc = rt.exec(cmd);

          StreamHandler errorGobbler = new StreamHandler(proc.getErrorStream(),
                  "ERROR");
          StreamHandler outputGobbler = new StreamHandler(proc.getInputStream(),
                  "OUTPUT");
                  
          errorGobbler.start();
          outputGobbler.start();

          int exitVal = proc.waitFor();
          logger.info("Terminato ShellTask valore di uscita: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EsicraConf.configura();
        ShellTask test = new ShellTask("ping 10.77.99.99");
        test.process();
    }
  
}
