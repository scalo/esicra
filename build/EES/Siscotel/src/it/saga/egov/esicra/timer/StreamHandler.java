package it.saga.egov.esicra.timer;

import java.io.*;
import java.util.*;
import org.apache.log4j.*;
import it.saga.egov.esicra.*;

public class StreamHandler extends Thread {

    Logger logger;
    InputStream is;
    String type;

    public StreamHandler(InputStream is, String type) {
        this.is = is;
        this.type = type;
        // recupera logger
        logger=EsicraConf.getLogger(this.getClass());
    }

   /**
    *    Indirizza lo standard output e lo standard error sul log
    */
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null){
              if (type.equalsIgnoreCase("ERROR")){
                logger.error(line);
              }
              else if(type.equalsIgnoreCase("OUTPUT")){
                logger.info(line);
              }
              else{
                logger.error("ATTENZIONE tipo di stream non supportato");
                break;
              }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }   
    }
}
