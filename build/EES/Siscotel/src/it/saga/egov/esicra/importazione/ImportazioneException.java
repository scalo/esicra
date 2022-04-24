package it.saga.egov.esicra.importazione;


public class ImportazioneException extends Exception  {

    public ImportazioneException() {
        new ImportazioneException("Errore di importazione");
    }

    public ImportazioneException(String message) {
        super(message);
    }
    
}