<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<hib:session>

<hib:find var="fatcats">
  from Cat c
  where c.weight > 15
</hib:find>

<c:forEach items="${fatcats}" var="fatcat">
  <p>
  delete <c:out value="${fatcat.pkid} ${fatcat.name} ${fatcat.weight}" />
  </p>
  <hib:delete target="${fatcat}" />
</c:forEach>

</hib:session>
