package it.saga.siscotel.esicra.anagrafeestesa.webservice.base;

import it.saga.egov.esicra.EsicraConf;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import org.apache.log4j.*;
import java.math.BigDecimal;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.*;
import it.saga.siscotel.srvfrontoffice.beans.base.*;
import it.saga.siscotel.srvfrontoffice.beans.commercio.*;


/**
 *   Numero massimo di elementi restituiti 500
 * 
 */
public class EesAnagrafeEstesaWs implements EesAnagrafeEstesaInt {

    private final static int MAX_RESULT=500;
    protected static Logger logger;
    private static final int COD_RAPPRESENTANTE = 1;
    private Session session ;
    
    public EesAnagrafeEstesaWs() {
        
        EsicraConf.configura();
        // prepara logger
        if (logger==null){
          logger= EsicraConf.configuraLogger(this.getClass());
          if (logger==null){
            logger.warn("Logger non configurato correttamente");
          }
        } 
        
        session = HibernateUtil.currentSession();
    }

 /**
   * Il servizio implementa la ricerca per Codice Fiscale di un 
   * determinato soggetto fisico nell'indice sovracomunale delle 
   * anagrafi e restituisce i suoi dati principali. 
   * Il parametro codice fiscale è obbligatorio.
   * La view object su cui si basa la ricerca è:
   * -VIndiceSogFisIndView.
   * 
   * @param codiceFiscale codice Fiscale
   * @return SoggettoBean
 */
 public SoggettoBean cercaSoggettoFisicoIndiceCF (String codiceFiscale) {

        logger.debug("**Inizio metodo cercaSoggettoFisicoIndiceCF");
        logger.debug("**parametri in input:");
        logger.debug("**  codiceFiscale:"+codiceFiscale);
    
        SoggettoBean soggBean = null;
        
        if (codiceFiscale == null) {
            logger.warn("codiceFiscale non valorizzato! Ricerca non effettuata.");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        if (codiceFiscale != null) {
            sb.append(" CODICE_FISCALE = '"+codiceFiscale+"'");
        } 
        
        String q = "from VIndiceSogFisInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogFisInd soggF = null;
        if(ite.hasNext()){
          soggF = (VIndiceSogFisInd) ite.next();
          soggBean = setSoggettoFisico(soggF);
        }
        
        logger.debug("**Fine metodo cercaSoggettoFisicoIndiceCF: ");
        return  soggBean;                                                               
    }

 /**
   * Il servizio implementa la 	ricerca per Identificativi dei soggetti fisici 
   * nell'indice sovracomunale delle anagrafi e restituisce i loro dati principali. 
   * E' necessario specificare il campo cognome (Cognome). 
   * La ricerca avviene su tutti i soggetti fisici appartenenti all'aggregazione 
   * sovracomunale aventi il dato cognome.
   * Se non è presente nessun filtro non viene eseguita nessuna ricerca.
   * La view object su cui si basa la ricerca è:
   * -VIndiceSogFisIndView.
   * 
   * 
   * @param pCognome Cognome
   * @param pNome Nome
   * @param pIdEnte Id Ente
   * @param pDesArea DesArea
   * @param pNumCiv Numero Civico
   * @param pLetCiv Lettera Civica
   * @return SoggettoBean[]
   */
    public  SoggettoBean[] cercaSoggettoFisicoIndiceId (
                    String     cognome ,
                    String     nome,
                    BigDecimal idEnte ,
                    String     desArea,
                    Integer    numCiv,
                    String     letCiv) {

        logger.debug("**Inizio metodo cercaSoggettoFisicoIndiceId");
        logger.debug("**parametri in input:");
        logger.debug("**  cognome:"+cognome);
        logger.debug("**  nome:"+nome);
        logger.debug("**  codice Ente:"+idEnte);
        logger.debug("**  descrizione Area:"+desArea);
        logger.debug("**  numero civico:"+numCiv);
        logger.debug("**  lettera civica:"+letCiv);
    
        Vector list = new Vector();
        
        if (cognome == null) {
            logger.warn("cognome non valorizzato! Ricerca non effettuata.");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        sb.append(" UPPER(COGNOME) = UPPER('"+cognome+"')");
        
        if (idEnte != null) {
            sb.append(" AND ID_ENTE = "+idEnte);
        } 
            
        if (nome != null){
            sb.append(" AND UPPER(NOME) = UPPER('"+nome+"')");
        } 
        if(desArea!=null ) {
            sb.append(" AND UPPER(AREA_INDIRIZZO) LIKE UPPER('%"+desArea+"%')");
        } 
    
        if(numCiv!=null && desArea!=null) {
            sb.append(" AND NUMERO_CIVICO_INDIRIZZO = '"+numCiv+"'");
        } 
        if(letCiv!=null && desArea!=null) {
            sb.append(" AND UPPER(LETTERA_CIVICO_INDIRIZZO) = UPPER('"+letCiv+"')");
        } 
        
        String q = "from VIndiceSogFisInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogFisInd soggF = null;
        while(ite.hasNext()){
          soggF = (VIndiceSogFisInd) ite.next();
          SoggettoBean  soggBean = setSoggettoFisico(soggF);
          list.add(soggBean);
        }
        
        int len = list.size();
        logger.debug("**numero soggetti:" +len);
        SoggettoBean[] array = new SoggettoBean[len];
        array = (SoggettoBean[]) list.toArray(array);
        
        logger.debug("**Fine metodo cercaSoggettoFisicoIndiceId");
    
        return    array;                           
                        
    }


 /** 
   * Il servizio implementa la ricerca per Nome dei soggetti fisici 
   * nell'indice sovracomunale delle anagrafi e restituisce i loro dati principali.
   * E' necessario specificare il campo nome e l'ente di appartenenza. 
   * La ricerca avviene così su tutti i soggetti fisici appartenenti all'ente 
   * specificato aventi il nome indicato.
   * La view object su cui si basa la ricerca è:
   * - VIndiceSogFisIndView
   * 
   * @param pNome Nome
   * @param pIdEnte Id Ente
   * @param pDesArea DesArea
   * @param pNumCiv Numero Civico
   * @param pLetCiv Letera Civica
   * 
   * @return SoggettoBean
   */

    public  SoggettoBean[] cercaSoggettoFisicoIndiceNome (String nome,
                                                        BigDecimal idEnte,
                                                        String     desArea,
                                                        Integer    numCiv,
                                                        String     letCiv) {
        
        logger.debug("**Inizio metodo cercaSoggettoFisicoIndiceNome");
        logger.debug("**parametri in iput:");
        logger.debug("**nome:"+nome);
        logger.debug("**codice Ente:"+idEnte);
        logger.debug("**descrizione Area:"+desArea);
        logger.debug("**numero civico:"+numCiv);
        logger.debug("**lettera civica:"+letCiv);
    
        Vector list = new Vector();
        
        if (nome == null && idEnte==null) {
            logger.warn("nome e codice ente non valorizzati! Ricerca non effettuata.");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

       
        sb.append(" UPPER(NOME) = UPPER('"+nome+"')");
        sb.append(" AND ID_ENTE = "+idEnte);

            
        if(desArea!=null ) {
            sb.append(" AND UPPER(AREA_INDIRIZZO) LIKE UPPER('%"+desArea+"%')");
        } 
    
        if(numCiv!=null && desArea!=null) {
            sb.append(" AND NUMERO_CIVICO_INDIRIZZO = '"+numCiv+"'");
        } 
        if(letCiv!=null && desArea!=null) {
            sb.append(" AND UPPER(LETTERA_CIVICO_INDIRIZZO) = UPPER('"+letCiv+"')");
        } 
        
        
        String q = "from VIndiceSogFisInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogFisInd soggF = null;
        while(ite.hasNext()){
          soggF = (VIndiceSogFisInd) ite.next();
          SoggettoBean  soggBean = setSoggettoFisico(soggF);
          list.add(soggBean);
        }
        
        int len = list.size();
        logger.debug("**numero soggetti:" +len);
        SoggettoBean[] array = new SoggettoBean[len];
        array = (SoggettoBean[]) list.toArray(array);
        
        logger.debug("**fine metodo cercaSoggettoFisicoIndiceNome");
        
        return    array;                                          
                         
    }

    
  /**
   * Il servizio implementa la ricerca per Indirizzo dei soggetti fisici 
   * nell'ente specificato e restituisce un array contente i dati principali
   * dei soggetti facenti parte l'ente.
   * E' necessario specificare il campo ente e l'area di apparteneza.
   * La view object su cui si basa la ricerca è:
   * - VIndiceSogFisIndView
   * 
   * @param pIdEnte Id Ente
   * @param pDesArea DesArea
   * @param pNumCiv Numero Civico
   * @param pLetCiv Letera Civica
   * 
   * @return SoggettoBean
   */

   public  SoggettoBean[] cercaSoggettoFisicoIndiceInd (
                     BigDecimal idEnte,
                     String     desArea,
                     Integer    numCiv,
                     String     letCiv) {

        logger.debug("**Inizio metodo cercaSoggettoFisicoIndiceIndiceInd");
        logger.debug("**parametri in iput:");
        logger.debug("**codice Ente:"+idEnte);
        logger.debug("**descrizione Area:"+desArea);
        logger.debug("**numero civico:"+numCiv);
        logger.debug("**lettera civica:"+letCiv);
    
        Vector list = new Vector();
        
        if (desArea == null && idEnte==null) {
            logger.warn("desArea e codice ente non valorizzati! Ricerca non effettuata.");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        sb.append(" ID_ENTE = "+idEnte);
        sb.append(" AND UPPER(AREA_INDIRIZZO) LIKE UPPER('%"+desArea+"%')");
         
    
        if(numCiv!=null) {
            sb.append(" AND NUMERO_CIVICO_INDIRIZZO = '"+numCiv+"'");
        } 
        if(letCiv!=null) {
            sb.append(" AND UPPER(LETTERA_CIVICO_INDIRIZZO) = UPPER('"+letCiv+"')");
        } 
        
        String q = "from VIndiceSogFisInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogFisInd soggF = null;
        while(ite.hasNext()){
          soggF = (VIndiceSogFisInd) ite.next();
          SoggettoBean  soggBean = setSoggettoFisico(soggF);
          list.add(soggBean);
        }
        
        int len = list.size();
        logger.debug("**numero soggetti:" +len);
        SoggettoBean[] array = new SoggettoBean[len];
        array = (SoggettoBean[]) list.toArray(array);
        
        logger.debug("**fine metodo cercaSoggettoFisicoIndiceNome");
    
        return    array; 
                
    }
    
  /** 
   * Recupera le informazioni dell'anagrafica del soggetto giuridico.
   * Il servizio implementa la ricerca per Partiva IVA di un determinato soggetto giuridico 
   * nell'indice sovracomunale delle anagrafi e restituisce i suoi dati principali.
   * La ricerca produce un solo soggetto giuridico corrispondente 
   * alla pratita IVA, obbligatoria per effettuare la ricerca. 
   * 
   *  I ViewObject sui quali si basa la ricerca è:
   *   VIndiceSogGiurIndView,
   *   AnaRappresentanteView.
   * 
   * 
   * @param partitaIva partita IVA   
   * @return SoggettoGiuridicoBean
   */
    public SoggettoGiuridicoBean cercaSoggettoGiuridicoIndicePI(String partitaIva ) {
        
        SoggettoGiuridicoBean soggGiur = null;
        
        //(NB:non vengono settati i dati relativi all'iscrizione CCIAA.)
        logger.debug("**Inizio metodo cercaSoggettoGiuridicoIndicePI");
        logger.debug("**parametri in iput:");
        logger.debug("**partitaIva:"+partitaIva);
        
        if (partitaIva == null) {
            logger.warn("Non valorizzata la partita iva! Ricerca non effettuata!");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        sb.append(" PARTITA_IVA = '"+partitaIva+"'");
        
        String q = "from VIndiceSogGiurInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogGiurInd soggG = null;
        if(ite.hasNext()){
          soggG = (VIndiceSogGiurInd) ite.next();
          soggGiur = setSoggettoGiur(soggG);
        }
        
        logger.debug("**fine metodo cercaSoggettoGiuridicoIndicePI");
        
        return    soggGiur;  
                              
       }

  /**
   * 
   * Il servizio implementa la ricerca per Identificativi dei
   * soggetti giuridici appartenenti all'indice sovracomunale delle 
   * anagrafi e restituisce i dati principali.
   * E' necessario specificare almeno il campo denominazione. La ricerca 
   * produce così tutti i soggetti giuridici appartenenti all'aggregazione 
   * sovracomunale aventi la denominazione specificata.
   * 
   *  I ViewObject sui quali si basa la ricerca è:
   *   VIndiceSogGiurIndView,
   *   AnaRappresentanteView. 
   * 
   * 
   * @param pDenominazione Denominazione
   * @param idEnte Id Ente
   * @param desArea Descrizione area
   * @param numCiv Numero Civico
   * @param letCiv Lettera Civica
   * @return SoggettoGiuridicoBean[]
   */
   public SoggettoGiuridicoBean[] cercaSoggettoGiuridicoIndiceId(
                                    String     denominazione,
                                    BigDecimal idEnte,
                                    String     desArea,
                                    Integer    numCiv,
                                    String     letCiv) {

        logger.debug("**Inizio metodo cercaSoggettoGiuridicoIndiceId");
        logger.debug("**parametri in iput:");
        logger.debug("**denominazione:"+denominazione);
        logger.debug("**codice Ente:"+idEnte);
        logger.debug("**descrizione Area:"+desArea);
        logger.debug("**numero civico:"+numCiv);
        logger.debug("**lettera civica:"+letCiv);
        
        Vector list = new Vector();
        /*
        ViewObject soggIndGVO = null;

        soggIndGVO = am.findViewObject("VIndiceSogGiurIndView");
        
        if (soggIndGVO == null) {
            logger.warn("Vista VIndiceSogGiurIndView non trovata");
            return null;
        }    
        */
         if (denominazione == null) {
            logger.warn("Non valorizzata la denominazione! Ricerca non effettuata!");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        sb.append("  UPPER(DENOMINAZIONE) LIKE  UPPER('"+denominazione+"%')");

        if (idEnte != null) {
            sb.append(" AND ID_ENTE = "+idEnte);
        } 
        if(desArea!=null) {
            sb.append(" AND UPPER(AREA_INDIRIZZO) LIKE UPPER('%"+desArea+"%')");
        } 
        if(numCiv!=null && desArea!=null) {
            sb.append(" AND NUMERO_CIVICO_INDIRIZZO = '"+numCiv+"'");
        } 
        if(letCiv!=null && desArea!=null) {
            sb.append(" AND UPPER(LETTERA_CIVICO_INDIRIZZO) = UPPER('"+letCiv+"')");
        } 

        SoggettoGiuridicoBean soggGiur = null;
        
        String q = "from VIndiceSogGiurInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogGiurInd soggG = null;
        while(ite.hasNext()){
          soggG = (VIndiceSogGiurInd) ite.next();
          soggGiur = setSoggettoGiur(soggG);
          list.add(soggGiur);
        }
        
        int len = list.size();
        logger.debug("numero soggetti:" +len);
        SoggettoGiuridicoBean[] array = new SoggettoGiuridicoBean[len];
        array = (SoggettoGiuridicoBean[]) list.toArray(array);
        
        logger.debug("**fine metodo cercaSoggettoGiuridicoIndiceId");
    
        return    array;  
                                        
    }

 /** 
  * 
   * Il servizio implementa la ricerca per Ente dei soggetti giuridici 
   * appartenenti e restituisce i dati dei soggetti recuperati.
   * E' necessario specificare il campo ente. La ricerca produce come 
   * risultato tutti i soggetti giuridici appartenenti all'ente specificato.
   * 
   * I ViewObject sui quali si basa la ricerca è:
   *   VIndiceSogGiurIndView,
   *   AnaRappresentanteView. 
   * 
   * @param pIdEnte IdEnte
   * @param pDesAreaCircolazione Descrizione Area Circolazione
   * @param pNumCiv Numero Civico
   * @param LetCiv Lettera Civica
   * @return SoggettoGiuridicoBean[]
   */
   public SoggettoGiuridicoBean[] cercaSoggettoGiuridicoIndiceEnte(
                                    BigDecimal idEnte,
                                    String     desArea,
                                    Integer    numCiv,
                                    String     letCiv) {


        logger.debug("**Inizio metodo cercaSoggettoGiuridicoIndiceEnte");
        logger.debug("**parametri in iput:");
        logger.debug("**codice Ente:"+idEnte);
        logger.debug("**descrizione Area:"+desArea);
        logger.debug("**numero civico:"+numCiv);
        logger.debug("**lettera civica:"+letCiv);
        
        Vector list = new Vector();
        
        if (idEnte == null) {
            logger.warn("Non valorizzato il codice dell'ente! Ricerca non effettuata!");
            return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        sb.append(" ID_ENTE = "+idEnte); 

        if(desArea!=null) {
            sb.append(" AND UPPER(AREA_INDIRIZZO) LIKE UPPER('%"+desArea+"%')");
        } 
        if(numCiv!=null && desArea!=null) {
            sb.append(" AND NUMERO_CIVICO_INDIRIZZO = '"+numCiv+"'");
        } 
        if(letCiv!=null && desArea!=null) {
            sb.append(" AND UPPER(LETTERA_CIVICO_INDIRIZZO) = UPPER('"+letCiv+"')");
        } 
        
        SoggettoGiuridicoBean soggGiur = null;
        
        String q = "from VIndiceSogGiurInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VIndiceSogGiurInd soggG = null;
        while(ite.hasNext()){
          soggG = (VIndiceSogGiurInd) ite.next();
          soggGiur = setSoggettoGiur(soggG);
          list.add(soggGiur);
        }
        
        int len = list.size();
        logger.debug("numero soggetti:" +len);
        SoggettoGiuridicoBean[] array = new SoggettoGiuridicoBean[len];
        array = (SoggettoGiuridicoBean[]) list.toArray(array);
        
        logger.debug("**fine metodo cercaSoggettoGiuridicoIndiceEnte");
    
        return    array;                                      
                                        
    }

  /**
   * Il servizio implementa la ricerca per Codice Ecografico di un 
   * determinato oggetto territoriale (immobile o terreno) nell'indice 
   * sovracomunale delle anagrafi e restituisce i suoi dati principali.
   * La ricerca produce un solo oggetto corrispondente al codice 
   * ecografico specificato.
   * 
   * Le viewObject utilizzate sono:
   *  -  ???? VIndiceOggettoIndView ???,
   *  - OtIdentificativoView,
   *  - VIndiceOggettoIdeView,
   *  - OtIndirizzoView.
   * 
   * @param codiceEcografico Codice Ecografico
   * @return OggettoBean
   */
    public OggettoBean cercaOggettoIndiceCE(String codiceEcografico) {
        OggettoBean oggB = null;
        
        logger.info("** Inizio metodo cercaOggettoIndiceCE.");
        Vector list          = new Vector();

        if (codiceEcografico== null) {
           logger.warn("Attenzione:codice ecografico non valorizzato! Ricerca non valorizzata");        
           return null;
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();

        sb.append(" COD_ECOGRAFICO = '"+codiceEcografico+"'");
        
        String q = "from OtIndice where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        Vector ideList     = new Vector();
        Vector indirList   = new Vector();
        OtIndice otIndiceRow = null;
        OtIdentificativo oggIdeRow = null;
        
        if(ite.hasNext()){
          otIndiceRow = (OtIndice) ite.next();
          
          oggB = new OggettoBean();
          logger.info("**codice dell'oggetto:"+otIndiceRow.getPkid());
          oggB.setCodiceEcografico(otIndiceRow.getCodEcografico());
          String codIstat = otIndiceRow.getCodIstatComune();
          oggB.setCodiceIstatComune(codIstat);
          
          // indirizzi
          Iterator indirizzi =  otIndiceRow.getOtIndirizzos().iterator();
          IndirizzoBean indOgg=null;
          while(indirizzi.hasNext()){
            OtIndirizzo indirRow = (OtIndirizzo) indirizzi.next();
            indOgg = new IndirizzoBean();
            //indOgg.setCap(indirRow.get);
            ComuneBean  comune = new ComuneBean();
            // conversione obbligatoria per riempire anche il bean Comune almeno con il cod istat
            comune.setCodIstatComune( new Integer(codIstat));
            indOgg.setComune(comune);
            indOgg.setDesArea(indirRow.getDesArea());
            indOgg.setInterno(indirRow.getInterno());
            indOgg.setLetCiv(indirRow.getLetCiv());
            //indOgg.setLocalita();
            indOgg.setNumCiv(indirRow.getNumCiv());
            indOgg.setScala(indirRow.getScala());
            //indOgg.setSpecieArea(indirRow.getLetCiv());
            indOgg.setDataInizio(indirRow.getDtIni());
            indOgg.setDataFine(indirRow.getDtFin());
  
            indirList.add(indOgg);
            
          }
          
          int nInd =indirList.size();
          logger.info("numero indirizzi:"+nInd);
          IndirizzoBean[] indirArray = new IndirizzoBean[nInd];
          indirArray = (IndirizzoBean[]) indirList.toArray(indirArray);
          logger.info("aggiungo la lista di indirizzi all'oggetto");
          oggB.setListaIndirizzo(indirArray);
          
          // identificativi
          Iterator identificativi =  otIndiceRow.getOtIdentificativos().iterator();
          IdentificativiCatastaliBean ideB = null;
          while(identificativi.hasNext()){
            oggIdeRow = (OtIdentificativo) identificativi.next();
            ideB = new IdentificativiCatastaliBean();
            if(oggIdeRow.getProtAnno()!=null) {
                ideB.setAnnoProtocollo(oggIdeRow.getProtAnno().toString());
            }
            ideB.setDesTipo(oggIdeRow.getDesIdentificativo());
            ideB.setFoglio(oggIdeRow.getFoglio());
            ideB.setIdComune(oggIdeRow.getIdComune());
            ideB.setMappale(oggIdeRow.getMappale());
            ideB.setNumeroProtocollo(oggIdeRow.getProtNum());
            ideB.setSezione(oggIdeRow.getSezione());
            ideB.setSubalterno(oggIdeRow.getSub());
            ideB.setTipo(oggIdeRow.getTipoCatasto());

            ideList.add(ideB);
          }
          
          int nI =ideList.size();
          logger.info("numero identificativi:"+nI);
          IdentificativiCatastaliBean[] idArray = new IdentificativiCatastaliBean[nI];
          idArray = (IdentificativiCatastaliBean[]) ideList.toArray(idArray);
          logger.info("aggiungo la lista di indentificativi all'ultimo oggetto.");
          oggB.setListaIdentificativiCatastali(idArray);
          list.add(oggB);
        }
        
        
        return    oggB;               

    }


 /**  
   * Il servizio implementa la ricerca per Identificativi Catastali degli 
   * oggetti territoriali (immobili o terreni) nell'indice 
   * sovracomunale delle anagrafi e restituisce i dati principali.
   * E' necessario specificare il campo ente ed almeno uno degli identificativi 
   * catastali costituiti da: sezione, foglio, mappale, subalterno, anno protocollo, 
   * numero protocollo. 
   * La ricerca produrrà come risultato tutti gli oggetti territoriali 
   * appartenenti all'ente specificato con gli identificativi catastali indicati.
   * 
   * Le viewObject utilizzate sono:
   *  - OtIdentificativoView, 
   *  - VIndiceOggettoIdeView, 
   *  - OtIndirizzoView. 
   * 
   * @param IdEnte IdEnte
   * @param Sezione Sezione
   * @param Foglio Foglio
   * @param Mappale Mappale
   * @param Sub Sub
   * @param Anno Anno
   * @param Protocollo Protocollo
   * @param String tipo Catasto
   * @return OggettoBean[]
   */
    public OggettoBean[] cercaOggettoIndiceIC(BigDecimal idEnte,
                                              String     sezione,
                                              String     foglio,
                                              String     mappale,
                                              String     sub,
                                              Integer    anno,
                                              String     protocollo,
                                              String     tipoCatasto) {


        logger.debug("**Inizio metodo cercaOggettoIndiceIC");
        logger.debug("**parametri in iput:");
        logger.debug("**codice Ente:"+idEnte);
        logger.debug("**sezione:"+sezione);
        logger.debug("**foglio:"+foglio);
        logger.debug("**mappale:"+mappale);
        logger.debug("**sub:"+sub);
        logger.debug("**anno:"+anno);
        logger.debug("**protocollo:"+protocollo);
        logger.debug("**tipoCatasto:"+tipoCatasto);
    
        Vector list = new Vector();
        
        if(idEnte==null) {
            logger.warn("Attenzione: codice dell'ente nullo!ricerca Non effettuata!");
            return null;
        }
        
        if(sezione == null && foglio == null &&  mappale == null  &&
           sub == null     && anno == null   &&  protocollo == null && 
           tipoCatasto == null) {

           logger.warn("Attenzione: non valorizzati dati obbligatori! Ricerca non effettuata!");
           return null; 
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();   
        sb.append(" ID_ENTE = "+idEnte+" ");
        if(sezione != null) {
            sb.append(" AND UPPER(SEZIONE) = UPPER('"+sezione+"')");
        }
        if(foglio != null) {
            sb.append(" AND UPPER(FOGLIO) = UPPER('"+foglio+"')");
        }
        if(mappale != null) {
            sb.append(" AND UPPER(MAPPALE) = UPPER('"+mappale+"')");
        }
        if(sub != null) {
            sb.append(" AND UPPER(SUB) = UPPER('"+sub+"')");
        }
        if(anno != null) {
            sb.append(" AND PROT_ANNO = "+anno+" ");
        }
        if(protocollo != null) {
            sb.append(" AND UPPER(PROT_NUM) = UPPER('"+protocollo+"')");
        }
        if(tipoCatasto != null) {
            sb.append(" AND UPPER(TIPO_CATASTO) = UPPER('"+tipoCatasto+"')");
        }
        
        // TODO controllare  setOrderByClause("AOT_PKID DESC")
        //sb.append(" ORDER BY AOT_PKID DESC");
        
        String q = "from OtIdentificativo where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        Vector ideList     = new Vector();
        Vector indirList   = new Vector();
        OtIdentificativo oggIdeRow = null;
        
        String codiceEcografico="";
        if(ite.hasNext()){
          oggIdeRow = (OtIdentificativo) ite.next();
          codiceEcografico = oggIdeRow.getOtIndice().getCodEcografico();
        }
        
        sb = new StringBuffer(" COD_ECOGRAFICO = '"+codiceEcografico+"'");
        q = "from OtIndice where "+sb.toString();
        query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        ite = query.iterate();
        
        OtIndice otIndiceRow =null;
        OggettoBean oggB = null;
        Iterator identificativi = null;
        Iterator indirizzi = null;
        
        while(ite.hasNext()){
          otIndiceRow = (OtIndice) ite.next();
          oggB = new OggettoBean();
          oggB.setCodiceEcografico(otIndiceRow.getCodEcografico());
          oggB.setCodiceIstatComune(otIndiceRow.getCodIstatComune());
          indirizzi = otIndiceRow.getOtIndirizzos().iterator();
          IndirizzoBean indOgg=null;
          OtIndirizzo indirRow=null;
          while(indirizzi.hasNext()){
            indirRow = (OtIndirizzo) indirizzi.next();
            indOgg = new IndirizzoBean();
            //indOgg.setCap(indirRow.get);
            //indOgg.setComune(indirRow.get);
            indOgg.setDesArea(indirRow.getDesArea());
            indOgg.setInterno(indirRow.getInterno());
            indOgg.setLetCiv(indirRow.getLetCiv());
            //indOgg.setLocalita();
            indOgg.setNumCiv(indirRow.getNumCiv());
            indOgg.setScala(indirRow.getScala());
            //indOgg.setSpecieArea(indirRow.getLetCiv());
            indOgg.setDataInizio(indirRow.getDtIni());
            indOgg.setDataFine(indirRow.getDtFin());

            indirList.add(indOgg);     
          }
          
          int nInd =indirList.size();
          logger.info("numero indirizzi:"+nInd);
          IndirizzoBean[] indirArray = new IndirizzoBean[nInd];
          indirArray = (IndirizzoBean[]) indirList.toArray(indirArray);
          logger.info("aggiunta la lista di indirizzi all'oggetto");
          oggB.setListaIndirizzo(indirArray);
          
          identificativi = otIndiceRow.getOtIdentificativos().iterator();
          IdentificativiCatastaliBean ideB = null;
          while(identificativi.hasNext()){
            oggIdeRow = (OtIdentificativo) identificativi.next();
            ideB = new IdentificativiCatastaliBean();
            if(oggIdeRow.getProtAnno()!=null) {
                ideB.setAnnoProtocollo(oggIdeRow.getProtAnno().toString());
            }
            ideB.setDesTipo(oggIdeRow.getDesIdentificativo());
            ideB.setFoglio(oggIdeRow.getFoglio());
            ideB.setIdComune(oggIdeRow.getIdComune());
            ideB.setMappale(oggIdeRow.getMappale());
            ideB.setNumeroProtocollo(oggIdeRow.getProtNum());
            ideB.setSezione(oggIdeRow.getSezione());
            ideB.setSubalterno(oggIdeRow.getSub());
            ideB.setTipo(oggIdeRow.getTipoCatasto());

            ideList.add(ideB);
          }
          
          int nId =ideList.size();
          logger.info("numero identificativi:"+nId);
          IdentificativiCatastaliBean[] idArray = new IdentificativiCatastaliBean[nId];
          idArray = (IdentificativiCatastaliBean[]) ideList.toArray(idArray);
          logger.info("aggiunta la lista di indentificativi all'ultimo oggetto.");
          oggB.setListaIdentificativiCatastali(idArray);
          
          list.add(oggB);
        }
        
        int len = list.size();
        OggettoBean[] array = new OggettoBean[len];
        array = (OggettoBean[]) list.toArray(array);
        logger.debug("**fine metodo cercaOggettoIndiceIC");    
        return    array;               
                                               
    }



  /** 
    * Il servizio implementa la ricerca per Indirizzo degli oggetti 
    * territoriali (immobili o terreni) nell'indice sovracomunale delle 
    * anagrafi.
    * E' necessario specificare i campi ente ed area. 
    * La ricerca prodce come risultato tutti gli oggetti territoriali 
    * appartenenti all'ente specificato ed ubicati nell'area indicata.
    * 
    *  Le viewObject utilizzate sono:
    *  - OtIdentificativoView, 
    *  - VIndiceOggettoIdeView, 
    *  - OtIndirizzoView.
    * 
    * 
    * @param pIdEnte IdEnte
    * @param desArea desArea
    * @param numCiv numCiv
    * @param letCiv letCiv
    * @return OggettoBean[]
    */

    public OggettoBean[] cercaOggettoIndiceInd(BigDecimal idEnte,
                                                 String     desArea,
                                                 Integer    numCiv,
                                                 String     letCiv ) {
                                                 
        logger.debug("**Inizio metodo cercaOggettoIndiceInd");
        logger.debug("**parametri in iput:");
        logger.debug("**codice Ente:"+idEnte);
        logger.debug("**desArea:"+desArea);
        logger.debug("**numCiv:"+numCiv);
        logger.debug("**letCiv:"+letCiv);
    
        Vector list = new Vector();
        
        if(idEnte==null) {
            logger.warn("Attenzione: codice dell'ente nullo! Ricerca Non effettuata!");
            return null;
        }
        
        if(desArea == null ) {
            logger.warn("Attenzione: non valorizzata la desArea! Ricerca non effettuata!");
            return null;              
        }

        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();   
        sb.append(" ID_ENTE = "+idEnte+" ");        
        sb.append(" AND UPPER(AREA_INDIRIZZO) LIKE UPPER('%"+desArea+"%')");

        if(numCiv != null) {
            sb.append(" AND NUMERO_CIVICO= "+numCiv+" ");
        }
        if(letCiv != null) {
            sb.append(" AND UPPER(LETTERA_CIVICO) = UPPER('"+letCiv+"')");
        }

        String q = "from VIndiceOggettoInd where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        Vector ideList     = new Vector();
        Vector indirList   = new Vector();
        VIndiceOggettoInd oggIdeRow = null;
        
        String codiceEcografico="";
        if(ite.hasNext()){
          oggIdeRow = (VIndiceOggettoInd) ite.next();
          codiceEcografico = oggIdeRow.getCodiceEcografico();
        }
        
        //System.out.println(" COD_ECOGRAFICO = '"+codiceEcografico+"'");
        
        sb = new StringBuffer(" COD_ECOGRAFICO = '"+codiceEcografico+"'");
        q = "from OtIndice where "+sb.toString();
        query = session.createQuery(q);
        ite = query.iterate();
        
        OtIndice otIndiceRow =null;
        OggettoBean oggB = null;
        Iterator identificativi = null;
        Iterator indirizzi = null;
        
        if(ite.hasNext()){
          otIndiceRow = (OtIndice) ite.next();
          oggB = new OggettoBean();
          oggB.setCodiceEcografico(otIndiceRow.getCodEcografico());
          oggB.setCodiceIstatComune(otIndiceRow.getCodIstatComune());
          indirizzi = otIndiceRow.getOtIndirizzos().iterator();
          IndirizzoBean indOgg=null;
          OtIndirizzo indirRow=null;
          while(indirizzi.hasNext()){
            indirRow = (OtIndirizzo) indirizzi.next();
            indOgg = new IndirizzoBean();
            //indOgg.setCap(indirRow.get);
            //indOgg.setComune(indirRow.get);
            indOgg.setDesArea(indirRow.getDesArea());
            indOgg.setInterno(indirRow.getInterno());
            indOgg.setLetCiv(indirRow.getLetCiv());
            //indOgg.setLocalita();
            indOgg.setNumCiv(indirRow.getNumCiv());
            indOgg.setScala(indirRow.getScala());
            //indOgg.setSpecieArea(indirRow.getLetCiv());
            indOgg.setDataInizio(indirRow.getDtIni());
            indOgg.setDataFine(indirRow.getDtFin());

            indirList.add(indOgg);     
          }
          
          int nInd =indirList.size();
          logger.info("numero indirizzi:"+nInd);
          IndirizzoBean[] indirArray = new IndirizzoBean[nInd];
          indirArray = (IndirizzoBean[]) indirList.toArray(indirArray);
          logger.info("aggiunta la lista di indirizzi all'oggetto");
          oggB.setListaIndirizzo(indirArray);
          
          identificativi = otIndiceRow.getOtIdentificativos().iterator();
          IdentificativiCatastaliBean ideB = null;
          OtIdentificativo otOgg = null;
          while(identificativi.hasNext()){
            otOgg = (OtIdentificativo) identificativi.next();
            ideB = new IdentificativiCatastaliBean();
            if(otOgg.getProtAnno()!=null) {
                ideB.setAnnoProtocollo(otOgg.getProtAnno().toString());
            }
            ideB.setDesTipo(otOgg.getDesIdentificativo());
            ideB.setFoglio(otOgg.getFoglio());
            ideB.setIdComune(otOgg.getIdComune());
            ideB.setMappale(otOgg.getMappale());
            ideB.setNumeroProtocollo(otOgg.getProtNum());
            ideB.setSezione(otOgg.getSezione());
            ideB.setSubalterno(otOgg.getSub());
            ideB.setTipo(otOgg.getTipoCatasto());

            ideList.add(ideB);
          }
          
          int nId =ideList.size();
          logger.info("numero identificativi:"+nId);
          IdentificativiCatastaliBean[] idArray = new IdentificativiCatastaliBean[nId];
          idArray = (IdentificativiCatastaliBean[]) ideList.toArray(idArray);
          logger.info("aggiunta la lista di indentificativi all'ultimo oggetto.");
          oggB.setListaIdentificativiCatastali(idArray);
          
          list.add(oggB);
        }
        
        int len = list.size();
        OggettoBean[] array = new OggettoBean[len];
        array = (OggettoBean[]) list.toArray(array);
        
        logger.debug("**fine metodo cercaOggettoIndice");
    
        return    array;      
    }

 
  /**  
    * Il metodo permette di trovare gli enti ed i settori in cui un soggetto 
    * fisico o giuridico è presente.
    * La viewObject sulla quale si basa la ricerca è:
    * - VSoggettoProvenienzaView.
    * 
    * @param pCodiceFiscale Codice Fiscale
    * @param pCodProvenienza Codice Provenienza
    * @return SoggettoProvenienzaBean
    */
    public SoggettoProvenienzaBean cercaSoggettoProvenienza (String codiceFiscale, 
                                                             String codProvenienza) {
        logger.debug("**Inizio metodo cercaSoggettoProvenienza");
        logger.debug("**parametri in iput:");
        logger.debug("**codiceFiscale:"+codiceFiscale);
        logger.debug("**codProvenienza:"+codProvenienza);
        SoggettoProvenienzaBean provSogg = null;
        Vector list = new Vector();
        
        if (codiceFiscale == null) {
            logger.warn("manca il codice fiscale/partita iva");
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("CODICE_SOGGETTO = '"+codiceFiscale+"'");
        if(codProvenienza!=null) {
           sb.append(" AND COD_PROVENIENZA = '"+codProvenienza+"'"); 
        }
        
        String q = "from VSoggettoProvenienza where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        
        provSogg = new SoggettoProvenienzaBean();
        ProvenienzaBean prov = null; 
        while(ite.hasNext()){
          VSoggettoProvenienza provSoggRow = (VSoggettoProvenienza) ite.next();
          prov = new ProvenienzaBean(); 
          provSogg.setCodFiscale(provSoggRow.getCodiceSoggetto());
          // ?? TODO ??
          provSogg.setFkidSoggetto(provSoggRow.getSoggPkid());
          provSogg.setIdEnte(new BigDecimal(provSoggRow.getIdEnte().toString()));

          prov.setCodProvenienza(provSoggRow.getCodProvenienza());
          prov.setDesProvenienza(provSoggRow.getDesProvenienza());
          
          list.add(prov);
        }
            
        int len = list.size();
        logger.debug("numero provenienze associate al soggetto:" + len);
        ProvenienzaBean[] provArray = new ProvenienzaBean[len];
        provArray = (ProvenienzaBean[]) list.toArray(provArray);
        provSogg.setListaProvenienza(provArray);

        logger.debug("**fine metodo cercaSoggettoProvenienza");
        return provSogg;
    }


  /**  
   * Recupero gli enti in cui l'oggetto territoriale 
   * è presente e i settori in cui è coinvolto.
   * La ricerca avviene sull'indice sovracomunali delle anagrafi.
   * La viewObject utilizzata è:
   *  - VOggettoProvenienzaView.
   *                           
   * @param pCodiceEcografico Codice Ecografico
   * @param pCodProvenienza CodiceProvenienza
   * @return OggettoProvenienzaBean
   */
 public OggettoProvenienzaBean cercaOggettoProvenienza (String codiceEcografico, String codProvenienza) {
        
        OggettoProvenienzaBean provOgg=null;
        logger.debug("**Inizio metodo cercaOggettoProvenienza");
        logger.debug("**parametri in iput:");
        logger.debug("**codiceEcografico:"+codiceEcografico);
        logger.debug("**codProvenienza:"+codProvenienza);
    
        Vector list = new Vector();
        
        if (codiceEcografico == null) {
            logger.warn("manca il codiceEcografico");
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("COD_ECOGRAFICO = '"+codiceEcografico+"'");
        if(codProvenienza!=null) {
           sb.append(" AND COD_PROVENIENZA = '"+codProvenienza+"'"); 
        }
        
        String q = "from VOggettoProvenienza where "+sb.toString();
        Query query = session.createQuery(q);
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        
        provOgg = new OggettoProvenienzaBean();
        
        ProvenienzaBean prov = null; 
        while(ite.hasNext()){
          VOggettoProvenienza oggProvRow = (VOggettoProvenienza) ite.next();
          prov = new ProvenienzaBean(); 
          provOgg.setCodiceEcografico(oggProvRow.getCodEcografico());
          // TODO controllare
          provOgg.setPkidOggetto(oggProvRow.getOggPkid());
          provOgg.setIdEnte(new BigDecimal(oggProvRow.getIdEnte().toString()));

          prov.setCodProvenienza(oggProvRow.getCodProvenienza());
          prov.setDesProvenienza(oggProvRow.getDesProvenienza());
          
          list.add(prov);
        }
        
        int len = list.size();
        logger.debug("numero provenienze " + len);
        ProvenienzaBean[] provArray = new ProvenienzaBean[len];
        provArray = (ProvenienzaBean[]) list.toArray(provArray);
        provOgg.setListaProvenienza(provArray);
        logger.debug("**fine metodo cercaOggettoProvenienza");
        
        return provOgg;
 }

  /**
   *  Chiude la sessione di Hibernate distruggendo l'oggetto
   *  TODO CONTROLLARE !!!!
   */
   protected void finalize() throws Throwable {
        try {
            HibernateUtil.currentSession().close();
        }catch(Exception e){
            logger.debug("Errore disconnect() "+e.getMessage());   
        }finally {
            super.finalize();
        }
    }



/** 
 * Metodo privato per recuperare i dati del rappresentante
 * 
 * @param codiceRappresentante fkid della tabella ana_rappresentante.
 * @return bean contenete i dati del soggetto.
 */
  private SoggettoBean setRappresentante(AnaSoggetto indSoggR,AnaIndirizzo recapito){
    
    SoggettoBean rappresentante = null;
    
    logger.info("inizio metodo cercaDatiRappresentante");
    
    if(indSoggR!=null) {
        //SoggettoViewRowImpl soggR = (SoggettoViewRowImpl)soggVO.next();
        String codFisc  = indSoggR.getCodiceFiscale();
        if(codFisc!=null) {
                //VIndiceSogFisIndViewRowImpl indSoggR = (VIndiceSogFisIndViewRowImpl)indSoggVO.next();
                
                rappresentante = new SoggettoBean();
                rappresentante.setCodiceFiscale(indSoggR.getCodiceFiscale());
                rappresentante.setCognome(indSoggR.getCognome());
                rappresentante.setDataNascita(indSoggR.getDtNascita());
                rappresentante.setIdEnte(new BigDecimal(indSoggR.getIdEnte().toString()));
                rappresentante.setNome(indSoggR.getNome());
                //soggF.setResidenza(soggFRow.getNome());
                if(indSoggR.getSesso()!=null) {
                    rappresentante.setSesso(codSesso(indSoggR.getSesso()));
                }
                ///
                ComuneBean nasComuneBean = new ComuneBean();
                //nasComuneBean.setCodIstatComune(codIstat(soggFRow.getFkidComuneNascita()));
                nasComuneBean.setDesComune(indSoggR.getDesComuneNascita());
                nasComuneBean.setDesProvincia(indSoggR.getDesProvinciaNascita());
        
                LocalitaBean nasLocalitaBean = new LocalitaBean();
                //nasLocalitaBean.setCodIstatStato(codIstat(indSoggR.getFkidStatoNascita()));
                nasLocalitaBean.setDesStato(indSoggR.getDesStatoNascita());
                nasLocalitaBean.setDesLocalita(indSoggR.getDesLocalitaNascita());
                nasLocalitaBean.setDesContea("");
        
                ComuneBean resComuneBean = new ComuneBean();
                //resComuneBean.setCodIstatComune(codIstat(indSoggR.getFkidComune()));
                resComuneBean.setDesComune(recapito.getDesComune());
                resComuneBean.setDesProvincia(recapito.getDesProvincia());

                LocalitaBean resLocalitaBean = new LocalitaBean();
                //resLocalitaBean.setCodIstatStato(codIstat(soggFRow.getFkidLocalita()));
                resLocalitaBean.setDesStato(recapito.getDesStato());
                resLocalitaBean.setDesLocalita(recapito.getDesLocalita());
                resLocalitaBean.setDesContea(recapito.getContea());
          
                IndirizzoBean residenzaBean = new IndirizzoBean();
                // residenzaBean.setSpecieArea(indSoggR.getDesArea()); ??
                residenzaBean.setDesArea(recapito.getDesArea());
                residenzaBean.setNumCiv(recapito.getNumCiv());
                residenzaBean.setLetCiv(recapito.getLetCiv());
                residenzaBean.setScala(recapito.getScala());
                residenzaBean.setInterno(recapito.getInterno());
                residenzaBean.setCap(recapito.getCap());
                // dtInizio e dtFine non utilizzati
                residenzaBean.setComune(resComuneBean);
                residenzaBean.setLocalita(resLocalitaBean);
                rappresentante.setResidenza(residenzaBean);
                ///

                rappresentante.setComuneNascita(nasComuneBean);
                rappresentante.setLocalitaNascita(nasLocalitaBean);
                
            //}   
        }
    }
    logger.debug("fine metodo cercaDatiRappresentante");
  
    return rappresentante;    
  }

  /**
   * metodo privato per la valorizzazione del soggetto bean.
   * @param VIndiceSogFisIndViewRowImpl 
   * @return SoggettoBean
  */ 
   private SoggettoBean setSoggettoFisico(VIndiceSogFisInd soggFRow) {

            SoggettoBean soggF = new SoggettoBean();
            soggF.setCodiceFiscale(soggFRow.getCodiceFiscale());
            soggF.setCognome(soggFRow.getCognome());
            soggF.setDataNascita(soggFRow.getDataNascita());
            soggF.setIdEnte(new BigDecimal(soggFRow.getIdEnte().toString()));
            soggF.setNome(soggFRow.getNome());
            //soggF.setResidenza(soggFRow.getNome());
            if(soggFRow.getSesso()!=null) {
                soggF.setSesso(codSesso(soggFRow.getSesso()));
            }

            ///
            ComuneBean nasComuneBean = new ComuneBean();
            //nasComuneBean.setCodIstatComune(codIstat(soggFRow.getFkidComuneNascita())); manca nella vista
            nasComuneBean.setDesComune(soggFRow.getComuneNascita());
            nasComuneBean.setDesProvincia(soggFRow.getProvinciaNascita());
        
            LocalitaBean nasLocalitaBean = new LocalitaBean();
            //nasLocalitaBean.setCodIstatStato(codIstat(soggFRow.getFkidStatoNascita()));
            nasLocalitaBean.setDesStato(soggFRow.getStatoNascita());
            nasLocalitaBean.setDesLocalita(soggFRow.getLocalitaNascita());
            nasLocalitaBean.setDesContea("");
        
            ComuneBean resComuneBean = new ComuneBean();
            //resComuneBean.setCodIstatComune(codIstat(soggFRow.getFkidComune()));
            resComuneBean.setDesComune(soggFRow.getDesComuneIndirizzo());
            resComuneBean.setDesProvincia(soggFRow.getDesProvinciaIndirizzo());

            LocalitaBean resLocalitaBean = new LocalitaBean();
            //resLocalitaBean.setCodIstatStato(codIstat(soggFRow.getFkidLocalita()));
            resLocalitaBean.setDesStato(soggFRow.getDesStatoIndirizzo());
            resLocalitaBean.setDesLocalita(soggFRow.getDesLocIndirizzo());
            resLocalitaBean.setDesContea(soggFRow.getConteaIndirizzo());
          
            IndirizzoBean residenzaBean = new IndirizzoBean();
            // residenzaBean.setSpecieArea(soggFRow.getDesArea()); ??
            residenzaBean.setDesArea(soggFRow.getAreaIndirizzo());
            residenzaBean.setNumCiv(soggFRow.getNumeroCivicoIndirizzo());
            residenzaBean.setLetCiv(soggFRow.getLetteraCivicoIndirizzo());
            residenzaBean.setScala(soggFRow.getScala());
            residenzaBean.setInterno(soggFRow.getInterno());
            residenzaBean.setCap(soggFRow.getCapIndirizzo());
            // dtInizio e dtFine non utilizzati
            residenzaBean.setComune(resComuneBean);
            residenzaBean.setLocalita(resLocalitaBean);
            soggF.setResidenza(residenzaBean);
            ///

            soggF.setComuneNascita(nasComuneBean);
            soggF.setLocalitaNascita(nasLocalitaBean); 

            return  soggF;   
   }

   /**  
    * Metodo privato per la valorizzazione del soggetto giuridico.
    *
    */

    private SoggettoGiuridicoBean setSoggettoGiur(VIndiceSogGiurInd soggGiurRow) {

        logger.info("inizio metodo setSoggGiur");
        
        SoggettoGiuridicoBean soggGiur = new SoggettoGiuridicoBean();
        IscrizioneCCIABean iscrizCCIA  = null;
        BigDecimal pkSogg = soggGiurRow.getPkid();
        soggGiur.setIdEnte(new BigDecimal(soggGiurRow.getIdEnte().toString()));
        soggGiur.setPartitaIva(soggGiurRow.getPartitaIva());
        String den = soggGiurRow.getDenominazione();
        if(den!=null && den.length()>0)
          soggGiur.setRagioneSociale(den);
        else
          soggGiur.setRagioneSociale((soggGiurRow.getNome()+" "+soggGiurRow.getCognome()).trim());
        //non viene settato il bean dell'iscrizioneCCIAA, manca nei dati del db
                
        //recupero il rappresentante
        
        StringBuffer sb = new StringBuffer(" FKID_SOGGETTO = " + pkSogg +
                              " AND FLG_RAPPRESENTANTE = " + COD_RAPPRESENTANTE);
        String q = "from AnaRappresentante where "+sb.toString();
        Query query = session.createQuery(q);
        Iterator ite = query.iterate();
        AnaRappresentante rappRow = null;
        if(ite.hasNext()){
          rappRow = (AnaRappresentante) ite.next();
        }
       
        if(rappRow!=null) {
            //BigDecimal codiceRappresentante = rappRow.getFkidRappresentante();
            //logger.debug("***codice rappresentante legale:" +codiceRappresentante);
            SchedaSoggettoBean rappresentante = null;
            AnaSoggetto soggRapp = rappRow.getAnaRappresentante();
            Iterator iter = soggRapp.getAnaIndirizzos().iterator();
            AnaIndirizzo soggInd = null;
            boolean found=false;
            while(iter.hasNext()&&!found){
              soggInd = (AnaIndirizzo) iter.next();
              System.out.println(soggInd+":"+soggInd.getDtMod());
              // TODO controllare se dt_mod è null !!
              if(soggInd.getDtMod()!=null) found=true;
            }
            if(soggRapp!=null) {
                rappresentante = new SchedaSoggettoBean();
                SoggettoBean sR = setRappresentante(soggRapp,soggInd); 
                rappresentante.setSoggetto(sR);
            }else{
                //il codice del rappresentante non è settato 
                //nella tabella ana_rappresentante quindi
                //prendo i dati non nulli presenti in tale tabella.
                rappresentante = new SchedaSoggettoBean();

                SoggettoBean sR = new SoggettoBean();
                sR.setCodiceFiscale(rappRow.getCodiceFiscale());
                sR.setCognome(rappRow.getCognome());
                sR.setNome(rappRow.getNome());
                sR.setDataNascita(rappRow.getDtNascita());
                sR.setIdEnte(new BigDecimal(rappRow.getIdEnte().toString()));
                //LOC_NASCITA, RECAPITO
                LocalitaBean locNas = new LocalitaBean();
                locNas.setDesLocalita(rappRow.getLocNascita());
                IndirizzoBean indRes = new IndirizzoBean();
                //indRes.setDesArea(rappRow.getRecapito()); NON SETTATO PERCHé non STRUTTURATO

                sR.setLocalitaNascita(locNas);
                sR.setResidenza(indRes);
                rappresentante.setSoggetto(sR); 

            }
            soggGiur.setRappresentanteLegale(rappresentante);
        }
                      
        //idirizzo sede
        IndirizzoBean indSede = new IndirizzoBean();
        //indirizzo comune
        ComuneBean comuneSede = new ComuneBean();
        //comuneSede.setCodIstatComune(codIstat(soggFRow.getFkidComune()));
        comuneSede.setDesComune(soggGiurRow.getDesComuneIndirizzo());
        comuneSede.setDesProvincia(soggGiurRow.getDesProvinciaIndirizzo()); 
        //localita comune
        LocalitaBean localitaSede = new LocalitaBean();
        //localitaSede.setCodIstatStato(codIstat(soggFRow.getFkidLocalita()));
        localitaSede.setDesStato(soggGiurRow.getDesStatoIndirizzo());
        localitaSede.setDesLocalita(soggGiurRow.getDesLocIndirizzo());
        localitaSede.setDesContea(soggGiurRow.getConteaIndirizzo());
                
        indSede.setCap(soggGiurRow.getCapIndirizzo());
        indSede.setComune(comuneSede);
        //indSede.setDataFine(); non presenti nel db
        //indSede.setDataInizio(); non presenti nel db
        indSede.setDesArea(soggGiurRow.getAreaIndirizzo());
        indSede.setInterno(soggGiurRow.getInterno());
        indSede.setNumCiv(soggGiurRow.getNumeroCivicoIndirizzo());
        indSede.setLetCiv(soggGiurRow.getLetteraCivicoIndirizzo());
        indSede.setLocalita(localitaSede);
        soggGiur.setIndirizzoSede(indSede);
                          
        return soggGiur;
    }          
  
  /**
   * metodo per la decodifica del sesso
   */
  private static String codSesso(Integer i){
    String sex[]={"M","F"};
    return sex[i.intValue()-1];
  }
  
  /**
  *  Funzione di utilita' per convertire bigdecimal in integer
  */
  private  static Integer codIstat(BigDecimal cod){
    if (cod==null)
      return null;
    return new Integer(cod.toString());
  }


}
