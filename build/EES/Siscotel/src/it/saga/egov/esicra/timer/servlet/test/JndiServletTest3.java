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

/**
 *  Crea thread e lo registra al naming service
 */
public class JndiServletTest3 extends HttpServlet  {
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    MyThread mt =null;
    out.println("<html>");
    out.println("<head><title>JndiServletTest3</title></head>");
    out.println("<body>");
    out.println("<h2>JndiServletTest3</h2>");
    try{
      Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
      Context initialContext = new InitialContext(env);
      Context ctx = (Context)cerca(initialContext,"esicra");
      if(ctx==null){
        ctx = initialContext.createSubcontext("esicra");
        mt = new MyThread("cuccucuciù");
        mt.start();
        ctx.bind("MyThread",mt);
        out.println("Contesto 'esicra' creato :"+ ctx);
      }else{      
        out.println("Contesto 'esicra' già creato :"+"</p>");
        mt=( MyThread)ctx.lookup("MyThread");
        out.println("Thread : "+mt.getName()+"</p>");
        out.println("Passati:"+mt.getTime()+"ms");
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
      log(e.toString());
      return o;
    }
    return o;
  }

public class MyThread extends Thread {

    long start =0;

    public MyThread(String name){
      super(name);
    }
    
    public MyThread(){
      super();
    }
    
    public void run(){
      while(true){
        if (start==0) start = System.currentTimeMillis();
        try{
          this.sleep(1000);
        }catch(Exception e){
          //
        }
      }
    }

    long getTime(){
      return System.currentTimeMillis()-start;
    }
    
  }
  
}

