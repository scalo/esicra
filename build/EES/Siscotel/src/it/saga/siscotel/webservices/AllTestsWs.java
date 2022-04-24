package it.saga.siscotel.webservices;

import it.saga.siscotel.webservices.serviziscolastici.*;
import it.saga.siscotel.webservices.pagamenti.*;
import it.saga.siscotel.webservices.profilazioni.*;
import it.saga.siscotel.webservices.albofornitori.*;
import java.util.Calendar;
import junit.framework.*;

public class AllTestsWs  extends TestCase{

  private static String endPoint ="http://10.77.98.19:8080/esicra/servlet/rpcrouter";
  
	public static Test suite(){
			TestSuite suite = new TestSuite();
      
			suite.addTest(MenseScolasticheWsTest.suite(endPoint));
      suite.addTest(TrasportoScolasticoWsTest.suite(endPoint));
			suite.addTest(CentriSportiviRicreativiWsTest.suite(endPoint));
      //pagamenti
      suite.addTest(PagamentoWsTest.suite(endPoint));
      // profilazioni
      suite.addTest(ProfilazioneWsTest.suite(endPoint));
      
      // albo fornitori
      
      suite.addTest(EesAlboFornitoriWsTest.suite(endPoint));
      
      return suite;
	}
  
	public static void main(String[] args) {
		if (args.length>0){
            endPoint=args[0];
    }
    System.out.println("TEST ENDPOINT --> "+endPoint);
    System.out.println("*********** INIZIO TESTS WEBSERVICES SISCOTEL SERVIZI SCOLASTICI E ALBO FORNITORI ["+Calendar.getInstance().getTime()+"] ***********");
    junit.textui.TestRunner.run(suite());
    System.out.println("***********  FINE  TESTS WEBSERVICES SISCOTEL SERVIZI SCOLASTICI E ALBO FORNITORI ["+Calendar.getInstance().getTime()+"] ***********");
    
	}

}
