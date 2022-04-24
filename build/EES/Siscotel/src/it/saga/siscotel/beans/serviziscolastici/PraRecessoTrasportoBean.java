package it.saga.siscotel.beans.serviziscolastici;

import it.saga.siscotel.beans.base.DatiPraticaBean;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import it.saga.siscotel.beans.serviziscolastici.DatiScuolaBean;


public class PraRecessoTrasportoBean  implements Serializable {
  private DatiPraticaBean datiPratica;
  private DatiScuolaBean datiScuola;
  private String percorso;

  public PraRecessoTrasportoBean() {
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

  public String getPercorso() {
    return percorso;
  }

  public void setPercorso(String percorso) {
    this.percorso = percorso;
  }
  
  public String toString(){
     return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static PraRecessoTrasportoBean test(){
    PraRecessoTrasportoBean b=new PraRecessoTrasportoBean();
    b.setDatiPratica(DatiPraticaBean.test());
    b.setDatiScuola(DatiScuolaBean.test());
    b.setPercorso("Piazza Garibaldi 11  - via Mazzini 16");
    b.getDatiPratica().getPratica().setIdServizio(new BigDecimal("210047"));
    return b;
  }

  public static void main(String[] args) {
    System.out.println(PraRecessoTrasportoBean.test());
  }
}