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

public class JndiServletTest2 extends HttpServlet  {
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
      Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
      Context initialContext = new InitialContext(env);
      out.println(initialContext+"<p/>");
      Context ctx = (Context)cerca(initialContext,"esicra");
      if(ctx==null){
        ctx = initialContext.createSubcontext("esicra");
        String s1 = new String("pippo");
        String s2 = new String("paperino");
        String s3 = new String("pluto");
        ctx.bind("s1",s1);
        ctx.bind("s2",s2);
        ctx.bind("s3",s3);
        out.println("Contesto 'esicra' creato");
      }else{
        out.println("Elenco : <p/>");
        NamingEnumeration enu = initialContext.list("esicra");
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
    }catch(Exception e){
      out.println("ERRORE GENERICO"); 
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