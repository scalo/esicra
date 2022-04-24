package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FornitoreRidottoBean implements Serializable
{
  //MANDATORY - identificativo fornitore
  private BigDecimal idFornitore;
  
  //MANDATORI - identificativo ente
  private BigDecimal idEnte;

  //MANDATORI - descrizione della caretoria
  private String desCategoria;
  
  //MANDATORI - deninazione fornitore
  private String denominazione;
  
  //MANDATORI - descrizione albo
  private String desAlbo;
  
  //MANDATORI - descrizione ente
  private String desEnte;
  
  //MANDATORI - dati sullo stato del fornitore
  private StatoBean datiStato;  

  public FornitoreRidottoBean(BigDecimal idFornitore,
                              BigDecimal idEnte,
                              String denominazione,
                              String desCategoria,
                              String desAlbo,
                              String desEnte,
                              StatoBean datiStato)
  {
    this.idFornitore = idFornitore;
    this.idEnte = idEnte;
    this.denominazione = denominazione;
    this.desCategoria = desCategoria;
    this.desAlbo = desAlbo;
    this.desEnte = desEnte;
    this.datiStato = datiStato;
  }

  public FornitoreRidottoBean()
  {
  
  }
  
  public BigDecimal getIdFornitore()
  {
    return this.idFornitore;
  }
  public void setIdFornitore(BigDecimal idFornitore)
  {
    this.idFornitore = idFornitore;
  }

  public BigDecimal getIdEnte()
  {
    return this.idEnte;
  }
  public void setIdEnte(BigDecimal idEnte)
  {
    this.idEnte = idEnte;
  }
  
  public String getDenominazione()
  {
    return this.denominazione;
  }
  public void setDenominazione(String denominazione)
  {
    this.denominazione = denominazione;
  }
  
  public String getDesCategoria()
  {
    return this.desCategoria;
  }
  public void setDesCategoria(String desCategoria)
  {
    this.desCategoria = desCategoria;
  }

  public String getDesAlbo()
  {
    return this.desAlbo;
  }
  public void setDesAlbo(String desAlbo)
  {
    this.desAlbo = desAlbo;
  }

  public String getDesEnte()
  {
    return this.desEnte;
  }
  public void setDesEnte(String desEnte)
  {
    this.desEnte = desEnte;
  }

  public StatoBean getDatiStato()
  {
    return this.datiStato;
  }
  public void setDatiStato(StatoBean datiStato)
  {
    this.datiStato = datiStato;
  }
  
  public static FornitoreRidottoBean test()
  {
    FornitoreRidottoBean test = new FornitoreRidottoBean();
    test.setDenominazione("TEST DENOMINAZIONE");
    test.setDesAlbo("TEST ALBO DES");
    test.setDesEnte("ENTE DESC");
    test.setIdEnte(new BigDecimal(8240));
    test.setIdFornitore(new BigDecimal(1));
    test.setDesCategoria("TEST CATEGORIA");    
    
    StatoBean stato = new StatoBean();
    stato.setIdEnte(new BigDecimal(8240));
    stato.setIdStato(new BigDecimal(1));
    stato.setStato("TEST STATO");
    
    test.setDatiStato(stato);
    
    return test;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }
  
}
