package it.saga.egov.esicra.db;

import java.util.*;
import java.sql.Types;
import java.lang.reflect.*;

/**
 *      Lista dei tipi sql Classe Statica (Singleton)
 *      Mappa i tipi java sql fornendo dei metodi di get per le descrizioni
 *      provato ad usare la reflection per prendere il nome dell'attributo 
 *     
 *     @todo i sinonimi per la compatibilità sono nel comparator
 *     
 *         
 */
public  class TypeList  {
    
    private static HashMap map = null;
    private static HashMap name2type = null;
    
    /**
     *      SINGLETON
    **/    
    public TypeList() {
        // no   
    }
    
    static void  create() {
        map = new HashMap();
        name2type = new HashMap();
        try{
            Class c =Class.forName("java.sql.Types");
            Field[] fields = c.getDeclaredFields();
            //System.out.println("class c="+c);
            //System.out.println("field="+fields);
            for(int i=0;i<fields.length;i++){
                //System.out.println(fields[i]);
                String typeName = fields[i].getName();
                map.put(Integer.toString(fields[i].getInt(c)),typeName);
                name2type.put(typeName,new Integer(fields[i].getInt(c)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String getTypeName(int i){
        if (map==null) create(); 
        return (String) map.get( Integer.toString(i));
    }
    
    public static int getTypeValue(String skey){
        if (name2type==null) create(); 
        return ((Integer) name2type.get(skey)).intValue();
    }
    
    public static void main(String[] args) {
        System.out.println("Tipo "+Types.DATE+" = "+ TypeList.getTypeName(Types.DATE));
        System.out.println("Tipo "+Types.VARCHAR+" = "+ TypeList.getTypeName(Types.VARCHAR));
        System.out.println("Tipo "+Types.TIMESTAMP+" = "+ TypeList.getTypeName(Types.TIMESTAMP));
        System.out.println("Tipo "+Types.DATE+" = "+ TypeList.getTypeName(Types.DATE));
        System.out.println("Tipo "+"VARCHAR"+" = "+ TypeList.getTypeValue("VARCHAR"));
        System.out.println("Tipo "+"NUMERIC"+" = "+ TypeList.getTypeValue("NUMERIC"));
        System.out.println("Tipo "+"DECIMAL"+" = "+ TypeList.getTypeValue("DECIMAL"));
        System.out.println("Tipo "+"INTEGER"+" = "+ TypeList.getTypeValue("INTEGER"));
    }
    
    public static boolean typeCompatibility(Column c1, Column c2){
        if(((c1.type==Types.NUMERIC)&&(c2.type==Types.DECIMAL))||
           ((c2.type==Types.NUMERIC)&&(c1.type==Types.DECIMAL))){
            return true;
        }else
        // postgres int4 <--> oracle decimal v1
        if(((c1.type==Types.INTEGER)&&(c1.size==4))&&((c2.type==Types.DECIMAL)&&(c2.size==22))||
            ((c2.type==Types.INTEGER)&&(c2.size==4))&&((c1.type==Types.DECIMAL)&&(c1.size==22))){
            return true;
        }else
        // postgres int4 <--> oracle decimal v2
        if(((c1.type==Types.INTEGER)&&(c1.size==4))&&((c2.type==Types.DECIMAL)&&(c2.size==38))||
            ((c2.type==Types.INTEGER)&&(c2.size==4))&&((c1.type==Types.DECIMAL)&&(c1.size==38))){
            return true;
        }else
        // timestamp -- date
        if(((c1.type==Types.DATE)&&(c1.size==4)&&(c2.type==Types.TIMESTAMP)&&(c2.size==7))||
          ((c2.type==Types.DATE)&&(c2.size==4)&&(c1.type==Types.TIMESTAMP)&&(c1.size==7))){
            return true;
        }else
        // timestamp (oracle / postgres)
        if(((c1.type==Types.TIMESTAMP)&&(c1.size==7)&&(c2.type==Types.TIMESTAMP)&&(c2.size==8))||
          ((c2.type==Types.TIMESTAMP)&&(c2.size==7)&&(c1.type==Types.TIMESTAMP)&&(c1.size==8))){
            return true;
        }else
        // Clob (OTHER == clob  w oracle )
        if((c1.type==Types.OTHER)||(c2.type==Types.OTHER)){
            return true;
        }
        return false;
    }
    
}
