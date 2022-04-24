<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="siscotel.css">

<script type="text/javascript" src="decode.js">
</script>

<script type="text/javascript" >
  
  function confermaEliminazione(pkid ){
    res = confirm("La tabella verrà rimossa definitivamente\n dalla lista delle tabelle da coordinare");
    if(res){
      window.location="eCoordinatorSalva.jsp?pkid="+pkid+"&azione=elimina";
    }else{
      //alert("tabella non cancellata");
    }
  }

</script>

<TITLE>Tabelle Coordinator</TITLE>
</head>
<body class = "body">

<c:import url="barramenu.jsp">
    <c:param name="menu"
         value="|Menù Principale, EesMenu.jsp, myimg/home.jpg |Menù Amministrazione,EesAmministrazione.jsp|Carica Nuova Tabella,eCoordinatorEdit.jsp?azione=crea |"/>
</c:import>
    
<c:import url="header.jsp">
  <c:param name="titolo_pagina"
           value="  Coordinazione"/>
  <c:param name="titolo_contenuto"
           value="Tabelle da Sincronizzare"/>
</c:import>

<hib:session>
<hib:find var="tabelle"  maxResults="150">
    from SerCoordinator order by ordine asc
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
                  <td width ="10%" align = "center">&nbsp;</td>
                  <td width ="40%" align = "center">Nome</td>   
                  <td width ="20%" align = "center">Ordine</td>
                  <td width ="10%" align = "center">Da Allineare</td>
                  <td width ="10%" align = "center">Bidirezionale</td>
                  <td width ="10%" align = "center">Da Cancellare</td>
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
                    <c:forEach var="tabella" items="${tabelle}"  varStatus="status">
                        <tr class='<c:out value="riga${status.count %2}" />'>
                            <td width="10%" align="center">
                                <a href= "eCoordinatorEdit.jsp?pkid=<c:out value="${tabella.pkid}" />&azione=modifica ">Modifica</a>
                                | <a href= "javascript:confermaEliminazione(<c:out value="${tabella.pkid}"/>)">Elimina</a> 
                           </td>
                            <td width="40%" align="center">
                                <c:out value="${tabella.nomeTabella}"/>
                            </td>
                            <td width="20%" align="center">
                                <c:out value="${tabella.ordine}"/>
                            </td>
                            <td width="10%" align="center">
                                <script type="text/javascript">decodeBoolean('<c:out value="${tabella.flAllinea}"/>')</script>
                            </td>
                            <td width="10%" align="center">
                                <script type="text/javascript">decodeBoolean('<c:out value="${tabella.flBidir}"/>')</script>
                            </td>
                            <td width="10%" align="center">
                                <script type="text/javascript">decodeBoolean('<c:out value="${tabella.flCancellabile}"/>')</script>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </td>
    </tr> 
    <tr class = "HelpRigaSezione" >
      <td colspan = "9" height="1"></td>
    </tr>
</table>

</body>
</html>