package it.saga.siscotel.db.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.apache.log4j.Logger;
import java.lang.reflect.*;
import java.util.*;

public class HibernateUtil{
  
  private static Logger log = Logger.getLogger(HibernateUtil.class);
  
  private static final SessionFactory sessionFactory;
  
  static {
    try {
      // Create the SessionFactory
      Configuration cfg  =  new Configuration();
      Properties  props  = new Properties();
      //<property name="connection.datasource">java:comp/env/jdbc/NodoDS</property>
      String tipo = System.getProperty("esicra.tipo_installazione");
      boolean local = false;
      String str = System.getProperty("esicra.local");
      if (str!=null &&str.equalsIgnoreCase("true")) local=true;
      if(tipo!=null&&tipo.toUpperCase().equals("PAT")&&!local){
        props.put("hibernate.connection.datasource","java:comp/env/jdbc/PatDS");
        cfg.addProperties(props);
      }else if(tipo!=null&&tipo.toUpperCase().equals("NODO")&&!local){
        props.put("hibernate.connection.datasource","java:comp/env/jdbc/NodoDS");
        cfg.addProperties(props);
      }
      // TODO aggiungere dialetto sql
      cfg.configure();
      //System.out.println(cfg.getProperties());
      sessionFactory =cfg.buildSessionFactory();
    } catch (Throwable ex) {
      log.error("Initial SessionFactory creation failed.", ex);
    throw new ExceptionInInitializerError(ex);
    }
  }
  
  public static SessionFactory getSessionfactory(){
    return sessionFactory;
  }
  
  public static final ThreadLocal session = new ThreadLocal();
  
  public static Session currentSession() {
    Session s = (Session) session.get();
    // Open a new Session, if this Thread has none yet
    if (s == null) {
      s = sessionFactory.openSession();
      //s.setFlushMode(FlushMode.COMMIT);
      session.set(s);
    }
    return s;
  }
  
  public static void remove(Object obj) {
    Session s = currentSession();
    s.delete(obj);
  }
  
  public static void closeSession() {
    Session s = (Session) session.get();
    if (s != null)
      s.close();
    session.set(null);
  }
  
  public static String camelize(String s){
    if(s!=null && s.trim().length()>0){
      return (s.trim().substring(0,1).toUpperCase()+s.trim().substring(1));
    }
    return s;
  }
  
  /**
   * 
   * Recupera il valore dell'attributo di un oggetto
   * 
   * @param object 
   * @param attr 
   * @return value dell'attributo
   */
  public static Object getAttribute(Object object, String attr){
    Object value=null;
    if(object==null) return null;
    Class clazz = object.getClass();
    try{
      String mname= "get"+camelize(attr);
      //System.out.println("method:"+mname);
      Method method = clazz.getMethod(mname,null);
      value = method.invoke(object,null);
    }catch(Exception e){
      return null;
    }
    return value;
  }
  
  /**
   * 
   * Imposta un attributo in un oggetto
   * 
   * @param object 
   * @param attr 
   * @param value 
   */
  public static void setAttribute(Object object, String attr,Object value){
    if(object==null) return ;
    Class clazz = object.getClass();
    try{
      String mname="set"+camelize(attr);
      //Class[] param ={ object.getClass()};
      //param[0]=value.getClass();
      //Method method = clazz.getMethod(mname,param);
      Method method = findMethod(clazz,mname);
      Object p[]={value};
      value = method.invoke(object,p);
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  /**
   * Trova un metodo data la classe
   * accesso lineare
   * 
   * @param clz 
   * @param methodName 
   * @return 
   */
  public static Method findMethod(Class clz,String methodName){
    //Class clz = obj.getClass();
    Method[] methods = clz.getMethods();
    for(int i=0;i<methods.length;i++){
      String name = methods[i].getName();
      if(name.equals(methodName)){
        return methods[i];
      }
    }
    return null;
  }
  
}

