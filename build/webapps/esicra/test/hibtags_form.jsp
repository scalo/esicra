<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%
  System.setProperty("id_ente","8240");
%>

<c:if test="${param.action=='save'}">
  <jsp:useBean id="form" class="it.saga.siscotel.db.test.Cat" />
  <jsp:setProperty  name="form" property="*" />
  <!-- validate -->
  <hib:session>
    <hib:save target="${form}" />
  </hib:session>
  <c:redirect url="hibtags_ok.jsp?pkid=${form.pkid}" />
</c:if>

<form method="get" action="hibtags_form.jsp" >
  <input type="hidden" name="action" value="save" />
  <table cellspacing="3" cellpadding="2" border="0" width="42%" align =center>
    <tbody>
      <tr>
        <td width="38%">nome</td>
        <td width="62%">
          <input  name="name" type="text" value="<c:out value='${form.name}' />" />
        </td>
      </tr>
      <tr>
        <td width="38%">sesso</td>
        <td width="62%">
          <input name="sex" type="text" value="<c:out value='${form.sex}' />" />
        </td>
      </tr>
      <tr>
        <td width="38%">peso</td>
        <td width="62%">
          <input  name="weight" type="text" value="<c:out value='${form.weight}' />" />
        </td>
      </tr>
      <tr>
        <td>
          <input type="submit" />
        </td>
        <td>
          <input type="reset" />
        </td>
      </tr>
    </tbody>
  </table>
</form>

