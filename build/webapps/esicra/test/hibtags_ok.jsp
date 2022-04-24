<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  isErrorPage="false" contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <title>
      hibtags_ok
    </title>
  </head>
  <body>
  <h1 align="center"><font color=blue>OK!</font></h1>

  <c:choose>
  <c:when test="${param.pkid == null }">
     pkid nullo
  </c:when>

<c:otherwise>

<hib:session>
<hib:load var="cat" classname="it.saga.siscotel.db.test.Cat" value="${param.pkid}" />

<table border=1 cellpadding=3 cellspacing=3 align="center">
  <tr>
    <th>name</th><th>value</th>
  </tr>
  <tr>
    <td>pkid</td><td><c:out value="${cat.pkid}" /></td>
  </tr>
  <tr>
    <td>name</td><td><c:out value="${cat.name}" /></td>
  </tr>
  <tr>
    <td>sex</td><td><c:out value="${cat.sex}" /></td>
  </tr>
  <tr>
    <td>weight</td><td><c:out value="${cat.weight}" /></td>
  </tr>
  <tr>
    <td>mother</td><td><c:out value="${cat.mother.name}" /></td>
  </tr>
  <tr>
    <td>kittens</td><td><c:out value="${cat.kittens.class}" /></td>
  </tr>
</table>
</hib:session>
</c:otherwise>
</c:choose>
<br/>
<a href="hibtags_form.jsp" >form inserimento</>
  </body>
</html>
