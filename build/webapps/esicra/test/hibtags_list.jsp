<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<!-- legge filtri -->

<c:set var="where" value="where sex='${param.sex}'" />

<hib:find var="cats">
  from Cat c ${where}
</hib:find>


<table width="30%" align=center border=1 class="tabella" >
  <tr>
    <th  width="10%"></th>
    <th  width="50%">nome</th>
    <th  width="10%">sesso</th>
    <th  width="40%">peso</th>
  </tr>
  <c:forEach var="cat" items="${cats}" varStatus="status">
  <tr  class="<c:out value="riga${status.count %2}" />" >
    <td><c:out value="${status.count}" /></td>
    <td><c:out value="${cat.name}" /></td>
    <td><c:out value="${cat.sex}" /></td>
    <td><c:out value="${cat.weight}" /></td>
  </tr>
  </c:forEach>
</table>
