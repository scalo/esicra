package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.*;
import org.apache.log4j.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.xml.sax.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.xml.parsers.*;

/**
 *    Oggetto Timer di esicra, è un thread che viene lanciato in background e gestisce la
 *    schedulazione di task per l'importazione , esportazione e la sincronizzazione.
 *    L'interfaccia prevede metodi per l'inserimento , sospensione , cancellazione di task.
 *
 *    @author OS
 *    @version 2.0
 */
public class EesTimer extends EesThread {
    // nome di default della dir  
    static final String TIMER_DIR_NAME = "persistence";

    //static final String TIMER_FILENAME="timer.dat";
    static final String TIMER_FILENAME = "timer.xml";
    static final String TIMER_FILENAME_OLD = "timer.old";

    /**
     *  Nome con cui il timer viene registrato nel Naming Service jndi
     *  nel contesto java:comp/env/esicra
     */
    public static final String JNDI_TIMER_NAME = "esicra.timer";

    /**
     *    periodo di sleep del timer
     */
    public static long DELAY = 300000; // 300 s
    static File timerDir = null;
    /**
     *  Logger del Timer
     */
    Logger logger = null;

    /**
     *  Tabella di Hash contenente di tasks registrati nello scheduler.
     *  attributo reso pubblic per utilizzarlo nei test
     */
    public Hashtable taskMap = null;
    private SAXBuilder saxBuilder;
    private Document docXml;

    /**
     *  Costruttori
     */
    public EesTimer() {
        super();
        //taskMap = Collections.synchronizedMap(new HashMap());
        taskMap = new Hashtable();
        String timer_dir_name = System.getProperty("esicra.timer.dir");
        if (timer_dir_name == null) {
            timer_dir_name = TIMER_DIR_NAME;
        }
        String dir = System.getProperty("esicra.home") + File.separator +
            timer_dir_name;

        // [...] controllare se la dir esiste prima , altrimenti crearla
        // questo è valido per tutta la struttura di configurazione 
        timerDir = new File(dir);
        // recurepa logger
        if (logger == null) {
            logger = EsicraConf.getLogger(this.getClass());
        }

        // usa il parser xerces
        //System.setProperty("javax.xml.parsers.SAXParserFactory", "oracle.xml.jaxp.JXSAXParserFactory");
        System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl");
    
        //istanzia factory e doc builder
        saxBuilder = new SAXBuilder();
        saxBuilder.setValidation(false);
        saxBuilder.setIgnoringElementContentWhitespace(true);
    }

    public EesTimer(long ms) {
        this();
        interval = ms;
    }

    /**
     *  Schedula un nuovo task nel timer.
     *  @param task EesTask
     */
    public void inserisci(EesTask task) {
        try{
          taskMap.put(task.getId(), task);
          task.setStato(EesTask.WAIT);
        }catch (ConcurrentModificationException concEx){
          // ripeti inserimento
          taskMap.put(task.getId(), task);
          task.setStato(EesTask.WAIT);
        }
    }

    /**
     *  Cancella un task , verrà rimosso dalla lista dei tasks.
     *  i task sono resi tutti cancellabili
     *  @param id  identificatore univoco del task , chiave della tabella di hash
     */
    synchronized public void cancella(Long id) throws EesTimerException {
        EesTask eve = (EesTask) taskMap.get(id);

        // i task sono resi cancellabili
        // if ((eve!=null)&&(eve.getStato()!= EesTask.RUN)){
        //logger.debug("task: "+eve);
        //logger.debug("Task da cancellare: "+id);
        //logger.debug("lista: "+taskMap.toString());
        if (eve != null) {
            taskMap.remove(id);
        } else {
            throw new EesTimerException("Impossibile cancellare il Task: " +
                id);
        }
        logger.debug("Task :" + id + " rimosso ");
    }

    /**
     *  Cancella TUTTI i  task registrati , verrà rimosso dalla lista dei tasks.
     *  Se uno dei task è in esecuzione verrà lanciata un'eccezione.
     */
    synchronized public void rimuoviTutti() {
        taskMap.clear();
    }

    /**
     *  Sospendi un task.
     *  Disabilita temporaneamente un task mettendolo nello stato di stop.
     *  Attenzione che non interrompe un task in esecuzione ma la sua schedulazione
     *  @paran id , identificatore univoco del task nella tabella di hash
     */
    public void sospendi(Long id) throws EesTimerException {
        EesTask t = (EesTask) taskMap.get(id);
        if (t != null) {
            t.setStato(EesTask.STOP);
        } else {
            throw new EesTimerException("EesTask non esistente");
        }
    }

    /**
     *  Rimette il'task in uno stato di WAIT, in modo che possa essere eseguito.
     */
    public void riprendi(Long id) throws EesTimerException {
        EesTask t = (EesTask) taskMap.get(id);
        if (t != null) {
            t.setStato(EesTask.WAIT);
        } else {
            throw new EesTimerException("EesTask con id=" + id +
                " non esistente");
        }
    }

    /**
     *  Restituisce la lista degli tasks registrati
     */
    public Collection list() {
        //[...] eventualmente ordinare la collection secondo qualche criterio
        Collection coll = Collections.synchronizedCollection(taskMap.values());
        List list=null;
        synchronized(coll){
          list = Collections.synchronizedList(new ArrayList(coll));
          Comparator compa = new Comparatore();
          Collections.sort(list, compa);
        }
        return list;
    }

    /**
     *  Memorizza la lista dei tasks su file.
     *  Viene richiamata ogni volta vengono inseriti o cancellati gli tasks.
     *  Ma non quando cambia lo stato.
     *  Realizzato per mantenere consistente la lista degli tasks
     *  schedulati anche in caso di interruzione del container
     */
    public void salva() throws EesTimerException {
        /*
        File storeFile = new File(timerDir,TIMER_FILENAME);
        File oldStoreFile = new File(timerDir,TIMER_FILENAME_OLD);
        try{
        // rinomina quello vecchio
        if(storeFile.exists()){
          storeFile.renameTo(oldStoreFile);
        }
        // scrive quello nuovo
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile));
        synchronized(taskMap){
          oos.writeObject(taskMap);
        }
        // se tutto ok cancella quello vecchio e rinomina quello nuovo
        oldStoreFile.delete();
        oos.close();
        }catch(IOException e ){
          // altrimenti rinomina quello vecchio
          oldStoreFile.renameTo(storeFile);
          String msg = "errore durante la serializzazionedegli taskMap del Timer ";
          logger.error(msg);
          throw new  EesTimerException(msg+":"+e.getMessage() );
        }
        */
        File storeFile = new File(timerDir, TIMER_FILENAME);
        File oldStoreFile = new File(timerDir, TIMER_FILENAME_OLD);
        try {
            // rinomina quello vecchio
            if (storeFile.exists()) {
                storeFile.renameTo(oldStoreFile);
            }

            // scrive quello nuovo
            //FileOutputStream fos  = new FileOutputStream(new File(timerDir,"timer.xml"));
            FileOutputStream fos = new FileOutputStream(new File(timerDir,
                        TIMER_FILENAME));
            Element root = new Element("timer");
            Iterator ite = taskMap.values().iterator();
            synchronized (ite) {
                int c = 0;
                while (ite.hasNext()) {
                    EesTask t = (EesTask) ite.next();
                    Element e = null;
                    Element task = new Element("task");
                    task.setAttribute("id", t.getId().toString());
                    task.setAttribute("class", t.getClass().getName());
                    //System.out.println(t.getClass().getName());
                    Element ele = null;
                    ele = new Element("inizio");
                    ele.setText(Long.toString(t.getDtInizio()));
                    task.addContent(ele);
                    ele = new Element("frequenza");
                    ele.setText(Long.toString(t.getFrequenza()));
                    task.addContent(ele);
                    ele = new Element("descrizione");
                    ele.setText(t.getDescrizione());
                    task.addContent(ele);
                    ele = new Element("ultima_esecuzione");
                    ele.setText(Long.toString(t.getUltimaEsecuzione()));
                    task.addContent(ele);
                    ele = new Element("stato");
                    ele.setText(Integer.toString(t.getStato()));
                    task.addContent(ele);
                    ele = new Element("giorni_settimana");
                    ele.setText(EesTask.Array2String(t.getGiorniSettimana()));
                    task.addContent(ele);
                    ele = new Element("id_servizio");
                    ele.setText(Long.toString(t.getIdServizio()));
                    task.addContent(ele);
                    ele = new Element("data_inizio");
                    ele.setText(Long.toString(t.getDataInizio()));
                    task.addContent(ele);
                    ele = new Element("data_fine");
                    ele.setText(Long.toString(t.getDataFine()));
                    task.addContent(ele);
                    ele = new Element("sigla");
                    ele.setText(t.getSiglaExport());
                    task.addContent(ele);
                    root.addContent(task);
                }
            }
            docXml = new Document(root);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(docXml, fos);
            //outputter.output(docXml,System.out);
            // se tutto ok cancella quello vecchio e rinomina quello nuovo
            oldStoreFile.delete();
            fos.close();
        } catch (IOException e) {
            // altrimenti rinomina quello vecchio
            oldStoreFile.renameTo(storeFile);
            String msg = "errore durante la serializzazionedegli taskMap del Timer ";
            logger.error(msg);
            throw new EesTimerException(msg + ":" + e.getMessage());
        }
    }

    /**
     *  Carica la lista degli eventi
     */
    public void carica() throws EesTimerException {
        /*
        File storeFile = new File(timerDir,TIMER_FILENAME);
        // se il file è vuoto non lo carica fa nulla
        if (storeFile.length()==0L) return ;
        try{
          // se il file non esiste lo crea e notifica l'evento
          if (!storeFile.exists()){
            storeFile.createNewFile();
            logger.warn("File "+TIMER_FILENAME+" creato");
            return;
          }
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile));
          taskMap = (Map) ois.readObject();
          Iterator ite = taskMap.values().iterator();
          synchronized(ite){
            // necessario sincronizzare l'iterator
            while(ite.hasNext()){
              EesTask t = (EesTask) ite.next();
              // ATTENZIONE  agli stati
              // se uno task è stato salvato in running lo imposta a wait
              if (t.getStato()==EesTask.RUN)
                t.setStato(EesTask.WAIT);
            }
            // setta il logger
            EesTask.setLogger(EsicraConf.getLogger("it.saga.egov.esicra.timer.EesTask"));
          }
          ois.close();
        }catch(Exception e){
          String msg = "errore durante la deserializzazionedegli taskMap del Timer ";
          logger.error(msg);
          throw new EesTimerException(e.getMessage());
        }
        */
        File storeFile = new File(timerDir, TIMER_FILENAME);

        // se il file è vuoto non lo carica fa nulla
        if (storeFile.length() == 0L) {
            return;
        }
        try {
            // se il file non esiste lo crea e notifica l'evento
            if (!storeFile.exists()) {
                storeFile.createNewFile();
                logger.warn("File " + TIMER_FILENAME + " creato");
                return;
            }
            FileInputStream fis = new FileInputStream(storeFile);
            docXml = saxBuilder.build(fis);
            Element timer = docXml.getRootElement();
            List lista = timer.getChildren("task");
            Iterator ite = lista.iterator();

            // aggiungere try catch
            synchronized (ite) {
                // necessario sincronizzare l'iterator
                while (ite.hasNext()) {
                    Element ele = (Element) ite.next();

                    // recupera gli attributi
                    String id = ele.getAttribute("id").getValue();
                    String inizio = ele.getChild("inizio").getValue();
                    String frequenza = ele.getChild("frequenza").getValue();
                    String ultima_esecuzione = ele.getChild("ultima_esecuzione")
                                                  .getValue();
                    String stato = ele.getChild("stato").getValue();
                    String descrizione = ele.getChild("descrizione").getValue();
                    String giorni_settimana = ele.getChild("giorni_settimana")
                                                 .getValue();
                    String id_servizio = ele.getChild("id_servizio").getValue();
                    String data_inizio = ele.getChild("data_inizio").getValue();
                    String data_fine = ele.getChild("data_fine").getValue();
                    String sigla = ele.getChild("sigla").getValue();
                    String className = ele.getAttribute("class").getValue();

                    // istanza la classe tramite reflection 
                    EesTask t = (EesTask) Class.forName(className).newInstance();

                    // imposta ID
                    t.setId(new Long(id));
                    // imposta attributi
                    t.setDtInizio(Long.parseLong(inizio));
                    t.setFrequenza(Long.parseLong(frequenza));
                    // se uno task è stato salvato in running viene impostato a wait
                    t.setStato(Integer.parseInt(stato));
                    t.setUltimaEsecuzione(Long.parseLong(ultima_esecuzione));
                    t.setGiorniSettimana(EesTask.String2Array(giorni_settimana));
                    t.setDataInizio(Long.parseLong(data_inizio));
                    t.setDataFine(Long.parseLong(data_fine));
                    t.setSiglaExport(sigla);
                    taskMap.put(new Long(id), t);
                    if (t.getStato() == EesTask.RUN) {
                        t.setStato(EesTask.WAIT);
                    }
                }

                // setta il logger
                EesTask.setLogger(EsicraConf.getLogger(
                        "it.saga.egov.esicra.timer.EesTask"));
            }
            fis.close();
        } catch (Exception e) {
            String msg = "errore durante la deserializzazione dei tasks del Timer ";
            logger.error(msg);
            throw new EesTimerException(e.getMessage());
        }
    }

    /**
     *  Blocco di esecuzione del EesThread.
     *  per ogni task , se la data corrente è > della data inizio e la data
     *  di ultima esecuzione è 0 , esegue il task associato , impostando lo stato a RUN .
     *  Se la frequenza è 0 , al termine della prima esecuzione  il task termina
     *  e  viene rimosso dalla tabella di hash .
     *  Altrimenti successivamente riesegue il task , se
     *  la data corrente  - data di ultima esecuzione è >= della frequenza.
     *  Tutte le date sono calcolate in millisecondi.
     *
     */
    protected void process() {
        long now = System.currentTimeMillis();
        Collection coll = taskMap.values();
        // scandisce gli eventi
        Iterator ite = coll.iterator();
        try{
        while (ite.hasNext()) {
            EesTask task = (EesTask) ite.next();
            //long last = task.getUltimaEsecuzione();
            //if (last==0) task.setUltimaEsecuzione(task.getDtInizio());
            long last = task.getDtInizio();
            //logger.debug("DtInizio="+task.getDtInizio());
            //logger.debug("DtUltimaEsecuzione="+task.getUltimaEsecuzione());
            long diff = (now - task.getUltimaEsecuzione());
            if ((now > task.getDtInizio()) && (diff > task.getFrequenza()) &&
                    (task.getStato() == EesTask.WAIT) && (task.giornoValido())) {
                task.setStato(EesTask.RUN);
                task.pre();
                String nome =task.getClass().getName();
                //System.out.println("CLASSE:"+nome);
                // ATTENZIONE !! nome della classe cablato nel codice
                if(nome.endsWith("MaadTask")){
                  /*
                  il task di aggiornamento maad è speciale
                  è necessario impostare il task a terminato prima che venga
                  eseguito
                  */
                  if (task.getFrequenza() == 0) {
                    task.setStato(EesTask.END);
                  }else{
                    task.setStato(EesTask.WAIT);
                  }
                  task.setUltimaEsecuzione();
                  task.setDtInizio(task.getDtInizio()+task.getFrequenza());
                  try {
                    salva();
                    task.run();
                    task.post();
                    return;
                  } catch (Exception e) {
                      logger.error(e.getMessage());
                  }
                }
                task.run();
                task.post();
                // Imposta ultima esecuzione
                task.setDtInizio(task.getDtInizio()+task.getFrequenza());
                //task.setUltimaEsecuzione(task.getUltimaEsecuzione()+task.getFrequenza());
                task.setUltimaEsecuzione();
                task.setStato(EesTask.WAIT);
                // System.out.println(task.toString());
                // se il task ha frequenza 0 ,  ne imposta lo stato ad END
                if (task.getFrequenza() == 0) {
                    task.setStato(EesTask.END);
                }

                // aggiorna a anche la tabella persistente dei task
                try {
                    salva();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        }catch (ConcurrentModificationException concEx){
          System.out.println("ConcurrentModificationException ** repeat later");
          return;
        }
    }

    /**
     *  Restituisce la directory di lavoro in qui vengono elaborati i files di
     *  trasferimento gestiti dai tasks.
     */
    public static File getTimerDir() {
        File dir = null;
        return dir;
    }

    /**
     *  classe anonima per ordinare i task del timer sort desc
     */
    class Comparatore implements Comparator {
        synchronized public int compare(Object o1, Object o2) {
            long pos1 = ((EesTask) o1).getDtInizio();
            long pos2 = ((EesTask) o2).getDtInizio();
            if (pos1 > pos2) {
                return -1;
            } else if (pos1 < pos2) {
                return 1;
            }
            return 0;
        }
    }
}
