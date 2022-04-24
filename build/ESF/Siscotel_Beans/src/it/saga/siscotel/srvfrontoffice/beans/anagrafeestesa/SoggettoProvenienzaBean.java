package it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa;
import java.math.BigDecimal;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.ProvenienzaBean;

public class SoggettoProvenienzaBean 
{
  private BigDecimal FkidSoggetto;
  private String CodFiscale;
  private BigDecimal IdEnte;
  private ProvenienzaBean[] ListaProvenienza;

  public SoggettoProvenienzaBean()
  {
  }
  
  /**
   * Costruttore SoggettoProvenienzaBean
   * @param pFkidSoggetto Fk Id Soggetto
   * @param pCodFiscale Codice Fiscale
   * @param pIdEnte Id Ente
   * @param pListaProvenienza Lista Provenienza
   */
   public SoggettoProvenienzaBean(BigDecimal pFkidSoggetto,String pCodFiscale,BigDecimal pIdEnte,ProvenienzaBean[] pListaProvenienza)
  {
    this.FkidSoggetto=pFkidSoggetto;
    this.CodFiscale=pCodFiscale;
    this.IdEnte=pIdEnte;
    this.ListaProvenienza=pListaProvenienza;
  }

  public BigDecimal getFkidSoggetto()
  {
    return FkidSoggetto;
  }

  public void setFkidSoggetto(BigDecimal FkidSoggetto)
  {
    this.FkidSoggetto = FkidSoggetto;
  }

  public String getCodFiscale()
  {
    return CodFiscale;
  }

  public void setCodFiscale(String CodFiscale)
  {
    this.CodFiscale = CodFiscale;
  }

  public BigDecimal getIdEnte()
  {
    return IdEnte;
  }

  public void setIdEnte(BigDecimal IdEnte)
  {
    this.IdEnte = IdEnte;
  }

  public ProvenienzaBean[] getListaProvenienza()
  {
    return ListaProvenienza;
  }

  public void setListaProvenienza(ProvenienzaBean[] ListaProvenienza)
  {
    this.ListaProvenienza = ListaProvenienza;
  }
  
   /**
     * Metodo restituisce il contenuto del bean istanziato 
     * @return toString del Bean SoggettoProvenienzaBean
     */
    public String toString(){

        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("FkidSoggetto = "+getFkidSoggetto()+", ");  
        sb.append("CodFiscale = "+getCodFiscale()+", ");  
        sb.append("IdEnte = "+getIdEnte()+", "); 
        if(ListaProvenienza==null){sb.append("ListaProvenienza = null ");}
            else{sb.append("ListaProvenienza = ");
              sb.append("{ ");
              for(int i=0;i<((ListaProvenienza!=null)?ListaProvenienza.length:-1);i++){
                 sb.append(ListaProvenienza[i]);
              };
               sb.append(" }");
            } 
            
        sb.append(" ] ");
        return sb.toString();
     }

  /**
   * Metodo di Test per il SoggettoProvenienzaBean
   * @return Restituisce SoggettoProvenienzaBean istanziato con valori di test
   */
    public static SoggettoProvenienzaBean test(){
        return new SoggettoProvenienzaBean(new BigDecimal("123456") , "CodFiscale", new BigDecimal("7734"),     
        new ProvenienzaBean[]{new ProvenienzaBean().test(),new ProvenienzaBean().test()}
        );            
    }
  
  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    SoggettoProvenienzaBean soggettoProvenienzaBean = new SoggettoProvenienzaBean();
    System.out.println("SoggettoProvenienzaBean = "+soggettoProvenienzaBean.test());
  }
  
  
}