package it.saga.egov.esicra.importazione;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.math.*;
import org.jdom.*;

/**
 * Classe per la conversione dei dati
 * 
 *  TODO
 *  creare eccezione per rilevare errori nei tipi di dati e interrompere
 *  l'importazione del documento
 */

 
public class CV  {

    protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    
    /**
     *   Converte stringa in formato yyyy-MM-dd in java.sql.Date 
     *   da passare all'object view
     */
    public static java.sql.Date data(String data) throws ParseException{
        if(data==null)
          return null;
        else if(data.trim() =="")
          return null;
        else
          return new java.sql.Date(sdf.parse(data).getTime());
        // return ((data==null)||(data.equals(""))?null:new java.sql.Date(sdf.parse(data).getTime()));
    }

    public static java.sql.Date data(Element e) throws ParseException{
        if(e==null)
          return null;
        else {
          String dt = e.getText();
          dt = dt.trim();
          if(dt =="")
            return null;
          else
            return new java.sql.Date(sdf.parse(dt).getTime());
          //return (e==null?null:new java.sql.Date(sdf.parse(e.getText()).getTime()));
        }
    }
    
    /**
     *  Converte chiave (da stringa numerica )in BigDecimal
     */
    public static BigDecimal Id(String intero){
        return new BigDecimal(intero);
    }

    /**
     *  Metodo temporaneo
     */
    public static BigDecimal PkId(int i){
        return new BigDecimal(Integer.toString(i));
    }

    public static Integer intero(int i){
        return new Integer(i);
    }

    public static Integer intero(String str){
        return new Integer(str);
    }
    public static Integer intero(Element element){
        if (element == null) {
          return null;
        } else {
          String aa = element.getText();
          aa=aa.trim();
          if (aa.equals(""))
              return null;
           else
              return new Integer(aa);
        }

    
//        return (element==null?null: new Integer(element.getText()));
    }

    public static String text(Element element){
//      return (element==null?null:element.getText());
        if (element == null)
             return null;
        else {
             String aa = element.getText();
             if (aa.equals(""))
                 return null;
            else
                return aa;
        }
        
    }
    
    public static java.sql.Timestamp timestamp(String ts)throws ParseException{
        return (ts==null?null:new java.sql.Timestamp(sdf.parse(ts).getTime()));
    }

    public static java.sql.Timestamp timestamp(Element e)throws ParseException{
        return (e==null?null:new java.sql.Timestamp(sdf.parse(e.getText()).getTime()));
    }

    public static java.sql.Timestamp timestamp(){
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * main test
     */
    public static void main(String[] args) {
        try{
        System.out.println("-------------Test----------------");
        System.out.println( "Data "+
            data("2001-02-20").toString());
        System.out.println( "Id "+
            Id("984589492456"));
        System.out.println( "PkId "+
            PkId(44));
        System.out.println( "Intero "+
            intero(12345)); 
        System.out.println( "TimeStamp "+
            timestamp("2001-20-02"));
        System.out.println( "TimeStamp null "+
            timestamp());   
        System.out.println("---------------------------------");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
