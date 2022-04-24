package it.saga.siscotel.webservices.albofornitori;

import it.saga.egov.esicra.xml.FindUsedBeans;
import it.saga.siscotel.beans.albofornitori.*;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EesAlboFornitoriTest extends EesAlboFornitoriAbstractTest
{

  public EesAlboFornitoriTest(String name)
  {
    super(name);
  }
  
  public static Test suite() 
  {
    System.out.println("EesAlboFornitoriTest");
    TestSuite suite = new TestSuite();

    suite.addTest(new EesAlboFornitoriTest("testGestioneAlbo"));    
    suite.addTest(new EesAlboFornitoriTest("testGestioneCategorie"));   
    suite.addTest(new EesAlboFornitoriTest("testGestioneFornitoriAlbo"));
    suite.addTest(new EesAlboFornitoriTest("testRichiestaAccreditamento"));
    //suite.addTest(new EesAlboFornitoriTest("testEliminazione"));
    return suite;
  }

  public EesAlboFornitoriInt objectFactory ()
  {
    return new EesAlboFornitoriWs();
  }

  public static void main(String args[])
  {
    System.setProperty("esicra.local", "true");
    junit.textui.TestRunner.run(suite());
    
    Class[] arr = new Class[]
    {
      AlboBean.class,
      CategoriaBean.class,
      FornitoreBean.class,
      FornitoreRidottoBean.class,
      FornitureBean.class,
      PraAccreditamentoFornitoreAlboBean.class,
      PraAccreditamentoIterBean.class,
      StatoBean.class
    };
    
    FindUsedBeans.printListBeans(arr);
  }
}
