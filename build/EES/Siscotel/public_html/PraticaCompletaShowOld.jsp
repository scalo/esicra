<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"
  import="java.io.*,javax.xml.transform.*,
javax.xml.transform.stream.*,
javax.xml.transform.sax.*,
it.saga.egov.esicra.esportazione.*"
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
  
  <c:set var="xml" value="${pratica.docXml}" />
  <br/>
  <table align="center" class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "98%" >
      <tr>
        <td>
          <div style="overflow:auto; height:500px">         
          <%
          String inXml=(String) pageContext.getAttribute("xml");
          TransformerFactory factory = TransformerFactory.newInstance();
          factory.setURIResolver(new XsltUriResolver());
          String filename = System.getProperty("esicra.xslt.dir")+File.separator+"PraticaHtml.xsl";
          FileInputStream xslt = new FileInputStream(filename);
          Templates template = factory.newTemplates(new StreamSource(xslt));
          // usa template
          Transformer xformer = template.newTransformer();
          
          // prepara input e output
          Source source = new StreamSource(new StringReader(inXml));
          StringWriter sw = new StringWriter(); 
          Result result = new StreamResult(sw);
          
          // applica trasformazione
          xformer.transform(source, result);
          sw.flush();
          out.println(sw.getBuffer().toString());
          xslt.close();
          
          %>  
          </div>
        </td>
      </tr>
  </table>

</body>
</hib:session>
</html>

<!--

            
-->
