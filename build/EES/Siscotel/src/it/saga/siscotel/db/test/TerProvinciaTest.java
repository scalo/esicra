package it.saga.siscotel.db.test;

import it.saga.siscotel.db.*;
import java.util.*;
import org.hibernate.*;
import it.saga.siscotel.db.TerProvincia;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class TerProvinciaTest  {
  
  public TerProvinciaTest() {
  }
  
  public static void main(String[] args) {
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from TerProvincia";
      Query query = session.createQuery(q);
      Iterator ite = query.iterate();
      TerProvincia tc = null;
      while(ite.hasNext()){
        tc = (TerProvincia) ite.next();
        System.out.println(tc.getDesProvincia());
      }
      
      //session.save(princess);
      //tx.commit();
      HibernateUtil.closeSession();
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
}