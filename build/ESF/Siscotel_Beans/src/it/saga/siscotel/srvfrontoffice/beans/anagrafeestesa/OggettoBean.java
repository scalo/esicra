package it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa;
import it.saga.siscotel.srvfrontoffice.beans.base.IndirizzoBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.IdentificativiCatastaliBean;

public class OggettoBean 
{
  private String CodiceEcografico;
  private String CodiceIstatComune;
  private IndirizzoBean[] ListaIndirizzo;
  private IdentificativiCatastaliBean[] ListaIdentificativiCatastali;

  public OggettoBean()
  {
  }

  /**
   * Costruttore OggettoBean
   * @param pCodiceEcografico Codice Ecografico
   * @param pCodiceIstatComune Codice Istat Comune
   * @param pListaIndirizzo Lista Indirizzo
   * @param pListaIdentificativiCatastali Lista Identificativi Catastali
   */
  public OggettoBean(String pCodiceEcografico,String pCodiceIstatComune, IndirizzoBean[] pListaIndirizzo,IdentificativiCatastaliBean[] pListaIdentificativiCatastali)
  {
   this.CodiceEcografico=pCodiceEcografico;
    this.CodiceIstatComune=pCodiceIstatComune;
    this.ListaIndirizzo=pListaIndirizzo;
    this.ListaIdentificativiCatastali=pListaIdentificativiCatastali;
  }

  public String getCodiceEcografico()
  {
    return CodiceEcografico;
  }

  public void setCodiceEcografico(String CodiceEcografico)
  {
    this.CodiceEcografico = CodiceEcografico;
  }

  public String getCodiceIstatComune()
  {
    return CodiceIstatComune;
  }

  public void setCodiceIstatComune(String CodiceIstatComune)
  {
    this.CodiceIstatComune = CodiceIstatComune;
  }

  public IndirizzoBean[] getListaIndirizzo()
  {
    return ListaIndirizzo;
  }

  public void setListaIndirizzo(IndirizzoBean[] ListaIndirizzo)
  {
    this.ListaIndirizzo = ListaIndirizzo;
  }

  public IdentificativiCatastaliBean[] getListaIdentificativiCatastali()
  {
    return ListaIdentificativiCatastali;
  }

  public void setListaIdentificativiCatastali(IdentificativiCatastaliBean[] ListaIdentificativiCatastali)
  {
    this.ListaIdentificativiCatastali = ListaIdentificativiCatastali;
  }
  
   /**
   * Metodo restituisce il contenuto del bean istanziato 
   * @return toString del Bean OggettoProvenienzaBean
   */
   public String toString(){


        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("CodiceEcografico = "+getCodiceEcografico()+", ");  
        sb.append("CodiceIstatComune = "+getCodiceIstatComune()+", ");  
        
        if(ListaIndirizzo==null){sb.append("ListaIndirizzo =null ");}
            else{sb.append("ListaIndirizzo = ");
              sb.append("{ ");
              for(int i=0;i<((ListaIndirizzo!=null)?ListaIndirizzo.length:-1);i++){
                 sb.append(ListaIndirizzo[i]);
              };
               sb.append(" }");
            } 
            
         if(ListaIdentificativiCatastali==null){sb.append("ListaIdentificativiCatastali =null ");}
            else{sb.append("ListaIdentificativiCatastali = ");
              sb.append("{ ");
              for(int i=0;i<((ListaIdentificativiCatastali!=null)?ListaIdentificativiCatastali.length:-1);i++){
                 sb.append(ListaIdentificativiCatastali[i]);
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
    public static OggettoBean test(){
        return new OggettoBean(      
         "CodiceEcografico","CodiceIstatComune", new IndirizzoBean[]{new IndirizzoBean().test(),new IndirizzoBean().test()},
        new IdentificativiCatastaliBean[]{new IdentificativiCatastaliBean().test(),new IdentificativiCatastaliBean().test()}
        );            
    }
  
  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    OggettoBean OggettoBean = new OggettoBean();
    System.out.println("OggettoBean = "+it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoBean.test());
  }
}