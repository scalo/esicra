// CLASSE DA RIMUOVERE messo tutto in EsicraConf

package it.saga.egov.esicra.coordinator;

import org.apache.log4j.*;
import javax.naming.*;
import java.net.URL;
import java.util.*;
import javax.sql.DataSource;

public class EesCoordinatorConf  {

  public EesCoordinatorConf() {

  }
  
  /*
   *  Configura logger
   *  
   
  public static Logger configuraLogger(Class classe){
    // configura logger
    Logger logger = null;
    try{
      logger=Logger.getLogger(classe);
      // file di properties si trova nel package della classe ed e' relativo alla classe
      String resource ="log4j.properties";
      URL configFileResource =
            classe.getResource(resource);      
      BasicConfigurator.resetConfiguration();
      PropertyConfigurator.configure(configFileResource);   
    }catch(Exception e){
      // logger.error(e.getMessage());
    }
    return logger;
  }
  */
  
  /*
   *    Configura risorse jndi come datasource e variabili di ambiente inerenti
   *    al contesto dell'applicazione web
   *    
   *    @return Map contenente variabili d'ambiente
   *      datasource ---> datasource
   *      id_ente    ---> id_ente
   *      porta      ---> porta del servlet engine (esicra)
   *      
   *      NB
   *      
   *      Vedere se conviene usare jndi o file di properties
   *      
   
  public static Map configuraRisorse(){
    HashMap map = new HashMap();
    try{
      Context ctx = new InitialContext();
      Context envCtx=(Context)ctx.lookup("java:comp/env");
      String id_ente = (String) envCtx.lookup("id_ente");
      String porta_esicra = (String) envCtx.lookup("porta_esicra");
      //logger.debug("id_ente-->"+id_ente);
      // nome jndi del Datasource jdbc
      DataSource ds_local = (DataSource) envCtx.lookup("jdbc/Esicra_svilDS");
      DataSource ds_remote = (DataSource) envCtx.lookup("jdbc/Esicram_msql2000DS");
      //logger.debug("ds --> "+ds);
      map.put("datasource_locale",ds_local);
      // nella configurazione non di test è lo stesso datasource
      map.put("datasource_remote",ds_remote);
      map.put("id_ente",id_ente);
      map.put("porta_esicra",porta_esicra);
    }catch(Exception e){
      //logger.error("Errore creazione DataSource "+ e.toString());
      return null;
    }
    return map;
  }
  
  
  public static String codiceTransazione(String id_ente){
    return id_ente+System.currentTimeMillis();
  }
   
  
  public static void main(String[] args) {
    // EesCoordinatorConf eesCoordinatorConf = new EesCoordinatorConf();
  }
  */
}
