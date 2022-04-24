<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.timer.servlet.EesHtmlUtils" %>
<%@ page import="it.saga.egov.esicra.*" %>

<html>
<head>
<link rel=stylesheet href="siscotel.css" type="text/css">
<title>EesMenu  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>
</head>
<%
  String logout = request.getParameter("logout");
  if(logout!=null){
    session.invalidate();
    response.sendRedirect ("EesMenu.jsp");
  }
  //String user = request.getUserPrincipal().getName();
%>
<body>

<script type="text/javascript" language="javascript" src="addtask.js" ></script>

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
        <td>&nbsp;</td>
        <td>|</td>
    		<td>
    			<a href="EesMenu.jsp?logout=yes">Logout</a>
    		</td>
    <td>|</td>         
    </tr>
</table>
<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" > 
    <TD>&nbsp;&nbsp;Menù Principale Siscotel</TD>
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
<table align=center >
	<tr><td><br></td></tr>  
  <tr >
    <td align="center" class="box"  >
		<a class="Menu" href="RicercaPratiche.jsp" style="font-size:16pt" >Gestione Pratiche</a>
    </td>
  </tr>
  <tr><td><br></td></tr>
  <%
    String consultazione = System.getProperty("esicra.consulta_indici");
    if (!(consultazione!= null && consultazione.equals("true"))) {
  %>
  <tr>
    <td align="center" class="box">
      <a class="Menu" href="EesDownload.jsp">Area Download</a>
    </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <%}%>
  <tr>
    <td align="center" class="box">
      <a class="Menu" href="EesConsultazione.jsp" style="font-size:13pt" >Area Consultazione</a>
    </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box">
      <a class="Menu" href="ProfilazioneServizi.jsp">Profilazione Servizi</a>
    </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box">
      <a class="Menu" href="EesAmministrazione.jsp">Area Amministrazione</a>
    </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="EesMonitoraggio.jsp">Area Monitoraggio </a></td>
  </tr>
  <tr><td><br></td></tr>
</table>

<br/>
<br/>
<br/>
<table align=center class="footer">
  <tr>
        <td align="center">
    	<b>Utente:</b> <%=request.getRemoteUser() %>,
    	</td>
   <%if(System.getProperty("esicra.nodo.host")!=null) { %>
    	<td align="center">
    	<b>Host:</b> <%=System.getProperty("esicra.nodo.host") %>, 
    	</td>
    <% } 
       if(System.getProperty("esicra.id_ente")!=null) { 
    %>
    	<td align="center">
    	<b>Codice Ente:</b> <%=System.getProperty("esicra.id_ente") %> 
    	</td>
    <% }
    	 if(System.getProperty("esicra.id_istat")!=null) { 
    %>
    	<td align="center">
    	<b>Codice Istat:</b> <%=System.getProperty("esicra.id_istat") %> 
    	</td>
    <% }	 
       if(System.getProperty("esicra.nodo.descrizione")!=null) { 
    %>
    	<td align="center">,
    	<b>Descrizione:</b> <%=System.getProperty("esicra.nodo.descrizione") %>. 
    	</td>
    <% } 
    %>
  </tr>
</table>

<!--/form-->
</body>
</html>

