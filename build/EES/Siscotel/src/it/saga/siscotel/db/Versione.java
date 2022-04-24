package it.saga.siscotel.db;


/**
 *    Classe contenente la versione del database per i files di configurazione di
 *    hibernate
 */
public class Versione  {

  private static String versione="1.7.2";
  
  static public String getVersione(){
    return versione;
  }
}
