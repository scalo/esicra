package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.egov.esicra.xml.PrettyBean;
import it.saga.siscotel.beans.serviziscolastici.PraIscrizioneMensaBean;
import it.saga.siscotel.beans.serviziscolastici.PraRecessoMensaBean;
import java.util.Calendar;
import java.util.Date;
import junit.framework.*;
import java.math.*;
import org.apache.soap.SOAPException;
import it.saga.siscotel.webservices.Servizi;
import java.text.SimpleDateFormat;

/**
 *      Classe astratta per il test di unità per i servizi scolastici
 *      Definisce i metodi di test che verranno usati dalle sottoclassi 
 *      MenseScolasticheTest e MenseScolasticheWsTest
 */

public abstract class MenseScolasticheAbstractTest extends TestCase {
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private  MenseScolasticheInt test;
    protected static BigDecimal idPraticaIscrizione;
    protected static BigDecimal idPraticaRecesso;
        
    public MenseScolasticheAbstractTest(String name) {
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
    public abstract MenseScolasticheInt objectFactory();

    public void testInserisciPraIscizioneMensaScolastica() throws Exception {
      System.out.println("INSERIMENTO PRATICA ISCRIZIONE MENSA SCOLASTICA");
      PraIscrizioneMensaBean iscrizioneMensaBean =PraIscrizioneMensaBean.test();
      iscrizioneMensaBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_ISCRIZIONE_MENSA_SCOLASTICA));
      iscrizioneMensaBean.getDatiPratica().getPratica().setOggetto("Domanda di Iscrizione Mensa Scolastica per Marco Pinoli");
      idPraticaIscrizione = test.inserisciPraIscrizioneMensaScolastica(iscrizioneMensaBean);
      System.out.println("Pratica Iscrizione Mensa id:"+idPraticaIscrizione);
      assertNotNull("PraIscizioneMensaScolastica test non inserita", idPraticaIscrizione);
      Thread.sleep(1200);
    }
    
    public void testInserisciPraIscizioneMensaScolasticaErrata() throws Exception {
      System.out.println("INSERIMENTO PRATICA ERRATA ISCRIZIONE MENSA SCOLASTICA");
      PraIscrizioneMensaBean iscrizioneMensaBean =PraIscrizioneMensaBean.test();
      iscrizioneMensaBean.getDatiPratica().getPratica().setIdEnteDestinatario(null);
      iscrizioneMensaBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_ISCRIZIONE_MENSA_SCOLASTICA));
      try{
        idPraticaIscrizione = test.inserisciPraIscrizioneMensaScolastica(iscrizioneMensaBean);
      }catch(SOAPException se){
        System.out.println(se);
      }
      assertNull("PraIscizioneMensaScolastica Incompleta inserita ", idPraticaIscrizione);
      //System.out.println("id:"+idPraticaIscrizione);
      Thread.sleep(1200);
    }
    
    public void testCercaPraIscizioneMensaScolastica() throws Exception {
      System.out.println("CERCA ISCRIZIONE MENSA SCOLASTICA");
      // paramentri obbligatori
      String codicefiscale = "PNLGNN77H8763HJZ";
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // parametri opzionali
      BigDecimal idPratica= null;
      Integer stato=new Integer(1);
      Date dtInizio=Calendar.getInstance().getTime();
      Date dtFine = Calendar.getInstance().getTime();
      
      System.out.println("codice fiscale:"+codicefiscale);
      System.out.println("data inizio:"+sdf.format(dtInizio));
      System.out.println("data fine:"+sdf.format(dtFine));
      PraIscrizioneMensaBean[] array = test.cercaPraIscrizioneMensaScolastica(
        codicefiscale,idEnteDestinatario,idPratica,stato,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pratica di iscrizione mensa trovata, array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATA PRATICA: " + ((PraIscrizioneMensaBean)array[i]).getDatiPratica().getPratica().getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PraIscrizioneMensaBean)array[i]));
          }
      }
    }

    public void testInserisciPraRecessoMensaScolastica() throws Exception {
      System.out.println("INSERIMENTO PRATICA RECESSO MENSA SCOLASTICA");
      PraRecessoMensaBean recessoMensaBean=PraRecessoMensaBean.test();
      recessoMensaBean.getDatiPratica().getPratica().setIdServizio(new BigDecimal(Servizi.ID_RECESSO_MENSA_SCOLASTICA));
      recessoMensaBean.getDatiPratica().getPratica().setOggetto("Domanda di Recesso Mensa Scolastica per Marco Pinoli");
      idPraticaRecesso = test.inserisciPraRecessoMensaScolastica(recessoMensaBean);
      System.out.println("Pratica Recesso Mensa id:"+idPraticaRecesso);
      assertNotNull("PraIscizioneMensaScolastica test non inserita", idPraticaRecesso);
      Thread.sleep(1200);
    }
    
    public void testCercaPraRecessoMensaScolastica() throws Exception {
       System.out.println("CERCA RECESSO MENSA SCOLASTICA");
      // paramentri obbligatori
      String codicefiscale = "PNLGNN77H8763HJZ";
      BigDecimal idEnteDestinatario= new BigDecimal("8240");
      // parametri opzionali
      BigDecimal idPratica= null;
      Integer stato=null;
      Date dtInizio=null;
      Date dtFine = null;
      System.out.println("codice fiscale:"+codicefiscale);
      PraRecessoMensaBean[] array = test.cercaPraRecessoMensaScolastica(
        codicefiscale,idEnteDestinatario,idPratica,stato,dtInizio,dtFine
      );
      if (array == null) {
          System.out.println("Nessuna pratica di iscrizione mensa trovata, array nullo");
      } else {
          System.out.println("numero pratiche :" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Pratica Nulla", array[i]);
              System.out.println("TROVATA PRATICA: " + ((PraRecessoMensaBean)array[i]).getDatiPratica().getPratica().getIdPratica()+"\n");
              System.out.println(PrettyBean.format((PraRecessoMensaBean)array[i]));
          }
      }
    }

}
