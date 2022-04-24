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
      Pratica Completa Show
    </title>
  </head>
  
  <body>
  <hib:session>
  
    <c:import url="barramenu.jsp">
      <c:param name="menu"
               value="|Indietro,PraticaEdit.jsp?pkid=${param.pkid}|,ListaPratiche,RicercaPratiche.jsp, myimg/cartella.jpg |"/>
    </c:import>
    
  <hib:load var="pratica" classname="it.saga.siscotel.db.PraPraticaTesta" value="${param.pkid}" />
  
  <%
    String resolver = System.getProperty("esicra.xslt.dir");
    String xslt = resolver+File.separator+"PraticaHtml.xsl";
    pageContext.setAttribute("xslt_url",xslt);
    pageContext.setAttribute("resolver_url",resolver);
  %>
  
  <c:set var="xml" value="${pratica.docXml}" />
  
  <c:import var="xslt" url="file:/${xslt_url}" />
  <br><br>
  <table align="center" class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "98%" >
      <tr>
        <td>
          <div style="overflow:auto; height:500px">         
            <x:transform  xml='${xml}' xslt='${xslt}' xsltSystemId='file:/${resolver_url}/' />
          </div>
        </td>
      </tr>
  </table>

</body>
</hib:session>
</html>
