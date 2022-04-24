package it.saga.egov.esicra.xml.test;

import java.util.Date;
import java.math.BigDecimal;


public class RateBollettaBean 
{
  private Integer NumRata;
  private Date DtScadenzaRata;
  private BigDecimal ImportoRata;
  //private String Stato; ELIMINATO ATTRIBUTO
  private PagBollettaRataBean PagBollettaRata;
  private Float iva;
  private Double doppio;
  

  public RateBollettaBean()
  {
  }

   public RateBollettaBean(Integer newNumRata, Date newDtScadenzaRata, BigDecimal newImportoRata, PagBollettaRataBean newPagBollettaRata,Float iva,Double doppio)
  {
      this.NumRata = newNumRata;
      this.DtScadenzaRata = newDtScadenzaRata;
      this.ImportoRata = newImportoRata;
      //this.Stato = newStato;
      this.PagBollettaRata = newPagBollettaRata;
      this.iva=iva;
      this.doppio=doppio;
  }

  public Integer getNumRata()
  {
    return NumRata;
  }

  public void setNumRata(Integer newNumRata)
  {
    NumRata = newNumRata;
  }

  public Date getDtScadenzaRata()
  {
    return DtScadenzaRata;
  }

  public void setDtScadenzaRata(Date newDtScadenzaRata)
  {
    DtScadenzaRata = newDtScadenzaRata;
  }

  public BigDecimal getImportoRata()
  {
    return ImportoRata;
  }

  public void setImportoRata(BigDecimal newImportoRata)
  {
    ImportoRata = newImportoRata;
  }

 /* public String getStato()
  {
    return Stato;
  }

  public void setStato(String newStato)
  {
    Stato = newStato;
  }
  */
   public PagBollettaRataBean getPagBollettaRata()
  {
    return PagBollettaRata;
  }

  /**
   *    li mortacci c'era setPagBollettaRataBean
   *    invece di setPagBollettaRata
   * 
   */
  public void setPagBollettaRata(PagBollettaRataBean newPagBollettaRata)
  {
    PagBollettaRata = newPagBollettaRata;
  }

  public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append("NumRata = "+getNumRata()+", ");
        sb.append("DtScadenzaRata = "+getDtScadenzaRata().toString()+", ");
        sb.append("ImportoRata = "+getImportoRata().toString()+", ");
        // sb.append("Stato = "+getStato()+", "); 
        if(PagBollettaRata==null){sb.append("PagBollettaRata = null ");}else{
        sb.append("PagBollettaRata = "+getPagBollettaRata().toString());}
        sb.append("Iva = "+getIva()+", ");
        sb.append("Doppio = "+getDoppio());
        sb.append(" ]");
        return sb.toString();
        }

        
  public static RateBollettaBean test(){
    RateBollettaBean rateBollettaBean = new RateBollettaBean( new Integer(1) , new Date(), new BigDecimal("12345"),  new PagBollettaRataBean().test() ,new Float(123.33),new Double(33.337));
    return rateBollettaBean;
  }
  
  public static void main(String[] args) {
   
    System.out.println(RateBollettaBean.test());
  }

    public Float getIva() {
        return iva;
    }

    public void setIva(Float newIva) {
        iva = newIva;
    }

    public Double getDoppio() {
        return doppio;
    }

    public void setDoppio(Double newDoppio) {
        doppio = newDoppio;
    }


  
}