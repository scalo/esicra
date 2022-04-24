package it.saga.egov.esicra.importazione;

import it.saga.siscotel.db.AnaSoggetto;
import it.saga.siscotel.db.AnaSoggettoProv;
import it.saga.siscotel.db.AnaRappresentante;
import it.saga.siscotel.db.SerProvenienza;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import java.util.Hashtable;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public class DbCommon {
    
    /** sottospecie di cache per la provenienza */
    public static  Hashtable serProvenienzaCache = new Hashtable();
     
    // cerca row dato il codice 
    public static Object cercaRiga(String classname, String campo, String codice) {
        //§ TODO controllo sui parametri di ingresso
        Session  session = HibernateUtil.currentSession();
        Object db_Row = null;
        Query query = session.createQuery("FROM " + classname + " WHERE " +
                campo + "='" + codice + "'");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        List list = query.list();
  
        //System.out.println("SIZE="+list.size());
        if (list.size() > 0) {
            db_Row = list.get(0);
        }
        return db_Row;
    }

    public static Object cercaRigaCorrente(String classname, String campo,
        String codice) {
        Session session = HibernateUtil.currentSession();
        Object db_Row = null;
        Query query = session.createQuery("FROM " + classname + " WHERE " +
                campo + "='" + codice + "' and dt_mod is not null");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        //§ istanzia pojo e recupera
        List list = query.list();
        //System.out.println("SIZE="+list.size());
        if (list.size() > 0) {
            db_Row = list.get(0);
        }
        return db_Row;
    }
    
 /**  
   * Annulla Records storici  
   * 
   * @param classname 
   * @param id 
   * @param pkid 
   * @param dtmod 
   */
   public static void annullaRecordStorico(String classname, String id,
        BigDecimal pkid, Timestamp dtmod) {
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("FROM " + classname +
                " WHERE "+id+"=" + pkid + " and dt_mod is not null");
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        Iterator ite = query.iterate();
        while (ite.hasNext()) {
            Object obj = ite.next();
            BigDecimal bd = (BigDecimal) HibernateUtil.getAttribute(obj, id);
            if (HibernateUtil.getAttribute(obj, "DtMod") != null) {
                if (!bd.equals(pkid)) {
                    long dt1 = ((java.sql.Timestamp) HibernateUtil.getAttribute(obj,
                            "DtMod")).getTime();
                    long dt2 = dtmod.getTime();
                    if (dt1 != dt2) {
                        HibernateUtil.setAttribute(obj, "DtMod", null);
                    }
                }
            }
        }
    }
    
  /*
  public static void aggiornaProvenienzaSogg(AnaSoggetto sogg, String  codProvenienza,Timestamp dtmod)   
         throws ImportazioneException {
      
      AnaSoggettoProv sp = null;
      SerProvenienza prov = null;
      
      Session session = HibernateUtil.currentSession();
      
      if (sogg != null) {
          // cerca provenienza
          prov = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","codProvenienza",codProvenienza);
          sp =(AnaSoggettoProv) cercaProvenienza(sogg,prov);
          if(prov==null){
            System.out.println("Codice provenienza non valido");
          }
          if(sp==null) {
              //res = (AnaSoggettoProvViewRowImpl) (((AnaSoggettoProvViewImpl) anaProvenienzaView).createRow(new BigDecimal(step)));                
              sp = new AnaSoggettoProv();
              sp.setAnaSoggetto(sogg);
              sp.setSerProvenienza(prov);
              sp.setDtMod(dtmod);
          } else{
             // aggiorna solo dt_mod
             sp.setDtMod(dtmod);
          }
    }
    session.saveOrUpdate(sp);
  }
  */
  /**
   * 
   * @deprecated metodo poco performante
   * 
   * @param sg 
   * @param prov 
   * @return 
   */
   public static  AnaSoggettoProv cercaProvenienza(AnaSoggetto sg,SerProvenienza prov){
      //SerProvenienza prov = (SerProvenienza) DbUtilita.cercaSerProvenienza("it.saga.siscotel.db.SerProvenienza","codProvenienza",cod_provenienza);
      //AnaSoggetto sg = (AnaSoggetto) session.get(AnaSoggetto.class,fkid_soggetto);
      Session session = HibernateUtil.currentSession();
      String q = "FROM AnaSoggettoProv sog  WHERE sog.anaSoggetto = ? "+
        "AND sog.serProvenienza = ? ";
      Query query = session.createQuery(q);
      query.setCacheable(true);
      query.setCacheMode(CacheMode.NORMAL);
      query.setEntity(0,sg);
      query.setEntity(1,prov);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      if(ite.hasNext())
        return (AnaSoggettoProv) ite.next();
      return null;
  }
  
   public static  AnaSoggettoProv cercaProvenienza(BigDecimal sogFkId,BigDecimal provFkId){
      Session session = HibernateUtil.currentSession();
      String q = "FROM AnaSoggettoProv sog  WHERE sog.anaSoggetto.pkid = ? "+
        "AND sog.serProvenienza.pkid = ? ";
      Query query = session.createQuery(q);
      query.setCacheable(true);
      query.setCacheMode(CacheMode.NORMAL);
      query.setBigDecimal(0,sogFkId);
      query.setBigDecimal(1,provFkId);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      if(ite.hasNext())
        return (AnaSoggettoProv) ite.next();
      return null;
  }
  
  
  public static AnaRappresentante cercaRappresentante(AnaSoggetto sog,AnaSoggetto rap){
      Session session = HibernateUtil.currentSession();
      String q = "FROM AnaRappresentante sog  WHERE sog.anaSoggetto = ? "+
        "AND sog.anaRappresentante = ? ";
      Query query = session.createQuery(q);
      query.setCacheable(true);
      query.setCacheMode(CacheMode.NORMAL);
      query.setEntity(0,sog);
      query.setEntity(1,rap);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      if(ite.hasNext())
        return (AnaRappresentante) ite.next();
      return null;
  }
  
  public static  void aggiornaProvenienzaSogg(AnaSoggetto sogg, String  codProvenienza,Timestamp dtmod , String id_ente)   
         throws ImportazioneException {
      Session session = HibernateUtil.currentSession();
      AnaSoggettoProv sp = null;
      SerProvenienza prov = null;
        
      if (sogg != null) {
          // TODO  da ottimizzare provenienza
          // cerca provenienza
          prov = (SerProvenienza) serProvenienzaCache.get(codProvenienza);
          if (prov==null){
            prov = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","codProvenienza",codProvenienza);
            serProvenienzaCache.put(codProvenienza,prov);
          }
          sp =(AnaSoggettoProv) DbCommon.cercaProvenienza(sogg.getPkid(),prov.getPkid());
          //sp =(AnaSoggettoProv) DbCommon.cercaProvenienza(sogg,prov);
          if(prov==null){
            System.out.println("Codice provenienza non valido");
          }
          if(sp==null) {
              //res = (AnaSoggettoProvViewRowImpl) (((AnaSoggettoProvViewImpl) anaProvenienzaView).createRow(new BigDecimal(step)));                
              sp = new AnaSoggettoProv();
              sp.setAnaSoggetto(sogg);
              sp.setSerProvenienza(prov);
              sp.setIdEnte(new Integer(id_ente));
              sp.setDtMod(dtmod);
          } else{
             // aggiorna solo dt_mod
             sp.setDtMod(dtmod);
          }
    }
    session.saveOrUpdate(sp);
  }
  
}
