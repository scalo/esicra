<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<hib:session>
<hib:find var="rappresentante"  maxResults="100">
  from AnaRappresentante
  where fkid_soggetto=${param.pkid} 
</hib:find>

<table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="HelpRigaTitolo">
        <b>&nbsp;&nbsp;Rappresentante: </b>
      </td>
    </tr>
</table>
<br/>

<c:set var="soggetto" value="${rappresentante[0].anaRappresentante}" />

<c:choose>
<c:when test="${empty soggetto}" >
<table border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile">
    <tr>
        <td>
            Descrizione:
            <b><c:out value="${rappresentante[0].desRappresentante}" /></b>
        </td>
    </tr>
    <tr>
        <td>
            Titolo:
            <b><c:out value="${rappresentante[0].desTitoloRapp}" /></b>
        </td>
    </tr>
    <tr>
        <td>
            Codice Fiscale:
            <b><c:out value="${rappresentante[0].codiceFiscale}" /></b>
        </td>
    </tr>
    <tr>
        <td>
            Cognome:
            <b><c:out value="${rappresentante[0].cognome}" /></b>
        </td>
        <td>
            Nome:
            <b><c:out value="${rappresentante[0].nome}" /></b>
        </td>
    </tr>
    <tr>
        <td>
            Data di Nascita:
            <b><fmt:formatDate value="${rappresentante[0].dtNascita}" pattern="dd/MM/yyyy" /></b>
        </td>
        <td>
            Località di Nascita:
            <b><c:out value="${rappresentante[0].locNascita}" /></b>
        </td>
    </tr>
</table>
</c:when>
<c:otherwise>
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
            <b><script type="text/javascript" >decodeSesso(<c:out value="${soggetto.sesso}" />)</script></b>
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
            Località di Nascita:
            <b><c:out value="${localitaNascita}" /></b>
        </td>
    </tr>
  </table>
</c:otherwise>
</c:choose>
</hib:session>
