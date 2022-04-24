package it.saga.siscotel.db.test;

import java.util.*;
import org.hibernate.*;
import it.saga.siscotel.db.SerEsicraInfo;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class SerEsicraInfoTest  {

  public static void main(String[] args) {
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from SerEsicraInfo";
      Query query = session.createQuery(q);
      Iterator ite = query.iterate();
      SerEsicraInfo tc = null;
      while(ite.hasNext()){
        tc = (SerEsicraInfo) ite.next();
        System.out.println(tc.getNomedb());
        System.out.println(tc.getVersione());
      }
      
      HibernateUtil.closeSession();
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
}