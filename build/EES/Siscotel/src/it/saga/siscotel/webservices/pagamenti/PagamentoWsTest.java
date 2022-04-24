package it.saga.siscotel.webservices.pagamenti;

import it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheInt;
import it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWsStub;
import junit.framework.*;

public class PagamentoWsTest extends PagamentoAbstractTest {

    private static String endPoint ="http://localhost:8080/esicra/servlet/rpcrouter";
    
    public PagamentoWsTest(String name) {
      super(name);
    }

    public PagamentoInt objectFactory (){
      PagamentoWsStub ref = new PagamentoWsStub();
      ref._setSoapURL(this.endPoint);
      return ref;
    }
    
    public  MenseScolasticheInt objectFactoryPratica(){
      MenseScolasticheWsStub ref = new MenseScolasticheWsStub();
      ref._setSoapURL(this.endPoint);
      return ref;
    }

    protected void tearDown(){
    }
    
    public static Test suite() {
      TestSuite suite=new TestSuite();
      suite.addTest(new PagamentoWsTest("testRegistraPagamento"));
      //suite.addTest(new PagamentoWsTest("testCercaPagamento"));
      return suite;
    }
    
    public static Test suite(String endpoint) {
      endPoint = endpoint;
      Test suite = suite();
      return suite;
    }
    
    public static void main(String args[]){
      System.out.println("TEST DI UNITA' PAGAMENTO SISCOTEL CREMONA\n\n");
      junit.textui.TestRunner.run(suite());
    }   
    
}
