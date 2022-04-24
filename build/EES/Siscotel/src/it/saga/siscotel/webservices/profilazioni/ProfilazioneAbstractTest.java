package it.saga.siscotel.webservices.profilazioni;

import it.saga.siscotel.db.ProTipo;
import junit.framework.*;
import java.math.*;


public abstract class ProfilazioneAbstractTest extends TestCase {

    private  ProfilazioneInt test;
    
    public ProfilazioneAbstractTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        test = objectFactory();
        assertNotNull(test);
    }
    
    public static Test suite() {
        System.out.println("createTestableInterface() abstract da implementare ");
        return null;
    }

    /**
     *  Factory per l'oggetto da testare
     */
    public abstract ProfilazioneInt objectFactory();

    public void testInserisciProfilazione() throws Exception{
      System.out.println("INSERISCI  PROFILAZIONE");
      
      BigDecimal idEnte = new BigDecimal("8240");
      BigDecimal idLista = new BigDecimal("10");
      String  valore="Scuola tecnica professionale Vincenzo Gargiulo";
      Boolean res = test.inserisciProfilazione(idLista,idEnte,valore);
      assertTrue("Profilazione non inserita",res.booleanValue());
    }
 
    public void testCercaListaProfilazione() throws Exception{
      System.out.println("CERCA PAGAMENTO");
      // paramentri obbligatori
      BigDecimal idLista =new BigDecimal("10");
      BigDecimal idEnte =new BigDecimal("8240");
      String[] array = test.cercaListaProfilazione(idLista,idEnte);
      if (array == null) {
          System.out.println("Nessuna profilazione trovata, array nullo");
      } else {
          System.out.println("numero profilazioni :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Profilazione Nulla", array[i]);
              //System.out.println("TROVATA PROFILAZIONE \n");
              System.out.println((String)array[i]);
          }
      }
    }
    
}
