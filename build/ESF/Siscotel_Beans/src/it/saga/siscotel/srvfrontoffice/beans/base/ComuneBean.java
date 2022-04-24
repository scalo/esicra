package it.saga.siscotel.srvfrontoffice.beans.base;

public class ComuneBean  {
  private Integer CodIstatComune;
  private String DesComune;
  private String DesProvincia;

  public ComuneBean() {
  }
  
  /**
   * Costruttore per ComuneBean
   * @param pCodIstatComune Codice Istat Comune
   * @param pDesComune Descrizione Comune
   * @param pDesProvincia Descrizione Provincia
   */
  public ComuneBean(Integer pCodIstatComune, String pDesComune , String pDesProvincia) {
    this.CodIstatComune=pCodIstatComune;
    this.DesComune=pDesComune;
    this.DesProvincia=pDesProvincia;
  }

  public Integer getCodIstatComune() {
    return CodIstatComune;
  }

  public void setCodIstatComune(Integer newCodIstatComune) {
    CodIstatComune = newCodIstatComune;
  }

  public String getDesComune() {
    return DesComune;
  }

  public void setDesComune(String newDesComune) {
    DesComune = newDesComune;
  }

  public String getDesProvincia() {
    return DesProvincia;
  }

  public void setDesProvincia(String newDesProvincia) {
    DesProvincia = newDesProvincia;
  }

  /**
   * Metodo restituisce il contenuto del bean ComuneBean 
   * @return toString del Bean ComuneBean
   */

  public String toString(){
    StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("CodIstatComune = "+getCodIstatComune()+", ");  
        sb.append("DesComune = "+getDesComune()+", ");  
        sb.append("DesProvincia = "+getDesProvincia()); 
        sb.append(" ] ");
        return sb.toString();
     }
  
  public static ComuneBean test(){
    return new ComuneBean(new Integer(16024),"Bergamo","BG");
  }

  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.ComuneBean.test());
    
  }
  
}