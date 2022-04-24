package it.saga.siscotel.esicra.anagrafeestesa.webservice.test;


import junit.framework.*;
import it.saga.siscotel.esicra.anagrafeestesa.webservice.stubs.EesAnagrafeEstesaWsStub;

public class TemperatureServiceWsTest extends TestCase {

    private  TemperatureServiceWsProxy test;
    
    private static String endPoint ="http://127.0.0.1:8080/siscotel/servlet/rpcrouter";
   
    public TemperatureServiceWsTest(String name) {
      super(name);
    }

    public TemperatureServiceWsProxy objectFactory (){
      TemperatureServiceWsProxy ref = new TemperatureServiceWsProxy();
      ref._setSoapURL(endPoint);
      return ref;
    }
    
    protected void setUp() throws Exception {
        test = objectFactory();
        assertNotNull(test);
    }
    
    protected void tearDown() {
       
    }
    
    public void testGetTemp() throws Exception{
      String str = "25034";
      Float temp =test.getTemp(str);
      assertNotNull("Temparatura restituita Nulla");
      System.out.println("Temperatura :"+temp);
    }
     
    public static Test suite() {

        TestSuite suite=new TestSuite();
        suite.addTest(new TemperatureServiceWsTest("testGetTemp"));
      
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