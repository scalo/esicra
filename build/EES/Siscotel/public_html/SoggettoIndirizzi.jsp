<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<!--
<c:out value="${param.pkid}" />
-->

<script type="text/javascript">

  function aot(indirizzo,civico,lettera){
    var uri="IndiceRicercaOggettoInd?";
    whereclause="";
    if(indirizzo != null){
      whereclause = whereclause+" AND UPPER(area_indirizzo) LIKE UPPER('*"+indirizzo+"*')";
    }
    if(civico!= null){
      whereclause = whereclause+" AND numero_civico="+civico;
    }
    if(lettera != null){
      whereclause = whereclause+" AND UPPER(lettera_civico) LIKE UPPER('*"+lettera+"*')";
    }
    if(whereclause != "") {
          whereclause = whereclause.substring(5);
    }
    //alert(whereclause);
    document.location.href="IndiceRicercaOggettoInd.jsp?whereclause="+whereclause;
    
  }
  
</script>

<hib:find var="indirizzi"  maxResults="100">
  from VIndirizzoCorrente
  where fkid_soggetto=${param.pkid} 
</hib:find>

<c:forEach var="inusato" items="${indirizzi}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:if test="${count gt 0}" >
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" >
           <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
            <tr>
              <td class="HelpRigaTitolo">
                <b>&nbsp;&nbsp;Indirizzi: </b>
                <br/>
                <br/>
              </td>
            </tr>
				    <tr>
				  	<td>
                <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                   <tr class = "HelpRigaSezione" >
                       <td colspan = "12" height="2"></td>
                   </tr>
                   <tr class="PrimaRiga">
                        <td width ="3% " align="left">Tipo</td>
                        <td width ="22%" align="left">Area Circolazione</td>
                        <td width ="6% " align="left">Num. Civ.</td>
                        <td width ="6% " align="left">Let. Civ.</td>
                        <td width ="6% " align="left">Colore</td>
                        <td width ="6% " align="left">Corte</td>
                        <td width ="6% " align="left">Scala</td>
                        <td width ="6% " align="left">Interno</td>
                        <td width ="6% " align="left">Piano</td>
                        <td width ="6% " align="left">Edificio</td>
                        <td width ="25%" align="left">Localita</td>
                        <td width ="2% " align="left"></td>                        
                    </tr>
                    <tr class = "HelpRigaSezione" >
                        <td colspan = "12" height="2"></td>
                    </tr>
                </table>
	                </td>
                  </tr>
                  <tr>
                      <td>
                      <div style="overflow:auto; height:55px">
			                    <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                              <c:forEach var="indirizzo" items="${indirizzi}" varStatus="status">
                                <c:set var="localita" value="${indirizzo.desComune} ${indirizzo.desProvincia}" />
                                <tr>
                                  <td width ="3% " align="left"><c:out value="${indirizzo.tipo}" /></td>
                                  <td width ="22%" align="left"><c:out value="${indirizzo.desArea}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.numCiv}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.letCiv}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.colore}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.corte}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.scala}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.interno}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.piano}" /></td>
                                  <td width ="6% " align="left"><c:out value="${indirizzo.edificio}" /></td>
                                  <td width ="25%" align="left"><c:out value="${localita}" /></td>
                                  <td width ="2% " align="left">
                                    <a href='#' onClick="aot('<c:out value="${indirizzo.desArea}" />','<c:out value="${indirizzo.numCiv}" />','<c:out value="${indirizzo.letCiv}" />' );return false">AOT</a>
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
</c:if>
