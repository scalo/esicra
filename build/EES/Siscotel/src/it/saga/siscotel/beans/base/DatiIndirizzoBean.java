package it.saga.siscotel.beans.base;

import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


public class DatiIndirizzoBean  implements Serializable{
  
  private String desArea;
  private Integer numCiv;
  private String letCiv;
  private String cap;
  private String comune;
  private String provincia;

  /**
   * Costruttore IndirizzoBean
   */
  public DatiIndirizzoBean() {
  }

  /**
   * Costruttore IndirizzoBean
   * 
   * @param newDesArea DesArea
   * @param newNumCiv NumCiv
   * @param newLetCiv LetCiv
   * @param newCap Cap
   * @param newComune Comune
   * @param newLocalita Localita
   */
  public DatiIndirizzoBean(String newDesArea, 
           Integer newNumCiv, String newLetCiv, 
            String newCap,String newComune,String newProvincia
           ){
           this.desArea = newDesArea;
           this.numCiv = newNumCiv;
           this.letCiv = newLetCiv;
           this.cap = newCap;
           this.comune=newComune;
           this.provincia=newProvincia;
                  
  }

  public String getDesArea() {
    return desArea;
  }

  public void setDesArea(String newDesArea) {
    desArea = newDesArea;
  }

  public Integer getNumCiv() {
    return numCiv;
  }

  public void setNumCiv(Integer newNumCiv) {
    numCiv = newNumCiv;
  }

  public String getLetCiv() {
    return letCiv;
  }

  public void setLetCiv(String newLetCiv) {
    letCiv = newLetCiv;
  }


  public String getCap() {
    return cap;
  }

  public void setCap(String newCap) {
    cap = newCap;
  }
  
   /**
   * Metodo restituisce il contenuto del bean
   * @return String
   */
  public String toString(){
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  
  public static DatiIndirizzoBean test() {
      
      DatiIndirizzoBean b =  new DatiIndirizzoBean();
      b.setDesArea("Via Ugo Foscolo");
      b.setNumCiv(new Integer(25));
      b.setLetCiv("A");
      b.setCap("24100");
      b.setComune("ComuneAmico");
      b.setProvincia("Bergamo");
      return b;
   }

  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }
  
  public static void main(String[] args) throws Exception{
    System.out.println(DatiIndirizzoBean.test());
  }
  
}