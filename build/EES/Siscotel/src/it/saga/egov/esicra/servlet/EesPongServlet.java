package it.saga.egov.esicra.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

public class EesPongServlet extends HttpServlet  {
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   *  La servlet chiamata dal client, contatta il pat e redirige il flusso
   *  al client.
   *  In qusto modo vi è la sicurezza che sia il nodo a chiamare il pat e non
   *  il browser dell'utente
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<body  onLoad='window.focus()' >");
    out.println("<h2 align=\"center\">");
    out.println("Connessione effettuata con successo");
    out.println("</h2><br>");
    out.println("<p align=\"center\">");
    out.println("<input type=\"button\" onClick='window.close()' value=\"chiudi\" >");
    out.println("</p></body>");
    out.println("</html>");
    out.close(); 
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);
  }
}
