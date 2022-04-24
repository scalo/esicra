<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%
    
    String whereclause = request.getParameter("whereclause");
    if(whereclause!=null)
      whereclause=whereclause.replace('*','%');

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

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
     
     campo = frm.cod_eco.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(codice_ecografico) LIKE UPPER('"+campo+"%')";
     }
     campo = frm.indirizzo.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(area_indirizzo) LIKE UPPER('%"+campo+"%')";
     }
     campo = frm.civico.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND numero_civico='"+campo+"'";
     }
     campo = frm.let_civico.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(lettera_civico)= UPPER('"+campo+"')";
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
  
  function whereClause(){
  }
  
  function svuotaCampi() {
       document.RicercaOggetto.cod_eco.value="";
       document.RicercaOggetto.indirizzo.value="";       
       document.RicercaOggetto.let_civico.value="";       
       document.RicercaOggetto.civico.value="";       
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



<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>Consultazione Indice Oggetti Territoriali</TITLE>

</head>
<body onLoad="caricaCampi()">


<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp " />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Indice Oggetti" />
  <c:param name="titolo_contenuto" value="Criteri Ricerca Oggetti" />
</c:import>

<form name="RicercaOggetto" action="IndiceRicercaOggettoInd.jsp" method="POST" >
<table class = "LabelForm" width = "100%" border = "0" align = "center" cellspacing="1" cellpadding="0">
    <tr>
        <td align="right">Codice Ecografico&nbsp;</td>
        <td><input type="text" name="cod_eco" value =  "<c:out value="${param.cod_eco}"/>" size="36" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Ubicazione&nbsp;</td>
        <td>
            <input type="text" name="indirizzo" value =  "<c:out value="${param.indirizzo}"/>" size="36" class="campi">
            Numero Civico&nbsp;<input type="text" name="civico" value = "<c:out value="${param.civico}"/>" onblur="validaNumero(this)" size="9" class="campi"> 
            / <input type="text" name="let_civico" value =  "<c:out value="${param.let_civico}"/>" size="9" class="campi">
        </td>
    </tr>
    <tr>
        <td colspan ="2" align="center">
            <br>
            <input type="submit" class = "pulsante" name="azione" value="Ricerca" onclick="return verificaCampi(document.RicercaOggetto)"; >
            <input type="button" class = "pulsante" name="azione" value="Elimina Filtro" onClick ="svuotaCampi()"%>
            <input type="hidden" name="whereclause" >
            <br>
        </td>
    </tr>
</table>
</form>

<br>

<%

 if (whereclause != null ){ %>
    <jsp:include page="IndiceListaOggettiInd.jsp">
        <jsp:param name="whereclause" value="<%= whereclause %>" />
    </jsp:include>
<%}%>


</body>
</html>

<SCRIPT type="text/javascript" src="js/utility.js" >
</SCRIPT>

