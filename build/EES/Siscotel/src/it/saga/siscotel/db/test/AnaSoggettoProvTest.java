package it.saga.siscotel.db.test;

import it.saga.egov.esicra.importazione.DbUtilita;
import java.math.BigDecimal;
import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;


public class AnaSoggettoProvTest  {
  
  public static void testList(){
    Session session = HibernateUtil.currentSession();
    try{
      SerProvenienza prov = (SerProvenienza) DbUtilita.cercaSerProvenienza("it.saga.siscotel.db.SerProvenienza","codProvenienza","10");
      AnaSoggetto sg = (AnaSoggetto) session.get(AnaSoggetto.class,new BigDecimal("20008240"));
      System.out.println("sg "+sg);
      System.out.println("prov "+prov);
      String q = "FROM AnaSoggettoProv sog  WHERE sog.anaSoggetto = ? "+
        "AND sog.serProvenienza = ? ";
      Query query = session.createQuery(q);
      query.setEntity(0,sg);
      query.setEntity(1,prov);
      //System.out.println("LIST="+query.list().size());
      Iterator ite = query.iterate();
      AnaSoggettoProv tc = null;
      while(ite.hasNext()){
        tc = (AnaSoggettoProv) ite.next();
        if(tc!=null){
          System.out.println("** " +tc.getPkid()+" "+tc.getSerProvenienza().getCodProvenienza()+" "+tc.getAnaSoggetto().getNome());
        }
      }
      HibernateUtil.closeSession();
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  public static void testInsert(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    AnaSoggetto sg = new AnaSoggetto();
    sg.setNome("Camillo");
    sg.setCognome("Cavour");
    sg.setDtMod(new Date(System.currentTimeMillis()));
    session.save(sg);
    tx.commit();
    HibernateUtil.closeSession();
  }
  
  public static void main(String[] args) {
    System.setProperty("id_ente","8240");
    testList();
  }

}