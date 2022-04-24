package it.saga.siscotel.srvfrontoffice.beans.commercio;

import java.util.Date;

public class IscrizioneCCIABean 
{
  private String NumeroIscrizioneCCIA;
  private String SedeCCIA;
  private Date DataIscrizione;

  public IscrizioneCCIABean()
  {
  }

  public IscrizioneCCIABean(String newNumeroIscrizioneCCIA, String newSedeCCIA, Date newDataIscrizione)
  {
    this.NumeroIscrizioneCCIA = newNumeroIscrizioneCCIA;
    this.SedeCCIA = newSedeCCIA;
    this.DataIscrizione = newDataIscrizione;
    
  }


 public String getNumeroIscrizioneCCIA()
  {
    return NumeroIscrizioneCCIA;
  }

  public void setNumeroIscrizioneCCIA(String newNumeroIscrizioneCCIA)
  {
    NumeroIscrizioneCCIA = newNumeroIscrizioneCCIA;
  }

  public String getSedeCCIA()
  {
    return SedeCCIA;
  }

  public void setSedeCCIA(String newSedeCCIA)
  {
    SedeCCIA = newSedeCCIA;
  }

  public Date getDataIscrizione()
  {
    return DataIscrizione;
  }

  public void setDataIscrizione(Date newDataIscrizione)
  {
    DataIscrizione = newDataIscrizione;
  }

  public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append("NumeroIscrizioneCCIA = "+getNumeroIscrizioneCCIA()+", ");
        sb.append("SedeCCIA = "+getSedeCCIA()+", ");
        sb.append("DataIscrizione = "+getDataIscrizione());
        sb.append(" ]");
        return sb.toString();
   }


  
  public static IscrizioneCCIABean test(){
    return new IscrizioneCCIABean("NumeroIscrizioneCCIA", "SedeCCIA" , new Date());
    //return new IscrizioneCCIABean();
  }

 
  public static void main(String[] args){
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.commercio.IscrizioneCCIABean.test());

  }

 
}