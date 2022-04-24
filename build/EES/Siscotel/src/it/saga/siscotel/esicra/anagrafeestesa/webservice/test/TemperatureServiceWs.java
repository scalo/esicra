package it.saga.siscotel.esicra.anagrafeestesa.webservice.test;

import java.util.Random;

public class TemperatureServiceWs  {
  
  private static Random rnd= new Random(System.currentTimeMillis());
  
  public TemperatureServiceWs() {
  }
  
  public Float getTemp(String zip){
    return  new Float(rnd.nextFloat()*40);
  }
  
}