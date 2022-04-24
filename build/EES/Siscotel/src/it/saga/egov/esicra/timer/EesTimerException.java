package it.saga.egov.esicra.timer;

/**
 *  Exception creata per gestire gli errori del timer
 */
public class EesTimerException extends Exception {
  
  public EesTimerException(String motivo) {
    super("Timer Exception: "+ motivo);
  }

}