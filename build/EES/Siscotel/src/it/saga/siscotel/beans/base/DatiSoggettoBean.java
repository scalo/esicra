package it.saga.siscotel.beans.base;

import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.util.Date;
import java.text.*;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DatiSoggettoBean  {

  private String codiceFiscale;
  private String cognome;
  private String nome;
  private String sesso;
  private Date dataNascita;
  private String luogoNascita;
  private DatiIndirizzoBean residenza;

  /**
   * Costruttore SoggettoBean
   */
  public DatiSoggettoBean() {
    this.residenza = new DatiIndirizzoBean();
  }

  /**
   * Costruttore DatiSoggettoBean
   * @param pCodiceFiscale CodiceFiscale
   * @param pCognome Cognome
   * @param pNome Nome
   * @param pSesso Sesso
   * @param pDataNascita DataNascita
   * @param pLuogoNascita LuogoNascita
   * @param pIndirizzo Indirizzo
   */
  public DatiSoggettoBean(String pCodiceFiscale,
                      String pCognome,
                      String pNome,
                      String pSesso,
                      Date pDataNascita,
                      String pLuogoNascita,
                      DatiIndirizzoBean pIndirizzo
                      ) {

    this.codiceFiscale=pCodiceFiscale;
    this.cognome=pCognome;
    this.nome=pNome;
    this.sesso=pSesso;
    this.dataNascita=pDataNascita;
    this.luogoNascita=pLuogoNascita;
    this.residenza=pIndirizzo;
    
  }
  
  public String getCodiceFiscale() {
    return this.codiceFiscale;
  }

  public void setCodiceFiscale(String pCodiceFiscale) {
    this.codiceFiscale = pCodiceFiscale;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String pCognome) {
    this.cognome = pCognome;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String pNome) {
    this.nome = pNome;
  }

  public String getSesso() {
    return this.sesso;
  }

  public void setSesso(String pSesso) {
    this.sesso = pSesso;
  }
  
  public Date getDataNascita() {
    return this.dataNascita;
  }

  public void setDataNascita(Date pDataNascita) {
    this.dataNascita = pDataNascita;
  }

  public String getLuogoNascita() {
    return this.luogoNascita;
  }

  public void setLuogoNascita(String pLuogoNascita) {
    this.luogoNascita = pLuogoNascita;
  }

  public DatiIndirizzoBean getResidenza() {
    return residenza;
  }

  public void setResidenza(DatiIndirizzoBean pResidenza) {
    residenza = pResidenza;
  }


  /**
   * Metodo restituisce il contenuto del bean istanziato 
   * @return toString del Bean 
   * */
  public String toString(){
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static DatiSoggettoBean test(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DatiSoggettoBean b=null;
    try{
      b = new DatiSoggettoBean();
      b.setCodiceFiscale("PNLGNN77H8763HJZ");
      b.setCognome("Pinoli");
      b.setNome("Gianni");
      b.setSesso("M");
      b.setDataNascita(sdf.parse("1960-04-20"));
      b.setLuogoNascita("ComuneAmico");
      b.setResidenza(DatiIndirizzoBean.test());
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
    return b;
  }
  
    public static DatiSoggettoBean test1(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DatiSoggettoBean b=null;
    try{
      b = new DatiSoggettoBean();
      b.setCodiceFiscale("PNLMRC98M9753FTR");
      b.setCognome("Pinoli");
      b.setNome("Marco");
      b.setSesso("M");
      b.setDataNascita(sdf.parse("1980-10-12"));
      b.setLuogoNascita("ComuneAmico");
      b.setResidenza(DatiIndirizzoBean.test());
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
    return b;
  }

  public static void main(String[] args){
    System.out.println(DatiSoggettoBean.test());
  } 

}
