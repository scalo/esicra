package it.saga.egov.esicra.timer.servlet;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import org.apache.log4j.*;

public class EesTimerConfServlet extends HttpServlet  {

  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    // presuppone che sia Esicra base sia configurato
    // EesTimerConf.configura();
    Logger logger = EsicraConf.getLogger(this.getClass());
    logger.info("EesTimerConf configurato");
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //nop
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //nop
  }
}
