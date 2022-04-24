<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<script type="text/javascript" language="javascript" src="decode.js" >
</script>

<hib:find var="soggetti"  maxResults="100">
  from VIndiceSogGiurInd
  where ${param.whereclause}
</hib:find>

<!--
  <c:out value="${param.whereclause}" />
-->

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
                        <td width ="15%" align="left">Partita Iva</td>
                        <td width ="22%" align="left">Ragione Sociale</td>
                        <td width ="45%" align="left">Indirizzo</td>                                           
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
                                <tr class="<c:out value="riga${status.count %2}" />">
                                  <td width ="3%">
                                      <a href='DettaglioSoggettoGiuridico.jsp?pkid=<c:out value="${soggetto.pkid}"/>&codfiscale=<c:out value="${soggetto.codiceFiscale}"/>&piva=<c:out value="${soggetto.partitaIva}"/>' >Sel. </a>
                                  </td>
                                  <td width ="15%" align="left"><c:out value="${soggetto.partitaIva}" /></td>
                                  <td width ="15%" align="left"><c:out value="${soggetto.codiceFiscale}" /></td>
                                  <td width ="22%" align="left"><c:out value="${soggetto.denominazione}" /></td>                  
                                  <td width ="45%" align="left">
                                    <c:out value="${soggetto.areaIndirizzo},${soggetto.numeroCivicoIndirizzo} ${soggetto.letteraCivicoIndirizzo}" />
                                    <c:if test="not empty ${soggetto.desComuneIndirizzo}">
                                      <c:out value="${soggetto.desComuneIndirizzo} (${soggetto.desProvinciaIndirizzo}) ${soggetto.capIndirizzo}" />
                                    </c:if>
                                    <c:if test="empty ${soggetto.desComuneIndirizzo}">
                                      <c:out value="${soggetto.desLocIndirizzo} (${soggetto.desConteaIndirizzo} ${soggetto.desStatoIndirizzo} ${soggetto.desConsolato} ) ${soggetto.capIndirizzo}" />
                                    </c:if>
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
   <b>NESSUN SOGGETTO TROVATO</b>
</c:otherwise>
</c:choose>
