package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.egov.esicra.xml.PrettyBean;
import it.saga.siscotel.beans.serviziscolastici.PraIscrizioneTrasportoBean;
import it.saga.siscotel.beans.serviziscolastici.PraRecessoTrasportoBean;
import java.util.Date;
import junit.framework.*;
import java.math.*;
import org.apache.soap.SOAPException;
import it.saga.siscotel.webservices.Servizi;

/**
 *      Classe astratta per il test di unità per i servizi scolastici
 *      Definisce i metodi di test che verranno usati dalle sottoclassi 
 *      TrasportoScolasticoTest e TrasportoScolasticoWsTest
 */

public abstract class TrasportoScolasticoAbstractTest extends TestCase {

    private  TrasportoScolasticoInt test;
    protected static BigDecimal idPraticaIscrizione;
    protected static BigDecimal idPraticaRecesso;
        
    public TrasportoScolasticoAbstractTest(String name) {
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
    public abstract TrasportoScolasticoInt objectFactory();

    public void testInserisciPraIscrizioneTrasportoScolastico() throws Exception {
      System.out.println("INSERIMENTO PRATICA ISCRIZIONE TRASPORTO SCOLASTICO");
      PraIscrizioneTrasportoBean iscrizaioneTrasportoBean =PraIscrizioneTrasportoBean.test();
      iscrizaioneTrasportoBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_ISCRIZIONE_TRASPORTO_SCOLASTICO));
      iscrizaioneTrasportoBean.getDatiPratica().getPratica().setOggetto("Domanda di Iscrizione Trasporto Scolastico per Marco Pinoli");
      idPraticaIscrizione = test.inserisciPraIscrizioneTrasportoScolastico(iscrizaioneTrasportoBean);
      System.out.println("Pratica Iscrizione Mensa id:"+idPraticaIscrizione);
      assertNotNull("PraIscizioneTrasportoScolastico test non inserita", idPraticaIscrizione);
      Thread.sleep(1200);
    }
    
    public void testCercaPraIscrizioneTrasportoScolastico() throws Exception {
      System.out.println("CERCA ISCRIZIONE TRASPORTO SCOLASTICO");
      // paramentri obbligatori
      String codicefiscale = "PNLGNN77H8763HJZ";
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // parametri opzionali
      BigDecimal idPratica= null;
      Integer stato=null;
      Date dtInizio=null;
      Date dtFine = null;
      System.out.println("codice fiscale:"+codicefiscale);
      PraIscrizioneTrasportoBean[] array = test.cercaPraIscrizioneTrasportoScolastico(
        codicefiscale,idEnteDestinatario,idPratica,stato,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pratica di iscrizione mensa trovata, array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATA PRATICA: " + ((PraIscrizioneTrasportoBean)array[i]).getDatiPratica().getPratica().getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PraIscrizioneTrasportoBean)array[i]));
          }
      }
    }

    public void testInserisciPraRecessoTrasportoScolastico() throws Exception {
      System.out.println("INSERIMENTO PRATICA RECESSO TRASPORTO SCOLASTICO");
      PraRecessoTrasportoBean recessoTrasportoBean=PraRecessoTrasportoBean.test();
      recessoTrasportoBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_RECESSO_TRASPORTO_SCOLASTICO));
      recessoTrasportoBean.getDatiPratica().getPratica().setOggetto("Domanda di Recesso Trasporto Scolastico per Marco Pinoli");
      idPraticaRecesso = test.inserisciPraRecessoTrasportoScolastico(recessoTrasportoBean);
      System.out.println("Pratica Recesso Mensa id:"+idPraticaRecesso);
      assertNotNull("PraIscizioneTrasportoScolastico test non inserita", idPraticaRecesso);
      Thread.sleep(1200);
    }
    
    public void testCercaPraRecessoTrasportoScolastico() throws Exception {
       System.out.println("CERCA RECESSO TRASPORTO SCOLASTICO");
      // paramentri obbligatori
      String codicefiscale = "PNLGNN77H8763HJZ";
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // parametri opzionali
      BigDecimal idPratica= null;
      Integer stato=null;
      Date dtInizio=null;
      Date dtFine = null;
      System.out.println("codice fiscale:"+codicefiscale);
      PraRecessoTrasportoBean[] array = test.cercaPraRecessoTrasportoScolastico(
        codicefiscale,idEnteDestinatario,idPratica,stato,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pratica di iscrizione mensa trovata, array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATA PRATICA: " + ((PraRecessoTrasportoBean)array[i]).getDatiPratica().getPratica().getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PraRecessoTrasportoBean)array[i]));
          }
      }
    }

}
