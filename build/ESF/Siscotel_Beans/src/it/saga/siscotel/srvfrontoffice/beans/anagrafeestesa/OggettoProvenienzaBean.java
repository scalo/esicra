package it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa;


import java.math.BigDecimal;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.ProvenienzaBean;

public class OggettoProvenienzaBean 
{
  private BigDecimal PkidOggetto;
  private String CodiceEcografico;
  private BigDecimal IdEnte;
  private ProvenienzaBean[] ListaProvenienza;

  public OggettoProvenienzaBean()
  {
  }
  
  /**
   * Costruttore per OggettoProvenienzaBean
   * @param pPkidOggetto Pk Id Oggetto
   * @param pCodFiscale Codice Fiscale
   * @param pIdEnte Id Ente
   * @param pListaProvenienza Lista Provenienza
   */
  public OggettoProvenienzaBean(BigDecimal pPkidOggetto,String pCodiceEcografico,BigDecimal pIdEnte,ProvenienzaBean[] pListaProvenienza)
  {
    this.PkidOggetto=pPkidOggetto;
    this.CodiceEcografico=pCodiceEcografico;
    this.IdEnte=pIdEnte;
    this.ListaProvenienza=pListaProvenienza;
  }



  public BigDecimal getPkidOggetto()
  {
    return PkidOggetto;
  }

  public void setPkidOggetto(BigDecimal PkidOggetto)
  {
    this.PkidOggetto = PkidOggetto;
  }

  public String getCodiceEcografico()
  {
    return CodiceEcografico;
  }

  public void setCodiceEcografico(String CodiceEcografico)
  {
    this.CodiceEcografico = CodiceEcografico;
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
   * @return toString del Bean OggettoProvenienzaBean
   */
   public String toString(){

        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("PkidOggetto = "+getPkidOggetto()+", ");  
        sb.append("CodiceEcografico = "+getCodiceEcografico()+", ");  
        sb.append("IdEnte = "+getIdEnte()+", "); 
        if(ListaProvenienza==null){sb.append("ListaProvenienza =null ");}
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
   * Metodo di Test per il OggettoProvenienzaBean
   * @return Restituisce OggettoProvenienzaBean istanziato con valori di test
   */
    public static OggettoProvenienzaBean test(){
        return new OggettoProvenienzaBean(new BigDecimal("123456") , "123456", new BigDecimal("7734"),     
        new ProvenienzaBean[]{new ProvenienzaBean().test(),new ProvenienzaBean().test()}
        );            
    }
  
  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    OggettoProvenienzaBean oggettoProvenienzaBean = new OggettoProvenienzaBean();
    System.out.println("OggettoProvenienzaBean = "+it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoProvenienzaBean.test());
  }


}