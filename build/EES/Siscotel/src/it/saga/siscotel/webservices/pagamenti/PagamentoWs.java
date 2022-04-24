package it.saga.siscotel.webservices.pagamenti;

import it.saga.egov.esicra.EsicraConf;
import it.saga.siscotel.beans.base.PagamentoBean;
import it.saga.siscotel.db.PraPraticaPag;
import it.saga.siscotel.db.PraPraticaTesta;
import java.math.BigDecimal;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import it.saga.egov.esicra.xml.PrettyBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.soap.SOAPException;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
  
  Web Service Gestione Pagamenti costituito dai metodi
  <ul>
    <li>
      registraPagamento
    </li>
    <li>
      cercaPagamento
    </li>
    
  </ul>

*/
public class PagamentoWs  implements PagamentoInt {
    
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Session session;
    private Transaction transaction ;
    private Logger logger;
    private Integer idEnte = null;
    private final static int MAX_RESULT=500;
    
    public PagamentoWs(){
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
   * @param idPratica 
   * @param idEnteDestinatario 
   * @param pagamento 
   * @return  un boolean che definisce l'esito dell'operazione
   * @throws SOAPException 
   */
   private boolean checkRegistraPagamento(BigDecimal idPratica, BigDecimal idEnteDestinatario,
        PagamentoBean[] pagamento) throws SOAPException{
     boolean ok = false;
     if (idPratica==null){
        throw new SOAPException("SOAP-ENV:Client","idPratica Null");
      }else{
        if (idEnteDestinatario==null){
          throw new SOAPException("SOAP-ENV:Client","idEnteDestinatario Null");
        }else{
           if (pagamento.length==0){
              throw new SOAPException("SOAP-ENV:Client","Nessun pagamento da registrare , array pagamenti vuoto");
           }else{
             ok=true;
           }
        }
      }
      return ok;
   }
    
   /**
   *  Forniti i dati che caratterizzano il bollettino che registra lavvenuto pagamento (pPagamento),
   *  tali dati vengono associati alla pratica individuata dallidentificativo 
   *  fornito (pIdPratica) nellente specificato (pIdEnteDestinatario). 
   *
   * @param idPratica id della pratica (obbligatorio)
   * @param pIdEnteDestinatario  id ente destinatario del pagamento (obbligatorio)
   * @param pagamento array dei pagamenti (obbligatorio)
   * @return Un boolean che definisce lesito delloperazione.
   * @throws Exception 
   */
    public Boolean registraPagamento(BigDecimal idPratica, BigDecimal idEnteDestinatario,
        PagamentoBean[] pagamento) throws Exception {
      transaction = session.beginTransaction();
      Boolean res = Boolean.TRUE;
      boolean check = checkRegistraPagamento(idPratica,idEnteDestinatario,pagamento);
      if(!check) logger.error("Errore nella pratica da inserire , campo obbligatorio mancante ");
      PraPraticaPag praPag= null;
      PraPraticaTesta praTesta = null;
      int len = pagamento.length;
      if(len==0){
        logger.warn("Nessun pagamento da registrare");
      }
      for(int i =0 ;i<len;i++){
        try{
          praPag = new PraPraticaPag();
          praTesta = (PraPraticaTesta) session.get(PraPraticaTesta.class,idPratica);
          // riempie bean
          praPag.setPraPraticaTesta(praTesta);
          praPag.setDesTipoPagamento(pagamento[i].getTipoPagamento());
          praPag.setCausale(pagamento[i].getCausalePagemento());
          praPag.setDtPagamento(pagamento[i].getDataPagamento());
          praPag.setImportoPagato(pagamento[i].getImportoPagato());
          praPag.setImportoDaPagare(pagamento[i].getImportoDaPagare());
          praPag.setNumRata(pagamento[i].getNumRata());
          praPag.setDtMod(Calendar.getInstance().getTime());
          praPag.setIdEnte(idEnte);
          praPag.setIdEnteDestinatario(new Integer(idEnteDestinatario.intValue()));
          // duplicato
          praPag.setIdServizio(praTesta.getIdServizio());
          // salva pagamento
          session.save(praPag);
          session.flush();
          transaction.commit();
        }catch(Exception e){
          logger.error(e);
          res=Boolean.FALSE;
        }
      }
      return res;
    }
    
  /**
   * 
   * Controlla che i parametri della pratica  necessari al metodo del web service siano corretti
   * 
   * @param idPratica 
   * @param idEnteDestinatario 
   * @param pagamento 
   * @return  un boolean che definisce l'esito dell'operazione
   * @throws SOAPException 
   */
    private boolean checkCercaPagamento(BigDecimal idPratica , BigDecimal idEnteDestinatario ) throws SOAPException{
     boolean ok = false;
     if(idPratica==null ){
       throw new SOAPException("SOAP-ENV:Client","idPratica Null");
     }else if (idEnteDestinatario==null){
          throw new SOAPException("SOAP-ENV:Client","idEnteDestinatario Null");
     }else{
          ok=true;
     }
      return ok;
    }
   
  /**
   * 
   * Cerca i pagamenti associati ad una determinata pratica (idPratica), specificando l'ente (idEnteDestinatario)
   *  e opzionalmente la data di inizio (dataInizio) e la data di fine (dataFine)
   * 
   * @param idPratica (obbligatorio)
   * @param idEnteDestinatario (obbligatorio)
   * @param dataInizio (opzionale)
   * @param dataFine  (opzionale)
   * @return  viene restituito un Boolean che definisce l'esito dell'operazione
   * @throws Exception 
   */
    public PagamentoBean[] cercaPagamento(BigDecimal idPratica,
        BigDecimal idEnteDestinatario,  Date dataInizio,
        Date dataFine) throws Exception {
        List list = new ArrayList();
        //imposta condizioni di where
        checkCercaPagamento(idPratica,idEnteDestinatario);
        PraPraticaTesta testa = (PraPraticaTesta) session.get(PraPraticaTesta.class,idPratica);
        Set set = testa.getPraPraticaPags();
        if(set !=null ){
          Iterator ite = set.iterator();
          PraPraticaPag pag = null;
          while(ite.hasNext()){
            pag = (PraPraticaPag) ite.next();
            PagamentoBean pagBean = new PagamentoBean();
            pagBean.setIdPratica(pag.getPkid());
            pagBean.setTipoPagamento(pag.getDesTipoPagamento());
            pagBean.setCausalePagemento(pag.getCausale());
            pagBean.setDataPagamento(pag.getDtPagamento());
            pagBean.setImportoPagato(pag.getImportoPagato());
            pagBean.setImportoDaPagare(pag.getImportoDaPagare());
            pagBean.setNumRata(pag.getNumRata());
            list.add(pagBean);
          }
        }else{
          System.out.println("set null");
        }
        int len = list.size();
        PagamentoBean[] array = new PagamentoBean[len];
        array = (PagamentoBean[]) list.toArray(array);
        return array;
    }
        
}
