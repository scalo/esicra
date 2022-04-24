package com.lokitech.hibtags;

import java.util.Properties;
import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateFactory {
  private static final SessionFactory sessionFactory;
  
  static {
    try {
      Configuration cfg  =  new Configuration();
      cfg.configure();
      // Recupera connessione da properties esicra se definite
      Properties  props  = new Properties();
      String tipo = System.getProperty("esicra.tipo_installazione");
      //System.out.println(tipo);
      if(tipo!=null&&tipo.equals("PAT")){
        props.put("hibernate.connection.datasource","java:comp/env/jdbc/PatDS");
        cfg.addProperties(props);
      }else if(tipo!=null&&tipo.equals("NODO")){
        props.put("hibernate.connection.datasource","java:comp/env/jdbc/NodoDS");
        cfg.addProperties(props);
      }
      sessionFactory =cfg.buildSessionFactory();      
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      //log.error("Initial SessionFactory creation failed.", ex);
    throw new ExceptionInInitializerError(ex);
    }
  }
  
  /*
  public static final ThreadLocal session = new ThreadLocal();

  public static Session currentSession() {
    Session s = (Session) session.get();
    // Open a new Session, if this Thread has none yet
    if (s == null) {
      s = sessionFactory.openSession();
      session.set(s);
    }
    return s;
  }
  
  public static void closeSession() {
    Session s = (Session) session.get();
    if (s != null)
    s.close();
    session.set(null);
  }
  */
  
  public static SessionFactory currentSessionFactory() {
    //System.out.println(System.getProperty("esicra.tipo_installazione"));
    return sessionFactory;
  }
}
