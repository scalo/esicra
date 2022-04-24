<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>

<script type="text/javascript" language="javascript" src="decode.js">
</script>


<hib:find var="oggetti"  maxResults="100">
    from VIndiceOggettoIde where ${param.whereclause} and ideDtMod is not null
</hib:find>

<c:forEach var="inusato" items="${oggetti}" varStatus="status">
    <c:if test="${status.last}">
        <c:set var="count" value="${status.count}"/>
    </c:if>
</c:forEach>


<c:choose>
    <c:when test="${count gt 0 }">
        <table border="0" width="100%" align="center" cellspacing="0"
               cellpadding="0">
            <tr>
                <td>
                    <b>Oggetti Trovati: <c:out value="${count}"/></b>
                    <br/>
                    <br/>
                </td>
                <td>
                    <br></br>
                    <br></br>
                </td>
            </tr>
            <tr>
                <td>
                    <table class="LabelForm" cellspacing="0" cellpadding="0"
                           border="0" width="100%">
                        <tr class="HelpRigaSezione">
                            <td colspan="11" height="2"></td>
                        </tr>
                        <tr class="PrimaRiga">
                            <td width ="3%">&nbsp;</td>
                            <td width = "8%" align="left">Cod Aot</td>
                            <td width ="10%" align="left">Cod Ecografico</td>
                            <td width ="10%" align="left">Catasto</td>
                            <td width = "8%" align="left">Tipo</td>
                            <td width ="10%" align="left">Sezione</td>
                            <td width ="10%" align="left">Foglio</td>
                            <td width ="10%" align="left">Mappale</td>
                            <td width ="10%" align="left">Sub.</td>
                            <td width ="8%" align="left">Anno Prot.</td>
                            <td width ="10%" align="left">Num. Prot.</td>
                        </tr>
                        <tr class="HelpRigaSezione">
                            <td colspan="11" height="1"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <div style="overflow:auto; height:250px">
                        <table class="LabelForm" cellspacing="0" cellpadding="0"
                               border="0" width="100%">
                            <c:forEach var="oggetto" items="${oggetti}"
                                       varStatus="status">
                                <tr class='<c:out value="riga${status.count %2}" />'>
                                    <td width="3%" align="left">
                                        <!-- ricordarsi che la chiave della vista è composta -->
                                        <a href='IndiceDettaglioOggetto.jsp?pkid=<c:out value="${oggetto.aotPkid}" />' />
                                            Sel.
                                        </a>
                                    </td>
                                    <td width="8%" align="left">
                                        <c:out value="${oggetto.codAot}"/>
                                    </td>
                                    <td width="10%" align="left">
                                        <c:out value="${oggetto.codiceEcografico}"/>
                                    </td>
                                    <td width="10%" align="left">
                                        <c:out value="${oggetto.desIdentificativo}"/>
                                    </td>
                                    <td width="8%" align="left">
  
                                        <script type="text/javascript">decodeImmobile('<c:out value="${oggetto.tipoCatasto}"/>')</script>
                                    </td><td width="10%" align="left">
                                        <c:out value="${oggetto.sezione}"/>
                                    </td>
                                    </td><td width="10%" align="left">
                                        <c:out value="${oggetto.foglio}"/>
                                    </td>
                                    </td><td width="10%" align="left">
                                        <c:out value="${oggetto.mappale}"/>
                                    </td>
                                    </td><td width="10%" align="left">
                                        <c:out value="${oggetto.sub}"/>
                                    </td>
                                    </td><td width="8%" align="left">
                                        <c:out value="${oggetto.protAnno}"/>
                                    </td>
                                    </td><td width="10%" align="left">
                                        <c:out value="${oggetto.protNum}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <b>NESSUN OGGETTO TROVATO</b>
    </c:otherwise>
</c:choose>

