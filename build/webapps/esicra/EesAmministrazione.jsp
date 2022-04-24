<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.timer.servlet.EesHtmlUtils" %>
<%@ page import="it.saga.egov.esicra.*" %>

<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">
<title>Menu Amministrazione  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>
<script language="JavaScript">
	// aggiungere titolo alla finestra
	function checkUrl(url){
		//alert(url);
		w = window.open(url,"PAT",'width=200,height=200');	
	}
	
</script>

</head>
<body>

<%	
	String pat_host=System.getProperty("esicra.pat.host");
	String pat_port=System.getProperty("esicra.pat.port");
	String pat_url_test="http://"+pat_host+":"+pat_port+"/esicra/EesPongServlet";
%>

<script type="text/javascript" language="javascript" src="addtask.js" ></script>

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
    <TD>&nbsp;&nbsp;Menù Amministrazione</TD>
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
  <tr>
    <td class="box" align="center">
      <a class="Menu" href="EesTimer.jsp">Schedulazione Task</a>    </td>
  </tr>
  <tr><td><br></td></tr>
  
  <tr>
    <td class="box" align="center">
      <a class="Menu" href="amministrazione/EesGestioneUtenti.jsp">Gestione Utenti</a>    </td>
  </tr>
  <tr><td><br></td></tr>
  <tr>
    <td class="box" align="center">
      <a class="Menu" href="amministrazione/EesGestioneGruppi.jsp">Gestione Gruppi</a>    </td>
  </tr>
  <tr><td><br></td></tr>
  
  <tr>
    <td class="box" align="center">
      <a class="Menu" href="EesViewLog.jsp">Amministrazione Log</a>
    </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="eCoordinator.jsp">Sincronizzazione Tabelle</a> </td>
  </tr>
  <!--
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td class="box" align="center"><a class="Menu" href="CompareDbJdbc.jsp">Confronto Strutture Db </a> </td>
  </tr>
  -->
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td class="box" align="center"><a class="Menu" href="EesAssistenzaRemota.jsp">Assistenza Remota</a> </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td class="box" align="center"><a class="Menu" href="" onclick="checkUrl('<%="EesPingServlet?url="+pat_url_test%>')" >Test di Connessione Locale</a> </td>
  </tr>
  <tr><td>&nbsp;</td></tr>
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

