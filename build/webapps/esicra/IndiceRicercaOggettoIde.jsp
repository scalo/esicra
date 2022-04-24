<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%  
  
    String whereclause = request.getParameter("whereclause");
    /*
    if (whereclause != null){
    	whereclause=whereclause.replace('*','%');
    }
    String azione = request.getParameter("azione");
    String call = request.getParameter("call");
    if(call== null){
        call="";
    }
    */
%>

<html>
<head>

<SCRIPT type="text/javascript" src="js/utility.js" >
</SCRIPT>

<script type="text/javascript">

  function verificaCampi(frm) 
   {


     var campo;
     var whereclause="";

     frm.whereclause.value="";       
      
     campo = frm.tipocatasto.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND tipo_catasto = '"+campo+"'";
      }

     campo = frm.cod_eco.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(codice_ecografico) LIKE UPPER('"+campo+"%')";
      }

     campo = frm.sezione.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(sezione) = UPPER('"+campo+"')";
      }
     campo = frm.foglio.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(foglio) = UPPER('"+campo+"')";
      }
     campo = frm.mappale.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(mappale) = UPPER('"+campo+"')";
      }
     campo = frm.sub.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(sub) = UPPER('"+campo+"')";
      }

     campo = frm.anno_prot.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(prot_anno) = UPPER('"+campo+"')";
      }
     campo = frm.num_prot.value;
     if (campo != null && trim(campo) != "" ) {
           whereclause = whereclause+" AND UPPER(prot_num) = UPPER('"+campo+"')";
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
       document.RicercaOggetto.cod_eco.value="";
       document.RicercaOggetto.tipocatasto.value="";
       document.RicercaOggetto.sezione.value="";       
       document.RicercaOggetto.foglio.value="";       
       document.RicercaOggetto.mappale.value="";       
       document.RicercaOggetto.sub.value="";       
       document.RicercaOggetto.anno_prot.value="";       
       document.RicercaOggetto.num_prot.value="";       
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
</script>

<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>E-Sicra - Consultazione Indice Oggetti Territoriali</TITLE>

</head>
<body>

<c:import url="barramenu.jsp" >
  <c:param name="menu" value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Consultazione,EesConsultazione.jsp " />
</c:import>

<c:import url="header.jsp" >
  <c:param name="titolo_pagina" value="Indice Oggetti" />
  <c:param name="titolo_contenuto" value="Criteri Ricerca Oggetti" />
</c:import>

<form name="RicercaOggetto" action="IndiceRicercaOggettoIde.jsp" method="POST" >
<table class = "LabelForm" border = "0" align = "center" cellspacing="2" cellpadding="1">
    <tr>
        <td align="right">Codice Ecografico&nbsp;</td>
        <td><input type="text" name="cod_eco" value =  "<c:out value="${param.cod_eco}" />" size="12" class="campi"></td>
        <td align="right">Tipo Catasto&nbsp;</td>
        <td>
          <select name="tipocatasto" class="campi">
               	<option value= "" <c:if test="${param.tipocatasto}==''"  >selected</c:if> ></option>   
               	<option value= "F" <c:if test="${param.tipocatasto}=='F'" >selected</c:if> >Fabbricati</option>
               	<option value= "T" <c:if test="${param.tipocatasto}=='M'"  >selected</c:if> >Terreni</option>
          </select>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td align="right">Sezione:&nbsp;</td>
        <td><input type="text" name="sezione" value =  "<c:out value="${param.sezione}"/>" size="12" class="campi"></td>
        <td align="right">Foglio:&nbsp;</td>
        <td><input type="text" name="foglio" value =  "<c:out value="${param.foglio}"/>" size="12" class="campi"></td>
        <td align="right">Mappale:&nbsp;</td>
        <td><input type="text" name="mappale" value = "<c:out value="${param.mappale}"/>" size="12" class="campi"></td>
        <td align="right">Sub.:&nbsp;</td>
        <td><input type="text" name="sub" value =  "<c:out value="${param.sub}"/>" size="12" class="campi"></td>
    </tr>
    <tr>
        <td align="right">Anno Protocollo:&nbsp;</td>
        <td><input type="text" name="anno_prot" value = "<c:out value="${param.anno_prot}"/>" size="12" class="campi"></td>
        <td align="right">Num. Protocollo:&nbsp;</td>
        <td><input type="text" name="num_prot" value =  "<c:out value="${param.num_prot}"/>" size="12" class="campi"></td>

    </tr>
    <tr>
        <td colspan ="8" align="center">
            <br>
            <input type="submit" class = "pulsante" name="azione" value="Ricerca" onclick="return verificaCampi(document.RicercaOggetto)"; >
            <input type="button" class = "pulsante" name="azione" value="Elimina Filtro" onClick ="svuotaCampi()"%>
            <input type="hidden" name="whereclause">
            <br>
        </td>
    </tr>
    
</table>
</form>

<br>

<% if (whereclause != null ){ %>
    <c:out value="${whereclause}" />
    <c:import url="IndiceListaOggettiIde.jsp">
        <c:param name="whereclause" value="${param.whereclause}" />
    </c:import>
<%}%>

</body>
</html>

