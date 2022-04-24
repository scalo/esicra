<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<script type="text/javascript">
  
  function salvaForm(){
    if (checkForm(document.frm) ){
      document.frm.azione.value="salva";
      return true;
    }
    return false
  }
  
  function annullaForm(){
    //alert("pulisci");
    document.frm.azione.value="annulla";
    return true;
  }
  
  function checkForm(form){
    var x=form.ordinale.value;
    var anum=/(^\d+$)|(^\d+\.\d+$)/;
    if (x.length==0 || anum.test(x)){
      testresult=true;
    }else{
      alert("Inserire un valore numerico!");
      form.ordinale.focus();
      testresult=false;
    }
    return (testresult);
  }
  
</script>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <link rel="STYLESHEET" type="text/css" href="siscotel.css"></link>
    <script type="text/javascript" src="decode.js" ></script>
    <title>
      Modifica Profilazione
    </title>
  </head>
  <hib:session>
  <body>
  
  <c:import url="barramenu.jsp">
    <c:param name="menu"  value="|Profilazione Servizi,ProfilazioneServizi.jsp|Menù Principale,EesMenu.jsp, myimg/cartella.jpg|"/>
  </c:import>
  <br>
  <c:import url="header.jsp">
    <c:param name="titolo_pagina"
             value="Aggiungi Profilazione"/>
    <c:param name="titolo_contenuto"
             value=""/>
  </c:import>

<%
  String id_ente=System.getProperty("esicra.id_ente");
  request.setAttribute("id_ente",id_ente);
%>

<br/><br/>
 
 <c:choose>
   <c:when test="${empty param.pkid}">
       <jsp:useBean id="profilazione" class="it.saga.siscotel.db.ProValore" />
   </c:when> 
   <c:otherwise>
      <hib:load var="profilazione" classname="it.saga.siscotel.db.ProValore" value="${param.pkid}" /> 
   </c:otherwise>   
 </c:choose>  

<hib:load var="tipo_profilazione" classname="it.saga.siscotel.db.ProTipo" value="${param.tipo}" />
  
<c:if test="${param.azione == 'salva'}">
  
  <jsp:useBean id="today" class="java.util.Date" />

  
  <c:set target="${profilazione}" property="descrizione" value="${param.descrizione}" />
  <c:set target="${profilazione}" property="idEnte" value="${id_ente}" />
  <c:set target="${profilazione}" property="proTipo" value="${tipo_profilazione}" />
  <c:set target="${profilazione}" property="ordinale" value="${param.ordinale}" />
  <c:set target="${profilazione}" property="dtMod" value="${today}" />
  
  <hib:save target="${profilazione}" />
  <c:redirect url="ProfilazioneServizi.jsp">
    <c:param name="tipo" value="${param.tipo}" />
    <c:param name="pkid" value="" />
    <c:param name="azione" value="" />
  </c:redirect>
  
</c:if>

<c:if test="${param.azione == 'annulla'}">
  <c:redirect url="ProfilazioneServizi.jsp">
    <c:param name="tipo" value="${param.tipo}" />
  </c:redirect>
</c:if>

<p align="center" class="etichetta">
Profilazione 
<c:out value='${tipo_profilazione.descrizione}'/>
</p>
<form name="frm" action="ModificaProfilazione.jsp" method="post" >

<input type="hidden" name="azione" />
<input type="hidden" name="tipo" value='<c:out value="${param.tipo}" />' />
<input type="hidden" name="pkid" value='<c:out value="${param.pkid}" />' />

<table class = "LabelForm" cellspacing="5" cellpadding="1" align="center" >
  <tr>
    <td><b>Id Profilazione</b></td>
    <td><input disabled type="text" name="id" size="22" 
      value="<c:out value='${profilazione.pkid}'/>"
    /></td>
  </tr>
  <tr>
    <td><b>Cod Tipo</b></td>
    <td><input disabled type="text" name="codTipo" size="22"
      value="<c:out value='${param.tipo}'/>"
    /></td>
  </tr>
  <tr>
    <td><b>Id Ente</b></td>
    <td><input disabled type="text" name="idEnte" size="7" 
       value="<c:out value='${profilazione.idEnte}'/>"
    /></td>
  </tr>
  <tr>
    <td><b>Descrizione</b></td>
    <td><input type="text" name="descrizione" size="50" 
      value="<c:out value='${profilazione.descrizione}'/>"
    /></td>
  </tr>
  <tr>
    <td><b>Ordinale</b></td>
    <td><input type="text" name="ordinale" size="10" 
      value="<c:out value='${profilazione.ordinale}'/>"
    /></td>
  </tr>
</table>

<br>
<p align="center">
<table align="center" cellspacing="20">
  <tr>
  <td>
    <input type="submit" name="aggiungi" value="Salva"  class = "pulsante" onclick="return salvaForm();" />
  </td>
  <td>
    <input type="submit" name="annulla" value="Annulla"  class = "pulsante" onclick="return annullaForm();" />
  </td>
  </tr>
</table>

</form>
</body>
</hib:session>
</html>
