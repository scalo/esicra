package it.saga.egov.esicra.coordinator.servlet;

//import oracle.jbo.domain.ClobDomain;
//import oracle.sql.CLOB;
import it.saga.egov.esicra.EsicraConf;
import it.saga.egov.esicra.coordinator.EesTransazione;
import it.saga.egov.esicra.coordinator.EesTransazioneFactory;
import org.apache.log4j.Logger;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public abstract class EesSincronizza extends HttpServlet
    implements SingleThreadModel {
    protected static Context context;
    private static final int DIM_BUFFER = 10240;
    protected Logger logger;
    protected DataSource datasource_nodo;
    protected DataSource datasource_pat;
    protected EesTransazioneFactory factoryTransazione;
    protected File workingDir;
    protected String id_ente;
    private SimpleDateFormat sdf;
    private int FETCH_SIZE;
    private int RECORDS4GC;

    public EesSincronizza() {
        id_ente = null;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        FETCH_SIZE = 1000;
        RECORDS4GC = 10000;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        EsicraConf.configura();
        logger = EsicraConf.configuraLogger(getClass());
        if (logger == null) {
            System.out.println("Errore preparazione logger");
        }
        id_ente = System.getProperty("esicra.id_ente");
        datasource_nodo = EsicraConf.getDataSource("esicra.datasource_nodo");
        datasource_pat = EsicraConf.getDataSource("esicra.datasource_pat");
        if (datasource_nodo == null) {
            logger.error("DataSource esicra.datasource_nodo null");
            return;
        }
        if (datasource_pat == null) {
            logger.error("DataSource esicra.datasource_pat null");
            return;
        } else {
            factoryTransazione = new EesTransazioneFactory(datasource_nodo);
            workingDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
            logger.debug("Working Dir =" + workingDir);
            return;
        }
    }

    public static String creaSelectQuery(String tabella) {
        return "SELECT * FROM " + tabella + " WHERE pkid = ? ";
    }

    public String creaInsertQuery(String tabella, int n) {
        StringBuffer sb = new StringBuffer();
        tabella = tabella.toUpperCase();
        sb.append("INSERT INTO " + tabella + " VALUES (?");
        for (int i = 1; i < n; i++)
            sb.append(",?");

        sb.append(")");
        logger.debug("Insert query:" + sb.toString());
        return sb.toString();
    }

    public String creaUpdateQuery(String tabella, Connection con) {
        StringBuffer sb = new StringBuffer();
        String db = System.getProperty("esicra.database");
        // lo schema è uguale allo user in genere
        String schema = System.getProperty("esicra.dbuser");
        if (db.equals("oracle")) {
            tabella = tabella.toUpperCase();
            schema = schema.toUpperCase();
        }
        if(schema==null) schema="esicra";
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = con.getMetaData();
            rs = dbmd.getColumns(null, schema, tabella, null);
            sb.append("UPDATE " + tabella + " SET ");
            for (int i = 1; rs.next(); i++) {
                if (i > 1) {
                    sb.append(",");
                }
                sb.append(rs.getString("COLUMN_NAME") + "=?");
            }

            sb.append(" WHERE pkid=? ");
            rs.close();
        } catch (SQLException sqlexception) {
          logger.error(sqlexception);
        }
        logger.debug("Update query:" + sb.toString());
        return sb.toString();
    }

    public String creaDeleteQuery(String tabella, Connection con) {
        StringBuffer sb = null;
        sb = new StringBuffer("DELETE FROM " + tabella +
                " WHERE dt_mod IS null");
        logger.debug("Delete query:" + sb.toString());
        return sb.toString();
    }

    /**
     * @deprecated Method caricaFlusso is deprecated
     */
    public HashMap caricaFlusso(InputStream is, Connection dbconn)
        throws Exception {
        logger.debug("[caricaFlusso]");
        Connection conn = dbconn;
        ArrayList row = new ArrayList();
        ArrayList lista = new ArrayList();
        ArrayList tab = new ArrayList();
        HashMap header = new HashMap();
        String tabella = "";
        int records_insert = 0;
        int records_update = 0;
        int cols = 0;
        try {
            ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(
                        new BufferedInputStream(is, 10240)));
            Object[] array = (Object[]) ois.readObject();
            int count = 0;
            header = (HashMap) array[0];
            BigDecimal cod_transazione = (BigDecimal) header.get(
                    "cod_transazione");
            for (int n = 1; n < array.length; n++) {
                tab = (ArrayList) array[n];
                cols = ((ArrayList) tab.get(1)).size();
                tabella = (String) tab.get(0);
                PreparedStatement pss = conn.prepareStatement(creaSelectQuery(
                            tabella));
                PreparedStatement psi = conn.prepareStatement(creaInsertQuery(
                            tabella, cols));
                PreparedStatement psu = conn.prepareStatement(creaUpdateQuery(
                            tabella, conn));
                ResultSetMetaData rsmd = pss.getMetaData();
                for (int j = 1; j < tab.size(); j++) {
                    count++;
                    row = (ArrayList) tab.get(j);
                    BigDecimal pkid = (BigDecimal) row.get(0);
                    pss.setObject(1, pkid);
                    ResultSet res = pss.executeQuery();
                    boolean esistente = res.next();
                    int rowLen = row.size();
                    if (esistente) {
                        for (int i = 0; i < rowLen; i++) {
                            Object o = row.get(i);
                            int tipo = rsmd.getColumnType(i + 1);
                            if (o == null) {
                                psu.setNull(i + 1, tipo);
                            } else {
                                psu.setObject(i + 1, o);
                            }
                        }

                        psu.setObject(rowLen + 1, pkid);
                        psu.executeUpdate();
                        records_update++;
                        continue;
                    }
                    for (int i = 0; i < rowLen; i++) {
                        Object o = row.get(i);
                        int tipo = rsmd.getColumnType(i + 1);
                        String tn = rsmd.getColumnTypeName(i + 1);
                        if (o == null) {
                            psi.setNull(i + 1, tipo);
                        } else {
                            psi.setObject(i + 1, o);
                        }
                    }
                    res.close();
                    psi.executeUpdate();
                    records_insert++;
                }

                psu.close();
                psi.close();
            }

            conn.commit();
            header.put("cod_transazione", cod_transazione);
            header.put("records_insert", new Integer(records_insert));
            header.put("records_update", new Integer(records_update));
        } catch (Exception e) {
            logger.debug("cols ->" + cols);
            logger.debug("tabella-->" + tabella);
            logger.debug("(caricaFlusso) " + e);
            e.printStackTrace();
            throw e;
        }
        return header;
    }

    /**
     * @deprecated Method esportaFlussoDati is deprecated
     */
    public HashMap esportaFlussoDati(BigDecimal cod_transazione,
        OutputStream os, Connection conn, boolean completo)
        throws Exception {
        logger.debug("[esportaFlussoDati]");
        ArrayList row = new ArrayList();
        ArrayList lista = new ArrayList();
        ArrayList tab = new ArrayList();
        ArrayList tabelle = new ArrayList();
        HashMap header = new HashMap();
        String msg = "";
        String tabella = "";
        String query = "";
        int n = 0;
        int records = 0;
        try {
            Statement stm = conn.createStatement();
            ResultSet rs;
            for (rs = stm.executeQuery(
                        "SELECT nome_tabella,ordine FROM ser_coordinator WHERE fl_allinea=1  order by ordine ");
                    rs.next(); tabelle.add(rs.getString(1)))
                ;
            rs.close();
            rs = stm.executeQuery(
                    "SELECT MAX(dt_mod) FROM ser_transazione WHERE stato=" +
                    EesTransazione.TERMINATA);
            Timestamp dt_allineamento = null;
            if (rs.next()) {
                dt_allineamento = rs.getTimestamp(1);
            }
            if (dt_allineamento == null) {
                dt_allineamento = new Timestamp(0L);
            }
            rs.close();
            for (int i = 0; i < tabelle.size(); i++) {
                tabella = (String) tabelle.get(i);
                tab = new ArrayList();
                if (tabella != null) {
                    if (!tabella.equals("")) {
                        ;
                    }
                }
                PreparedStatement pstm = null;
                if (completo) {
                    query = "SELECT * FROM " + tabella;
                    pstm = conn.prepareStatement(query);
                } else {
                    query = "SELECT * FROM " + tabella + " WHERE dt_mod >?";
                    pstm = conn.prepareStatement(query);
                    pstm.setTimestamp(1, dt_allineamento);
                }
                rs = pstm.executeQuery();
                ResultSetMetaData rsmt = rs.getMetaData();
                int numColonne = rsmt.getColumnCount();
                tab.add(tabella);
                while (rs.next()) {
                    row = new ArrayList();
                    for (int j = 0; j < numColonne; j++)
                        row.add(rs.getObject(j + 1));

                    tab.add(row);
                    records++;
                }
                if (tab.size() > 1) {
                    lista.add(tab);
                }
            }

            msg = "Informazioni trasferimento Dati";
            header.put("info", msg);
            header.put("cod_transazione", cod_transazione);
            header.put("id_ente", new BigDecimal(id_ente));
            header.put("tabelle", tabelle);
            header.put("records_esportati", Integer.toString(records));
            lista.add(0, header);
            logger.debug(header);
            Object[] array = new Object[lista.size()];
            ObjectOutputStream oout = new ObjectOutputStream(new GZIPOutputStream(
                        new BufferedOutputStream(os, 10240)));
            array = lista.toArray(array);
            oout.writeObject(((Object) (array)));
            oout.flush();
            oout.close();
        } catch (Exception e) {
            logger.debug("QUERY:" + query);
            logger.debug("Tabella: " + tabella + "#record=" + records);
            logger.debug("(esportaFlussoDati) " + e);
            throw e;
        }
        return header;
    }

    /**
     * @deprecated Method esportaFlussoPratiche is deprecated
     */
    public HashMap esportaFlussoPratiche(BigDecimal cod_transazione,
        OutputStream os, Connection conn) throws Exception {
        logger.debug("[esportaFlussoPratiche]");
        ArrayList row = new ArrayList();
        ArrayList lista = new ArrayList();
        ArrayList tab = new ArrayList();
        ArrayList tabelle = new ArrayList();
        HashMap header = new HashMap();
        String query = "";
        String tabella = "";
        String msg = "";
        int records = 0;
        int n = 0;
        try {
            Statement stm = conn.createStatement();
            query = "select nome_tabella,ordine from ser_coordinator where fl_allinea=1 and fl_bidir=1 order by ordine";
            ResultSet rs;
            for (rs = stm.executeQuery(query); rs.next();
                    tabelle.add(rs.getString(1)))
                ;
            rs.close();
            rs = stm.executeQuery("SELECT MAX(dt_mod) FROM ser_transazione");
            Timestamp dt_allineamento = null;
            if (rs.next()) {
                dt_allineamento = rs.getTimestamp(1);
            }
            if (dt_allineamento == null) {
                dt_allineamento = new Timestamp(0L);
                logger.warn("dt_allineamento==null --> dt_allineamto=" +
                    dt_allineamento);
            }
            rs.close();
            for (int i = 0; i < tabelle.size(); i++) {
                tabella = (String) tabelle.get(i);
                tab = new ArrayList();
                if ((tabella == null) || tabella.equals("")) {
                    logger.debug("nome tabella non valido" + tabella);
                }
                Statement pstm = conn.createStatement();
                query = "select * from " + tabella;
                if (dt_allineamento != null) {
                    query = query + " where dt_mod >'" +
                        dt_allineamento.toString() + "'";
                }
                rs = pstm.executeQuery(query);
                ResultSetMetaData rsmt = rs.getMetaData();
                int numColonne = rsmt.getColumnCount();
                tab.add(tabella);
                for (n = 0; rs.next(); n++) {
                    row = new ArrayList();
                    for (int j = 0; j < numColonne; j++)
                        row.add(rs.getObject(j + 1));

                    tab.add(row);
                    records++;
                }

                if (tab.size() > 1) {
                    lista.add(tab);
                }
            }

            msg = "Informazioni trasferimento Pratiche";
            header.put("info", msg);
            header.put("cod_transazione", cod_transazione);
            header.put("tabelle", tabelle);
            header.put("records_esportati", new Integer(records));
            lista.add(0, header);
            logger.debug(header);
            Object[] array = new Object[lista.size()];
            ObjectOutputStream oout = new ObjectOutputStream(new GZIPOutputStream(
                        new BufferedOutputStream(os, 10240)));
            array = lista.toArray(array);
            oout.writeObject(((Object) (array)));
            oout.flush();
            oout.close();
        } catch (Exception e) {
            logger.error("QUERY:" + query);
            logger.error("Tabella: " + tabella + "#records=" + records);
            logger.error("(esportaFlussoPratiche) " + e);
            throw e;
        }
        return header;
    }

    public void pulisciTabelleDati() throws SQLException {
        String listaQuery = "SELECT nome_tabella FROM ser_coordinator WHERE fl_cancellabile=1 AND fl_allinea=1 AND fl_bidir=0";
        logger.debug("listaQuery: " + listaQuery);
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        Statement listaStm = conn.createStatement();
        ResultSet rs = listaStm.executeQuery(listaQuery);
        int cancellati;
        int n;
        for (cancellati = 0; rs.next(); cancellati += n) {
            String nome_tabella = rs.getString(1);
            Statement deleteStm = conn.createStatement();
            String deleteQuery = "DELETE FROM " + nome_tabella +
                " WHERE dt_mod IS NULL";
            n = deleteStm.executeUpdate(deleteQuery);
            if (n > 0) {
                logger.debug("deleteQuery: " + deleteQuery);
                logger.debug("tabella :" + nome_tabella +
                    " records cancellati =" + n);
            }
        }
        rs.close();
        listaStm.close();
        logger.debug("Totale Dati records cancellati =" + cancellati);
        conn.commit();
        conn.close();
    }

    public void pulisciTabellePratiche(BigDecimal id_ente_destinatario)
        throws SQLException {
        String listaQuery = "SELECT nome_tabella FROM ser_coordinator WHERE fl_cancellabile=1 AND fl_bidir=1";
        Connection conn = datasource_nodo.getConnection();
        conn.setAutoCommit(false);
        Statement listaStm = conn.createStatement();
        logger.debug("id_ente_destinatario=" + id_ente_destinatario);
        ResultSet rs = listaStm.executeQuery(listaQuery);
        int cancellati;
        int n;
        for (cancellati = 0; rs.next(); cancellati += n) {
            String nome_tabella = rs.getString(1);
            Statement deleteStm = conn.createStatement();
            String deleteQuery = "DELETE FROM " + nome_tabella +
                " WHERE dt_mod IS null AND id_ente_destinatario=" +
                id_ente_destinatario;
            n = deleteStm.executeUpdate(deleteQuery);
            if (n > 0) {
                logger.debug("deleteQuery: " + deleteQuery);
                logger.debug("tabella :" + nome_tabella +
                    " records cancellati =" + n);
            }
        }
        rs.close();
        listaStm.close();
        logger.debug("Totale Dati records cancellati =" + cancellati);
        conn.commit();
        conn.close();
    }

    protected static String calcHMS(long ms) {
        long s = ms / (long) 1000;
        long h = s / (long) 3600;
        s -= (h * (long) 3600);
        long m = s / (long) 60;
        s -= (m * (long) 60);
        return "ore:" + h + " min:" + m + " sec:" + s;
    }

    public void destroy() {
    }

    public HashMap caricaFlusso(File file) {
        logger.debug("[caricaFlusso]");
        Connection poolconn = null;
        Connection conn = null;
        ArrayList tempClobList = new ArrayList();
        ArrayList row = new ArrayList();
        HashMap header = new HashMap();
        String tabella = "";
        int records_insert = 0;
        int records_update = 0;
        int records = 0;
        int cols = 0;
        int rows = 0;
        String db = System.getProperty("esicra.database");
        logger.debug("db=" + db);
        if (db == null) {
            logger.error("Property esicra.database non impostata");
        }
        PreparedStatement pss = null;
        PreparedStatement psi = null;
        PreparedStatement psu = null;
        ResultSet res = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
                        new GZIPInputStream(new FileInputStream(file), 10240),
                        10240));
            poolconn = datasource_pat.getConnection();
            conn = poolconn.getMetaData().getConnection();
            conn.setAutoCommit(false);
            BigDecimal cod_transazione = (BigDecimal) ois.readObject();
            BigDecimal id_ente = (BigDecimal) ois.readObject();
            logger.debug("COD_TRANSAZIONE = " + cod_transazione);
            logger.debug("ID_ENTE = " + id_ente);
            boolean eof = false;
            while (!eof)
                try {
                    Object obj = ois.readObject();
                    tabella = (String) obj;
                    rows = ((Integer) ois.readObject()).intValue();
                    cols = ((Integer) ois.readObject()).intValue();
                    logger.debug("* tabella = " + tabella);
                    logger.debug("righe = " + rows);
                    logger.debug("colonne = " + cols);
                    pss = conn.prepareStatement(creaSelectQuery(tabella));
                    psi = conn.prepareStatement(creaInsertQuery(tabella, cols));
                    psu = conn.prepareStatement(creaUpdateQuery(tabella, conn));
                    BigDecimal pkid = null;
                    res = null;
                    ResultSetMetaData rsmd = null;
                    Object o = null;
                    for (int j = 0; j < rows; j++) {
                        row.clear();
                        row = (ArrayList) ois.readObject();
                        pkid = (BigDecimal) row.get(0);
                        pss.setBigDecimal(1, pkid);
                        res = pss.executeQuery();
                        rsmd = pss.getMetaData();
                        boolean esistente = res.next();
                        res.close();
                        java.io.StringReader reader = null;
                        if (esistente) {
                            for (int i = 0; i < cols; i++) {
                                o = row.get(i);
                                int tipo = rsmd.getColumnType(i + 1);
                                if (db.equalsIgnoreCase("oracle") &&
                                        (tipo == 2005)) {
                                    /*
                                    logger.debug("update oracle clob");
                                    try {
                                        CLOB tempClob = CLOB.createTemporary(conn,
                                                true, 1);
                                        tempClob.open(1);
                                        Writer tempClobWriter = tempClob.getCharacterOutputStream();
                                        tempClobWriter.write((String) o);
                                        tempClobWriter.flush();
                                        tempClobWriter.close();
                                        tempClob.close();
                                        //logger.debug("index = " + (i + 1));
                                        //logger.debug("tempClob = " + tempClob);
                                        psu.setClob(i + 1, tempClob);
                                    } catch (Exception e) {
                                        logger.error("ERRORE CLOB :" + e);
                                    }
                                    */
                                    logger.error("Database Oracle non supportato da questa versione di Esicra");
                                    continue;
                                }
                                if (o == null) {
                                    psu.setNull(i + 1, tipo);
                                } else {
                                    psu.setObject(i + 1, o);
                                }
                            }

                            psu.setObject(cols + 1, pkid);
                            psu.executeUpdate();
                            records_update++;
                        } else {
                            for (int i = 0; i < cols; i++) {
                                o = row.get(i);
                                int tipo = rsmd.getColumnType(i + 1);
                                String tn = rsmd.getColumnTypeName(i + 1);
                                if (o == null) {
                                    psi.setNull(i + 1, tipo);
                                    continue;
                                }
                                if (db.equalsIgnoreCase("oracle") &&
                                        (tipo == 2005)) {
                                    /*
                                    logger.debug("insert oracle clob");
                                    logger.debug("Connection: " +
                                        conn.getClass().getName());
                                    try {
                                        CLOB tempClob = CLOB.createTemporary(conn,
                                                true, 1);
                                        tempClob.open(1);
                                        Writer tempClobWriter = tempClob.getCharacterOutputStream();
                                        tempClobWriter.write((String) o);
                                        tempClobWriter.flush();
                                        tempClobWriter.close();
                                        tempClob.close();
                                        logger.debug("index = " + (i + 1));
                                        logger.debug("tempClob = " + tempClob);
                                        psi.setClob(i + 1, tempClob);
                                    } catch (SQLException e) {
                                        logger.error(e);
                                    }
                                    */
                                    logger.error("Database Oracle non supportato da questa versione di Esicra");
                                } else {
                                    psi.setObject(i + 1, o);
                                }
                            }

                            psi.executeUpdate();
                            records_insert++;
                        }
                        pss.clearParameters();
                        psu.clearParameters();
                        psi.clearParameters();
                    }

                    logger.debug("* Tabella " + tabella + " caricata ");
                    if (pss != null) {
                        pss.close();
                    }
                    if (psu != null) {
                        psu.close();
                    }
                    if (psi != null) {
                        psi.close();
                    }
                    pss = null;
                    psu = null;
                    psi = null;
                } catch (EOFException ee) {
                    eof = true;
                }
            logger.debug("pre commit");
            conn.commit();
            logger.debug("post commit");
            header.put("cod_transazione", cod_transazione);
            header.put("id_ente", id_ente);
            header.put("records_insert", new Integer(records_insert));
            header.put("records_update", new Integer(records_update));
        } catch (Exception e) {
            logger.error("cols ->" + cols);
            logger.error("rows ->" + rows);
            logger.error("tabella-->" + tabella);
            logger.error("Errore (caricaFlusso) " + e);
            e.printStackTrace();
        } finally {
            try {
                if (pss != null) {
                    pss.close();
                }
                if (psu != null) {
                    psu.close();
                }
                if (psi != null) {
                    psi.close();
                }
                //logger.debug("1");
                if (poolconn != null) {
                    poolconn.close();
                }
                //logger.debug("2");
            } catch (Exception exception1) {
            }
        }
        return header;
    }

    public HashMap preparaFlussoDati(BigDecimal cod_transazione,
        boolean completo) {
        File flusso = null;
        Connection conn = null;
        ResultSet rs = null;
        Statement stm = null;
        PreparedStatement pstm = null;
        PreparedStatement cpstm = null;
        ResultSetMetaData rsmd = null;
        logger.debug("[prepara Flusso Dati]");
        ArrayList tabelle = new ArrayList();
        HashMap header = new HashMap();
        String msg = "";
        String tabella = "";
        String query = "";
        int n = 0;
        int records = 0;
        try {
            if (completo) {
                logger.debug("Sincronizzazione completa");
            } else {
                logger.debug("Sincronizzazione parziale");
            }
            String nomeFile = "esicra-" +
                Long.toString(System.currentTimeMillis());
            flusso = File.createTempFile(nomeFile, null, null);
            flusso.deleteOnExit();
            ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(
                        new GZIPOutputStream(new FileOutputStream(flusso)),
                        10240));
            oout.writeObject(cod_transazione);
            BigDecimal id_ente = new BigDecimal(System.getProperty(
                        "esicra.id_ente"));
            logger.debug("* ID_ENTE =" + id_ente);
            oout.writeObject(id_ente);
            conn = datasource_nodo.getConnection();
            stm = conn.createStatement();
            for (rs = stm.executeQuery(
                        "SELECT nome_tabella,ordine FROM ser_coordinator WHERE fl_allinea=1  order by ordine ");
                    rs.next(); tabelle.add(rs.getString(1)))
                ;
            rs.close();
            rs = stm.executeQuery(
                    "SELECT MAX(dt_mod) FROM ser_transazione WHERE stato=" +
                    EesTransazione.TERMINATA);
            Timestamp dt_allineamento = null;
            if (rs.next()) {
                dt_allineamento = rs.getTimestamp(1);
            }
            if (dt_allineamento == null) {
                dt_allineamento = new Timestamp(0L);
            }
            logger.warn("Data Allineamento = " + dt_allineamento);
            rs.close();
            int numColonne = 0;
            int righe = 0;
            String queryCount = "";
            ArrayList row = new ArrayList();
            for (int i = 0; i < tabelle.size(); i++) {
                pstm = null;
                cpstm = null;
                tabella = (String) tabelle.get(i);
                if ((tabella == null) || tabella.equals("")) {
                    logger.debug("nome tabella non valido" + tabella);
                }
                if (completo) {
                    queryCount = "SELECT count(*) FROM " + tabella;
                    query = "SELECT * FROM " + tabella;
                    cpstm = conn.prepareStatement(queryCount);
                } else {
                    queryCount = "SELECT count(*) FROM " + tabella +
                        " WHERE dt_mod IS NULL OR dt_mod > '" +
                        sdf.format(dt_allineamento) + "'";
                    query = "SELECT * FROM " + tabella +
                        " WHERE dt_mod IS NULL OR dt_mod > '" +
                        sdf.format(dt_allineamento) + "'";
                }
                logger.debug("Query: " + query);
                logger.debug("QueryCount: " + queryCount);
                stm = conn.createStatement();
                rs = stm.executeQuery(queryCount);
                rs.next();
                righe = rs.getInt(1);
                rs.close();
                stm.close();
                stm = null;
                stm = conn.createStatement();
                stm.setFetchSize(FETCH_SIZE);
                rs = stm.executeQuery(query);
                rsmd = rs.getMetaData();
                numColonne = rsmd.getColumnCount();
                if (righe <= 0) {
                    continue;
                }
                logger.debug("Tabella: " + tabella + " Righe :" + righe +
                    " Colonne: " + numColonne);
                oout.writeObject(tabella);
                oout.writeObject(new Integer(righe));
                oout.writeObject(new Integer(numColonne));
                do {
                    if (!rs.next()) {
                        break;
                    }
                    row = new ArrayList();
                    for (int j = 0; j < numColonne; j++)
                        row.add(rs.getObject(j + 1));

                    oout.writeObject(row);
                    if ((++records % RECORDS4GC) == 0) {
                        oout.reset();
                        oout.flush();
                        logger.debug("records = " + records);
                        logger.debug("Memoria in uso  = " +
                            ((Runtime.getRuntime().totalMemory() -
                            Runtime.getRuntime().freeMemory()) / (long) 1024));
                        System.gc();
                        Runtime.getRuntime().gc();
                    }
                } while (true);
                stm.close();
                rs.close();
                rs = null;
            }

            msg = "Informazioni trasferimento Dati";
            header.put("info", msg);
            header.put("cod_transazione", cod_transazione);
            header.put("id_ente", id_ente);
            header.put("tabelle", tabelle);
            header.put("records_esportati", Integer.toString(records));
            header.put("file", flusso);
            logger.debug(header);
            oout.flush();
            oout.close();
        } catch (Exception e) {
            logger.debug("QUERY:" + query);
            logger.debug("Tabella: " + tabella + "#record=" + records);
            logger.debug("(preparaFlussoDati) " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (cpstm != null) {
                    cpstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception exception1) {
            }
        }
        return header;
    }

    public HashMap preparaFlussoPratiche(BigDecimal cod_transazione,
        BigDecimal id_ente_destinatario) {
        String db = System.getProperty("esicra.database");
        Connection conn = null;
        logger.debug("[prepara FlussoPratiche]");
        ArrayList row = new ArrayList();
        ArrayList tabelle = new ArrayList();
        HashMap header = new HashMap();
        String query = "";
        String queryCount = "";
        String tabella = "";
        String msg = "";
        int records = 0;
        int n = 0;
        try {
            String nomeFile = "esicra-" +
                Long.toString(System.currentTimeMillis());
            File flusso = File.createTempFile(nomeFile, null, null);
            flusso.deleteOnExit();
            ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(
                        new GZIPOutputStream(new FileOutputStream(flusso)),
                        10240));
            oout.writeObject(cod_transazione);
            BigDecimal id_ente = new BigDecimal(System.getProperty(
                        "esicra.id_ente"));
            oout.writeObject(id_ente);
            conn = datasource_nodo.getConnection();
            Statement stm = conn.createStatement();
            query = "select nome_tabella,ordine from ser_coordinator where fl_allinea=1 and fl_bidir=1 order by ordine";
            logger.debug("Prepara pratiche :" + query);
            ResultSet rs;
            for (rs = stm.executeQuery(query); rs.next();
                    tabelle.add(rs.getString(1)))
                ;
            rs.close();
            rs = stm.executeQuery(
                    "SELECT MAX(dt_mod) FROM ser_transazione WHERE  stato =" +
                    EesTransazione.TERMINATA + " and id_ente_destinatario=" +
                    id_ente_destinatario);
            Timestamp dt_allineamento = null;
            if (rs.next()) {
                dt_allineamento = rs.getTimestamp(1);
            }
            if (dt_allineamento == null) {
                dt_allineamento = new Timestamp(0L);
            }
            logger.info(" Data Allineamento = " + dt_allineamento);
            rs.close();
            PreparedStatement pstm = null;
            PreparedStatement cpstm = null;
            int numColonne = 0;
            int righe = 0;
            for (int i = 0; i < tabelle.size(); i++) {
                tabella = (String) tabelle.get(i);
                if ((tabella == null) || tabella.equals("")) {
                    logger.debug("nome tabella non valido" + tabella);
                }
                if (dt_allineamento != null) {
                    query = "SELECT * FROM " + tabella +
                        " WHERE ( dt_mod IS NULL OR dt_mod > ? ) AND id_ente_destinatario=" +
                        id_ente_destinatario;
                    queryCount = "SELECT count(*) FROM " + tabella +
                        " WHERE (dt_mod IS NULL OR dt_mod > ? ) AND id_ente_destinatario=" +
                        id_ente_destinatario;
                    cpstm = conn.prepareStatement(queryCount);
                    cpstm.setTimestamp(1, dt_allineamento);
                    pstm = conn.prepareStatement(query);
                    pstm.setTimestamp(1, dt_allineamento);
                }
                rs = cpstm.executeQuery();
                rs.next();
                righe = rs.getInt(1);
                rs.close();
                rs = pstm.executeQuery();
                ResultSetMetaData rsmt = rs.getMetaData();
                numColonne = rsmt.getColumnCount();
                if (righe <= 0) {
                    continue;
                }
                logger.debug("Esportazione pratiche Tabella: " + tabella);
                logger.debug("Colonne:" + numColonne);
                oout.writeObject(tabella);
                oout.writeObject(new Integer(righe));
                oout.writeObject(new Integer(numColonne));
                logger.debug("fine testata");
                if (db.equals("oracle")) {
                    /*
                    logger.debug("oracle");
                    Object o = null;
                    while (rs.next()) {
                        row = new ArrayList();
                        for (int j = 0; j < numColonne; j++) {
                            int tipoSql = rsmt.getColumnType(j + 1);
                            if (tipoSql == 2005) {
                                CLOB clob = (CLOB) rs.getObject(j + 1);
                                ClobDomain clobd = new ClobDomain(clob);
                                row.add(clobd.toString());
                            } else {
                                o = rs.getObject(j + 1);
                                row.add(o);
                            }
                        }

                        oout.writeObject(row);
                        records++;
                    }
                    */
                    logger.error("Database Oracle non supportato da questa versione di Esicra");
                } else {
                    while (rs.next()) {
                        row = new ArrayList();
                        for (int j = 0; j < numColonne; j++)
                            row.add(rs.getObject(j + 1));

                        oout.writeObject(row);
                        records++;
                    }
                }
                logger.debug("Esportazione pratiche Tabella: " + tabella +
                    "Terminata");
                rs.close();
                rsmt = null;
            }

            msg = "Informazioni trasferimento Pratiche";
            header.put("info", msg);
            header.put("cod_transazione", cod_transazione);
            header.put("tabelle", tabelle);
            header.put("records_esportati", new Integer(records));
            header.put("file", flusso);
            logger.debug(header);
            oout.flush();
            oout.close();
        } catch (Exception e) {
            logger.error("QUERY:" + query);
            logger.error("Tabella: " + tabella + "#records=" + records);
            logger.error("(preparaFlussoPratiche) " + e);
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception exception1) {
            }
        }
        return header;
    }

    public void inviaFile(File file, OutputStream out) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(
                        out));
            BufferedInputStream bis = new BufferedInputStream(new GZIPInputStream(
                        new FileInputStream(file)), 10240);
            byte[] buff = new byte[10240];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
                bos.write(buff, 0, bytesRead);
            bis.close();
            bos.close();
        } catch (IOException e) {
            logger.error("Errore invio File " + e.getMessage());
        }
    }

    public File riceviFile(InputStream in) {
        String nomeFile = "esicra-" +
            Long.toString(System.currentTimeMillis());
        File flussoOut = null;
        try {
            flussoOut = File.createTempFile(nomeFile, null, null);
            flussoOut.deleteOnExit();
            BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(
                        new FileOutputStream(flussoOut)), 10240);
            BufferedInputStream bis = new BufferedInputStream(new GZIPInputStream(
                        in));
            byte[] buff = new byte[10240];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
                bos.write(buff, 0, bytesRead);
            bis.close();
            bos.close();
        } catch (IOException e) {
            logger.error("Errore ricezione File " + e.getMessage());
        }
        return flussoOut;
    }
}
