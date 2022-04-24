<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.text.*,java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!--
  TODO
      aggiungere gestione utenti
      togliere gli scriptlet 
-->

<%
  String whereclause = request.getParameter("whereclause");
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  
  String azione = request.getParameter("azione");
  Calendar cal = Calendar.getInstance();
  Date today=cal.getTime();
  cal.add(Calendar.MONTH, -1);
  Date yesterday = cal.getTime();
  
  pageContext.setAttribute("today",today);
  pageContext.setAttribute("yesterday",yesterday);
  
%>

<script type="text/javascript" src="js/date.js"></script>
<style  type="text/css">@import url(css/calendar-blue.css);</style>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-it.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/utility.js" >

</script>

<script type="text/javascript">

  function validaNumero(campo) {
    campo.value = trim(campo.value);
    if (campo.value != ""){
      if ( isInteger(campo.value) == false){
      	alert("Attenzione è richiesto un valore numerico");
        campo.select();
        return false;
      } 
    }
      return true;        
  }

  function verificaCampi(frm){
     
     //alert(frm.da_data.value);
     
     var campo;
     var whereclause="";
      
     frm.whereclause.value="";       

     campo = frm.oggetto.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(oggetto) LIKE UPPER('"+campo+"%')";
     }

     campo = frm.cfiscale.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(codice_fiscale) LIKE UPPER('"+campo+"%')";
      }

     campo = frm.cognome.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(oggetto) LIKE UPPER('%"+campo+"')";
      }

     campo = frm.nome.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(oggetto) LIKE UPPER('"+campo+"%')";
     }
      
     campo = frm.da_data.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND dtPratica >= TO_DATE('"+campo+"','dd/MM/yyyy')";
     }
     
     campo = frm.a_data.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND dtPratica <= TO_DATE('"+campo+"','dd/MM/yyyy')";
     }

     campo = frm.da_identificativo.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND pkid >="+campo+"";
     }
     
     campo = frm.a_identificativo.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND pkid <="+campo+"";
     }
     
     campo = frm.stato.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND idStatoPratica="+campo;
     }
     
     campo = frm.servizio.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND idServizio="+campo;
     }
     
     if(whereclause != "") {
          whereclause = whereclause.substring(5);
          frm.whereclause.value = whereclause;
          // ALERT DEBUG
          // alert(whereclause);
          return true;       
      }
      
  		alert("Specificare un criterio di ricerca!!");
  		return false;    
    }

  function svuotaCampi() {
       document.ricerca.oggetto.value="";
       document.ricerca.cognome.value="";
       document.ricerca.cfiscale.value="";
       document.ricerca.nome.value="";
       document.ricerca.stato.value="";
       document.ricerca.servizio.value="";
       document.ricerca.da_data.value="";
       document.ricerca.a_data.value="";
       document.ricerca.da_identificativo.value="";
       document.ricerca.a_identificativo.value="";
       document.ricerca.da_codice.value="";
       document.ricerca.a_codice.value=""; 
   }
   
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>E-Sicra - Ricerca Pratiche</TITLE>

</head>
<body>

<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg" />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Ricerca Pratiche" />
  <c:param name="titolo_contenuto" value="Criteri Ricerca Pratiche" />
</c:import>

<hib:find var="stati" maxResults="100">
    select distinct idStatoPratica,desStatoPratica from VPraticaCompleta order by desStatoPratica
</hib:find>

<hib:find var="servizi" maxResults="100">
    select distinct idServizio,desBreve from VPraticaCompleta order by desBreve
</hib:find>

<c:choose>
  <c:when test='${param.azione eq "ricerca"}' >
    <c:set var="cfiscale" scope="session" value="${param.cfiscale}" />
    <c:set var="nome" scope="session" value="${param.nome}" />
    <c:set var="cognome" scope="session" value="${param.cognome}" />
    <c:set var="oggetto" scope="session" value="${param.oggetto}" />
    <c:set var="da_identificativo" scope="session" value="${param.da_identificativo}" />
    <c:set var="a_identificativo" scope="session" value="${param.a_identificativo}" />
    <fmt:parseDate var="a_data" pattern="dd/MM/yyyy" value="${param.a_data}" scope="session" />
    <fmt:parseDate var="da_data" pattern="dd/MM/yyyy" value="${param.da_data}" scope="session" />
  </c:when>
  <c:otherwise>
    <c:if test="${empty da_data}">
      <c:set var="da_data" value="${yesterday}" scope="session" />
    </c:if>
    <c:if test="${empty a_data}">
      <c:set var="a_data" value="${today}" scope="session" />
    </c:if>
  </c:otherwise>
</c:choose>
<form name="ricerca"  action="RicercaPratiche.jsp" method="POST" >

<table class = "LabelForm" width = "100%" border = "0" cellpadding="0">
<tr>
<td>
<table class = "LabelForm"  border = "0" align ="left">
    <tr>
        <td align="left">Codice Fiscale&nbsp;</td>
        <td><input type="text" name="cfiscale"
                   value = "<c:out value="${cfiscale}" />" size="36" class="campi">
        </td>
    </tr>
    <tr>
        <td align="left">Cognome&nbsp;</td>
        <td><input type="text" name="cognome"
                   value ="<c:out value="${param.cognome}"/>" size="36" class="campi">
        </td>
    </tr>
    <tr>
        <td align="left">Nome&nbsp;</td>
        <td><input type="text" name="nome"
                   value ="<c:out value="${param.nome}" />" size="36" class="campi">
        </td>
    </tr>
    <tr>
        <td align="left">Oggetto&nbsp;</td>
        <td><input type="text" name="oggetto"
                   value ="<c:out value="${param.oggetto}" />" size="36" class="campi">
        </td>
    </tr>
    <tr>
    <td>
</table>
</td>
<td valign="top">
<table class = "LabelForm"  border = "0"  cellspacing="2">

  <tr>
      <td align="left" >Da Data </td>
      <td >
          <input type="text" name="da_data" id="da_data"
                 value='<fmt:formatDate value="${da_data}" pattern="dd/MM/yyyy" />'  onblur="validaData(this)"
                 size="18" class="campi"></input>
          <img alt="calendar" align="middle" src="myimg/calendar.gif" id="trigger1"
               style="cursor: pointer;" onmouseover="this.style.background='blue';"
               onmouseout="this.style.background=''"></img>
          <script type="text/javascript">
                Calendar.setup({
                    inputField : "da_data",
                    ifFormat : "%d/%m/%Y",
                    button : "trigger1", 
                    step  :  1   
                });
          </script>
      </td>
      <td align="left" >A Data </td>
      <td >
          <input type="text" name="a_data" id="a_data"
                 value='<fmt:formatDate value="${a_data}" pattern="dd/MM/yyyy" />'  onblur="validaData(this)"
                 size="18" class="campi"></input>
          <img alt="calendar" align="middle" src="myimg/calendar.gif" id="trigger2"
               style="cursor: pointer;" onmouseover="this.style.background='blue';"
               onmouseout="this.style.background=''"></img>
          <script type="text/javascript">
                Calendar.setup({
                    inputField : "a_data",
                    ifFormat : "%d/%m/%Y",
                    button : "trigger2", 
                    step  :  1   
                });
          </script>
      </td>
      </tr>
      <tr>
          <td align="left">Da Identificativo </td>
          <td>
          <input type="text" name="da_identificativo"
                  value = "<c:out value="${param.da_identificativo}" />" size="22" class="campi">
          </td>
          <td align="left">A Identificativo</td>
          <td>
          <input type="text" name="a_identificativo"
                  value = "<c:out value="${param.a_identificativo}" />" size="22" class="campi">
          </td>
      </tr>
                           
    <tr><td>&nbsp;</td></tr>
    <tr>
    <td align="left">Servizio</td>
    <td>
        <select name="servizio" class="campi">
          <option selected value="" >Tutti</option>
          <c:forEach items="${servizi}" var="servizio">
            <option   value="<c:out value='${servizio[0]}' />" />
            <c:out value='${servizio[1]}' />
            </option>
          </c:forEach>
        </select>
    </td>
    
    <td align="left">Stato Pratica</td>
    <td>
       <select name="stato" class="campi">
        <option selected value="" >Tutti</option>
        <c:forEach items="${stati}" var="stato">
          <option   value="<c:out value='${stato[0]}' />" />
          <c:out value='${stato[1]}' />
          </option>
        </c:forEach>
       </select>
    </td>
    </tr>              
                            
</table>
</td>
<tr>
<td  colspan = 4 align="center" >
<table>
<tr>
    <td>
        <br>
        <input type="submit" class = "pulsante" name="azione" value="ricerca" onclick="return verificaCampi(document.ricerca)"; >
        <input type="button" class = "pulsante" name="azione" value="elimina filtro" onClick ="svuotaCampi()"%>
        <input type="hidden" name="whereclause">
        <br>
    </td>
</tr>
</table>
</td>
</tr>
</table>
</form>

<% if (whereclause != null ){ %>
    <c:import url="ListaPratiche.jsp">
        <c:param name="whereclause" value="${param.whereclause}" />
    </c:import>
<%}%>

</body>
</html>

