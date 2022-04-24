package it.saga.egov.esicra.xml.test;

import java.math.BigDecimal;

public class AllegatoBean 
{
  private BigDecimal IdPratica;
  private String IdAllegato;
  private String Ref;
  
  public AllegatoBean(){
  }

  public AllegatoBean(BigDecimal idPratica, String idAllegato, String ref){
    this.IdPratica = idPratica;
    this.IdAllegato = idAllegato;
    this.Ref = ref;
  }

  public BigDecimal getIdPratica(){
    return this.IdPratica;
  }

  public void setIdPratica(BigDecimal pIdPratica){
    this.IdPratica = pIdPratica;
  }

  public String getIdAllegato(){
    return this.IdAllegato;
  }

  public void setIdAllegato(String pIdAllegato){
    this.IdAllegato = pIdAllegato;
  }

   public String getRef(){
    return this.Ref;
  }

  public void setRef(String pRef){
    this.Ref = pRef;
  }
  
/********************************************************/
// Metodo di utility: ritorna il bean toString
// La stringa restituita inizia con [ e termina con ] ; 
// gli attributi del bena sono separati da ,
/********************************************************/
  public String toString (){
    String beanToString = new String("[" +"IdPratica ="+ this.getIdPratica()+ "," +"IdAllegato = "+ this.getIdAllegato()+"," +"Ref = "+this.getRef()+ "]");
    return beanToString;
  }

  public static AllegatoBean test(){
    return new AllegatoBean(
        new BigDecimal("111001"),
        "ABS10/23",
        "http://portale/per_SCLR34E47BX_200301023.doc"
    );
  }

  public static AllegatoBean test2(){
    return new AllegatoBean(
        new BigDecimal("1212"),
        "CFB18733",
        "http://indirizzo/certificato.pdf"
    );
  }
  
  public static void main(String[] args){
    System.out.println(AllegatoBean.test());
  }
  
}