package it.saga.egov.esicra.servlet;

import it.saga.egov.esicra.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import javax.naming.*;
import java.util.*;
import java.io.*;
import java.util.jar.*;

/**
 *  Servlet di Informazione del sistema

 */
public class InfoSistemaServlet extends HttpServlet  {

  private static final String CONTENT_TYPE = "text/html; charset=iso-8859-1";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    // non fa altro che richiamare il la classe  EsicraConf
    EsicraConf.configura();
  }
  
  /**
   *  Restituisce le informazioni memorizzate nel naming service jndi, nel contesto
   *  java:comp/env/esicra
   *  NB:
   *  Il contesto java:comp/env è il contesto generico definito dal servlet
   *  engine per gli oggetti condivisi dalle web applications
   *  Il comportamento del jndi differisce sostanzialmente tra tomcat e oc4j.
   *  Il promo crea nel contesto j2ee di default java:comp/env un sottocontesto
   *  jdbc , mentre il secondo no.
   *  Per tale motivo , mentre nel primo caso si accede alla lista degli oggetti
   *  del contesto tramite lookup al java:comp/env/jdbc , nel secondo caso ciò
   *  può essere fatto attraverso java:comp/env.
   *  
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    try{
      Hashtable env = new Hashtable();
      out.println("<HTML>");
      out.println("<head>");
      out.println("<link rel=stylesheet href=\"timer.css\" type=\"text/css\">");
      out.println("<title>Ees Timer Admin</title>");
      out.println("</head>");
      out.println("<BODY>");
      out.println("<H1 align=center>Elenco degli Oggetti registrati nel contesto JNDI e System Properties</H1>");
      String engine = System.getProperty("esicra.engine");
      out.println("<H3 align=center>["+engine+"]  "+new Date()+"</H3><BR/>");
      try {
        //request.getSession().getServletContext().get
        String filename = request.getSession().getServletContext().getRealPath(File.separator+"META-INF"+File.separator+"MANIFEST.MF");
        File fileManifest =  new File(filename);
        FileInputStream in = new FileInputStream(fileManifest);
        Manifest manifest = new Manifest(in);
        Attributes atts = manifest.getAttributes("esicra");
        //System.out.println("MANIFEST:"+atts);
        String version = atts.getValue("Implementation-Version");
        out.println("<H2 align=center >Versione:  "+version+"</H2></BR>");
        /*
        for (Iterator it=atts.keySet().iterator(); it.hasNext(); ) {
            Attributes.Name attrName = (Attributes.Name)it.next();
            String attrValue = atts.getValue(attrName);
            out.println("<H3 align=center >"+attrName+"="+attrValue+"</H3></BR>");
            
        }
        */
        in.close();
      } catch (IOException e) {
        System.out.println(e);
      }

      Context jdbcCtx = EsicraConf.getJdbcContext();
      out.println("<H3>Memoria in uso  = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024);
      out.println("<H3>Memoria Libera  = "+ Runtime.getRuntime().freeMemory()/1024 +"</H3>");
      out.println("<H3>Memoria Totale  = "+ Runtime.getRuntime().totalMemory()/1024+"</H3>");
      out.println("<H3>Threads attivi  = "+ Thread.activeCount()+"</H3>");
      out.println("<H2 align=center> java:comp/env </H2><BR>");
      
      if(jdbcCtx==null){
        out.println("<H3 align=center><I>contesto non trovato</I></H3><BR/>");
      }else{
        //contesto già esistente elenca gli oggetti.
        NamingEnumeration enume=jdbcCtx.list("");
        out.println("<OL>");
        while(enume.hasMore()){
          Object o = enume.next();
          out.println("<LI><B>");
          out.println(o);
          out.println("</LI></B>");
        }
        //out.println("<h4>"+cercaRisorsa(envCtx,"jdbc/Esicram_msql2000DS")+"</h4>");
        out.println("</OL>");
        enume.close();
      }
      
      Context esicraCtx = EsicraConf.getInitialContext(EsicraConf.PATH);
      if(esicraCtx==null){
        //contesto non trovato
      }else{
        //contesto già esistente elenca gli oggetti.
        //ut.println("<H2 align=center>Elenco degli Oggetti registrati nel contesto JNDI </H2>");
        //out.println("<H5 align=center>"+new Date()+"</H5><BR/>");
        out.println("<H2 align=center> java:comp/env/esicra </H2><BR>");
        NamingEnumeration enume=esicraCtx.list("");
        out.println("<OL>");
        while(enume.hasMore()){
          Object o = enume.next();
          out.println("<LI><B>");
          out.println(o);
          out.println("</LI></B>");
        }
        out.println("</OL>");
        enume.close();
      }
      out.println("<H2 align=center>Elenco System Properties</H2>");
      Properties props = System.getProperties();
      SortedSet ss = new TreeSet(props.keySet());
      Iterator ite = ss.iterator();
      out.println("<OL>");
      while(ite.hasNext()){
        String key = (String) ite.next();
        String val = (String) props.getProperty(key);
        out.println("<LI><B>");
        out.println(key+" = "+val);
        out.println("</LI></B>");
      }
      out.println("</OL>");
    }catch(NamingException ne){
      out.println("<H2 align=center> Errore Naming </H2>");
      out.println(ne.toString());
    }catch(Exception e){
      out.println("<H2 align=center> Errore </H2>");
      out.println(e.toString());
    }finally{
                   
      out.println("<table>" +
                  " <tr><td><br></td></tr>"+
                  " <tr>"+
                  "   <td>"+
                  "    <a href=\"EesMenu.jsp\">Ritorna al Menu </a>"+
                  "   </td>"+ 
                  " </tr>"+  
                  "</table>"+
                  "</BODY>");
      out.println("</HTML>");
      
    }
    out.close();
  }
  
  /**
   *  Metodo statico per evitare di utilizzare try/catch annidati
   *  
   *  @param    ctx contesto in cui cercare la risorsa
   *  @param    nome  nome della risorsa
   *  @return   Oggetto risorsa oppure null se non trovata
   *
   */
  synchronized private static Object cercaRisorsa(Context ctx ,String nome ) {
    Object o = null;
    try{
      return ctx.lookup(nome);
    }catch(NamingException e){
      return o;
    }
  }
  
}

