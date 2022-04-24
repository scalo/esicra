<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="javax.sql.* , java.sql.* , it.saga.egov.esicra.* , javax.naming.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/plain ; charset=windows-1252">
<title>
Test data source
</title>
</head>
<body>

<%  
  out.println("<h2 align=\"center\"> TEST DATASOURCE NODO</h2><br>");
  
  EsicraConf.configura();
  InitialContext init = new InitialContext();
  out.println("Context = "+ init+"<br>");
  DataSource ds = (DataSource) EsicraConf.getDataSource("esicra.datasource_nodo");
  out.println("DataSource = "+ds+"<br>");
  String query ="select * from ter_regione";
  Connection conn = ds.getConnection();
  Statement stm =conn.createStatement();
  ResultSet rs = stm.executeQuery(query);
  while(rs.next()){
  	out.println("<p>");
    out.println(rs.getString(3));
  }
  conn.close();

%>

</body>
</html>
