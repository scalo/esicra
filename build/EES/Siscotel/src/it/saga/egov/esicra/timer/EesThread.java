package it.saga.egov.esicra.timer;


/**   Classe Thread di Esicra , implementa l'interfaccia Runnable ,
  *   in modo da poter ridefinire il metodo stop(),
  *   cosa che non può essere fatta con la classe Thread.
  *   Ciò è necessario poichè il metodo stop() della classe thread può causare problemi,
  *   lasciando il thread in uno stato incostistente e danneggiando gli altri.
  *   Per dettagli consultare il java tutorial e le java api 
  *  
  *  @author OS
  *  @version 1.0
 */
 
public abstract class EesThread implements Runnable{

  protected static final long  INTERVAL = 60000 ; // 60 sec
  
  private  volatile Thread runningThread ;

  /**
   *  Intervallo di attesa del thread tra ogni ciclo di esecuzione e il
   *  successivo. Di default settato a 60 sec ma configurabile come parametro
   *  da passare al costruttore.
   */
  protected long interval = INTERVAL;

  /**
   *  Identificativo dell'EesThread
   */
  private Long id;
  
  public EesThread(){
    id = new Long(System.currentTimeMillis());
    // Aspetta un millisecondo !!! altrimenti si ha lo stesso timestamp su
    // 2 chiamate successive.
    try{
      Thread.currentThread().sleep(50);
    }catch(Exception e){
      // nop
    }
  }

  public EesThread(long ms){
    this();
    interval=ms;
  }
    
  public void start(){
    if(runningThread==null){
      runningThread= new Thread(this);
      // imposta il thread di tipo daemon, se esistono solo thread daemon la
      // VM termina.
      runningThread.setDaemon(true);
      runningThread.start();
    }
  }
  /**
   *  Il metodo stop interrompe il thread "gentilmente"
   *  La tecnica utilizzata consiste nel rimuovere il reference al thread corrente.
   */
  public void stop(){
    runningThread=null;
  }
  
  /**
   *  Override del metodo run() per rendere il Thread interrompibile e stoppabile
   *  Il metodo viene ripetuto in loop fino al termine che avviene nel momento in cui la
   *  variabile volatile runningThread diventa null
   */
  public void run(){
    Thread thisThread = Thread.currentThread();
    while(runningThread == thisThread){
      try{
        process();
        // manda a dormire il thread per l'intervallo di tempo definito
        thisThread.sleep(interval); 
      }catch(InterruptedException e){
      }
    }
  }

  /**
   *  Restituisce l'identificativo del thread
   *  @return  id del thread , espresso come timestamp in ms
   */
  public Long getId(){
    return id;
  }
  
  /**
   *  Blocco  di esecuzione del thread 
   *  @return valore che identifica se il thread debba terminare o
   *          continuare ad eseguire il blocco ciclicamente.
   */
  protected abstract void process();

  
}