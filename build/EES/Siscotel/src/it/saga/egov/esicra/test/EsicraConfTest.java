package it.saga.egov.esicra.test;

import it.saga.egov.esicra.*;
import javax.naming.*;
import java.util.*;

/**
 *  Classe di test per verificare il funzionamento del Naming Service TYREX
 *  
 */
public class EsicraConfTest  {

  public static final String PATH = "java:comp/env/esicra";
  
  public EsicraConfTest() {
  }

  public static void main(String[] args) throws NamingException {
    Context ctx = EsicraConf.getInitialContext(PATH);
    ctx.bind("pippo",new String("stringa 1"));
    ctx.bind("pluto",new String("stringa 2"));

    ctx=null;
    ctx = EsicraConf.getInitialContext(PATH);
    
    // visualizza tutto il contesto
    Enumeration enume = ctx.listBindings("");
    while(enume.hasMoreElements()){
      System.out.println(enume.nextElement());
    }
  }
  
}