package it.saga.siscotel.db.test;

import java.sql.Timestamp;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.*;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;
//import org.hibernate.util.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.lang.Exception;
import java.math.*;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 *      @todo   OTTIMIZZAZIONE transazione
 *      @todo   properties mettere UPPERCASE !!!!!
 *      @todo   generazione automatica delle righe della esipkid
 *      @todo   controllare se il "for update" funziona con oracle e mssql
 *      @todo   controllare le properties estese
 *      @todo   cercare di recuperare il nome della tabella dal bean
 *      @todo   togliere System.out.println
 *      @todo   controllare atomicità
 */
public class PkidTableGenerator implements PersistentIdentifierGenerator,
    Configurable {
    /**
     *      @todo attributi da sistemare
    */
    public static final String PKID_TABLE = "pkid_table";
    public static final String TABLE_COLUMN = "table_column";
    public static final String ID_COLUMN = "target_column";
    public static final String TABLE_NAME = "target_table";
    private static final Log logger = LogFactory.getLog(PkidTableGenerator.class);
    //private static final SimpleDateFormat SDF = new SimpleDateFormat();
    private String tablePkid;
    private String tableName;
    private String tableColumn;
    private String idColumn;
    private String query;
    private String update;
    private static String id_ente = System.getProperty("id_ente");
    
    private static long count = 0;
    
    String create_row = "INSERT INTO ESINEXTID " + " ( PKID , " + " NOME_TABELLA ," +" INCREMENTO ," +" NEXT_ID," +
            "DT_MOD ," + "DT_INS, " +"fkid_utente_ins, fkid_proc_ins) " + "VALUES (? , ? , 1, 2 ,? , ?, 2, 4 )";

    
    public void configure(Type type, Properties params, Dialect dialect) {
        // attributi default
        // System.out.println("Props:"+params);
        this.tablePkid = PropertiesHelper.getString(PKID_TABLE, params,
                "ESINEXTID");
        this.tableColumn = PropertiesHelper.getString(TABLE_COLUMN, params,
                "nome_tabella");
        this.idColumn = PropertiesHelper.getString(ID_COLUMN, params, "next_id");

        this.tableName = PropertiesHelper.getString(TABLE_NAME, params,
                "UNDEFINED");

        String schemaName = params.getProperty(SCHEMA);
        // System.out.println("schemaName: "+schemaName);
        //if ((schemaName != null) && (tablePkid.indexOf(StringHelper.DOT) < 0)) {
        if ((schemaName != null) && (tablePkid.indexOf(".") < 0)) {
            tablePkid = schemaName + '.' + tablePkid;
        }

        query = "select next_id  from " + tablePkid + " where " +
            tableColumn + " = ?";
        /*
        if (dialect.supportsForUpdate()) {
            query += " for update";
        }
        */
        // System.out.println("Update idColumn "+idColumn);
        update = "update " + tablePkid + " set next_id  = ? where " +
            tableColumn + " = ?";
    }

    public synchronized Serializable generate(SessionImplementor session,
        Object object) throws HibernateException {
        
        // This has to be done using a different connection to the
        // containing transaction because the new hi value must
        // remain valid even if the containing transaction rolls
        // back
        /* TODO sarebbe opportuno cmq tenere una sessione separata da 
         *  committare solo periodicamente    */
        
        Connection conn = session.getBatcher().openConnection();
        long result = 0;
        int rows;
        String id_ente = "";
        try {
            do {
                // The loop ensures atomicity of the
                // select + update even for no transaction
                // or read committed isolation level
                PreparedStatement qps=null;
                try {
                    //System.out.println("Query="+query);
                    qps = conn.prepareStatement(query);
                    // recupera id_ente
                    id_ente = System.getProperty("id_ente");
                    //System.out.println("id_ente="+id_ente);
                    if (id_ente == null) {
                        throw new IdentifierGenerationException("id_ente non trovato");
                    }
                    //System.out.println("Tablename="+tableName);
                    //System.out.println("tablePkid="+tablePkid);
                    qps.setString(1, tableName);
                    ResultSet rs = qps.executeQuery();
                    
                    if (!rs.next()) {
                        try{
                          //System.out.println(create_row);
                          long ts = System.currentTimeMillis();
                          PreparedStatement create = conn.prepareStatement(create_row);                
                          create.setBigDecimal(1,new BigDecimal(ts));
                          create.setString(2,tableName);
                          create.setTimestamp(3,new Timestamp(ts));
                          create.setTimestamp(4,new Timestamp(ts));
                          create.execute();
                          conn.commit();
                          create.close();
                          result=1;
                        }catch(Exception e){
                          String err =
                              "could not read a hi value - you need to populate the table: " +
                              tablePkid;
                          System.out.println(e);
                          logger.error(err);
                          System.exit(1);
                          throw new IdentifierGenerationException(err);
                          //logger.debug("Create Entry for table: "+tablePkid);
                          
                        }
                    }else{
                      result = rs.getLong(1);
                      rs.close();
                    }
                    if (qps!=null) qps.close();
                } catch (SQLException sqle) {
                    logger.error("could not read a hi value", sqle);
                    
                    throw new HibernateException(sqle.toString());
                } catch (Exception ex) {
                    logger.error("id_ente non trovato", ex);
                } 
                
                try {
                    //System.out.println("result:"+result);
                    //System.out.println("tableName:"+tableName);
                    PreparedStatement ups = conn.prepareStatement(update);
                    ups.setLong(1, result + 1);
                    ups.setString(2, tableName);
                    rows = ups.executeUpdate();
                    conn.commit();
                    ups.close();
                } catch (SQLException sqle) {
                    logger.error("could not update hi value in: " + tablePkid, sqle);
                    throw new HibernateException(sqle.toString());  
                } 
            } while (rows == 0);
   
            BigDecimal seq = new BigDecimal(result).multiply(new BigDecimal(
                        10000000));
            BigDecimal id = new BigDecimal(id_ente);
            return seq.add(id);
        } finally {
            //session.getBatcher().closeConnection(conn);
            session.flush();
        }
        
        /*
        BigDecimal seq = new BigDecimal(count).multiply(new BigDecimal(
                        10000000));
        BigDecimal id = new BigDecimal(id_ente);
        count ++;
        // if (count%50==0) System.out.println(count);
       
        return seq.add(id);
        */
    }

    public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {
        //System.out.println("sqlCreateStrings");
        return new String[] {
            // "create table " + tablePkid +
            // " ( " + 
            //    idColumn + " " + dialect.getTypeName(Types.INTEGER) +
            // " )",
            create_row
        };
    }

    public String[] sqlDropStrings(Dialect dialect) {
        //return "drop table " + tablePkid + dialect.getCascadeConstraintsString();
        StringBuffer sqlDropString = new StringBuffer().append("drop table ");
        if (dialect.supportsIfExistsBeforeTableName()) {
            sqlDropString.append("if exists ");
        }
        sqlDropString.append(tablePkid).append(dialect.getCascadeConstraintsString());
        if (dialect.supportsIfExistsBeforeTableName()) {
            sqlDropString.append(" if exists");
        }
        return new String[]{sqlDropString.toString()};
    }

    public Object generatorKey() {
        return tablePkid;
    }
    
}

