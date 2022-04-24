package it.saga.egov.esicra.test;

import java.util.*;
import java.io.*;

/**
 *  Programma di test che legge un file di properties
 */
public class PropertiesTest  {
  
  ResourceBundle props=null;
  
  public PropertiesTest() {
    props =
        ResourceBundle.getBundle("it.saga.egov.esicra.esicra");
  }

  public void lista(){
    if(props==null)
      System.out.println("Nessuna props");
    else{
      Enumeration keys = props.getKeys();
      while(keys.hasMoreElements()){
        String key = (String)keys.nextElement();
        System.out.println(key+"="+props.getString(key));
      }
      
    }
  }

  public  void test() throws IOException{
    String path = (String) props.getString("esicra.test");
    LineNumberReader lr = new LineNumberReader(new FileReader(new File(path)));
    while(true){
      String line= lr.readLine();
      if(line==null) return;
      System.out.println(line);
    }
    
  }
  
  public static void main(String[] args) throws IOException {
    PropertiesTest propertiesTest = new PropertiesTest();
    propertiesTest.lista();
    System.out.println("-----------------------------------------------");
    propertiesTest.test();
  }
}