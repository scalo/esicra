package it.saga.siscotel.beans.base;

import java.io.Serializable;
import it.saga.siscotel.beans.SiscotelToStringStyle;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Date;

public class PagamentoBean  implements Serializable {

  private BigDecimal idPratica;
  private String tipoPagamento;
  private String causalePagemento;
  private Date dataPagamento;
  private BigDecimal importoPagato;
  private BigDecimal importoDaPagare;
  private Integer numRata;

  public PagamentoBean() {
  }

  public BigDecimal getIdPratica() {
    return idPratica;
  }

  public void setIdPratica(BigDecimal idPratica) {
    this.idPratica = idPratica;
  }

  public String getTipoPagamento() {
    return tipoPagamento;
  }

  public void setTipoPagamento(String tipoPagamento) {
    this.tipoPagamento = tipoPagamento;
  }

  public String getCausalePagemento() {
    return causalePagemento;
  }

  public void setCausalePagemento(String causalePagemento) {
    this.causalePagemento = causalePagemento;
  }

  public Date getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public BigDecimal getImportoPagato() {
    return importoPagato;
  }

  public void setImportoPagato(BigDecimal importoPagato) {
    this.importoPagato = importoPagato;
  }

  public BigDecimal getImportoDaPagare() {
    return importoDaPagare;
  }

  public void setImportoDaPagare(BigDecimal importoDaPagare) {
    this.importoDaPagare = importoDaPagare;
  }

  public Integer getNumRata() {
    return numRata;
  }

  public void setNumRata(Integer numRata) {
    this.numRata = numRata;
  }
  
    public String toString(){
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }

  public static PagamentoBean test(){
    PagamentoBean b = new PagamentoBean();
    b.setIdPratica(new BigDecimal("555"));
    b.setTipoPagamento("Tipo pagamento Test");
    b.setCausalePagemento("Causale pagamento test");
    b.setDataPagamento(new Date(System.currentTimeMillis()));
    b.setImportoPagato(new BigDecimal("50"));
    b.setImportoDaPagare(new BigDecimal("100"));
    b.setNumRata(new Integer(1));
    return b;
  }
  
  public static void main(String[] args){
    System.out.println(PagamentoBean.test());
  }
}