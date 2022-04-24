<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="it.saga.egov.esicra.log.*" %>
<%@ page import="it.saga.egov.esicra.*" %>
<%@ page import="it.saga.egov.esicra.vpn.*" %>
<%@ page import="org.apache.log4j.*" %>

<html>
<head>
<meta http-equiv="refresh" content="10;url=EesAssistenzaRemota.jsp">
<link rel=stylesheet href="esicra.css" type="text/css">
<title>Assistenza Remota <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>

<!--

    stati
    0) down
    1) startup
    2) up
    3) shutdown

-->

<script>
  function status(stato){
    var res="";
    switch (stato){
      case 0:
        img="vpn_down.gif";
        des="down";
      break;
      case 1:
        img="vpn_startup.gif";
        des="startup";
      break;
      case 2:
        img="vpn_up.gif";
        des="up"
      break;
      case 3:
        img="vpn_shutdown.gif";
        des="shutdown"
      break;
    }
    res="<img src=\"images/"+img+ "\"/>";
    res+="<h3>"+des+"</h3>"
    //alert("stato="+stato+"\n res="+res);
    return res;
  }
</script>
</head>

<body>
<!--  control -->
<%
  String azione = request.getParameter("azione");
  if (azione==null) azione="test";
  else azione=azione.trim();
  EesAssistenza assist = null;
  //out.println("azione="+azione);
  if(azione.equals("attiva")){
    assist = new EesAssistenza();
    assist.startup();
  }else
  if(azione.equals("disattiva")){
    assist = new EesAssistenza();
    assist.shutdown();
  }else if(azione.equals("test")){
    assist = new EesAssistenza();
    assist.test();
  }
%>
<table border="0" align="right" class="barramenu" > 
    <tr>
        <td>|</td>
        <td>
            <a href= "EesAmministrazione.jsp">Menù Amministrazione </a>
        </td>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" align="middle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>
<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo"> 
    <TD>&nbsp;&nbsp;Assistenza Remota</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>
  <TR  class="HelpRigaTitoloSezione">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR>
</TABLE>
<P>&nbsp;</P>
<table cellspacing="3" cellpadding="2" border="0" align="center">
  <tr>
    <td align="center"><H2>
      <FONT color="#333399">Stato VPN </FONT>
    </H2></td>
  </tr>
  <tr>
    <td align=center><b>(<%=System.getProperty("esicra.vpn.assistenza")!=null?System.getProperty("esicra.vpn.assistenza"):""%>)</b></td>
  </tr>
  <tr>
    <%
      if (assist!=null&&(assist.getStatus()==2)) { %>
        <td><b><FONT color="#6699cc">IP:</FONT></b><%=assist.getVpnIp()%></td>
    <%}else
      %><td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center">
      <script type="text/javascript" language="javascript">
      document.write(status(<%=EesAssistenza.getStatus()%>));
      </script>
    </td>
  </tr>
</table>

<p></p>
<form action="EesAssistenzaRemota.jsp" method="post">
  <input type="hidden" name="azione" value=""/>
  <table cellspacing="2" cellpadding="3" border="0" align="center">
    <tr>
      <td align="center">
        <input type="button" value="Attiva" class="pulsante" onClick="this.form.azione.value='attiva';this.form.submit()"/>
      </td>
    </tr>
    <tr>
      <td align="center">
        <input type="button" value="Disattiva" class="pulsante" onClick="this.form.azione.value='disattiva';this.form.submit()"/>
      </td>
    </tr>
    <tr>
      <td align="center">
        <input type="button" value="Test" class="pulsante" onClick="this.form.azione.value='test';this.form.submit()"/>
      </td>
    </tr>
  </table>

</form>
</body>
</html>

