package it.saga.egov.esicra.xml.test;

import java.util.Date;
import java.math.BigDecimal;

public class PagBollettaRataBean 
{
  private BigDecimal IdPratica;
  private Date DataPagRata;
  private BigDecimal ImportoPagato;
  private String DesPagamento;
  private String Distinta;
  private String Autorizzazione;

  public PagBollettaRataBean()
  {
  }
  
  /**
   * Costruisce bean.
   * @param newIdPratica
   * @param newDataPagRata
   * @param nemImportoPagato
   * @param newDesPagamento
   * @deprecated
   */
  public PagBollettaRataBean(BigDecimal newIdPratica,  Date newDataPagRata, BigDecimal nemImportoPagato, String newDesPagamento)
  {   
      this.IdPratica = newIdPratica;
      this.DataPagRata = newDataPagRata;
      this.ImportoPagato = nemImportoPagato;
      this.DesPagamento = newDesPagamento;
  }
  
  public PagBollettaRataBean(BigDecimal newIdPratica,  Date newDataPagRata, BigDecimal nemImportoPagato, String newDesPagamento, String newDistinta, String newAutorizzazione)
  {   
      this.IdPratica = newIdPratica;
      this.DataPagRata = newDataPagRata;
      this.ImportoPagato = nemImportoPagato;
      this.DesPagamento = newDesPagamento;
      this.Distinta = newDistinta;
      this.Autorizzazione = newAutorizzazione;
  }


  public BigDecimal getIdPratica()
  {
    return IdPratica;
  }

  public void setIdPratica(BigDecimal newIdPratica)
  {
    IdPratica = newIdPratica;
  }
  
  public Date getDataPagRata()
  {
    return DataPagRata;
  }

  public void setDataPagRata(Date newDataPagRata)
  {
    DataPagRata = newDataPagRata;
  }

  public BigDecimal getImportoPagato()
  {
    return ImportoPagato;
  }

  public void setImportoPagato(BigDecimal newImportoPagato)
  {
    ImportoPagato = newImportoPagato;
  }

  public String getDesPagamento()
  {
    return DesPagamento;
  }

  public void setDesPagamento(String newDesPagamento)
  {
    DesPagamento = newDesPagamento;
  }

 public void setDistinta(String Distinta)
  {
    this.Distinta = Distinta;
  }


  public String getDistinta()
  {
    return Distinta;
  }

  public String getAutorizzazione()
  {
    return Autorizzazione;
  }

  public void setAutorizzazione(String newAutorizzazione)
  {
    Autorizzazione = newAutorizzazione;
  }

 public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append("IdPratica = "+getIdPratica().toString()+", ");
        sb.append("DataPagRata = "+getDataPagRata()+", ");
        sb.append("ImportoPagato = "+getImportoPagato()+", ");
        sb.append("DesPagamento = "+getDesPagamento()+", ");
        sb.append("Distinta = "+getDistinta()+", ");
        sb.append("Autorizzazione = "+getAutorizzazione());
        sb.append(" ]");
        return sb.toString();
        }

        
  public static PagBollettaRataBean test(){
      PagBollettaRataBean pagBollettaRataBean = new PagBollettaRataBean(new BigDecimal("5555"),new Date(),new BigDecimal("222.2"), "DesPagamento","Distinta", "Autorizzazione");
    return pagBollettaRataBean;
  }
  
  public static void main(String[] args) {
   
    System.out.println(PagBollettaRataBean.test());
  }


 

}