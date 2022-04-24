<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
  
<script type="text/javascript" src="decode.js" ></script>

<hib:find var="identificativi"  maxResults="100">
  from OtIdentificativo 
  where fkid_ot = ${param.pkid}
</hib:find>

<c:forEach var="inusato" items="${identificativi}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:if test="${count gt 0}">
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="0" cellpadding="0">
    <tr>
        <td >
           <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" class="HelpRigaTitolo">
                <b>&nbsp;&nbsp;Identificativi Catastali: </b>
                <p/>
              </td>
            </tr>
				    <tr>
				  	<td>
                <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                   <tr class = "HelpRigaSezione" >
                       <td colspan = "8" height="2"></td>
                   </tr>
                   <tr class="PrimaRiga">
                        <td width ="20%" align="left">Catasto</td>
                        <td width ="20%" align="left">Tipo</td>
                        <td width ="10%" align="left">Sezione
                        <td width ="10%" align="left">Foglio
                        <td width ="10%" align="left">Mappale
                        <td width ="10%" align="left">Subalterno
                        <td width ="10%" align="left">Anno Prot.
                        <td width ="10%" align="left">Numero Prot.                              
                    </tr>
                    <tr class = "HelpRigaSezione" >
                        <td colspan = "8" height="1"></td>
                    </tr>
                </table>
	                </td>
                  </tr>
                  <tr>
                      <td>
                      <div style="overflow:auto; height:55px">
			                    <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width="100%" >
                              <c:forEach var="identificativo" items="${identificativi}" varStatus="status">
                                <tr class="<c:out value="riga${status.count %2}" />" >
                                  <td width ="20%" align="left"><c:out value="${identificativo.desIdentificativo}" /></td>
                                  <td width ="20%" align="left"><script type="text/javascript">decodeImmobile('<c:out value="${identificativo.tipoCatasto}" />')</script></td>
                                  <td width ="10%" align="left"><c:out value="${identificativo.sezione}" /></td>
                                  <td width ="10%" align="left"><c:out value="${identificativo.foglio}" /></td>
                                  <td width ="10%" align="left"><c:out value="${identificativo.mappale}" /></td>
                                  <td width ="10%" align="left"><c:out value="${identificativo.sub}" /></td>
                                  <td width ="10%" align="left"><c:out value="${identificativo.protAnno}" /></td>
                                  <td width ="10%" align="left"><c:out value="${identificativo.protNum}" /></td>
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

