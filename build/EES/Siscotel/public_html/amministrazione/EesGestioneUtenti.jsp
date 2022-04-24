<%@ page language="java" errorPage="../GestioneErrori.jsp" contentType="text/html;charset=windows-1252" 
    import = "it.saga.egov.esicra.*,
              java.sql.*, 
              javax.sql.*, 
              java.util.*,  
              java.math.* "%>

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../esicra.css">
<TITLE>Gestione Utenti</TITLE>
</head>
<script type="text/javascript">
  function elimina_utente(user){
    //alert(user);
    res= confirm("Attenzione !! \n L'utente "+user+" verrà cancellato definitivamente");
  }
</script>
<body class = "body">

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
		<td>|</td>
		<td>
			<a href="EesModificaUtente.jsp">Aggiungi Utente</a>
		</td>
    <td>|</td>
    <td>
			<a href= "EesGestioneGruppi.jsp">Gestione Gruppi</a>
		</td>
    <td>|</td>
    <td>
        <a href= "../EesAmministrazione.jsp">Menù Amministrazione</a>
    </td>
    <td>|</td>
    <td><IMG SRC="../myimg/home.jpg"  ALT="../myimg/home.jpg" align="middle">&nbsp;<a href="../EesMenu.jsp" >Menù Principale</a></td>
    <td>|</td>        
    </tr>
</table>

<br/>

<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo"> 
    <TD>Gestione Utenti&nbsp;&nbsp;</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>
  <TR  class="HelpRigaTitoloSezione">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>

<br/>
<br/>
<br/>
<b>
Elenco Utenti
</b>
<table align = "center" class = "LabelForm" cellspacing="0" cellpadding="0" border="0" width = "100%" >
  <TR class="HelpRigaSezione" >
  <TD colspan = "10" height="1"></TD></TR>
  <tr class = "PrimaRiga">
       <td width = "5%" align = "center"></td>
       <td width = "5%" align = "center"></td>
       <!--
       <td width = "5%" align = "center"></td> 
       -->
       <td width ="20%" align = "center">Login</td>   
       <td width ="20%" align = "center">Nome</td>
       <td width ="20%" align = "center">Cognome</td>
  </tr>
  <tr class = "HelpRigaSezione" >
     <td colspan = "10" height="1"></td>
  </tr>
</table>

<%
DataSource ds =  EsicraConf.getDataSource("esicra.datasource_nodo");
int i=0;
String rowStyle="";
/* 
gli utenti amministrati hanno dt_ins non nulla , per motivi di sicurezza
l'utente tomcat non è visibile
*/

String query ="select * from j2ee_users where dt_ins is not null";
//String query ="select distinct ju.user_name ,ju.nome,ju.cognome from j2ee_users ju , ser_gruppo_utente gu  where ju.user_name= gu.utente";
Connection conn = ds.getConnection();
Statement stm = conn.createStatement();
ResultSet rs =stm.executeQuery(query);
%>

<table  width="100%" align="center" cellpadding=0 cellspacing=0>
    <tr>
        <td>
            <DIV  style="overflow:auto; height:386px">
            
            <table width="100%" cellpadding=0 cellspacing=0>
                 <% while(rs.next()){
                 
                     if (i%2 == 0)
                         rowStyle = "RigaPari";
                      else
                         rowStyle = "RigaDispari";
                 %>
                  <tr class="<%=rowStyle%>">
                    <td width ="5%" align = "center">
                        <a href= "EesVisualizzaUtente.jsp?utente=<%=rs.getObject("USER_NAME")%>">Visualizza</a> 
                    </td>
                    <td width ="5%" align = "center">
                        <a href= "EesModificaUtente.jsp?utente=<%=rs.getObject("USER_NAME")%>">Modifica</a> 
                    </td>
                    <!--
                    <td width ="5%" align = "center">
                        <a href="" onclick="elimina_utente('<%=rs.getObject("USER_NAME")%>')">Elimina</a> 
                    </td>
                    -->
                    <td width ="20%" align = "center"> 
                      <%=rs.getObject("USER_NAME") %>
                    </td>
                    <td width ="20%" align = "center">
                      <%=rs.getObject("NOME") %>
                    </td>
                    <td width ="20%" align = "center">
                      <%=rs.getObject("COGNOME") %>
                    </td>
                    
                  <%i++;
                  }%>
                  
              <tr class = "HelpRigaSezione" >
                 <td colspan = "10" height="1"></td>
              </tr>
            </table>
            </div>
        </td>
    </tr>
</table>
<%
  rs.close();
  stm.close();
  conn.close();
%>
</body>
</html>

