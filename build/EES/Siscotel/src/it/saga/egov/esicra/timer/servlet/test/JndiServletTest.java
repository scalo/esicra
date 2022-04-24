package it.saga.egov.esicra.timer.servlet.test;

import it.saga.egov.esicra.timer.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;
//import it.saga.egov.esicra.timer.EesTaskOld;

public class JndiServletTest extends HttpServlet implements SingleThreadModel {

  private static SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
  
  private static final String CONTENT_TYPE = "text/html; charset=utf-8";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    EesTimer scheduler=null;
    Context ctx = null;
    Context initialContext=null;
    Hashtable env = new Hashtable();
    // per test si usa factory di apache piuttosto che quello j2ee
    System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
    try{
    initialContext = new InitialContext(env);
      ctx = (Context) cercaRisorsa(initialContext,"esicra");
      if (ctx==null){
        ctx =(Context) initialContext.createSubcontext("esicra");  
      }//scheduler = (EesScheduler) ctx.lookup("scheduler");
      scheduler = (EesTimer) cercaRisorsa(ctx,"scheduler");
      if (scheduler==null){
        scheduler = new EesTimer();
        //scheduler.schedule(new ImportTask(),10000,true);
        // aspetta 20 millisec perchè altrimenti i 2 thread hanno lo 
        // OCIO stesso timestamp 
        Thread.currentThread().sleep(20);
        //scheduler.schedule(new ImportTask(),10000,true);
        ctx.bind("scheduler",scheduler);
        out.println("Nuovo Scheduler registrato id: "+scheduler.getId()+"<p/>");
      }else{
        out.println("<html>");
        out.println("<head><title>JndiServletTest</title></head>");
        out.println("<body>");
        out.println("Scheduler già registrato id: "+scheduler.getId()+"<p/>");
        List list =(List) scheduler.list();
        out.println("<UL>");
        for(int i=0;i<list.size();i++){
          EesTask t = (EesTask)list.get(i);
          Long id = t.getId();
          out.println("<LI> task id="+id);
          //out.println("data ="+t.getInfo(id));
          out.println("</LI>");
        }
        out.println("</UL>");
      }
    }catch(NamingException e){
      out.println("Non posso creare contesto iniziale");
    }catch(Exception e){
      out.println("boh???");
      log(e.toString());
    }
    out.println("</body></html>");
    out.close();
    this.destroy();
    System.out.println("destroy()");
  }

  /*
   *    metodo da riciclare 
   */
  
  /**
   *  Metodo statico per evitare di utilizzare try/catch annidati
   *  
   *  @param  ctx contesto in cui cercare la risorsa
   *  @param  nome  nome della risorsa
   *  @return   Oggetto risorsa oppure null se non trovata
   */

  private static Object cercaRisorsa(Context ctx ,String nome ) {
    Object o = null;
    try{
      return ctx.lookup(nome);
    }catch(NamingException e){
      return o;
    }
  }

}