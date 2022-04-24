<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"
  import="java.io.*,java.math.*"
%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x"%>

  <hib:session>
    <c:import url="barramenu.jsp">
      <c:param name="menu"
               value="|Indietro,PraticaEdit.jsp?pkid=${param.pkid}|,ListaPratiche,RicercaPratiche.jsp,myimg/cartella.jpg |"/>
    </c:import>
    
  <hib:load var="pratica" classname="it.saga.siscotel.db.PraPraticaTesta" value="${param.pkid}" />
  
  <c:set var="xml" value="${pratica.docXml}" />
  <c:set var="pratica" value="${pratica.pkid}" />
  
  <%
    StringBufferInputStream fis = null;
    OutputStream fos = null;
    String xml = (String) pageContext.getAttribute("xml");
    BigDecimal pratica = (BigDecimal) pageContext.getAttribute("pratica");
    response.setContentType("application/binary");
    response.setHeader("Content-Disposition", "attachment; filename=pratica"+pratica+".xml");
    fis = new StringBufferInputStream(xml);
    
    fos = response.getOutputStream();
    int curByte = -1;
    while ((curByte = fis.read()) != -1)
        fos.write(curByte);
    fos.flush();
  %>

</hib:session>
