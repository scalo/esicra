package it.saga.siscotel.srvfrontoffice.beans.base;

import java.util.Date;
import java.text.SimpleDateFormat;
import it.saga.siscotel.srvfrontoffice.beans.base.ComuneBean;
import it.saga.siscotel.srvfrontoffice.beans.base.LocalitaBean;

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

  /**
   * Costruttore IndirizzoBean
   * @param newSpecieArea Specie Area
   * @param newDesArea Descrizione Area
   * @param newNumCiv Numero Civico
   * @param newLetCiv Lettera Civica
   * @param newScala Scala
   * @param newInterno Interno
   * @param newCap Cap
   * @param newDataInizio Data Inizio
   * @param newDataFine Data Fine
   * @param newComune Comune
   * @param newLocalita Localita
   */
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

  /**
   * Metodo restituisce il contenuto del bean IndirizzoBean 
   * @return toString del Bean IndirizzoBean
   */
  public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append("SpecieArea = "+getSpecieArea()+", ");  
        sb.append("DesArea = "+getDesArea()+", "); 
        sb.append("NumCiv = "+getNumCiv()+", ");  
        sb.append("Lettera Civica = "+getLetCiv()+", "); 
        sb.append("Scala = "+getScala()+", "); 
        sb.append("Interno = "+getInterno()+", "); 
        sb.append("Cap = "+getCap()+", "); 
        sb.append("DataInizio = "+getDataInizio()+", "); 
        sb.append("DataFine = "+getDataFine()+", "); 
        
        if(comune==null){sb.append("Comune = null");}
        else{sb.append("Comune = "+getComune().toString()+", ");}
        
         if(localita==null){sb.append("Localita = null");}
        else{sb.append("Localita = "+getLocalita().toString());}
        
            
        sb.append(" ] ");
        return sb.toString();
     }
  
public static IndirizzoBean test() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try{
        return new IndirizzoBean("VIA","UgoFoscolo",new Integer(25),"A","SCALA","INTERNO",
                                 "24100",sdf.parse("2003-01-11"),sdf.parse("2003-12-11"),
                                 ComuneBean.test(),it.saga.siscotel.srvfrontoffice.beans.base.LocalitaBean.test());
    } catch(Exception e){
      e.printStackTrace();
      return null;
    }

 }

  public static void main(String[] args) throws Exception{
    System.out.println(it.saga.siscotel.srvfrontoffice.beans.base.IndirizzoBean.test());
  }
}
