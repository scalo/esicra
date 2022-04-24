package it.saga.siscotel.esicra.anagrafeestesa.webservice.test;


import junit.framework.*;

public class HelloWorldWsTest extends TestCase {

    private  HelloWorldWsProxy test;
    
    private static String endPoint ="http://127.0.0.1:8080/siscotel/servlet/rpcrouter";
   
    public HelloWorldWsTest(String name) {
      super(name);
    }

    public HelloWorldWsProxy objectFactory (){
      HelloWorldWsProxy ref = new HelloWorldWsProxy();
      ref._setSoapURL(endPoint);
      return ref;
    }
    
    protected void setUp() throws Exception {
        test = objectFactory();
        assertNotNull(test);
    }
    
    protected void tearDown() {
       
    }
    
    public void testSayHello() throws Exception{
      String str = "Tironi di Saga";
      String res = test.sayHello(str);
      assertNotNull("Stringa restituita Nulla");
      System.out.println(res);
    }
     
    public static Test suite() {

        TestSuite suite=new TestSuite();
        suite.addTest(new HelloWorldWsTest("testSayHello"));
      
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
