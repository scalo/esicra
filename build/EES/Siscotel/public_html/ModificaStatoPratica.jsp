<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
  
  function verificaForm(frm){
    val = frm.stato.value;
    //alert(val);
    if(val==-1){
      alert("Selezionare uno stato da assegnare alla pratica");
      return false;
    }
    // aggiorna stato
    frm.codStato.value=frm.stato.options[frm.stato.selectedIndex].value;
    frm.desStato.value=frm.stato.options[frm.stato.selectedIndex].text;
    return true;
  }

  function aggiorna_test(frm){
    frm.codStato.value=frm.stato.options[frm.stato.selectedIndex].value;
    frm.desStato.value=frm.stato.options[frm.stato.selectedIndex].text;
    alert(frm.codStato.value+" "+frm.desStato.value);
  }

</script>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <link rel="STYLESHEET" type="text/css" href="siscotel.css"></link>
    <script type="text/javascript" src="decode.js" ></script>
    <title>
      Modifica pratica
    </title>
  </head>
  
  <body>
  <hib:session>
  
  <c:import url="barramenu.jsp">
    <c:param name="menu"  value="|Indietro,PraticaEdit.jsp?pkid=${param.testa_pkid}|Ricerca Pratiche,RicercaPratiche.jsp,myimg/cartella.jpg|"/>
  </c:import>
  
  <c:import url="header.jsp">
    <c:param name="titolo_pagina"
             value="Gestione Pratica"/>
    <c:param name="titolo_contenuto"
             value="Pratica: ${param.testa_pkid}"/>
  </c:import>

<jsp:useBean id="today" class="java.util.Date" />

<hib:find var="stati" maxResults="100">  
    select stato,desStato from PraStato order by stato
</hib:find>

<hib:load var="testa" classname="it.saga.siscotel.db.PraPraticaTesta" value="${param.testa_pkid}" />

<c:choose>
  <c:when test='${param.aggiungi eq "aggiungi"}' >
    <c:out value="aggiungi" />
    <jsp:useBean id="dettaglio" class="it.saga.siscotel.db.PraPraticaDett" />
      <c:set target="${dettaglio}" property="dtMod" value="${today}" />
      <c:set target="${dettaglio}" property="dtStato" value="${today}" />
      <c:set target="${dettaglio}" property="idEnte" value="${param.id_ente}" />
      <c:set target="${dettaglio}" property="idEnteDestinatario" value="${param.id_ente_destinatario}" />
      <c:set target="${dettaglio}" property="praPraticaTesta" value="${testa}" />
      <!--
      <c:set target="${dettaglio}" property="idResponsabile" value="${param.idResponsabile}" />
      -->
      <c:set target="${dettaglio}" property="note" value="${param.note}" />
      <c:set target="${dettaglio}" property="desResponsabile" value="${param.desResponsabile}" />
      <c:set target="${dettaglio}" property="idStatoPratica" value="${param.codStato}" />
      <c:set target="${dettaglio}" property="desStatoPratica" value="${param.desStato}" />
      
      <hib:save target="${dettaglio}" />
      <c:redirect url="PraticaEdit.jsp?pkid=${param.testa_pkid}" />
  </c:when>
  
</c:choose>

<form name="frm" action="ModificaStatoPratica.jsp" method="post" >

<input type="hidden" name="id_ente_destinatario" value='<c:out value="${testa.idEnteDestinatario}" />'/>
<input type="hidden" name="id_ente" value='<c:out value="${testa.idEnte}" />'/>
<input type="hidden" name="testa_pkid" value='<c:out value="${testa.pkid}" />'/>
<input type="hidden" name="codStato" />
<input type="hidden" name="desStato" />

<br>
<input type="hidden"  name="testa_pkid" value='<c:out value="${param.testa_pkid}"/>' />

<c:set var="stato"  value="${dettaglio.idStatoPratica}"/>

<table class = "LabelForm" cellspacing="5" cellpadding="5">
  <tr>
  <!--
     <td colspan="2">Id Responsabile&nbsp;<b><input type="text" size="30" maxlength="80"  name="idResponsabile" class="campi"/></b></td> 
  -->
     <td>Responsabile&nbsp;</td>
     <td>
      <input type="text" size="30" maxlength="80" name="desResponsabile"class="campi"/>
     </td>
  </tr>
  <!--
  <tr>
     <td colspan="2">Id Pratica:&nbsp;<b><c:out value="${param.testa_pkid}"/></b></td>
     <td colspan="2">Id Dettaglio:&nbsp;<b><c:out value="${param.dett_pkid}"/></b></td> 
  </tr> 
  
  <tr>
    <td>Stato Pratica&nbsp;</td>
    <td>
      <select name="stato" class="campi">
        <option selected value="-1" >DA SELEZIONARE</option>
        <option value="1"  >DA PRENDERE IN CARICO</option>
        <option value="2"  >IN ATTESA DI ALLEGATI</option>
        <option value="3"  >IN ATTESA DI PAGAMENTO</option>
        <option value="4"  >PAGATA</option>
        <option value="5"  >RICHIESTO PAGAMENTO ON-LINE</option>
        <option value="10" >IN CARICO ALL'ENTE</option>
        <option value="20" >ISTRUTTORIA</option>
        <option value="30" >IN ELABORAZIONE</option>
        <option value="40" >RESPINTA</option>
        <option value="50" >ACCETTATA</option>
        <option value="60" >PRONTA PER CONSEGNA</option>
        <option value="70" >EVASA</option>
        <option value="80" >SPEDITA</option>
        <option value="0"  >INCOMPLETA</option>
       </select></td>   
  </tr>
  -->
  <tr>
    <td>Stato Pratica&nbsp;</td>
    <td>
      <select name="stato" class="campi" >
        <option selected value="-1" >DA SELEZIONARE</option>
        <c:forEach items="${stati}" var="stato">
          <option   value="<c:out value='${stato[0]}' />" />
          <c:out value='${stato[1]}' />
          </option>
        </c:forEach>
       </select></td>   
  </tr>
  <!--
  <tr>
    <td>Data&nbsp;</td>
    <td>
      <INPUT size="12" type="text" name="dtStato" value='<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" />' class ="campi"></td> 
    </td>
    <td>&nbsp;</td>
  </tr>
  -->
  <tr>
   <td>Note&nbsp;</td>
   <td colspan="2"><textarea  name="note" cols="80" rows="10" class = "campi" ></textarea></td>
  </tr> 
</table>

<br>
<br>

<input type="submit" name="aggiungi" value="aggiungi"  class = "pulsante" onclick="return verificaForm(document.frm)" />

</form>
</hib:session>
</body>
</html>
