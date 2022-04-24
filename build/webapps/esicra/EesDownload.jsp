<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.timer.servlet.EesHtmlUtils" %>
<%@ page import="it.saga.egov.esicra.*" %>

<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">
<title>Menu Amministrazione  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>
<script language="JavaScript">

	function checkUrl(url){
		w = window.open(url,url,'width=200,height=200');	
	}

</script>

</head>
<body>
<!--style type="text/css" src="timer.css">
</style-->
<%
	String pat_host=System.getProperty("esicra.pat.host");
	String pat_port=System.getProperty("esicra.pat.port");
	String pat_url_test="http://"+pat_host+":"+pat_port+"/esicra/servlet/soaprouter";
%>

<script type="text/javascript" language="javascript" src="addtask.js" ></script>
<!--
<h1 align=center >Menu Amministrazione</h1><br/>
-->

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" align="absmiddle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>

<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" > 
    <TD>&nbsp;&nbsp;Area Download</TD>
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
      <a class="Menu" href="EesEsportazioni.jsp">Download Esportazioni </a>
    </td>
  </tr>
  </tr>
  
  <tr><td><br></td></tr>
  <tr>
    <td class="box" align="center">
      <a class="Menu" href="EesAllegati.jsp">Download Allegati</a>
    </td>
  </tr>
  <tr><td><br></td></tr>
</table>

<br>
<!--/form-->
</body>
</html>

