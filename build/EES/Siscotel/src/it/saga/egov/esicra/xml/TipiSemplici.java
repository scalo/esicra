package it.saga.egov.esicra.xml;

import java.text.*;
import java.lang.reflect.*;
import java.math.*;
import java.sql.*;

/**
 *  Classe di utilità per gestire costruttori di tipi semplici da utilizzare
 *  per la costruzione dinamica di beans tramite reflection
 *  
 *  @author OS
 *  
 *  NB
 *  forse bisogna gestire un ClassLoader.
 *  
 */
public class TipiSemplici  {

  static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  // System ClassLoader
  //static final ClassLoader loader = ClassLoader(); 
  
  private static  String[] tipi={
    "java.lang.Integer",
    "java.math.BigDecimal",
    "java.lang.String",
    "java.util.Date",
    "java.util.Timestamp",
    "java.lang.Long",
    "java.lang.Character",
    "java.lang.Byte",
    "java.lang.Boolean",
    "java.lang.Float",
    "java.lang.Double"
  };

  /**
   *  Restituisce true se il tipo è semplice
   */
  public static boolean isPrimitive(Class classe){
    String nome = classe.getName();
    for(int i=0;i<tipi.length;i++){
      if(nome.equalsIgnoreCase(tipi[i])){
        return true;
      }
    }
    return false;
  }
  
  public static Object costruttoreSemplice(Class classe){
    return costruttoreSemplice(classe, null);
  }
  /**
   *  Costruttore semplice per i tipi base , riceve in ingresso una stringa
   */
  public static Object costruttoreSemplice(Class classe, String valore){
    Constructor cons=null;
    Object[] arglist=null;
    Object obj=null;
    try{
      String nomeClasse=classe.getName();
      if (nomeClasse.equals("java.lang.Integer")){
        Class[] pars ={Integer.TYPE};
        Integer val = (valore!=null)?new Integer(valore):new Integer(0);
        arglist =new Object[]{val};
        cons = classe.getDeclaredConstructor(pars);
      }else
      if (nomeClasse.equals("java.lang.Float")){
        Class[] pars ={Float.TYPE};
        Float val = (valore!=null)?new Float(valore):new Float(0.0);
        arglist =new Object[]{val};
        cons = classe.getDeclaredConstructor(pars);
      }else
      if (nomeClasse.equals("java.lang.Double")){
        Class[] pars ={Double.TYPE};
        Double val = (valore!=null)?new Double(valore):new Double(0.0);
        arglist =new Object[]{val};
        cons = classe.getDeclaredConstructor(pars);
      }else
      if(nomeClasse.equals("java.math.BigDecimal")){
        Class[] pars = {new String().getClass()};
        arglist = new Object[]{(valore!=null)?valore:"0"};
        cons = classe.getDeclaredConstructor(pars);
      }else
      if(nomeClasse.equals("java.lang.String")){
        Class[] pars ={new String().getClass()};
        arglist = new Object[]{(valore!=null)?valore:""};
        cons = classe.getDeclaredConstructor(pars);
      }else
      if(nomeClasse.equals("java.lang.Boolean")){
        Class[] pars ={Boolean.TYPE};
        arglist = new Object[]{(valore!=null)?new Boolean(valore):new Boolean("false")};
        cons = classe.getDeclaredConstructor(pars);
      }else
      if(nomeClasse.equals("java.util.Date")){
        return (valore!=null)?sdf.parse(valore):sdf.parse("1900-01-01");
      }else{ // è un Bean
        // attenzione la classe deve essere visibile al SystemClassLoader
        return classe.newInstance();
      }
      // è una classe semplice delle precedenti
      if(arglist!=null)
        obj=cons.newInstance(arglist);
      }catch(Exception e){
        e.printStackTrace();
      }  
    return obj;
  }
  
  public static String toString(java.util.Date data){
    return sdf.format(data);
  }
  
  public static void main(String[] args){
    try{
      System.out.println("Integer="+isPrimitive(Class.forName("java.lang.Integer")));
      System.out.println("Certificato="+isPrimitive(Class.forName("it.saga.egov.esicra.xml.TipiSemplici")));
      System.out.println("Oggetto Semplice="+costruttoreSemplice(Class.forName("java.lang.Integer")));
      System.out.println("Boolean ="+costruttoreSemplice(Class.forName("java.lang.Boolean")));  
    }catch(Exception e){
      e.printStackTrace(); 
    }
  }
 
}