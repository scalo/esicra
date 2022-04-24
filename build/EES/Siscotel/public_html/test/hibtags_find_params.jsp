<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"
import="java.util.*,it.saga.siscotel.db.test.*"
%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<hib:find var="cats">
  from Cat c
  join fetch c.kittens as kitten
  where kitten.name= ?
  <hib:param value="${param.kitten_name}" />
</hib:find>

<c:forEach var="inusato" items="${cats}" varStatus="status">
  <c:if test="${status.last}"><c:set var="count" value="${status.count}"/></c:if>
</c:forEach>

<c:out value="${cats}" />

<%
  /*
  List l = (List) pageContext.getAttribute("cats");
  for(int i=0;i<l.size();i++){
    out.println((Cat)l.get(i));
  }
  */
%>

<c:forEach items="${cats}" var="cat">
  <c:out value="${cat.name}" />
</c:forEach>


<c:if test="${empty cats}">
  We did not find any cats who is a parent of that kitten. <c:out value="${param.kitten_name}" />
</c:if>
