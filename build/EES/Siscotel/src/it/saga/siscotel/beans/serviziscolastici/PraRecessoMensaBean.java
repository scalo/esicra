package it.saga.siscotel.beans.serviziscolastici;

import it.saga.siscotel.beans.base.DatiPraticaBean;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import it.saga.siscotel.beans.serviziscolastici.DatiScuolaBean;


public class PraRecessoMensaBean  implements Serializable {
  private DatiPraticaBean datiPratica;
  private DatiScuolaBean datiScuola;
  private String mensa;

  public PraRecessoMensaBean() {
  }

  public DatiPraticaBean getDatiPratica() {
    return datiPratica;
  }

  public void setDatiPratica(DatiPraticaBean datiPratica) {
    this.datiPratica = datiPratica;
  }

  public DatiScuolaBean getDatiScuola() {
    return datiScuola;
  }

  public void setDatiScuola(DatiScuolaBean datiScuola) {
    this.datiScuola = datiScuola;
  }

  public String getMensa() {
    return mensa;
  }

  public void setMensa(String mensa) {
    this.mensa = mensa;
  }
  
  public String toString(){
     return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static PraRecessoMensaBean test(){
    PraRecessoMensaBean b=new PraRecessoMensaBean();
    b.setDatiPratica(DatiPraticaBean.test());
    b.setDatiScuola(DatiScuolaBean.test());
    b.setMensa("Mensa scolastica");
    String[] listaEsenzioni = new String[2];
    listaEsenzioni[0]="Esenzione per motivi fiscali";
    listaEsenzioni[1]="Esenzione per motivi inerenti";
    String[] listaAgevolazioni = new String[1];
    listaAgevolazioni[0]="Agevolazione Comunale";
    b.getDatiPratica().getPratica().setIdServizio(new BigDecimal("210046"));
    return b;
  }

  public static void main(String[] args) {
    System.out.println(PraRecessoMensaBean.test());
  }
}