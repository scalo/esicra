package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;
import java.math.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PraAccreditamentoIterBean implements Serializable
{
  //MANDATORY - identificativo richiesta ITER
  private BigDecimal idRichiestaIter;
  
  //MANDATORY - identificatico della richiesta di accreditamento
  private BigDecimal idRichiestaAcc;
  
  private String responsabile;
  
  private String noteStato;
  
  //MANDATORY - 
  private Date dataStato;
  
  //MANDATORY - 
  private StatoBean stato;

  public BigDecimal COD_STATO_INOLTRATA = new BigDecimal(1);
  public BigDecimal COD_STATO_IN_ATTESA = new BigDecimal(2);
  public BigDecimal COD_STATO_ACCETTATA = new BigDecimal(3);

  public PraAccreditamentoIterBean()
  {
  
  }

  public PraAccreditamentoIterBean(BigDecimal idRichiestaIter,
                                   String responsabile,
                                   String noteStato,
                                   Date dataStato,
                                   StatoBean stato)
  {
    this.idRichiestaIter = idRichiestaIter;
    this.responsabile = responsabile;
    this.noteStato = noteStato;
    this.dataStato = dataStato;
    this.stato = stato;
  }
  
  public BigDecimal getIdRichiestaIter()
  {
    return this.idRichiestaIter;
  }
  public void setIdRichiestaIter(BigDecimal idRichiestaIter)
  {
    this.idRichiestaIter = idRichiestaIter;
  }

  public BigDecimal getIdRichiestaAcc()
  {
    return this.idRichiestaAcc;
  }
  public void setIdRichiestaAcc(BigDecimal idRichiestaAcc)
  {
    this.idRichiestaAcc = idRichiestaAcc;
  }

  public String getResponsabile()
  {
    return this.responsabile;
  }
  public void setResponsabile(String responsabile)
  {
    this.responsabile = responsabile;
  }

  public String getNoteStato()
  {
    return this.noteStato;
  }
  public void setNoteStato(String noteStato)
  {
    this.noteStato = noteStato;
  }

  public Date getDataStato()
  {
    return this.dataStato;
  }
  public void setDataStato(Date dataStato)
  {
    this.dataStato = dataStato;
  }

  public StatoBean getStato()
  {
    return this.stato;
  }
  public void setStato(StatoBean stato)
  {
    this.stato = stato;
  }
    
  public static PraAccreditamentoIterBean test()
  {
    PraAccreditamentoIterBean bean = new PraAccreditamentoIterBean();
    
    bean.setDataStato(new Date());
    bean.setNoteStato("TEST - NOTE");
    bean.setResponsabile("TEST - RESPOSABILE");
    bean.setStato(StatoBean.test());
    
    return bean;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }
}
