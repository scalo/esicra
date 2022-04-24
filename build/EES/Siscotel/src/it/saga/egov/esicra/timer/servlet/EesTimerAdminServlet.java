/**
 * 
 *  CLASSE ABBANDONATA , centralizzato tutto in EesTimerServlet per la logica
 * 
 * 
 *
 *  @deprecated
 * 
 */
package it.saga.egov.esicra.timer.servlet;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.*;
//<< TEMPORANEO
import  it.saga.egov.esicra.timer.test.*;
import it.saga.egov.esicra.timer.test.EesTask4Test2;
import it.saga.egov.esicra.timer.DummyTask;
//>>

/**
 *    Servlet di controllo che interfaccia l'EesTimer.
 *    Da usare per fermare , far ripartire e stoppare l'EesTimer
 */
public class EesTimerAdminServlet extends HttpServlet implements SingleThreadModel {

  private static SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
  
  private static final String CONTENT_TYPE = "text/html; charset=iso-8859-1";

  private static Logger logger;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    logger = EsicraConf.getLogger(this.getClass());
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    String op = request.getParameter("operazione");
    try {
      if(op.equalsIgnoreCase("inserisci")){
        EesTimer timer = (EesTimer) EesTimerConf.getInstance();
        if(timer==null){
          //redirige all apagina di errore
          // TODO customizzare
          out.println("<H1 align=center >"+"Timer non trovato"+"<H1>");
          out.close();
          return;
          //response.sendError(response.SC_INTERNAL_SERVER_ERROR);
        }
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
        String pDescrizione=request.getParameter("descrizione");
        long dtInizio = Long.parseLong(pData);
        long frequenza = Long.parseLong(pFrequenza);
        boolean[] settimana = giorniSettimana(pSettimana);
        //<< TEMPORANEO
        out.println("<html>");
        out.println("<body>");
        out.println("<p> data evento: "+dtInizio+"</p>");
        out.println("<p>giorni settimana: "+settimana+"</p>");
        out.println("<p>frequenza: "+frequenza+"</p>");
        out.println("<p>tipo task: "+pTipoTask+"</p>");
        out.println("<p>Descrizione: "+pDescrizione+"</p>");
        
        //>>
        // inserisci task  (vengono istanziati gli oggetti task)
        // path della classe dei task
        //<< TEMPORANEO
        String pack="it.saga.egov.esicra.timer";
        String nomeClasse=pack+"."+pTipoTask;
        //EesTask task = (EesTask) Class.forName(nomeClasse).newInstance();
        EesTask task =null;
        if(pTipoTask.equals("ImportTask")){
          task= new ImportTask();
        }else if(pTipoTask.equals("ExportTask")){
          //passa come paramentro al task la classe di esportazione  
          //task=new ExportTask();
        }else if(pTipoTask.equals("EesTaskGen")){
          out.println("<B>Task:</B>"+task);
          task=new DummyTask(dtInizio,frequenza,settimana);
        }else if(pTipoTask.equals("CoordinatorTask")){
          out.println("<B>Task:</B>"+task);
          task=new CoordinatorTask(dtInizio,frequenza,settimana);
        }else{
          logger.error("Tipo di task non supportato");
        }
        // imposta il task 
        /**
        task.setDtInizio(dtInizio);
        task.setFrequenza(frequenza);
        task.setGiorniSettimana(settimana);
        //come ultima cosa rende attivo il task 
        task.setStato(EesTask.RUN);
        */
        //>>
        task.setDescrizione(pDescrizione);
        timer.inserisci(task);
        logger.info("Inseriti Task :"+task.getDescrizione());
      
      }
    }catch(Exception e) {
      e.printStackTrace();
    }finally{
    }
    out.println("<a href=\"EesTimer.jsp\">Ritorna al Timer Admin</a>");
    out.println("</body>");
    out.println("</html>");
    out.close();
  }

  /**   
   *    Metodo statico di conversione per convertire i giorni della settimana
   *    ricevuti come parametro stringa dal form in array di boolean utilizzato
   *    dal Task.
   **/
 public static boolean[] giorniSettimana(String settimana){
    boolean array[]=new boolean[7];
    // sen non c'è selezione della settimana titorna array nullo
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

