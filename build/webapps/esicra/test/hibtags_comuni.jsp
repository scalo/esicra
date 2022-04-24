<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<hib:find var="comuni" maxResults="100">
  from TerComune c
</hib:find>



<c:forEach items="${comuni}" var="comune">
  <p>
  <c:out value="${comune.pkid} ${comune.desComune} ${comune.desProvincia} " />
  </p>
</c:forEach>
