package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class AnaSoggettoTest  {
  
  public static void testList(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from AnaSoggetto where pkid=20008240";
      Query query = session.createQuery(q);
      System.out.println(query.getQueryString());
      Iterator ite = query.iterate();
      AnaSoggetto tc = null;
      while(ite.hasNext()){
        tc = (AnaSoggetto) ite.next();
        //System.out.println("TC = "+tc);
        if(tc!=null){
          System.out.println("** " + tc.getPkid()+" "+tc.getDenominazione());
        }
      }
      //tx.commit();
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
    //testList();
    testInsert();
  }

}