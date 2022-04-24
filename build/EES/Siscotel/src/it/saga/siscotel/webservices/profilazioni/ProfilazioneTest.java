package it.saga.siscotel.webservices.profilazioni;


import junit.framework.*;
import java.text.SimpleDateFormat;

public class ProfilazioneTest extends ProfilazioneAbstractTest {
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public ProfilazioneTest(String name) {
      super(name);
    }
    
    protected void tearDown() {
       
    }
    
    public static Test suite() {
        System.out.println("ProfilazioneTest");
        TestSuite suite=new TestSuite();
        suite.addTest(new ProfilazioneTest("testInserisciProfilazione"));
        suite.addTest(new ProfilazioneTest("testCercaListaProfilazione"));
        return suite;
    }

    public  ProfilazioneInt objectFactory (){
        return new ProfilazioneWs();
    }

    public static void main(String args[]){
      System.setProperty("esicra.local", "true");
      junit.textui.TestRunner.run(suite());
    }
    
}

