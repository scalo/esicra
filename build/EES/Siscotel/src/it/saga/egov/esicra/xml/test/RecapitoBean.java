package it.saga.egov.esicra.xml.test;
import java.util.Date;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;


public class RecapitoBean {
  private IndirizzoBean indirizzo ;
  private String presso;
  private String telefono;
  private String email;

  public RecapitoBean() {
    this.indirizzo = new IndirizzoBean() ;
  }
  
  public RecapitoBean(IndirizzoBean newIndirizzo, 
                       String newPresso, String newTelefono,String newEmail) {

    this.indirizzo=newIndirizzo;
    this.presso=newPresso;
    this.telefono=newTelefono;
    this.email=newEmail;
  }

  public IndirizzoBean getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(IndirizzoBean newIndirizzoBean) {
    indirizzo = newIndirizzoBean;
  }

  public String getPresso() {
    return presso;
  }

  public void setPresso(String newPresso) {
    presso = newPresso;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String newTelefono) {
    telefono = newTelefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String newEmail) {
    email = newEmail;
  }


 public String toString(){
    return new String("[Indirizzo="+getIndirizzo()+","+
                      "Presso="+getPresso()+","+
                      "Telefono="+getTelefono()+","+
                      "Email="+getEmail()
                      +"]");
  }

  /*
  public static RecapitoBean test3(){
      
    return new RecapitoBean("VIA","UgoFoscolo",new Integer(25),"A",null,null,
                              "26100",null,null,
                              null,new LocalitaBean(new Integer(203),"Austria","Vienna"),
                              null,null,null);
  }
  */

  public static RecapitoBean test(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
    return new RecapitoBean(IndirizzoBean.test(),
            "Suo Zio","037225987","prova@www.virgilio.it");
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }
  
  public static RecapitoBean test4(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
    return new RecapitoBean(IndirizzoBean.test(),
            "Suo Zio","037225987","prova@www.virgilio.it");
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }



  public static void main(String[] args) throws Exception{
    System.out.println(RecapitoBean.test4());
  }


}