package it.saga.egov.esicra.utilita;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;



public class htmlUtil  {
  public htmlUtil() {
  }

  public static String leggiPar(HttpServletRequest params, String nomeParametro) {
     String  ris =  params.getParameter(nomeParametro);
     if (ris != null) 
         ris= ris.trim();
     return ris;
  }
  

  public static String leggiPar(HttpSession session, String nomeParametro) {
     String  ris =  (String) session.getAttribute(nomeParametro);
     if (ris != null) 
         ris= ris.trim();
     return ris;
  }

   public static String leggiPar(HttpSession session, HttpServletRequest params, String nomeParametro) {
     String  ris =  leggiPar(params,nomeParametro);
     if (ris==null) {
        ris = leggiPar(session,nomeParametro);
     } else {  
        session.setAttribute(nomeParametro,ris);
     }
    if (ris!=null) 
        ris=ris.trim();
    else
      ris="";
      
     return ris;
    
   }


   public static String leggiReqPar(HttpServletRequest request, HttpSession session, String nomeParametro) {
     String ris = request.getParameter(nomeParametro);
     if (ris==null) {
        ris = leggiPar(session,nomeParametro);
     } else {  
        session.setAttribute(nomeParametro,ris);
     }
    if (ris!=null) 
        ris=ris.trim();
    else
      ris="";
      
     return ris;
    
   }




/*
 
   public static ApplicationModule VerificaSessioneOC4J(HttpSession session, String nomeAm, ApplicationModule am) {
     ApplicationModule ris =  (ApplicationModule) session.getAttribute(nomeAm);
     if (ris==null) {
        session.setAttribute(nomeAm,am);
        ApriConessione(am,System.getProperty("esicra.dbconnection"),System.getProperty("esicra.dbuser"),System.getProperty("esicra.dbpassword"));
        ris = am;
     } 

     return ris;
   }


   public static ApplicationModule VerificaSessioneOC4J(HttpSession session, String nomeAm) {
     ApplicationModule ris =  (ApplicationModule) session.getAttribute(nomeAm);
     if (ris==null) {
        System.out.println("Application Module non inizializzato !!!!!!!!!!!!!"); 
     } 
     return ris;
   }



    public static int ApriConessione(ApplicationModule appMod, String URL, String user, String pwd) {
        Transaction myTrans= appMod.getTransaction();
        if (myTrans != null){
            String am1 = appMod.getTransaction().getConnectionMetadata().getJdbcURL().trim();
            String am2 = URL.trim();
            if (!am1.equalsIgnoreCase(am2) ){
                myTrans.disconnect();
                java.util.Properties info= new java.util.Properties();
                info.put("user", user);
                info.put("password", pwd);
               // info.put("defaultRowPrefetch","15");
                myTrans.connect(am2, info);
                System.out.println("Disconnesso da: "+am1+" - Riconnesso a: "+am2);
            } else {
                System.out.println("Connesso a: "+am1);                
            }
        } else {
            System.out.println("ATTENZIONE!!!!! Connessione non configurata");
        }
        return 1;
    }

*/        


}