package it.saga.egov.esicra.xml.test;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LocalitaBean  {
  
  private Integer codIstatStato;
  private String desStato;
  private String desLocalita;
  private String desContea;

  public LocalitaBean() {
  }

  public LocalitaBean(Integer codIstatStato, String desStato , String desLocalita) {
    this.codIstatStato=codIstatStato;
    this.desStato=desStato;
    this.desLocalita=desLocalita;
  }

 
  
  public Integer getCodIstatStato() {
    return codIstatStato;
  }

  public void setCodIstatStato(Integer newCodIstatStato) {
    codIstatStato = newCodIstatStato;
  }

  public String getDesStato() {
    return desStato;
  }

  public void setDesStato(String newDesStato) {
    desStato = newDesStato;
  }

  public String getDesLocalita() {
    return desLocalita;
  }

  public void setDesLocalita(String newDesLocalita) {
    desLocalita = newDesLocalita;
  }

  public String getDesContea() {
    return desContea;
  }

  public void setDesContea(String newDesContea) {
    desContea = newDesContea;
  }

/*
  public String toString(){
    return new String("["+
                      "codIstatStato="+getCodIstatStato()+","+
                      "desStato="+getDesStato()+","+
                      "desLocalita="+getDesLocalita()+","+
                      "desContea="+getDesContea()
                      +"]");
  }
*/

  public String toString() {
     return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
  }

  public static LocalitaBean test(){
    return new LocalitaBean(new Integer(203),"Austria","Vienna");
  }

  public static LocalitaBean testNull(){
    return new LocalitaBean(new Integer(203),"Austria","");
  }

  public static void main(String[] args){
    System.out.println(LocalitaBean.test());
    System.out.println(LocalitaBean.testNull());
  }
}

