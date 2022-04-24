<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.timer.servlet.EesHtmlUtils" %>
<%@ page import="it.saga.egov.esicra.*" %>

<html>
<head>
<link rel=stylesheet href="siscotel.css" type="text/css">
<title>EesMenu  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>
</head>
<body>


<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" ALT="myimg/home.jpg" align="middle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>

<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" > 
    <TD>&nbsp;&nbsp;Consultazione</TD>
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
		<a class="Menu" href="Soggetti.jsp">Soggetti Fisici e Giuridici</a>
    </td>
  </tr>
  <tr><td><br></td></tr>
<% if (System.getProperty("esicra.consulta_indici")!= null && System.getProperty("esicra.consulta_indici").equals("true")) {
%>
  <tr>
    <td align="center" class="box">
      <a class="Menu" href="IndiceRicercaSoggettoFis.jsp">Indice Soggetti Fisici</a>
    </td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="IndiceRicercaSoggettoGiur.jsp">Indice Soggetti Giuridici</a></td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="IndiceRicercaOggettoInd.jsp">Indice Oggetti Territoriali per Indirizzo</a></td>
  </tr>
  <tr>
    <td><br></td>
  </tr>
  <tr>
    <td align="center" class="box"><a class="Menu" href="IndiceRicercaOggettoIde.jsp">Indice Oggetti Territoriali per Identificativo</a></td>
  </tr>

  <tr><td><br></td></tr>
<%}%>
</table>

<!--/form-->
</body>
</html>

