package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

public class StatoBean implements Serializable
{
  //MANDATORY - chiave univoca 
  private BigDecimal idStato;
  
  //MANDATORY - identificativo dell'ente 
  private BigDecimal idEnte;

  //MANDATORY - codice dello stato
  private String codStato;

  //MANDATORY - descrizione dello stato  
  private String stato;

  public StatoBean(BigDecimal idStato, BigDecimal idEnte, String codStato, String stato)
  {
    this.idStato = idStato;
    this.idEnte = idEnte;
    this.codStato = codStato;
    this.stato = stato;
  }
    
  public StatoBean()
  {
  
  }
  
  public BigDecimal getIdStato()
  {
    return this.idStato;
  }
  public void setIdStato(BigDecimal idStato)
  {
    this.idStato = idStato;
  }

  public BigDecimal getIdEnte()
  {
    return this.idEnte;
  }
  public void setIdEnte(BigDecimal idEnte)
  {
    this.idEnte = idEnte;
  }

  public String getCodStato()
  {
    return this.codStato;
  }
  public void setCodStato(String codStato)
  {
    this.codStato = codStato;  
  }
  
  public String getStato()
  {
    return this.stato;
  }
  public void setStato(String stato)
  {
    this.stato = stato;  
  }
  
  public static StatoBean test()
  {
    StatoBean bean = new StatoBean();
    bean.setIdEnte(new BigDecimal(8240));
    bean.setStato("01");
    bean.setStato("STATO");
    
    return bean;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }
   
}
