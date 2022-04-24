package it.saga.siscotel.esicra.anagrafeestesa.webservice.base;


import junit.framework.*;
import it.saga.siscotel.esicra.anagrafeestesa.webservice.stubs.EesAnagrafeEstesaWsStub;


/**
 * 
 *  Test di unità remoto (Stub)
 * 
 */
public class EesAnagrafeEstesaWsTest extends EesAnagrafeEstesaAbstractTest {

    private static String endPoint ="http://127.0.0.1:8080/esicra/servlet/rpcrouter";
    //private static String endPoint ="http://rsportal3:9080/esicra/servlet/rpcrouter";
    //private static String endPoint ="http://127.0.0.1:7070/esicra/servlet/soaprouter";
    
    public EesAnagrafeEstesaWsTest(String name) {
      super(name);
    }

    public EesAnagrafeEstesaInt objectFactory (){
      EesAnagrafeEstesaWsStub ref = new EesAnagrafeEstesaWsStub();
      ref.endpoint= this.endPoint;
      return ref;
    }

    protected void tearDown(){
    }
    
    public static Test suite() {

        TestSuite suite=new TestSuite();
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoFisicoIndiceCF"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoFisicoIndiceId"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoFisicoIndiceNome"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoFisicoIndiceInd"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoGiuridicoIndicePI"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoGiuridicoIndiceId"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoGiuridicoIndiceEnte"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaOggettoIndiceCE"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaOggettoIndiceIC"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaOggettoIndiceInd"));  
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaSoggettoProvenienza"));
        suite.addTest(new EesAnagrafeEstesaWsTest("testCercaOggettoProvenienza"));
      
        return suite;
        
    }
    
    public static Test suite(String endpoint) {
        endPoint = endpoint;
        Test suite = suite();
        return suite;
    }
    
    public static void main(String args[]){
      System.out.println("TEST DI UNITA' ANAGRAFE ESTESA SISCOTEL CREMONA\n\n");
      junit.textui.TestRunner.run(suite());
    }   
    
}
