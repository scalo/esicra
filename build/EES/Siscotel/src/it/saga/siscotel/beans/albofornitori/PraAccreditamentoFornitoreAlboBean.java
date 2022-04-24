package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PraAccreditamentoFornitoreAlboBean implements Serializable
{
  private BigDecimal idRichiesta;
  private BigDecimal idEnte;
  private BigDecimal idEnteRichiedente;
  private BigDecimal idEnteDestinatario;
  private BigDecimal idAlbo;
    
  private Date dataPresentazione;

  private String cognomeRichiedente;
  private String nomeRichiedente;
  private String desEnteDestinatario;
  private String desEnteRichiedente;
    
  private FornitoreBean datiFornitore;
  private FornitureBean[] datiForniture;
  private CategoriaBean[] datiCategorie;  
  private PraAccreditamentoIterBean[] accreditamentoIter;
  
  public PraAccreditamentoFornitoreAlboBean(BigDecimal idRichiesta,
                                            BigDecimal idAlbo,
                                            BigDecimal idEnteDestinatario,
                                            BigDecimal idEnteRichiedente,
                                            String desEnteDestinatario,
                                            String desEnteRichiedente,
                                            Date dataPresentazione,
                                            String cognomeRichiedente,
                                            String nomeRichiedente,
                                            FornitoreBean datiFornitore,
                                            FornitureBean[] datiForniture,
                                            CategoriaBean[] datiCategorie,
                                            PraAccreditamentoIterBean[] accreditamentoIter)
  {
    this.idRichiesta = idRichiesta;
    this.idEnteDestinatario = idEnteDestinatario;
    this.idAlbo = idAlbo;
    this.dataPresentazione = dataPresentazione;
    this.cognomeRichiedente = cognomeRichiedente;
    this.nomeRichiedente = nomeRichiedente;
    this.idEnteRichiedente = idEnteRichiedente;
    this.datiFornitore = datiFornitore;
    this.datiForniture = datiForniture;
    this.idEnteRichiedente = idEnteRichiedente;
    this.accreditamentoIter = accreditamentoIter;
    this.datiCategorie = datiCategorie;
    this.desEnteDestinatario = desEnteDestinatario;
    this.desEnteRichiedente = desEnteRichiedente;
  }

  public PraAccreditamentoFornitoreAlboBean()
  {
  
  }
  
  public BigDecimal getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta(BigDecimal idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }

  public BigDecimal getIdEnte()
  {
    return this.idEnte;
  }
  public void setIdEnte(BigDecimal idEnte)
  {
    this.idEnte = idEnte;
  }

  public BigDecimal getIdAlbo()
  {
    return this.idAlbo;
  }
  public void setIdAlbo(BigDecimal idAlbo)
  {
    this.idAlbo = idAlbo;
  }

  public Date getDataPresentazione()
  {
    return this.dataPresentazione;
  }
  public void setDataPresentazione(Date dataPresentazione)
  {
    this.dataPresentazione = dataPresentazione;
  }

  public String getCognomeRichiedente()
  {
    return this.cognomeRichiedente;
  }
  public void setCognomeRichiedente(String cognomeRichiedente)
  {
    this.cognomeRichiedente = cognomeRichiedente;
  }

  public String getNomeRichiedente()
  {
    return this.nomeRichiedente;
  }
  public void setNomeRichiedente(String nomeRichiedente)
  {
    this.nomeRichiedente = nomeRichiedente;
  }

  public BigDecimal getIdEnteRichiedente()
  {
    return this.idEnteRichiedente;
  }
  public void setIdEnteRichiedente(BigDecimal idEnteRichiedente)
  {
    this.idEnteRichiedente = idEnteRichiedente;
  }

  public BigDecimal getIdEnteDestinatario()
  {
    return this.idEnteDestinatario;
  }
  public void setIdEnteDestinatario(BigDecimal idEnteDestinatario)
  {
    this.idEnteDestinatario = idEnteDestinatario;
  }

  public String getDesEnteDestinatario()
  {
    return this.desEnteDestinatario;
  }
  public void setDesEnteDestinatario(String desEnteDestinatario)
  {
    this.desEnteDestinatario = desEnteDestinatario;
  }

  public String getDesEnteRichiedente()
  {
    return this.desEnteRichiedente;
  }
  public void setDesEnteRichiedente(String desEnteRichiedente)
  {
    this.desEnteRichiedente = desEnteRichiedente;
  }

  public FornitoreBean getDatiFornitore()
  {
    return this.datiFornitore;
  }
  public void setDatiFornitore(FornitoreBean datiFornitore)
  {
    this.datiFornitore = datiFornitore;
  }

  public PraAccreditamentoIterBean[] getAccreditamentoIter()
  {
    return this.accreditamentoIter;
  }
  public void setAccreditamentoIter(PraAccreditamentoIterBean[] accreditamentoIter)
  {
    this.accreditamentoIter = accreditamentoIter;
  }

  public FornitureBean[] getDatiForniture()
  {
    return this.datiForniture;
  }
  public void setDatiForniture(FornitureBean[] datiForniture)
  {
    this.datiForniture = datiForniture;
  }

  public CategoriaBean[] getDatiCategorie()
  {
    return this.datiCategorie;
  }
  public void setDatiCategorie(CategoriaBean[] datiCategorie)
  {
    this.datiCategorie = datiCategorie;
  }
  
  public static PraAccreditamentoFornitoreAlboBean test()
  {
    PraAccreditamentoFornitoreAlboBean bean = new PraAccreditamentoFornitoreAlboBean();
    bean.setCognomeRichiedente("TEST COGNOME");
    bean.setNomeRichiedente("TEST NOME");
    bean.setDataPresentazione(new Date());
    bean.setIdEnteRichiedente(new BigDecimal(8240));
    bean.setIdEnte(new BigDecimal(8240));
    bean.setIdAlbo(new BigDecimal(1));
    bean.setDatiFornitore(FornitoreBean.testSoggettoFisico());
    bean.setDatiForniture(new FornitureBean[]
                          {FornitureBean.test()});
    bean.setAccreditamentoIter(new PraAccreditamentoIterBean[]
                                {PraAccreditamentoIterBean.test()});
    bean.setDatiCategorie(new CategoriaBean[]
                          {CategoriaBean.test()});
                                
    return bean;
  }
    
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

}
