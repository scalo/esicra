package it.saga.siscotel.webservices.profilazioni;

import junit.framework.*;

public class ProfilazioneWsTest extends ProfilazioneAbstractTest {

    private static String endPoint ="http://127.0.0.1:9090/esicra/servlet/rpcrouter";
    
    public ProfilazioneWsTest(String name) {
      super(name);
    }

    public ProfilazioneInt objectFactory (){
      ProfilazioneWsStub ref = new ProfilazioneWsStub();
      ref._setSoapURL(this.endPoint);
      return ref;
    }

    protected void tearDown(){
    }
    
    public static Test suite() {

        TestSuite suite=new TestSuite();
        suite.addTest(new ProfilazioneWsTest("testInserisciProfilazione"));
        suite.addTest(new ProfilazioneWsTest("testCercaListaProfilazione"));
        return suite;
        
    }
    
    public static Test suite(String endpoint) {
        endPoint = endpoint;
        Test suite = suite();
        return suite;
    }
    
    public static void main(String args[]){
      System.out.println("TEST DI UNITA' SERVIZI SCOLASTICI SISCOTEL CREMONA\n\n");
      junit.textui.TestRunner.run(suite());
    }   
    
}
