package it.saga.egov.esicra.coordinator;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.math.*;
import org.apache.log4j.*;
import javax.naming.Context;
import javax.naming.InitialContext;

// datasource e connection pool di apache (x locale)

import org.apache.commons.pool.*;
import org.apache.commons.pool.impl.*;


public class EesTransazione {

  private Logger logger;
  private Map envMap;
  
  // ATTRIBUTI
  private BigDecimal cod_transazione;
  private BigDecimal id_ente_destinatario;
  private Integer stato;
  private BigDecimal durata;
  private Timestamp dt_mod;
  // imposta un valore per risolvee il problema del null del driver jdbc Micro$oft
  private String nota="nessuna";
  
  // COSTANTI per gestire lo stato della transazione
  public static final Integer ERRORE = new Integer(-1) ;
  public static final Integer VUOTA = new Integer(0) ;
  public static final Integer INIZIATA = new Integer(1) ;
  public static final Integer RICEVUTA = new Integer(2) ;
  public static final Integer NOTIFICA = new Integer(3); // in attesa di notifica
  public static final Integer TERMINATA = new Integer(4);
  
  // COSTANTI per gestire errori semplici
  public static final int TRANS_OK=0;
  public static final int TRANS_NOT_FOUND=-1;
  public static final int TRANS_ERROR=-2;

  private DataSource dataSource=null;
  

  public EesTransazione(DataSource ds , Logger log) throws SQLException {
    // memorizza il datasource del factory
    dataSource=ds;
    logger=log;
    stato = EesTransazione.INIZIATA;
    long tms = System.currentTimeMillis();
    // aspetta 50 ms per sicurezza
    try{
      Thread.currentThread().sleep(50);
    }catch(InterruptedException e){
      // mah
    }
    setCodTransazione(new BigDecimal(tms));
    setDtMod(new Timestamp(tms));
  }

  public void setStato(Integer status){
    stato=status;
  }

  public Integer getStato(){
    return stato;
  }

  public void setDurata(BigDecimal val){
    durata=val;
  }

  public BigDecimal getDurata(){
    return durata;
  }

  public void setNota(String s){
    nota=s;
  }

  public String getNota(){
    return nota;
  }
  
  /**
   *  Restituisce il codice della transazione
   */
  public BigDecimal getCodTransazione(){
    return cod_transazione;
  }

  public void setCodTransazione(BigDecimal cod){
    cod_transazione=cod;
  }

  public Timestamp getDtMod(){
    return dt_mod;
  }

  public void setDtMod(Timestamp ts){
    dt_mod=ts;
  }

  public void setIdEnteDestinatario(BigDecimal id){
    id_ente_destinatario=id;
  }

  public BigDecimal getIdEnteDestinatario(){
    return id_ente_destinatario;
  }

  private boolean find(BigDecimal cod_transazione){
    boolean res=false; 
    Connection conn=null;
    String query="SELECT cod_transazione  FROM ser_transazione WHERE cod_transazione="+
      cod_transazione.toString(); 
    // recupera connessione dal datasource
    try{
      conn=dataSource.getConnection();
      conn.setAutoCommit(false);
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(query);
      if(rs.next()){
        res=true;
      }
      rs.close();
      stm.close();
      conn.close();
    }catch(SQLException se){
      logger.error(se);
      try{
        // prova lo stesso a rilasciare la connessione
        conn.close();
      }catch(SQLException e){
        // nop
      }
    }
    return res;
  }

  public boolean save(){
    boolean res=false;
    BigDecimal cod = getCodTransazione();
    boolean  found = find(cod);
    if(found){
      res = update();
    }else{
      res = insert();
    }
    return res;
  }
  
  private boolean insert(){
    logger.debug("insert  pre commit");
    boolean res=false;
    Connection conn=null;
    String query = "INSERT INTO ser_transazione (cod_transazione, stato , dt_mod , id_ente_destinatario , durata , nota) VALUES (?,?,?,?,?,?)";
    logger.debug("insert: "+query.toString());
    try{
      conn = dataSource.getConnection();
      PreparedStatement pstm = conn.prepareStatement(query);
      pstm.setObject(1,cod_transazione);
      pstm.setObject(2,EesTransazione.VUOTA);
      pstm.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
      pstm.setObject(4,id_ente_destinatario);
      long diff = System.currentTimeMillis()-getDtMod().getTime();
      pstm.setObject(5,new BigDecimal(diff));
      pstm.setObject(6,getNota());
      int n = pstm.executeUpdate();
      conn.commit();
      logger.debug("insert  post commit");
      pstm.close();
      conn.close();
      res=true;
    }catch(Exception se){
      //se.printStackTrace();
      logger.error(se);
      try{
        // prova lo stessoa rilasciare la connessione
        conn.close();
      }catch(SQLException e){
        // nop
      }
      res=false;
    }
    return res;
  }

  private boolean update(){
    logger.debug("update  pre commit");
    boolean res=false;
    Connection conn =null;
    String query= "UPDATE ser_transazione SET  stato=? , dt_mod=? , id_ente_destinatario=?, durata=?,  nota=?  WHERE cod_transazione=? ";  
    logger.debug(query.toString());
    try{
      conn = dataSource.getConnection();
      conn.setAutoCommit(false);
      PreparedStatement pstm = conn.prepareStatement(query);
      pstm.setObject(1,getStato());
      pstm.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
      pstm.setObject(3,getIdEnteDestinatario());
      long diff = System.currentTimeMillis()-getDtMod().getTime();
      pstm.setObject(4,new BigDecimal(diff));
      pstm.setObject(5,getNota());
      pstm.setObject(6,cod_transazione);
      int n = pstm.executeUpdate();
      conn.commit();
      logger.debug("update  post commit");
      pstm.close();
      conn.close();
      res = true;
    }catch(Exception se){
      //se.printStackTrace();
      logger.error(se);
      res= false;
      try{
        // prova lo stesso a rilasciare la connessione
        conn.close();
      }catch(SQLException e){
        // nop
      }
    }
    return res;
  }
  
  public int delete(){
    boolean found=false;
    Connection conn=null;
    StringBuffer query=new StringBuffer("DELETE FROM ser_transazione WHERE cod_transazione=");
    query.append(getCodTransazione().toString());
    logger.debug("delete:"+query.toString());
    try{
      conn=dataSource.getConnection();
      conn.setAutoCommit(false);
      Statement stm = conn.createStatement();
      int n = stm.executeUpdate(query.toString());
      conn.commit();
      if (n==0) return TRANS_NOT_FOUND;
      stm.close();
      conn.close();
    }catch(SQLException se){
      se.printStackTrace();
      logger.error(se);
      try{
        // prova lo stesso a rilasciare la connessione
        conn.close();
      }catch(SQLException e){
        // nop
      }
      return TRANS_ERROR;
    }
    return TRANS_OK;
  }
  
  public boolean load(BigDecimal cod){
    boolean res=false;
    Connection conn = null;
    StringBuffer query =new StringBuffer("SELECT * FROM ser_transazione WHERE cod_transazione=");
    query.append(cod.toString());
    logger.debug("transazione load: "+query.toString());
    try{
      conn = dataSource.getConnection();
      Statement stm=conn.createStatement();
      ResultSet rs = stm.executeQuery(query.toString());
      // sempre il tapullo oracle , UPPERCASE
      if(rs.next()){
        this.cod_transazione=(BigDecimal)rs.getObject("COD_TRANSAZIONE");
        // altro tapullo per Oracle , mappatura degli int è sbagliata 
        this.stato = new Integer(rs.getInt("STATO"));
        this.id_ente_destinatario =(BigDecimal)rs.getObject("ID_ENTE_DESTINATARIO");
        this.dt_mod =(Timestamp) rs.getTimestamp("DT_MOD");
        this.durata = (BigDecimal) rs.getObject("DURATA");
        this.nota = (String) rs.getObject("NOTA");
        res=true;
      }
      rs.close();
      stm.close();
      conn.close();
      
    }catch(SQLException se){
      logger.error(se);
      try{
        res=false;
        // prova lo stesso a rilasciare la connessione
        conn.close();
      }catch(SQLException e){
        // nop
      }
    }
    return res;
  }
  
  /**
   *  Rilascia le risorse quando chiamata dal garbage collector
   */
  protected void finalize() throws Throwable{
    //chiude  la connessione
    //conn.close();  
  }
  
  public static String decodifica(Integer val){
    if(val.equals(ERRORE))
      return "ERRORE";
    else if(val.equals(VUOTA))
      return "VUOTA";
    else if(val.equals(INIZIATA))
      return "INIZIATA";
    else if(val.equals(RICEVUTA))
      return "RICEVUTA";
    else if(val.equals(INIZIATA))
      return "NOTIFICA";
    else if(val.equals(TERMINATA))
      return "TERMINATA";
    return "SCONOSCIUTO";
  }
  
  public static void main(String[] args) {
    System.out.println(EesTransazione.decodifica(new Integer(-1)));
    System.out.println(EesTransazione.decodifica(new Integer(0)));
    System.out.println(EesTransazione.decodifica(new Integer(1)));
    System.out.println(EesTransazione.decodifica(new Integer(2)));
    System.out.println(EesTransazione.decodifica(new Integer(3)));
    System.out.println(EesTransazione.decodifica(new Integer(4)));
  }
  
}

