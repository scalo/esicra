package it.saga.siscotel.beans.base;

import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.math.BigDecimal;
import java.util.Date;


public class PraticaBean implements Serializable {

  private BigDecimal idPratica;
  private BigDecimal idEnteDestinatario;
  private Date dataPratica;
  private String oggetto;
  private BigDecimal idServizio;
  private String nomeUtente; 
  private RecapitoBean recapito;

  public PraticaBean(){
    this.recapito = new RecapitoBean(); 
  }
    
  public PraticaBean( BigDecimal idPratica,
                      Date dataPratica,
                      String oggetto,
                      BigDecimal newIdEnteDestinatario,
                      String nomeUtente,
                      BigDecimal newIdServizio,
                      RecapitoBean newRecapito
                      
                      ) {
    this.idPratica=idPratica;
    this.dataPratica=dataPratica;
    this.oggetto=oggetto;
    this.nomeUtente=nomeUtente;
    this.idEnteDestinatario=newIdEnteDestinatario;
    this.idServizio=newIdServizio;
    this.recapito=newRecapito;
  }

  public void setIdPratica(BigDecimal idPratica){
    this.idPratica=idPratica;
  }

  public BigDecimal getIdPratica(){
    return this.idPratica;
  }

  public void setDataPratica(Date dataPratica){
    this.dataPratica=dataPratica;
  }

  public Date getDataPratica(){
    return this.dataPratica;
  }

  public void setOggetto(String oggetto){
    this.oggetto=oggetto;
  }

  public String getOggetto(){
    return this.oggetto;
  }

  public void setNomeUtente(String  nomeUtente){
    this.nomeUtente=nomeUtente;
  }

  public String getNomeUtente(){
    return this.nomeUtente;
  }

  public void setIdServizio(BigDecimal newIdServizio){
    this.idServizio=newIdServizio;
  }

  public BigDecimal getIdServizio(){
    return this.idServizio;
  }
  
  public RecapitoBean getRecapito() {
    return recapito;
  }

  public void setRecapito(RecapitoBean newRecapito) {
    recapito = newRecapito;
  }

  public BigDecimal getIdEnteDestinatario() {
    return idEnteDestinatario;
  }

  public void setIdEnteDestinatario(BigDecimal newIdEnteDestinatario) {
    idEnteDestinatario = newIdEnteDestinatario;
  }


  /**
   * Metodo restituisce il contenuto del bean istanziato 
   * @return toString del Bean
   */ 
  public String toString(){
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static PraticaBean test(){
    PraticaBean b = new PraticaBean();
    b.setIdPratica(new BigDecimal("101"));
    b.setDataPratica( new Date(System.currentTimeMillis()));
    b.setOggetto("Oggetto della pratica");
    b.setIdEnteDestinatario(new BigDecimal("8240"));
    b.setNomeUtente("Bargianni Luca");
    b.setIdServizio(new BigDecimal("555555"));
    b.setRecapito(RecapitoBean.test());
    return b;
  }
  
  public static void main(String[] args){
    System.out.println(PraticaBean.test());
  }

}
