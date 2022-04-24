<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.*" %>

<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">
<title>Menu Gestione Pratiche</title>

</head>
<body>


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
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" > 
    <TD>&nbsp;&nbsp;Menù Gestione Pratiche</TD>
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
    <td align="center" class="box">
      <a class="Menu" href="RicercaPratiche.jsp">Ricerca Pratiche</a>
    </td>
  </tr>
  <tr><td><br></td></tr>
  <tr>
    <td align="center" class="box">
      <a class="Menu" href="AggiornaPratiche.jsp">Cambio Stato Gruppo di Pratiche</a>
    </td>
  </tr>
  <tr><td><br></td></tr>
</table>

<br>

<!--/form-->
</body>
</html>
