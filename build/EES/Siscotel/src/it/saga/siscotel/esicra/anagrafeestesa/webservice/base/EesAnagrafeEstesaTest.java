package it.saga.siscotel.esicra.anagrafeestesa.webservice.base;


import junit.framework.*;
import java.text.SimpleDateFormat;

/**
 *  Test di unità locale
 * 
 *  TODO controllare dati
 * 
 */

public class EesAnagrafeEstesaTest extends EesAnagrafeEstesaAbstractTest {
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public EesAnagrafeEstesaTest(String name) {
      super(name);
    }
    
    protected void tearDown() {
       
    }
    
    public static Test suite() {
        System.out.println("EesAnagrafeEstesaTest");
        TestSuite suite=new TestSuite();
        
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoFisicoIndiceCF"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoFisicoIndiceId"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoFisicoIndiceNome"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoFisicoIndiceInd"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoGiuridicoIndicePI"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoGiuridicoIndiceId"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoGiuridicoIndiceEnte"));
        
        suite.addTest(new EesAnagrafeEstesaTest("testCercaOggettoIndiceCE"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaOggettoIndiceIC"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaOggettoIndiceInd"));
        
        suite.addTest(new EesAnagrafeEstesaTest("testCercaSoggettoProvenienza"));
        suite.addTest(new EesAnagrafeEstesaTest("testCercaOggettoProvenienza"));
        
        return suite;
    }

    public  EesAnagrafeEstesaInt objectFactory (){
        return new EesAnagrafeEstesaWs();
    }

    public static void main(String args[]){
        junit.textui.TestRunner.run(suite());
    }
    
}

