<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"
  import="java.io.File"
%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x"%>

<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <link rel="STYLESHEET" type="text/css" href="siscotel.css"></link>
    <script type="text/javascript" src="decode.js" ></script>
    <title>
      Xml
    </title>
  </head>
  <body>
  <hib:session>
    <c:import url="barramenu.jsp">
      <c:param name="menu"
               value="|Indietro,PraticaEdit.jsp?pkid=${param.pkid}|,ListaPratiche,RicercaPratiche.jsp,myimg/cartella.jpg |"/>
    </c:import>
    
  <hib:load var="pratica" classname="it.saga.siscotel.db.PraPraticaTesta" value="${param.pkid}" />
  
  <c:set var="xml" value="${pratica.docXml}" />
  
  <c:out value="${xml}" />

</body>
</hib:session>
</html>
