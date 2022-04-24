package it.saga.egov.esicra.coordinator.servlet;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.coordinator.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import javax.sql.*;
import java.io.*;
import java.math.*;
import it.saga.egov.esicra.coordinator.servlet.EesSincronizza;

/**
 *    Restituisce cod_transazione della notifica , in caso di errore restituisce
 *    codice "-1"
 *    
 *    @todo migliorare e tracciare in modo più specifico gli errori
 */
public class EesNotifica extends EesSincronizza implements SingleThreadModel {

  private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String cod ="";
    String nodo="";
    boolean transazioneOk=false;
    ObjectOutputStream oos =null;
    BigDecimal id_ente_destinatario=null;
    try {
      //datasource_pat = (DataSource)envMap.get("datasource_remote");
      /*
      datasource_ = EsicraConf.getDataSource("esicra.datasource_pat");
        if(datasource==null){
        logger.error("DataSource esicra.datasource_pat null");
        return;
      }
      */
      cod= request.getParameter("cod_transazione");
      nodo= request.getParameter("id_ente");
      id_ente_destinatario = new BigDecimal(nodo);
      logger.debug("param cod_transazione= "+cod);
      logger.debug("param id_ente_destinatario= "+id_ente_destinatario);
      oos = new ObjectOutputStream(response.getOutputStream());
      // controlla che il codice transazione sia un numero valido
      // altrimenti lancia eccezione che rest cod di errore
      //int num = Integer.parseInt(cod_transazione);
      BigDecimal cod_transazione = new BigDecimal(cod);
      response.setContentType(CONTENT_TYPE);
      //logger.debug("pre "+trans.getCodTransazione());
      //** Registra Transazione
      EesTransazione trans = factoryTransazione.find(cod_transazione);
      //int res= trans.load(new BigDecimal(cod_transazione));
      if (trans!=null){
        trans.setStato(EesTransazione.TERMINATA);
        trans.save();
        transazioneOk=true;
      }
      //logger.debug("post "+trans.getCodTransazione());
      //** Rimuovi dal db  i records cancellati logicamente
      pulisciTabellePratiche(id_ente_destinatario);
      pulisciTabelleDati();
      logger.info("[Transazione "+ cod_transazione + "] Completata" );
      if(transazioneOk){
        //pulisciTabelle(conn);    
        oos.writeObject(cod_transazione.toString());    
      }else{       
        oos.writeObject("-1");
      }
      oos.close();
      //** restituisce il codice della transazione
    }catch(Exception e) {
      transazioneOk=false;
      logger.error(e);
      if (oos!=null){
        oos.close();
        oos.writeObject("-2");
      }
      logger.error("Errore Notifica: "+e);
      logger.error("Cod = "+cod );
      logger.error("Nodo ="+nodo);
    }
  }
  
}

