<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="it-IT" />
<jsp:useBean id="today" class="java.util.Date" />
Oggi: <b><fmt:formatDate value="${today}" pattern="dd/MM/yyyy" /></b>

<br/>



<hib:find var="cats">
  from Cat c
  where sex = ?
  <hib:param value="F" />
</hib:find>

<c:out value="${cats}" />


<c:forEach items="${cats}" var="cat">
  <p>
  <c:out value="${cat.pkid} ${cat.name} ${cat.sex} " />
  </p>
</c:forEach>
