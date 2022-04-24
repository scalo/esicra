<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" language="javascript" src="decode.js" >
</script>

<hib:find var="soggetti"  maxResults="100">
  from VAnaSoggettoCorrente
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
                <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "98%" >
                   <tr class = "HelpRigaSezione" >
                       <td colspan = "9" height="2"></td>
                   </tr>
                   <tr class="PrimaRiga">
                        <td width ="3%">&nbsp;</td>
                        <td width ="15%" align="left">PKID</td>
                        <td width ="5%"  align="left">Natura</td>
                        <td width ="12%" align="left">Cod. Fiscale</td>
                        <td width ="10%" align="left">P.IVA</td>
                        <td width ="20%" align="left">Cognome Nome/Denominazione</td>
                        <td width ="4%"  align="left">Sesso</td>
                        <td width ="8%"  align="left">Data Nascita</td>
                        <td width ="16%" align="left">Localita Nascita</td>                                        
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
                                  <c:if test="${soggetto.natura==0}">
                                    <a href='DettaglioSoggettoFisico.jsp?pkid=<c:out value="${soggetto.pkid}"/>&codfiscale=<c:out value="${soggetto.codiceFiscale}"/>' >Sel. </a>
                                  </c:if>
                                  <c:if test="${soggetto.natura==1}">
                                    <a href='DettaglioSoggettoGiuridico.jsp?pkid=<c:out value="${soggetto.pkid}&piva=${soggetto.partitaIva}"/>' >Sel. </a>
                                  </c:if>
                                </td>
                                <td width ="15%" align="left"><c:out value="${soggetto.pkid}" /></td>
                                <td width ="5% " align="left"><script type="text/javascript" >decodeNatura(<c:out value="${soggetto.natura}" />)</script></td>
                                <td width ="12%" align="left"><c:out value="${soggetto.codiceFiscale}" /></td>
                                <td width ="10%" align="left"><c:out value="${soggetto.partitaIva}" /></td>
                                <c:choose>
                                <c:when test="${ not empty soggetto.cognome}">
                                  <td width ="20%" align="left"><c:out value="${soggetto.cognome} ${soggetto.nome}" /></td>
  	                            </c:when>
                                <c:otherwise>
                                  <td width ="20%" align="left"><c:out value="${soggetto.denominazione}" /></td>
                                </c:otherwise>
                                </c:choose>
                                <td width ="4% " align="left"><script type="text/javascript" >decodeSesso(<c:out value="${soggetto.sesso}" />)</script></td>   
                                <td width ="8% " align="left"><fmt:formatDate value="${soggetto.dtNascita}" pattern="dd/MM/yyyy" /></td>
  	                            <td width ="16%" align="left">
                                  <c:if test="${not empty soggetto.dtNascita}">
                                    <c:out value="${soggetto.desComuneNascita}${soggetto.desLocalitaNascita} (${soggetto.desProvinciaNascita}${soggetto.desStatoNascita})" />
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
