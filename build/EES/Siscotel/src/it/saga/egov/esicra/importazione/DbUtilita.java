package it.saga.egov.esicra.importazione;

import it.saga.egov.esicra.EsicraConf;
import it.saga.egov.esicra.importazione.ImportazioneException;
import it.saga.siscotel.db.AnaSoggetto;
import it.saga.siscotel.db.SerProvenienza;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import org.hibernate.*;
import java.util.*;

public class DbUtilita{
    //private Object am;
    //private Object utilitasoggettoview;
    //private SessionFactory sessionFactory 
    
    public DbUtilita(Object AM) {
        //utilitasoggettoview = null;
        //am = AM;
        //utilitasoggettoview = am.findViewObject("CodSoggettoView");
        //sessionFactory=HibernateFactory.currentSessionFactory();
    }

    public DbUtilita() {
        //utilitasoggettoview = null;
    }
    
    public static void remove(Object obj){
      HibernateUtil.remove(obj);
    }
    
    // ritorna la riga corrente associata ad un codice
    public Object cercaRigaCorrente(String  classname, String campo ,String codice){
        //§ TODO controllo sui parametri di ingresso
        Object db_Row = null;
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("from "+classname +" where "+campo+"='"+codice+"' and dt_mod is not null" );
        //§ istanzia pojo e recupera
        List list = query.list();
        System.out.println("SIZE="+list.size());
        if(list.size()>0){
          db_Row = list.get(0); 
        }
        /*
        Row res = null;
        Row db_Row = null;
        Date maxDtFin = Date.valueOf("1900-01-01");
        try {
            view.setWhereClauseParam(0, codice);
            view.executeQuery();
            view.setWhereClause("");
        } catch (Exception e) {
            throw new DBException("Errore nella query su vista " +
                view.getFullName() + " " + e);
        }
        do {
            if (!view.hasNext()) {
                break;
            }
            res = view.next();
            Date dbData = (Date) res.getAttribute("DtFin");
            if ((maxDtFin == null) && (dbData == null)) {
                throw new DBException(
                    "Record corrente duplicato nel database - codice:" +
                    codice);
            }
            if ((dbData == null) ||
                    ((maxDtFin != null) && dbData.after(maxDtFin))) {
                maxDtFin = dbData;
                db_Row = res;
            }
        } while (true);
        */
        return db_Row;
    }
    
    public static  BigDecimal cercaPkid(String classname,String campo,String valore){
      BigDecimal bd  = null;
      Session session = HibernateUtil.currentSession();
      String q = "from "+classname +" where "+campo+"= ?";
      Query query = session.createQuery(q);
      System.out.println("query = "+q);
      query.setString(0,valore);
      //§ istanzia pojo e recupera
      List list = query.list();
      System.out.println("SIZE="+list.size());
      if(list.size()>0){
        Object obj = list.get(0); 
        bd= new BigDecimal((String) HibernateUtil.getAttribute(obj,campo));
      }
      return bd;
    }
    
    public static  Object cercaSerProvenienza(String classname,String campo,String valore){
      Object  obj  = null;
      Session session = HibernateUtil.currentSession();
      String q = "from "+classname +" where "+campo+"= ?";
      Query query = session.createQuery(q);
      System.out.println("query = "+q);
      query.setString(0,valore);
      List list = query.list();
      if(list.size()>0){
        obj = list.get(0); 
      }
      return obj;
    }
    
    public static Object cercaProvenienza(BigDecimal fkid_soggetto,String cod_provenienza){
      Session session = HibernateUtil.currentSession();
      SerProvenienza prov = (SerProvenienza) DbUtilita.cercaSerProvenienza("it.saga.siscotel.db.SerProvenienza","codProvenienza",cod_provenienza);
      AnaSoggetto sg = (AnaSoggetto) session.get(AnaSoggetto.class,fkid_soggetto);
      String q = "FROM AnaSoggettoProv sog  WHERE sog.anaSoggetto = ? "+
        "AND sog.serProvenienza = ? ";
      Query query = session.createQuery(q);
      query.setEntity(0,sg);
      query.setEntity(1,prov);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      if(ite.hasNext())
        return ite.next();
      return null;
    }
    
    // ritorna pkid di un soggetto dato il codice e l'intervallo temporale
    public BigDecimal cercaSoggetto(String codice, Date data)
        throws Exception {
        BigDecimal res = null;
        /*
        Date maxDtFin = Date.valueOf("1900-01-01");
        Row viewRow = null;
        if (codice == null) {
            return null;
        }
        if (utilitasoggettoview != null) {
            if (data == null) {
                viewRow = cercaRigaCorrente(utilitasoggettoview, codice);
                if (viewRow != null) {
                    res = (BigDecimal) viewRow.getAttribute("Pkid");
                }
            } else {
                utilitasoggettoview.setWhereClauseParam(0, codice);
                utilitasoggettoview.executeQuery();
                utilitasoggettoview.setWhereClause("");
                do {
                    if (!utilitasoggettoview.hasNext()) {
                        break;
                    }
                    viewRow = utilitasoggettoview.next();
                    Date dbDataFin = (Date) viewRow.getAttribute("DtFin");
                    Date dbDataIni = (Date) viewRow.getAttribute("DtIni");
                    if ((maxDtFin == null) && (dbDataFin == null)) {
                        throw new DBException(
                            "Record corrente duplicato nel database: " +
                            codice);
                    }
                    if (dbDataIni.before(data) &&
                            ((dbDataFin == null) || dbDataFin.equals(data) ||
                            dbDataFin.after(data))) {
                        maxDtFin = dbDataFin;
                        res = (BigDecimal) viewRow.getAttribute("Pkid");
                    }
                } while (true);
            }
        } else {
            throw new DBException("ViewObject Soggetto non trovato");
        }
        */
        return res;
    }
    
    // cerca row dato il codice 
   public Object cercaRiga(String classname, String campo ,String codice){
        //§ TODO controllo sui parametri di ingresso
        Object db_Row = null;
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("from "+classname +" where "+campo+"='"+codice+"' and dt_mod is not null" );
        //§ istanzia pojo e recupera
        List list = query.list();
        System.out.println("SIZE="+list.size());
        if(list.size()>0){
          db_Row = list.get(0);
        }
        /*
          if (codice == null) {
              return null;
          }
          caricaVista(view, codice);
          Row row = view.next();
          if (row != null) {
              return row;
          } else {
              return null;
          }
        */
        return db_Row;
    }
    
    
    // Imposta codice nella where della view
    public void caricaVista(Object view, String codice) {
      /*
        view.setWhereClauseParam(0, codice);
        view.executeQuery();
        view.setWhereClause("");
      */
    }
    
    // Imposta where sulla view
    public static void caricaVista(Object view, String nomeCodice,
        BigDecimal pkid) {
        /*
        view.setWhereClause(nomeCodice + "='" + pkid + "' ");
        view.executeQuery();
        view.setWhereClause("");
        */
    }
    
    // cerca codice , dato il nome dell' attributo e il valore
    public BigDecimal cercaCodice(Object view, String nomeCodice,
        String valCodice) {
        BigDecimal cod=null;
        /*
        view.setWhereClause("upper(" + nomeCodice + ") = upper('" + valCodice +
            "')");
        view.executeQuery();
        view.setWhereClause("");
        Row row = view.next();
        if (row != null) {
            return (BigDecimal) row.getAttribute("Pkid");
        } else {
            return null;
        }
        */
        return cod;
    }
    
    // cerca codice relativo ad una data
    public BigDecimal cercaCodiceData(Object view, String codice, Date Data)
        throws Exception {
        BigDecimal res = null;
        /*
        Row viewRow = null;
        Date maxDtFin = Date.valueOf("1900-01-01");
        if (codice == null) {
            return null;
        }
        if (Data == null) {
            viewRow = cercaRigaCorrente(view, codice);
            if (viewRow != null) {
                res = (BigDecimal) viewRow.getAttribute("Pkid");
            }
        } else {
            caricaVista(view, codice);
            do {
                if (!view.hasNext()) {
                    break;
                }
                viewRow = view.next();
                Date dbDataFin = (Date) viewRow.getAttribute("DtFin");
                Date dbDataIni = (Date) viewRow.getAttribute("DtIni");
                if ((maxDtFin == null) && (dbDataFin == null)) {
                    throw new DBException(
                        "Record corrente duplicato nel database: " + codice);
                }
                if (dbDataIni.before(Data) &&
                        ((dbDataFin == null) || dbDataFin.equals(Data) ||
                        dbDataFin.after(Data))) {
                    maxDtFin = dbDataFin;
                    res = (BigDecimal) viewRow.getAttribute("Pkid");
                }
            } while (true);
        }
        */
        return res;
    }
    
    /**
     * 
     * Annulla tutti i records che non hanno pkid o la dt_mod specificata,
     * impostandone la dt_mod a null
     * 
     * @param classname nome del pojo
     * @param pkid pkid del record corrente
     * @param codice nome dell'attributo codice
     * @param valore valore del codice
     * @param dtmod  dtmod del record corrente
     * 
     */
    public void annullaRecordStorico(String classname, BigDecimal pkid,String codice,
        String valore,Timestamp dtmod) {
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("from "+classname +" where "+codice+"='"+valore+"'" );
        Iterator ite = query.iterate();
        while(ite.hasNext()){
          Object cur = ite.next();
          BigDecimal pk = (BigDecimal) HibernateUtil.getAttribute(cur,"pkid");
          Timestamp ts = (Timestamp) HibernateUtil.getAttribute(cur,"dtmod");
          if(ts !=null && !pkid.equals(pk)){
            long dt1 = ts.getTime();
            long dt2 = dtmod.getTime();
            if (dt1 != dt2) {
              HibernateUtil.setAttribute(cur,"dtmod",null);
            }
          }
        }
        /*
        Row res = null;
        do {
            if (!view.hasNext()) {
                break;
            }
            res = view.next();
            if (res.getAttribute("DtMod") != null) {
                if (!res.getAttribute("Pkid").equals(pkid)) {
                    long dt1 = ((java.sql.Timestamp) res.getAttribute("DtMod")).getTime();
                    long dt2 = dtmod.getTime();
                    if (dt1 != dt2) {
                        res.setAttribute("DtMod", null);
                    }
                }
            }
        } while (true);
        */
    }
    
    // aggiorna record storico secondo un algoritmo cremonese
    //
    public void aggiornaStorico(Object dbRow, Object newRow, Timestamp dtMod)
        throws Exception {
        /*
        try {
            if (dbRow != null) {
                Date dbDtFin = (Date) dbRow.getAttribute("DtFin");
                Date newDtFin = (Date) newRow.getAttribute("DtFin");
                if (!dbRow.getAttribute("DtMod").equals(dtMod)) {
                    copyRow(newRow, dbRow);
                    newRow.remove();
                } else {
                    if ((newDtFin == null) ||
                            ((dbDtFin != null) && newDtFin.after(dbDtFin))) {
                        swapRow(dbRow, newRow);
                    }
                }
            }
        } catch (Exception e) {
            throw new DBException("Errore Aggiornamento dello storico " + e);
        }*/
    }
    
    // Attenzione annulla tutto il view object imponendo a null
    // la dt_mod
    public void annullaRecord(Object view, Timestamp dtmod) {
        /*
        Object res = null;
        do {
            if (!view.hasNext()) {
                break;
            }

            res = view.next();
            if (dtmod != null) {
                if (res.getAttribute("DtMod") != null) {
                    long dt1 = ((java.sql.Timestamp) res.getAttribute("DtMod")).getTime();
                    long dt2 = dtmod.getTime();
                    if (dt1 != dt2) {
                        res.setAttribute("DtMod", null);
                    }
                }
            }
        } while (true);
        */
    }
    
    // recupera pkid dato il codice di provenienza
    public BigDecimal getPkidProvenienza(String cod_provenienza)
        throws ImportazioneException {
        BigDecimal pkid=null;
        /*
        SerProvenienzaViewRowImpl provenienzaRow;
        ViewObject serProvenienzaView = am.findViewObject("SerProvenienzaView");
        if (serProvenienzaView == null) {
            throw new ImportazioneException(
                "SerProvenienzaView: ViewObject non creato");
        }
        serProvenienzaView.setWhereClause("cod_provenienza = '" +
            cod_provenienza + "'");
        serProvenienzaView.executeQuery();
        serProvenienzaView.setWhereClause("");
        if (serProvenienzaView.hasNext()) {
            provenienzaRow = (SerProvenienzaViewRowImpl) serProvenienzaView.next();
            return provenienzaRow.getPkid();
        } else {
            return null;
        }
        */
        return pkid;
    }
    
    /*
      Aggiorna la provenienza di un soggetto , passando il codice provenienza
      0:
      1:
    */
    
    public int aggiornaProvenienzaSogg(BigDecimal fkidSogg,
        String cod_provenienza, int step, Timestamp dtmod)
        throws ImportazioneException {
        int res=0;
        /*
        BigDecimal pkidProvenienza;
        ViewObject anaProvenienzaView = am.findViewObject("AnaSoggettoProvView");
        if (anaProvenienzaView == null) {
            throw new ImportazioneException(
                "AnaSoggettoViewProvView: ViewObject non creato");
        }

        AnaSoggettoProvViewRowImpl res = null;

        pkidProvenienza = getPkidProvenienza(cod_provenienza);

        if (pkidProvenienza != null) {
            anaProvenienzaView.setWhereClause("fkid_soggetto ='" + fkidSogg +
                "' and fkid_provenienza = '" + pkidProvenienza + "'");
            anaProvenienzaView.executeQuery();
            anaProvenienzaView.setWhereClause("");
            if (!anaProvenienzaView.hasNext()) {
                res = (AnaSoggettoProvViewRowImpl) (((AnaSoggettoProvViewImpl) anaProvenienzaView).createRow(new BigDecimal(
                            step)));
                res.setFkidSoggetto(fkidSogg);
                res.setDtMod(dtmod);
                res.setFkidProvenienza(pkidProvenienza);
            } else {
                res = (AnaSoggettoProvViewRowImpl) anaProvenienzaView.next();
                res.setDtMod(dtmod);
            }
            return 1;
        } else {
            return 0;
        }
        */
        return res;
    }
    
    /*
      crea o aggiorna  la provenienza di un soggetto
    */
    public int aggiornaProvenienzaSogg(BigDecimal fkidSogg,
        BigDecimal fkidProvenienza, int step, Timestamp dtmod)
        throws ImportazioneException {
        int res=0;
        /*
        ViewObject anaProvenienzaView = am.findViewObject("AnaSoggettoProvView");
        if (anaProvenienzaView == null) {
            throw new ImportazioneException(
                "AnaSoggettoViewProvView: ViewObject non creato");
        }

        AnaSoggettoProvViewRowImpl res = null;

        if (fkidProvenienza != null) {
            anaProvenienzaView.setWhereClause("fkid_soggetto ='" + fkidSogg +
                "' and fkid_provenienza = '" + fkidProvenienza + "'");
            anaProvenienzaView.executeQuery();
            anaProvenienzaView.setWhereClause("");
            if (!anaProvenienzaView.hasNext()) {
                res = (AnaSoggettoProvViewRowImpl) (((AnaSoggettoProvViewImpl) anaProvenienzaView).createRow(new BigDecimal(
                            step)));
                res.setFkidSoggetto(fkidSogg);
                res.setDtMod(dtmod);
                res.setFkidProvenienza(fkidProvenienza);
            } else {
                res = (AnaSoggettoProvViewRowImpl) anaProvenienzaView.next();
                res.setDtMod(dtmod);
            }
            return 1;
        } else {
            return 0;
        }
        */
        return res;
    }
    
    /*
      rimuove la provenienza di un soggetto
     */
    public int eliminaProvenienzaSogg(BigDecimal fkidSogg,
        BigDecimal fkidProvenienza, int step, Timestamp dtmod)
        throws ImportazioneException {
        int res=0;
        /*
        ViewObject anaProvenienzaView = am.findViewObject("AnaSoggettoProvView");
        if (anaProvenienzaView == null) {
            throw new ImportazioneException(
                "AnaSoggettoViewProvView: ViewObject non creato");
        }

        AnaSoggettoProvViewRowImpl res = null;

        if (fkidProvenienza != null) {
            anaProvenienzaView.setWhereClause("fkid_soggetto ='" + fkidSogg +
                "' and fkid_provenienza = '" + fkidProvenienza + "'");
            anaProvenienzaView.executeQuery();
            anaProvenienzaView.setWhereClause("");
            if (anaProvenienzaView.hasNext()) {
                res = (AnaSoggettoProvViewRowImpl) anaProvenienzaView.next();
                res.setDtMod(null);
            }
            return 1;
        } else {
            return 0;
        }
        */
        return res;
    }
    
    // USATA ???
    public static void getInfoDB() {
        /*
        EsicraConf.configura();
        ApplicationModule amDB = null;
        amDB = EsicraConf.creaApplicationModule(
                "it.saga.egov.esicra.bc4j.VersioneModule");
        if (amDB == null) {
            System.out.println("Application Module non trovato");
        } else {
            ViewObject esicraview = null;
            esicraview = amDB.findViewObject("SerEsicraInfoView");
            if (esicraview == null) {
                System.out.println("Vista non trovata");
            } else {
                SerEsicraInfoViewRowImpl row;
                for (; esicraview.hasNext();
                        System.out.println(" - Des Ente: " + row.getDesEnte())) {
                    row = (SerEsicraInfoViewRowImpl) esicraview.next();
                    System.out.print("NomeDB: " + row.getNomedb());
                    System.out.print(" - Versione: " + row.getVersione());
                    System.out.print(" - DB Engine: " + row.getDbEngine());
                    System.out.print(" - Servlet Engine: " +
                        row.getServletEngine());
                    System.out.print(" - Host: " + row.getHost());
                    System.out.print("-  Id Ente: " + row.getIdEnte());
                }
            }
        }
        amDB.getTransaction().disconnect(false);
        */
    }
    
    /*
     *  ritorna codice istat
     */
    public BigDecimal getIstat() {
        BigDecimal istat= null;
        /*
        ViewObject infodbview = null;
        infodbview = am.findViewObject("SerEsicraInfoView");
        if (infodbview != null) {
            if (infodbview.hasNext()) {
                SerEsicraInfoViewRowImpl row = (SerEsicraInfoViewRowImpl) infodbview.next();
                return row.getCodIstat();
            } else {
                return new BigDecimal("0");
            }
        } else {
            System.out.println("vista non trovata");
            return new BigDecimal("0");
        }
        */
        return istat;
    }

    public static void main(String[] args) {
        getInfoDB();
    }

    public class DBException extends Exception {
        public DBException() {
            new DBException("Errore");
        }

        public DBException(String message) {
            super(message);
        }
    }
}
