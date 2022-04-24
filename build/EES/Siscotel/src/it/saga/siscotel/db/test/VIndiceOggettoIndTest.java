package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class VIndiceOggettoIndTest  {

  public static void testList(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    try{
      String q = "from VIndiceOggettoInd";
      Query query = session.createQuery(q);
      System.out.println(query.getQueryString());
      Iterator ite = query.iterate();
      VIndiceOggettoInd tc = null;
      while(ite.hasNext()){
        tc = (VIndiceOggettoInd) ite.next();
        //System.out.println("TC = "+tc);
        System.out.println(tc.getCid()+" "+tc.getAreaIndirizzo()+" "+tc.getCodCatasto());
      }
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