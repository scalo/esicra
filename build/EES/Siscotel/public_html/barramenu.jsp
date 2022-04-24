<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="fn" %>

<!--  barra munu dinamica -->

<TABLE border="0" align="right" class="barramenu">
  <tr>
    <c:forTokens items="${param.menu}" var="item" delims="|" >
      <td>|</td>
      <fn:split separator="," var="items"><c:out value="${item}"/></fn:split>
      <c:if test="${not empty items[2]}">
        <td>
        <img src="<c:out value="${items[2]}"/>" alt ="<c:out value="${items[2]}"/>" align="middle" />
        </td>
      </c:if>
      <td>
      <A href=<c:out value="${items[1]}"/> > <c:out value="${items[0]}" /> </A>
      </td>
    </c:forTokens>
    <td>|</td>
  </tr>
</TABLE>
<br>

