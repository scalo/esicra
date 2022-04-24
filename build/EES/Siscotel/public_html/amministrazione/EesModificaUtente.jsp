
<%@ page language="java" errorPage="GestioneErrori.jsp" contentType="text/html;charset=windows-1252" 
    import = "javax.sql.*,  
              java.sql.*,  
              it.saga.egov.esicra.*,
              java.text.SimpleDateFormat"
%>
<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="../esicra.css">
<TITLE>Modifica Utente</TITLE>
<script type="text/javascript">
  
  function checkDate(str){
    var pattern="^$|^\\d{1,2}\/\\d{1,2}\/(19|20)\\d{2}$";
    var rex = new RegExp(pattern);
    //alert(pattern);
    //alert(str);
    if(rex.test(str))
      return true;
    return false;
  }
  
  function ControlloDati(form){
    //alert("controllo dati obbligatori");
    var login = document.form.utente.value;
    var nome = document.form.nome.value;
    var cognome = document.form.cognome.value;
    var password1 = document.form.password1.value;
    var password2 = document.form.password2.value;
    var datanascita = document.form.data_nascita.value;
    if(!checkDate(datanascita)){
      alert("Errore data di nascita \n il formato corretto è gg/mm/aaaa");
      return false;
    }
    var codicefiscale = document.form.codice_fiscale.value;
    var ruolo = document.form.ruolo.value;
    if(login==null ||login.length==0 ){
      alert("Login Utente non impostata");
      return false;
    }
    if(ruolo==null ||ruolo.length==0 ){
      alert("Ruolo Utente non impostato");
      return false;
    }
    if(nome==null ||nome.length==0 ){
      alert("Nome Utente non impostato");
      return false;
    }
    if(cognome==null ||cognome.length==0 ){
      alert("Cognome Utente non impostato");
      return false;
    }
    if(password1 =="" ||password1=="" || password1!=password2 ){
      alert("Password non coincidenti");
      return false;
    }
    alert( "Salvare l'utente con le seguenti impostazioni ?\n\n"+
      "utente: '"+login+"'\n"+
      "nome: '"+nome+"'\n"+
      "cognome: '"+cognome+"'\n"+
      "ruolo: '"+ruolo+"'\n"+
      //"password1: '"+password1+"'\n"+
      //"password2: '"+password2+"'\n"+
      "data nascita: '"+datanascita+"'\n"+
      "codice fiscale: '"+codicefiscale+"'\n"
    );
    
    return true;
  }
  
  function SalvaModifiche(form){
    //alert("salva modifiche");
    var res = confirm("Salvare le modifiche all'utente "+ form.utente.value +" ?");
    if(res){
      var ok=ControlloDati(form);
      if(ok){
        // submit
        form.azione.value="salva_modifiche";
        form.submit();
      }
    }
    return;
  }
  
  function RimuoviUtente(form){
    //alert("rimuovi utente");
    var res = confirm ("Rimuovere l'utente " +form.utente.value +" ?");
    if(res){
        // submit
        form.azione.value="rimuovi_utente";
        form.submit();
    }
    return;
  }

  function AggiungiUtente(form){
    var res = confirm ("Creare un nuovo utente e abbandonare le modifiche correnti ?");
    if(res)
       window.location="EesModificaUtente.jsp";
    else
      return;
  }

  function selezionato(list){
      for(var j=0;j<list.length;j++){
        if (list[j].selected==true)
          return true;
      }
    return false;
  }

  function AggiungiGruppo(form){
    form.azione.value="aggiungi_gruppo";
    form.submit();
  }
  
  function RimuoviGruppo(form){
    form.azione.value="rimuovi_gruppo";
    form.submit();
  }

  function AbilitaDisponibili(form){
    //alert("disponibili "+selezionato(form.disponibili.options));
    form.disponibili.focus();
    var disponibili = selezionato(form.disponibili.options) ;
    var new_user = form.nuovo_utente.value ;
    if(disponibili && new_user=="false") {
       form.rimuovi.disabled=true;
       form.aggiungi.disabled=false;
       for(var i=0;i<form.attivi.options.length;i++){
          form.attivi.options[i].selected=false;
       }
    }
  }
  
  function AbilitaAttivi(form){ 
    //alert("attivi "+selezionato(form.attivi.options));
    form.attivi.focus();
    var attivi = selezionato(form.attivi.options) ;
    var new_user = form.nuovo_utente.value ;
    if(attivi && new_user=="false"){
      form.rimuovi.disabled=false;
      form.aggiungi.disabled=true;
      for(var i=0;i<form.disponibili.options.length;i++){
        form.disponibili.options[i].selected=false;
      }
    }
  }
</script>
</head>

<body class = "body">

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
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
    <TD>Modifica Utente&nbsp;&nbsp;</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>
  <TR  class="HelpRigaTitoloSezione" >
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>

<%

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
DataSource ds = EsicraConf.getDataSource("esicra.datasource_nodo");
// legge parametri in ingresso
String disponibili[] = request.getParameterValues("disponibili");
String attivi[] = request.getParameterValues("attivi");
// parametri
String utente = request.getParameter("utente");
String nome = request.getParameter("nome");
String cognome = request.getParameter("cognome");
String data_nascita = request.getParameter("data_nascita");
String codice_fiscale = request.getParameter("codice_fiscale");
String ruolo  = request.getParameter("ruolo");
String password = request.getParameter("password1");
String azione = request.getParameter("azione");
String nuovo_utente="false";
//System.out.println(utente==null || utente.trim().length()==0);
if (utente==null || utente.trim().length()==0) {
  utente="";
  nuovo_utente="true";
  nome="";
  utente="";
  cognome="";
  password="";
  data_nascita="";
  codice_fiscale="";
  ruolo="";
}

/*
System.out.println("nuovo utente:"+nuovo_utente);
System.out.println("utente:"+utente);
System.out.println("azione:"+azione);
System.out.println("disponibili:"+disponibili);
System.out.println("attivi:"+attivi);
*/

if (azione==null){
  azione="";
} 

String query_gruppi_disponibili="select * from ser_gruppo where nome  not in (select u.gruppo from ser_gruppo_utente u ,ser_gruppo g  where u.gruppo=g.nome and u.utente='"+utente+"')";
String query_gruppi_attivi="select u.gruppo from ser_gruppo_utente u ,ser_gruppo g  where u.gruppo=g.nome and u.utente='"+utente+"'";
String query_servizi_attivi="select distinct s.id_servizio,s.des_breve from  ser_gruppo g,ser_gruppo_servizio gs, ser_servizio s , ser_gruppo_utente gu where gs.gruppo=g.nome and s.id_servizio=gs.id_servizio and tipo_export=2 and gu.gruppo=gs.gruppo and gu.utente='"+utente+"' order by s.des_breve";
String query_utente="select * from j2ee_users where user_name='"+utente+"'";
String query_ruolo="select * from j2ee_users u, j2ee_user_roles r where u.user_name=r.user_name and r.user_name='"+utente+"'";
//System.out.println(query_ruolo);
//System.out.println(query_utente);
Connection conn = ds.getConnection();
Statement stm1 = conn.createStatement();
Statement stm2 = conn.createStatement();
Statement stm3 = conn.createStatement();
Statement stm4 = conn.createStatement();
Statement stm5 = conn.createStatement();

PreparedStatement insert_j2ee_user = conn.prepareStatement("insert into j2ee_users (user_name,nome,cognome,user_pass,data_nascita,codice_fiscale,dt_ins,dt_mod)  values (?,?,?,?,?,?,?,?) ");
PreparedStatement insert_j2ee_user_role = conn.prepareStatement("insert into j2ee_user_roles (user_name,role_name)  values (?,?) ");
PreparedStatement update_j2ee_user = conn.prepareStatement("update j2ee_users set nome=?,cognome=?,user_pass=?,data_nascita=?,codice_fiscale=?,dt_mod=?  where  user_name=?");
PreparedStatement update_j2ee_user_role = conn.prepareStatement("update j2ee_user_roles set user_name=?,role_name=? where user_name=? ");
PreparedStatement delete_j2ee_user = conn.prepareStatement("delete from j2ee_users where user_name=? ");
PreparedStatement delete_j2ee_user_role = conn.prepareStatement("delete from j2ee_user_roles where user_name=? ");

PreparedStatement insert = conn.prepareStatement("insert into ser_gruppo_utente (gruppo,utente)  values (?,?) ");
PreparedStatement delete = conn.prepareStatement("delete from ser_gruppo_utente where gruppo=? and utente=?");

Timestamp ts = new Timestamp(System.currentTimeMillis());
 
// processa azione
try{
  if(azione.equalsIgnoreCase("aggiungi_gruppo")){
    //aggiunge gruppo
    for(int i=0 ;i<disponibili.length;i++){
      //System.out.println("aggiungi "+disponibili[i]);
      insert.setObject(1,disponibili[i]);
      insert.setObject(2,utente);
      insert.execute();
    }
  }else if(azione.equalsIgnoreCase("rimuovi_gruppo")){
    //rimuove gruppo
    for(int i=0 ;i<attivi.length;i++){
      //System.out.println("rimuovi "+attivi[i]);
      delete.setObject(1,attivi[i]);
      delete.setObject(2,utente);
      delete.execute();
    }
  }else if(azione.equalsIgnoreCase("salva_modifiche")){
    // System.out.println("salva modifiche utente");
    // salva modifiche utente
    // inserimento
    try{
     
      insert_j2ee_user.setObject(1,utente);
      insert_j2ee_user.setObject(2,nome);
      insert_j2ee_user.setObject(3,cognome);
      insert_j2ee_user.setObject(4,password);
      if(!data_nascita.equals(""))
        insert_j2ee_user.setObject(5,sdf.parse(data_nascita));
      else
        insert_j2ee_user.setObject(5,null);
      insert_j2ee_user.setObject(6,codice_fiscale);
      insert_j2ee_user.setObject(7,ts);
      insert_j2ee_user.setObject(8,ts);
      insert_j2ee_user.execute();
      
      insert_j2ee_user_role.setObject(1,utente);
      insert_j2ee_user_role.setObject(2,ruolo);
      insert_j2ee_user_role.execute();
      
    }catch(SQLException e){
      //System.out.println("errore inserimento"+e);
    }
    // update
    update_j2ee_user.setObject(1,nome);
    update_j2ee_user.setObject(2,cognome);
    update_j2ee_user.setObject(3,password);
    if(!data_nascita.equals(""))
      update_j2ee_user.setObject(4,sdf.parse(data_nascita));
    else
      update_j2ee_user.setObject(4,null);
    update_j2ee_user.setObject(5,codice_fiscale);
    update_j2ee_user.setObject(6,ts);
    update_j2ee_user.setObject(7,utente);
    update_j2ee_user.execute();
    
    update_j2ee_user_role.setObject(1,utente);
    update_j2ee_user_role.setObject(2,ruolo);
    update_j2ee_user_role.setObject(3,utente);
    update_j2ee_user_role.execute();
    
  }else if(azione.equalsIgnoreCase("rimuovi_utente")){
    //rimuove utente
    delete_j2ee_user.setObject(1,utente);
    delete_j2ee_user.execute();
    //rimuove ruolo
    delete_j2ee_user_role.setObject(1,utente);
    delete_j2ee_user_role.execute();
    // pulisce campi
    utente="";
    nome="";
    cognome="";
    password="";
    data_nascita="";
    codice_fiscale="";
    ruolo="";
  }
}catch(SQLException ex){
  // probabile chiave duplicata
  System.out.println("Errore Sql "+ex);
}

ResultSet rs_gruppi_disponibili =stm1.executeQuery(query_gruppi_disponibili);
ResultSet rs_gruppi_attivi =stm2.executeQuery(query_gruppi_attivi);
ResultSet rs_servizi_attivi =stm3.executeQuery(query_servizi_attivi);
ResultSet rs_utente =stm4.executeQuery(query_utente);
ResultSet rs_ruolo= stm5.executeQuery(query_ruolo);

//String nome="";
//String cognome="";
//String password="";
//String data_nascita="";
//String codice_fiscale="";
//String ruolo="";

if(rs_utente.next()){
  Object o_nome=rs_utente.getObject("NOME");
  nome=o_nome==null?"":(String)o_nome;
  Object o_cognome=rs_utente.getObject("COGNOME");
  cognome=o_cognome==null?"":(String)o_cognome;
  //System.out.println("nome:"+nome);
  //System.out.println("cognome:"+cognome);
  Object o_password=rs_utente.getObject("USER_PASS");
  password=(String)o_password==null?"":(String)o_password;
  Object o_data_nascita=rs_utente.getObject("DATA_NASCITA");
  data_nascita=o_data_nascita==null?"":sdf.format(o_data_nascita);
  Object o_codice_fiscale=rs_utente.getObject("CODICE_FISCALE");
  codice_fiscale=o_codice_fiscale==null?"":(String)o_codice_fiscale;
}

if(rs_ruolo.next()){
  Object o_ruolo=rs_ruolo.getObject("ROLE_NAME");
  ruolo=o_ruolo==null?"":(String)o_ruolo;
  //System.out.println("ruolo:"+ruolo);
}

%>

<form name="form" action="EesModificaUtente.jsp">
<input type="hidden" name="nuovo_utente" value="<%=nuovo_utente%>">
<input name="azione" type="hidden"  value="">
<p>
    <div align="center">
      <p>
      <table width="400px" ><tr><td>
      <table cellspacing="3" cellpadding="2" border="0" width="260px" class ="form">
          <tr>
            <td width="100px" class="label">Login</td>
            <td width="120px"><input name="utente" type="text" value="<%=utente%>" class="campi" /></td>
          </tr>
          <tr>
            <td class="label">Password</td>
            <td ><input name="password1" type="password" class="campi" value="<%=password%>"/></td>
          </tr>
          <tr>
            <td class="label">Conferma Password</td>
            <td><input name="password2" type="password"  class="campi" value="<%=password%>"/></td>
          </tr>
          <tr>
            <td class="label">Nome</td>
            <td><input name="nome" type="text" class="campi" value="<%=nome%>"/></td>
          </tr>
          <tr>
            <td class="label">Cognome</td>
            <td ><input name="cognome" type="text" class="campi" value="<%=cognome%>"/></td>
          </tr>
          <tr>
            <td class="label">Data di Nascita</td>
            <td><input name="data_nascita" type="text" class="campi" value="<%=data_nascita%>"/></td>
          </tr>
          <tr>
            <td class="label">Codice Fiscale</td>
            <td><input name="codice_fiscale" type="text" class="campi" value="<%=codice_fiscale%>" maxlength=16 /></td>
          </tr>
          <tr>
            <td class="label">Ruolo</td>
            <td><select style="width:120px" name="ruolo"  class="campi" >
              <option></option>
              <option value="amministratore" <% if (ruolo.equals("amministratore")) out.print("SELECTED");%> >amministratore</option>
              <option value="operatore" <% if (ruolo.equals("operatore")) out.print("SELECTED");%> >operatore</option>
            </select></td>
          </tr>
      </table>
      </td>
      <td>
      <table align="center"><tr>
      <td align="center" style="height:60px;color:red; font-weight: bolder;font-size:10pt"><%
        if(nuovo_utente.equals("true")){
          out.println("utente non memorizzato");
        }
        //else out.println("utente memorizzato");
      %></td>
      </tr>
      <tr>
      <td align="center">
        <input  class="pulsante"  name="modifica_utente" value="Salva Modifiche" type="button"  onClick="SalvaModifiche(document.form)"/>
      </td>
      <tr><td>&nbsp;</td></tr>
      <tr><td align="center">
        <input  class="pulsante"  name="rimuovi_utente" value="Rimuovi Utente" type="button"  onClick="RimuoviUtente(document.form)"/>
      </tr></td>
      <tr><td>&nbsp;</td></tr>
      <tr><td align="center">
        <input  class="pulsante"  name="aggiungi_utente" value="Aggiungi Utente" type="button"  onClick="AggiungiUtente(document.form)"/>
      </tr></td>
      </tr>
      <!--
      <tr><td>&nbsp;</td></tr>
      <tr>
      <td align="center">
        <input  width="80" class="pulsante"  name="check" value="Check" type="button"  onClick='alert("data nascita "+checkDate(document.form.data_nascita.value))'/>
      </td>
      </tr>
      -->
      </table>
      </td>
      </tr>
      </table>
      </div>
    <p>

    <div align="center" ></div>
            <table cellspacing="3" cellpadding="5" border="0" width="70%" align="center" class ="form">
                <tbody>
                    <tr>
                        
                        <td height="23" width="20%" >
                            <table>
                            <tr><td align="center" class="etichetta" >Gruppi Disponibili</td></tr>
                            <tr><td>
                            <select  size="15" name="disponibili" style="width:200px" multiple="multiple"  onClick=AbilitaDisponibili(document.form)>
                            <% while(rs_gruppi_disponibili.next()){%>
                            <option value='<%=rs_gruppi_disponibili.getObject("NOME")%>'><%=rs_gruppi_disponibili.getObject("NOME")%></option>
                            <%}%>
                            </select>
                            </td></tr>
                            </table>
                        </td>
                        
                        <td height="23" width="10%" align="center">
                            <table cellspacing="2" cellpadding="3" border="0" 
                                   width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center">
                                            <input  disabled  style="width:60px;height:20px;font-size:15px" name="aggiungi" value="   --&gt;   " type="button" onClick="AggiungiGruppo(document.form)" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <input disabled style="width:60px;height:20px;font-size:15px" name="rimuovi" value="   &lt;--   " type="button" onClick="RimuoviGruppo(document.form)" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        
                        <td height="23" width="20%" align="center" >
                            <table >
                            <tr><td align="center" class="etichetta" >Gruppi Attivi</td></tr>
                            <tr><td>
                            <select size="15" name="attivi" multiple="multiple" style="width:200px"  onClick="AbilitaAttivi(document.form)">
                            <% while(rs_gruppi_attivi.next()){%>
                            <option value='<%=rs_gruppi_attivi.getObject("GRUPPO")%>'><%=rs_gruppi_attivi.getObject("GRUPPO")%></option>
                            <%}%>
                            </select>
                            </td></tr>
                            </table>
                        </td>
                        <td height="23" width="10%" align="center">
                        </td>
                        <td height="23" width="20%" align="center" >
                            <table >
                            <tr><td align="center" class="etichetta" >Servizi</td></tr>
                            <tr><td>
                            <select  size="15"  name="servizi" multiple="multiple" style="width:200px" >
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

  rs_gruppi_disponibili.close();
  rs_gruppi_attivi.close();
  rs_servizi_attivi.close();
  rs_utente.close();
  rs_ruolo.close();
  stm1.close();
  stm2.close();
  stm3.close();
  stm4.close();
  stm5.close();

  insert_j2ee_user.close();
  insert_j2ee_user_role.close();
  update_j2ee_user.close();
  update_j2ee_user_role.close(); 
  delete_j2ee_user.close();
  delete_j2ee_user_role.close(); 
  insert.close();
  delete.close();
  
  conn.close();
  
%>
  </body>
</html>



