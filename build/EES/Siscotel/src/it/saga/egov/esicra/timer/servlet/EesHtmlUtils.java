package it.saga.egov.esicra.timer.servlet;

import it.saga.egov.esicra.timer.*;
import java.util.Vector;
import java.util.Calendar;
import java.sql.*;
import javax.sql.*;
import it.saga.egov.esicra.*;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 *  Classe di utilità per lapplicazione Web di amministrazione del Timer
 *  E' costituita da metodi statici
 */
public class EesHtmlUtils  {

  public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
  
  public static final String[] mesi={"Gennaio","Febbraio","Marzo","Aprile","Maggio",
    "Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
   
  public static int MAX_ANNI=10;
  
  public static String[] tipi(){
    Hashtable tipi=EesTimerConf.listaTasks();
    Vector list= new Vector();
    Enumeration enu = tipi.keys();
    while(enu.hasMoreElements()){
      Object key = enu.nextElement();
      // ATTENZIONE che il value dell'option è SOLO il nome della classe
      String opt="<option value=\""+tipi.get(key)+"\">"+key+"</option>";
      list.add(opt);
    }
    String[] array = new String[list.size()];
    array = (String[])list.toArray(array);
    return array;
  }
  
  public static String[] anni(){
    Vector list= new Vector();
    for(int i=0;i<MAX_ANNI;i++){
      // prende l'anno corrente 
      int anno=Calendar.getInstance().get(Calendar.YEAR);
      int n=anno+i;
      String opt="<option value=\" "+n+" \">"+n+"</option>";
      list.add(opt);
    }
    String[] array = new String[list.size()];
    array = (String[])list.toArray(array);
    return array;
  }

  public static String[] mesi(){
    Vector list= new Vector();
    for(int i=0;i<=11;i++){
      String opt="<option value=\""+ i +"\">"+mesi[i] + "</option>";
      list.add(opt);
    }
    String[] array = new String[list.size()];
    array = (String[])list.toArray(array);
    return array;
  }

  public static String[] giorni(){
    Vector list= new Vector();
    for(int i=1;i<=31;i++){
      String opt="<option value=\" "+i+" \">"+i+"</option>";
      list.add(opt);
    }
    String[] array = new String[list.size()];
    array = (String[])list.toArray(array);
    return array;
  }

  public static String[] ore(){
    Vector list= new Vector();
    for(int i=0;i<=23;i++){
      String opt="<option value=\" "+i+" \">"+i+"</option>";
      list.add(opt);
    }
    String[] array = new String[list.size()];
    array = (String[])list.toArray(array);
    return array;
  }

  public static String[] minuti(){
    Vector list= new Vector();
    for(int i=0;i<=59;i++){
      String opt="<option value=\" "+i+" \">"+i+"</option>";
      list.add(opt);
    }
    String[] array = new String[list.size()];
    array = (String[])list.toArray(array);
    return array;
  }

  public static String[] serviziPagamenti(){
    String[] array = null;
    try{
        Vector list= new Vector();
        DataSource datasource_nodo;
        datasource_nodo = EsicraConf.getDataSource("esicra.datasource_nodo");
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        String query = "select distinct s.id_servizio, s.des_breve from ser_servizio s , pra_pratica_pag p  where s.id_servizio=p.id_servizio and s.tipo_export=1 order by s.des_breve";
        Statement stm = conn.createStatement();
        ResultSet rs =  stm.executeQuery(query);
        while (rs.next()){
           String opt= "<option value=\""+ rs.getBigDecimal("ID_SERVIZIO") +"\">"+rs.getString("DES_BREVE")+"</option>";
           list.add(opt);
        }
        rs.close();
        conn.close();
        array = new String[list.size()];
        array = (String[])list.toArray(array); 
    }catch(Exception e ){
      System.out.println(e);
    }
    return array;
  }

  public static String[] serviziPratiche(){
    String[] array = null;
    try{
        Vector list= new Vector();
        DataSource datasource_nodo;
        datasource_nodo = EsicraConf.getDataSource("esicra.datasource_nodo");
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        String query = "select distinct s.id_servizio,s.des_breve from ser_servizio s , pra_pratica_testa p  where s.id_servizio=p.id_servizio and s.tipo_export=2 order by s.des_breve";
        Statement stm = conn.createStatement();
        ResultSet rs =  stm.executeQuery(query);
        while (rs.next()){
           String opt= "<option value=\""+ rs.getBigDecimal("ID_SERVIZIO") +"\">"+rs.getString("DES_BREVE")+"</option>";
           list.add(opt);
        }
        rs.close();
        conn.close();
        array = new String[list.size()];
        array = (String[])list.toArray(array); 
    }catch(Exception e ){
      System.out.println(e);
    }
    return array;
  }

  public static String siglaPagamento(String id){
    String sigla = "SCONOSCIUTO";
    try{
        DataSource datasource_nodo;
        datasource_nodo = EsicraConf.getDataSource("esicra.datasource_nodo");
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        String query = "select SIGLA_EXPORT from SER_SERVIZIO where TIPO_EXPORT=1 and ID_SERVIZIO="+id;
        Statement stm = conn.createStatement();
        ResultSet rs =  stm.executeQuery(query);
        if(rs.next()==true){
            sigla = rs.getString(1);
        }
        rs.close();
        conn.close();
    }catch(Exception e ){
        System.out.println(e);
    }
    return sigla;
  }  

  public static String siglaPratica(String id){
    String sigla = "SCONOSCIUTO";
    try{
        DataSource datasource_nodo;
        datasource_nodo = EsicraConf.getDataSource("esicra.datasource_nodo");
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        String query = "select SIGLA_EXPORT from SER_SERVIZIO where TIPO_EXPORT=2 and ID_SERVIZIO="+id;
        Statement stm = conn.createStatement();
        ResultSet rs =  stm.executeQuery(query);
        if(rs.next()==true){
            sigla = rs.getString(1);
        }
        rs.close();
        conn.close();
    }catch(Exception e ){
        System.out.println(e);
    }
    return sigla;
  }  

  public static String maxDtExport(){
    String dtString="";
    Date data=null;
    try{
        DataSource datasource_nodo;
        datasource_nodo = EsicraConf.getDataSource("esicra.datasource_nodo");
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        String query = "select max(dt_mod) from ser_export";
        Statement stm = conn.createStatement();
        ResultSet rs =  stm.executeQuery(query);
        rs.next();
        data = rs.getDate(1);
        if(data!=null)
          dtString= data.toString();
        else
          data=new Date(System.currentTimeMillis());
        rs.close();
        conn.close();
    }catch(Exception e ){
        System.out.println(e);
        return "error";
    }
    return DATE_FORMAT.format(data);
  }

  public static String curDate(){
    Date data=new Date(System.currentTimeMillis());
    return DATE_FORMAT.format(data);
  }  
}

