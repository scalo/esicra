<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!-- 

  TODO
  formattazione data
  decodifica stati , durata (ms)
   
-->

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../siscotel.css">
<TITLE>Transazioni</TITLE>
</head>
<body class = "body">

<!-- barra menu -->
<c:import url="../barramenu.jsp">
    <c:param name="menu"
         value="|Menù Principale, ../EesMenu.jsp, ../myimg/home.jpg |Area Monitoraggio,../EesMonitoraggio.jsp|"/>
</c:import>
    
<c:import url="../header.jsp">
  <c:param name="titolo_pagina"
           value="  Monitoraggio"/>
  <c:param name="titolo_contenuto"
           value="Transazioni"/>
</c:import>

<hib:session>
<hib:find var="transazioni"  maxResults="150">
    from SerTransazione order by dtMod desc
</hib:find>
</hib:session>

<table border="0" width="100%" align="center" cellspacing="0"
       cellpadding="0">
    <tr>
        <td>
            <table class="LabelForm" cellspacing="0" cellpadding="0"
                   border="0" width="100%">
                <tr class="HelpRigaSezione">
                    <td colspan="6" height="2"></td>
                </tr>
                <tr class="PrimaRiga">
                   <td width ="10%" align = "center">Codice Transazione</td>   
                   <td width ="10%" align = "center">Id. Ente Destinatario</td>
                   <td width ="10%" align = "center">Stato</td>
                   <td width ="20%" align = "center">Durata</td>
                   <td width ="30%" align = "center">Nota</td>
                   <td width ="20%" align = "center">Data</td
                </tr>
                <tr class="HelpRigaSezione">
                    <td colspan="6" height="1"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <div style="overflow:auto; height:386px">
                <table class="LabelForm" cellspacing="0" cellpadding="0"
                       border="0" width="100%">
                    <c:forEach var="transazione" items="${transazioni}"  varStatus="status">
                        <tr class='<c:out value="riga${status.count %2}" />'>
                        
                            <td width="10%" align="center">
                                <c:out value="${transazione.codTransazione}"/>
                            </td>
                            <td width="10%" align="center">
                                <c:out value="${transazione.idEnteDestinatario}"/>
                            </td>
                            <td width="10%" align="center">
                                <c:out value="${transazione.stato}"/>
                            </td>
                            <td width="20%" align="center">
                                <c:out value="${transazione.durata}"/>
                            </td>
                            <td width="30%" align="center">
                                <c:out value="${transazione.nota}"/>
                            </td>
                            <td width="20%" align="center">
                                <fmt:formatDate value="${transazione.dtMod}" pattern="dd/MM/yyyy hh:mm" />
                            </td>
                          
                        </tr>
                        
                    </c:forEach>
                    <!--
                    <tr class = "HelpRigaSezione" >
                      <td colspan = "6" height="1"></td>
                    </tr>
                    -->
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