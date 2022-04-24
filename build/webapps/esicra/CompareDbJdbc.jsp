<%@ page import="it.saga.egov.esicra.db.* , java.util.* "%>
<%@ page errorPage="CompareDbError.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Confronto Basi Dati (Jdbc)</title>
    <link rel="stylesheet" href="esicra.css" type="text/css" />
	<script language="JavaScript">
		function openUrl(url,jdbc,db){
			//alert(jdbc); 800 300
			jdbc_url=url+"?jdbc="+jdbc+"&db="+db;
			w = window.open(jdbc_url,"Parametri Connessione",'width=350,height=100');
		}
	</script>
  </head>
  <body>
    <!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
        <td>|</td>
        <td>
            <a href= "EesAmministrazione.jsp">Menù Amministrazione </a>
        </td>
        <td>|</td>
        <td><IMG SRC="myimg/home.jpg" alt="menu" align="middle">&nbsp;<a href="EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>
<br>

<!-- titolo -->
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" > 
    <TD>&nbsp;&nbsp;Confronto Basi Dati (Jdbc)</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" >
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>
    <%   
    
    String URL1="jdbc:postgresql://srvrsnod:5432/esicra";
    String URL2="jdbc:oracle:thin:@srvrspat:1521:esicram";    
    
    long time=System.currentTimeMillis(); 
    HttpSession sessione = null;
    sessione = request.getSession();
    
    Database db1 = (Database) sessione.getAttribute("db1");
    Database db2 = (Database) sessione.getAttribute("db2");
    
	String schema1 = request.getParameter("schema1");
	String schema2 = request.getParameter("schema2");
	
	/*
    String url = request.getParameter("url");
	String db = request.getParameter("db");
    
    String user = request.getParameter("user");
    String password = request.getParameter("password");
    String action = request.getParameter("action");
	
	
	if(action!=null && db!= null && action.equals("disconnetti")){
        sessione.removeAttribute(db);
    }
	
  	
    if(action!=null && action.equals("connetti_db1")){
        db1=new Database(url,schema,user,password);
        sessione.setAttribute("db1",db1);
    }
    
    if(action!=null && action.equals("connetti_db2")){
        db2=new Database(url,schema,user,password);
        sessione.setAttribute("db2",db2);
    }
    out.println("** DEBUG PARAMS ** <br>");
    Enumeration enum = request.getParameterNames();
    while(enum.hasMoreElements()){
        String param = (String) enum.nextElement();
        out.println(param+" = "+request.getParameter(param)+"</p>");
    }
      
    out.println("** DEBUG SESSION ** <br>");
    enum = sessione.getAttributeNames();
    while(enum.hasMoreElements()){
        String attr = (String) enum.nextElement();
        Object obj = sessione.getAttribute(attr);
        out.println(attr+" = "+obj+"</p>");
    }
    Database obj1 = (Database) sessione.getAttribute("db1");
    if(obj1!=null){
    out.println("<p>connection db1 = "+obj1.getConnection()+"</p>");
    }
    Database obj2 = (Database) sessione.getAttribute("db2");
    if(obj1!=null){
    out.println("<p>connection db2 = "+obj2.getConnection()+"</p>");
    }
     
    if(sessione!=null){
        out.print("sessione="+sessione);
    }
    */
    %>
	<form method="get" action="CompareDbServlet" name="form">
	<input type="hidden" name="azione" value="connetti"/>
	<input type="hidden" name="db" />
	<input type="hidden" name="schema" />
    <table width="100%" border="0" cellpadding="4" cellspacing="4" >
      <tr>
        <td width="43%">
            <div align="right">
              <table cellspacing="3" cellpadding="2" border="0" align="center">
                <tr>
                  <td colspan="3" align="center">
                    <strong>Database 1</strong> 
                    <%   
					  if(db1!=null){
						if (db1.isConnected())
							out.println("<font color=\"green\"><i>(connected)</i></font>");
						else
							out.println("<font color=\"red\"><i>(not connected)</i></font>");
					  }else
						 out.println("<font color=\"red\"><i>(not connected)</i></font>");
					%>
                  </td>
                </tr>
                <tr>
                  <td colspan="3" align="center">
                    <em>
                      <input name="jdbc_url1" value="<%=URL1%>" size="50"/>
                    </em>
                  </td>
                </tr>
                <tr>
                  <td colspan="3" align="center">
                    <input type="button" class="pulsante" onClick="openUrl('JdbcConnect.jsp',jdbc_url1.value,'db1')" value="Connetti"/>
   					&nbsp;&nbsp;	
                    <input type="submit" class="pulsante" onClick="this.form.azione.value='disconnetti';this.form.db.value='db1'" value="Disconnetti"/>
                  </td>
                </tr>
                <tr>
                  <td width="100" align="right" class="etichetta">schema:</td>
                  <td width="129"><div align="left">
                  <select name="schema1" style="width:150px" >
				  	<% if(db1!=null){
						   String[]  schemas = db1.listSchemas();
						   for(int i=0 ; i<schemas.length ;i++){
							String val =schemas[i];
							%>
							<option value="<%=val%>" ><%=val%></option>
							<%
						   }
						}
					%>
				  </select>
                </div></td>
                <td width="130" align="center"><input type="button" class="pulsante" onClick="this.form.azione.value='genera';this.form.db.value='db1';this.form.schema.value=this.form.schema1.value;this.form.submit()" value="Genera Xml"/></td>
                </tr>
              </table>
            </div>
          
        </td>
        <td width="14%">
            <DIV align="center">
              <P>
                <input class="pulsante" type="button" value="Confronta" onClick="this.form.azione.value='confronta';this.form.submit()"/>
              </P>
              <P>
                <input class="pulsante" type="button" value="Pulisci"  onClick="this.form.azione.value='pulisci';this.form.submit()"/>
              </P>
            </DIV>
          
        </td>
        <td width="43%">
            <table width="368" border="0" align="center" cellpadding="2" cellspacing="1">
              <tr>
                <td colspan="3" align="center">
                  <strong>Database 2</strong> 
                  <%   
                  if(db2!=null){
                    if (db2.isConnected())
                        out.println("<font color=\"green\"><i>(connected)</i></font>");
                    else
                        out.println("<font color=\"red\"><i>(not connected)</i></font>");
                  }else
                     out.println("<font color=\"red\"><i>(not connected)</i></font>");
                  %>
                </td>
              </tr>
              <tr>
                <td colspan="3" align="center">
                  <em>
                    <input name="jdbc_url2" value="<%=URL2 %>" size="50"/>
                  </em>
                </td>
              </tr>
              <tr>
                <td colspan="3" align="center">
                    <input type="button" class="pulsante" onClick="this.form.azione.value='connetti';openUrl('JdbcConnect.jsp',jdbc_url2.value,'db2')" value="Connetti"/>
                	&nbsp;&nbsp;
                    <input type="submit" class="pulsante" onClick="this.form.azione.value='disconnetti';this.form.db.value='db2'" value="Disconnetti"/>
				</td>
              </tr>
              <tr>
                <td width="100" align="right" class="etichetta">schema:</td>
              	<td width="128"><div align="left">
                <select style="width:150px" name="schema2">
					<% if(db2!=null){
						   String[]  schemas = db2.listSchemas();
						   for(int i=0 ; i<schemas.length ;i++){
							String val =schemas[i];
							%>
							<option value="<%=val%>" ><%=val%></option>
							<%
						   }
						}
					%>
				</select>
              </div></td>
              <td width="128" align="center"><input type="button" class="pulsante" onClick="this.form.azione.value='genera';this.form.db.value='db2';this.form.schema.value=this.form.schema2.value;this.form.submit()" value="Genera Xml"/></td>
              </tr>
            </table>
          
        </td>
      </tr>
  </table>
  </form>   
    <div align="center">
      <table width="200" border="0">
        <tr>
          <td><textarea cols="65" wrap=off rows="18" style="font-size:8.0pt;" readonly><%   
                if(db1!=null){
					db1.setSchema(schema1);
                    out.print(db1);
				}
          %></textarea></td>
          <td>&nbsp;&nbsp;&nbsp;</td>
          <td><textarea cols="65" wrap=off rows="18" style="font-size:8.0pt;" readonly><%   
                if(db2!=null){
                    db2.setSchema(schema2);
					out.print(db2);
				}
          %></textarea></td>
        </tr>
      </table>
    </div>
    &nbsp;
    <table>
      <tr>
        <td width="391" class="titoletto">tempo di elaborazione: <%=(System.currentTimeMillis()-time)%> ms  </td>
      </tr>
    </table>    
    <table width="975" border="0">
      <tr>
        <td align="center"><textarea cols="120" wrap=off rows="6" style="font-size:8.0pt;" readonly><%
			if(db1!=null&&db2!=null){
					out.print(db1.getCompLog());
			}
		%></textarea></td>
      </tr>
    </table>    
  </body>
</html>
