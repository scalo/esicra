package it.saga.siscotel.srvfrontoffice.beans.base;

public class LocalitaBean  {
  
  private Integer codIstatStato;
  private String desStato;
  private String desLocalita;
  private String desContea;

  public LocalitaBean() {
  }

  /**
   * Costruttore  LocalitaBean
   * @param codIstatStato Codice Istat Stato
   * @param desStato  Descrizione Stato
   * @param desLocalita Descrizione Localita
   */
  public LocalitaBean(Integer codIstatStato, String desStato , String desLocalita, String desContea) {
    this.codIstatStato=codIstatStato;
    this.desStato=desStato;
    this.desLocalita=desLocalita;
    this.desContea=desContea;
    
  }

 
  public Integer getCodIstatStato() {
    return codIstatStato;
  }

  public void setCodIstatStato(Integer newCodIstatStato) {
    codIstatStato = newCodIstatStato;
  }

  public String getDesStato() {
    return desStato;
  }

  public void setDesStato(String newDesStato) {
    desStato = newDesStato;
  }

  public String getDesLocalita() {
    return desLocalita;
  }

  public void setDesLocalita(String newDesLocalita) {
    desLocalita = newDesLocalita;
  }

  public String getDesContea() {
    return desContea;
  }

  public void setDesContea(String newDesContea) {
    desContea = newDesContea;
  }

  /**
   * Metodo restituisce il contenuto del bean LocalitaBean 
   * @return toString del Bean LocalitaBean
   */
     
  public String toString(){
    return new String("["+
                      "codIstatStato="+getCodIstatStato()+","+
                      "desStato="+getDesStato()+","+
                      "desLocalita="+getDesLocalita()+","+
                      "desContea="+getDesContea()
                      +"]");
  }
  
  public static LocalitaBean test(){
    return new LocalitaBean(new Integer(203),"Austria","Vienna","DesContea");
  }

  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.LocalitaBean.test());
  }

}