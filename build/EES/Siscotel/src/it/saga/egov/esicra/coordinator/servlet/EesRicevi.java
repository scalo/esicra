package it.saga.egov.esicra.coordinator.servlet;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.coordinator.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.math.*;
import java.io.*;
import it.saga.egov.esicra.coordinator.servlet.EesSincronizza;

public class EesRicevi extends EesSincronizza implements SingleThreadModel {

  private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.sendError(response.SC_BAD_REQUEST,"Metodo GET non supportato");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.warn("-----------------------------------------------------------");
    // Memorizza le informazioni del chiamante per la notifica
    //String porta_esicra =(String) envMap.get("porta_esicra");
    String porta_esicra = System.getProperty("esicra.port");
    String uri = "http://"+request.getRemoteHost()+":"+porta_esicra+"/esicra/EesRicevi";
    logger.debug("Chiamante: "+uri);
    BigDecimal cod_transazione = null;
    BigDecimal id_ente_destinatario=null;
    int count=0;
    ResultSet rs=null;
    HashMap header=null;
    Connection conn=null;
    try {
      long start = System.currentTimeMillis();
      //conn = datasource_pat.getConnection();
      //conn.setAutoCommit(false);
      // RICEVE FLUSSO AGGIORNAMENTO DATI
      logger.debug("/Receive : Arrivata richiesta POST");
      File fileFlussoDati = riceviFile(request.getInputStream());
      //header = caricaFlusso(request.getInputStream(),conn);
      logger.debug("File ricevuto: "+fileFlussoDati.getAbsolutePath());
      long stop = System.currentTimeMillis(); 
      logger.debug("Tempo copia file "+calcHMS(stop-start)); start=stop;
      header = caricaFlusso(fileFlussoDati);
      cod_transazione=(BigDecimal)header.get("cod_transazione");
      id_ente_destinatario=(BigDecimal)header.get("id_ente");
      stop = System.currentTimeMillis();
      logger.debug("Tempo caricamento file "+calcHMS(stop-start)); start=stop;
      logger.debug("Flusso dati insert records#:="+header.get("records_insert"));
      logger.debug("Flusso dati update caricato records#:="+header.get("records_update"));
      logger.debug("cod_transazione = "+cod_transazione);
      logger.debug("id_ente_destinatario = "+id_ente_destinatario);
      EesTransazione trans =factoryTransazione.create();
      trans.setCodTransazione(cod_transazione);
      trans.setStato(EesTransazione.RICEVUTA);
      trans.setIdEnteDestinatario(id_ente_destinatario);
      trans.save();
      // Prepara flusso di aggiornamento pratiche
      header = preparaFlussoPratiche(cod_transazione ,id_ente_destinatario);
      File fileFlussoPratiche =(File) header.get("file");
      inviaFile(fileFlussoPratiche,response.getOutputStream());
      //header=esportaFlussoPratiche(cod_transazione,response.getOutputStream(),conn);
      logger.debug("Flusso pratiche esportato records#: "+header.get("records_esportati"));
      //conn.close();
    } catch(Exception e) { 
      logger.debug(e.toString());
      try{
        // prova lo stesso a rilasciare la connessione
        conn.close();
      }catch(SQLException ex){
        // nop
      }
    }
  }
}

