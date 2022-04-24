<%@ page language="java" errorPage="GestioneErrori.jsp" contentType="text/html;charset=windows-1252" 
    import = "javax.sql.*,  
              java.sql.*,  
              it.saga.egov.esicra.*,
              java.text.SimpleDateFormat"%>

<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../esicra.css">
<TITLE>Visualizza Utente</TITLE>
<script type="text/javascript">

  function clearList(list){
    //alert(list.options.length);
    for(var i=0 ;i<list.options.length;i++){
      list.options[i].selected=false;
    }
  }

</script>
</head>

<body class = "body">
<%
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
DataSource ds = EsicraConf.getDataSource("esicra.datasource_nodo");
// legge parametri in ingresso
String disponibili[] = request.getParameterValues("disponibili");
String attivi[] = request.getParameterValues("attivi");
String utente = request.getParameter("utente");
String azione = request.getParameter("azione");
if (utente==null) utente="";
if (azione==null) azione="";
/*
System.out.println("utente:"+utente);
System.out.println("azione:"+azione);
System.out.println("disponibili:"+disponibili);
System.out.println("attivi:"+attivi);
*/
String query_gruppi_attivi="select u.gruppo from ser_gruppo_utente u ,ser_gruppo g  where u.gruppo=g.nome and u.utente='"+utente+"'";
String query_servizi_attivi="select distinct s.id_servizio,s.des_breve from  ser_gruppo g,ser_gruppo_servizio gs, ser_servizio s , ser_gruppo_utente gu where gs.gruppo=g.nome and s.id_servizio=gs.id_servizio and tipo_export=2 and gu.gruppo=gs.gruppo and gu.utente='"+utente+"' order by s.des_breve";
String query_utente="select * from j2ee_users where user_name='"+utente+"'";
String query_ruolo="select * from j2ee_users u, j2ee_user_roles r where u.user_name=r.user_name and r.user_name='"+utente+"'";
Connection conn = ds.getConnection();
Statement stm1 = conn.createStatement();
Statement stm2 = conn.createStatement();
Statement stm3 = conn.createStatement();
Statement stm4 = conn.createStatement();


ResultSet rs_servizi_attivi =stm1.executeQuery(query_servizi_attivi);
ResultSet rs_gruppi_attivi =stm2.executeQuery(query_gruppi_attivi);
ResultSet rs_ruolo =stm3.executeQuery(query_ruolo);
ResultSet rs_utente =stm4.executeQuery(query_utente);

String nome="";
String cognome="";
String password="";
String data_nascita="";
String codice_fiscale="";
String ruolo="";

if(rs_ruolo.next()){
  ruolo=rs_ruolo.getString("ROLE_NAME");
}

if(rs_utente.next()){
  Object o_nome=rs_utente.getObject("NOME");
  nome=nome==null?"":(String)o_nome;
  Object o_cognome=rs_utente.getObject("COGNOME");
  cognome=cognome==null?"":(String)o_cognome;
  Object o_password=rs_utente.getObject("USER_PASS");
  password=(String)o_password==null?"":(String)o_password;
  Object o_data_nascita=rs_utente.getObject("DATA_NASCITA");
  data_nascita=o_data_nascita==null?"":sdf.format(o_data_nascita);
  Object o_codice_fiscale=rs_utente.getObject("CODICE_FISCALE");
  codice_fiscale=o_codice_fiscale==null?"":(String)o_codice_fiscale;
}

%>
<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
    <td>|</td>
		<td>
			<a href= "EesModificaUtente.jsp?utente=<%=utente%>" >Modifica Utente</a>
		</td>
		<td>|</td>
		<td>
			<a href= "EesGestioneUtenti.jsp">Gestione Utenti</a>
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
    <td><IMG SRC="../myimg/home.jpg" alt="myimg/home.jpg" align="middle">&nbsp;<a href="../EesMenu.jsp" >Menù Principale</a></td>
    <td>|</td>        
    </tr>
</table>

<br/>

<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo"> 
    <TD>Visualizza Utente&nbsp;&nbsp;</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>
  <TR  class="HelpRigaTitoloSezione" >
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>

<form name="form" action="EesVisualizzaUtente.jsp">
<p>
    <div align="center">
      <p>
      <table cellspacing="3" cellpadding="2" border="0" width="30%" class ="form">
          <tr>
            <td width="40%" class="label">Login</td>
            <td width="60%"><input name="utente" type="text" value="<%=utente%>" class="campi" readonly /></td>
          </tr>
          <tr>
            <td width="30%" class="label">Password</td>
            <td width="70%"><input name="password1" type="text" class="campi" value="<%=password%>" readonly  /></td>
          </tr>
          <tr>
            <td width="30%" class="label">Nome</td>
            <td width="70%"><input name="nome" type="text" class="campi" value="<%=nome%>" readonly /></td>
          </tr>
          <tr>
            <td width="30%" class="label">Cognome</td>
            <td width="70%"><input name="cognome" type="text" class="campi" value="<%=cognome%>" readonly /></td>
          </tr>
          <tr>
            <td width="30%" class="label">Data di Nascita</td>
            <td width="70%"><input name="datanascita" type="text" class="campi" value="<%=data_nascita%>" readonly /></td>
          </tr>
          <tr>
            <td width="30%" class="label">Codice Fiscale</td>
            <td width="70%"><input name="codicefiscale" type="text" class="campi" value="<%=codice_fiscale%>" readonly /></td>
          </tr>
          <tr>
            <td width="30%" class="label">Ruolo</td>
            <td width="70%"><input name="ruolo" type="text" class="campi" value="<%=ruolo%>" readonly /></td>
          </tr>
      </table>
    </div>
    <p>

    <div align="center" ></div>
            <table cellspacing="3" cellpadding="5" border="0" width="60%" align="center" class ="form">
                <tbody>
                    <tr>
                        <td height="23" width="45%" >
                            <table>
                            <tr><td align="center" class="etichetta"> Gruppi</td></tr>
                            <tr><td>
                            <select name="disponibili" size="15" multiple="multiple" style="width:200px" onClick="clearList(this)">
                            <% while(rs_gruppi_attivi.next()){%>
                            <option value='<%=rs_gruppi_attivi.getObject("GRUPPO")%>'><%=rs_gruppi_attivi.getObject("GRUPPO")%></option>
                            <%}%>
                            </select>
                            </td></tr>
                            </table>
                        </td>
                        <td height="23" width="10%" align="center">
                            <!--
                            <table cellspacing="2" cellpadding="3" border="0" 
                                   width="100%">
                                
                                <tbody>
                                    <tr>
                                        <td align="center">
                                            <input  style="width:60px;height:20px;font-size:15px" name="azione" value="   --&gt;   " type="submit" onClick="verificaCampi(this.form)" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <input style="width:60px;height:20px;font-size:15px" name="azione" value="   &lt;--   " type="submit" onClick="document.form.submit();" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            -->
                        </td>
                        
                        <td height="23" width="45%" align="center" >
                            <table >
                            <tr><td align="center" class="etichetta" >Servizi</td></tr>
                            <tr><td>
                            <select size="15" name="attivi" multiple="multiple" style="width:200px" onClick="clearList(this)">
                            <% while(rs_servizi_attivi.next()){%>
                            <option value='<%=rs_servizi_attivi.getObject("DES_BREVE")%>'><%=rs_servizi_attivi.getObject("DES_BREVE")%></option>
                            <%}%>
                            </select>
                            </td></tr>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
</form>
<%
  
  rs_servizi_attivi.close();
  rs_gruppi_attivi.close();
  rs_ruolo.close();
  rs_utente.close();
  stm1.close();
  stm2.close();
  stm3.close();
  stm4.close();
  conn.close();
  
%>
  </BODY>
</html>
