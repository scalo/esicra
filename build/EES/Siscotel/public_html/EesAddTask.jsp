<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="
java.sql.Date,
it.saga.egov.esicra.timer.servlet.EesHtmlUtils
" %>

<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">

<title>Aggiungi Task <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>

<!--style type="text/css" src="timer.css">
</style-->
<script type="text/javascript" language="javascript" src="addtask.js" >
</script>
<script type="text/javascript">
      function taskChanged(){
            var tipo_task=document.forms[0].task.value;
            //var disabled = document.forms[0].task.disabled;
            //alert("Task changed \n tipo task "+tipo_task+"\disabled="+disabled);
           
            if(tipo_task=="ExportPagamentiTask"){
                //alert(tipo_task);
                document.forms[0].servizio_pagamento.disabled=false;
                document.forms[0].servizio_pratica.disabled=true;
                document.forms[0].data_ini.disabled=false;
                document.forms[0].data_fine.disabled=false;
                //document.getElementById("servizio_pagamento").style.display="block";
                //document.getElementById("servizio_pratiche").style.display="none"
            }else
            if(tipo_task=="ExportPraticheTask"){
                //alert(tipo_task);
                document.forms[0].servizio_pratica.disabled=false;
                document.forms[0].servizio_pagamento.disabled=true;
                document.forms[0].data_ini.disabled=false;
                document.forms[0].data_fine.disabled=false;
                //document.getElementById("servizio_pratiche").style.display="block";
                //document.getElementById("servizio_pagamento").style.display="none";
            }else{
                document.forms[0].servizio_pagamento.disabled=true;
                document.forms[0].servizio_pratica.disabled=true;
                document.forms[0].data_ini.disabled=true;
                document.forms[0].data_fine.disabled=true;
            }
      }
</script>

</head>
<body>

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
    		<td>|</td>
        <td>
            <a href= "EesTimer.jsp">Ritorna al Timer Admin</a>
        </td>
        <td>|</td>
        <td>
            <a href= "EesAmministrazione.jsp">Menù Amministrazione </a>
        </td>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" align="absmiddle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>
<br>



<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" height="20"> 
    <TD>&nbsp;&nbsp;Aggiungi  Task </TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" height="20">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>


<form name="task" action="EesTimerServlet" method="get">

<!-- PARAMETRI NASCOSTI calcolati e passati alla servlet -->
<input type="hidden" name="operazione" value="" >
<!--
<input type="hidden" name="tipo_task" value="" >
-->
<input type="hidden" name="data_evento" value="" >
<input type="hidden" name="frequenza" value="" >
<input type="hidden" name="giorni_settimana" value="" >

<br/>
<br/>

<table width="100%" border="1" class ="form" cellpadding="1" cellspacing="1" >
  <colgroup span="1">
    <col width="25%"/>
    <col width="25%"/>
    <col width="25%"/>
    <col width="25%"/>
  </colgroup>
  <tr>
  <td>
    <STRONG>Tipo Task: </STRONG>
    <select name="task" size="1"  onChange="taskChanged()">
      <option value="" selected="selected" ></option>
      <%
        String[] tipi =EesHtmlUtils.tipi();
        for(int i=0;i<tipi.length;i++){
          out.println(tipi[i]);
        }
      %>
    </select>
  </td>
  </tr>
  <tr>
  <td id="servizio_pagamento" >
    <STRONG>Pagamenti: </STRONG>
      <select disabled name="servizio_pagamento" size="1"  >
      <option value="" selected="selected" ></option>
      <%
        String[] servizi = EesHtmlUtils.serviziPagamenti();
        for(int i=0;i<servizi.length;i++){
          out.println(servizi[i]);
        }
      %>
    </select>
  </td>
  <td id="servizio_pratica" >
    <STRONG>Pratiche: </STRONG>
      <select disabled name="servizio_pratica" size="1"  >
      <option value="" selected="selected" ></option>
      <%
        servizi = EesHtmlUtils.serviziPratiche();
        for(int i=0;i<servizi.length;i++){
          out.println(servizi[i]);
        }
      %>
    </select>
  </td>
  <td>
    <STRONG>Data&nbsp;inizio&nbsp;export : 
    <input disabled type="text" name="data_ini" maxlength="10" size="10" 
    value="<%=EesHtmlUtils.maxDtExport()%>"/></STRONG>
    </td>
  <td>
    <STRONG>Data fine export:</STRONG>
  <input disabled type="text" name="data_fine" maxlength="10" size="10" 
  value="<%=EesHtmlUtils.curDate()%>"/></td>
  </tr>
</table>

<table width="100%" border="1" class ="form">
  <colgroup span="1">
    <col width="10%"/>
    <col width="1%"/>
    <col width="30%"/>
    <col width="25%"/>
    <col width="25%"/>
  </colgroup>
  <!--
  <tr>
  <td>
    <STRONG>Tipo Task</STRONG>
  </td>
  <td></td>
  <td>
    <select name="task" size="1" col="10">
      <option value="" selected="selected" ></option>
      <%
        /*
        String[] tipi =EesHtmlUtils.tipi();
        for(int i=0;i<tipi.length;i++){
          out.println(tipi[i]);
        }
        */
      %>
    </select>
  </td>
  <td>
    <STRONG>Servizio: </STRONG><select name="servizio" size="1" col="10">
      <option value="" selected="selected" ></option>
      <%
        /*
        String[] servizi = EesHtmlUtils.servizi();
        for(int i=0;i<servizi.length;i++){
          out.println(servizi[i]);
        }
        */
      %>
    </select>
  </td>
  <td>
    <STRONG>Data&nbsp;inizio&nbsp;export : 
    <input type="text" name="data_ini" maxlength="10" size="10" value="<%=new Date(System.currentTimeMillis()).toString()%>"/></STRONG>
    </td>
  <td>
    <STRONG>Data fine export:</STRONG>
  <input type="text" name="data_fine" maxlength="10" size="10" value="<%=new Date(System.currentTimeMillis()).toString()%>"/></td>
  </tr>
  -->
  <tr>
  <td>
    <STRONG>Data Inizio</STRONG>
  </td>
  <td></td>
  <td >
    <STRONG>Anno: </STRONG><select name="anno" size="1">
      <option value="" selected="selected"></option>
      <%
        String[] anni=EesHtmlUtils.anni();
        for(int i=0;i<anni.length;i++){
          out.println(anni[i]);
        }
      %>
    </select>
  </td>
  <td >
    <STRONG>Mese: </STRONG><select name="mese" size="1" col="10">
      <option value="" selected="selected" ></option>
      <%
        String[] mesi=EesHtmlUtils.mesi();
        for(int i=0;i<mesi.length;i++){
          out.println(mesi[i]);
        }
      %>
    </select>
  </td>
  <td colspan="2">
    <STRONG>Giorno: </STRONG><select name="giorno" size="1" col="10">
      <option value="" selected="selected" ></option>
      <%
        String[] giorni=EesHtmlUtils.giorni();
        for(int i=0;i<giorni.length;i++){
          out.println(giorni[i]);
        }
      %>
    </select>
  </td>
  </tr>
  <tr>
  <td >
    <STRONG>Orario</STRONG>
  </td>
  <td></td>
  <td>
    <STRONG>Ora: </STRONG><select name="ora" size="1" col="10">
      <option value="" selected="selected" ></option>
      <%
        String[] ore=EesHtmlUtils.ore();
        for(int i=0;i<ore.length;i++){
          out.println(ore[i]);
        }
      %>
    </select>
  </td>
  <td>
    <STRONG>Minuti: </STRONG><select name="minuti" size="1" col="10">
      <option value="" selected="selected" ></option>
        <% 
        String[] minuti=EesHtmlUtils.minuti();
        for(int i=0;i<minuti.length;i++){
          out.println(minuti[i]);
        }
      %>
    </select>
  </td>
  <td colspan="2"></td>
  </tr>
  <tr>
  <td>
    <STRONG>Frequenza</STRONG>
  </td>
  <td></td>
  <td>
    <STRONG>Giorni
    <br/>Ore
    <br/>Minuti</STRONG></td>
  <td> 
   <input name="fg" type="text" size="5" value="0"><br>
   <input name="fo" type="text" size="5" value="0"><br>
   <input name="fm" type="text" size="5" value="0">
  </td>
  <td rowspan="2" colspan="3">
    <STRONG>Descrizione Task </STRONG>
  <textarea name="descrizione" rows="10" cols="40" ></textarea></td>
  <tr>
    <td>
      <STRONG>Giorni della Settimana</STRONG>
    </td>
    <td></td>
    <td >
      <input name="lunedi" value="0" type="checkbox" checked >Lunedì<br/>
      <input name="martedi" value="1" type="checkbox" checked>Martedì<br/>
      <input name="mercoledi" value="2" type="checkbox" checked>Mercoledì<br/>
      <input name="giovedi" value="3" type="checkbox" checked>Giovedì<br/>
      <input name="venerdi" value="4" type="checkbox" checked>Venerdì<br/>
      <input name="sabato" value="5" type="checkbox" checked>Sabato<br/>
      <input name="domenica" value="6" type="checkbox" checked>Domenica<br/>
    </td>
    <td>
      
      <table>
      <!--
        <tr><td>
          <input type="button" value="Seleziona"  onclick="selezionaTutti(this.form)" >
        </td></tr>
        <tr><td>
          <input type="button" value="Deseleziona" onclick="deselezionaTutti(this.form)" >
        </td></tr>
      -->
      </table>
     
    </td>
  </tr>
</table>
<br><br>
<table align=center>
<tr>
  <td height="18">
    <input type="reset"  value="Cancella valori" class="pulsante" >
  </td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
  <td height="18">
    <input type="button" value="Aggiungi  Task  " onclick="validaForm(this.form)" class="pulsante" >
  </td>
</tr>
<!--
<tr><td>&nbsp;</td></tr>
<tr><td>&nbsp;</td></tr>
<tr>
  <td>
    <a href="EesMenu.jsp">Ritorna al Menu</a>
  </td>
  <td>
    <a href="EesTimer.jsp">Ritorna al Timer Admin</a>
  </td>
</tr>
-->
</table>
</form>
</body>
</html>

