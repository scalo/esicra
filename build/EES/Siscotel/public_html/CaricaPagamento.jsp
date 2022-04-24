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

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="esicra.css">
<TITLE>Gestione Pagamenti</TITLE>
</head>
<script type="text/javascript" >
   
   function annoCorrente(){
      var today = new Date( );
      return today.getFullYear();
   }
   
   function campiDefault(){
      document.GestionePagamenti.anno.value=annoCorrente();
   }
   
   function verificaCampi() 
   {
     validaMoney(document.GestionePagamenti.importoDaPagare);
     validaMoney(document.GestionePagamenti.importoPagato);
     validaMoney(document.GestionePagamenti.importoTotale);
     validaData(document.GestionePagamenti.dtPagamento);
     validaData(document.GestionePagamenti.dtDistinta);
     validaData(document.GestionePagamenti.dtRegistrazione);
           
     if( (trim(document.GestionePagamenti.anno.value) != "") && (!isInteger(document.GestionePagamenti.anno.value) )) {
              alert("Anno non valido");
              document.GestionePagamenti.anno.focus();
              return false;
     } else if ( (trim(document.GestionePagamenti.importoDaPagare.value) != "") && (controllaMoney(document.GestionePagamenti.importoDaPagare.value) == false) ) {
          document.GestionePagamenti.ImportoDaPagare.focus();
          return false;
      } else if( (trim(document.GestionePagamenti.importoTotale.value) != "") && (controllaMoney(document.GestionePagamenti.importoTotale.value) == false)) {
          document.GestionePagamenti.importoTotale.focus();
          return false;
      } else if( (trim(document.GestionePagamenti.importoPagato.value) != "") && (controllaMoney(document.GestionePagamenti.importoPagato.value) == false)) {
          document.GestionePagamenti.importoPagato.focus();
          return false;
       } else if ((trim(document.GestionePagamenti.dtPagamento.value) != "") && (!controllaData(document.GestionePagamenti.dtPagamento.value))) {
                 document.GestionePagamenti.dtPagamento.focus();
                 return false;
       } else if ((trim(document.GestionePagamenti.dtDistinta.value) != "") && (!controllaData(document.GestionePagamenti.dtDistinta.value))){
                 document.GestionePagamenti.dtDistinta.focus();
                 return false;
     } else if ( validaNumero(document.GestionePagamenti.numRata) == false ) {
          document.GestionePagamenti.numRata.focus();
          return false;
       } else if ((trim(document.GestionePagamenti.dtScadenza.value) != "") && (!controllaData(document.GestionePagamenti.dtScadenza.value))){
                 document.GestionePagamenti.dtScadenza.focus();
                 return false;
       } else if ((trim(document.GestionePagamenti.dtRegistrazione.value) != "")&& (!controllaData(document.GestionePagamenti.dtRegistrazione.value))) {
                 document.GestionePagamenti.dtPagamento.focus();
                 return false;
       } else {
        return true;       
       }
    }

    function validaMoney(campo) {
      if (campo != null && trim(campo.value) != "") {
         campo.value = normalizzaMoney(campo.value);
      }
      return true;
    }

    function validaData(campo) {
      campo.value = normalizzaData(campo.value);
      return true;
    }


   function validaNumero(campo) {
      campo.value = trim(campo.value);
      var RegExp=/\D/ 
      if(RegExp.test(campo.value)) {
       	alert("Attenzione è richiesto un valore numerico");
        return false
      }
      return true;        
   }

</script>

<body class = "body" onload="campiDefault()">
<hib:session>
<c:import url="barramenu.jsp">
      <c:param name="menu"
               value="|Indietro ,PraticaEdit.jsp?pkid=${param.testa_pkid}|ListaPratiche,RicercaPratiche.jsp, myimg/cartella.jpg|"/>
    </c:import>
    <br/>
    <c:import url="header.jsp">
      <c:param name="titolo_pagina"
               value="Gestione Pratica"/>
      <c:param name="titolo_contenuto"
               value="Nuovo Pagamento"/>
    </c:import>
    <br/>
<jsp:useBean id="today" class="java.util.Date" />

<hib:load var="testa" classname="it.saga.siscotel.db.PraPraticaTesta" value="${param.testa_pkid}" />

<c:choose>
  <c:when test='${param.carica eq "carica"}' >
     <jsp:useBean id="pagamento" class="it.saga.siscotel.db.PraPraticaPag" />

    <!-- salva pagamento-->
    <c:set target="${pagamento}" property="dtMod" value="${today}" />
    <c:set target="${pagamento}" property="idEnte" value="${param.id_ente}" />
    <c:set target="${pagamento}" property="idEnteDestinatario" value="${param.id_ente_destinatario}" />
    <c:set target="${pagamento}" property="numRata" value="${param.numRata}" />
    <c:set target="${pagamento}" property="anno" value="${param.anno}" />
    <c:set target="${pagamento}" property="distinta" value="${param.distinta}" />
    <c:set target="${pagamento}" property="causale" value="${param.causale}" />
    <c:set target="${pagamento}" property="praPraticaTesta" value="${testa}" />
    <c:set target="${pagamento}" property="codTipoPagamento" value="${param.codTipoPagamento}" />
    <c:set target="${pagamento}" property="desTipoPagamento" value="${param.desTipoPagamento}" />
    <c:set target="${pagamento}" property="codBolletta" value="${param.codBolletta}" />
     
    <!--
      unica cosa da fare a causa dei BigDecimal  che hanno problemi di castin con le jstl   
    -->
    <%
  
      String importoPagato= request.getParameter("importoPagato");
      String importoDaPagare= request.getParameter("importoDaPagare");
      String importoTotale= request.getParameter("importoTotale");
      String idServizio = request.getParameter("idServizio");
      
      if (importoPagato!=null && importoPagato.trim().length()>0)
        pageContext.setAttribute("importoPagato",new BigDecimal(request.getParameter("importoPagato")));
      if(importoDaPagare!=null && importoDaPagare.trim().length()>0)
        pageContext.setAttribute("importoDaPagare",new BigDecimal(request.getParameter("importoDaPagare")));
      if(importoTotale!=null && importoTotale.trim().length()>0)
        pageContext.setAttribute("importoTotale",new BigDecimal(request.getParameter("importoTotale")));
      if(idServizio!=null && idServizio.trim().length()>0)
        pageContext.setAttribute("idServizio",new BigDecimal(request.getParameter("idServizio")));
      
    %>
    <fmt:parseDate var="dtScadenza" pattern="dd/MM/yyyy" value="${param.dtScadenza}" />
    <fmt:parseDate var="dtPagamento" pattern="dd/MM/yyyy" value="${param.dtPagamento}" />
    <fmt:parseDate var="dtRegistrazione" pattern="dd/MM/yyyy" value="${param.dtRegistrazione}" />
    <fmt:parseDate var="dtScadenza" pattern="dd/MM/yyyy" value="${param.dtScadenza}" />
    <!-- bigdecimal-->
    <c:set target="${pagamento}" property="importoPagato" value="${importoPagato}" />
    <c:set target="${pagamento}" property="importoDaPagare" value="${importoDaPagare}" />
    <c:set target="${pagamento}" property="importoTotale" value="${importoTotale}" />
    <c:set target="${pagamento}" property="idServizio" value="${idServizio}" />
    
    <c:set target="${pagamento}" property="dtDistinta" value="${dtDistinta}" />
    <c:set target="${pagamento}" property="dtPagamento" value="${dtPagamento}" />
    <c:set target="${pagamento}" property="dtRegistrazione" value="${dtRegistrazione}" />
    <c:set target="${pagamento}" property="dtScadenza" value="${dtScadenza}" />
    
    <hib:save target="${pagamento}" />
    
    <c:redirect url="PraticaEdit.jsp?pkid=${param.testa_pkid}" />
    
  </c:when>
  <c:otherwise>
  </c:otherwise>
</c:choose>

<form name="GestionePagamenti" action="CaricaPagamento.jsp"  method="post" onsubmit="return verificaCampi()" >
<!-- attributi non presenti nel form -->
<input type="hidden" name="id_ente_destinatario" value='<c:out value="${testa.idEnteDestinatario}" />'/>
<input type="hidden" name="id_ente" value='<c:out value="${testa.idEnte}" />'/>
<input type="hidden" name="testa_pkid" value='<c:out value="${testa.pkid}" />'/>
<input type="hidden" name="idServizio" value='<c:out value="${testa.idServizio}" />'/>

<table align="center" class = "LabelForm">
    <tr>
        <td  align = "center">
            <table border ="1" class = "LabelForm">
                <tr>
                    <td>
                        <table class = "LabelForm"> 
                           <tr><td colspan="5" align="center"><b>Bolletta di Riferimento</b></td></tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                                <td>Codice:</td>
                                <td><input type="text" name="codBolletta" value='<c:out value="${pagamento.codBolletta}" />' class = "campi"></td>
                                <td>Servizio:</td>
                                <td><b><c:out value="${testa.idServizio}" /></b></td>                                
                            </tr>
                            <tr>
                                <td>Anno:</td>
                                <td><input type="text" name="anno"  class = "campi"></td>
                                <td>Num. Rata:</td>
                                <td><input type="text" name="numRata"  class = "campi"></td>
                            </tr>
                            <tr>
                                <td>Causale</td>
                                <td colspan = 3><input type="text" name="causale"   class = "campi"></td>
                            </tr>
                            <tr>
                                <td>Importo Rata:</td>
                                <td><input type="text" name="importoDaPagare"   onblur="validaMoney(this);" class = "campi" ></td>
                                <td>Totale:</td>
                                <td><input type="text" name="importoTotale"   onblur="validaMoney(this);"  class = "campi"></td>
                            </tr>
                            <tr>
                                <td>Data Scadenza:</td>
                                <td>
                                <input type="text" name="dtScadenza" id="dtScadenza"
                                            onblur="validaData(this)"
                                           size="18" class="campi"></input>
                                    <img alt="calendar" align="top" src="myimg/calendar.gif" id="trigger0"
                                         style="cursor: pointer;" onmouseover="this.style.background='blue';"
                                         onmouseout="this.style.background=''"></img>
                                    <script type="text/javascript">
                                          Calendar.setup({
                                              inputField : "dtScadenza",
                                              ifFormat : "%d/%m/%Y",
                                              button : "trigger0", 
                                              step  :  1   
                                          });
                                    </script>
                               </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <br>
            <br>

            <table border ="1" class = "LabelForm"> 
                <tr>    
                    <td>
                        <table class = "LabelForm">                           
                            <tr><td colspan="5" align="center"><b> Dati di Pagamento</b></td></tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                                <td>Importo Pagato:</td>
                                <td><input type="text" name="importoPagato" onblur="validaMoney(this);"  class = "campi"></td>
                                <td>Data Pagamento:</td>
                                <td>
                                  <input type="text" name="dtPagamento" id="dtPagamento"
                                           value='<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" />' onblur="validaData(this)"
                                           size="18" class="campi"></input>
                                    <img alt="calendar" align="top" src="myimg/calendar.gif" id="trigger1"
                                         style="cursor: pointer;" onmouseover="this.style.background='blue';"
                                         onmouseout="this.style.background=''"></img>
                                    <script type="text/javascript">
                                          Calendar.setup({
                                              inputField : "dtPagamento",
                                              ifFormat : "%d/%m/%Y",
                                              button : "trigger1", 
                                              step  :  1   
                                          });
                                    </script>
                                </td>
                            </tr>
                            <tr>
                                <td>Distinta Avvenuto Pagamento:</td>
                                <td><input type="text" size="30" name="distinta"  class = "campi"></td>
                                <td>Data Distinta:</td>
                                <td>
                                    <input type="text" name="dtDistinta" id="dtDistinta"
                                           value='<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" />' onblur="validaData(this)"
                                           size="18" class="campi"></input>
                                    <img alt="calendar" align="top" src="myimg/calendar.gif" id="trigger2"
                                         style="cursor: pointer;" onmouseover="this.style.background='blue';"
                                         onmouseout="this.style.background=''"></img>
                                    <script type="text/javascript">
                                          Calendar.setup({
                                              inputField : "dtDistinta",
                                              ifFormat : "%d/%m/%Y",
                                              button : "trigger2", 
                                              step  :  1   
                                          });
                                    </script>
                                </td>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>Data Registrazione:</td>
                                <td>
                                  <input type="text" name="dtRegistrazione" id="dtRegistrazione"
                                           value='<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" />' onblur="validaData(this)"
                                           size="18" class="campi"></input>
                                    <img alt="calendar" align="top" src="myimg/calendar.gif" id="trigger3"
                                         style="cursor: pointer;" onmouseover="this.style.background='blue';"
                                         onmouseout="this.style.background=''"></img>
                                    <script type="text/javascript">
                                          Calendar.setup({
                                              inputField : "dtRegistrazione",
                                              ifFormat : "%d/%m/%Y",
                                              button : "trigger3", 
                                              step  :  1   
                                          });
                                    </script>
                                </td>
                            </tr>
                            <tr>
                                <td>Cod. Tipo:</td>
                                <td><input type="text" name="codTipoPagamento" class = "campi"></td>
                                <td>Des. Tipo:</td>
                                <td><input type="text" name="desTipoPagamento" class = "campi"></td>
                            </tr>
                       </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>      
<br>

<p align="center">
<input type="submit" name="carica" value="carica" onclick="" class="pulsante">
</p>

</form>
</hib:session>
</body>


