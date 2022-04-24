<%@ page language = "java" isErrorPage="true" import = "java.util.*, java.io.* " contentType="text/html;charset=windows-1252" %>
<html>
<head>
<META NAME="GENERATOR" CONTENT="Oracle JDeveloper">
<TITLE>Error page</TITLE>
</head>
<body>

<center><H2>Errore</H2></center>

<a href="javascript:history.go(-1)" >Return</a>

<div CLASS="errorText">
Messaggio Errore: <%= exception.getMessage() %>
<BR>

<PRE><%
      StringWriter writer = new StringWriter();
      PrintWriter prn = new PrintWriter(writer);

      exception.printStackTrace(prn);
      prn.flush();

      out.print(writer.toString()); %>
</PRE>
<%-- --%>
</div>
</body>
</html>
