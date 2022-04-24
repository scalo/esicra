<%@ page language="java" errorPage="../GestioneErrori.jsp" contentType="text/html;charset=windows-1252" 
    import = "it.saga.egov.esicra.*,
              java.sql.*, 
              javax.sql.*, 
              java.util.*,  
              java.math.* "
%>

<html>
<head>

<LINK REL=STYLESHEET TYPE="text/css" HREF="../esicra.css">
<TITLE>Gestione Gruppi</TITLE>
</head>

<script type="text/javascript">
  
 function trim(s) {
    while (s.substring(0,1) == ' ') {
      s = s.substring(1,s.length);
    }
    while (s.substring(s.length-1,s.length) == ' ') {
      s = s.substring(0,s.length-1);
    }
    return s;
 }
  
  function AggiungiServizio(form){
    form.azione.value="aggiungi_servizio";
    //var res = confirm ("Aggiungere il servizio "+form.id_servizio.value + " al gruppo "+form.gruppo.value+" ?");
    form.submit();
  }
  
  function RimuoviServizio(form){
    form.azione.value="rimuovi_servizio";
    //var res = confirm ("Rimuovere il servizio "+form.id_servizio.value + " dal gruppo "+form.gruppo.value+" ?");
    form.submit();
  }
  
  function AggiungiGruppo(form){
    form.nuovo_gruppo.value = trim(form.nuovo_gruppo.value);
    var gruppo=form.nuovo_gruppo.value;
    if(gruppo.length>0){
      var res = confirm ("Aggiungere il gruppo "+gruppo +" ["+form.des_gruppo.value+"] ?" );
      if(!res) return;
      form.azione.value="aggiungi_gruppo";
      form.submit();
    }else{
      alert("impossibile aggiungere gruppo nullo !");
    }
  }
  
  function RimuoviGruppo(form){
    form.gruppo.value=trim(form.gruppo.value);
    var gruppo=form.gruppo.value;
    if(gruppo.length>0){
      if(gruppo=="tutti"){
        alert("Impossibile cancellare il gruppo predefinito");
        return;
      }
      var res = confirm("Si è sicuri di voler cancellare il gruppo: "+gruppo+"  ?");
      if(res){
        form.azione.value="rimuovi_gruppo";
        form.submit();
      }else {
        //alert("non rimosso");
        return;
      }
    }else{
      alert("Impossibile rimuovere gruppo nullo !");
    }
  }
  
  function selezionato(list){
    for(var j=0;j<list.length;j++){
      if (list[j].selected==true)
        return true;
    }
    return false;
  }
  
  function AbilitaDisponibili(form){
    //alert("disponibili "+selezionato(form.disponibili.options));
    form.disponibili.focus();
    var disponibili = selezionato(form.disponibili.options) ;
    if(disponibili && form.gruppo.value.length>0 ) {
       form.rimuovi_srv.disabled=true;
       form.aggiungi_srv.disabled=false;
       for(var i=0;i<form.attivi.options.length;i++){
          form.attivi.options[i].selected=false;
       }
    }
  }
  
  function AbilitaAttivi(form){ 
    //alert("attivi "+selezionato(form.attivi.options));
    form.attivi.focus();
    var attivi = selezionato(form.attivi.options) ;
    if(attivi && form.gruppo.value.length>0){
      form.rimuovi_srv.disabled=false;
      form.aggiungi_srv.disabled=true;
      for(var i=0;i<form.disponibili.options.length;i++){
        form.disponibili.options[i].selected=false;
      }
    }
  }
  
  function ControlloGruppo(form){
    //alert(form.gruppo.value.length);
    var ok = form.gruppo.value.length>0;
    if(ok) {
      form.submit();
    }else{
      alert("Nessun servizio selezionato");
      return;
    }
  }

/*
  function controllo_attivi(form){
    var attivi = form.attivi.value;
    alert(attivi);
    var ok = attivi.size()>0;
    if(ok) form.submit();
    else {
      alert("Nessun servizio selezionato");  
      return;
    }
  }
  
  function controllo_disponibili(form){
    var disponibili = form.disponibili.value ;
    var ok = disponibili.size()>0;
    if(ok) form.submit();
    else {
      alert("Nessun servizio selezionato");
      return;
    }
  }
*/
  
</script>
<body class = "body" >

<!-- barra menu -->
<table border="0" align="right" class="barramenu" > 
    <tr>
		<td>|</td>
		<td>
			<a href="EesGestioneUtenti.jsp">Gestione Utenti</a>
		</td>
        <td>|</td>
        <td>
            <a href= "../EesAmministrazione.jsp">Menù Amministrazione</a>
        </td>
        <td>|</td>
        <td><IMG SRC="../myimg/home.jpg" align="middle" ALT="myimg/home.jpg">&nbsp;<a href="../EesMenu.jsp" >Menù Principale</a></td>
        <td>|</td>        
    </tr>
</table>

<br/>

<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo"> 
    <TD>Gestione Gruppi&nbsp;&nbsp;</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>
  <TR  class="HelpRigaTitoloSezione" >
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>

<br/>
<br/>
<br/>

<%
DataSource ds = EsicraConf.getDataSource("esicra.datasource_nodo");
String rowStyle="";
// legge parametri in ingresso
String disponibili[] = request.getParameterValues("disponibili");
String attivi[] = request.getParameterValues("attivi");
String gruppo = request.getParameter("gruppo");
String nuovo_gruppo = request.getParameter("nuovo_gruppo");
String des_gruppo = request.getParameter("des_gruppo");
/*
String azione = request.getParameter("azione");
String aggiungi = request.getParameter("aggiungi");
String rimuovi = request.getParameter("rimuovi");
*/
String azione = request.getParameter("azione");
if (gruppo==null) gruppo=" ";
if (azione==null) azione=" ";
if (nuovo_gruppo==null) nuovo_gruppo="";
if (des_gruppo==null) des_gruppo="";
//System.out.println("gruppo:"+gruppo);
//System.out.println("azione:"+azione);
//System.out.println("nuovo_gruppo:"+nuovo_gruppo);
//System.out.println("des_gruppo:"+des_gruppo);
//System.out.println("disponibili:"+disponibili);
//System.out.println("attivi:"+attivi);
String query_gruppi="select * from ser_gruppo order by nome";
String query_servizi_disponibili="select * from ser_servizio s where tipo_export=2 and s.id_servizio not in (select gs.id_servizio from ser_gruppo_servizio gs where gs.gruppo='"+gruppo+"' ) order by des_breve";
String query_servizi_attivi="select g.nome , s.id_servizio,s.des_breve from  ser_gruppo g,ser_gruppo_servizio gs, ser_servizio s where gs.gruppo=g.nome and s.id_servizio=gs.id_servizio and tipo_export=2 and g.nome like '"+gruppo+"' order by s.des_breve";
//System.out.println(query_servizi_attivi);
Connection conn = ds.getConnection();
Statement stm1 = conn.createStatement();
Statement stm2 = conn.createStatement();
Statement stm3 = conn.createStatement();
PreparedStatement insert_group = conn.prepareStatement("insert into ser_gruppo (nome,des_gruppo)  values (?,?) ");
PreparedStatement delete_group = conn.prepareStatement("delete from ser_gruppo where nome=? ");
PreparedStatement insert = conn.prepareStatement("insert into ser_gruppo_servizio (gruppo,id_servizio)  values (?,?) ");
PreparedStatement delete = conn.prepareStatement("delete from ser_gruppo_servizio where gruppo=? and id_servizio=?");

// processa azione
try{
  if(azione.equalsIgnoreCase("aggiungi_servizio")){
    //aggiunge servizi
    for(int i=0 ;i<disponibili.length;i++){
      //System.out.println("aggiungi "+disponibili[i]);
      insert.setObject(1,gruppo);
      insert.setObject(2,disponibili[i]);
      insert.execute();
    }
  }else if(azione.equalsIgnoreCase("rimuovi_servizio") ){
    //rimuove servizi
    for(int i=0 ;i<attivi.length;i++){
      //System.out.println("rimuovi "+attivi[i]);
      delete.setObject(1,gruppo);
      delete.setObject(2,attivi[i]);
      delete.execute();
    }
  }
  if(azione.equalsIgnoreCase("aggiungi_gruppo") && nuovo_gruppo.trim().length()>0){
    //System.out.println("aggiungi gruppo  "+nuovo_gruppo);
    insert_group.setObject(1,nuovo_gruppo);
    insert_group.setObject(2,des_gruppo);
    insert_group.execute();
    gruppo=nuovo_gruppo;
    nuovo_gruppo="";
    des_gruppo="";
    query_servizi_disponibili="select * from ser_servizio s where tipo_export=2 and s.id_servizio not in (select gs.id_servizio from ser_gruppo_servizio gs where gs.gruppo='"+gruppo+"' ) order by des_breve";
    query_servizi_attivi="select g.nome , s.id_servizio,s.des_breve from  ser_gruppo g,ser_gruppo_servizio gs, ser_servizio s where gs.gruppo=g.nome and s.id_servizio=gs.id_servizio and tipo_export=2 and g.nome like '"+gruppo+"' order by s.des_breve";

  }
  
  if(azione.equalsIgnoreCase("rimuovi_gruppo") && gruppo.trim().length()>0){
    //System.out.println("rimuovi gruppo  "+gruppo);
    delete_group.setObject(1,gruppo);
    delete_group.execute();
    gruppo="";
  }
}catch(SQLException e){
  // probabile chiave duplicata
  System.out.println("chiave duplicata");
  System.out.println(e);
}

ResultSet rs_gruppi =stm1.executeQuery(query_gruppi);
ResultSet rs_servizi_disponibili =stm2.executeQuery(query_servizi_disponibili);
ResultSet rs_servizi_attivi =stm3.executeQuery(query_servizi_attivi);

%>
<div align="center" >
<form name="form" action="EesGestioneGruppi.jsp">
<input name="azione" type="hidden"  value="">
<table>
<tr>
    <td class="label">Nuovo Gruppo:</td>
    <td class="input"><input tabindex=1 name="nuovo_gruppo" type="text" maxlength="30" size="30" value="<%=nuovo_gruppo%>" /></td>
    <td><input tabindex=3   class="pulsante"  name="aggiungi_gruppo" value="Aggiungi Gruppo" type="button"  onClick="AggiungiGruppo(document.form)"/></td>
</tr>
<tr>
    <td class="label">Descrizione:</td>
    <td class="input"><input  tabindex=2 name="des_gruppo" type="text" size="30" value="<%=des_gruppo%>" /></td>
    </tr>
</table>
<table>
<tr>
<td class="etichetta">Gruppo&nbsp;&nbsp;
</tr>
</td>
<tr>
<td>
<SELECT style="width:100px" id="gruppo" name="gruppo" class="campi" onChange="ControlloGruppo(document.form)">
<OPTION value=<%=gruppo%>><%=gruppo%></OPTION>
<% while(rs_gruppi.next()){%>
  <OPTION value='<%=rs_gruppi.getObject("NOME")%>'><%=rs_gruppi.getObject("NOME")%></OPTION>
<%}%>
</SELECT>
</td>
<td><input class="pulsante"  name="rimuovi_gruppo" value="Rimuovi Gruppo" type="button"  onClick="RimuoviGruppo(document.form)"/></td>
</tr>
</table>
<%
// pulisce parametri
  disponibili = null;
  attivi = null;
  gruppo = null;
  azione = null;
%>
        <br/>
            <table cellspacing="3" cellpadding="5" border="0" width="50%" align="center">
                <tbody>
                    
                    <tr><td>
                        <table>
                        <tr><td align="center" class="etichetta"><b>Servizi Disponibili</b></td></tr>
                        <tr>
                        <td height="23" width="40%" align="center" >
                            <select name="disponibili" size="15" multiple="multiple" style="width:200px" onChange="AbilitaDisponibili(document.form)">
                            <% while(rs_servizi_disponibili.next()){%>
                            <option value='<%=rs_servizi_disponibili.getObject("ID_SERVIZIO")%>'><%=rs_servizi_disponibili.getObject("DES_BREVE")%></option>
                            <%}%>
                            </select>
                        </td>
                        </tr>
                        </td>
                        </table>
                        <td height="23" width="10%" align="center">
                            <table cellspacing="2" cellpadding="3" border="0" 
                                   width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center">
                                            <input  style="width:60px;height:20px;font-size:15px" name="aggiungi_srv" onClick="AggiungiServizio(document.form);" value="   --&gt;   " type="button"  disabled="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> </td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <input style="width:60px;height:20px;font-size:15px" name="rimuovi_srv" onClick="RimuoviServizio(document.form)" value="   &lt;--   " type="button" disabled="true" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        
                        <td>
                        <table>
                        <tr><td align="center" class="etichetta"><b>Servizi Attivi</b></td></tr>
                        <tr>
                        <td height="23" width="40%" align="center" >
                            <select size="15" name="attivi" multiple="multiple" style="width:200px" onChange="AbilitaAttivi(document.form)">                  
                            <% while(rs_servizi_attivi.next()){%>
                            <option value='<%=rs_servizi_attivi.getObject("ID_SERVIZIO")%>'><%=rs_servizi_attivi.getObject("DES_BREVE")%></option>
                            <%}%>
                            </select>
                        </td>
                        </tr>
                        </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
<%
  rs_gruppi.close();
  rs_servizi_disponibili.close();
  rs_servizi_attivi.close();
  stm1.close();
  stm2.close();
  stm3.close();
  insert.close();
  delete.close();
  insert_group.close();
  delete_group.close();
  conn.close();
  
%>
</div>
</body>
</html>

