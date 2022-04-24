<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  isErrorPage="true" contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <title>
      hibtags_error
    </title>
  </head>
  <body>
  <h1><font color=red>Error !</font></h1>
  <c:out value="${pageContext.exception.message}" />
  </body>
</html>