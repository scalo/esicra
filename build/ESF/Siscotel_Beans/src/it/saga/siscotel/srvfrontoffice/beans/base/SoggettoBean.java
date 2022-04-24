package it.saga.siscotel.srvfrontoffice.beans.base;

import java.util.Date;
import java.text.*;
import java.math.BigDecimal;
import it.saga.siscotel.srvfrontoffice.beans.base.ComuneBean;
import it.saga.siscotel.srvfrontoffice.beans.base.IndirizzoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.LocalitaBean;

public class SoggettoBean  { 
  private String CodiceFiscale;
  private String Cognome;
  private String Nome;
  private String Sesso;
  private Date DataNascita;
  private ComuneBean ComuneNascita;
  private LocalitaBean LocalitaNascita;
  private IndirizzoBean Residenza;
  private BigDecimal IdEnte;

  public SoggettoBean() {
    this.ComuneNascita = new ComuneBean();
    this.LocalitaNascita = new LocalitaBean();
    this.Residenza = new IndirizzoBean();
  }

  public SoggettoBean(
        String pCodiceFiscale,
        String pCognome,
        String pNome,
        String pSesso,
        Date pDataNascita,
        ComuneBean pComune,
        LocalitaBean pLocalita,
        IndirizzoBean pIndirizzo,
        BigDecimal pIdEnte
        ) {

    this.CodiceFiscale=pCodiceFiscale;
    this.Cognome=pCognome;
    this.Nome=pNome;
    this.Sesso=pSesso;
    this.DataNascita=pDataNascita;
    this.ComuneNascita=pComune;
    this.LocalitaNascita=pLocalita;
    this.Residenza=pIndirizzo;
    this.IdEnte=pIdEnte;
    
  }
  
  public String getCodiceFiscale() {
    return CodiceFiscale;
  }

  public void setCodiceFiscale(String pCodiceFiscale) {
    CodiceFiscale = pCodiceFiscale;
  }

  public String getCognome() {
    return Cognome;
  }

  public void setCognome(String pCognome) {
    Cognome = pCognome;
  }

  public String getNome() {
    return Nome;
  }

  public void setNome(String pNome) {
    Nome = pNome;
  }

  public String getSesso() {
    return this.Sesso;
  }

  public void setSesso(String pSesso) {
    Sesso = pSesso;
  }
  
  public Date getDataNascita() {
    return DataNascita;
  }

  public void setDataNascita(Date pDataNascita) {
    DataNascita = pDataNascita;
  }

  public ComuneBean getComuneNascita() {
    return ComuneNascita;
  }

  public void setComuneNascita(ComuneBean pComuneNascita) {
    ComuneNascita = pComuneNascita;
  }

  public LocalitaBean getLocalitaNascita() {
    return LocalitaNascita;
  }

  public void setLocalitaNascita(LocalitaBean pLocalitaNascita) {
    LocalitaNascita = pLocalitaNascita;
  }

  public IndirizzoBean getResidenza() {
    return Residenza;
  }

  public void setResidenza(IndirizzoBean pResidenza) {
    Residenza = pResidenza;
  }

  public BigDecimal getIdEnte() {
    return IdEnte;
  }

  public void setIdEnte(BigDecimal newIdEnte) {
    IdEnte = newIdEnte;
  }

  public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    sb.append("CodiceFiscale="+getCodiceFiscale()+", ");
    sb.append("Cognome="+getCognome()+", ");
    sb.append("Nome="+getNome()+", ");
    sb.append("Sesso="+getSesso()+", ");
    sb.append("DataNascita="+getDataNascita()+", ");
    sb.append("ComuneNascita="+getComuneNascita()+", ");
    sb.append("LocalitaNasciat="+getLocalitaNascita()+", ");
    sb.append("Residenza="+getResidenza()+", ");
    sb.append("IdEnte="+getIdEnte());
    sb.append(" ]");
    return sb.toString();
  }

  public static SoggettoBean test(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
    return new SoggettoBean(
          "PNLGNN77H8763HJZ",
          "Pinoli",
          "Gianni",
          "M",
          sdf.parse("1960-04-20"),
          ComuneBean.test(),
          null,
          IndirizzoBean.test(),
          new BigDecimal("7734")
    );
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }
  
  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.SoggettoBean.test());
    
  }

  

}