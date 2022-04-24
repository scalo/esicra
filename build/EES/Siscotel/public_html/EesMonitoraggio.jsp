<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.timer.servlet.EesHtmlUtils" %>
<%@ page import="it.saga.egov.esicra.*" %>

<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">
<title>Area Monitoraggio<%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>

</head>
<body>

<%	
	String pat_host=System.getProperty("esicra.pat.host");
	String pat_port=System.getProperty("esicra.pat.port");
	String pat_url_test="http://"+pat_host+":"+pat_port+"/esicra/EesPongServlet";
%>

<script type="text/javascript" language="javascript" src="addtask.js" ></script>
<!--
<h1 align=center >Menu Amministrazione</h1><br/>
-->

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" align="middle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>

<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="1" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" > 
    <TD>&nbsp;&nbsp;Area Monitoraggio </TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" >
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>

<br>
<br>
<table align="center" >
  <tr><td><br></td></tr>
  <tr>
    <td class="box" align="center">
      <a class="Menu" href="monitoraggio/InfoSistema.jsp">Informazioni Sistema</a></td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="monitoraggio/eTransazioni.jsp">Elenco Transazioni</a> </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="monitoraggio/eImportazioni.jsp">Stato Importazione Flussi</a> </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <!--
  <tr>
    <td align="center" class="box"><a class="Menu" href="monitoraggio/InfoDb.jsp">Informazioni Database</a></td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  -->
</table>

<br>
<!--
<div align="center">
<input type="button" value="Test Pat" onclick="checkUrl('<%="EesPingServlet?url="+pat_url_test%>')" class="pulsante">
</div>
-->
<!--/form-->
</body>
</html>

