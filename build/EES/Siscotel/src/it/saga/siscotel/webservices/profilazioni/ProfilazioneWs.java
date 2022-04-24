package it.saga.siscotel.webservices.profilazioni;

import it.saga.egov.esicra.EsicraConf;
import it.saga.siscotel.beans.base.PagamentoBean;
import it.saga.siscotel.db.PraPraticaPag;
import it.saga.siscotel.db.PraPraticaTesta;
import it.saga.siscotel.db.ProTipo;
import it.saga.siscotel.db.ProValore;
import java.math.BigDecimal;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import it.saga.egov.esicra.xml.PrettyBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.soap.SOAPException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
  
  Web Service Gestione Profilazioni costituito dai metodi
  <ul>
    <li>
      cercaListaProfilazione
    </li>
    <li>
      inserisciProfilazione
    </li>
  </ul>

*/
public class ProfilazioneWs implements ProfilazioneInt {
    
    private final static int MAX_RESULT=500;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Session session;
    private Transaction transaction ;
    private Logger logger;
    private Integer idEnte = null;
    
    public ProfilazioneWs() {
      EsicraConf.configura();
      logger = Logger.getLogger(this.getClass());
      session = HibernateUtil.currentSession();
      String id_ente = System.getProperty("esicra.id_ente");
      idEnte = new Integer(id_ente);
    }
  
  
  /**
   * 
   * Controlla che i parametri della pratica  necessari al metodo del web service siano corretti
   * 
   * @param idLista 
   * @param idEnte
   * @return  un boolean che definisce l'esito dell'operazione
   * @throws SOAPException 
   */
   private boolean checkCercaListaProfilazione(BigDecimal idLista, BigDecimal idEnte) throws SOAPException{
     boolean ok = false;
     if (idLista==null){
        throw new SOAPException("SOAP-ENV:Client","idLista Null");
      }else{
        if (idEnte==null){
          throw new SOAPException("SOAP-ENV:Client","idEnte Null");
        }else{
             ok=true;
        }
      }
      return ok;
   }
    
 /**
   * Forniti i dati che caratterizzano la tipologia di lista di profilazione (pIdLista) e 
   * lente di cui si vuole conoscere la profilazione, viene ritornata la lista dei valori 
   * di profilazione per la lista specificate e relativi allente selezionato.
   * 
   * @param idLista  identificativo della profilazione (obbligatorio)
   * @param idEnte   ente (obbligatorio)
   * @return  	Un array di String contenente i dati individuati nel caso in cui la ricerca produca informazioni.
      Una array vuoto nel caso in cui non esistano valori di profilazione per la lista e lente selezionati.
      SoapException di tipo SOAP-Env:Client nel caso in cui i parametri di ricerca non siano conformi alle specifiche
      
   * @throws Exception  (SoapException)
   */
    public String[] cercaListaProfilazione(BigDecimal idLista, BigDecimal idEnte)
      throws Exception {
        List list = new ArrayList();
        //imposta condizioni di where
        checkCercaListaProfilazione(idLista,idEnte);
        StringBuffer sb = new StringBuffer();
        if (idEnte != null) {
            sb.append(" ID_ENTE = "+idEnte);
        }
        if (idLista != null){
            sb.append(" AND TIPO = "+idLista);
        }
        String q = "from ProValore where "+sb.toString();
        Query query = session.createQuery(q);
        logger.debug("CERCA LISTA PROFILAZIONE:"+query.getQueryString());
        query.setMaxResults(MAX_RESULT);
        Iterator ite = query.iterate();
        ProValore val = null;
        while(ite.hasNext()){
          val = (ProValore) ite.next();
          String valore  = val.getDescrizione();
          list.add(valore);
        }
        int len = list.size();
        String[] array = new String[len];
        array = (String[]) list.toArray(array);
        return array;
    }
    
 /** 
   * Si inserisce una profilazione specificando (idLista) , l'ente per cui viene fatta la profilazione (idEnte),
   * il valore della profilazione
   * 
   * @param idLista 
   * @param idEnte 
   * @param valore 
   * @return  un Boolean che definisce l'esito dell'operazione
   * @throws Exception  (SoapException)
   */
    
    public Boolean inserisciProfilazione(BigDecimal idLista, BigDecimal idEnte, String valore)
        throws Exception {
       Boolean res = Boolean.TRUE;
       transaction = session.beginTransaction();
       ProTipo proTipo =  (ProTipo) session.get(ProTipo.class,new BigDecimal("10"));
       ProValore pro = new ProValore();
       pro.setDescrizione("Scuola Elementare Di Test "+System.currentTimeMillis());
       pro.setProTipo(proTipo);
       pro.setIdEnte(new Integer("8240"));
       pro.setDtMod(Calendar.getInstance().getTime());
       session.save(pro);
       transaction.commit();
       return res;
    }
}

