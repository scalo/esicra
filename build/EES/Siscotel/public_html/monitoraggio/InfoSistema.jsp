<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"
import="
it.saga.egov.esicra.*,
javax.naming.*,
java.util.*,
java.util.jar.*,
java.io.*
"
%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>


<!--
  TODO 
  
    mettere a posto le connessioni e nomi db
    
-->

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../siscotel.css">
<TITLE>Transazioni</TITLE>
</head>
<body class = "body">

<!-- barra menu -->
<c:import url="../barramenu.jsp">
    <c:param name="menu"
         value="|Menù Principale, ../EesMenu.jsp, ../myimg/home.jpg |Area Monitoraggio,../EesMonitoraggio.jsp|"/>
</c:import>
    
<c:import url="../header.jsp">
  <c:param name="titolo_pagina"
           value="  Monitoraggio"/>
  <c:param name="titolo_contenuto"
           value="Informazioni Sistema"/>
</c:import>

<hib:session>
<hib:load 
  var="info"
  classname="it.saga.siscotel.db.SerEsicraInfo"
  value="esicra"
/>
<H2 align=center>Elenco degli Oggetti registrati nel contesto JNDI e System Properties</H2>

<%
  String engine = System.getProperty("esicra.engine");
  out.println("<H3 align=center>["+engine+"]  "+new Date()+"</H3><BR/>");
%>

<h3>Informazioni Database</h3>
<b>nome: </b><c:out value="${info.nomedb}"/><br>
<b>versione: </b><c:out value="${info.versione}"/><br>
<b>engine: </b><c:out value="${info.dbEngine}"/><br>
<br>
<%
try{
      Hashtable env = new Hashtable();
      
      /*
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
        in.close();
      } catch (IOException e) {
      System.out.println(e);
      }
      */
      out.println("<H3>Memoria in uso  = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024);
      out.println("<H3>Memoria Libera  = "+ Runtime.getRuntime().freeMemory()/1024 +"</H3>");
      out.println("<H3>Memoria Totale  = "+ Runtime.getRuntime().totalMemory()/1024+"</H3>");
      out.println("<H3>Threads attivi  = "+ Thread.activeCount()+"</H3>");
      out.println("<H2 align=center> java:comp/env </H2><BR>");
      
      Context jdbcCtx = EsicraConf.getJdbcContext();
      
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
        if(key!=null&&(key.toLowerCase().endsWith("password")))
          val="*********"; 
        out.println(key+" = "+val);
        out.println("</LI></B>");
      }
      out.println("</OL>");
      
} catch (Exception e) {
        System.out.println(e);
}

%>

</hib:session>

</body>
</html>

