package it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa;

public class IdentificativiCatastaliBean 
{
  private String Tipo;
  private String DesTipo;
  private String IdComune;
  private String Sezione;
  private String Foglio;
  private String Mappale;
  private String Subalterno;
  private String AnnoProtocollo;
  private String NumeroProtocollo;

  public IdentificativiCatastaliBean()
  {
  }
 
  /**
   * Costruttore per IdentificativiCatastaliBean
   * @param pTipo Tipo
   * @param pDesTipo Descrizione Tipo
   * @param pIdComune Id Comune
   * @param pSezione Sezione
   * @param pFoglio Foglio
   * @param pMappale Mappale
   * @param pSubalterno Subalterno
   * @param pAnnoProtocollo Anno Protocollo
   * @param pNumeroProtocollo Numero Protocollo
   */
  public IdentificativiCatastaliBean(String pTipo,String pDesTipo,String pIdComune,String pSezione,
                                     String pFoglio,String pMappale,String pSubalterno,String pAnnoProtocollo,String pNumeroProtocollo)
  {
    this.Tipo=pTipo;
    this.DesTipo=pDesTipo;
    this.IdComune=pIdComune;
    this.Sezione=pSezione;
    this.Foglio=pFoglio;
    this.Mappale=pMappale;
    this.Subalterno=pSubalterno;
    this.AnnoProtocollo=pAnnoProtocollo;
    this.NumeroProtocollo=pNumeroProtocollo;
  }

  public String getTipo()
  {
    return Tipo;
  }

  public void setTipo(String Tipo)
  {
    this.Tipo = Tipo;
  }

  public String getDesTipo()
  {
    return DesTipo;
  }

  public void setDesTipo(String DesTipo)
  {
    this.DesTipo = DesTipo;
  }

  public String getIdComune()
  {
    return IdComune;
  }

  public void setIdComune(String IdComune)
  {
    this.IdComune = IdComune;
  }

  public String getSezione()
  {
    return Sezione;
  }

  public void setSezione(String Sezione)
  {
    this.Sezione = Sezione;
  }

  public String getFoglio()
  {
    return Foglio;
  }

  public void setFoglio(String Foglio)
  {
    this.Foglio = Foglio;
  }

  public String getMappale()
  {
    return Mappale;
  }

  public void setMappale(String Mappale)
  {
    this.Mappale = Mappale;
  }

  public String getSubalterno()
  {
    return Subalterno;
  }

  public void setSubalterno(String Subalterno)
  {
    this.Subalterno = Subalterno;
  }

  public String getAnnoProtocollo()
  {
    return AnnoProtocollo;
  }

  public void setAnnoProtocollo(String AnnoProtocollo)
  {
    this.AnnoProtocollo = AnnoProtocollo;
  }

  public String getNumeroProtocollo()
  {
    return NumeroProtocollo;
  }

  public void setNumeroProtocollo(String NumeroProtocollo)
  {
    this.NumeroProtocollo = NumeroProtocollo;
  }
  
    /**
     * Metodo restituisce il contenuto del bean istanziato 
     * @return toString del Bean IdentificativiCatastaliBean
     */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("Tipo = "+getTipo()+", ");  
        sb.append("DesTipo = "+getDesTipo()+", ");  
        sb.append("IdComune = "+getIdComune()+", "); 
        sb.append("Sezione = "+getSezione()+", "); 
        sb.append("Foglio = "+getFoglio()+", "); 
        sb.append("Mappale = "+getMappale()+", "); 
        sb.append("Subalterno = "+getSubalterno()+", "); 
        sb.append("AnnoProtocollo = "+getAnnoProtocollo()+", "); 
        sb.append("NumeroProtocollo = "+getNumeroProtocollo()); 
        sb.append(" ] ");
        return sb.toString();
     }

  /**
   * Metodo di Test per il IdentificativiCatastaliBean
   * @return Restituisce IdentificativiCatastaliBean istanziato con valori di test
   */
    public static IdentificativiCatastaliBean test(){
        return new IdentificativiCatastaliBean(
         "Tipo","DesTipo","IdComune","Sezione","Foglio","Mappale","Subalterno","AnnoProtocollo","NumeroProtocollo");            
    }
  
  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    IdentificativiCatastaliBean identificativiCatastaliBean = new IdentificativiCatastaliBean();
    System.out.println("IdentificativiCatastaliBean = "+identificativiCatastaliBean.test());
  }
}
