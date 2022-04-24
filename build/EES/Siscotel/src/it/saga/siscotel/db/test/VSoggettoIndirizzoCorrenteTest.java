package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.AnaSoggetto;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class VSoggettoIndirizzoCorrenteTest  {
  
  public VSoggettoIndirizzoCorrenteTest() {
  
  }
  
  public static void testList(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from VSoggettoIndirizzoCorrente";
      Query query = session.createQuery(q);
      Iterator ite = query.iterate();
      VSoggettoIndirizzoCorrente tc = null;
      while(ite.hasNext()){
        tc = (VSoggettoIndirizzoCorrente) ite.next();
        System.out.println(tc.getNome()+" "+tc.getCognome()+" "+tc.getDesArea()+" "+tc.getDesComune());
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
    VSoggettoIndirizzoCorrenteTest.testList();
  }
  
}