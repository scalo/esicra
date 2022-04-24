<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ page import="it.saga.siscotel.db.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>E-Sicra - Consultazione Indice Anagrafe Estesa</TITLE>
<script type="text/javascript" src="decode.js" ></script>
</head>
<body>

<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp |Ricerca Oggetti per Indirizzo ,IndiceRicercaOggettoInd.jsp |Ricerca Oggetti Identificativo ,IndiceRicercaOggettoIde.jsp" />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Soggetti Residenti" />
  <c:param name="titolo_contenuto" value="Ricerca Soggetti Residenti" />
</c:import>

<br>

<hib:find var="soggetti"  maxResults="100">
  from AnaSoggetto sog join fetch  sog.anaIndirizzos as indirizzo
  where UPPER(indirizzo.desArea) LIKE  UPPER(?) AND  indirizzo.numCiv = ? AND indirizzo.dtMod is not null
  <c:if test="param.letCiv">AND UPPER(indirizzo.letCiv) LIKE UPPER(?)</c:if>
  <hib:param value="${param.area}" />
  <hib:param value="${param.civico}" />
  <c:if test="param.letCiv">
    <hib:param value="${param.lettera}" />
  </c:if>
</hib:find>


<c:forEach var="inusato" items="${soggetti}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:choose>
<c:when test="${count gt 0 }" >
<table  width = "100%" border = "0" align = "center" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center">
           <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                   <b>Soggetti Trovati: <c:out value="${count}"/></b>
                   <br/>
                   <br/>
              </td>
            </tr>
				    <tr>
				  	<td>
                <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                   <tr class = "HelpRigaSezione" >
                       <td colspan = "9" height="2"></td>
                   </tr>
                   <tr class="PrimaRiga">
                        <td width ="3%">&nbsp;</td>
                        <td width ="15%" align="left">Codice Fiscale</td>
                        <td width ="12%" align="left">Cognome</td>
                        <td width ="10%" align="left">Nome</td>
                        <td width ="4%" align="left">Sesso</td>   
                        <td width ="8%" align="left">Data Nascita</td>
                        <td width ="24%" align="left">Localita Nascita</td>                                        
                        <td width ="24%" align="left">Indirizzo</td>                                                                                          
                    </tr>
                    <tr class = "HelpRigaSezione" >
                        <td colspan = "9" height="1"></td>
                    </tr>
                </table>
	                </td>
                  </tr>
                  <tr>
                      <td>
                      <div style="overflow:auto; height:250px">
			                    <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                              <c:forEach var="soggetto" items="${soggetti}" varStatus="status">
                                <tr class='<c:out value="riga${status.count %2}" />'  >
                                  <!-- IndiceDettaglioSoggetto pkid , cfiscale sono indicati tutti ... -->
                                  <td width ="3%" align="left"><a href='DettaglioSoggettoFisico.jsp?pkid=<c:out value="${soggetto.pkid}"/>&codfiscale=<c:out value="${soggetto.codiceFiscale}" />'>Sel.</a></td>
                                  <td width ="15%" align="left"><c:out value="${soggetto.codiceFiscale}" /></td>
                                  <td width ="12%" align="left"><c:out value="${soggetto.cognome}" /></td>
                                  <td width ="10%" align="left"><c:out value="${soggetto.nome}" /></td>
                                  <td width ="4% " align="left"><script type="text/javascript" >decodeSesso('<c:out value="${soggetto.sesso}" />')</script></td>   
                                  <td width ="8%" align="left"><fmt:formatDate value="${soggetto.dtNascita}" pattern="dd/MM/yyyy" /></td>
                                  <td width ="24%" align="left"><c:out value="${soggetto.desComuneNascita}${soggetto.desLocalitaNascita} (${soggetto.desProvinciaNascita}${soggetto.desStatoNascita})" /></td>                        
                                  <td width ="24% " align="left">
                                    <!-- solo il primo elemento  end=1 -->
                                    <c:forEach var="indirizzo" items="${soggetto.anaIndirizzos}" end="1" >
                                      <c:out value="${indirizzo.desArea}"/>,<c:out value="${indirizzo.numCiv}"/> <c:out value="${indirizzo.letCiv}" />
                                    </c:forEach>
                                  </td>
                                </tr>
                              </c:forEach>
                          </table>     
                      </div>
                      </td>
                  </tr>
             </table>
        </td>
    </tr>
</table>
</c:when>
<c:otherwise>
   <b>NESSUN SOGGETTO RESIDENTE TROVATO</b>
</c:otherwise>
</c:choose>

</body>
</html>
