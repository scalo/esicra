<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*" %>
<%@ page import="it.saga.egov.esicra.log.*" %>
<%@ page import="it.saga.egov.esicra.*" %>
<%@ page import="org.apache.log4j.*" %>

<html>
<head>

<link rel=stylesheet href="esicra.css" type="text/css">
<title>Logs  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>
</head>

<!--
<script type="text/javascript" language="javascript" src="viewlog.js">
</script>
-->

<%  
    // root logger
    Logger logger = EsicraConf.getLogger("");
    String logfile=request.getParameter("logfile");
    String loglevel=request.getParameter("loglevel");
    if(logfile!=null && logfile.length()==0){
        logfile=(String)request.getSession().getAttribute("logfile");
    }else{
        request.getSession().setAttribute("logfile",logfile);
    }
    if(loglevel!=null){
        //logfile=(String)request.getSession().getAttribute("loglevel");
        if( loglevel.equalsIgnoreCase("FATAL")||
            loglevel.equalsIgnoreCase("ERROR")||
            loglevel.equalsIgnoreCase("INFO")||
            loglevel.equalsIgnoreCase("WARN")||
            loglevel.equalsIgnoreCase("DEBUG")){
                request.getSession().setAttribute("loglevel",loglevel);
                Logger.getRootLogger().setLevel(Level.toLevel(loglevel));
        }
    }else{
        loglevel=(String)request.getSession().getAttribute("loglevel");
        if(loglevel==null)
            loglevel=Logger.getRootLogger().getLevel().toString();
    }
    String logdir = System.getProperty("esicra.log");
    String clear=request.getParameter("clear");
    String visualizza=request.getParameter("visualizza");
    if(clear!=null && logfile!=null && logfile.length()>0 ){
        // pulisce il file di log
        File file = new File(logdir,logfile);
        FileWriter fw = new FileWriter(file);
        fw.flush();
        fw.close();
        logger.debug("Cancellato logger "+logfile);
    }
%>
<body>

<!--
<b>Debug:</b>
logfile = <%=logfile %>
loglevel = <%=loglevel %>
clear = <%=clear %>
visualizza = <%=visualizza %>
logger = <%=logger %>
-->
<!--
<h1 align="center">Log Viewer</h1>
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
    <TD>&nbsp;&nbsp;Gestione Logs</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" height="20">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>
<p/>
<form action="EesViewLog.jsp">
<table>
    <tr>
    <th class="etichetta">File di log</th>
    <td>
    <select  name="logfile" class="selezione">
        <option selected ></option>
        <%  
            String[] lista =ViewLog.getLogFiles();
            String selected="";
            for(int i=0;i<lista.length;i++){
                if(lista[i].equals(logfile)){
                    selected="selected";
                }else {
                    selected="";
                }
                out.println("<option "+selected+">"+lista[i]+ "</option>");          
            }
        %>
    </select>
    </td>
    <td  align ="left" >
        <input class="pulsante" name="visualizza" type="submit" value="Visualizza" />
    </td>
    </tr>
    <tr><th class="etichetta" >Livello Corrente Logger</th> 
    <td  align ="center" >
        <select  name="loglevel" class="selezione">
            <option selected ><%=Logger.getRootLogger().getEffectiveLevel().toString()%></option>
            <option>DEBUG</option>
            <option>INFO</option>
            <option>WARN</option>
            <option>ERROR</option>
            <option>FATAL</option>
        </select>
    </td>
    <td align ="left" >
        <input class="pulsante" type="submit" value="Cambia Livello" />
    </td>
    </tr>
    
    <tr>
    <td align="center" ><input class="pulsante" name="clear" type="submit" value="Pulisci Log" /></td>
    </tr>
    
</table>

<table align="center">
  <tr><td class="etichetta">Directory logs:</td><td><%=logdir%></td></tr>
  <tr><td class="etichetta">file di log :</td><td><%=(logfile!=null)?logfile:""%></td></tr>  
</table>
	<br>
<div align="center">
<textarea  cols=140 rows=20 wrap=off readonly="true" class="Testo"><%
    if(logfile!=null){
    out.print(ViewLog.getLog(logfile));
    }%>
</textarea>
</div>
<br>
</form>
<!--
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

