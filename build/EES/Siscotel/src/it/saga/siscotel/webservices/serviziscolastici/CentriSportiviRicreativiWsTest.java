package it.saga.siscotel.webservices.serviziscolastici;

import junit.framework.*;

public class CentriSportiviRicreativiWsTest extends CentriSportiviRicreativiAbstractTest {

    private static String endPoint ="http://localhost:9090/esicra/servlet/rpcrouter";
    
    public CentriSportiviRicreativiWsTest(String name) {
      super(name);
    }

    public CentriSportiviRicreativiInt objectFactory (){
      CentriSportiviRicreativiWsStub ref = new CentriSportiviRicreativiWsStub();
      ref._setSoapURL(this.endPoint);
      return ref;
    }

    protected void tearDown(){
    }
    
    public static Test suite() {
        System.out.println("TEST DI UNITA' CENTRI SPORTIVI E RICREATIVI SISCOTEL CREMONA\n");
        TestSuite suite=new TestSuite();
        suite.addTest(new CentriSportiviRicreativiWsTest("testInserisciPraIscrizioneCentriSportiviRicreativi"));
        suite.addTest(new CentriSportiviRicreativiWsTest("testCercaPraIscrizioneCentriSportiviRicreativi"));
        suite.addTest(new CentriSportiviRicreativiWsTest("testInserisciPraRecessoCentriSportiviRicreativi"));
        suite.addTest(new CentriSportiviRicreativiWsTest("testCercaPraRecessoCentriSportiviRicreativi"));
        return suite;
    }
    
    public static Test suite(String endpoint) {
        endPoint = endpoint;
        Test suite = suite();
        return suite;
    }
    
    public static void main(String args[]){
      junit.textui.TestRunner.run(suite());
    }   
    
}
