package it.saga.egov.esicra.db;

import java.lang.ClassCastException;
import java.lang.ClassNotFoundException;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.*;
import org.apache.log4j.Logger;
import org.xml.sax.*;
import javax.xml.parsers.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.helpers.Loader;
import it.saga.egov.esicra.EsicraConf;

/**     
 *      Per un utilizzo da jsp , il database è costituito da un solo schema.
 * 
 *      @todo inserire il nome del database (a sapere come recuperarlo ...)
 *      
 *      l'ordine degli elementi nell'xml non è rilevante (importa l'ord o il pos)
 */
public class Database implements Comparable {
    
    /* Encoding Latin1 */
    private static final String HEADER="<?xml version=\"1.0\" encoding=\"ISO-8859-1\" />\n";
    
    // private String dbname;
    private Connection conn;
    private Logger logger;
    private CompareLogger compLog;
    private Document docXml;
    private String dbName;
    private String hostName;
    private SAXBuilder saxBuilder = null;
    private String vendor ;
    
    /**
     * Comment here
     * @supplierCardinality 0..1
     * @associates <{it.saga.esicra.db.Schema}>
     */
    protected Schema schema;

    protected String schemaName;
   
    private String user;
    private String password;
    private String url ;
        
    public Database(){
        // usa il parser oracle
        //System.setProperty("javax.xml.parsers.SAXParserFactory","oracle.xml.jaxp.JXSAXParserFactory");
        System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl");
        //istanzia factory e doc builder
        saxBuilder = new SAXBuilder();
        saxBuilder.setValidation(false);
        saxBuilder.setIgnoringElementContentWhitespace(true);
    }
    
    public Database(String url,String schemaName,String user, String password ){
        this();
        this.user=user;
        this.password=password;
        this.url=url;
        this.schemaName=schemaName;
        logger = Logger.getLogger(this.getClass());
        compLog = CompareLogger.getLogger();
        try{
            //this.vendor=vendor;
            conn= DriverManager.getConnection(url,user,password);
            //logger.debug("conn:"+conn);
            DatabaseMetaData meta = conn.getMetaData();
            //System.out.println("DriverName"+meta.getDriverName());
            //System.out.println("DatabaseProductName"+meta.getDatabaseProductName());
            this.vendor= meta.getDatabaseProductName();
            conn.close();
        }catch( SQLException se){
            logger.error("Errore connessione DB "+se);
            return;
        }
    }
    
    public void fromJdbc(){
        try{
            // riapre una connessione;
            conn= DriverManager.getConnection(url,user,password);
            //logger.debug("conn:"+conn);
            DatabaseMetaData meta = conn.getMetaData();
            if(schemaName!=null){
              this.schema= new Schema(schemaName,meta);
              //System.out.println("schema "+this.schema);
            }
            creaXml();
            conn.close();
        }catch( SQLException se){
            logger.error("Errore connessione DB "+se);
            return;
        }
    }
    
    private void creaXml(){
        if (docXml==null&&schema!=null){
            Element element = new Element("database");
            /* ??? si può recuperare il nome del db ??? */
            //element.setAttribute("name",dbName); 
            element.setAttribute("vendor",vendor);
            docXml = new Document(element);  
            element.addContent(getSchema().toXml());
        }
    }
    
    public Element toXml() {
        return docXml.getRootElement();
    }
     
    public boolean loadXml(InputStream is){
        Reader reader = new InputStreamReader(is);
        //Reader reader = new StringReader(strDoc);
        try{
            Document doc = saxBuilder.build(reader);
            //System.out.println(doc.getRootElement().toString());
            fromXml(doc.getRootElement());
        }catch(Exception e){
            logger.error(e);
        }
        return true;
    }
    
    public void  fromXml(Element  node){
        Element e = node.getChild("schema");
        String name = e.getAttribute("name").getValue();
        Schema sch = new Schema(name);
        sch.fromXml(e);
        this.schema=sch;
        creaXml();
    }
    
    public String toString(){
        if(docXml==null) return "";
        StringWriter sw = new StringWriter(); 
        try {
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(docXml,sw);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
    
    public static void main(String[] args) {
        //Class.forName("org.postgresql.Driver");
        EsicraConf.configura();
        String url = "jdbc:postgresql://srvrsnod:5432/esicra_test";
        Database db  = new Database(url,"esicra","esicra","egov2003");
        if(db!=null)
            System.out.println(db);
    }
    
    public Connection  getConnection(){
        return conn;
    }
    
    public int compareTo(Object o){
        compLog.clear();
        Database d1 = this;
        Database d2 = (Database) o;
        Schema s1 = d1.getSchema();
        Schema s2 = d2.getSchema();
        if(s1.compareTo(s2)!=0){
           return -1;
        }
        return 0;
    }
    
    public  String[] listSchemas(){
        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getSchemas();
            ArrayList lista= new ArrayList();
            while(rs.next()){
                String schemaName =(String) rs.getObject("TABLE_SCHEM");
                lista.add(schemaName);
            }
            rs.close();
            int len = lista.size();
            //System.out.println("LEN:"+len);
            String[] array = new String[len];
            for(int i=0;i<len;i++){
                array[i]=(String)lista.get(i);
            }
            return array; 
        }catch(SQLException e){
            return null;
        }
    }
    
    public boolean isConnected(){
        if (conn!=null) return true;
        return false;
    }

    public  void setSchema(String schemaName){
        this.schemaName=schemaName;
        try{
            // riapre una connessione;
            conn= DriverManager.getConnection(url,user,password);
            logger.debug("conn:"+conn);
            DatabaseMetaData meta = conn.getMetaData();
            if(schemaName!=null){
              this.schema= new Schema(schemaName,meta);
            }
            creaXml();
            conn.close();
        }catch( SQLException se){
            logger.error("Errore connessione DB "+se);
            return;
        }
    }
    
    public Schema getSchema(){
        return this.schema;
    }
    
    public String getCompLog(){
      return compLog.getLog();
    }
    
}
