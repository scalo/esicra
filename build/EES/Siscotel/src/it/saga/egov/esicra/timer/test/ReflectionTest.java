package it.saga.egov.esicra.timer.test;

import it.saga.egov.esicra.timer.*;
import it.saga.egov.esicra.timer.test.*;
import java.lang.reflect.*;

/**
 *  Test per utilizzare la reflection nella creazione dei Task (EesTask)
 *  
 */
public class ReflectionTest  {

  private static final String BASE="it.saga.egov.esicra.timer.EesTask";
    
  public ReflectionTest(String nomeClasse) {
    try{
      //carica la classe base dei task
      Class classe = Class.forName(nomeClasse);
      //cerca tutte le sottclassi
      
    }catch(Exception e){
      
    }
  }

  
  
}