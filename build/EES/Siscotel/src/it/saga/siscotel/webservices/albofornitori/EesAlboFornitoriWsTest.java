package it.saga.siscotel.webservices.albofornitori;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EesAlboFornitoriWsTest extends EesAlboFornitoriAbstractTest
{

  private static String endPoint ="http://localhost:9090/esicra/servlet/rpcrouter";
  
  public EesAlboFornitoriWsTest(String name) 
  {
    super(name);
  }

  public EesAlboFornitoriInt objectFactory ()
  {
    EesAlboFornitoriWsStub ref = new EesAlboFornitoriWsStub();
    ref.endpoint= this.endPoint;
    
    return ref;
  }

  protected void tearDown(){
  }
  
  public static Test suite()
  {
    TestSuite suite=new TestSuite();
    suite.addTest(new EesAlboFornitoriWsTest("testGestioneAlbo"));    
    suite.addTest(new EesAlboFornitoriWsTest("testGestioneCategorie"));   
    suite.addTest(new EesAlboFornitoriWsTest("testGestioneFornitoriAlbo"));
    suite.addTest(new EesAlboFornitoriWsTest("testRichiestaAccreditamento"));
    //suite.addTest(new EesAlboFornitoriWsTest("testEliminazione"));
    return suite;
      
  }
  
  public static Test suite(String endpoint) 
  {
    endPoint = endpoint;
    Test suite = suite();
    return suite;
  }
  
  public static void main(String args[])
  {
    System.out.println("TEST DI UNITA' ALBO FORNITORI \n\n");
    junit.textui.TestRunner.run(suite());
  }  


}
