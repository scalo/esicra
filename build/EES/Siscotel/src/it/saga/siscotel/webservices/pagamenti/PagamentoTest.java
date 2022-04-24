package it.saga.siscotel.webservices.pagamenti;

import it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheInt;
import it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWs;
import junit.framework.*;
import java.text.SimpleDateFormat;

public class PagamentoTest extends PagamentoAbstractTest{
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public PagamentoTest(String name) {
      super(name);
    }
    
    protected void tearDown() {       
    }
    
    public static Test suite() {
        System.out.println("PagamentoTest");
        TestSuite suite=new TestSuite();
        suite.addTest(new PagamentoTest("testRegistraPagamento"));
        //suite.addTest(new PagamentoTest("testCercaPagamento"));
        return suite;
    }

    public  PagamentoInt objectFactory (){
        return new PagamentoWs();
    }
    
    public  MenseScolasticheInt objectFactoryPratica (){
        return new MenseScolasticheWs();
    }

    public static void main(String args[]){
      System.setProperty("esicra.local", "true");
      junit.textui.TestRunner.run(suite());
    }
    
}

