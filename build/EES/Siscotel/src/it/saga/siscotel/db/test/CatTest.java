package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import java.math.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class CatTest  {
  public CatTest() {
  }
  
  public static void testInsert(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    Cat cat = new Cat();
    cat.setName("Camillo");
    cat.setSex('M');
    cat.setWeight(9.9f);
    session.save(cat);
    tx.commit();
    HibernateUtil.closeSession();
  }
  
  public static void testList(){
    Session session = HibernateUtil.currentSession();
    try{
      String q = "from Cat";
      Query query = session.createQuery(q);
      Iterator ite = query.iterate();
      Cat tc = null;
      while(ite.hasNext()){
        tc = (Cat) ite.next();
        System.out.println(tc.getName());
      }
      HibernateUtil.closeSession();
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  public static void testKittens(){
    Session session = HibernateUtil.currentSession();
    Cat cat = new Cat();
    cat = (Cat) session.load("it.saga.siscotel.db.test.Cat",new BigDecimal("0"));
    System.out.println(cat.getKittens());
  }
  
  public static void main(String[] args) {
    System.setProperty("id_ente","8240");
    //CatTest.testInsert();
    //CatTest.testList();
    CatTest.testKittens();
  }
  
}