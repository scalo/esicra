package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.egov.esicra.xml.PrettyBean;
import it.saga.siscotel.beans.serviziscolastici.PraIscrizioneCentroBean;
import it.saga.siscotel.beans.serviziscolastici.PraRecessoCentroBean;
import java.util.Date;
import junit.framework.*;
import java.math.*;
import it.saga.siscotel.webservices.Servizi;

/**
 *      Classe astratta per il test di unità per i servizi scolastici
 *      Definisce i metodi di test che verranno usati dalle sottoclassi 
 *      CentriSportiviRicreativiTest e CentriSportiviRicreativiWsTest
 */

public abstract class CentriSportiviRicreativiAbstractTest extends TestCase {

    private  CentriSportiviRicreativiInt test;
    protected static BigDecimal idPraticaIscrizione;
    protected static BigDecimal idPraticaRecesso;
        
    public CentriSportiviRicreativiAbstractTest(String name) {
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
    public abstract CentriSportiviRicreativiInt objectFactory();

    public void testInserisciPraIscrizioneCentriSportiviRicreativi() throws Exception {
      System.out.println("INSERIMENTO PRATICA ISCRIZIONE CENTRI SPORTIVI E RICREATIVI");
      PraIscrizioneCentroBean iscrizioneTrasportoBean =PraIscrizioneCentroBean.test();
      iscrizioneTrasportoBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_ISCRIZIONE_CENTRO_SPORTIVO));
      iscrizioneTrasportoBean.getDatiPratica().getPratica().setOggetto("Domanda di Iscrizione Centro Sportivo per Marco Pinoli");
      idPraticaIscrizione = test.inserisciPraIscrizioneCentro(iscrizioneTrasportoBean);
      System.out.println("Pratica Iscrizione Centro Sportivo id:"+idPraticaIscrizione);
      assertNotNull("PraIscizioneCentriSportiviRicreativi test non inserita", idPraticaIscrizione);
      Thread.sleep(1200);
    }
    
    public void testCercaPraIscrizioneCentriSportiviRicreativi() throws Exception {
      System.out.println("CERCA ISCRIZIONE CENTRI SPORTIVI E RICREATIVI");
      // paramentri obbligatori
      String codicefiscale = "PNLGNN77H8763HJZ";
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // parametri opzionali
      BigDecimal idPratica= null;
      Integer stato=null;
      Date dtInizio=null;
      Date dtFine = null;
      System.out.println("codice fiscale:"+codicefiscale);
      PraIscrizioneCentroBean[] array = test.cercaPraIscrizioneCentro(
        codicefiscale,idEnteDestinatario,idPratica,stato,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pratica di iscrizione mensa trovata, array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATA PRATICA: " + ((PraIscrizioneCentroBean)array[i]).getDatiPratica().getPratica().getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PraIscrizioneCentroBean)array[i]));
          }
      }
    }

    public void testInserisciPraRecessoCentriSportiviRicreativi() throws Exception {
      System.out.println("INSERIMENTO PRATICA RECESSO CENTRI SPORTIVI E RICREATIVI");
      PraRecessoCentroBean recessoTrasportoBean=PraRecessoCentroBean.test();
      recessoTrasportoBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_RECESSO_CENTRO_SPORTIVO));
      recessoTrasportoBean.getDatiPratica().getPratica().setOggetto("Domanda di Recesso Centro Sportivo per Marco Pinoli");
      idPraticaRecesso = test.inserisciPraRecessoCentro(recessoTrasportoBean);
      System.out.println("Pratica Recesso Mensa id:"+idPraticaRecesso);
      assertNotNull("PraIscizioneCentriSportiviRicreativi test non inserita", idPraticaRecesso);
      Thread.sleep(1200);
    }
    
    public void testCercaPraRecessoCentriSportiviRicreativi() throws Exception {
       System.out.println("CERCA RECESSO CENTRI SPORTIVI E RICREATIVI");
      // paramentri obbligatori
      String codicefiscale = "PNLGNN77H8763HJZ";
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // parametri opzionali
      BigDecimal idPratica= null;
      Integer stato=null;
      Date dtInizio=null;
      Date dtFine = null;
      System.out.println("codice fiscale:"+codicefiscale);
      PraRecessoCentroBean[] array = test.cercaPraRecessoCentro(
        codicefiscale,idEnteDestinatario,idPratica,stato,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pratica di iscrizione mensa trovata, array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATA PRATICA: " + ((PraRecessoCentroBean)array[i]).getDatiPratica().getPratica().getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PraRecessoCentroBean)array[i]));
          }
      }
    }
}
