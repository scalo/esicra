<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="javax.sql.* , java.sql.* , it.saga.egov.esicra.* , javax.naming.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/plain ; charset=windows-1252">
<title>
Test metadata
</title>
</head>
<body>

<%  
  out.println("<h2 align=\"center\"> TEST METADATA</h2><br>");
    StringBuffer sb =new StringBuffer();
    EsicraConf.configura();
    InitialContext init = new InitialContext();
    out.println("Context = "+ init+"<br>");
    DataSource ds = (DataSource) EsicraConf.getDataSource("esicra.datasource_nodo");
    out.println("DataSource = "+ds+"<br>");
    String query ="select * from ter_regione";
    Connection conn = null; // ds.getConnection();
    Connection poolconn = ds.getConnection();
    conn = poolconn.getMetaData().getConnection();
    //Statement stm =conn.createStatement();
    ResultSet rs = null;
    DatabaseMetaData dbmd = conn.getMetaData();
    rs = dbmd.getColumns(null, "esicra", "ana_soggetto", null);
    sb.append("UPDATE " + "ana_soggetto" + " SET ");
    for (int i = 1; rs.next(); i++) {
        if (i > 1) {
            sb.append(",");
        }
        sb.append(rs.getString("COLUMN_NAME") + "=?");
    }
    sb.append(" WHERE pkid=? ");
    out.println(sb.toString());
    rs.close();
    conn.close();
  

%>

</body>
</html>
