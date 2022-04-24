<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <link rel="STYLESHEET" type="text/css" href="esicra.css"></link>
    <title>
      E-Sicra - Dettaglio Oggetto
    </title>
  </head>
  <body>
  
    <c:import url="barramenu.jsp">
      <c:param name="menu"
               value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp |Ricerca Oggetti per Indirizzo ,IndiceRicercaOggettoInd.jsp|Ricerca Oggetti per Identificativo ,IndiceRicercaOggettoIde.jsp|"/>
    </c:import>
    
    <c:import url="header.jsp">
      <c:param name="titolo_pagina"
               value="  Dettaglio Oggetto (pkid = ${param.pkid} )"/>
      <c:param name="titolo_contenuto"
               value="Consultazione Indice Oggetti Territoriali"/>
    </c:import>
    
    <br></br>
    <br></br>
    
    
    <c:import url="OggettoTerritoriale.jsp">
      <c:param name="pkid" value="${param.pkid}"/>
    </c:import>
    
    <br>
    <br>
    
    <c:import url="OggettoIndirizzo.jsp">
      <c:param name="pkid" value="${param.pkid}"/>
    </c:import>
    
    <c:import url="OggettoIdentificativi.jsp">
      <c:param name="pkid" value="${param.pkid}"/>
    </c:import>
    
    
    <c:import url="ProvenienzaOggetto.jsp">
      <c:param name="cod_aot" value="${param.pkid}"/>
    </c:import>
    
  </body>
  
</html>