package it.saga.siscotel.webservices.serviziscolastici;

import junit.framework.*;
import java.text.SimpleDateFormat;

public class CentriSportiviRicreativiTest extends CentriSportiviRicreativiAbstractTest {
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public CentriSportiviRicreativiTest(String name) {
      super(name);
    }
    
    protected void tearDown() {   
    }
    
    public static Test suite() {
        System.out.println("TEST DI UNITA' CENTRI SPORTIVI E RICREATIVI SISCOTEL CREMONA\n");
        TestSuite suite=new TestSuite();
        suite.addTest(new CentriSportiviRicreativiTest("testInserisciPraIscrizioneCentriSportiviRicreativi"));
        suite.addTest(new CentriSportiviRicreativiTest("testCercaPraIscrizioneCentriSportiviRicreativi"));
        suite.addTest(new CentriSportiviRicreativiTest("testInserisciPraRecessoCentriSportiviRicreativi"));
        suite.addTest(new CentriSportiviRicreativiTest("testCercaPraRecessoCentriSportiviRicreativi"));
        return suite;
    }

    public  CentriSportiviRicreativiInt objectFactory (){
        return new CentriSportiviRicreativiWs();
    }
    
    public static void main(String args[]){
        System.setProperty("esicra.local", "true");
        junit.textui.TestRunner.run(suite());
    }
}
