<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.text.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%
    String whereclause = request.getParameter("whereclause");
    String azione = request.getParameter("azione");
%>

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
  <c:param name="titolo_pagina" value="Indice Soggetti Giuridici" />
  <c:param name="titolo_contenuto" value="Criteri Ricerca Soggetto Giuridico" />
</c:import>



<form name="RicercaSoggetto"   action="IndiceRicercaSoggettoGiur.jsp" method="POST" >
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="1" cellpadding="0">
    <tr>
        <td align="right">Codice Fiscale&nbsp;</td>
        <td><input type="text" name="cfiscale" value = "<c:out value="${param.cfiscale}"/>" size="36" class="campi"></td>
        <td align="right">Partita Iva&nbsp;</td>
        <td><input type="text" name="piva" value = "<c:out value="${param.piva}"/>" size="36" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Ragione Sociale&nbsp;</td>
        <td colspan="3"><input type="text" name="denominazione" value = "<c:out value="${param.denominazione}"/>" size="72" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Indirizzo&nbsp;</td>
        <td><input type="text" name="indirizzo" id="data" value = "<c:out value="${param.indirizzo}"/>"  size="36" class="campi"></td>
        <td align="right">Numero Civico&nbsp;</td>
        <td><input type="text" name="civico" value = "<c:out value="${param.civico}"/>" onblur="validaNumero(this)" size="9" class="campi"> /
            <input type="text" name="let_civico" value = "<c:out value="${param.let_civico}"/>" size="9" class="campi">
        </td>
    </tr>
    <tr>
        <td align="right">Comune/Localita Estera&nbsp;</td>
        <td><input type="text" name="comune" value = "<c:out value="${param.comune}"/>" size="36" class="campi"></td>
        <td align="right">Stato&nbsp;</td>
        <td><input type="text" name="stato" value = "<c:out value="${param.stato}"/>" size="36" class="campi"></td>
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
    <jsp:include page="IndiceListaSoggettiGiur.jsp">
        <jsp:param name="whereclause" value="<%= whereclause %>" />
    </jsp:include>
<%}%>

</body>
</html>

<SCRIPT type="text/javascript" src="js/utility.js" >
</SCRIPT>

<script type="text/javascript">

  function verificaCampi(frm) 
   {

     var campo;
     var whereclause="";

     frm.whereclause.value="";       
     
     if(isInteger(frm.civico.value) == false){
        frm.civico.select();
        return false;
     }
      
     campo = frm.denominazione.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(denominazione) LIKE UPPER('"+campo+"%')";
      }

     campo = frm.piva.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(partita_iva) = ('"+campo+"')";
      }

     campo = frm.cfiscale.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(codice_fiscale) LIKE UPPER('"+campo+"%')";
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
           whereclause = whereclause+" AND UPPER(des_stato_indirizzo) LIKE UPPER('"+campo+"%')";
      }

     campo = frm.comune.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND (UPPER(des_comune_indirizzo) LIKE UPPER ('"+campo+"%') OR UPPER(des_loc_indirizzo) LIKE UPPER ('"+campo+"%'))";
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
       document.RicercaSoggetto.denominazione.value="";
       document.RicercaSoggetto.cfiscale.value="";       
       document.RicercaSoggetto.piva.value="";       
       document.RicercaSoggetto.indirizzo.value="";       
       document.RicercaSoggetto.civico.value="";       
       document.RicercaSoggetto.let_civico.value="";       
       document.RicercaSoggetto.stato.value="";       
       document.RicercaSoggetto.comune.value="";       
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
   
</script>
