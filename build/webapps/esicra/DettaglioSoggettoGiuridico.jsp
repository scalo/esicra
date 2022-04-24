<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="esicra.css">
<script type="text/javascript" language="javascript" src="decode.js" >
</script>
<title>
Dettaglio Soggetto Giuridico
</title>
</head>
<body>

<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp |Ricerca Soggetti,Soggetti.jsp|Indice Soggetti Giuridici,IndiceRicercaSoggettoGiur.jsp|" />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Dati Anagrafici (Cod. = ${param.piva})" />
  <c:param name="titolo_contenuto" value="Consultazione Dati Soggetto Giuridico" />
</c:import>

<c:import url="AnagraficaSoggettoGiuridico.jsp">
    <c:param name="pkid" value="${param.pkid}" />
</c:import>

<br>
<br>

<c:import url="SoggettoIndirizzi.jsp">
    <c:param name="pkid" value="${param.pkid}" />
</c:import>

<br>

<c:import url="Rappresentante.jsp">
      <c:param name="pkid" value="${param.pkid}" />
</c:import>
<br>

<c:import url="ProvenienzaSoggetto.jsp">
    <c:param name="cfiscale" value="${param.cfiscale}" />
</c:import>

</body>
</html>
