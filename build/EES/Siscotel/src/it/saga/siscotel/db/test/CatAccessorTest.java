package it.saga.siscotel.db.test;

import org.hibernate.*;
import java.util.*;
import java.math.*;
import java.lang.reflect.*;
import org.apache.commons.beanutils.BeanUtilsBean;
import it.saga.siscotel.db.hibernate.HibernateUtil;

public class CatAccessorTest  {
  
  public static String camelize(String s){
    if(s!=null && s.trim().length()>0){
      return (s.trim().substring(0,1).toUpperCase()+s.trim().substring(1));
    }
    return s;
  }
  
  public static Object getAttribute(Object object, String attr){
    Object value=null;
    if(object==null) return null;
    Class clazz = object.getClass();
    try{
      System.out.println("clazz:"+clazz);
      String mname= "get"+camelize(attr);
      System.out.println("method:"+mname);
      Method method = clazz.getMethod(mname,null);
      System.out.println(method);
      value = method.invoke(object,null);
    }catch(Exception e){
      return null;
    }
    return value;
  }
  
  public static void setAttribute(Object object, String attr,Object value){
    if(object==null) return ;
    Class clazz = object.getClass();
    try{
      //Field field =clazz.getField(attr);
      String mname= "set"+camelize(attr);
      System.out.println("method:"+mname);
      Class param[]={value.getClass()};
      System.out.println(param);
      Method method = clazz.getMethod(mname,param);
      System.out.println(method);
      Object p[]={value};
      value = method.invoke(object,p);
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  /*
   * 
   */
  public static void copy(Object src,Object dst){
    try{
    BeanUtilsBean.getInstance().copyProperties(dst,src);
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
  /*
   *  
   */
  public static void swap(Object o1,Object o2){
    BigDecimal p1 = (BigDecimal) getAttribute(o1,"pkid");
    BigDecimal p2 = (BigDecimal) getAttribute(o2,"pkid");
    try{
      BeanUtilsBean util = BeanUtilsBean.getInstance();
      Constructor cons = o1.getClass().getConstructor(null);
      //System.out.println("clazz "+o1.getClass().getName());
      //System.out.println("claz "+new Cat().getClass().getName());
      Object tmp1= cons.newInstance(null);
      //Object tmp2= cons.newInstance(null);
      util.copyProperties(tmp1,o1);
      //System.out.println("1");
      //System.out.println("tmp1 "+ tmp1);
      util.copyProperties(o1,o2);
      //System.out.println("2");
      //System.out.println("o1 "+ o1);
      //System.out.println("tmp2 "+ tmp2);
      util.copyProperties(o2,tmp1);
      //System.out.println("3");
      //System.out.println("o2 "+ o2);
      //BeanUtilsBean.getInstance().copyProperties(o1,tmp2);
      //System.out.println("4");
      //System.out.println("o1 "+ o1);
      setAttribute(o1,"pkid",p1);
      setAttribute(o2,"pkid",p2);
    }catch(Exception e){
      System.out.println(e);
    }
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
  
  public static void testReflection() throws Exception{
    Session session = HibernateUtil.currentSession();
    Cat cat = new Cat();
    cat = (Cat) session.load("it.saga.siscotel.db.test.Cat",new BigDecimal("0"));
    Field[] fields = cat.getClass().getFields();
    for(int i=0;i<fields.length;i++){
      System.out.println(fields[i].getName());
    }
  }
  
  public static void testListFields(){
    Cat cat = new Cat();
    Class classe= Cat.class;
    Field[] fields = classe.getDeclaredFields();
    for(int i=0;i<fields.length;i++){
      System.out.println(fields[i].getName());
    }
  }
  
  public static void testCopy(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    Cat cat = new Cat();
    Cat newcat = new Cat();
    cat = (Cat) session.load("it.saga.siscotel.db.test.Cat",new BigDecimal("0"));
    BigDecimal pkid = newcat.getPkid();
    copy(cat,newcat);
    newcat.setPkid(pkid);
    setAttribute(newcat,"name","Giuseppina");
    session.save(newcat);
    tx.commit();
    session.close();
  }
  
  public static void testSwapOld(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    Cat cat1 = (Cat) session.load("it.saga.siscotel.db.test.Cat",new BigDecimal("3"));
    Cat cat2 = (Cat) session.load("it.saga.siscotel.db.test.Cat",new BigDecimal("55"));
    Cat tmp1 = new Cat();
    Cat tmp2 = new Cat();
    System.out.println("1");
    copy(cat1,tmp1);
    copy(cat2,tmp2);
    System.out.println("2");
    copy(tmp2,cat1);
    System.out.println("3");
    copy(tmp1,cat2);
    cat1.setPkid(new BigDecimal(3));
    cat2.setPkid(new BigDecimal(55));
    System.out.println("4");
    tx.commit();
    session.close();
    System.out.println(5);
  }
  
  public static void testSwap(){
    Session session = HibernateUtil.currentSession();
    Transaction tx = session.beginTransaction();
    Cat cat1 = new Cat();
    session.load(cat1,new BigDecimal("3"));
    Cat cat2 = new Cat();
    session.load(cat2,new BigDecimal("55"));
    swap(cat1,cat2);
    tx.commit();
    session.close();
  }
  
  public static void main(String[] args) throws Exception {
    System.setProperty("id_ente","8240");
    //CatAccessorTest.testReflection();
    //CatAccessorTest.testListFields();
    //CatAccessorTest.testCopy();
    CatAccessorTest.testSwap();
  }
  
}
