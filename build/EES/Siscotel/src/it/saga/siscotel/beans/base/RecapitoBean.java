package it.saga.siscotel.beans.base;

import java.io.Serializable;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RecapitoBean implements Serializable
{
  private DatiIndirizzoBean indirizzo ;
  private String presso;
  private String telefono;
  private String email;
  private String fax;

  /**
   * Costruttore RecapitoBean
   */
  public RecapitoBean() {
    this.indirizzo = new DatiIndirizzoBean() ;
  }
  
  /**
   * Costruttore RecapitoBean
   * @param newIndirizzo Indirizzo
   * @param newPresso Presso
   * @param newTelefono Telefono
   * @param newEmail Email
   */
  public RecapitoBean(DatiIndirizzoBean newIndirizzo, 
                       String newPresso, 
                       String newTelefono,
                       String newEmail,
                       String newFax) {

    this.indirizzo=newIndirizzo;
    this.presso=newPresso;
    this.telefono=newTelefono;
    this.email=newEmail;
    this.fax=newFax;
  }

  public DatiIndirizzoBean getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(DatiIndirizzoBean newIndirizzoBean) {
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
  
   public void setFax(String fax)
  {
    this.fax = fax;
  }


  public String getFax()
  {
    return fax;
  }


 /**
   * Metodo restituisce il contenuto del bean istanziato 
   * @return toString del Bean 
   */
  public String toString(){
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
   }

  public static RecapitoBean test(){
    RecapitoBean b = new RecapitoBean();
    b.setIndirizzo(DatiIndirizzoBean.test());
    b.setPresso("Franco Bauli");
    b.setFax("037225987");
    b.setTelefono("390376562378");
    b.setEmail("pinoli@emailfinta.it");
    return b;
  }
  

  public static void main(String[] args) throws Exception{
    System.out.println(RecapitoBean.test().toString());
  }

}