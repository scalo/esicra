package it.saga.egov.esicra.importazione;

import it.saga.egov.esicra.importazione.soggetto.*;
import it.saga.egov.esicra.importazione.oggetti.*;

/**
 *   Carica Dati di test Siscotel
 */
public class LoadAll  {
 
  public static void main(String[] args) {
    String arg[] = null;
    
    Soggetto.main(arg);
    SoggettoFisico.main(arg);
    SoggettoGiuridico.main(arg);
    Indirizzo.main(arg);
    AllineaProvenienza.main(arg);
    Aot.main(arg);
    AotIdentificativo.main(arg);
    AotIndirizzo.main(arg);
    AotProvenienza.main(arg);
  }
  
}