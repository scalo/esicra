package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import org.apache.log4j.*;
import java.util.*;
import java.text.*;
import java.io.Serializable;

/**
 *   Classe astratta rappresentante un evento generico gestito dal Timer.
 *   Implementa le interfacce java 
 *   Runnable  in quanto istanzia un thread per eseguire il job
 *   Serializable  per essere serializzata su file o altro.
 *   Il logger (attributo statico che non viene serializzato) viene assegnato dal
 *   costruttore, la deserializzazione non reimposta il logger.
 *   Occorre un metodo statico che venga richiamato durante la deserializzazione.
 *   
 *   @author OS
 *   @version 1.0
 */
abstract public class EesTask implements Runnable,  Serializable {

  /**
   *   Blocco di esecuzione del task
   */
  abstract public void process();

  /**
   *    Metodo di pre da usare in overridde con process
   */
  public void pre(){};

  /**
   *    Metodo di post
   */
  public void post(){};
  
  /**
   *   Nome del task 
   *   
   *   Restituisce il nome della classe in automatico
   */
  public String  nome(){
    
    Class classe = this.getClass();
    String fullname = classe.getName();
    String pac = classe.getPackage().getName();
    //System.out.println("PACKAGE: "+pac);
    //System.out.println("FULLNAME: "+fullname);
    String nome = fullname.substring(pac.length()+1,fullname.length());
    //System.out.println("NOME: "+nome);
    return nome;
  }
  
  /**
   *  Lo stato si un evento può essere RUN , WAIT e STOP .
   *  NULL è il valore predefinito e identifica un evento appena registrato
   *  END , stato assunto quando un task senza frequenza termina l'esecuzione
   */
  public static final int NULL = 0 ;
  public static final int RUN  = 1 ;
  public static final int WAIT = 2 ;
  public static final int STOP = 3 ;
  public static final int END = 4 ;

  /**
   *  array per la decodifica degli stati
   */
  static final String[] DES_STATO ={"stato nullo","in esecuzione","attesa di esecuzione","fermo","concluso"};

  /**
   *  array di decodifica per la settimana
   */
  static final String[] DES_SETTIMANA ={"LU","MA","ME","GI","VE","SA","DO"};
  
  /**
   *  simple date format per conversioni
   */
  //private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
  public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
 
  /**
   *  Logger utilizzato ricevuto dal timer.
   *  E' statico perchè non viene serializzato
   */
  protected static Logger logger=null;
  
  /**
   *  Data di inizio espressa in millisecondi
   */
  private long inizio ;

  /**
   *  Frequenza di esecuzione di un evento periodico espressa in millisecondi.
   *  frequenza 0 significa che l'evento deve verificarsi solo una volta.
   */
  private long frequenza ;

  /**
   *  Insieme dei giorni della settimana in cui deve essere eseguito il task
   *  se è null non viene considerato nella logica.
   */
  private boolean[] giorniSettimana = null;

  /**
   *  Data di ultima esecuzione dell'evento espressa in millisecondi.
   *  Quando il thread non è stato ancora eseguito viene settata a 0
   */
  private long ultimaEsecuzione=0;
  
  /**
   *  Lo stato  viene inizializzato ad uno stato  non valido.
   *  ( [...] viene o non viene serializzato ??)
   */
  private int stato = NULL;

  /**
   *  Identificativo dell'evento.
   *  Utilizzato per lo scopo il solito timestamp espresso in ms.
   *  E' utilizzato un Long ,perchè l'attributo funge da chiave per la map dei task
   */
   protected Long id = null;

  /**
   *  Descrizione del task (facoltativa)
   */
   private String descrizione="";

   /**
    *   Parametro data_inizio (es esportazione)
    */
   private long data_inizio=0; 

   /**
    *   Parametro data fine
    */ 
   private long data_fine= 0;

   /**
    *   Parametro id servizio
    */ 
   private long id_servizio= 0;

   /**
    *   Parametro sigla file export
    */ 
   private String siglaExport ="VOID";
  
  /**
   *  Costruttore dell' oggetto evento. 
   */
  public EesTask(long dataInizio, long frequenza, boolean[] settimana ){
    this();
    this.inizio = dataInizio;
    this.frequenza = frequenza;
    this.giorniSettimana = settimana;
  }
    
  /**
   *  Costruttore privo di argomenti necessario per usare la reflection o il
   *  mecanismo corrente del timer per istanziare i task specifici.
   */
  public EesTask(){
    // assegna id univoco all'evento
    id = new Long(System.currentTimeMillis());
    // Aspetta 50 millisecondi !!! altrimenti si ha lo stesso timestamp su
    // 2 chiamate successive. Grazie Micro$oft 
    try{
      Thread.currentThread().sleep(50);
    }catch(Exception e){
      // nop
    }
    // recupera logger
    logger=EsicraConf.getLogger(this.getClass());
  }

  public static void setLogger(Logger pLogger){
    logger = pLogger;
  }
  
  /**
   *  Inserisce descrizione del task (facoltativa)
   *  @param desc Stringa descrizione del task
   */
  public void setDescrizione(String desc){
    this.descrizione=desc;
  }

  /**
   *  Restituisce la descrizione del task
   *  @return stringa contenente la descrizione
   */
  public String getDescrizione(){
    return this.descrizione;
  }
  
  /**
   *  Restituisce l'identificativo dell' istanza evento
   *  @return id evento
   */
  public Long getId(){
    return id;
  }

  /**
   *  Imposta l'identificativo dell' istanza evento
   *  @param id long
   */
  public void setId(Long id){
    this.id=id;
  }
  
  /**
   *  Restituisce lo stato corrente dell'evento
   *  @return stato dell'evento.
   */
  public int getStato(){
    return stato;
  }

  /**
   *  Restituisce lo stato corrente dell'evento come destrizione
   *  @return  stringa che descrive lo stato evento.
   */
  public String getFormatStato(){
    return DES_STATO[getStato()];
  }


  /**
   *  Imposta lo stato dell'evento
   *  @param status , nuovo stato associato all'evento.
   */
  synchronized public void setStato(int status){
    this.stato=status;
  }
  
  /**
   *  Restituisce la data di inizio 
   *  @return timestamp in millisecondi
   */
  public long getDtInizio(){
    return inizio;
  }

  /**
   *  Imposta la data di inizio dell'evento
   *  @param ms , data di inizio espressa in milisecondi
   */
  public void setDtInizio(long ms){
    this.inizio=ms;
  }
  
  /**
   *  Restituisce la data di inizio in formato stringa
   *  @return stringa formattata
   */
  public String getFormatDtInizio(){
    return sdf.format(new Date(getDtInizio()));
  }

  /**
   *  Imposta la frequenza dell'evento
   *  @param ms , frequenza espressa in millisecondi
   */
  public void setFrequenza(long ms){
    this.frequenza=ms;
  }
  
  /**
   *  Restituisce la frequenza dell'evento
   *  @return frequenza , come timestamp in ms
   */
  public long getFrequenza(){
    return frequenza;
  }

    /**
     *  Restituisce la frequenza dell'evento in formato stringa
     *  @return stringa formattata
     */
    public String getFormatFrequenza() {
        String str = "";
        long ms = getFrequenza();
        if (ms == 0) {
            return "";
        }
        long g;
        long h;
        long m;
        g = h = m = 0;
        long mTot = ms / (1000 * 60); //minuti totali = giorni+ ore+ minuti    
        long hTot = mTot / 60; //ore totali    = giorni+ ore+ minuti    
        long minuti = mTot - (hTot * 60); //minuti
        long gTot = hTot / 24; //giorni 
        if (gTot > 0) {
            g = gTot;
            h = hTot - (gTot * 24);
        } else {
            h = hTot;
        }
        if (g > 0) {
            str += g+" giorni ";
        }
        if (h > 0) {
            str += h+" ore ";
        }
        if (minuti > 0) {
            str += minuti+" minuti ";
        }
        return str;
    }
  
  /**
   *  Restituisce la data di ultima esecuzione dell'evento
   *  @return ultimaEsecuzione , come timestamp in ms
   */
  public long getUltimaEsecuzione(){
    return ultimaEsecuzione;
  }

  /**
   *  Restituisce la data di ultima esecuzione dell'evento
   *  formattata come stringa.
   *  @return ultimaEsecuzione , stringa formattata
   */
  public String getFormatUltimaEsecuzione(){
    if (getUltimaEsecuzione()==0) return "";
    return sdf.format(new Date(getUltimaEsecuzione()));
  }

  /**
   *  Imposta la data di ultima esecuzione con il timestamp corrente
   */
  public void setUltimaEsecuzione(){
    ultimaEsecuzione=System.currentTimeMillis();
  }

  /**
   *  Imposta la data di ultima esecuzione con il timestamp specificato
   */  
  public void setUltimaEsecuzione(long timestamp){
    ultimaEsecuzione=timestamp;
  }
  
  /**
   *    Restituisce la frequenza settimanale dell schedulazione  del task.
   *    L'array di boolean rappresenta i giorni della settimana
   *    0-6 con  0->lunedì , 6-> domenica
   *    @return array di boolean , oppure null se la scadenza settimanale non
   *      è impostata.
   */
  public boolean[] getGiorniSettimana(){
    return giorniSettimana;
  }

  public String getFormatSettimana(){
    if (giorniSettimana==null) return "tutti";
    else return settimanaToString(giorniSettimana);
  }
  
  /**
   *  Imposta la validità di schededulazione del task durante la 
   *  settimana.
   *  @param giorni , array di boolean che rappresenta i giorni della settimana
   */
  public void setGiorniSettimana(boolean[] giorni){
    this.giorniSettimana=giorni;
  }

  /**
   *  Imposta il parametro di data inizio
   *  @param ms , frequenza espressa in millisecondi
   */
  public void setDataInizio(long ms){
    this.data_inizio=ms;
  }
  
  /**
   *  Restituisce il parametro di data inizio
   *  @return data_inizio , come timestamp in ms
   */
  public long getDataInizio(){
    return data_inizio;
  }  

  /**
   *  Imposta il parametro di data fine
   *  @param ms , frequenza espressa in millisecondi
   */
  public void setDataFine(long ms){
    this.data_fine=ms;
  }
  
  /**
   *  Restituisce il parametro di data fine
   *  @return data_inizio , come timestamp in ms
   */
  public long getDataFine(){
    return data_fine;
  }    

  /**
   *  Imposta il parametro di ID_SERVIZIO
   *  @param  id_servizio di export
   */
  public void setIdServizio(long id){
    this.id_servizio=id;
  }
  
  /**
   *  Restituisce il parametro di ID_SERVIZIO
   *  @return id_servizio di export
   */
  public long getIdServizio(){
    return id_servizio;
  } 

 /**
   *  Imposta il parametro SIGLA
   *  @param sigla del file di export
   */
  public void setSiglaExport(String sigla){
    this.siglaExport=sigla;
  }
  
  /**
   *  Restituisce il parametro SIGLA
   *  @return sigla del file di export
   */
  public String getSiglaExport(){
    return this.siglaExport;
  } 
  

  /**
    *  Indica se il giorno è valido per la schedulazione impostata
    *  
    *  Viene fatto schiftare il giorno restituito dal Calendar
    *   in Calendar il giorno della settimana 
    *    1-dom 7 sab
    *   normalmente in italia
    *    0-lun 6 dom
    *   Se la struttura non è definita (null) , viene accettato ogni giorno; 
    *   
    **/
  public boolean giornoValido(){
    boolean res=false; 
    int calDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    int curGioSett =(calDay+5)%7;
    boolean array[] = getGiorniSettimana();
    if(array!=null){
      res = array[curGioSett];
    }else
      return true;
    return res;
  }
  
  /**
   *    Metodo standard dell'interfaccia Runnable , definito per rendere simile 
   *    la classe EesTask all classe EesThread.
   *    Prima e  dopo l'esecuzione del blocco , imposta nell'istanza lo stato
   *    corretto , RUN all'inizio e WAIT al termine .
   *    
   */
  
  public void run(){
    setStato(EesTask.RUN);  
    process();
    setStato(EesTask.WAIT);
  }

  public void start(){
    Thread runningThread= new Thread(this);
    /* [..] vedere se è meglio un thread daemon o no , forse è meglio no
    // imposta il thread di tipo daemon, se esistono solo thread daemon la VM termina.
    runningThread.setDaemon(true);
    */
    runningThread.start();
  }

  /**
   *  Metodo che restituisce le informazioni del task sotto forma di stringa
   */
   public String toString(){
     StringBuffer sb = new StringBuffer("[ ");
     sb.append("id="+getId()+" ,");
     sb.append("ini="+millisToString(inizio)+" ,");
     sb.append("freq="+frequenza/1000+" sec ,");
     sb.append(ultimaEsecuzione==0?"0 ,":"ult="+ millisToString(ultimaEsecuzione)+" ,"); 
     sb.append(giorniSettimana==null?"":settimanaToString(giorniSettimana)+" ,");
     sb.append("<"+DES_STATO[stato]+">");
     sb.append(" ]");
     return sb.toString();
   }

  /**
   *  Metodo che restituisce le informazioni del task sotto forma di stringa
   */
   public String toStringRaw(){
     StringBuffer sb = new StringBuffer("[ ");
     sb.append(getDtInizio()+" ,");
     sb.append(getFrequenza()+" ,");
     sb.append(getUltimaEsecuzione()+" ,"); 
     sb.append(settimanaToString(getGiorniSettimana())+" ,");
     sb.append("<"+DES_STATO[getStato()]+">");
     sb.append(" ]");
     return sb.toString();
   }
  
  /**
   *  Metodi di utilità impiegato da toString()
   */
  private static String millisToString(long millis){
    return sdf.format(new Date(millis));
  }

  private static String settimanaToString(boolean sett[]){
    StringBuffer sb = new StringBuffer("[");
    for(int i=0;i<7;i++){
      if (sett[i]) sb.append(DES_SETTIMANA[i]+" ");
    }
    sb.append("]");
    return sb.toString();
  }

  public static boolean[] String2Array(String str){
      if (str==null||str.length()==0) return null;
      boolean[] array= new boolean[str.length()];
      for(int i=0;i<str.length();i++){
          if(str.charAt(i)=='1')
            array[i]=true ;
          else 
          array[i]=false;
      } 
      return array;
  }

  public static String Array2String(boolean[] array){
    StringBuffer sb = new StringBuffer();
    for(int i=0;i<array.length;i++){
        if(array[i]==true)
            sb.append("1");
        else
            sb.append("0");
    }
    return sb.toString();
  }

  public static void main(String[] args) throws Exception {
        int calDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        //int calDay = 1;
        System.out.println(Calendar.getInstance().getTime());
        int curGioSett =(calDay+5)%7;
        System.out.println("Calendar ="+calDay+"  "+EesTask.DES_SETTIMANA[calDay]);
        System.out.println("Cur ="+curGioSett+"  "+EesTask.DES_SETTIMANA[curGioSett]);
  }
  
}

