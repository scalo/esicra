package it.saga.siscotel.srvfrontoffice.beans.base;

import java.util.Date;

public class PermessoSoggiornoBean  {
 
  private String ComuneQuestura;
  private String ProvinciaQuestura;
  private String Tipo;
  private String NumeroPermesso;
  private Date DataRilascio;
  private Date DataRinnovo;
  private Date DataScadenza;

  public PermessoSoggiornoBean() {
  }

  public PermessoSoggiornoBean(String newComuneQuestura, String newProvinciaQuestura, String newTipo, String newNumeroPermesso, Date newDataRilascio, Date newDataRinnovo, Date newDataScadenza) {
    this.ComuneQuestura = newComuneQuestura;
    this.ProvinciaQuestura = newProvinciaQuestura;
    this.Tipo = newTipo;
    this.NumeroPermesso = newNumeroPermesso;
    this.DataRilascio = newDataRilascio;
    this.DataRinnovo = newDataRinnovo;
    this.DataScadenza = newDataScadenza;
  }

  public String getComuneQuestura()
  {
    return ComuneQuestura;
  }

  public void setComuneQuestura(String newComuneQuestura)
  {
    ComuneQuestura = newComuneQuestura;
  }

  public String getProvinciaQuestura()
  {
    return ProvinciaQuestura;
  }

  public void setProvinciaQuestura(String newProvinciaQuestura)
  {
    ProvinciaQuestura = newProvinciaQuestura;
  }

  public String getTipo()
  {
    return Tipo;
  }

  public void setTipo(String newTipo)
  {
    Tipo = newTipo;
  }

  public String getNumeroPermesso()
  {
    return NumeroPermesso;
  }

  public void setNumeroPermesso(String newNumeroPermesso)
  {
    NumeroPermesso = newNumeroPermesso;
  }

 public Date getDataRilascio()
  {
    return DataRilascio;
  }

  public void setDataRilascio(Date newDataRilascio)
  {
    DataRilascio = newDataRilascio;
  }

  public Date getDataRinnovo()
  {
    return DataRinnovo;
  }

  public void setDataRinnovo(Date newDataRinnovo)
  {
    DataRinnovo = newDataRinnovo;
  }

  public Date getDataScadenza()
  {
    return DataScadenza;
  }

  public void setDataScadenza(Date newDataScadenza)
  {
    DataScadenza = newDataScadenza;
  }

   public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append("ComuneQuestura = "+getComuneQuestura()+", ");
        sb.append("ProvinciaQuestura = "+getProvinciaQuestura()+", ");
        sb.append("Tipo = "+getTipo()+", ");
        sb.append("NumeroPermesso = "+getNumeroPermesso()+", ");
        sb.append("DataRilascio = "+getDataRilascio()+", ");
        sb.append("DataRinnovo = "+getDataRinnovo()+", ");
        sb.append("DataScadenza = "+getDataScadenza()+", ");
        sb.append(" ]");
        return sb.toString();
    }

  public static PermessoSoggiornoBean test(){
    return new PermessoSoggiornoBean("Comune Questura", "Provincia Questura" , "Tipo", "Numero Permesso", new Date(), new Date(), new Date());
  }

 
  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.PermessoSoggiornoBean.test());

  }
}