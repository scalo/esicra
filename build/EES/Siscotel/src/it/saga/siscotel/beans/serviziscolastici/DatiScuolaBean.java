package it.saga.siscotel.beans.serviziscolastici;

import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DatiScuolaBean implements Serializable
{

  private String scuola;
  private String classe;
  private String annoScolastico;

  public String getScuola() {
    return scuola;
  }

  public void setScuola(String scuola) {
    this.scuola = scuola;
  }

  public String getClasse() {
    return classe;
  }

  public void setClasse(String classe) {
    this.classe = classe;
  }

  public String getAnnoScolastico() {
    return annoScolastico;
  }

  public void setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;
  }

  public String toString(){
     return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static DatiScuolaBean test(){
    DatiScuolaBean b=new DatiScuolaBean();
    b.setScuola("Scuola Media Statale Giordano Bruno");
    b.setClasse("3 B");
    b.setAnnoScolastico("2005");
    return b;
  }

  public static void main(String[] args){
    System.out.println(DatiScuolaBean.test().toString());
  }

}
