package it.saga.siscotel.webservices.serviziscolastici;

import junit.framework.*;
import java.text.SimpleDateFormat;

public class MenseScolasticheTest extends MenseScolasticheAbstractTest {
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public MenseScolasticheTest(String name) {
      super(name);
    }
    
    protected void tearDown() {   
    }
    
    public static Test suite() {
        System.out.println("TEST DI UNITA' MENSE SCOLASTICHE SISCOTEL CREMONA\n");
        TestSuite suite=new TestSuite();
        suite.addTest(new MenseScolasticheTest("testInserisciPraIscizioneMensaScolasticaErrata"));
        suite.addTest(new MenseScolasticheTest("testInserisciPraIscizioneMensaScolastica"));
        suite.addTest(new MenseScolasticheTest("testCercaPraIscizioneMensaScolastica"));
        suite.addTest(new MenseScolasticheTest("testInserisciPraRecessoMensaScolastica"));
        suite.addTest(new MenseScolasticheTest("testCercaPraRecessoMensaScolastica"));
        return suite;
    }

    public  MenseScolasticheInt objectFactory (){
        return new MenseScolasticheWs();
    }
    
    public static void main(String args[]){
        System.setProperty("esicra.local", "true");
        junit.textui.TestRunner.run(suite());
    }
    
}

