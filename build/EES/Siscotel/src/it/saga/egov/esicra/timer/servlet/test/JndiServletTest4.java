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
import javax.naming.*;
import java.util.*;

public class JndiServletTest4 extends HttpServlet  {
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>JndiServletTest2</title></head>");
    out.println("<body>");
    out.println("<h2>JndiServletTest2</h2>");
    try{
      Hashtable env= new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
      Context initialContext = new InitialContext(env);
      out.println(initialContext+"<p/>");  
      Context esicraCtx = (Context) cerca(initialContext,"java:comp/env/esicra");
      if(esicraCtx==null){
        Context ctx = initialContext.createSubcontext("java:comp");
        ctx = initialContext.createSubcontext("java:comp/env");
        esicraCtx = (Context) initialContext.createSubcontext("java:comp/env/esicra");
        // crea variabili
        esicraCtx.bind("var1",new String("pippo"));
        esicraCtx.bind("var2",new String("pluto"));
        esicraCtx.bind("var3",new String("paperino"));
        out.println("Contesto 'java:comp/env/esicra' creato");
      }else{
        out.println("Elenco : <p/>");
        NamingEnumeration enu = esicraCtx.list("");
        out.println("<UL>");
        while(enu.hasMore()){
          out.println("<LI>");
          out.println(enu.next().toString());
          out.println("<LI>");
        }
        out.println("</UL>");
      }
    }catch(NamingException ne){
      out.println("ERRORE NAMING");
      out.println(ne.toString());
    }catch(Exception e){
      out.println("ERRORE GENERICO"); 
      out.println(e.toString());
    }
    out.println("</body></html>");
    out.close();
  }

  private Object cerca(Context ctx,String nome){
    Object o=null;
    try{
      o=ctx.lookup(nome);
    }catch(NamingException e){
      return o;
    }
    return o;
  }
}