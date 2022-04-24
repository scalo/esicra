package it.saga.siscotel.beans.base;

import java.io.Serializable;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DatiPraticaBean implements Serializable {

  private DatiSoggettoBean soggettoRichiedente;
  private DatiSoggettoBean soggettoFruitore;
  private String titolaritaRichiedente;
  private PraticaBean pratica;

  public DatiPraticaBean(){
  }
  
  public String toString(){
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
   }

  public static void main(String[] args) throws Exception{
    System.out.println(RecapitoBean.test().toString());
  }

  public DatiSoggettoBean getSoggettoRichiedente() {
    return soggettoRichiedente;
  }

  public void setSoggettoRichiedente(DatiSoggettoBean soggettoRichiedente) {
    this.soggettoRichiedente = soggettoRichiedente;
  }

  public DatiSoggettoBean getSoggettoFruitore() {
    return soggettoFruitore;
  }

  public void setSoggettoFruitore(DatiSoggettoBean soggettoFruitore) {
    this.soggettoFruitore = soggettoFruitore;
  }

  public String getTitolaritaRichiedente() {
    return titolaritaRichiedente;
  }

  public void setTitolaritaRichiedente(String titolaritaRichiedente) {
    this.titolaritaRichiedente = titolaritaRichiedente;
  }

  public PraticaBean getPratica() {
    return pratica;
  }

  public void setPratica(PraticaBean pratica) {
    this.pratica = pratica;
  }

  public static DatiPraticaBean test(){
    DatiPraticaBean b=new DatiPraticaBean();
    b.setSoggettoRichiedente(DatiSoggettoBean.test());
    b.setSoggettoFruitore(DatiSoggettoBean.test1());
    b.setTitolaritaRichiedente("Genitore");
    b.setPratica(PraticaBean.test());
    return b;
  }

}