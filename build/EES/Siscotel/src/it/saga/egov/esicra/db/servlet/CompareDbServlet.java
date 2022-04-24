package it.saga.egov.esicra.db.servlet;

import it.saga.egov.esicra.db.*;
import javax.servlet.*;
import org.apache.log4j.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

public class CompareDbServlet extends HttpServlet  {

    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    private Logger logger;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger = Logger.getLogger(this.getClass());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dbPage="CompareDbJdbc.jsp";
        // variabili
        String jdbc="";
        String db="";
        String user="";
        String password="";
        String schema="";
        // azione
        String azione=request.getParameter("azione");
        if (azione!=null){
          if(azione.equalsIgnoreCase("connetti")){
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            jdbc=request.getParameter("jdbc");
            db=request.getParameter("db");
            user=request.getParameter("user");
            password=request.getParameter("password");
            schema=request.getParameter("schema");
            if(schema!= null && schema.length()==0) schema=null;
            logger.debug("azione="+azione);
            logger.debug("jdbc="+jdbc);
            logger.debug("db="+db);
            logger.debug("user="+azione);
            logger.debug("password="+password);
            logger.debug("schema="+schema);
            Database database= new Database(jdbc,schema,user,password);
            logger.debug("database="+database);
            request.getSession().setAttribute(db,database);
            out.println("<html>");
            out.println("<body onLoad='window.close();window.opener.location.reload();' >");
            out.println("<h2 align=\"center\">");
            out.println("Pat not found !");
            out.println("</h2>");
            out.println("</body>");
            out.println("</html>");
          }else
          if(azione.equalsIgnoreCase("disconnetti")){
            db=request.getParameter("db");
            request.getSession().removeAttribute(db);
            response.sendRedirect(dbPage);
          }else
          if(azione.equalsIgnoreCase("pulisci")){
            request.getSession().removeAttribute("db1");
            request.getSession().removeAttribute("db2");
            response.sendRedirect(dbPage);
          }else
          if(azione.equalsIgnoreCase("confronta")){
            Database db1= (Database) request.getSession().getAttribute("db1");
            Database db2= (Database) request.getSession().getAttribute("db2");
            System.out.println("db1:"+db1.getSchema());
            System.out.println("db2:"+db2.getSchema());
            if(db1!=null&&db2!=null){
              db1.compareTo(db2);
            }
            response.sendRedirect(dbPage);
          }else
          if(azione.equalsIgnoreCase("genera")){
            db=request.getParameter("db");
            schema=request.getParameter("schema");
            logger.debug("azione="+azione);
            logger.debug("db="+db);
            logger.debug("schema="+schema);
            if(db!=null&&schema!=null){
              Database database= (Database) request.getSession().getAttribute(db);
              database.setSchema(schema);
            }
            response.sendRedirect(dbPage);
          }
        }
    }

}
