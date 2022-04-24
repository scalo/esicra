<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.text.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%
    String whereclause = request.getParameter("whereclause");
    //SimpleDateFormat objNow = new SimpleDateFormat("dd/MM/yyyy");
    String azione = request.getParameter("azione");
%>

<script type="text/javascript" src="js/date.js"></script>
<script type="text/javascript" src="js/utility.js" ></script>
<style  type="text/css">@import url(css/calendar-blue.css);</style>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-it.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script type="text/javascript" src="js/utility.js" >
</script>

<script type="text/javascript">

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

  function verificaCampi(frm) 
   {
      
     var campo;
     var whereclause="";

     validaData(frm.data_nas); 
     
     if(isInteger(frm.civico.value) == false){
        frm.civico.select();
        return false;
     }

     frm.whereclause.value="";       

     campo = frm.cognome.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(cognome) LIKE UPPER('"+campo+"%')";
      }

     campo = frm.nome.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(nome) LIKE UPPER('"+campo+"%')";
      }
     campo = frm.data_nas.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND data_nascita = TO_DATE('"+campo+"','dd/MM/yyyy')";
      }

     campo = frm.cfiscale.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(codice_fiscale) LIKE UPPER('"+campo+"%')";
      }
      
     campo = frm.localita_nas.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND ( UPPER(comune_nascita) LIKE UPPER('"+campo+"%') OR UPPER(localita_nascita) LIKE UPPER('"+campo+"%') OR UPPER(stato_nascita) LIKE UPPER('"+campo+"%'))";
      }

     campo = frm.indirizzo.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(area_indirizzo) LIKE UPPER('%"+campo+"%')";
      }

     campo = frm.civico.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND numero_civico_indirizzo = '"+campo+"'";
      }

     campo = frm.let_civico.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(lettera_civico_indirizzo) = UPPER('"+campo+"')";
      }

     campo = frm.stato.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(des_stato_indirizzo) LIKE UPPER('%"+campo+"%')";
      }

     campo = frm.comune.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND (UPPER(des_comune_indirizzo) LIKE UPPER ('"+campo+"%') OR UPPER(des_loc_indirizzo) LIKE UPPER ('"+campo+"%'))";
      }

     campo = frm.sesso.value;
     if (campo != null && trim(campo) != "" && trim(campo)!= "-99") {
           whereclause = whereclause+" AND sesso = '"+campo+"'"; 
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
       document.RicercaSoggetto.comune.value="";       
       document.RicercaSoggetto.indirizzo.value="";       
       document.RicercaSoggetto.civico.value="";       
       document.RicercaSoggetto.let_civico.value="";       
       document.RicercaSoggetto.stato.value="";       
       document.RicercaSoggetto.sesso.value="-99"; 
       
   }


   
</script>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>E-Sicra - Consultazione Indice Anagrafe Estesa</TITLE>

</head>
<body>

<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp " />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Indice Soggetti Fisici" />
  <c:param name="titolo_contenuto" value="Criteri Ricerca Soggetto Fisico" />
</c:import>


<form name="RicercaSoggetto"   action="IndiceRicercaSoggettoFis.jsp" method="POST" >
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="1" cellpadding="1">
    <tr>
        <td align="right">Codice Fiscale&nbsp;</td>
        <td><input type="text" name="cfiscale"
                   value = "<c:out value="${param.cfiscale}" />" size="36" class="campi"></td>
        <td align="right">Sesso&nbsp;</td>
        <td>
            <select name="sesso">
               	<option value= "-99"></option>
               	<option value="1" <c:if test="${param.sesso==1}">selected</c:if> >Maschio</option>
               	<option value="2" <c:if test="${param.sesso==2}">selected</c:if> >Femmina</option>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right">Cognome&nbsp;</td>
        <td><input type="text" name="cognome"
                   value ="<c:out value="${param.cognome}"/>" size="36" class="campi"></td>
        <td align="right">Nome&nbsp;</td>
        <td><input type="text" name="nome"
                   value ="<c:out value="${param.nome}" />" size="36" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Data Nascita&nbsp;</td>
        <td><input type="text" name="data_nas" id="data_nas"
                   value ="<c:out value="${param.data_nas}" />" onblur="validaData(this)" size="18" class="campi">
          <img alt ="calendar" align="middle" src="myimg/calendar.gif" id="trigger"
            style="cursor: pointer;"  onmouseover="this.style.background='blue';"
            onmouseout="this.style.background=''" />
          <script type="text/javascript">
            Calendar.setup({
                inputField : "data_nas",
                ifFormat : "%d/%m/%Y",
                button : "trigger", 
                step  :  1   
            });
          </script>
        </td>
        <td align="right"> Località Nascita&nbsp;</td>
        <td><input type="text" name="localita_nas"
                   value ="<c:out value="${param.localita_nas}" />" size="36" class="campi"></td>
    </tr>
    <tr>
        <td><br></td>
        <td><br></b></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td align="right">Indirizzo&nbsp;</td>
        <td><input type="text" name="indirizzo"
                   value ="<c:out value="${param.indirizzo}" />" size="36" class="campi"></td>
        <td align="right">Numero Civico&nbsp;</td>
        <td><input type="text" name="civico"
                   value ="<c:out value="${param.civico}" />" onblur="validaNumero(this)" size="9" class="campi"> /
            <input type="text" name="let_civico"
                   value = "<c:out value="${param.let_civico}"/>" size="9" class="campi">
        </td>
    </tr>
    <tr>
        <td align="right">Comune/Località Estera&nbsp;</td>
        <td><input type="text" name="comune"
                   value = "<c:out value="${param.comune}" />" size="36" class="campi"></td>
        <td align="right">Stato&nbsp;</td>
        <td><input type="text" name="stato"
                   value ="<c:out value="${param.stato}" />" size="36" class="campi"></td>
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
    <c:import url="IndiceListaSoggetti.jsp">
        <c:param name="whereclause" value="${param.whereclause}" />
    </c:import>
<%}%>

</body>
</html>

