package it.saga.siscotel.db.hibernate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.*;
import java.io.*;

public class PkidGenerator implements IdentifierGenerator  {
  
  private static long old=0;
  private static DecimalFormat decimal = new DecimalFormat("0000000"); 
  
  public synchronized Serializable generate(SessionImplementor session,
  Object object) throws HibernateException {
      return (Serializable)generate();
   }
   
  public Object generate(){
      // check id_ente
      String id_ente=System.getProperty("esicra.id_ente");
      long ts = System.currentTimeMillis();
      while(old>=ts) ts++;
      String result=Long.toString(ts)+decimal.format(Integer.valueOf(id_ente));
      old=ts;
      BigDecimal seq = new BigDecimal(result);
      return seq;
   }
   
   
   public static void main(String[] args) {
      System.setProperty("esicra.id_ente","8240");
      PkidGenerator pk = new PkidGenerator();
      for(int i=0;i<20;i++){
        System.out.println(pk.generate());
      }
   }
   
}
