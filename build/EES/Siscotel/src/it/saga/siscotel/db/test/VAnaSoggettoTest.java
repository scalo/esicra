package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.AnaSoggetto;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class VAnaSoggettoTest  {
  public VAnaSoggettoTest() {
  }

  public static void testInsert(){
  
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    AnaSoggetto sogg = new AnaSoggetto();
    
    session.save(sogg);
    tx.commit();
    HibernateUtil.closeSession();
  }
  
  public static void testList(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from AnaSoggetto";
      Query query = session.createQuery(q);
      Iterator ite = query.iterate();
      AnaSoggetto tc = null;
      while(ite.hasNext()){
        tc = (AnaSoggetto) ite.next();
        System.out.println(tc.getNome()+" "+tc.getCognome()+" "+tc.getDenominazione());
      }
      //session.save(princess);
      //tx.commit();
      HibernateUtil.closeSession();
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  public static void main(String[] args) {
    System.setProperty("id_ente","8240");
    //SoggettoTest.testInsert();
    VAnaSoggettoTest.testList();
  }
  
}