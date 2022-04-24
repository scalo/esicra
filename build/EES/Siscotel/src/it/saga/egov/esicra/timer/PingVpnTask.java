package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.*;
import it.saga.egov.esicra.timer.EesTask;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.*;

/**
 *    Test Ping attivando VPN
 */
public class PingVpnTask extends EesTask {

  private  long  N=15000 ; // 15 sec
  
  private static final SimpleDateFormat sdf = new SimpleDateFormat();

  public PingVpnTask(){
    super();
  }
  

  public PingVpnTask(long dataInizio, long frequenza, boolean[] settimana ){
    super(dataInizio,frequenza,settimana);
  }

  
  public void process(){
    try{
      logger.info("Task "+this.getId()+" partito "+ GregorianCalendar.getInstance().getTime());
      Thread.currentThread().sleep(N);
      //termina
      logger.info("Task "+ this.getId()+ " terminato" );
    }catch (Exception e){
    }
  }
}

