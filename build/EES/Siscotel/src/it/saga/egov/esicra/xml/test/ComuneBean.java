package it.saga.egov.esicra.xml.test;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ComuneBean  {
  private Integer codIstatComune;
  private String desComune;
  private String desProvincia;

  public ComuneBean() {
  }
  
  public ComuneBean(Integer codIstatComune, String desComune , String desProvincia) {
    this.codIstatComune=codIstatComune;
    this.desComune=desComune;
    this.desProvincia=desProvincia;
  }

  public Integer getCodIstatComune() {
    return codIstatComune;
  }

  public void setCodIstatComune(Integer newCodIstatComune) {
    codIstatComune = newCodIstatComune;
  }

  public String getDesComune() {
    return desComune;
  }

  public void setDesComune(String newDesComune) {
    desComune = newDesComune;
  }

  public String getDesProvincia() {
    return desProvincia;
  }

  public void setDesProvincia(String newDesProvincia) {
    desProvincia = newDesProvincia;
  }
  
  /*
  public String toString(){
    return new String("["+"codIstatComune="+getCodIstatComune()+","+
                      "desComune="+getDesComune()+","+
                      "desProvincia="+getDesProvincia()
                      +"]");
  }
  */
  
  public static ComuneBean test(){
    return new ComuneBean(new Integer(19097),"Soncino","Cremona");
  }

  public static ComuneBean test1(){
    return new ComuneBean(new Integer(10025),"Genova","GE");
  }

  public String toString() {
     return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public static void main(String[] args) {
    ComuneBean comuneBean = ComuneBean.test();
    System.out.println(ToStringBuilder.reflectionToString(comuneBean,ToStringStyle.MULTI_LINE_STYLE));      
    
  }
  
  
}
