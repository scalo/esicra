<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="it.saga.egov.esicra.timer.servlet.EesHtmlUtils" %>
<%@ page import="it.saga.egov.esicra.*" %>
<%@ page import="it.saga.egov.esicra.importazione.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.text.*" %>

<html>
<head>
<meta http-equiv="pragma" content="nocache">
<link rel=stylesheet href="esicra.css" type="text/css">

<title>Esportazione  <%=System.getProperty("esicra.nodo.descrizione")%> <%=System.getProperty("esicra.id_ente") %> <%=System.getProperty("esicra.tipo_installazione")%></title>


<script type="text/javascript">
			
      function scarica(filename){
      			//alert("download "+filename);
      			filename=decodeURI(filename);
            //alert("download "+filename);
            document.forms["download"].file.value=filename;
            document.forms["download"].submit();
      }
  
      function cancella(filename){
      		filename=decodeURI(filename);
            res = confirm("Il file "+filename+" verrà cancellato\n");
            if(res){
            	document.forms["rimuovi"].file.value=filename;
            	document.forms["rimuovi"].submit();
            }else
              return;
      }
  
</script>
</head>
<body>
<%

String esicra_home=System.getProperty("esicra.home");
String allegati=System.getProperty("esicra.attachments.ftp.localDir");
String allegati_dir=esicra_home+File.separator+allegati;
File dir = new File(allegati_dir);

String azione=request.getParameter("azione");
String filename=request.getParameter("file");

if(azione!=null){
	if(azione.equals("rimuovi")){
		filename=URLDecoder.decode(filename);
		File file= new File(dir,filename);
		boolean exists =file.exists();
		//System.out.println("<p>exists="+exists+"</p>");
		boolean deleted=file.delete();
		//System.out.println("<p>deleted:"+deleted+"</p>");
		//System.out.println("file:"+ filename);
	}
}

FilenameFilter filter = new FilenameFilter() {
  public boolean accept(File dir, String name) {
    name = name.toLowerCase();
    return !name.startsWith(".");
  }
};

File[] lista = dir.listFiles(filter);
/*
out.println("<p> allegati_dir="+allegati_dir+"</p>");
out.println("<p>azione="+request.getParameter("azione")+"</p>");
out.println("<p>file="+request.getParameter("file")+"</p>");
out.println("<p>size="+lista.length+"</p>");
*/

NumberFormat fmt = new DecimalFormat("0.000");

%>


<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
    		<td>|</td>
        <td>
            <a href= "EesDownload.jsp">Area Download </a>
        </td>
        <td>|</td>
        <td>
            <a href= "EesAmministrazione.jsp">Menù Amministrazione </a>
        </td>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" alt="home" align="middle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>
<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" height="20"> 
    <TD>&nbsp;&nbsp;Allegati</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" height="20">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>


<form name="download" action="DownloadServlet">
	<input type=hidden name="azione" value="download" />
  <input type=hidden name="file" value="" />
	<input type=hidden name="dir" value="<%=allegati_dir%>" />
</form>

<form name="rimuovi" action="EesAllegati.jsp" method="post" >
	<input type=hidden name="azione" value="rimuovi" />
	<input type=hidden name="file" value="" />
</form>

<br>

<div align="center" >
<table width="100%">
	<tr class="HelpRigaSezione" >
  <td height="1"></td>
  </tr>   
  <tr  class="HelpRigaTitoloSezione" height="20">
    <td class="HelpRigaTitoloRight">Elenco files Allegati</td>
  </tr>
  <tr class="HelpRigaSezione" >
  <td height="1"></td>
  </tr>
</table>
<div style="overflow:auto; height:400px ; ">

<table width="99%">
  <% for(int i=0;i<lista.length;i++){ %>
  <tr class="<%=i%2==0?"RigaPari":"RigaDispari" %>">
  <td ><%=lista[i].getName()%></td>
  <td><%=fmt.format(lista[i].length()/1024.0)%> KB</td>
  <td width="80"><input type=button value="Download" onclick="scarica('<%=URLEncoder.encode(lista[i].getName())%>')" class="pulsante"></td>
  <td width="80"><input type=button value="Rimuovi" onclick="cancella('<%=URLEncoder.encode(lista[i].getName())%>')" class="pulsante"></td>
  </tr>
  <%
  }
  %>
</table>
</div>
</div>
<!--
<table align="center">
  <tr><td><br></td></tr>
  <tr>
   <td>
     <a href="EesMenu.jsp">Ritorna al Menù Principale</a>
    </td> 
  </tr>  
</table>
-->
</body>
</html>

