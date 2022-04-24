package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class AnaRappresentanteTest  {
  
  public static void testList(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from AnaRappresentante where fkid_soggetto=240008240";
      Query query = session.createQuery(q);
      System.out.println(query.getQueryString());
      Iterator ite = query.iterate();
      AnaRappresentante tc = null;
      while(ite.hasNext()){
        tc = (AnaRappresentante) ite.next();
        //System.out.println("TC = "+tc);
        if(tc.getAnaSoggetto()!=null && tc.getAnaRappresentante()!=null){
          System.out.println("** " + tc.getPkid()+" "+tc.getAnaSoggetto().getDenominazione());
        }
      }
      //tx.commit();
      HibernateUtil.closeSession();
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  public static void main(String[] args) {
    System.setProperty("id_ente","8240");
    testList();
  }

}