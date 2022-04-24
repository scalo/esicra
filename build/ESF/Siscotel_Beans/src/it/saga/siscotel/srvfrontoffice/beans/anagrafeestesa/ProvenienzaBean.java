package it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa;

public class ProvenienzaBean 
{
  private String CodProvenienza;
  private String DesProvenienza;

  
  public ProvenienzaBean()
  {
  }

  /**
   * Costruttore per ProvenienzaBean
   * @param pCodProvenienza Codice Provenienza
   * @param pDesProvenienza Descrizione Provenienza
   */
  public ProvenienzaBean(String pCodProvenienza, String pDesProvenienza)
  {
    this.CodProvenienza=pCodProvenienza;
    this.DesProvenienza=pDesProvenienza;
  }
  
  public String getCodProvenienza()
  {
    return CodProvenienza;
  }

  public void setCodProvenienza(String CodProvenienza)
  {
    this.CodProvenienza = CodProvenienza;
  }

  public String getDesProvenienza()
  {
    return DesProvenienza;
  }

  public void setDesProvenienza(String DesProvenienza)
  {
    this.DesProvenienza = DesProvenienza;
  }
  
   /**
     * Metodo restituisce il contenuto del bean istanziato 
     * @return toString del Bean RichiestaSDIBean
     */
    public String toString(){
  
        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("CodProvenienza = "+getCodProvenienza()+", ");  
        sb.append("DesProvenienza = "+getDesProvenienza());  
        sb.append(" ] ");
        return sb.toString();
     }

  /**
   * Metodo di Test per il DatiPersonaResponsabileBean
   * @return Restituisce DatiTipoMensa istanziato con valori di test
   */
    public static ProvenienzaBean test(){
        return new ProvenienzaBean("CodProvenienza","DesProvenienza");            
    }
  
  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    ProvenienzaBean provenienzaBean = new ProvenienzaBean();
    System.out.println("ProvenienzaBean = "+it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.ProvenienzaBean.test());
  }

 
}