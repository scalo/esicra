/*
 *  CLASSE ABBANDONATA
 */
 
package it.saga.egov.esicra.timer.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

/**
 *  Servlet di test per controllare i parametri passati dai forms
 */
public class EesTimerParameters extends HttpServlet  {

  private static final String CONTENT_TYPE = "text/html; charset=iso-8859-1";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>EesTimerParameters</title></head>");
    out.println("<h1 align=center >EesTimerParameters</title></h1>");
    out.println("<body>");
    Enumeration enume =  request.getParameterNames();
    
    while(enume.hasMoreElements()){
      String name = (String) enume.nextElement();
      String value = (String) request.getParameter(name);
      out.println("<p>");
        out.println("<b>"+name+"</b> :"+value);
      out.println("</p>");
    }
    out.println("<br>");
    out.println("<B>Data: </B>"+ new Date());
    out.println("</body></html>");
    out.close();
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request ,response );
  }
}