package it.saga.siscotel.webservices.pagamenti;

import it.saga.egov.esicra.xml.PrettyBean;
import it.saga.siscotel.beans.base.PagamentoBean;
import it.saga.siscotel.beans.serviziscolastici.PraIscrizioneMensaBean;
import it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheInt;
import junit.framework.*;
import java.math.*;
import java.util.Date;
import it.saga.siscotel.srvfrontoffice.beans.base.*;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 *  
 *  TODO  creare anche pratica
 * 
 */
public abstract class PagamentoAbstractTest extends TestCase {

    private  PagamentoInt test;
    private  MenseScolasticheInt pratica;
    private  BigDecimal idPratica ;
    
    public PagamentoAbstractTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception { 
        test = objectFactory();
        assertNotNull(test);
        pratica = objectFactoryPratica();
        idPratica = pratica.inserisciPraIscrizioneMensaScolastica(
          PraIscrizioneMensaBean.test()
        );
        System.out.println("pratica registrata:"+idPratica);
    }
    
    public static Test suite() {
        System.out.println("createTestableInterface() abstract da implementare ");
        return null;
    }

    /**
     *  Factory per l'oggetto
     */
    public abstract PagamentoInt objectFactory();
    
    public abstract MenseScolasticheInt objectFactoryPratica();
    
    public void testRegistraPagamento() throws Exception{
      System.out.println("REGISTRA PAGAMENTO");
      PagamentoBean pag = PagamentoBean.test();
      PagamentoBean[] pagamenti= new PagamentoBean[1];
      pagamenti[0] = pag;
      BigDecimal idEnteDestinatario = new BigDecimal("8240");
      test.registraPagamento(idPratica,idEnteDestinatario,pagamenti);
    }
    
    public void testCercaPagamento() throws Exception{
      System.out.println("CERCA PAGAMENTO");
      // paramentri obbligatori
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // idPratica impostato attributo globale
      // parametri opzionali
      Date dtInizio=null;
      Date dtFine = null;
      PagamentoBean[] array = test.cercaPagamento(
        idPratica,idEnteDestinatario,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pagamento trovato array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATO PAGAMENTO id pratica:" + ((PagamentoBean)array[i]).getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PagamentoBean)array[i]));
          }
      }
    }
}
