package it.saga.egov.esicra.importazione;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.utilita.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdom.Namespace;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;

/**

  Parser Sax - scompone  lista di Documenti in un file in documenti singoli
    che verranno poi elaborati singolarmente.
    Classe astratta che deve essere derivata per poter essere utilizzata.

    @todo Migliorare le strutture temporanee di memorizzazione

    NB Il parser sax funziona in sigle-thread

    2004-30-12 rinominata  var prog in cont, cambiato scopo doc da private a protected

**/

public abstract class ImportParser extends DefaultHandler {
    private static final String PARSER_NAME = "org.apache.xerces.jaxp.SAXParserFactoryImpl";
    protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    protected static Logger logger;
    /** Elementi*/
    private String currentElement;
    private Vector storage;
    protected StringBuffer doc;
    private String buffer;
    //sessione hibernate
    protected Session session;
    private Transaction  transaction ;
    protected XMLReader parser;
    protected SAXParser sax;
    protected InputSource input;
    protected InputStream reader=null;
    protected Vector ultimi;
    protected Vector rigettati;
    protected Vector elementi = null;
    protected String listaElementi = null;
    protected Timestamp dtmod = null;
    /**
     *  contatore documenti elaborati del perser
     */
    protected int cont = 0;
    /**
     *  progressivo letto dall'attributo nel tag
     */
    protected int progressivo = 0;
    protected String versione = null;
    protected File schemaDir = null;
    protected String schemaName = "";
    protected boolean validazione = false;
    protected boolean simulazione = false;
    private boolean inizioLista = false;
    private boolean inizioElementoLista = false;
    /* namespace aggiunto per parametrizzare il getChild() con un oggetto piuttosto
     che con un null , per scopi futuri
     */
    protected Namespace namespace = null;
    //protected boolean flagTraccia = false;
    //protected Vector tracciaImportati = new Vector();
    protected int nonvalidi=0;
    protected String ultimoErrore="";
    
    /**
     *  caratteri di escape per xml
     */
    static public final String escapedChars = "<>\'\"&]";

    /**
     *  caratteri validi per l'xml 
     */
    static public final String okChars      = "\r\n\t";  

    /**
     *  Costruttore semplice che riceve in ingresso solo il file
     *
     *  @param file , file da processare
     *
     */
    public ImportParser(File file) throws FileNotFoundException, SAXException {
        dtmod = new Timestamp(System.currentTimeMillis());
        doc = new StringBuffer();
        elementi = new Vector();
        listaElementi = "dummy";
        ultimi = new Vector();
        rigettati = new Vector();
        nonvalidi=0;
        /*
        // configurazione del logger spostata in EsicraConf
        logger = Logger.getLogger(this.getClass());
        logger.setLevel(Level.DEBUG);
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
        */
        
        // aggancia la sessione di hibernate
        session=HibernateUtil.currentSession();
        transaction = session.beginTransaction();
        String str = System.getProperty("esicra.import.validazione");
        if (str != null) {
            validazione = Boolean.valueOf(str.trim()).booleanValue();
        }else{
            // se non impostata la validazione è disabilitata
            validazione=false;
        }
        str = System.getProperty("esicra.import.simulazione");
        if (str != null) {
            simulazione = Boolean.valueOf(str.trim()).booleanValue();
        }else{
            // se non impostata la simulazione è disabilitata
            simulazione=false;
        }
        if (logger == null) {
            EsicraConf.configura();
        }
        logger = EsicraConf.configuraLogger(this.getClass());

        if (System.getProperty("esicra.xsd.dir") != null) {
            schemaDir = new File(System.getProperty("esicra.xsd.dir"));
        } else {
            logger.error("Attenzione proprietà esicra.xsd.dir non configurata");
        }

        try {
            // prepatata patch per & nell'xml
            //reader = new SagaXmlFilterInputStream(new FileInputStream(file));
            reader = new FileInputStream(file);
            // forza il character encoding a UTF8 , se non lo è da errore
            //FileInputStream fin = new FileInputStream(file);
            //InputStreamReader reader = new InputStreamReader(fin,"UTF-8");
            input = new InputSource(reader);
            //logger.debug("Encoding:"+input.getEncoding());
            //parser = XMLReaderFactory.createXMLReader(PARSER_NAME);
            String factoryImpl = System.getProperty("javax.xml.parsers.SAXParserFactory");
            if (factoryImpl == null){
              System.setProperty("javax.xml.parsers.SAXParserFactory",
                                 "org.apache.xerces.jaxp.SAXParserFactoryImpl");
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            //factory.setValidating(true);
            sax = factory.newSAXParser();
            parser = sax.getXMLReader();
            parser.setContentHandler(this);
        } catch (SAXException e) {
            throw new NoClassDefFoundError("Parser SAX " + PARSER_NAME +
                " non disponibile");
        }
        catch(Exception e){
            logger.error(e);
        }
    }

    public void setLista(String le) {
        this.listaElementi = le;
    }

    public void setElementi(Vector v) {
        elementi = v;
    }

    public void parse() throws SAXException, IOException {
        try{
          parser.parse(input);
        }catch(UTFDataFormatException ue){
          logger.error("Encoding flusso non valido (UTFDataFormatException) :"+ue);
        }
    }

    public void startDocument() throws SAXException {
        logger.debug("Inizio Lista. Versione Programma: " + versione);
    }

    public void startElement(String namespaceURI, String localName,
        String rawName, Attributes atts) throws SAXException {
        currentElement = localName;
        if (localName.equals(listaElementi)) {
            // verifico se la versione è  quella corretta
            String s = null;
            if (versione == null) {
                throw new SAXException("Versione Flusso Xml non definita");
            }
            for (int i = 0; i < atts.getLength(); i++) {
                if (atts.getQName(i).equalsIgnoreCase("versione")) {
                    s = atts.getValue(i);
                    // classe luigi
                    if (GestioneVersione.verificaVersione(versione, s) == false) {
                        throw new SAXException(
                            "Versione Flusso non valida. Il flusso deve avere versione " +
                            versione + " o minore");
                    } else {
                        versione = s;
                        logger.debug("Inizio Lista. Versione Documento XML:" +
                            versione);
                    }
                }
            }
            if (s == null) {
                throw new SAXException("Versione Flusso Xml: '" + localName +
                    "' non specificata");
            }
            inizioLista=true;
        } else if (elementi.contains(localName)) {
            inizioElementoLista=true;
            schemaName = localName + ".xsd";
            doc = new StringBuffer("");
            doc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            // aggiunge schema nel documento xml estratto
            doc.append("<" + localName +
                " xmlns=\"http://www.saga.it/egov/esicra/importazione/xml/esicra_import\" \n" +
                " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                " xsi:schemaLocation= \"" +
                "http://www.saga.it/egov/esicra/importazione/xml/esicra_import \n" +
                "xsd/" + versione + "/" + localName + ".xsd\"");
            if (atts != null) {
                for (int i = 0; i < atts.getLength(); i++) {
                    if (atts.getQName(i).equalsIgnoreCase("progressivo")) {
                        String s = atts.getValue(i);
                        progressivo = Integer.parseInt(s);
                    } else {
                        doc.append(" " + atts.getQName(i) + "=\"" +
                            atts.getValue(i) + "\"");
                    }
                }
            }
            doc.append(">\n");
        } else if(inizioLista && inizioElementoLista )  { // altri
            doc.append("<" + localName);
            if (atts != null) {
                for (int i = 0; i < atts.getLength(); i++) {
                    doc.append(" " + atts.getQName(i) + "=\"" +
                        atts.getValue(i) + "\"");
                }
            }
            doc.append(">");
        }else{
            if(!inizioLista){
                //throw new SAXException("ElementoLista del file xml non valido per l'importazione");
                ultimoErrore="ElementoLista del file xml non valido per l'importazione";
            }
            else{
                //throw new SAXException("ElementoLista del file xml non valido per l'importazione");
                logger.error(ultimoErrore);
            }
            logger.error("ElementoLista del file xml non valido per l'importazione");
            
            //Configuration.releaseRootApplicationModule(am, false);
            //am.getTransaction().disconnect(false);
            throw new SAXException("ElementoLista del file xml non valido per l'importazione");
        }
    }

    public void characters(char[] ch, int start, int end)
        throws SAXException {
        StringBuffer data = new StringBuffer( new String(ch, start, end).trim());
        for(int i=0;i<data.length();i++){
                char c = data.charAt(i);
                if(needsEscape(c)){
                    data.replace(i,i+1,xmlEscape(c));
                }
        }
        doc.append(data.toString());
    }

    public void endElement(String namespaceURI, String localName, String rawName)
        throws SAXException {
        if (localName.equals(listaElementi)) {
            //nop
        } else if (elementi.contains(localName)) {
            doc.append("</" + localName + ">\n");
            // GESTISCE CARATTERI DI ESCAPE
            /*
            for(int i=0;i<doc.length();i++){
                char c = doc.charAt(i);
                if(needsEscape(c)){
                    doc.replace(i,i,sgmlEntity(c));
                }
            }
            */
            // salva il documento
            ultimi.add(doc.toString());
            //<< VALIDAZIONE JAXP
            if (validazione) {
                try {
                    byte[] sb = doc.toString().getBytes("UTF-8");
                    ByteArrayInputStream in = new ByteArrayInputStream(sb);
                    JAXPValidator validator = new JAXPValidator();
                    String schema= schemaDir +File.separator+schemaName;
                    //logger.debug("schema = "+schema);
                    boolean res = validator.validateSchema(schema,in);
                    if(!res){
                      logger.error("Errore Validazione progressivo #"+progressivo);
                      //logger.debug(doc.toString());
                      nonvalidi++;
                    }else{
                      if(!simulazione)
                      process(doc.toString());
                    }
                    cont++;
                }catch (Exception e) {
                    // ignora momentaneamente
                    logger.error("Errore Generico I/O progressivo #"+progressivo+" "+e.getMessage());
                    logger.debug("\n"+doc.toString());
                }
            } else {
                //>>
                try {
                    if(!simulazione) process(doc.toString());
                    cont++; 
                } catch (Exception e) {
                    logger.error("Errore process() progressivo #"+progressivo);
                    //logger.debug("Errore process() progressivo #"+progressivo, e);
                    logger.debug(doc.toString());
                }
            }
        } else {
            //aggiungi tutto al documento corrente
            //write(doc);
            //chiudi documento
            doc.append("</" + localName + ">\n");
        }
    }

    public void endDocument() throws SAXException {
        logger.debug("Fine Lista");
        // !!!!  se togli questo commit , gli ultimi record non sono caricati !!!!
        committBlock();
        statistica();
        // Configuration.releaseRootApplicationModule(am, false);
        //am.getTransaction().disconnect(false);
        // chiude sessione hibernate
        transaction.commit();
        HibernateUtil.closeSession();
        logger.debug("commit transaction");
        session.flush();
        session.close();
        logger.debug("session close");
        try {
            logger.debug("close reader "+reader);
            if(reader!=null) reader.close();
        } catch (IOException e) {
            //logger.debug("release error AM");
            logger.error("Errore nella chiusura del file " + e.getMessage());
        }
    }

    /**
     *  committa i documenti importati, in caso di errore li riprocesse
     *  singolarmente , salvando eventualmente i rigettati
     */
    public void committBlock() {
        try {
           // am.getTransaction().commit();
           transaction.commit();
        } catch (Exception e) {
            // STACKTRACE
            e.printStackTrace();
            // am.getTransaction().rollback();
            //transaction.commit();
            logger.debug("error commit transaction");
            /*
                riprocessa gli ultimi documenti non committati e
                salva i documenti non importati
            */
            Iterator ite = ultimi.iterator();
            while (ite.hasNext()) {
                // recupera il documento
                String str = (String) ite.next();
                try {
                    process(str);
                    // am.getTransaction().commit();
                    //
                } catch (Exception se) {
                    // am.getTransaction().rollback();
                    logger.warn(
                        "Documento  non importato: probabile errore sql ");
                    //salva il documento e continua
                    rigettati.add(str);
                }
            }
            ultimi.clear();
        }
        //
        //svuota il blocco degli ultimi documenti
        ultimi.clear();
    }

    /**
     *  Testituisce il numero dei documenti elaborati
     */
    public int elaborati() {
        return cont;
    }

    /**
     *  Restituisce il numero di documenti rigettati
     */
    public int rigettati() {
        return rigettati.size();
    }
    
  /**
   *    controlla se il carattere necessita di escape
   */
  static protected boolean needsEscape(char c) {
    return ((c < '\u0020' && okChars.indexOf(c) < 0) ||
            escapedChars.indexOf(c) >= 0);
  }

  /**
   *    restituisce la sequenza di escape associata ad un particolare carattere
   */
  public static String xmlEscape(char c) {
    return (c == '<') ? "&lt;" :
           (c == '>') ? "&gt;" :
           (c == '\'') ? "&apos;" :
           (c == '\"') ? "&quot;" :
           (c == '&') ? "&amp;" :
           (c == ']') ? "&#93;" :
           (c < '\u0020' && c != '\n' && c != '\r' && c != '\t') || c > '\u0080' ?
              toXmlEncoding(c) :
           null;
  }

  public static String toXmlEncoding(char c) {
    return c > 0x20 || c == 0x9 || c == 0xa || c == 0xd ? "&#" + (int)c + ";" : "?";
  }
    
  protected abstract void process(String doc) throws Exception;

  protected abstract void statistica();

  /**
   *    restituisce il numero di documenti non validi, -1 se la validazione
   *    non è abilitata.
   */
  public int getNonValidi(){
    if(validazione){
        return nonvalidi;    
    }
    return -1;
  }

  public String getElementoLista(){
      return listaElementi;
  }

  public String[] getElementi(){
      String[]  list= new String[elementi.size()];
      list=(String[])elementi.toArray(list);
      return list;
  }
  
  /**
   *  Restituisce la data di importazione
   *  
   * @return dtmod  data di inizio importazione
   */
  public Timestamp getDtMod(){
    return dtmod;
  }

}
