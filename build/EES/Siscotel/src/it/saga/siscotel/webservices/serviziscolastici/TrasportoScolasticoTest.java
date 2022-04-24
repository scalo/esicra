package it.saga.siscotel.webservices.serviziscolastici;

import junit.framework.*;
import java.text.SimpleDateFormat;

public class TrasportoScolasticoTest extends TrasportoScolasticoAbstractTest {
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public TrasportoScolasticoTest(String name) {
      super(name);
    }
    
    protected void tearDown() {   
    }
    
    public static Test suite() {
        System.out.println("TEST DI UNITA' TRASPORTO SCOLASTICO SISCOTEL CREMONA\n");
        TestSuite suite=new TestSuite();
        suite.addTest(new TrasportoScolasticoTest("testInserisciPraIscrizioneTrasportoScolastico"));
        suite.addTest(new TrasportoScolasticoTest("testCercaPraIscrizioneTrasportoScolastico"));
        suite.addTest(new TrasportoScolasticoTest("testInserisciPraRecessoTrasportoScolastico"));
        suite.addTest(new TrasportoScolasticoTest("testCercaPraRecessoTrasportoScolastico"));
        return suite;
    }

    public  TrasportoScolasticoInt objectFactory (){
        return new TrasportoScolasticoWs();
    }
    
    public static void main(String args[]){
        System.setProperty("esicra.local", "true");
        junit.textui.TestRunner.run(suite());
    }
    
}

