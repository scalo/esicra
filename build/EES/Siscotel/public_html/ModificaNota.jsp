<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <link rel="STYLESHEET" type="text/css" href="siscotel.css"></link>
    <script type="text/javascript" src="decode.js" ></script>
    <title>
      Modifica pratica
    </title>
  </head>
  
  <body>
  <hib:session>
  
  <c:import url="barramenu.jsp">
    <c:param name="menu"  value="|Ricerca Pratiche,RicercaPratiche.jsp,myimg/cartella.jpg|"/>
  </c:import>
  
  <c:import url="header.jsp">
    <c:param name="titolo_pagina"
             value="Gestione Pratica"/>
    <c:param name="titolo_contenuto"
             value="Pratica: ${param.testa_pkid}"/>
  </c:import>

<hib:load var="dettaglio" classname="it.saga.siscotel.db.PraPraticaDett" value="${param.dett_pkid}" />

<c:choose>
  <c:when test='${param.modifica == "modifica"}' >
    <!-- modifica solo le note ! -->
    <jsp:useBean id="today" class="java.util.Date" />
    <c:set target="${dettaglio}" property="note" value="${param.note}" />
    <c:set target="${dettaglio}" property="desResponsabile" value="${param.desResponsabile}" />
    <hib:save target="${dettaglio}" />
    <c:redirect url="PraticaEdit.jsp?pkid=${param.testa_pkid}" />
  </c:when>
</c:choose>

<form name="frm" action="ModificaNota.jsp" method="post" >
<br>
<input type="hidden"  name="testa_pkid" value='<c:out value="${param.testa_pkid}"/>' />
<input type="hidden"  name="dett_pkid" value='<c:out value="${param.dett_pkid}"/>' />

<c:set var="stato"  value="${dettaglio.idStatoPratica}"/>

<table class = "LabelForm" cellspacing="5" cellpadding="5">
  <tr>
    <!--
     <td colspan="2">Id Responsabile:&nbsp;<b><c:out value="${dettaglio.idResponsabile}"/></b></td> 
    -->
     <td>Responsabile:</td>
     <td>
      <input type="text" size="30" maxlength="80" name="desResponsabile" value="<c:out value='${dettaglio.desResponsabile}' />" class="campi"/>
    </td>
  </tr>
    <tr>
     <td colspan="2">Id Pratica:&nbsp;<c:out value="${param.testa_pkid}"/></td> 
     <td colspan="2">Id Dettaglio:&nbsp;<c:out value="${param.dett_pkid}"/></td> 
  </tr> 
  <tr>
    <td>Stato Pratica&nbsp;</td>
    <td>
      <b><INPUT size="40" disabled type="text" value="<c:out value='${dettaglio.desStatoPratica}' />" class ="campi">
      </b>
    (<c:out value="${dettaglio.idStatoPratica}"/>)
    </td>   
  </tr> 
  <tr>
    <td>Data&nbsp;</td>
    <td>
      <b><INPUT size="12" disabled type="text" name="DtStato" value='<fmt:formatDate value="${dettaglio.dtStato}" pattern="dd/MM/yyyy" />' class ="campi"> </b></td> 
    </td>
    <td>&nbsp;</td>
  </tr>
  <tr>
   <td>Note&nbsp;</td>
   <td colspan="2"> <textarea  name="note" cols="80" rows="10" class = "campi" ><c:out value="${dettaglio.note}"/></textarea></td>
  </tr> 
</table>

<br>
<br>

<input type="submit" name="modifica" value="modifica"  class = "pulsante" />

</form>
</hib:session>
</body>
</html>
