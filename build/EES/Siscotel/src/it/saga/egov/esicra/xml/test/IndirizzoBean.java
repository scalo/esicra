package it.saga.egov.esicra.xml.test;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class IndirizzoBean  {
  
  private String specieArea;
  private String desArea;
  private Integer numCiv;
  private String letCiv;
  private String scala;
  private String interno;
  private String cap;
  private Date dataInizio;
  private Date dataFine;
  private ComuneBean comune;
  private LocalitaBean localita;

  public IndirizzoBean() {
    this.comune = new ComuneBean();
    this.localita = new LocalitaBean();
  
  }

  public IndirizzoBean(String newSpecieArea, String newDesArea, 
                       Integer newNumCiv, String newLetCiv, 
                       String newScala, String newInterno, String newCap,
                       Date newDataInizio, Date newDataFine,
                       ComuneBean newComune, LocalitaBean newLocalita ) {
                       this.specieArea = newSpecieArea;
                       this.desArea = newDesArea;
                       this.numCiv = newNumCiv;
                       this.letCiv = newLetCiv;
                       this.scala = newScala;
                       this.interno = newInterno;
                       this.cap = newCap;
                       this.dataInizio = newDataInizio;
                       this.dataFine = newDataFine;
                       this.comune = newComune;
                       this.localita = newLocalita;
  }


  public ComuneBean getComune() {
    return comune;
  }

  public void setComune(ComuneBean newComune) {
    comune = newComune;
  }

  public LocalitaBean getLocalita() {
    return localita;
  }

  public void setLocalita(LocalitaBean newLocalita) {
    localita = newLocalita;
  }

  public String getSpecieArea() {
    return specieArea;
  }

  public void setSpecieArea(String newSpecieArea) {
    specieArea = newSpecieArea;
  }

  public String getDesArea() {
    return desArea;
  }

  public void setDesArea(String newDesArea) {
    desArea = newDesArea;
  }

  public Integer getNumCiv() {
    return numCiv;
  }

  public void setNumCiv(Integer newNumCiv) {
    numCiv = newNumCiv;
  }

  public String getLetCiv() {
    return letCiv;
  }

  public void setLetCiv(String newLetCiv) {
    letCiv = newLetCiv;
  }

  public String getScala() {
    return scala;
  }

  public void setScala(String newScala) {
    scala = newScala;
  }

  public String getInterno() {
    return interno;
  }

  public void setInterno(String newInterno) {
    interno = newInterno;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String newCap) {
    cap = newCap;
  }


  public Date getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(Date newDataInizio) {
    dataInizio = newDataInizio;
  }

  public Date getDataFine() {
    return dataFine;
  }

  public void setDataFine(Date newDataFine) {
    dataFine = newDataFine;
  }

/*
 public String toString(){
    return new String("["+"SpecieArea="+getSpecieArea()+","+
                      "DesArea="+getDesArea()+","+
                      "NumCiv="+getNumCiv()+","+
                      "LetCiv="+getLetCiv()+","+
                      "Scala="+getScala()+","+
                      "Interno="+getInterno()+","+
                      "Cap="+getCap()+","+
                      "DataInizio="+getDataInizio()+","+
                      "DatFine="+getDataFine()+","+
                      "Comune="+getComune()+","+
                      "Localita="+getLocalita()
                      +"]");
  }
*/


public static IndirizzoBean test() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
        return new IndirizzoBean("VIA","Mullà",new Integer(25),"A","SCALA","INTERNO",
                                 "26100",sdf.parse("2003-01-11"),sdf.parse("2003-12-11"),
                                 ComuneBean.test(),null);
    } catch(Exception e){
      e.printStackTrace();
      return null;
    }

 }

 public static IndirizzoBean test1() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
        return new IndirizzoBean("VIA","LATTE CORSO NIZZA",new Integer(95),"","","",
                                 "16100",sdf.parse("2001-01-01"),null,
                                 ComuneBean.test1(),null);
    } catch(Exception e){
      e.printStackTrace();
      return null;
    }

 }

  public String toString() {
     return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
  }

  public static void main(String[] args) throws Exception{
    System.out.println(IndirizzoBean.test());
  }


  
}