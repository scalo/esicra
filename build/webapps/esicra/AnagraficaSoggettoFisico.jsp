<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<hib:session>
<hib:load var="soggetto" classname="it.saga.siscotel.db.VAnaSoggettoCorrente" value="${param.pkid}" />

<hib:find var="indirizzi"  maxResults="100">
  from VSoggettoIndirizzoCorrente
  where fkid_soggetto=${param.pkid} 
</hib:find>

<c:forEach var="inusato" items="${indirizzi}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:set var="localitaNascita" 
value="${soggetto.desComuneNascita} ${soggetto.desLocalitaNascita}( ${soggetto.desProvinciaNascita} ${soggetto.desStatoNascita} )" 
/>

<table border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile">
    <tr>
        <td>
            Codice Fiscale:
            <b><c:out value="${soggetto.codiceFiscale}"/></b>
        </td>
        <td>
            Sesso:
            <b><script type="text/javascript">decodeSesso(<c:out value="${soggetto.sesso}" />)</script></b>
        </td>
    </tr>
    <tr>
        <td>
            Cognome:
            <b><c:out value="${soggetto.cognome}" /></b>
        </td>
        <td>
            Nome:
            <b><c:out value="${soggetto.nome}" /></b>
        </td>
    </tr>
    <c:if test="${not empty soggetto.partitaIva }" >
    <tr>
      <td>
            Partita Iva:
            <b><c:out value="${soggetto.partitaIva}" /></b>
      </td>
    </tr>
    </c:if>
    <tr>
        <td>
            Data di Nascita:
            <b><fmt:formatDate value="${soggetto.dtNascita}" pattern="dd/MM/yyyy" /></b>
        </td>
        <td>
            Localita di Nascita:
            <b><c:out value="${localitaNascita}" /></b>
        </td>
    </tr>
<c:choose>
<c:when test="${count gt 0}" >
    <c:set var="indirizzoCorrente" 
      value="${indirizzi[0].desArea} ${indirizzi[0].numCiv} ${indirizzi[0].letCiv} ${indirizzi[0].edificio} ${indirizzi[0].scala}" 
    />
    <c:set var="localita" 
      value="${indirizzi[0].desComune} ${indirizzi[0].desProvincia} ${indirizzi[0].cap} ${indirizzi[0].desLocalita}" 
    /> 
    <tr>
        <td>
            Indirizzo:
            <b><c:out value="${indirizzoCorrente}" /></b>
        </td>
        <td>
            Localita:
            <b><c:out value="${localita}" /></b>
        </td>
    </tr>
</c:when>
<c:otherwise>
<tr align="center"><td colspan=2>
<br/>
<b>SOGGETTO NON RESIDENTE</b>
</td></tr>
</c:otherwise>
</c:choose>
</table>
</hib:session>
