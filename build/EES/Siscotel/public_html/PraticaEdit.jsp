<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!--
  TODO 
  
    aggiungere modifica e carica pagamento
-->

<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <link rel="STYLESHEET" type="text/css" href="siscotel.css"></link>
    <script type="text/javascript" src="decode.js" ></script>
    <title>
      Visualizzazione Pratica
    </title>
  </head>
  <body>
  <hib:session>
  
  <hib:load var="pratica" classname="it.saga.siscotel.db.PraPraticaTesta" value="${param.pkid}" />

  <hib:load var="recapito" classname="it.saga.siscotel.db.PraRecapito" value="${param.pkid}" />
  
    <c:import url="barramenu.jsp">
      <c:param name="menu"
               value="|Consulta Pratica Completa,PraticaCompletaShowOld.jsp?pkid=${pratica.pkid}|Scarica Pratica,ScaricaPratica.jsp?pkid=${param.pkid}|Modifica Stato Pratica,ModificaStatoPratica.jsp?testa_pkid=${param.pkid}|Carica Pagamento,CaricaPagamento.jsp?testa_pkid=${param.pkid}|Ricerca Pratiche,RicercaPratiche.jsp, myimg/cartella.jpg |"/>
    </c:import>
    <br/>
    <c:import url="header.jsp">
      <c:param name="titolo_pagina"
               value="Gestione Pratica"/>
      <c:param name="titolo_contenuto"
               value="Pratica: ${param.pkid}"/>
    </c:import>
    <br/>

<hib:find var="servizi">
  from SerServizio
  where idServizio=${pratica.idServizio} 
</hib:find>

<!-- FILTRI -->

<hib:filter var="dettagli" items="${pratica.praPraticaDetts}">
   where this.dtMod is not null order by this.dtMod desc
</hib:filter>

<hib:filter var="pagamenti" items="${pratica.praPraticaPags}">
   where this.dtMod is not null order by this.dtMod desc
</hib:filter>

<hib:filter var="recapiti" items="${pratica.praRecapitos}">
   where this.dtMod is not null 
</hib:filter>

<c:forEach var="inusato" items="${dettagli}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count_dett" value="${status.count}"/></c:if>
</c:forEach>

<c:forEach var="inusato" items="${pagamenti}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count_pag" value="${status.count}"/></c:if>
</c:forEach>

<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center" class="HelpStile">
    <tr>
        <td>
            Servizio:
            <b><c:out value="${servizi[0].desBreve}"/></b>
        </td>
        <td>
            <!--
            Codice BO:
            <b><c:out value="${pratica.codPraticaBo}"/></b>
            -->
        </td>
        <td>
            Data:
            <b><fmt:formatDate value="${pratica.dtPratica}" pattern="dd/MM/yyyy" /></b>
        </td>
    </tr>
    <tr>
        <td>
            CodiceFiscale:
            <b><c:out value="${pratica.codiceFiscale}" /></b>
        </td>
        <td>
            Notifica:
            <b><c:out value="${pratica.tipoNotifica}"/></b>
        </td>
        <td>
            Consegna:
            <b><c:out value="${pratica.codTipoConsegna}"/></b>
        </td>
    </tr>
    <tr>
        <td>
            <!--
            Modalità Allegati:
            <b><b><c:out value="${pratica.desModalitaAllegati}"/></b></b>
            -->
        </td>
        <td>
            Canale:
            <b><c:out value="${pratica.codTipoConsegna}"/></b>
        </td>
        <td>
            Ente Destinatario:
            <b><c:out value="${pratica.idEnteDestinatario}"/></b>
        </td>
    </tr>
    <tr><td> <br> </td></tr>
</table>

<table>
<!-- solo il primo elemento end=1 -->
<c:forEach var="recapito" items="${recapiti}" end="1" >
<tr>
<td valign="top" class="HelpRigaTitolo">Recapito:</td>
<td>
<table class="HelpStile" border="0" width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <c:out value="${recapito.desArea}"/>,<c:out value="${recapito.numCiv}"/> <c:out value="${recapito.letCiv}" />
      <c:if test="${not empty recapito.scala}" >
        Scala:<c:out value="${recapito.scala}"/>
      </c:if>
      <c:if test="${not empty recapito.interno}">
        Interno:<c:out value="${recapito.interno}"/>
      </c:if>
    </td>
  </tr>
  <tr>
    <td>
      <c:out value="${recapito.cap}"/>,<c:out value="${recapito.desComune}"/> <c:out value="${recapito.desProvincia}" />
    </td>
  </tr>
  <tr>
    <td>
      Presso:<c:out value="${recapito.presso}"/>
    </td>
  </tr>
  <tr>
    <td>
      Telefono:<c:out value="${recapito.telefono}"/>
      &nbsp;
      E-mail:<c:out value="${recapito.email}"/>
    </td>
  </tr>
  <tr><td> <br> </td></tr>
  </c:forEach>
</table>
</td>
</tr>
</table>
<br>

<c:choose>
<c:when test="${count_dett gt 0}" >
  <table border=0 cellspacing=0 class = "LabelForm">
    <tr>
      <td class="HelpRigaTitolo">Iter Pratica:</td>
    </tr>
    <tr>
        <td></td>
    </tr>
  </table>
       <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
               <tr class = "HelpRigaSezione" >
                   <td colspan = "9" height="2"></td>
               </tr>
               <tr class="PrimaRiga">
                    <td width ="12%">&nbsp;</td>
                    <td width ="18%" align="left">Stato Pratica</td>
                    <td width ="15%" align="left">Responsabile</td>
                    <td width ="14%" align="left">Data</td>                                        
                    <td width ="40%" align="left">Note</td>                                          
                </tr>
                <tr class = "HelpRigaSezione" >
                    <td colspan = "9" height="1"></td>
                </tr>
            </table>
              </td>
              </tr>
              <tr>
                  <td>
                  <div style="overflow:auto; height:120px">
                      <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                          <c:forEach var="dettaglio" items="${dettagli}" varStatus="status">
                            <tr class="<c:out value="riga${status.count %2}" />">
                              <td width ="12%">
                                  <a href='ModificaNota.jsp?dett_pkid=<c:out value="${dettaglio.pkid}"/>&testa_pkid=<c:out value="${pratica.pkid}"/>' >&nbsp;&nbsp;Modifica Nota</a>
                              </td>
                              <td width ="18%" align="left"><c:out value="${dettaglio.desStatoPratica}" /></td>
                              <td width ="15%" align="left"><c:out value="${dettaglio.desResponsabile}" /></td>                  
                              <td width ="14%" align="left"><fmt:formatDate value="${dettaglio.dtStato}" pattern="dd/MM/yyyy hh:mm" /></td>
                              <td width ="40%" align="left">             
                                  <c:out value="${dettaglio.note}" />                     
                              </td>
                              </tr>
                          </c:forEach>
                      </table>     
                  </div>
                  </td>
                </tr>
             </table>
</c:when>
</c:choose>
<br/>
<c:choose>
<c:when test="${count_pag gt 0}" >
  <table border=0 cellspacing=0 class = "LabelForm">
    <tr>
      <td class="HelpRigaTitolo">Pagamenti:</td>
    </tr>
    <tr>
        <td></td>
    </tr>
  </table>
       <table border="0" width ="100%"  align = "center" cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
               <tr class = "HelpRigaSezione" >
                   <td colspan = "11" height="2"></td>
               </tr>
               <tr class="PrimaRiga">
                   <td width ="8%"  align = "left">&nbsp;</td>
                   <td width ="15%" align = "left">Identificativo</td>
                   <td width ="10%" align = "left">Codice</td>
                   <td width ="8%" align = "left">Scadenza</td>
                   <td width ="8%" align = "left">Data Pag.</td>   
                   <td width ="10%" align = "left">Pagato</td>
                   <td width ="10%" align = "left">Da Pagare</td>
                   <td width ="10"  align = "left">Totale</td>
                   <td width ="7%" align = "left">Anno</td>
                   <td width ="8%" align = "left">Rata</td>
                   <td width ="5%" align = "left"></td>                                        
                </tr>
                <tr class = "HelpRigaSezione" >
                    <td colspan = "11" height="1"></td>
                </tr>
            </table>
              </td>
              </tr>
              <tr>
                  <td>
                  <div style="overflow:auto; height:85px">
                      <table class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
                          <c:forEach var="pagamento" items="${pagamenti}" varStatus="status">
                            <tr class="<c:out value="riga${status.count %2}" />">
                              <td width ="8%" align="left">
                                  <a href='ModificaPagamento.jsp?pag_pkid=<c:out value="${pagamento.pkid}"/>&testa_pkid=<c:out value="${pratica.pkid}"/>' >&nbsp;&nbsp;Modifica </a>
                              </td>
                              <td width ="15%" align="left"><c:out value="${pagamento.pkid}" /></td>
                              <td width ="10%" align="left"><c:out value="${pagamento.codBolletta}" /></td>
                              <td width ="8%" align="left"><fmt:formatDate value="${pagamento.dtScadenza}" pattern="dd/MM/yyyy" /></td>
                              <td width ="8%" align="left"><fmt:formatDate value="${pagamento.dtPagamento}" pattern="dd/MM/yyyy" /></td>
                              <td width ="10%" align="left"><c:out value="${pagamento.importoPagato}" /></td>
                              <td width ="10%" align="left"><c:out value="${pagamento.importoDaPagare}" /></td>                  
                              <td width ="10%" align="left"><c:out value="${pagamento.importoTotale}" /></td>
                              <td width ="7%" align="left"><c:out value="${pagamento.anno}" /></td>
                              <td width ="8%" align="left"><c:out value="${pagamento.numRata}" /></td>
                              <td width ="5%" align="left">
                                <c:choose>
                                  <c:when test="${pagamento.importoPagato eq pagamento.importoDaPagare}">
                                    <img src="images/bulgren.gif" alt="images/bulgren.gif">
                                  </c:when>
                                  <c:otherwise>
                                    <img src="images/bulred.gif" alt="images/bulred.gif">
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
</c:when>
</c:choose>

</hib:session>

</body>
</html>
