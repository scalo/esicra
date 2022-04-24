<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" language="javascript" src="decode.js" >
</script>

<hib:find var="pratiche"  maxResults="250">
  from VPraticaCompleta where ${param.whereclause} order by dtPratica desc
</hib:find>

<c:forEach var="inusato" items="${pratiche}" varStatus="status">
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
                   <b>Pratiche Trovate: <c:out value="${count}"/></b>
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
                        <td width ="12%" align="left">Identificativo</td>
                        <!--
                        <td width ="8%"  align="left">Codice BO</td>
                        -->
                        <td width ="15%" align="left">Servizio</td>   
                        <td width ="8%"  align="left">Data</td>
                        <td width ="15%" align="left">Codice Fiscale</td>
                        <td width ="20%" align="left">Utente</td>                                        
                        <td width ="20%" align="left">Stato Pratica</td>                                          
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
                              <c:forEach var="pratica" items="${pratiche}" varStatus="status">
                                <tr class="<c:out value="riga${status.count %2}" />">
                                  <td width ="3%">
                                      <a href='PraticaEdit.jsp?pkid=<c:out value="${pratica.pkid}"/>' >Sel. </a>
                                  </td>
                                  <td width ="12%" align="left"><c:out value="${pratica.pkid}" /></td>
                                  <!--
                                  <td width ="8%"  align="left"><c:out value="${pratica.codPraticaBo}" /></td>
                                  -->
                                  <td width ="15%" align="left"><c:out value="${pratica.desBreve}" /></td>   
                                  <td width ="8%"  align="left"><fmt:formatDate value="${pratica.dtPratica}" pattern="dd/MM/yyyy" /></td>
                                  <td width ="15%" align="left"><c:out value="${pratica.codiceFiscale}" /></td>
                                  <td width ="20%" align="left"><c:out value="${pratica.oggetto}" /></td>
                                  <td width ="20%" align="left"><c:out value="${pratica.desStatoPratica}" /></td> 
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
  <br/><br/>
  <p align="center"><b>NESSUNA PRATICA SODDISFA I CRITERI DI RICERCA</b></p>
</c:otherwise>
</c:choose>

