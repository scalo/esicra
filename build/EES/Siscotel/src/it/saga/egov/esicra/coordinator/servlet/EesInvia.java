package it.saga.egov.esicra.coordinator.servlet;

import it.saga.egov.esicra.coordinator.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.math.*;
import it.saga.egov.esicra.coordinator.servlet.EesSincronizza;

/**
 *  Servlet utilizzata dal nodo per realizzare la sincronizzazione con il PAT
 * 
 */
public class EesInvia extends EesSincronizza implements SingleThreadModel  {

  private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    ResultSet rs = null;
    String id_ente="";
    PrintWriter out=null;
    ArrayList row=new ArrayList();
    ArrayList lista=new ArrayList();
    ArrayList tab= new ArrayList();
    HashMap headerExport = null;
    HashMap headerImport = null;
    BigDecimal cod_transazione=null;
    BigDecimal id_ente_nodo=null;
    Connection conn = null;
    //long start = System.currentTimeMillis();
    int records=0;
    try {
      // parametri servlet
      String uri = request.getParameter("uri");
      if(uri==null){
        logger.error("Uri nodo non valido");
        return;
      }
      // parametro id_ente_pat
      String pat = request.getParameter("id_ente");
      try{
        id_ente_nodo = new BigDecimal(pat);
      }catch(NumberFormatException ne){
        id_ente_nodo=null;
        logger.error("Ente destinatario formato non valido");
      }
      if(id_ente_nodo==null){
        logger.error("Ente destinatario non inserito");
        return;
        // [...] gestione errori
      }
      String pCompleto = request.getParameter("completo");
      boolean completo=false;
      if(pCompleto!=null&&pCompleto.equals("true"))
        completo = true;
      logger.debug("ID_ENTE_DESTINATARIO: "+id_ente_nodo);
      logger.debug("URI NODO: "+uri);
      id_ente = System.getProperty("esicra.id_ente");
      
      conn = datasource_nodo.getConnection();
      conn.setAutoCommit(false);
      logger.debug("conn-->"+conn);
      logger.debug("URL-->"+uri);
      URL urlRicevi = new URL(uri);
      URLConnection uc =(URLConnection)urlRicevi.openConnection();
      uc.setDoOutput(true);
      uc.setUseCaches(false);
      uc.setDoInput(true);
      // CREA TRANSAZIONE
      EesTransazione trans = factoryTransazione.create();
      cod_transazione = trans.getCodTransazione();
      logger.debug("Transazione: "+cod_transazione);
      trans.setStato(EesTransazione.INIZIATA);
      trans.setIdEnteDestinatario(id_ente_nodo);
      trans.save();
      // * EsportaFlussoDati *
      // headerExport =esportaFlussoDati(cod_transazione ,uc.getOutputStream(),conn,completo);
      long start = System.currentTimeMillis();
      headerExport = preparaFlussoDati(cod_transazione,completo);
      long stop = System.currentTimeMillis();
      logger.debug("Tempo preparazione file "+calcHMS(stop-start)); start=stop;
      File fileDati = (File) headerExport.get("file");
      logger.debug("File dati#: "+fileDati);
      OutputStream stream = uc.getOutputStream();
      //logger.debug("Stream: "+stream.toString());
      inviaFile(fileDati , stream );
      stop = System.currentTimeMillis();
      logger.debug("Tempo invio file "+calcHMS(stop-start));
      String ses = (String) headerExport.get("records_esportati");
      logger.debug("Flusso Dati inviato, records#: "+ses);
      // * CaricaFlussoPratiche *
      File filePratiche=riceviFile(uc.getInputStream());
      headerImport = caricaFlusso(filePratiche);
      logger.debug("Flusso insert records#: "+headerImport.get("records_insert"));
      logger.debug("Flusso update records#: "+headerImport.get("records_update"));
      // Invia Notifica di avvenuta transazione
      trans.setStato(EesTransazione.NOTIFICA);
      trans.save();
      // prepara url per la notifica
      String nomeApplicazione = System.getProperty("esicra.context");
      if(nomeApplicazione==null) nomeApplicazione="esicra";
      String dest = urlRicevi.getProtocol()+"://"+urlRicevi.getHost()+
        ":"+urlRicevi.getPort()+"/"+nomeApplicazione+"/EesNotifica";
      dest+="?cod_transazione="+cod_transazione;
      dest+="&id_ente="+id_ente_nodo;
      URL urlNotifica = new URL(dest);
      logger.debug("Url notifica: "+urlNotifica);
      URLConnection ucn =(URLConnection)urlNotifica.openConnection();
      ucn.setDoOutput(true);
      ucn.setUseCaches(false); 
      ucn.setDoInput(true);
      ObjectInputStream ois= new ObjectInputStream(ucn.getInputStream());
      String res = (String) ois.readObject();
      logger.debug("Risposta notifica = "+res);
      BigDecimal bd = new BigDecimal(res);
      // NOTIFICA completata
      response.setContentType(CONTENT_TYPE);
      out = response.getWriter();
      if(cod_transazione.equals(bd)){
        // cancella fisicamente records segnati come cancellati (dt_mod = null)
        pulisciTabellePratiche(id_ente_nodo);
        pulisciTabelleDati();
        logger.debug("cancellati records con dt_mod = null");
        trans.setStato(EesTransazione.TERMINATA);
        trans.save();
      }else{
        trans.setStato(EesTransazione.ERRORE);
        trans.save();
        logger.error("Errore nella transazione: "+cod_transazione);
        out.println("Errore Transazione: "+cod_transazione);
        //<--- DEVE RESTITUIRE CODICE DI ERRORE  ma come ?
        out.close();
        return;
      }
      // risultato della sincronizzazione restituito al chiamante 
      // sotto forma di flusso txt sostiuito da log
      /*
      long stop = System.currentTimeMillis();
      
      out.println("Sincronizzazione su  "+uri );
      out.println("Transazione ="+cod_transazione);
      out.println("Esportazione: "+ headerExport);
      out.println("Importazione pratiche: "+ headerImport);  
      out.println("Tempo di elaborazione: "+ calcHMS(stop-start));
      */
      out.close();
      conn.close();
      logger.info("[Transazione "+ cod_transazione + "] Completata" );
    }catch(Exception e) {
        logger.debug("ERRORE");
        logger.debug(e.getMessage());
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

