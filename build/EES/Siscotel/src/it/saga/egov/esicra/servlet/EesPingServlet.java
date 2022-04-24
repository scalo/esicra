package it.saga.egov.esicra.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

public class EesPingServlet extends HttpServlet  {
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
    String ping_url=request.getParameter("url");
    URL url=null;
    if(ping_url!=null){
      url = new URL(ping_url);
    }else
      return;
    try{  
      URLConnection uc = (URLConnection) url.openConnection();
      uc.setDoOutput(true);
      uc.setUseCaches(false);
      uc.setDoInput(true);
      InputStream in = uc.getInputStream();
      OutputStream out = response.getOutputStream();
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
    }catch(Exception e){
      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<body onLoad='window.focus()' >");
      out.println("<h2 align=\"center\">");
      out.println("Host remoto non trovato !");
      out.println("</h2>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request,response);
  }
}