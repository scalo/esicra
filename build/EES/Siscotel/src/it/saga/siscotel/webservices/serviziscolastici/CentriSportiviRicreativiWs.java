package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.egov.esicra.EsicraConf;
import it.saga.egov.esicra.xml.Bean2Xml;
import it.saga.egov.esicra.xml.Xml2Bean;
import it.saga.siscotel.beans.serviziscolastici.PraIscrizioneCentroBean;
import it.saga.siscotel.beans.serviziscolastici.PraRecessoCentroBean;
import it.saga.siscotel.beans.base.PraticaBean;
import it.saga.siscotel.beans.base.DatiPraticaBean;
import it.saga.siscotel.db.PraPraticaDett;
import it.saga.siscotel.db.PraPraticaTesta;
import it.saga.siscotel.db.VPraticaCompleta;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import it.saga.egov.esicra.xml.PrettyBean;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.apache.soap.SOAPException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import it.saga.siscotel.webservices.Servizi;


/**
  
  Web Service Gestione Centri Sportici e Ricreativi costituito dai metodi
  <ul>
    <li>
      inserisciPraIscrizioneCentro
    </li>
    <li>
      cercaPraIscrizioneCentro
    </li>
    <li>
      inserisciPraRecessoCentro
    </li>
    <li>
      cercaPraRecessoCentro
    </li>
  </ul>

*/
public class CentriSportiviRicreativiWs implements CentriSportiviRicreativiInt {
    
    private final static int MAX_RESULT=500;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Session session;
    private Transaction transaction ;
    private Logger logger;
    private Integer idEnte;
    
    public CentriSportiviRicreativiWs() {
      EsicraConf.configura();
      logger = Logger.getLogger(this.getClass());
      session = HibernateUtil.currentSession();
      transaction = session.beginTransaction();
      String id_ente = System.getProperty("esicra.id_ente");
      idEnte = new Integer(id_ente);
    }
   
   /**
    *
    Controlla che i parametri della pratica  necessari al metodo del web service siano corretti
    * @param datiPra  bean da verificare
    * @return un boolean che definisce l'esito dell'operazione
    * @throws SoapException di tipo SOAP-Env:Client nel caso in cui i parametri 
    *       di ricerca non siano conformi alle specifiche
    *   
    */
    private boolean checkDatiPratica(DatiPraticaBean datiPra) throws SOAPException{
      boolean ok = false;
      if (datiPra==null){
        throw new SOAPException("SOAP-ENV:Client","DatiPraticaBean Null");
      }else{
        PraticaBean pra = datiPra.getPratica();
        if (pra==null){
          throw new SOAPException("SOAP-ENV:Client","PraticaBean Null");
        }else{
           Object idServizio = pra.getIdServizio();
           Object dataPratica = pra.getDataPratica();
           Object idEnteDestinatario = pra.getIdEnteDestinatario();
           if (idServizio==null){
              throw new SOAPException("SOAP-ENV:Client","idServizio Null");
           }else if(dataPratica==null){
              throw new SOAPException("SOAP-ENV:Client","dataPratica Null");
           }else if(idEnteDestinatario==null){
              throw new SOAPException("SOAP-ENV:Client","idEnteDestinatario Null");
           }else{
             ok=true;
           }
        }
      }
      return ok;
    }
    
    /**
    * Inserisce una pratica di iscrizione ad un centro sportivo ricreativo
   * 
   * @param pPraRecessoMensa bean contenete i dati della pratica
   * @return   Un identificativo univoco relativo alla pratica creata.
   * @throws Exception (SoapException)
   */
    public BigDecimal inserisciPraIscrizioneCentro(
        PraIscrizioneCentroBean praIscrizioneCentro) throws Exception {
        boolean check = checkDatiPratica(praIscrizioneCentro.getDatiPratica());
        if(!check) logger.error("Errore nella pratica da inserire , campo obbligatorio mancante ");
        BigDecimal idPratica=null;
        logger.debug(PrettyBean.format(praIscrizioneCentro));
        PraPraticaTesta testa = new PraPraticaTesta();
        //testa.setIdServizio(praIscrizioneCentro.getDatiPratica().getPratica().getIdServizio());
        testa.setIdServizio(new BigDecimal(Servizi.ID_ISCRIZIONE_CENTRO_SPORTIVO));
        testa.setDtPratica(praIscrizioneCentro.getDatiPratica().getPratica().getDataPratica());
        // come da ws precedenti es edilizia viene impostato il richiedente
        testa.setCodiceFiscale(praIscrizioneCentro.getDatiPratica().getSoggettoRichiedente().getCodiceFiscale());
        testa.setOggetto(praIscrizioneCentro.getDatiPratica().getPratica().getOggetto());
        //String id_ente = System.getProperty("esicra.id_ente");
        //Integer idEnte = new Integer(id_ente);
        Integer idEnteDestinatario = new Integer((praIscrizioneCentro.getDatiPratica().getPratica().getIdEnteDestinatario().intValue()));
        testa.setIdEnteDestinatario(idEnteDestinatario);
        testa.setIdEnte(idEnte);
        testa.setDtMod(Calendar.getInstance().getTime());
        PraPraticaDett dettaglio = new PraPraticaDett();
        dettaglio.setIdStatoPratica(new Integer(1));
        dettaglio.setDesStatoPratica("DA PRENDERE IN CARICO");
        dettaglio.setIdEnteDestinatario(idEnteDestinatario);
        dettaglio.setIdEnte(idEnte);
        dettaglio.setDtMod(Calendar.getInstance().getTime());
        dettaglio.setPraPraticaTesta(testa);
        dettaglio.setDtStato(Calendar.getInstance().getTime());
        testa.setDocXml("");
        session.save(testa);
        session.save(dettaglio);
        idPratica = testa.getPkid();
        // Corregge l'id della pratica
        praIscrizioneCentro.getDatiPratica().getPratica().setIdPratica(testa.getPkid());
        Bean2Xml bean2Xml = new Bean2Xml(praIscrizioneCentro);
        testa.setDocXml(bean2Xml.toString());
        session.save(testa);
        session.flush();
        transaction.commit();
        return idPratica;
    }
    
  /**
   * 
   * Il servizio implementa la ricerca delle pratiche di richiesta iscrizione secondo le modalità sotto elencate:
    è necessario specificare il codice fiscale (pCodiceFiscale) e lidentificativo (idEnteDestinatario) dellente destinatario della pratica
    è consentito pilotare la ricerca specificando anche lidentificativo della pratica (pIdPratica).
    è consentito limitare la ricerca alle sole pratiche che si trovano in un determinato stato (pStato).
    è consentito rifinire ulteriormente la ricerca specificando la data di inizio (pDataInizio) e di fine (pDataFine) o entrambe per limitare la ricerca ad un intervallo di tempo.
    I parametri opzionali non utilizzati devono essere attualizzati con il valore NULL.

   * 
   * @param codiceFiscale 
   * @param idEnteDestinatario 
   * @param idPratica 
   * @param stato 
   * @param dataInizio 
   * @param dataFine 
   * @return 	Un array di PraIscrizioneCentroBean contenente i dati individuati nel caso in cui la ricerca produca informazioni.
      Una array vuoto nel caso in cui non esistano pratiche che soddisfino i criteri impostati.
      SoapException di tipo SOAP-Env:Client nel caso in cui i parametri di ricerca non siano conformi alle specifiche

   * @throws Exception 
   */
    public PraIscrizioneCentroBean[] cercaPraIscrizioneCentro(String codiceFiscale,
        BigDecimal idEnteDestinatario, BigDecimal idPratica, Integer stato, Date dataInizio,
        Date dataFine) throws Exception {
        List list = new ArrayList();
        //imposta condizioni di where
        StringBuffer sb = new StringBuffer();
        String str ="";
        sb.append(" UPPER(CODICE_FISCALE) = UPPER('"+codiceFiscale+"')");
        if (idEnteDestinatario != null) {
            sb.append(" AND ID_ENTE_DESTINATARIO = "+idEnteDestinatario);
        }
        if (idPratica != null){
            sb.append(" AND ID_PRATICA = "+idPratica);
        }
        if (stato != null){
            sb.append(" AND ID_STATO_PRATICA = "+stato);
        }
        if(dataInizio !=null){
          str = sdf.format(dataInizio);
          sb.append( " AND dt_pratica >=  to_date('" + str + "','DD/MM/YYYY')");
        }
         if(dataInizio !=null){
          str = sdf.format(dataFine);
          sb.append( " AND dt_pratica <=  to_date('" + str + "','DD/MM/YYYY')");
        }
        // codice servizio
        sb.append(" AND id_servizio="+Servizi.ID_ISCRIZIONE_CENTRO_SPORTIVO);
        String q = "from VPraticaCompleta where "+sb.toString();
        Query query = session.createQuery(q);
        logger.debug("CERCA PRA ISCRIZIONE CENTRO SPORTIVO:"+query.getQueryString());
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        VPraticaCompleta vPra = null;
        while(ite.hasNext()){
          vPra = (VPraticaCompleta) ite.next();
          // deserializza la pratica
          String xmlDoc = vPra.getDocXml();
          PraIscrizioneCentroBean  pratica = new PraIscrizioneCentroBean();
          Xml2Bean xml2Bean = new Xml2Bean(xmlDoc,pratica);
          pratica = (PraIscrizioneCentroBean) xml2Bean.getBean();
          list.add(pratica);
        }
        int len = list.size();
        PraIscrizioneCentroBean[] array = new PraIscrizioneCentroBean[len];
        array = (PraIscrizioneCentroBean[]) list.toArray(array);
        return array;
    }
    
   /**
    * Inserisce una pratica di recesso da iscrizione ad un centro sportivo ricreativo
   * 
   * @param pPraRecessoMensa bean contenete i dati della pratica
   * @return   Un identificativo univoco relativo alla pratica creata.
   * @throws Exception (SoapException)
   */
    public BigDecimal inserisciPraRecessoCentro(PraRecessoCentroBean praRecessoCentro)
        throws Exception {
          boolean check = checkDatiPratica(praRecessoCentro.getDatiPratica());
          if(!check) logger.error("Errore nella pratica da inserire , campo obbligatorio mancante ");
          BigDecimal idPratica=null;
          logger.debug(PrettyBean.format(praRecessoCentro));
          PraPraticaTesta testa = new PraPraticaTesta();
          //testa.setIdServizio(praRecessoCentro.getDatiPratica().getPratica().getIdServizio());
          testa.setIdServizio(new BigDecimal(Servizi.ID_RECESSO_CENTRO_SPORTIVO));
          testa.setDtPratica(praRecessoCentro.getDatiPratica().getPratica().getDataPratica());
          // come da ws precedenti es edilizia viene impostato il richiedente
          testa.setCodiceFiscale(praRecessoCentro.getDatiPratica().getSoggettoRichiedente().getCodiceFiscale());
          testa.setOggetto(praRecessoCentro.getDatiPratica().getPratica().getOggetto());    
          //String id_ente = System.getProperty("esicra.id_ente");
          //Integer idEnte = new Integer(id_ente);
          Integer idEnteDestinatario = new Integer((praRecessoCentro.getDatiPratica().getPratica().getIdEnteDestinatario().intValue()));
          testa.setIdEnteDestinatario(idEnteDestinatario);
          testa.setIdEnte(idEnte);
          testa.setDtMod(Calendar.getInstance().getTime());
          PraPraticaDett dettaglio = new PraPraticaDett();
          dettaglio.setIdStatoPratica(new Integer(1));
          dettaglio.setDesStatoPratica("DA PRENDERE IN CARICO");
          dettaglio.setIdEnteDestinatario(idEnteDestinatario);
          dettaglio.setIdEnte(idEnte);
          dettaglio.setDtMod(Calendar.getInstance().getTime());
          dettaglio.setPraPraticaTesta(testa);
          dettaglio.setDtStato(Calendar.getInstance().getTime());
          testa.setDocXml("");
          session.save(testa);
          session.save(dettaglio);
          idPratica = testa.getPkid();
          // Corregge l'id della pratica
          praRecessoCentro.getDatiPratica().getPratica().setIdPratica(testa.getPkid());
          Bean2Xml bean2Xml = new Bean2Xml(praRecessoCentro);
          testa.setDocXml(bean2Xml.toString());
          session.save(testa);
          session.flush();
          transaction.commit();
          return idPratica;
    }
    
  /**
   * 
   * Il servizio implementa la ricerca delle pratiche di richiesta recesso secondo le modalità sotto elencate:
    è necessario specificare il codice fiscale (pCodiceFiscale) e lidentificativo (idEnteDestinatario) dellente destinatario della pratica.
    è consentito pilotare la ricerca specificando anche lidentificativo della pratica (pIdPratica).
    è consentito limitare la ricerca alle sole pratiche che si trovano in un determinato stato (pStato).
    è consentito rifinire ulteriormente la ricerca specificando la data di inizio (pDataInizio) e di fine (pDataFine) o entrambe per limitare la ricerca ad un intervallo di tempo.
    I parametri opzionali non utilizzati devono essere attualizzati con il valore NULL.

   * 
   * @param codiceFiscale 
   * @param idEnteDestinatario 
   * @param idPratica 
   * @param stato 
   * @param dataInizio 
   * @param dataFine 
   * @return  	Un array di PraRecessoCentroBean contenente i dati individuati nel caso in cui la ricerca produca informazioni.
      Una array vuoto nel caso in cui non esistano pratiche che soddisfino i criteri impostati.
      SoapException di tipo SOAP-Env:Client nel caso in cui i parametri di ricerca non siano conformi alle specifiche

   * @throws Exception 
   */
    public PraRecessoCentroBean[] cercaPraRecessoCentro(String codiceFiscale,
        BigDecimal idEnteDestinatario, BigDecimal idPratica, Integer stato, Date dataInizio,
        Date dataFine) throws Exception {
          List list = new ArrayList();
          //imposta condizioni di where
          StringBuffer sb = new StringBuffer();
          String str ="";
          sb.append(" UPPER(CODICE_FISCALE) = UPPER('"+codiceFiscale+"')");
          if (idEnteDestinatario != null) {
              sb.append(" AND ID_ENTE_DESTINATARIO = "+idEnteDestinatario);
          }
          if (idPratica != null){
            sb.append(" AND ID_PRATICA = "+idPratica);
         }
          if (stato != null){
              sb.append(" AND ID_STATO_PRATICA = "+stato);
          }
          if(dataInizio !=null){
            str = sdf.format(dataInizio);
            sb.append( " AND dt_pratica >=  to_date('" + str + "','DD/MM/YYYY')");
          }
           if(dataInizio !=null){
            str = sdf.format(dataFine);
            sb.append( " AND dt_pratica <=  to_date('" + str + "','DD/MM/YYYY')");
          }
          // codice servizio
          sb.append(" AND id_servizio="+Servizi.ID_RECESSO_CENTRO_SPORTIVO);
          String q = "from VPraticaCompleta where "+sb.toString();
          Query query = session.createQuery(q);
          query.setMaxResults(MAX_RESULT);
          logger.debug("CERCA PRA RECESSO CENTRO SPORTIVO:"+query.getQueryString());
          Iterator ite = query.iterate();
          VPraticaCompleta vPra = null;
          while(ite.hasNext()){
            vPra = (VPraticaCompleta) ite.next();
            // deserializza la pratica
            String xmlDoc = vPra.getDocXml();
            PraRecessoCentroBean  pratica = new PraRecessoCentroBean();
            Xml2Bean xml2Bean = new Xml2Bean(xmlDoc,pratica);
            pratica = (PraRecessoCentroBean) xml2Bean.getBean();
            list.add(pratica);
          }
          int len = list.size();
          //System.out.println("len:"+len);
          PraRecessoCentroBean[] array = new PraRecessoCentroBean[len];
          array = (PraRecessoCentroBean[]) list.toArray(array);
          return array;
    }   
}

