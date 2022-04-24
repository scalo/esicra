package it.saga.siscotel.webservices.serviziscolastici;

import junit.framework.*;

public class TrasportoScolasticoWsTest extends TrasportoScolasticoAbstractTest {

    private static String endPoint ="http://localhost:9090/esicra/servlet/rpcrouter";
    
    public TrasportoScolasticoWsTest(String name) {
      super(name);
    }

    public TrasportoScolasticoInt objectFactory (){
      TrasportoScolasticoWsStub ref = new TrasportoScolasticoWsStub();
      ref._setSoapURL(this.endPoint);
      //ref.endpoint= this.endPoint;
      return ref;
    }

    protected void tearDown(){
    }
    
    public static Test suite() {
        System.out.println("TEST DI UNITA' TRASPORTO SCOLASTICO SISCOTEL CREMONA\n");
        TestSuite suite=new TestSuite();
        suite.addTest(new TrasportoScolasticoWsTest("testInserisciPraIscrizioneTrasportoScolastico"));
        suite.addTest(new TrasportoScolasticoWsTest("testCercaPraIscrizioneTrasportoScolastico"));
        suite.addTest(new TrasportoScolasticoWsTest("testInserisciPraRecessoTrasportoScolastico"));
        suite.addTest(new TrasportoScolasticoWsTest("testCercaPraRecessoTrasportoScolastico"));
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

