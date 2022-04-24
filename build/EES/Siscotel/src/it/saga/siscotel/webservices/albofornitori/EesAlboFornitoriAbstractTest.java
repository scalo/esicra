package it.saga.siscotel.webservices.albofornitori;

import it.saga.siscotel.beans.albofornitori.*;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;

public abstract class EesAlboFornitoriAbstractTest extends TestCase
{
  private EesAlboFornitoriInt test;
  
  public static BigDecimal ID_ENTE = new BigDecimal(8240);  
  public static BigDecimal[] ARR_ID_CATEGORIE = null;
  public static BigDecimal[] ARR_ID_ALBO = null;  
  public static BigDecimal[] ARR_ID_FORNITORI = null;
  
  public static CategoriaBean[] ARR_CATEGORIE = null;
  
  public EesAlboFornitoriAbstractTest(String name)
  {
    super(name);
  }
  
  public EesAlboFornitoriAbstractTest()
  {
  
  }

  protected void setUp() throws Exception 
  {
    test = objectFactory();
    assertNotNull(test);
  }
  
  public static Test suite()
  {
    System.out.println("createTestableInterface() abstract da implementare ");
    return null;
  }
  
  /**
   * Eliminazione dei dati inseriti nei TEST
   * -> metodo eliminaCategoria()
   * @throws Exception
   */
  public void testEliminazione() throws Exception
  {
    System.out.println("--> ELIMINAZIONE CATEGORIE ");
    for(int i=0; i<ARR_ID_CATEGORIE.length; i++)
    {
      Boolean flag1 = test.eliminaCategoria(ARR_ID_CATEGORIE[i]);
      assertEquals("Eliminazione CATEGORIA con idCategoria = " + ARR_ID_CATEGORIE[i] + " FALLITA",flag1,new Boolean("true"));
    }
 
    System.out.println("--> ELIMINAZIONE ALBI ");    
    Boolean flag3 = test.eliminaAlbo(ARR_ID_ALBO[0]);
    Boolean flag4 = test.eliminaAlbo(ARR_ID_ALBO[1]);
    Boolean flag5 = test.eliminaAlbo(ARR_ID_ALBO[2]);    
    assertEquals("eliminazione ALBO con idAlbo " + ARR_ID_ALBO[0] + " fallita",flag3,new Boolean("true"));
    assertEquals("eliminazione ALBO con idAlbo " + ARR_ID_ALBO[1] + " fallita",flag4,new Boolean("true"));
    assertEquals("eliminazione ALBO con idAlbo " + ARR_ID_ALBO[2] + " fallita",flag5,new Boolean("true"));
    
    System.out.println("--> ELIMINAZIONE FORNITORI ");
    for(int j=0; j<ARR_ID_FORNITORI.length; j++)
    {
      Boolean flag2 = test.eliminaFornitore(ARR_ID_FORNITORI[j]);
      assertEquals("Eliminazione FORNITORI con idFornitori = " + ARR_ID_FORNITORI[j] + " FALLITA",flag2,new Boolean("true"));
    }

  }
 
  public void testRichiestaAccreditamento() throws Exception
  {
    System.out.println("---> INIZIO test testRichiestaAccreditamento <---"); 
    
    System.out.println("-> INIZIO test inserisciPraAccreditamentoFornitoreAlbo");
    PraAccreditamentoFornitoreAlboBean bean = PraAccreditamentoFornitoreAlboBean.test();
    bean.setIdAlbo(ARR_ID_ALBO[1]);
    
    FornitureBean[] arrForniture = new FornitureBean[1];
    FornitureBean item = FornitureBean.test();
    arrForniture[0] = item;
    item.setCategoria(ARR_CATEGORIE[0]);
    
    bean.setDatiForniture(arrForniture);
    bean.setDatiCategorie(ARR_CATEGORIE);
        
    BigDecimal idRichiesta =  test.inserisciPraAccreditamentoFornitoreAlbo(bean);     
    System.out.println("-> FINE test inserisciPraAccreditamentoFornitoreAlbo");
    
    System.out.println("INIZIO AccreditaFornitore");
    test.AccreditaFornitore(idRichiesta,ARR_ID_CATEGORIE);
    System.out.println("FINE AccreditaFornitore");
    
    System.out.println("-> INIZIO cercaPraAccreditamentoFornitore");
    PraAccreditamentoFornitoreAlboBean[] arrAcc = test.cercaPraAccreditamentoFornitore(ID_ENTE,null,null,null,null,ARR_ID_ALBO[1],null,null);
    assertNotNull("ricerca accreditamento ha restituito un array a null",arrAcc);   
    System.out.println("-> FINE cercaPraAccreditamentoFornitore");
        
    System.out.println("---> FINE test testRichiestaAccreditamento <---"); 
  }
  
  /**
   * TEST GESTIONE FORNITORI. Il test viene svolto nel seguente modo:
   *  -> Vengono caricati i possibili stati di un fornitore (metodo listaStatoFornitore)
   *  -> Viene inserito un nuovo fornitore senza accreditamento (metodo inserisciFornitoreInAlbo)
   *  -> Viene cercato il fornitore appena create (metodo cercaFornitore)
   *  -> Vengono caricati i dati completi del fornitore appena creato (cercaDettagliFornitore)
   *  -> Viene cambiato lo stato del fornitore nell'albo (metodo cambiaStatoFornitore)
   * @throws Exception
   */
  public void testGestioneFornitoriAlbo() throws Exception
  {
    System.out.println("---> INIZIO test testGestioneFornitoriAlbo <---");
    
    ARR_ID_FORNITORI = new BigDecimal[3];
    
    //---------- TEST listaStatoFornitore ---------------
    System.out.println("--> INIZIO Caricamento STATI FORNITORI");
    
    StatoBean[] arrStati = test.listaStatoFornitore(null);
    assertNotNull("lista stati non caricata",arrStati);
    
    if(arrStati != null)
    {
      for(int i=0; i<arrStati.length; i++)
      {
        System.out.println("-> (" + i+ ")" + arrStati[i].toString());
      }
    }
    
    System.out.println("--> FINE Caricamento STATI FORNITORI");
    
    //---------- TEST inserisciFornitoreInAlbo ---------------
    System.out.println("--> INIZIO Inserimento nuovo fornitore senza accreditamento");

    BigDecimal idFornitore = test.inserisciFornitoreInAlbo(ARR_ID_ALBO[1],ARR_ID_CATEGORIE,FornitoreBean.testSoggettoFisico());
    ARR_ID_FORNITORI[0] = idFornitore;
    assertNotNull("Fornitore non creato",idFornitore);

    System.out.println("--> FINE Inserimento nuovo fornitore senza accreditamento");
    
    //---------- TEST cercaFornitore ---------------    
    System.out.println("--> INIZIO Ricerca fornitori");
    
    FornitoreRidottoBean[] arr = test.cercaFornitore(ID_ENTE,ARR_ID_ALBO[1],null,null);
    assertNotNull("Lista fornitori is NULL",arr);
    
    if(arr != null)
    {
      for(int i=0; i<arr.length; i++)
      {
        System.out.println("-> " + arr[i].toString());
      }
    }
    
    System.out.println("--> FINE Ricerca fornitori");

    //---------- TEST cercaDettagliFornitore ---------------      
    System.out.println("--> INIZIO test carica fornitore idFornitore = " + idFornitore);  
    
    FornitoreBean fornitore = test.cercaDettagliFornitore(idFornitore);
    assertNotNull("Dettaglio fornitore non trovato",fornitore);    
    System.out.println("--> FINE test testGestioneFornitoriAlbo");

    //---------- TEST cambiaStatoFornitore ---------------    
    System.out.println("--> INIZIO test cambiaStatoFornitore");
    
    if(ARR_ID_CATEGORIE != null)
    {
      for(int j=0; j<ARR_ID_CATEGORIE.length; j++)
      {
        System.out.println("--> ID ALBO = " + ARR_ID_ALBO[1]);
        System.out.println("--> ID FORNITORE = " + idFornitore);
        System.out.println("--> ID CATEGORIA = " + ARR_ID_CATEGORIE[j]);
        System.out.println("--> ID STATO = " + arrStati[1].getIdStato());
      
        Boolean flag2 = test.cambiaStatoFornitore(ARR_ID_ALBO[1],idFornitore,ARR_ID_CATEGORIE[j],new BigDecimal(2));
        assertEquals("Cambio stato fornitore=" + idFornitore + " fallito", new Boolean("true"),flag2);
      }
    }     
    
    System.out.println("--> INIZIO test cercaCategorieFornitoreAlbo");
    CategoriaBean[] arrCat = test.cercaCategorieFornitoreAlbo(ARR_ID_ALBO[1],idFornitore);
    assertNotNull("categorie associate al fonitore non trovate",arrCat);    
    System.out.println("---> FINE test testGestioneFornitoriAlbo <---");

  }
  
  
  /**
   * TEST GESTIONE ALBI. Il test viene svolto nel seguente modo:
   * -> Viene testato il metodo creaAlbo(),vengono creati 3 Albi con:
   *    -> La data di sistema è minore della DATA INIZIO (Stato NON ATTIVO)
   *    -> La data di sistema appartiene all'intervallo [DATA INIZIO ,DATA FINE] (Stato ATTIVO)
   *    -> La data di sistema è maggiora alla DATA FINE (Stato SCADUTO)
   *  -> Viene testao il metodo cercaAlbo(), restituisce un elecon degli Albi appena creati
   *  -> Viene testato il metodo rinnovaAlbo()
   *  -> Viene testao il metodo chiudiAlbo()
   * @throws Exception
   */
  public void testGestioneAlbo() throws Exception
  {
    System.out.println("---> INIZIO test testGestioneAlbo() - ID_ENTE " + ID_ENTE + " <--");
    
    ARR_ID_ALBO = new BigDecimal[3];
    
    // ------- TEST creaAlbo ---------
    System.out.println("--> INIZIO test creaAlbo() ");
    long dateNow = System.currentTimeMillis();
    long TwoMonth = new Long("25184000000").longValue();
    long OneMonth = new Long("2592000000").longValue();
    
    BigDecimal idAlbo = null;
    AlboBean item = AlboBean.test(); 
    
    // -- CASO 1 - la data corrente date prima dell'intervallo
    Date dataInizio_1 = new Date(new Long(dateNow-TwoMonth).longValue());
    Date dataFine_1 = new Date(new Long(dateNow-OneMonth).longValue());

    item.setDesAlbo("TEST ALBO 1");
    item.setDataInizioValidita(dataInizio_1);
    item.setDataFineValidita(dataFine_1);
    
    idAlbo = test.creaAlbo(item);
    ARR_ID_ALBO[0] = idAlbo;
    assertNotNull("idAlbo is NULL",idAlbo);
    
    System.out.println("---> (1) Id Categoria = " + idAlbo);
        
    //-- CASO 2
    Date dataInizio_2 =  new Date(new Long(dateNow-OneMonth).longValue());
    Date dataFine_2 =  new Date(new Long(dateNow+OneMonth).longValue());

    item.setDesAlbo("TEST ALBO 2");
    item.setDataInizioValidita(dataInizio_2);
    item.setDataFineValidita(dataFine_2);
    
    idAlbo = test.creaAlbo(item);
    ARR_ID_ALBO[1] = idAlbo;
    
    assertNotNull("id albo is NULL",idAlbo);
    
    System.out.println("---> (2) Id Categoria = " + idAlbo);
     
    //-- CASO 3
    Date dataInizio_3 = new Date(dateNow+OneMonth);
    Date dataFine_3 = new Date(dateNow+TwoMonth);
    
    item.setDesAlbo("TEST ALBO 3");
    item.setDataInizioValidita(dataInizio_3);
    item.setDataFineValidita(dataFine_3);
    
    idAlbo = test.creaAlbo(item);
    ARR_ID_ALBO[2] = idAlbo;
    assertNotNull("id albo is NULL",idAlbo);
    
    System.out.println("---> (3) Id Categoria = " + idAlbo);

    System.out.println("--> FINE test creaAlbo() ");
        
    // ------- TEST  cercaAlbo ------- 
    System.out.println("--> INIZIO test cercaAlbo ");
    AlboBean[] arr = test.cercaAlbo(ID_ENTE);
    assertNotNull("lista ALBO nel metodo cercaAlbo is NULL",arr);
    System.out.println("--> FINE test cercaAlbo ");
    
    if(arr != null)
    {
      for(int i=0; i<arr.length; i++)
      {
        System.out.println("---> (" + i + ") " + arr[i].toString());  
      }
      
      // ----- TEST rinnovaAlbo -------
      System.out.println("--> INIZIO test rinnovaAlbo()");
      
      arr[0].setDataInizioValidita(dataInizio_3);
      arr[0].setDataFineValidita(dataFine_3);
      
      Boolean flag1 = test.rinnovaAlbo(arr[0]);
      assertEquals("aggiormaento albo fallito",flag1,new Boolean("true"));
      
      AlboBean[] arr1 = test.cercaAlbo(ID_ENTE);
             
      for(int i=0; i<arr1.length; i++)
      {
        System.out.println("---> (" + i + ") " + arr1[i].toString());  
      }

      System.out.println("--> FINE test rinnovaAlbo()");
      
      System.out.println("--> INIZIO test chiudiAlbo()");
      
      Boolean flag2 = test.chiudiAlbo(arr[2].getIdAlbo());
      assertEquals("chiusura albo fallita",flag2,new Boolean("true"));

      AlboBean[] arr2 = test.cercaAlbo(ID_ENTE);
             
      for(int i=0; i<arr1.length; i++)
      {
        System.out.println("---> (" + i + ") " + arr2[i].toString());  
      }
      
      System.out.println("--> FINE test chiudiAlbo()");
      

    }
    
    System.out.println("-> Fine test testAggiornaCategoria()");   
  }
  
  /**
   * TEST GESTIONE CATEGORIE. Il test viene svolto nel seguente modo:
   *  -> Vengono create 10 categorie (metodo inserisciCategoria)
   *  -> Vengono cercate le categorie create (metodo cercaCategorie)
   *  -> Vengono aggiornate le categorie (metodo aggiornaCategoria)
   * @throws Exception
   */
  public void testGestioneCategorie() throws Exception
  {
    System.out.println("-> Inizio test testInserisciCategoria() - ID_ENTE " + ID_ENTE + " - ");
         
    CategoriaBean item = CategoriaBean.test();
    
    item.setIdAlbo(ARR_ID_ALBO[1]);
    
    for(int i=0; i<10; i++)
    {
      item.setDesCategoria("TEST CATEGORIA " + i);
      BigDecimal idCategoria = test.inserisciCategoria(item);
      assertNotNull("id categoria is NULL",idCategoria);
    
      System.out.println("(" + i + ") Id Categoria = " + idCategoria);
      System.out.println("-> Fine test testInserisciCategoria()");
    } 
    
    System.out.println("-> Fine test testInserisciCategoria()");
    
    System.out.println("-> Inizio test testCercaCategorie()");
    CategoriaBean[] arr = test.cercaCategorie(ID_ENTE);
    
    assertNotNull("-> Array categorie is NULL",arr);
    
    if(arr != null)
    {
      ARR_ID_CATEGORIE = new BigDecimal[arr.length];
      ARR_CATEGORIE = arr;
      
      for(int i=0; i<arr.length; i++)
      {
        ARR_ID_CATEGORIE[i]= arr[i].getIdCategoria();
        System.out.println("(" + i +") - " + arr[i].getDesCategoria() + "(" + arr[i].getIdEnte() + ")");
      }
      
      System.out.println("-> Fine test testCercaCategorie()");
      
      System.out.println("-> Inizio test testAggiornaCategoria()");
      
      for(int j=0; j<arr.length; j++)
      {
        arr[j].setDesCategoria("MODIFICA - " + arr[j].getDesCategoria());
        Boolean flag = test.aggiornaCategoria(arr[j]);
        
        assertEquals("Aggiornamento categoria :" + arr[j].getIdCategoria(),flag,new Boolean("true"));
        
        System.out.println("(" + j + ") Id Categoria = " + arr[j].getIdCategoria());      
      }
    }

    System.out.println("-> Fine test testAggiornaCategoria()");
  }
  
  /**
   *  Factory per l'oggetto da testare
   */
  public abstract EesAlboFornitoriInt objectFactory();
}
