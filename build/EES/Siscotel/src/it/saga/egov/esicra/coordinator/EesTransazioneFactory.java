package it.saga.egov.esicra.coordinator;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.math.*;
import org.apache.log4j.*;
import javax.naming.Context;
import javax.naming.InitialContext;
// datasource e connection pool di apache (x locale)
import org.apache.commons.dbcp.*;
import org.apache.commons.pool.*;
import org.apache.commons.pool.impl.*;
import it.saga.egov.esicra.*;

public class EesTransazioneFactory  {

  private Logger logger;
  private Map envMap;
  private DataSource ds ;

  /*  RIMOSSO
   *  Configura factory , datasource + variabili d'ambiente
   
  public EesTransazioneFactory(){
    EsicraConf.configura();
    logger = EsicraConf.configuraLogger(this.getClass());  
    if(logger==null){
      System.out.println("Errore preparazione logger");
    }
    /*
    envMap = EesCoordinatorConf.configuraRisorse();
    
    if(envMap==null){
      logger.error("Errore configurazione risorse");
    }
   //id_ente = (String) envMap.get("id_ente");
   ds = (DataSource)envMap.get("datasource_locale");
    
  }
  */

  public EesTransazioneFactory(DataSource ds)  {
      EsicraConf.configura();
      logger = EsicraConf.configuraLogger(this.getClass());  
      if(logger==null){
        System.out.println("Errore preparazione logger");
      }
      this.ds=ds;
  }

  public EesTransazione create() throws SQLException{
    EesTransazione trans= new EesTransazione(ds,logger);
    // trans.setCodTransazione(new BigDecimal(System.currentTimeMillis()));
    return trans;
  }
  
  public EesTransazione find(BigDecimal cod_transazione) throws SQLException{
    EesTransazione trans= new EesTransazione(ds,logger);
    logger.debug("pre");
    boolean found=trans.load(cod_transazione);
    logger.debug("post");
    if(found) return trans;
    else
      return null;
  }

  /*
   *  Main di test 
   */
  public static void main(String[] args){
    // per il test , imposta contesto jndi di test
    // normalmente viene recuperato dal servlet container
    String testUri = "jdbc:postgresql://srvrsnod:5432/esicra_svil";
    try{
      String id_ente="19097";
      Hashtable env = new Hashtable();
      // per test si usa factory di apache piuttosto che quello j2ee
      System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
      System.setProperty("jdbc.drivers","org.postgresql.Driver");
      Context initialContext = new InitialContext();
      // CREA CONTESTO AMBIENTE COME NEL SERVLET ENGINE
      Context ctx =(Context) initialContext.createSubcontext("java:lang").createSubcontext("env");
      Context jdbc = ctx.createSubcontext("jdbc");
      ObjectPool connectionPool = new GenericObjectPool(null);
      ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(testUri,"postgres","postgres");
      PoolableConnectionFactory pcf = new PoolableConnectionFactory(connectionFactory,
        connectionPool,null,null,false,true);
      DataSource ds = new PoolingDataSource(connectionPool);
    
      ctx.bind("id_ente_destinatario",id_ente);
      ctx.bind("porta_esicra","8988");
      ctx.bind("jdbc/Srvrsnod_esicra_svilDS",ds);
      String res = (String)ctx.lookup("id_ente_destinatario");
      BigDecimal id_ente_destinatario = new BigDecimal(res);
      System.out.println("ID_ENTE-->"+ res);
      System.out.println("PORTA_ESICRA-->"+ (String)ctx.lookup("porta_esicra"));
      System.out.println("PORTA_ESICRA-->"+ (DataSource)ctx.lookup("jdbc/Srvrsnod_esicra_svilDS"));

      EesTransazioneFactory factory = new EesTransazioneFactory(ds);      
      EesTransazione trans = factory.create();
      trans.setStato(EesTransazione.INIZIATA);
      trans.setIdEnteDestinatario(new BigDecimal("10077"));
      BigDecimal cod = trans.getCodTransazione();
      trans.save();
      System.out.println("COD="+cod);
      EesTransazione trans2 = factory.find(cod);
      if(trans2!=null){
        trans2.setStato(EesTransazione.RICEVUTA);
        trans2.setNota("transazione di test");
        trans2.save();
      }else
        System.out.println("transazione non trovata "+cod);
    }catch(Exception ne){
      ne.printStackTrace();
    }
  }    
}

