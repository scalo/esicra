<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252" import="java.math.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="js/date.js"></script>
<style  type="text/css">@import url(css/calendar-blue.css);</style>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-it.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/utility.js" ></script>

<!--
  TODO 
    controllo checkbox per cancellazione
-->

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="esicra.css">
<TITLE>Gestione Pagamenti</TITLE>
</head>

<script type="text/javascript">
  
  function listaProfilazioni(){
    //alert("lista profilazioni");
    document.frm.submit();
  }
  
  function checkForm(frm){
    alert(frm.azione.value);
  }
  
  function doAction(az){
    document.frm.azione.value=az;
    //len = document.frm.sel.length;
    //alert("len="+len);
    document.frm.submit();
    //alert(document.frm.azione.value);
  }
  
  function selectRadio(frm){
    len= frm.sel.length;
    idx=-1;
    for(i=0;i<len;i++){
      if (frm.sel[i].checked==true){
        idx=i;
        break;
      }
    }
    if(frm.sel.length==null){
      frm.pkid.value=frm.sel.value;
      frm.modifica.disabled=false;
      frm.elimina.disabled=false;
      return true;
    }
    frm.pkid.value=frm.sel[idx].value;
    //alert(frm.sel[idx].value);
    //alert("len="+len);
    //alert("idx="+idx);
    if (idx==-1) return false;
    frm.modifica.disabled=false;
    frm.elimina.disabled=false;
    return true;
  }
  
</script>

<body class = "body">

<c:import url="barramenu.jsp">
  <c:param name="menu"
           value="|Menù Principale,EesMenu.jsp, myimg/cartella.jpg|"/>
</c:import>
<br/>
<c:import url="header.jsp">
  <c:param name="titolo_pagina"
           value="Profilazione Servizi"/>
  <c:param name="titolo_contenuto"
           value=""/>
</c:import>

<c:if test="${param.azione == 'aggiungi'}">
  <c:redirect url="ModificaProfilazione.jsp">
    <c:param name="tipo" value="${param.tipo}"/>
    <c:param name="azione" value="${param.azione}"/>
  </c:redirect>
</c:if>

<c:if test="${param.azione == 'modifica'}">
  <c:redirect url="ModificaProfilazione.jsp">
    <c:param name="tipo" value="${param.tipo}"/>
    <c:param name="pkid" value="${param.pkid}"/>
    <c:param name="azione" value="${param.azione}"/>
  </c:redirect>
</c:if>

<c:if test="${param.azione eq 'elimina'}">
    <hib:load var="profilo" classname="it.saga.siscotel.db.ProValore" value="${param.pkid}" /> 
    <hib:delete target="${profilo}" />
    <c:redirect url="ProfilazioneServizi.jsp">
      <c:param name="tipo" value="${tipo}" />
    </c:redirect>
</c:if>

<c:choose>
  <c:when test="${empty param.tipo}">
    <c:set var="tipo" value="10" />
  </c:when>
  <c:otherwise>
    <c:set var="tipo" value="${param.tipo}" />
  </c:otherwise>
</c:choose>


<hib:find var="valori" maxResults="100">
    select pkid,descrizione,ordinale from ProValore where tipo=${tipo} order by ordinale
</hib:find>

<hib:find var="tipi" maxResults="100">
    select pkid,descrizione from ProTipo
</hib:find>

<form name="frm" action="ProfilazioneServizi.jsp"  method="get" onsubmit="return checkForm(document.ProfilazioneServizi);">
<input name="azione" type="hidden" />
<input name="pkid" type="hidden" />
<table class = "LabelForm"  border = "0"  cellpadding="10" cellspacing="10" align="center">
  <tr>
  <td valign="top">
    <table style="font-size:12px">
      <tr ><td align="center"><b>Tipo<b></td></tr>
      <tr>
        <td height="1px"></td>
      </tr>
      <tr>
      <td>
        <select name="tipo" onchange="listaProfilazioni()" class="Campi">
          <c:forEach items="${tipi}" var="tipo_profilazione">
            <option   
              value="<c:out value='${tipo_profilazione[0]}' />" 
              <c:if test='${tipo eq tipo_profilazione[0]}'>selected</c:if>
            />
            <c:out value='${tipo_profilazione[1]}' />
            </option>
          </c:forEach>
        </select>
      </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td align="right">
          <input type="button" class = "pulsante" name="aggiungi" value="Aggiungi" onclick="doAction('aggiungi');" />
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td align="right">
          <input type="button" disabled class = "pulsante" name="modifica" value="Modifica" onclick="doAction('modifica');" />
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td align="right">
          <input type="button" disabled class = "pulsante" name="elimina" value="Elimina" onclick="doAction('elimina');" />
        </td>
      </tr>
    </table>
  </td>
  
  <td valign="top" >
      <table align="right" cellspacing="0" style="font-size:12px">
        <tr>
          <td>
            <table align="right" width="520px">
            <tr>
              <th  width="20" ></th>
              <th  width="160">Id</th>
              <th  width="250">Descrizione</th>
              <th  width="60" >Ordinale</th>
              <th  width="20"></th>
            </tr> 
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <div style="overflow:auto; height:230px; width:525px;background-color:#F0F8FF;" >
               <table class="griglia" align ="right" width="500px" >
                  <tr>
                    <th height="1px"></th>
                  </tr>
                  <c:forEach items="${valori}" var="valore">
                    <tr>
                    <td  width="20" height="18"><input name="sel" type="radio" 
                      onClick="selectRadio(document.frm)"
                      value="<c:out value='${valore[0]}'/>"
                      />
                    </td>
                    <td width="160"><c:out value='${valore[0]}'/></td>
                    <td width="250"><c:out value='${valore[1]}'/></td>
                    <td width="60" ><c:out value='${valore[2]}'/></td>
                    </tr>
                  </c:forEach>
                </tr>
                <c:forEach begin="0" end="15">
                <tr>
                    <td  width="20" height="18"></td>
                    <td width="160">&nbsp;</td>
                    <td width="250"></td>
                    <td width="60" ></td>
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

</form>
</body>

