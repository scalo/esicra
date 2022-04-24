package it.saga.egov.esicra;

import it.saga.egov.esicra.importazione.DbCommon;
import org.apache.log4j.*;
import java.io.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import it.saga.siscotel.db.Versione;
import it.saga.siscotel.db.SerEsicraInfo;

/**
 *  Classe di configurazione di ESICRA
 *  Tramite questa vengono impostate le risorse globali dell'applicazione
 *  come il logger , oggetti vari e le variabili di ambiente per l'instalazione
 *  di esicra.
 *  La property fondamentale è "esicra.conf" , passata come parametro -D JVM ,
 *  indica dove localizzare il file principale di configurazione di esicra.
 *  In oc4j embedded , il parametro deve essere specificato nel progetto-> Runner
 *  come java option : -Desicra.conf=c:\esicra\conf
 *  In tomcat va messa come variabile d'ambiente CATALINA_OPTS
 *  In oc4j 9iAs i bc4j , creano un ritardo di 10 min all'app server durante la createRootAppModule
 *
 */
public class EsicraConf {
    // path assoluti dei files di rosorsa utilizzati da esicra
    // i nomi dei files delle properties utilizzati dai resource boundle non 
    // vogliono i suffissi properties
    public static final String ESICRA = "esicra";
    public static final String LOG4J = "esicra_log4j";
    public static final String PATH = "java:comp/env/esicra";
    public static final String BC4J_PACKAGE = "it.saga.egov.esicra.bc4j.";

    //public static final String PATH=  "java:comp/env";
    public static final String[] TIPI_DB = { "oracle", "postgresql", "mssql" };
    public static String esicraConf = "";
    private static boolean configurato = false;
    private static boolean moduli = false;
    private static boolean properties = false;
    private static boolean contesto = false;
    public static final String TYREX_NS = "tyrex.naming.MemoryContextFactory";
    public static final String TOMCAT_NS = "org.apache.naming.java.javaURLContextFactory";
    static Logger logger = null;
    
    /** Versione dell'applicazione **/
    
    public static String versione="1_1_1";
    
    /**
     *  Metodo statico che carica le properties di Esicra e le rende
     *  accessibili come system properties
     */
    public static void configura() {
        System.setProperty("esicra.versione",versione);
        // directory di configurazione di esicra
        if (configurato) {
            //System.out.println("esicra già configurato");
            return;
        }
        esicraConf = System.getProperty("esicra.conf");
        if (esicraConf == null) {
            System.out.println(
                "ERRORE CONFIGURAZIONE:  property esicra.conf non trovata ");
            return;
        }
        configuraProperties();
        // CONFIGURA IL CONTESTO
        configuraContesto();
        configurato = true;
        controllaVersione();
        System.out.println("Esicra/Maad versione : "+versione);
        logger.info("Esicra versione: "+versione);
        logger.info("Id Ente:"+System.getProperty("esicra.id_ente"));
        logger.info("Id Istat:"+System.getProperty("esicra.id_istat"));
    }

    /**
     *  carica le properties dal file di configurazione
     **/
    public static void configuraProperties() {
        if (properties) {
            return;
        }
        File dir = new File(esicraConf);
        
        File confFile = new File(dir, "esicra.properties");
        try {
            FileInputStream confStream = new FileInputStream(confFile);
            java.util.Properties props = new java.util.Properties(System.getProperties());
            props.load(confStream);
            System.setProperties(props);
            java.util.Properties pp = new java.util.Properties(System.getProperties());
            String logdir = System.getProperty("esicra.log");

            //System.out.println("esicra.log="+logdir);
            String filename = esicraConf + File.separator +
                "esicra_log4j.properties";

            //System.out.println("filename: "+filename);
            String classpath = System.getProperty("java.class.path");

            //System.out.println("CP ="+classpath);
            String newclasspath = esicraConf + File.pathSeparator + classpath;

            //System.out.println("NVP "+newclasspath);
            System.setProperty("java.class.path", newclasspath);
            PropertyConfigurator.configure(filename);
            logger=Logger.getLogger(EsicraConf.class);
            logger.debug("esicraConf="+dir.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("FILE DI CONFIGURAZIONE" +
                confFile.getAbsolutePath() + " NON TROVATO !");
            return;
        }

        // Controllo sulla property esicra.database
        String db = System.getProperty("esicra.database");
        boolean supportato = false;
        for (int i = 0; i < TIPI_DB.length; i++) {
            if (TIPI_DB[i].equals(db)) {
                supportato = true;
            }
        }
        if (((db == null) || (db.equals(""))) && supportato) {
            //logger.error("Tipo di database non impostato o sconosciuto "+db);
            System.out.println("Tipo di database non impostato o sconosciuto " +
                db);
        } else {
            logger.info("esicra.database=" + db);
        }

        //System.out.println("esicra.database="+db);
        properties = true;
    }

    /**
     *  Configura il contesto jndi
     */
    public static void configuraContesto() {
        if (contesto) {
            return;
        }
        try {
            Context esicraCtx = getInitialContext(PATH);

            //logger.debug("esicraCtx = "+esicraCtx);
        } catch (Exception e) {
            // ERRORE
            e.printStackTrace();
        }
        contesto = true;
    }

    /**
     *  Metodo statico per evitare di utilizzare try/catch annidati
     *
     *  @param    ctx contesto in cui cercare la risorsa
     *  @param    nome  nome della risorsa
     *  @return   Oggetto risorsa oppure null se non trovata
     *
     *
     */
    public static Object cercaRisorsa(Context ctx, String nome) {
        Object o = null;
        try {
            return ctx.lookup(nome);
        } catch (NamingException e) {
            //e.printStackTrace();
            logger.error("Risorsa jndi "+nome+" non trovata");
            return o;
        }
    }

    /**
     *  Metotodo che converte un ResourceBoundle in oggetto Properties,
     *  necessario per la configurazione di log4j.
     *  NB
     *  l'accesso diretto ai files , crea problemi a oc4j locale ( internal server error)
     */
    private static java.util.Properties resourceBoundleToProperties(
        ResourceBundle rb) {
        Enumeration keys = rb.getKeys();
        java.util.Properties props = new java.util.Properties();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            props.put(key, rb.getObject(key));
        }
        return props;
    }

    /**
     *  Configura logger
     *  vecchio metodo tenuto per compatibilita'
     *  TEMPORANEO
     *
     */
    public static Logger configuraLogger(Class classe) {
        return getLogger(classe);
    }

    /**
     *  Restituisce il logger associato alla classe
     *  @param classe , classe di cui si vuole ottenere il logger
     *  @return Logger della classe
     */
    public static Logger getLogger(Class classe) {
        if (classe == null) {
            return Logger.getRootLogger();
        }
        return Logger.getLogger(classe);
    }

    /**
     *  Restituisce il logger associato alla classe
     *  @param str , string rappresentante una classe java
     *  @return Logger della classe
     */
    public static Logger getLogger(String str) {
        if (str == null) {
            return Logger.getRootLogger();
        }
        return Logger.getLogger(str);
    }
 
    /**
     *  Metodo che restituisce il contesto iniziale , utilizzando il
     *  Tyrex JNDI naming service (in memory)
     *  @param url nome dell' url provider
     *  @return InitialContext
     */
    public static InitialContext getInitialContext(String url)
        throws NamingException {
        Hashtable env;
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, TYREX_NS);
        env.put(Context.PROVIDER_URL, url);
        if (url != null) {
            env.put(Context.URL_PKG_PREFIXES, "tyrex.naming");
        }
        return new InitialContext(env);
    }

    /**
     *  Metodo che restituisce il contesto jdbc , configurato
     *  nel servlet engine.
     *  Metodo necessario per poter elencare tuti gli oggetti registrati nel contesto
     *  d'ambiente e mantenere la compatibilità del codice tra tomcat e oc4j che gestiscono
     *  il jndi in maniera differente.
     *  Il metodo è usato solo dalla servlet che visualizza l'elenco degli oggetti jndi registrati
     *  e le proprietà di sistema.
     *
     *  @return  Jdbc Context
     */
    public static Context getJdbcContext() throws NamingException {
        Context init = new InitialContext();
        String engine = System.getProperty("esicra.engine");
        if (engine.equals("oc4j")) {
            return (Context) init.lookup("java:comp/env");
        }else
        if (engine.equals("ias")) {
            return init;
        }
        // else tomcat
        return (Context) init.lookup("java:comp/env");
    }

    /**
     *   Metodo che restituisce il datasource associato al nome della risorsa passato.
     *   @param propDs  stringa contenente il nome della propery del datasource
     *    (il nome del datasource deve essere COMPLETO di sottocontesto)
     *      es: jdbc/miodatasource , questo è necessario a causa della differente gestione
     *      del context jndi di default tra tomcat e oc4j
     *
     */
    public static DataSource getDataSource(String propDs) {
        // context jdbc
        String nomeRisorsa = System.getProperty(propDs);
        Context jdbc = null;
        try {
            //Context init = new InitialContext();
            jdbc = (Context) getJdbcContext();
        } catch (NamingException e) {
            System.out.println(e);
        }
        if (jdbc == null) {
            System.out.println("Contesto J2ee NON TROVATO !");
        }
        return (DataSource) cercaRisorsa(jdbc, nomeRisorsa);
    }

    /**
     *  Controlla che la versione del bc4j contenuta nella classe Versione del
     *  esicradb.jar sia la stessa di quella memorizzata nel db , nella tabella
     *  ser_esicrainfo , campo versione.
     */
    private static void controllaVersione() {
        
        String versioneHbm = Versione.getVersione();
        Logger logger = getLogger(EsicraConf.class);
        SerEsicraInfo row = (SerEsicraInfo) DbCommon.cercaRiga("it.saga.siscotel.db.SerEsicraInfo","nomedb","esicra");
        if (row == null) {
                logger.error("Tabella SER_ESICRA_INFO non valida");
        }
        else {
                String versioneDb = row.getVersione();
                if (versioneHbm.equalsIgnoreCase(versioneDb)) {
                    logger.info("Versione Database Esicra " + versioneDb);
                } else {
                    logger.error("Versione DB diversa da versione classi Hibernate  db=" +
                        versioneDb + " hibernate=" + versioneHbm);
                }
        }
    }

    public static void main(String[] args) {
        configura();
    }
}

