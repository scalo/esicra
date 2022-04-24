package it.saga.siscotel.srvfrontoffice.beans.base;

import it.saga.siscotel.srvfrontoffice.beans.commercio.IscrizioneCCIABean;
import java.math.BigDecimal;
import it.saga.siscotel.srvfrontoffice.beans.base.IndirizzoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.SchedaSoggettoBean;

public class SoggettoGiuridicoBean 
{
  private String RagioneSociale;
  private String PartitaIva;
  private BigDecimal IdEnte;
  private IndirizzoBean IndirizzoSede;
  private SchedaSoggettoBean RappresentanteLegale;
  private IscrizioneCCIABean IscrizioneCCIA;

  public SoggettoGiuridicoBean()
  {
    this.IndirizzoSede = new IndirizzoBean();
    this.RappresentanteLegale = new SchedaSoggettoBean();
    this.IscrizioneCCIA = new IscrizioneCCIABean();
  }

   public SoggettoGiuridicoBean(String newRagioneSociale,String newPartitaIva,BigDecimal newIdEnte, IndirizzoBean newIndirizzoSede, SchedaSoggettoBean newRappresentanteLegale,IscrizioneCCIABean newIscrizioneCCIA)
  {
    this.RagioneSociale = newRagioneSociale;
    this.PartitaIva = newPartitaIva;
    this.IdEnte = newIdEnte;
    this.IndirizzoSede = newIndirizzoSede;
    this.RappresentanteLegale = newRappresentanteLegale;
    this.IscrizioneCCIA = newIscrizioneCCIA;
  
  }

  public String getRagioneSociale()
  {
    return RagioneSociale;
  }

  public void setRagioneSociale(String newRagioneSociale)
  {
    RagioneSociale = newRagioneSociale;
  }

  public String getPartitaIva()
  {
    return PartitaIva;
  }

  public void setPartitaIva(String newPartitaIva)
  {
    PartitaIva = newPartitaIva;
  }

  public BigDecimal getIdEnte()
  {
    return IdEnte;
  }

  public void setIdEnte(BigDecimal newIdEnte)
  {
    IdEnte = newIdEnte;
  }

  public IndirizzoBean getIndirizzoSede()
  {
    return IndirizzoSede;
  }

  public void setIndirizzoSede(IndirizzoBean newIndirizzoSede)
  {
    IndirizzoSede = newIndirizzoSede;
  }

  public SchedaSoggettoBean getRappresentanteLegale()
  {
    return RappresentanteLegale;
  }

  public void setRappresentanteLegale(SchedaSoggettoBean newRappresentanteLegale)
  {
    RappresentanteLegale = newRappresentanteLegale;
  }

  public IscrizioneCCIABean getIscrizioneCCIA()
  {
    return IscrizioneCCIA;
  }

  public void setIscrizioneCCIA(IscrizioneCCIABean newIscrizioneCCIA)
  {
    IscrizioneCCIA = newIscrizioneCCIA;
  }


 public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    sb.append("RagioneSociale = "+getRagioneSociale()+", ");
    sb.append("PartitaIva = "+getPartitaIva()+", ");
    sb.append("IdEnte = "+getIdEnte()+", ");
    if(IndirizzoSede==null){sb.append("IndirizzoSede = null ");
    }else{sb.append("IndirizzoSede = "+getIndirizzoSede().toString()+", ");}
    if(RappresentanteLegale==null){sb.append("RappresentanteLegale = null ");
    }else{sb.append("RappresentanteLegale = "+getRappresentanteLegale().toString()+", ");}
    if(IscrizioneCCIA==null){sb.append("IscrizioneCCIA = null ");
    }else{sb.append("IscrizioneCCIA = "+getIscrizioneCCIA().toString());}    
    sb.append(" ]");
    return sb.toString();
  }
 
  public static SoggettoGiuridicoBean test(){
    return new SoggettoGiuridicoBean("RagioneSociale", "PartitaIva" , new BigDecimal("7734"), new IndirizzoBean().test(), new SchedaSoggettoBean().test(),  new IscrizioneCCIABean().test());
  }
 
  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.SoggettoGiuridicoBean.test());

  }

}