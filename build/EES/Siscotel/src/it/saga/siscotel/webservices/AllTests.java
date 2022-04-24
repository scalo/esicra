package it.saga.siscotel.webservices;


import it.saga.siscotel.webservices.serviziscolastici.*;
import it.saga.siscotel.webservices.pagamenti.*;
import it.saga.siscotel.webservices.profilazioni.*;
import junit.framework.*;

public class AllTests  extends TestCase{

  
	public static Test suite(){
			TestSuite suite = new TestSuite();
			suite.addTest(MenseScolasticheTest.suite());
      suite.addTest(TrasportoScolasticoTest.suite());
			suite.addTest(CentriSportiviRicreativiTest.suite());
      //pagamenti
      
      // profilazioni
      return suite;
	}
  
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
  

}