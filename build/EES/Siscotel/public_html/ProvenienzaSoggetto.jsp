<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<!--
<c:out value="${param.pkid}" />
<c:out value="${param.codfiscale}" />
-->
  
</script>

<hib:find var="provenienze"  maxResults="100">
  from VSoggettoProvenienza s
  where (UPPER(codice_soggetto)=UPPER('${param.codfiscale}') and sogg_pkid=${param.pkid} )
  order by id_ente, cod_provenienza
</hib:find>

<c:forEach var="inusato" items="${provenienze}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:if test="${count gt 0}">
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="0" cellpadding="0">
    <tr>
        <td >
           <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" class="HelpRigaTitolo">
                <b>&nbsp;&nbsp;Provenienza: </b>
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
                        <td width ="20%" align="left">Ente</td>
                        <td width ="20%" align="left">Cod. Provenienza</td>
                        <td width ="60%" align="left">Des. Provenienza</td>                               
                    </tr>
                    <tr class = "HelpRigaSezione" >
                        <td colspan = "9" height="1"></td>
                    </tr>
                </table>
	                </td>
                  </tr>
                  <tr>
                      <td>
                      <div style="overflow:auto; height:150px">
			                    <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width="100%" >
                              <c:forEach var="provenienza" items="${provenienze}" varStatus="status">
                                <tr class="<c:out value="riga${status.count %2}" />" >
                                <td width ="20%" align="left"><c:out value="${provenienza.idEnte}" /></td>
                                <td width ="20%" align="left"><c:out value="${provenienza.codProvenienza}" /></td>
                                <td width ="60%" align="left"><c:out value="${provenienza.desProvenienza}" /></td>
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
</c:if>