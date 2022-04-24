<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!--
  TODO 
  
    error page GestioneErrori.jsp
    allineare la pagina
    eliminazione
    formattazione data
   
-->

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../siscotel.css">

<script type="text/javascript" >
  
  function confermaEliminazione(pkid ){
    res = confirm("L\'informazione verrà rimossa definitivamente\n dalla lista dei flussi importati");
    if(res){
      window.location="eImportazioneSalva.jsp?pkid="+pkid+"&azione=elimina";
    }else{
      //alert("flusso non cancellato");
    }
  }

</script>

<TITLE>Flussi Importati</TITLE>
</head>
<body class = "body">

<hib:session>
<hib:find var="flussi"  maxResults="150">
    from SerImport order by dtMod desc
</hib:find>
</hib:session>

<!-- barra menu -->
<c:import url="../barramenu.jsp">
    <c:param name="menu"
         value="|Menù Principale, ../EesMenu.jsp, ../myimg/home.jpg |Area Monitoraggio,../EesMonitoraggio.jsp|"/>
</c:import>
    
<c:import url="../header.jsp">
  <c:param name="titolo_pagina"
           value="  Monitoraggio"/>
  <c:param name="titolo_contenuto"
           value="Flussi Importati"/>
</c:import>

<br>

<table border="0" width="100%" align="center" cellspacing="0"
       cellpadding="0">
    <tr>
        <td>
            <table class="LabelForm" cellspacing="0" cellpadding="0"
                   border="0" width="100%">
                <tr class="HelpRigaSezione">
                    <td colspan="10" height="2"></td>
                </tr>
                <tr class="PrimaRiga">
                  <td width = "5%" align = "center"></td> 
                  <td width ="20%" align = "center">Flusso</td>   
                  <td width ="8%"  align = "center">Documenti In Elaborazione</td>
                  <td width ="10%" align = "center">Durata (sec.)</td>
                  <td width ="8%"  align = "center">Documenti Elaborati</td>
                  <td width ="8%"  align = "center">Documenti non Inseriti</td>
                  <td width ="8%"  align = "center">Documenti Ignorati</td>
                  <td width ="8%"  align = "center">Documenti Non Validati</td>
                  <td width ="20%" align = "center">Data</td>
                  <td width ="5%"  align = "center"></td>
                </tr>
                <tr class="HelpRigaSezione">
                    <td colspan="10" height="1"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <div style="overflow:auto; height:386px">
                <table class="LabelForm" cellspacing="0" cellpadding="0"
                       border="0" width="100%">
                    <c:forEach var="flusso" items="${flussi}"  varStatus="status">
                        <tr class='<c:out value="riga${status.count %2}" />'>
                            <td width="5%" align="center">
                                <a href= "javascript:confermaEliminazione(<c:out value="${flusso.pkid}"/>)">Elimina</a> 
                           </td>
                            <td width="20%" align="center">
                                <c:out value="${flusso.nomeFile}"/>
                            </td>
                            <td width="8%" align="center">
                                <c:out value="${flusso.inElaborazione}"/>
                            </td>
                            <td width="10%" align="center">
                                <c:out value="${flusso.tempo}"/>
                            </td>
                            <td width="8%" align="center">
                                <c:out value="${flusso.elaborati}"/>
                            </td>
                            <td width="8%" align="center">
                                <c:out value="${flusso.ignorati}"/>
                            </td>
                            <td width="8%" align="center">
                                <c:out value="${flusso.rigettati}"/>
                            </td>
                            <td width="20%" align="center">
                                <fmt:formatDate value="${flusso.dtMod}" pattern="dd/MM/yyyy hh:mm" />
                            </td>
                            <td width="5%" align="center">
                                <c:choose>
                                  <c:when test="${flusso.rigettati gt 0 }">
                                      <img alt="red" src="../myimg/bulred1.gif">
                                  </c:when><c:when test="${flusso.ignorati gt 0 }">
                                      <img alt="red" src="../myimg/bulyel1.gif">
                                  </c:when>
                                  <c:otherwise>
                                      <img alt="green" src="../myimg/bulgren1.gif">
                                  </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    
                </table>
            </div>
        </td>
    </tr> 
</table>
<table width="100%">
  <tr class = "HelpRigaSezione" >
    <td colspan = "10" height="1"></td>
  </tr>
</table>
</body>
</html>