<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<hib:session>

<hib:load var="oggetto" classname="it.saga.siscotel.db.VIndiceOggettoIde" value="${param.pkid}" />


<table border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile">
    <tr>
        <td>
            Codice Oggetto:
            <b><c:out value="${oggetto.codAot}"/></b>
        </td>
        <td>
            Codice Ecografico:
            <b><c:out value="${oggetto.codiceEcografico}" /></b>
        </td>
    </tr>
    <tr>
        <td>
            Codice Catasto:
            <b><c:out value="${oggetto.codCatasto}" /></b>
        </td>
        <td>
            Codice Istat Comune:
            <b><c:out value="${oggetto.aotCodIstat}" /></b>
        </td>
    </tr>
</table>

</hib:session>
