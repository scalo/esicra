<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<!--
<c:out value="${param.pkid}" />
<c:out value="${param.codfiscale}" />
-->
  
</script>

<hib:find var="indirizzi"  maxResults="100">
  from OtIndirizzo 
  where fkid_ot = ${param.pkid}
</hib:find>

<c:forEach var="inusato" items="${indirizzi}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:if test="${count gt 0}">
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="0" cellpadding="0">
    <tr>
        <td >
           <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" class="HelpRigaTitolo">
                <b>&nbsp;&nbsp;Ubicazione: </b>
                <p/>
              </td>
            </tr>
				    <tr>
				  	<td>
                <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                   <tr class = "HelpRigaSezione" >
                       <td colspan = "10" height="2"></td>
                   </tr>
                   <tr class="PrimaRiga">
                        <td width ="45%" align="left">Area Circolazione</td>
                        <td width ="6%" align="left">Num. Civ.</td>
                        <td width ="6%" align="left">Let. Civ.
                        <td width ="6%" align="left">Colore
                        <td width ="6%" align="left">Corte
                        <td width ="6%" align="left">Scala
                        <td width ="6%" align="left">Interno
                        <td width ="6%" align="left">Piano
                        <td width ="6%" align="left">Edificio
                        <td width ="6%" align="left">Residenti                          
                    </tr>
                    <tr class = "HelpRigaSezione" >
                        <td colspan = "10" height="1"></td>
                    </tr>
                </table>
	                </td>
                  </tr>
                  <tr>
                      <td>
                      <div style="overflow:auto; height:55px">
			                    <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width="100%" >
                              <c:forEach var="indirizzo" items="${indirizzi}" varStatus="status">
                                <!-- 
                                  mette nel filtro
                                  area, civico e lettera
                                  
                                  AND numCiv=${indirizzo.numCiv} AND letCiv LIKE UPPER('${indirizzo.letCiv}') 
                                  
                                -->
                                <c:set var="filtro" value="area=${indirizzo.desArea}&civico=${indirizzo.numCiv}&lettera=${indirizzo.letCiv}" />

                                <tr class="<c:out value="riga${status.count %2}" />" >
                                  <td width ="45%" align="left"><c:out value="${indirizzo.desArea}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.numCiv}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.letCiv}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.colore}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.corte}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.scala}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.interno}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.piano}" /></td>
                                  <td width ="6%" align="left"><c:out value="${indirizzo.edificio}" /></td>
                                  <td width ="6%"><a href='ListaSoggettiIndirizzo.jsp?<c:out value="${filtro}" />' >Sel.</a></td>
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
