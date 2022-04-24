package it.saga.egov.esicra.utilita;

/**
 * 
 * @author lrigotti
 * 
 */

public class GestioneVersione  {

  public GestioneVersione() {

  }


  // ricevuta in ingresso la versione del programma e la versione del 
  // flusso Xml, viene restituito true se il programma ha una versione 
  // maggiore o uguale alla versione dell'Xml
  
  public static boolean verificaVersione (String prg, String xml) {
     int prg_versione = 0;
     int prg_sottoVersione = 0;
     int prg_release = 0;

     int xml_versione = 0;
     int xml_sottoVersione = 0;
     int xml_release = 0;

     prg_versione = getVersione(prg);
     prg_sottoVersione = getSottoVersione(prg);
     prg_release = getRelease(prg); 

     xml_versione = getVersione(xml);
     xml_sottoVersione = getSottoVersione(xml);
     xml_release = getRelease(xml); 

     if (xml_versione > prg_versione)
         return false;
 
     if (xml_sottoVersione > prg_sottoVersione)
         return false;
      
     return true;      

  }

  private static int getVersione(String s1) {
    int res = 0;
    int pos = 0;
 
    // ricerca del primo punto
    pos = s1.indexOf('.',0);
    if  (pos > 0) 
         try {
            res = Integer.parseInt(s1.substring(0,pos));
         } catch (Exception e) {
                res = 0;
         }
    else 
         try {
         res = Integer.parseInt(s1);
         } catch(Exception e) {
           res = 0;
         }
  return res;

  }

  private static int getSottoVersione(String s1) {
    int res = 0;
    int pos = 0;
    int pos1 = 0;
    String s=null;
    // ricerca del primo punto
    pos = s1.indexOf('.',0);
    if  (pos < 0) 
        return 0;

    pos1 = s1.indexOf('.',pos+1);

    if  (pos1 < 0) {
        try {
            res = Integer.parseInt(s1.substring(pos+1));
        } catch (Exception e){
            res = 0; 
        }
    }

    else  
         try {
            res = Integer.parseInt(s1.substring(pos+1,pos1));
         } catch (Exception e) {
           res = 0;
         }
    
   return res;

  }

  private static int getRelease(String s1) {
    int res = 0;
    int pos = 0;
    int pos1 = 0;

    String s=null;
    // ricerca del primo punto
    pos = s1.indexOf('.',0);
    if  (pos < 0) 
        return 0;
    pos1 = s1.indexOf('.',pos+1);

    if  (pos1 < 0) 
        return 0;
    else {   
        try {
            res = Integer.parseInt(s1.substring(pos1+1));
        } catch (Exception e){
            res = 0; 
        }
    }
   return res;

  }
  

 public static void main(String[] args){
  String a = "35.2.24";
  System.out.println(a+" = "+ GestioneVersione.getVersione(a)+" - "+GestioneVersione.getSottoVersione(a)+" - "+GestioneVersione.getRelease(a)); 

  a="3525"; 
  System.out.println(a+" = "+ GestioneVersione.getVersione(a)+" - "+GestioneVersione.getSottoVersione(a)+" - "+GestioneVersione.getRelease(a)); 

  a="321.256";
  System.out.println(a+" = "+ GestioneVersione.getVersione(a)+" - "+GestioneVersione.getSottoVersione(a)+" - "+GestioneVersione.getRelease(a)); 

  a="321.25.34.";
  System.out.println(a+" = "+ GestioneVersione.getVersione(a)+" - "+GestioneVersione.getSottoVersione(a)+" - "+GestioneVersione.getRelease(a)); 
    
  if (GestioneVersione.verificaVersione("3.2","3.1") == true) 
      System.out.println("3.2 -- 3.1 --  vero");
  else
      System.out.println("3.2 -- 3.1 --  falso");


  if ( GestioneVersione.verificaVersione("3.1","3.1") == true) 
      System.out.println("3.1 -- 3.1 --  vero");
  else
      System.out.println("3.1 -- 3.1 --  falso");

  if ( GestioneVersione.verificaVersione("3.0.25","3.1") == true) 
      System.out.println("3.0.25 -- 3.1 --  vero");
  else
      System.out.println("3.0.25 -- 3.1 --  falso");

  if ( GestioneVersione.verificaVersione("2","3.1") == true) 
      System.out.println("2 -- 3.1 --  vero");
  else
      System.out.println("2 -- 3.1 --  falso");


  } 
}