package it.saga.siscotel.webservices.serviziscolastici;

import junit.framework.*;

public class MenseScolasticheWsTest extends MenseScolasticheAbstractTest {

    //private static String endPoint ="http://10.77.166.1:9080/esicra/servlet/rpcrouter";
    private static String endPoint ="http://localhost:8080/esicra/servlet/rpcrouter";
    
    public MenseScolasticheWsTest(String name) {
      super(name);
    }

    public MenseScolasticheInt objectFactory (){
      MenseScolasticheWsStub ref = new MenseScolasticheWsStub();
      ref._setSoapURL(this.endPoint);
      return ref;
    }

    protected void tearDown(){
    }
    
    public static Test suite() {
        System.out.println("MENSE SCOLASTICHE WS TEST");
        TestSuite suite=new TestSuite();
        suite.addTest(new MenseScolasticheWsTest("testInserisciPraIscizioneMensaScolastica"));
        suite.addTest(new MenseScolasticheWsTest("testInserisciPraRecessoMensaScolastica"));
        suite.addTest(new MenseScolasticheWsTest("testCercaPraIscizioneMensaScolastica"));
        suite.addTest(new MenseScolasticheWsTest("testCercaPraRecessoMensaScolastica"));
        return suite;
    }
    
    public static Test suite(String end) {
        endPoint = end;
        Test suite = suite();
        return suite;
    }
    
    public static void main(String args[]){
      System.out.println("TEST DI UNITA' SERVIZI SCOLASTICI SISCOTEL CREMONA\n\n");
      junit.textui.TestRunner.run(suite());
    }   
    
}
