package it.saga.egov.esicra.timer.test;

import java.util.Calendar;

/**
 *  Classe di test per utilizzare Calendar, i metodi di estrazione dei singoli campi
 *  dell'oggetto java.sql.Date (es: getDay() etc..) sono deprecati.
 *  Occorre usare java.util.Calendar
 *  Necassario per realizzare le correzioni  necessarie per adattare le costanti
 *  intere al formato cron.
 *  
 *  1) Minuto  (0-59)
 *  2) Ora (0-23)
 *  3) Giorno del Mese (1-31)
 *  4) Mese dell'anno  (1-12)
 *  5) Giorno della settimana (0-6  0 domenica)
 *  
 */
 
public class CalendarTest  {

  public static void main(String[] args) {
    Calendar cur = Calendar.getInstance();
    int minuti = cur.get(Calendar.MINUTE);
    int ora = cur.get(Calendar.HOUR_OF_DAY);
    int giornoMese = cur.get(Calendar.DAY_OF_MONTH);
    int mese = cur.get(Calendar.MONTH)+1;
    int giornoSettimana = cur.get(Calendar.DAY_OF_WEEK)-1;

    System.out.println(
      "minuti: "+minuti+"\n"+
      "ora: "+ora+"\n"+
      "giorno del mese: "+giornoMese+"\n"+
      "mese: "+mese+"\n"+
      "giorno settimana: "+(giornoSettimana)+"\n");

    System.out.println("gennaio:"+Calendar.JANUARY);
    System.out.println("dicembre:"+Calendar.DECEMBER);
    System.out.println("domenica:"+Calendar.SUNDAY);
    System.out.println("sabato:"+Calendar.SATURDAY);
  }
  
}