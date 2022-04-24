package it.saga.siscotel.esicra.anagrafeestesa.webservice.base;

import it.saga.egov.esicra.xml.PrettyBean;
import junit.framework.*;
import java.math.*;
import it.saga.siscotel.srvfrontoffice.beans.base.*;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *      Classe astratta per il test di unità sul servizio di anagrafe estesa 
 *      per la ricerca dei soggetti. 
 *      Definisce i metodi di test che verranno usati dalle sottoclassi 
 *      EesAnagrafeEstesaTest e EesAnagrafeEstesaWsTest
 */

public abstract class EesAnagrafeEstesaAbstractTest extends TestCase {

    private  EesAnagrafeEstesaInt test;
    
    public EesAnagrafeEstesaAbstractTest(String name) {
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
    public abstract EesAnagrafeEstesaInt objectFactory();

     /**
     *  Test di ricerca del soggetto fisico di test Pinoli Gianni,
     *  con codice fiscale: PNLGNN77H8763HJZ.
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoFisicoIndiceCF() throws Exception{
      String cf = "PNLGNN77H8763HJZ";
      String cfs = "MTTLHR61C21Z112U";
      
      System.out.println("CERCA SOGGETTO FISICO INDICE PER CODICE FISCALE :"+cf);
      SoggettoBean soggB = test.cercaSoggettoFisicoIndiceCF(cf);

      assertNotNull("** cerca Soggetto Fisico Indice trovato Nullo", soggB);
      System.out.println("TROVATO SOGGETTO FISICO : SoggettoBean=" + PrettyBean.format(soggB));
      
      System.out.println("CERCA SOGGETTO FISICO INDICE PER CODICE FISCALE :"+cf);
      soggB = test.cercaSoggettoFisicoIndiceCF(cfs);

      assertNotNull("** cerca Soggetto Fisico Indice trovato Nullo", soggB);
      System.out.println("TROVATO SOGGETTO FISICO :\n SoggettoBean=" + PrettyBean.format(soggB));
            
    }
    

   /**
     *  Test di ricerca dei soggetto fisico per identificativi. 
     *  Utilizzato il soggetto di test Pinoli Gianni.
     *  
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoFisicoIndiceId() throws Exception{
      String cognome    = "Pinoli";//null;//
      String nome       = null;
      BigDecimal idEnte = new BigDecimal("8240");
      String desArea    = null;
      Integer numCiv    = null;
      String letCiv     = null;
      
      System.out.println("CERCA SOGGETTO FISICO INDICE CON IDENTIFICATIVO:");
      System.out.println("cognome="+cognome);
      System.out.println("nome="+nome);
      System.out.println("id_ente="+idEnte);
      System.out.println("descrizione_area="+desArea);
      System.out.println("numero_civico="+numCiv);
      System.out.println("lettera_civico="+letCiv+"\n");
      SoggettoBean[] array = test.cercaSoggettoFisicoIndiceId(cognome,
      nome,idEnte,desArea,numCiv,letCiv);
      if (array == null) {
          System.out.println("** Nessun soggetto fisico trovato, array nullo");
      } else {
          System.out.println("numero soggetti fisici trovati:" + array.length+"\n");     
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Soggetto Fisico trovato Nullo", array[i]);
              System.out.println("TROVATO SOGGETTO FISICO :\n SoggettoBean=" + PrettyBean.format((SoggettoBean)array[i]));
          }
      }
    }
    

    /**
     *  Test di ricerca dei soggetto fisico per nome. 
     *  Utilizzato il soggetto di test Pinoli Gianni.
     *  
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoFisicoIndiceNome() throws Exception{
      
      String nome = "Gianni Giovanni";
      BigDecimal idEnte = new BigDecimal("8240");
      String desArea = "Ugo Foscolo";
      Integer numCiv = null;
      String letCiv = null;
      
      System.out.println("CERCA SOGGETTO FISICO INDICE CON NOME :");
      System.out.println("nome="+nome);
      System.out.println("id_ente="+idEnte);
      System.out.println("descrizione_area="+desArea);
      System.out.println("numero_civico="+numCiv);
      System.out.println("lettera_civico="+letCiv+"\n");
      
      SoggettoBean[] array = test.cercaSoggettoFisicoIndiceNome(
      nome,idEnte,desArea,numCiv,letCiv);
      if (array == null) {
          System.out.println("** Nessun soggetto fisico trovato, array nullo");
      } else {
          System.out.println("numero soggetti fisici trovati:" + array.length+"\n");
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Soggetto Fisico trovato Nullo", array[i]);
              System.out.println("TROVATO SOGGETTO FISICO :\n SoggettoBean=" + PrettyBean.format((SoggettoBean)array[i]));
          }
      }
      
    }

    /**
     *  Test di ricerca dei soggetto fisico per indirizzo. 
     *  Utilizzato il soggetto di test Pinoli Gianni.
     *  
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoFisicoIndiceInd() throws Exception{
      
      BigDecimal idEnte = new BigDecimal("8240");
      String desArea = "MAZZINI";
      Integer numCiv = null;
      String letCiv = null;
      
      System.out.println("CERCA SOGGETTO FISICO INDICE CON INDIRIZZO:");
      System.out.println("id_ente="+idEnte);
      System.out.println("descrizione_area="+desArea);
      System.out.println("numero_civico="+numCiv);
      System.out.println("lettera_civico="+letCiv+"\n");
      try {  
          SoggettoBean[] array = test.cercaSoggettoFisicoIndiceInd(idEnte,
                                                       desArea,
                                                       numCiv,
                                                       letCiv);
          if (array == null) {
              System.out.println("Nessun soggetto fisico trovato, array nullo");
          } else {
              System.out.println("numero soggetti fisici:" + array.length+"\n");
          
              for (int i = 0; i < array.length; i++) {
                  assertNotNull("**  Soggetto Fisico trovato Nullo", array[i]);
                  System.out.println("TROVATO SOGGETTO FISICO :\n SoggettoBean=" + PrettyBean.format((SoggettoBean)array[i]));
              }
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }

     /**
     *  Test di ricerca del soggetto giuridico di test 
     *  con denominazione 'GELATERIA PRIMAVERA',
     *  con P.I.V.A.: 01137069875.
     *  
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoGiuridicoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoGiuridicoIndicePI() throws Exception{
        
        String pIva = "01137069875";
    
        System.out.println("CERCA SOGGETTO GIURIDICO INDICE CON PIVA");
        System.out.println("pIva="+pIva);
        
        SoggettoGiuridicoBean soggGB = test.cercaSoggettoGiuridicoIndicePI(pIva);

        assertNotNull("**  Soggetto Giuridico trovato Nullo", soggGB);
        System.out.println("TROVATO SOGGETTO GIURIDICO :\n SoggettoGiuridicoBean=" + PrettyBean.format(soggGB));
         
    }


    /**
     *  Test di ricerca del soggetto giuridico di test 
     *  con denominazione 'GELATERIA PRIMAVERA',
     *  con P.I.V.A.: 01137069875.
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoGiuridicoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoGiuridicoIndiceId() throws Exception{
      String denominazione = "GELATERIA";//
      BigDecimal idEnte = new BigDecimal("8240");//null;//
      String desArea = null;
      Integer numCiv = null;
      String letCiv = null;
      
      System.out.println("CERCA SOGGETTO GIURIDICO  INDICE CON DENOMINAZIONE");
      
      System.out.println("denominazione="+denominazione);
      System.out.println("id_ente="+idEnte);
      System.out.println("descrizione_area="+desArea);
      System.out.println("numero_civico="+numCiv);
      System.out.println("lettera_civico="+letCiv+"\n");
        
      SoggettoGiuridicoBean[] array = test.cercaSoggettoGiuridicoIndiceId(denominazione, 
                                                                  idEnte,
                                                                  desArea, 
                                                                  numCiv,
                                                                  letCiv);
      if (array == null) {
          System.out.println("** Nessun soggetto trovato, array nullo");
      } else {
          System.out.println("numero soggetti giuridico:" + array.length);
          for (int i = 0; i < array.length; i++) {
              assertNotNull("**  Soggetto Giuridico trovato Nullo", array[i]);
              System.out.println("TROVATO SOGGETTO GIURIDICO:\n SoggettoGiuridicoBean=" +PrettyBean.format((SoggettoGiuridicoBean)array[i]));
          }
      }
        
    }

    /**
     *  Test di ricerca del soggetto giuridico di test 
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo SoggettoGiuridicoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoGiuridicoIndiceEnte() throws Exception{
        BigDecimal idEnte = new BigDecimal("8240");
        String desArea = "Ugo Foscolo"; //null; //
        Integer numCiv = null;
        String letCiv = null; //"B";
      
        System.out.println("CERCA SOGGETTO GIURIDICO  INDICE PER  ENTE");
        
        System.out.println("id_ente="+idEnte);
        System.out.println("descrizione_area="+desArea);
        System.out.println("numero_civico="+numCiv);
        System.out.println("lettera_civico="+letCiv+"\n");
        
        SoggettoGiuridicoBean[] array = test.cercaSoggettoGiuridicoIndiceEnte(idEnte,
                                                                        desArea, 
                                                                        numCiv,
                                                                        letCiv);
        if (array == null) {
            System.out.println("Nessun soggetto trovato, array nullo");
        } else {
            System.out.println("numero soggetti giuridici:" + array.length);
            for (int i = 0; i < array.length; i++) {
                assertNotNull("**  Soggetto Giuridico trovato Nullo", array[i]);
                System.out.println("TROVATO SOGGETTO GIURIDICO :\n SoggettoGiuridicoBean=" + PrettyBean.format((SoggettoGiuridicoBean)array[i]));
            }
        }
    }


   /**
     *  Test di ricerca del oggetto territoriale di test con codice ecografico: 
     *   COD_ECO_1 .
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo OggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaOggettoIndiceCE() throws Exception{
        String codEcogr = "COD_ECO_1"; 
        System.out.println("CERCA OGGETTO INDICE PER CODICE ECOGRAFICO:");
        System.out.println("Codice Ecografico="+codEcogr);
        OggettoBean oggB = test.cercaOggettoIndiceCE(codEcogr);
        assertNotNull("** oggetto trovato nullo", oggB);
        
        System.out.println("TROVATO OGGETTO :\n OggettoBean=" + PrettyBean.format(oggB));
      
    }

    /**
     *  Test di ricerca del oggetto territoriale di test con codice ecografico: 
     *   COD_ECO_1 .
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo OggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaOggettoIndiceIC() throws Exception{

        String sezione  = "sez 1";//null;//
        String foglio   = null; //"fgl 4";
        String mappale  = null; // "mapp1";
        String sub      = null;//"2";
        Integer anno    = null;// new Integer("2006");//null;
        String protocollo = null;//"prot 125";//null;
        String tipoCatasto = null;// "F";//null;
        BigDecimal idEnte = new BigDecimal("8240");
      
        System.out.println("CERCA OGGETTO INDICE PER IDENTIFICATIVO CATASTALE:");
        
        System.out.println("id_ente="+idEnte);
        System.out.println("sezione="+sezione);
        System.out.println("foglio="+foglio);
        System.out.println("mappale="+mappale);
        System.out.println("sub="+sub);
        System.out.println("anno="+anno);
        System.out.println("protocollo="+protocollo);
        System.out.println("tipo_catasto="+tipoCatasto);
        
        OggettoBean[] array = test.cercaOggettoIndiceIC(idEnte,
                                                        sezione,
                                                        foglio,
                                                        mappale,
                                                        sub,
                                                        anno,
                                                        protocollo,
                                                        tipoCatasto);
        if (array == null) {
            System.out.println("Nessun oggetto territoriale trovato, array nullo");
        } else {
            System.out.println("numero oggetti indice:" + array.length);
            for (int i = 0; i < array.length; i++) {
                assertNotNull("** oggetto indice trovato Nullo", array[i]);
                System.out.println("TROVATO OGGETTO INDICE:" +PrettyBean.format((OggettoBean)array[i]));
            }
        }
    }


    /**
     *  Test di ricerca del oggetto territoriale di test con codice ecografico: 
     *   COD_ECO_1 .
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo OggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaOggettoIndiceInd() throws Exception{

        BigDecimal idEnte = new BigDecimal("8240");
        String desArea = "Via Roma";
        Integer numCiv = null;//new Integer(15);
        String letCiv = null;
      
        System.out.println("CERCA OGGETTO INDICE PER ENTE:");
        
        System.out.println("id_ente="+idEnte);
        System.out.println("descrizione_area="+desArea);
        System.out.println("numero_civico="+numCiv);
        System.out.println("lettera_civico="+letCiv);
        
        OggettoBean[] array = test.cercaOggettoIndiceInd(idEnte,
                                                        desArea,
                                                        numCiv,
                                                        letCiv);
        if (array == null) {
            System.out.println("Nessun oggetto territoriale trovato, array nullo");
        } else {
            System.out.println("numero oggetti indice: " + array.length);
            for (int i = 0; i < array.length; i++) {
                assertNotNull("** oggetto indice trovato Nullo", array[i]);
                System.out.println("TROVATO OGGETTO INDICE:\nOggettoBean=" + PrettyBean.format((OggettoBean)array[i]));
            }
        }
        
    }


    /**
     *  Test di ricerca degli enti in cui il soggetto è presente.
     *  Il soggetto di test è : Bacca Anna, con cod_fisc:BCCNNA70M41A794K,
     *  e il codice di provenienza è 10.
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo OggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaSoggettoProvenienza() throws Exception{
      String codFisc = "BCCNNA70M41A794K"; 
      String codProv = "10";
      
      System.out.println("CERCA SOGGETTO PER PROVENIENZA:"+codProv);
      System.out.println("cod_provenienza="+codProv);
      System.out.println("cod_fiscale="+codFisc);
      
      SoggettoProvenienzaBean soggB = test.cercaSoggettoProvenienza(codFisc, 
                                                  codProv);

      assertNotNull("** soggetto provenienza non trovato ",soggB);
      System.out.println("TROVATO SOGGETTO PROVENIENZA:\nOggettoBean="+PrettyBean.format(soggB));
        
    }


    /**
     *  Test di ricerca degli enti in cui l'oggetto è presente.
     *  Il codice ecografico dell'oggetto di test è: COD_ECO_4,
     *   e il codice di provenienza è 20.
     *  Il metodo di test richiede come risposta affermativa la restituzione
     *  di un array di bean di tipo OggettoBean.
     *  Questo metodo astratto viene utilizzato sia nel test di unità con connessione
     *  diretta al db , sia per quello del web service.
     */
    public void testCercaOggettoProvenienza() throws Exception{
      String codEcogr = "COD_ECO_4"; 
      String codProv = "20";
      
      System.out.println("CERCA OGGETTO PER PROVENIENZA ");
      System.out.println("cod_provenienza="+codProv);
      System.out.println("cod_ecografico"+codEcogr);
     
      OggettoProvenienzaBean oggB = test.cercaOggettoProvenienza(codEcogr, 
                                                  codProv);

      assertNotNull("oggetto provenienza non trovato",oggB);
      System.out.println("TROVATO OGGETTO PROVENIENZA:\nOggettoBean=" +PrettyBean.format(oggB));
            
    }




}