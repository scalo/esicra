package it.saga.egov.esicra.timer.servlet;

import it.saga.egov.esicra.timer.test.*;
import it.saga.egov.esicra.timer.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import javax.naming.*;
import java.util.*;
import org.apache.log4j.*;
import java.text.SimpleDateFormat;
import it.saga.egov.esicra.timer.servlet.EesHtmlUtils;

/**
 *  Servlet di controllo dell'EesTimer , gestisce le azioni associate
 *  alla pagina EesTimer.jsp
 *    
 *    @param operazione , operazione da compiere
 *      
 *      * cancella  cancella un task specificato da  un parametro secondario id
 *      * rimuovi_tutti i task schedulati
 *    
 *    @id  id richiesto per eseguire alcune operazioni
 *      
 *  
 */
public class EesTimerServlet extends HttpServlet  {

  private static final String CONTENT_TYPE = "text/html; charset=iso-8859-1"; 
  public  static final SimpleDateFormat sdf = EesHtmlUtils.DATE_FORMAT;
  private static Logger logger;
  private static String[] tipiTask; 
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    logger = Logger.getLogger(this.getClass());
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String op = "" ;
    EesTimer timer = null;
    try {
      timer = (EesTimer) EesTimerConf.getInstance();
      if(timer==null){
          //redirige alla pagina di errore
          response.sendError(response.SC_INTERNAL_SERVER_ERROR);
          return;
      }
      op = request.getParameter("operazione");
      //id = Long.parseLong(request.getParameter("id"));
      if(op.equalsIgnoreCase("cancella")){
       String ids = request.getParameter("id"); 
       Long id= new Long(ids);
       logger.info("Task "+id+" cancellato");
       timer.cancella(id);
      }else if(op.equalsIgnoreCase("rimuovi_tutti")){
        timer.rimuoviTutti();
      }else if(op.equalsIgnoreCase("modifica")){
          // [... ] operazione da implementare
      }else if(op.equalsIgnoreCase("inserisci")){ 
        /** giorni della settimana espressi come stringa
         *  0- lun  6 dom
         *  es 045 --> lun+gio+ven
         **/
        String pSettimana=request.getParameter("giorni_settimana");
        /** data dell'evento espressa in millisecondi **/
        String pData=request.getParameter("data_evento");
        /** frequenza dell'evento espressa in millisecondi **/
        String pFrequenza=request.getParameter("frequenza");
        // ATTENZIONE , togliere spazi in testa e coda al parametro
        String pTipoTask=request.getParameter("task");
        logger.debug("Task: "+pTipoTask);
        String pDescrizione=request.getParameter("descrizione");
        long dtInizio = Long.parseLong(pData);
        if (dtInizio==0){
            logger.debug("task immediato");
            dtInizio=System.currentTimeMillis();
        }
        long frequenza = Long.parseLong(pFrequenza);
        boolean[] settimana = giorniSettimana(pSettimana);
        String pack="it.saga.egov.esicra.timer";
        // parametri per task di export
        String pServizioPagamento=request.getParameter("servizio_pagamento");
        String pServizioPratica=request.getParameter("servizio_pratica");
        String pDataIni=request.getParameter("data_ini");
        String pDataFine=request.getParameter("data_fine");

        // DEBUG
        logger.debug("servizio_pagamento="+pServizioPagamento);
        logger.debug("servizio_pratica="+pServizioPratica);
        logger.debug("data_ini="+pDataIni);
        logger.debug("data_fine="+pDataFine);
        
        EesTask task =null;
        Hashtable tipiTask= EesTimerConf.listaTasks();
        if(tipiTask.containsValue(pTipoTask)){
            String nomeclasse=pack+"."+pTipoTask;
            Class classe = Class.forName(nomeclasse);
            task = (EesTask)classe.newInstance();
            task.setDtInizio(dtInizio);
            task.setFrequenza(frequenza);
            task.setGiorniSettimana(settimana);
            logger.info("creato task "+task);
            //task=new EesTaskGen(dtInizio,frequenza,settimana);
            // aggiunge i parametri per l'export
            if(pTipoTask.equals("ExportPagamentiTask")){
                logger.debug("ExportPagamentiTask");
                long id_servizio=Long.parseLong(pServizioPagamento);
                long data_inizio=(sdf.parse(pDataIni)).getTime();
                long data_fine=(sdf.parse(pDataFine)).getTime();
                String prefisso = EesHtmlUtils.siglaPagamento(pServizioPagamento);
                logger.debug("PREFISSO="+prefisso);
                task.setIdServizio(id_servizio);
                task.setDataInizio(data_inizio);
                task.setDataFine(data_fine);
                task.setSiglaExport(prefisso);
            }
            if(pTipoTask.equals("ExportPraticheTask")){
                logger.debug("ExportPraticheTask");
                long id_servizio=Long.parseLong(pServizioPratica);
                long data_inizio=(sdf.parse(pDataIni)).getTime();
                long data_fine=(sdf.parse(pDataFine)).getTime();
                String prefisso = EesHtmlUtils.siglaPratica(pServizioPratica);
                logger.debug("PREFISSO="+prefisso);
                task.setIdServizio(id_servizio);
                task.setDataInizio(data_inizio);
                task.setDataFine(data_fine);
                task.setSiglaExport(prefisso);
            }
        }else{
          // tipo task non definito
          logger.error("Tipo di task non riconosciuto");
        }
        
        /*
        //<< TEMPORANEO
        if(pTipoTask.equals("ImportTask")){
          task= new ImportTask();
        }else if(pTipoTask.equals("ExportTask")){
          task=new ExportTask();
        }else if(pTipoTask.equals("EesTaskGen")){
          task=new EesTaskGen(dtInizio,frequenza,settimana);
        }else{
          // tipo task non definito
        }
        //>>
        */
        
        task.setDescrizione(pDescrizione);
        timer.inserisci(task);
        logger.info("Inserito Task :"+task.getDescrizione());
        logger.debug("frequenza: "+task.getFrequenza());
      }else if(op.equalsIgnoreCase("rimuovi_completati")){
        int n=0;
        synchronized(timer.taskMap){
          Iterator ite = timer.taskMap.values().iterator();
          //logger.debug("Rimuovi completati: init size="+timer.list().size());         
          while(ite.hasNext()){
            EesTask t = (EesTask) ite.next();
            if(t.getStato()==EesTask.END){
              //logger.debug("rimosso task completato id="+t.getId()+" status="+t.getStato());
              ite.remove();
              n++;
            }
          }
        }
        logger.info("Rimossi "+n+" Tasks completati ");
      }else if(op.equalsIgnoreCase("aggiungi_test")){
        EesTimerConf.test();
        logger.info("Inseriti Tasks di test ");
      }else{
        //operazione non supportata
      }
      // SALVA I TASKS del timer
      timer.salva();
    } catch(Exception e) {
      e.printStackTrace();
      logger.error(e);
    }finally{
      
    }
    response.sendRedirect("EesTimer.jsp");
  }

  /**   
   *    Metodo statico di conversione per convertire i giorni della settimana
   *    ricevuti come parametro stringa dal form in array di boolean utilizzato
   *    dal Task.
   **/
  public static boolean[] giorniSettimana(String settimana){
    boolean array[]=new boolean[7];
    // se non c'è selezione della settimana ritorna array nullo
    if (settimana.length()==0) return null;
    for(int i=0;i<settimana.length();i++){
      char[] c = new char[1];
      c[0] = settimana.charAt(i);
      int n  = Integer.parseInt(new String(c));
      array[n]=true;
    }
    return array;
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request ,response );
  }
  
}

