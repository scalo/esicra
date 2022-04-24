package it.saga.siscotel.srvfrontoffice.beans.base;

import java.util.Date;
import java.text.*;
import it.saga.siscotel.srvfrontoffice.beans.base.PermessoSoggiornoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.SoggettoBean;
 
public class SchedaSoggettoBean  {
  private SoggettoBean Soggetto;
  private PermessoSoggiornoBean PermessoSoggiorno;
  private String StatoCivile;
  private String Cittadinanza;
  private Boolean Certificabile;
  

  public SchedaSoggettoBean() {
    this.Soggetto = new SoggettoBean();
    this.PermessoSoggiorno = new PermessoSoggiornoBean();
  }

  /**
   * Costruttore SchedaSoggettoBean
   * @deprecated 
   * @param pSoggetto Soggetto
   * @param pStatoCivile Stato Civile
   * @param pCittadinanza Cittadinanza
   * @param pCertificabile Certificabile
   */
  public SchedaSoggettoBean(
        SoggettoBean pSoggetto,
        String pStatoCivile,
        String pCittadinanza,
        Boolean pCertificabile) {
    this.Soggetto = pSoggetto;
    this.StatoCivile = pStatoCivile;
    this.Cittadinanza = pCittadinanza;
    this.Certificabile  = pCertificabile;
    this.PermessoSoggiorno = new PermessoSoggiornoBean();
  }
  
  /**
   * Costruttore per  SchedaSoggettoBean
   * @param pSoggetto Soggetto
   * @param pStatoCivile Stato Civile
   * @param pCittadinanza Cittadinanza
   * @param pCertificabile Certificabile
   * @param pPermessoSoggiorno PermessoSoggiorno
   */
  public SchedaSoggettoBean(
        SoggettoBean pSoggetto,
        String pStatoCivile,
        String pCittadinanza,
        Boolean pCertificabile, 
        PermessoSoggiornoBean pPermessoSoggiorno) {
    this.Soggetto = pSoggetto;
    this.StatoCivile = pStatoCivile;
    this.Cittadinanza = pCittadinanza;
    this.Certificabile  = pCertificabile;
    this.PermessoSoggiorno = pPermessoSoggiorno;
  }
  
  public SoggettoBean getSoggetto() {
    return Soggetto;
  }

  public void setSoggetto(SoggettoBean pSoggetto) {
    Soggetto = pSoggetto;
  }

  public String getStatoCivile() {
    return this.StatoCivile;
  }

  public void setStatoCivile(String pStatoCivile) {
    StatoCivile = pStatoCivile;
  }

  public String getCittadinanza() {
    return this.Cittadinanza;
  }

  public void setCittadinanza(String pCittadinanza) {
    Cittadinanza = pCittadinanza;
  }

  public void setCertificabile(Boolean pCertificabile) {
    Certificabile = pCertificabile;
  }

  public Boolean getCertificabile() {
    return Certificabile;
  }

  public Boolean isCertificabile() {
    return getCertificabile();
  }
  
  public void setPermessoSoggiorno(PermessoSoggiornoBean PermessoSoggiorno)
  {
    this.PermessoSoggiorno = PermessoSoggiorno;
  }


  public PermessoSoggiornoBean getPermessoSoggiorno()
  {
    return PermessoSoggiorno;
  }
  public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    sb.append("Soggetto="+getSoggetto().toString()+", ");
    sb.append("StatoCivile="+getStatoCivile()+", ");
    sb.append("Cittadinanza="+getCittadinanza()+", ");
    sb.append("Certificabile="+getCertificabile()+", ");
    sb.append("PermessoSoggiorno="+getPermessoSoggiorno().toString());
    sb.append(" ]");
    return sb.toString();
  }

  public static SchedaSoggettoBean test(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
    return new SchedaSoggettoBean(
         it.saga.siscotel.srvfrontoffice.beans.base.SoggettoBean.test(),
          "Celibe",
          "Straniera",
          new Boolean(false),
          PermessoSoggiornoBean.test()
    );
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.SchedaSoggettoBean.test());
  }




}