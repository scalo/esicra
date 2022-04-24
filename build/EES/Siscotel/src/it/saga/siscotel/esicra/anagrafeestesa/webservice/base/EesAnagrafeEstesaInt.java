package it.saga.siscotel.esicra.anagrafeestesa.webservice.base;

import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.SoggettoProvenienzaBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoProvenienzaBean;
import it.saga.siscotel.srvfrontoffice.beans.base.SoggettoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.SoggettoGiuridicoBean;
import java.math.BigDecimal;

public interface EesAnagrafeEstesaInt  {

/**
   * Recupero informazioni  Anagrafica Soggetto 
   * @param pCodiceFiscale Codice Fiscale
   * @return SoggettoBean
   */
 public SoggettoBean cercaSoggettoFisicoIndiceCF (String pCodiceFiscale) throws Exception;

/**
   * Recupero informazioni Anagrafica Soggetto.
   * 
   * @param pCognome Cognome
   * @param pNome Nome
   * @param pIdEnte Id Ente
   * @param pDesArea DesArea
   * @param pNumCiv Numero Civico
   * @param pLetCiv Letera Civica
   * 
   * @return SoggettoBean
   */
    public  SoggettoBean[] cercaSoggettoFisicoIndiceId (
                    String     pCognome,
                    String     pNome,
                    BigDecimal pIdEnte ,
                    String     pDesArea,
                    Integer    pNumCiv,
                    String     pLetCiv) throws Exception;

/**
   * Recupero informazioni Anagrafica Soggetto.
   * 
   * @param pNome Nome
   * @param pIdEnte Id Ente
   * @param pDesArea DesArea
   * @param pNumCiv Numero Civico
   * @param pLetCiv Letera Civica
   * 
   * @return SoggettoBean
   */

    public  SoggettoBean[] cercaSoggettoFisicoIndiceNome (
                     String     pNome,
                     BigDecimal pIdEnte,
                     String     pDesArea,
                     Integer    pNumCiv,
                     String     pLetCiv) throws Exception;

                     
 /**
   * Recupero informazioni Anagrafica Soggetto.
   * 
   * @param pNome Nome
   * @param pIdEnte Id Ente
   * @param pDesArea DesArea
   * @param pNumCiv Numero Civico
   * @param pLetCiv Letera Civica
   * 
   * @return SoggettoBean
   */
   public  SoggettoBean[] cercaSoggettoFisicoIndiceInd (
                        BigDecimal pIdEnte,
                        String     pDesArea,
                        Integer    pNumCiv,
                        String     pLetCiv) throws Exception;

                              

/**
   * Recupero informazioni  Anagrafica Soggetto 
   * @param pPartitaIva  Partita Iva
   * @return SoggettoGiuridicoBean
   */
  public SoggettoGiuridicoBean cercaSoggettoGiuridicoIndicePI(String pPartitaIva ) throws Exception;


/**
   * Recupero informazioni Anagrafica Soggetto.
   * 
   * @param pDenominazione Denominazione
   * @param pIdEnte IdEnte
   * @param pDesAreaCircolazione Descrizione Area Circolazione
   * @param pNumCiv Numero Civico
   * @param LetCiv Lettera Civica
   * @return SoggettoGiuridicoBean[]
   */
  public SoggettoGiuridicoBean[] cercaSoggettoGiuridicoIndiceId(
                                    String     denominazione,
                                    BigDecimal idEnte,
                                    String     desArea,
                                    Integer    numCiv,
                                    String     letCiv) throws Exception;


/**
   * Recupero informazioni Anagrafica Soggetto.
   * 
   * @param pIdEnte IdEnte
   * @param pDesAreaCircolazione Descrizione Area Circolazione
   * @param pNumCiv Numero Civico
   * @param LetCiv Lettera Civica
   * @return SoggettoGiuridicoBean[]
   */
   public SoggettoGiuridicoBean[] cercaSoggettoGiuridicoIndiceEnte(
                                    BigDecimal pIdEnte,
                                    String     pDesArea,
                                    Integer    pNumCiv,
                                    String     pLetCiv) throws Exception;
  

   /**
   * recupero dati dell'oggetto
   * @param pCodiceEcografico Codice Ecografico
   * @return OggettoBean
   */
    public OggettoBean cercaOggettoIndiceCE(String codiceEcografico) throws Exception;



 /**
   * recupero dati dell'oggetto
   * 
   * @param pIdEnte IdEnte
   * @param pSezione Sezione
   * @param pFoglio Foglio
   * @param pMappale Mappale
   * @param pSub Sub
   * @param pAnno Anno
   * @param pProtocollo Protocollo
   * @param String tipo Catasto
   */
    public OggettoBean[] cercaOggettoIndiceIC(BigDecimal pIdEnte,
                                              String     pSezione,
                                              String     pFoglio,
                                              String     pMappale,
                                              String     pSub,
                                              Integer    pAnno,
                                              String     pProtocollo,
                                              String     tipoCatasto) throws Exception;

    /**
   * recupero dati dell'oggetto
   * @param pIdEnte IdEnte
   * @param pDesArea Descrizione Area
   * @param pNumCiv NumCiv
   * @param pLetCiv LetCiv
   * @return OggettoBean[]
   */
   public OggettoBean[] cercaOggettoIndiceInd(BigDecimal idEnte,
                                                 String     desArea,
                                                 Integer    numCiv,
                                                 String     letCiv ) throws Exception;                                           


  /**
   * Recupero enti in cui il soggetto fisico o giuridico è presente e 
   *  dei settori in cui è coinvolto
   * @param pCodiceFiscale Codice Fiscale o partita iva
   * @param pCodProvenienza Codice Provenienza
   * @return SoggettoProvenienzaBean
   */
 public SoggettoProvenienzaBean cercaSoggettoProvenienza (String pCodiceFiscale, String pCodProvenienza) throws Exception;


  /**
   * Recupero ente in cui il l'oggetto è presente e dei settori in cui è coinvolto
   * CercaOggettoProvenienza: La ricerca avviene sull'indice sovracomunali 
   *                           delle anagrafi.
   * @param pCodiceEcografico Codice Ecografico
   * @param pCodProvenienza CodiceProvenienza
   * @return OggettoProvenienzaBean
   */
 public OggettoProvenienzaBean cercaOggettoProvenienza (String codiceEcografico, String codProvenienza) throws Exception;




}