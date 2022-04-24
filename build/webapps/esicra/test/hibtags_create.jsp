<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<%
  System.setProperty("id_ente","8240");
  
%>

<jsp:useBean id="cat" class="it.saga.siscotel.db.test.Cat" />

<c:set target="${cat}" property="name" value="Baldanzone" />
<c:set target="${cat}" property="sex" value="M" />
<c:set target="${cat}" property="weight" value="19.6" />

<c:out value="${cat.pkid} ${cat.name} ${cat.sex} ${cat.weight}" />

<hib:session>
<hib:save target="${cat}" />
</hib:session>
