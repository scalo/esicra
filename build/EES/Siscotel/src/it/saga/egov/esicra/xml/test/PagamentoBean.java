package it.saga.egov.esicra.xml.test;

import java.math.*;
import java.util.Date;
import java.math.BigDecimal;
import java.text.*;


public class PagamentoBean 
{ 
  private BigDecimal idPratica;
  private String codTipoPagamento;
  private String	codEnteTipoPagamento;
  private String causalePagamento;
  private String desTipoPagamento;
  private Date dataPagamento;
  private Date dataRegistrazione;
  private BigDecimal importoPagato;
  private BigDecimal importoDaPagare;
  private BigDecimal importoTotale;
  private Integer idCanale;
  private String desCanale;
  private String distinta;
  private Date dataDistinta;
  private Integer numRata;
  
  public PagamentoBean(){
  }


  /**
   * Non usare.
   * 
   * @deprecated
   */
  public PagamentoBean(BigDecimal newIdPratica, String newCodPagamento, String newDesPagamento,
                      String newDesTipoPagamento, Date newDataPagamento , 
                      BigDecimal newImporto , Integer newIdCanale) {
    
  }
  
  public PagamentoBean(BigDecimal idPratica, String codTipoPagamento, String	codEnteTipoPagamento,
                      String causalePagamento, String desTipoPagamento , Date dataPagamento,
                      Date dataRegistrazione , BigDecimal importoPagato, BigDecimal importoDaPagare,
                      BigDecimal importoTotale, Integer idCanale, String desCanale,
                      String distinta, Date dataDistinta, Integer numRata ) {
    this.idPratica = idPratica;
    this.codTipoPagamento = codTipoPagamento;
    this.codEnteTipoPagamento = codEnteTipoPagamento;
    this.causalePagamento = causalePagamento;
    this.desTipoPagamento = desTipoPagamento;
    this.dataPagamento = dataPagamento;
    this.dataRegistrazione = dataRegistrazione;
    this.importoPagato = importoPagato;
    this.importoDaPagare = importoDaPagare;
    this.importoTotale = importoTotale;
    this.idCanale = idCanale;
    this.desCanale = desCanale;
    this.distinta = distinta;
    this.dataDistinta = dataDistinta;
    this.numRata = numRata;
  }



  public String toString(){
    String beanToString = new String("[IdPratica ="+getIdPratica()+ ", "+
                                        "codTipoPagamento="+this.getCodTipoPagamento()+", "+
                                        "Causale="+getCausale()+ ","+
                                        "DesTipoPagamento="+getDesTipoPagamento()+", "+
                                        "ImportoPagato="+getImportoPagato()+", "+
                                        "IdCanale="+getIdCanale()+", "+
                                        "DataPagamento="+ getDataPagamento()+
                                        
                                     "]");
    return beanToString;
  }

  public static PagamentoBean test(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    PagamentoBean bean=null;
    try{
      bean = new PagamentoBean(     
        new BigDecimal("1001"),     //IdPratica
        "1",                  //codTipoPagamento
        "1",   //codEnteTipoPagamento
        "pagamneto permesso di circolazione",            //causalePagamento
        "Contrassegno",    //desTipoPagamento
        sdf.parse("1999-12-12"),    //dataPagamento
        sdf.parse("1999-12-12"), //dataRegistrazione
        new BigDecimal("1000"), //importoPagato
        new BigDecimal("1000"), //importoPagato
        new BigDecimal("1000"), //importoPagato
        new Integer("3"),//idCanale
        "web",//desCanale
        "aa", //distinta
         sdf.parse("1999-12-12"),//dataDistinta
        new Integer(1)//numRata
        );
      
    }catch(Exception e){
      e.printStackTrace();
    }
    return bean;
  }

  public static void main(String[] args){
    PagamentoBean bean =PagamentoBean.test();
    System.out.println(bean);
  }


  public void setIdPratica(BigDecimal idPratica)
  {
    this.idPratica = idPratica;
  }


  public BigDecimal getIdPratica()
  {
    return idPratica;
  }


  public void setCodTipoPagamento(String codTipoPagamento)
  {
    this.codTipoPagamento = codTipoPagamento;
  }


  public String getCodTipoPagamento()
  {
    return codTipoPagamento;
  }


  public void setCodEnteTipoPagamento(String codEnteTipoPagamento)
  {
    this.codEnteTipoPagamento = codEnteTipoPagamento;
  }


  public String getCodEnteTipoPagamento()
  {
    return codEnteTipoPagamento;
  }


  public void setCausale(String causale)
  {
    this.causalePagamento = causale;
  }


  public String getCausale()
  {
    return causalePagamento;
  }


  public void setDesTipoPagamento(String desTipoPagamento)
  {
    this.desTipoPagamento = desTipoPagamento;
  }


  public String getDesTipoPagamento()
  {
    return desTipoPagamento;
  }


  public void setDataPagamento(Date dataPagamento)
  {
    this.dataPagamento = dataPagamento;
  }


  public Date getDataPagamento()
  {
    return dataPagamento;
  }


  public void setDataRegistrazione(Date dataRegistrazione)
  {
    this.dataRegistrazione = dataRegistrazione;
  }


  public Date getDataRegistrazione()
  {
    return dataRegistrazione;
  }


  public void setImportoPagato(BigDecimal importoPagato)
  {
    this.importoPagato = importoPagato;
  }


  public BigDecimal getImportoPagato()
  {
    return importoPagato;
  }


  public void setImportoDaPagare(BigDecimal importoDaPagare)
  {
    this.importoDaPagare = importoDaPagare;
  }


  public BigDecimal getImportoDaPagare()
  {
    return importoDaPagare;
  }


  public void setImportoTotale(BigDecimal importoTotale)
  {
    this.importoTotale = importoTotale;
  }


  public BigDecimal getImportoTotale()
  {
    return importoTotale;
  }


  public void setIdCanale(Integer idCanale)
  {
    this.idCanale = idCanale;
  }


  public Integer getIdCanale()
  {
    return idCanale;
  }


  public void setDesCanale(String desCanale)
  {
    this.desCanale = desCanale;
  }


  public String getDesCanale()
  {
    return desCanale;
  }


  public void setDistinta(String distinta)
  {
    this.distinta = distinta;
  }


  public String getDistinta()
  {
    return distinta;
  }


  public void setDataDistinta(Date dataDistinta)
  {
    this.dataDistinta = dataDistinta;
  }


  public Date getDataDistinta()
  {
    return dataDistinta;
  }


  public void setNumRata(Integer numRata)
  {
    this.numRata = numRata;
  }


  public Integer getNumRata()
  {
    return numRata;
  }



}