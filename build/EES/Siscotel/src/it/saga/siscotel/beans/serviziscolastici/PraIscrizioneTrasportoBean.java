package it.saga.siscotel.beans.serviziscolastici;

import it.saga.siscotel.beans.base.DatiPraticaBean;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import it.saga.siscotel.beans.serviziscolastici.DatiScuolaBean;

public class PraIscrizioneTrasportoBean implements Serializable {

  private DatiPraticaBean datiPratica;
  private String percorso;
  private String[] listaEsenzioni;
  private String[] listaAgevolazioni;
  private DatiScuolaBean datiScuola;

  public PraIscrizioneTrasportoBean() {
  
  }

  public DatiPraticaBean getDatiPratica() {
    return datiPratica;
  }

  public void setDatiPratica(DatiPraticaBean datiPratica) {
    this.datiPratica = datiPratica;
  }

  public String getPercorso() {
    return percorso;
  }

  public void setPercorso(String percorso) {
    this.percorso = percorso;
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

  public static PraIscrizioneTrasportoBean test(){
    PraIscrizioneTrasportoBean b=new PraIscrizioneTrasportoBean();
    b.setDatiPratica(DatiPraticaBean.test());
    b.setDatiScuola(DatiScuolaBean.test());
    b.setPercorso("Piazza Garibaldi 11  - via Mazzini 16");
    String[] listaEsenzioni = new String[2];
    listaEsenzioni[0]="Esenzione per motivi fiscali";
    listaEsenzioni[1]="Esenzione per motivi inerenti";
    String[] listaAgevolazioni = new String[1];
    b.setListaEsenzioni(listaEsenzioni);
    b.setListaAgevolazioni(listaAgevolazioni);
    listaAgevolazioni[0]="Agevolazione Comunale";
    b.getDatiPratica().getPratica().setIdServizio(new BigDecimal("100142"));
    return b;
  }

  public static void main(String[] args) {
    System.out.println(PraIscrizioneTrasportoBean.test());
  }

}