<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page session="false" %>
<%@ page import="it.saga.egov.esicra.timer.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %> 

<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">

<title>Timer  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>

<!--style type="text/css" src="timer.css">
</style-->
</head>
<body>

<!-- Recupera l'istanza dello scheduler -->
<%
EesTimer timer = EesTimerConf.getInstance();
response.setHeader("Refresh","10"); // ogni 10 secondi
%>
<script type="text/javascript" language="javascript" src="timer.js">
</script>

<!--
<h1 align="center" >Controllo Esicra Timer</h1>
-->

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
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
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" height="20"> 
    <TD>&nbsp;&nbsp;Controllo Timer</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" height="20">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>


<br/>
<table align="center" border="1">
  <tr class="PrimaRiga">
   <td align="center"><b>Timer</b>
   </td>
   <td align="center">
    <script type="text/javascript" language="javascript">
      document.write(status(<%=(timer==null)?false:true %>));
    </script>
   </td>
  </tr>
  <tr>
   <td colspan="2"><%= DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.DEFAULT,Locale.ITALY).format(new Date()) %>
     <%-- = DateFormat.getDateInstance().format(new Date(System.currentTimeMillis()))   //new Date(System.currentTimeMillis())
      --%>
   </td> 
  </tr>

</table>
<br/>
<form action="EesTimerServlet" method="get">

<!--  CAMPI NASCOSTI  passati come parametri fondamentali alla servlet -->

<input type="hidden" name="operazione" value="nulla" >
<input type="hidden" name="id" value="nulla" >

<table border="0" width="100%" >
  <tr>
    <td > </td> 
    <td rowspan="3" width="50" height="30">
      <table>
      <tr>
        <td>
          <!-- campo debug/sviluppo
          <input value="Aggiungi test(debug)" type="button" onclick="aggiungiTest(this.form)" />
           -->
        </td> 
      </tr>
      <tr>
        <td>
          <input value="Aggiungi" type="button" onclick="aggiungi()" class="pulsante" />
        </td> 
      <tr>
        <td>
          <input disabled=true  value="Modifica" type="button" onclick="modifica()"  class="pulsante"/>
        </td> 
      </tr>
      <tr>
        <td>
          <input value="Cancella" type="button"  onclick="cancella(this.form)" class="pulsante"/> 
        </td>
      </tr>
      <tr>
        <td>
          <input value="Rimuovi Completati" type="button" onclick="rimuoviCompletati(this.form)" class="pulsante" />
        </td>
      </tr>
      </table>
    </td></tr>
  <tr>
		<td >
      <table border="1" align="center" >
       <colgroup span="1">
          <col width="5%"/>
          <col width="8%"/>
          <col width="15%"/>
          <col width="15%"/>
          <col width="5%"/>
          <col width="20%"/>
          <col width="15%"/>
          <col width="5%"/>
      </colgroup>
      <tr class="Testata" height="25">
      <th ></th><th>Id</th><th>Task</th><th>Data Inizio</th><th>Frequenza</th><th>Giorni Settimana</th><th>Data ultima esecuzione</th><th>Stato</th>
      </tr>
      <!--  Elenco dei task-->
      <%  Iterator ite = timer.list().iterator();
          int cont=0;
          while(ite.hasNext()){
            EesTask task = (EesTask)  ite.next();
      %>
      <tr align=center class="<%=cont%2==0?"RigaPari":"RigaDispari" %>" height="25" >
        <td>
          <input type="radio" name="selezione" value="<%=task.getId() %>" unchecked>
        </td>
        <td> <%=task.getId()%> </td>
        <td> <%=task.nome()%> </td>
        <td> <%=task.getFormatDtInizio()%> </td>
        <td> <%=task.getFormatFrequenza()%> </td>
        <td> <%=task.getFormatSettimana()%> </td>
        <td> <%=task.getFormatUltimaEsecuzione()%> </td>
        <td> <%=task.getFormatStato()%> </td>
      </tr>
      <%
      	  	cont++;
      		}
      %>
      </table>
    	</td>
    	<td></td>  
  	</tr>
  	<tr><td></td></tr>
  <tr><td><input type="reset"  value="Deseleziona" class="pulsante"></td></tr>
		</table>
</form>
<!--
<table border="0" width="100%" height="100">
  <tr >
		<td width="200" align=right heigth="30" cellpadding="30">
      <input type="button" value="Esci" onClick="esci()" class="pulsante"/>
    </td>
	</tr>
</table>
<table>
  <tr><td><br></td></tr>
  <tr>
   <td>
     <a href="EesMenu.jsp">Ritorna al Menu </a>
    </td> 
  </tr>  
</table>
-->
</body>
</html>

