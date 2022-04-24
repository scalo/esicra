package it.saga.siscotel.beans.serviziscolastici;

import it.saga.siscotel.beans.base.DatiPraticaBean;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import it.saga.siscotel.beans.serviziscolastici.DatiScuolaBean;

public class PraIscrizioneCentroBean implements Serializable {

  private DatiPraticaBean datiPratica;
  private String centro;
  private String[] listaEsenzioni;
  private String[] listaAgevolazioni;
  private DatiScuolaBean datiScuola;
  private String corso;

  public PraIscrizioneCentroBean() {
  }

  public DatiPraticaBean getDatiPratica() {
    return datiPratica;
  }

  public void setDatiPratica(DatiPraticaBean datiPratica) {
    this.datiPratica = datiPratica;
  }

  public String getCentro() {
    return centro;
  }

  public void setCentro(String centro) {
    this.centro = centro;
  }

  public String[] getListaEsenzioni() {
    return listaEsenzioni;
  }

  public void setListaEsenzioni(String[] listaEsenzioni) {
    this.listaEsenzioni = listaEsenzioni;
  }

  public String[] getListaAgevolazioni() {
    return listaAgevolazioni;
  }

  public void setListaAgevolazioni(String[] listaAgevolazioni) {
    this.listaAgevolazioni = listaAgevolazioni;
  }

  public DatiScuolaBean getDatiScuola() {
    return datiScuola;
  }

  public void setDatiScuola(DatiScuolaBean datiScuola) {
    this.datiScuola = datiScuola;
  }

  public String toString(){
     return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static PraIscrizioneCentroBean test(){
    PraIscrizioneCentroBean b=new PraIscrizioneCentroBean();
    b.setDatiPratica(DatiPraticaBean.test());
    b.setDatiScuola(DatiScuolaBean.test());
    b.setCentro("Centro Polisportivo Ben Johnson");
    b.setCorso("Tamburello");
    String[] listaEsenzioni = new String[2];
    listaEsenzioni[0]="Esenzione per motivi fiscali";
    listaEsenzioni[1]="Esenzione per motivi inerenti";
    String[] listaAgevolazioni = new String[1];
    b.setListaEsenzioni(listaEsenzioni);
    b.setListaAgevolazioni(listaAgevolazioni);
    listaAgevolazioni[0]="Agevolazione Comunale";
    b.getDatiPratica().getPratica().setIdServizio(new BigDecimal("100168"));
    return b;
  }

  public static void main(String[] args) {
    System.out.println(PraIscrizioneCentroBean.test());
  }

  public String getCorso() {
    return corso;
  }

  public void setCorso(String corso) {
    this.corso = corso;
  }

}