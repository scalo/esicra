<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%
    String whereclause = request.getParameter("whereclause");
    String azione = request.getParameter("azione");
%>

<script type="text/javascript" SRC="js/date.js"></script>
<script TYPE="text/javascript" SRC="js/utility.js" ></script>
<style type="text/css">@import url(css/calendar-blue.css);</style>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-it.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<SCRIPT type="text/javascript" >

function verificaCampi(frm) {

     validaData(frm.data_nas); 

     var campo;
     var whereclause="";

     frm.whereclause.value="";       

     campo = frm.cognome.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND (UPPER(cognome) LIKE UPPER('"+campo+"%') OR UPPER(denominazione) LIKE UPPER('"+campo+"%'))";
      }

     campo = frm.nome.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(nome) LIKE UPPER('"+campo+"%')";
      }
     campo = frm.data_nas.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND dt_nascita = TO_DATE('"+campo+"','dd/MM/yyyy')";
      }

     campo = frm.cfiscale.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(codice_fiscale) LIKE UPPER('"+campo+"%')";
      }
      
     campo = frm.piva.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(partita_iva) =('"+campo+"')";
      }

     campo = frm.localita_nas.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND ( UPPER(des_comune_nascita) LIKE UPPER('"+campo+"%') OR UPPER(des_localita_nascita) LIKE UPPER('"+campo+"%') OR UPPER(des_stato_nascita) LIKE UPPER('"+campo+"%'))";
      }

     if(whereclause != "") {
          whereclause = whereclause.substring(5);
          frm.whereclause.value = whereclause;
          return true;       
     } else {
  		alert("Specificare un criterio di ricerca!!");
  		return false;    
     }
  }

  function svuotaCampi() {
       document.RicercaSoggetto.cognome.value="";
       document.RicercaSoggetto.cfiscale.value="";       
       document.RicercaSoggetto.nome.value="";       
       document.RicercaSoggetto.data_nas.value="";
       document.RicercaSoggetto.localita_nas.value="";       
       document.RicercaSoggetto.piva.value="";  
   }

  function validaData(campo) {
      campo.value = normalizzaData(campo.value);
      return true;
  }

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
   
</SCRIPT>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>E-Sicra - ConsultazioneSoggetti Fisici Giuridici</TITLE>
</head>
<body>

<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp |" />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Soggetti Fisici e Giuridici" />
  <c:param name="titolo_contenuto" value="Criteri Ricerca Soggetto" />
</c:import>

<form name="RicercaSoggetto"  action="Soggetti.jsp" method="POST" >
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="1" cellpadding="1">
    <tr>
        <td align="right">Codice Fiscale&nbsp;</td>
        <td><input type="text" name="cfiscale" value="<c:out value="${param.cfiscale}" />" size="36" class="campi"></td>
        <td align="right">Partita Iva&nbsp;</td>
        <td><input type="text" name="piva" value ="<c:out value="${param.piva}" />" size="36" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Cognome/Denominazione&nbsp;</td>
        <td><input type="text" name="cognome" value ="<c:out value="${param.cognome}" />" size="36" class="campi"></td>
        <td align="right">Nome&nbsp;</td>
        <td><input type="text" name="nome" value ="<c:out value="${param.nome}" />" size="36" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Data Nascita&nbsp;</td>
        <td><input type="text" name="data_nas" id="data_nas" value = "<c:out value="${param.data_nas}" />"  onblur="validaData(this)" size="18" class="campi">
          <img alt ="calendar" align="middle" src="myimg/calendar.gif" id="trigger"
            style="cursor: pointer;"  onmouseover="this.style.background='blue';"
            onmouseout="this.style.background=''" />
          <script type="text/javascript">
            Calendar.setup({
                inputField : "data_nas",
                ifFormat : "%d/%m/%Y",
                button : "trigger", 
                step  :    1   
            });
          </script>
        </td>
        <td align="right">Località Nascita&nbsp;</td>
        <td><input type="text" name="localita_nas" id="data" value ="<c:out value="${param.localita_nas}" />" onblur="validaData(this)" size="36" class="campi"></td>
    </tr>
    <tr>
        <td colspan = 4 align="center">
            <br>
            <input type="submit" class = "pulsante" name="azione" value="Ricerca" onclick="return verificaCampi(document.RicercaSoggetto)"; >
            <input type="button" class = "pulsante" name="azione" value="Elimina Filtro" onClick ="svuotaCampi()"%>
            <input type="hidden" name="whereclause">
            <br>
        </td>
    </tr>
</table>
</form>

<br>

<% if (whereclause != null ){ %>
    <jsp:include page="ListaSoggetti.jsp">
        <jsp:param name="whereclause" value="<%= whereclause %>" />
    </jsp:include>
<%}%>

</body>
</html>


