<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<html>
<head>

<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">
<TITLE>Tabella da Allineare</TITLE>
</head>

<body>

<script type="text/javascript" src="js/utility.js"  ></script>

<script type="text/javascript">

   function verificaCampi() {
       ok_campi = true; 
    
     if (trim(document.ModificaTabella.nomeTabella.value) == "" ||
         trim(document.ModificaTabella.ordine.value) == "") {
           alert("I campi: \n\n   - Nome Tabella \n   - Ordine \n\n devono essere compilati");
         ok_campi = false; 
       } 
       
       return ok_campi;
   }

</script>


<c:import url="barramenu.jsp">
    <c:param name="menu"
         value="|Ritorna a Elenco Tabelle,javascript:history.go(-1)|"/>
</c:import>
    
<c:import url="header.jsp">
  <c:param name="titolo_pagina"
           value="  Tabella da Allineare"/>
  <c:param name="titolo_contenuto"
           value=""/>
</c:import>


<c:if test="${not empty tabella}">
  <c:remove var="tabella" scope="session" />
</c:if>


<hib:session>

<c:if test='${param.azione=="modifica"}'>
<hib:load 
  var="tabella"
  classname="it.saga.siscotel.db.SerCoordinator"
  value="${param.pkid}"
/>
</c:if>

<form name="ModificaTabella" action="eCoordinatorSalva.jsp"  method="post" onsubmit="return verificaCampi();" >

<br>
<br>
<table border="0" cellpadding="" cellspacing="0" class = "LabelForm">
  <tr>
        <td>Nome Tabella:</td>
        <td><input type="text"  name="nomeTabella"  value='<c:out value="${tabella.nomeTabella}" />'  
        
        class = "campi" /></td>
  </tr>
  <tr>
   <td>Ordine:</td>
        <td><input type="text" name="ordine" value='<c:out value="${tabella.ordine}" />'  class = "campi" /></td>
  </tr> 
  <tr>  
        <td>Tabella da Allineare:</td>
        <td> 
            <input type="radio" name="flAllinea" value="1" <c:if test="${tabella.flAllinea==1}" >checked</c:if> /> Si<br>
            <input type="radio" name="flAllinea" value="0" <c:if test="${tabella.flAllinea!=1}" >checked</c:if> /> No<br>           
        </td> 
         
    </tr>
    <tr>
        <td>Tabella Bidirezionale:</td>
        <td> 
            <input type="radio" name="flBidir" value="1" <c:if test="${tabella.flBidir==1}" >checked</c:if> /> Si<br>
            <input type="radio" name="flBidir" value="0" <c:if test="${tabella.flBidir!=1  }" >checked</c:if> /> No<br>
        </td>    
    </tr>
  <tr>
        <td>Tabella da Cancellare:</td>
        <td> 
            <input type="radio" name="flCancellabile" value="1" <c:if test="${tabella.flCancellabile==1}" >checked</c:if> /> Si<br>
            <input type="radio" name="flCancellabile" value="0" <c:if test="${tabella.flCancellabile!=1}" >checked</c:if> /> No<br>
        </td> 
    </tr>
</table>

<input type="hidden" name="azione" value="<c:out value="${param.azione}" />"></input>
<input type="hidden" name="pkid" value="<c:out value="${param.pkid}" />" ></input>


<input type="submit" align ="middle" value="Conferma" class = "pulsante" ></input>

<br>
<br>
</hib:session>

</form>

</body>
</html>

