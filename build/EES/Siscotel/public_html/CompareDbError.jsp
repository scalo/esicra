<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*" %>
<%@ page isErrorPage="true" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>DbCompareError</title>
    <link rel="stylesheet" href="esicra.css" type="text/css"/>
  </head>
  <body> 
    <DIV align="center">
      <H1>
        <FONT color="#ff3333">Errore</FONT>
      </H1>
    </DIV>
    <P>&nbsp;</P>
    <P><b>
      <%=exception %>
    </b></P>
      <%
      exception.printStackTrace(new PrintWriter(out));
      %>
    <P>&nbsp;</P>
    <P>
      <a href="javascript:history.go(-1)" >Ritorna</a>
    </P>
	<%
		session.removeAttribute("db1");
		session.removeAttribute("db2");
	%>
  </body>
</html>
