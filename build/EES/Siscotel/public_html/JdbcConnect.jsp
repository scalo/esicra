<%@ page contentType="text/html;charset=iso-8859-1"%>
<%@ page import="
java.sql.Date,
it.saga.egov.esicra.timer.servlet.EesHtmlUtils
" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Parametri connessione jdbc</title>
</head>
<body onLoad='window.focus()'>
<%	
	String jdbc = request.getParameter("jdbc");
	String db = request.getParameter("db");
	String schema = request.getParameter("schema");
	/*
	HttpSession sessione = request.getSession();
	String jdbc = request.getParameter("jdbc");
	String azione=request.getParameter("azione");
	String db = request.getParameter("db");
    String schema = request.getParameter("schema");
    String user = request.getParameter("user");
    String password = request.getParameter("password");
	
	if(azione!=null&&db!=null&&azione.equals("connetti")){
		Database database=new Database(jdbc,schema,user,password);
        sessione.setAttribute(db,database);
  }
  */
	%>
		<script>
    //alert("close all");
		window.close();
		window.opener.location.reload();
		</script>

<form method="get" action="CompareDbServlet" name="form1">
<input type="hidden" name="azione" value="connetti" />
<input type="hidden" name="db" value="<%=db%>" />
<input type="hidden" name="schema" value="<%=schema!=null?schema:""%>" />

<table border="0" align="center" cellpadding="2" cellspacing="3">
	<tr>
	  <td align="right"><strong>URL jdbc:</strong></td>
	  <td align="left">
	  	<input name="jdbc" type="text" readonly size="40" value="<%=jdbc%>"/>
	  </td>
	</tr>
	<tr>
		<td  align="right"><strong>user:</strong></td>
		<td  align="left">
			<input name="user" type="text" size="30"/>
		</td>
	</tr>
	<tr>
		<td align="right"><strong>password:</strong></td>
		<td align="left">
			<input name="password" type="password" size="30"/>
		</td>
	</tr>
	<tr>
	</tr>
	<tr>
	  <td colspan="2" align="center">
	  	<!-- window.close();window.opener.location.reload(); -->
		<input type="button" value="Connetti" onClick="this.form.submit()"/>
	  </td>
	</tr>
  </table>
</form>
</body>
</html>
