package it.saga.siscotel.esicra.anagrafeestesa;

import junit.framework.*;
import junit.swingui.*;
import it.saga.siscotel.esicra.anagrafeestesa.webservice.base.*;
import java.util.*;

public class AllTestsWsSiscotel extends TestCase{

    // endpoint
    static String endPoint ="http://localhost:9090/esicra/servlet/rpcrouter";
    //static String endPoint ="http://10.42.42.1:8080/esicra/servlet/soaprouter";
    
    public static Test suite(){

        TestSuite suite = new TestSuite();
        // test di unita' dei web services da testare
        suite.addTest(EesAnagrafeEstesaWsTest.suite(endPoint));
  
        return suite;
    }

    public static void main(String[] args) {
        if (args.length>0){
            endPoint=args[0];
        }
        System.out.println("TEST ENDPOINT --> "+endPoint);
        System.out.println("*********** INIZIO TESTS WEBSERVICES SISCOTEL ANAGRAFE ESTESA ["+Calendar.getInstance().getTime()+"] ***********");
        junit.textui.TestRunner.run(suite());
        System.out.println("***********  FINE  TESTS WEBSERVICES SISCOTEL ANAGRAFE ESTESA ["+Calendar.getInstance().getTime()+"] ***********");
    }
}

