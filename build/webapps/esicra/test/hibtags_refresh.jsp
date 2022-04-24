<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%-- Open a new session for this page --%>
<hib:session>

<%-- This cat was loaded in a previous JSP and was 
  stored as an attribute in the HttpSession.  We want to refresh the same
  object but need to refresh it to the current hibernate session. --%>
<hib:refresh target="${cat}" />

<%-- Loops over our cat's kittens and print them out --%>
<c:forEach items="${cat.kittens}" var="kitten">
  <c:out value="${kitten.name}" />
</c:forEach>

<%-- Close the current session that we needed for the kittens association --%>
</hib:session>
