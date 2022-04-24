package it.saga.egov.esicra.xml.test;

import it.saga.egov.esicra.xml.test.*;
import java.util.Date;

public class PraPresidentiScrutatoriBean 
{
  PraticaBean pratica;
  SoggettoBean soggetto;
  String	titoloStudio;
  String	professione;
  Boolean	flgIscrizione;
  Boolean	flgCompatibilita;
  Boolean	flgPresidente;
  String	desStato;
  Integer	stato;
  String	note;
  Date	dataStato;
    private String nuovoAttributo;

  public PraPresidentiScrutatoriBean()
  {
  }
  
  public PraPresidentiScrutatoriBean( PraticaBean pratica, SoggettoBean soggetto, String	titoloStudio,
                                      String	professione, Boolean	flgIscrizione, Boolean	flgCompatibilita,
                                      Boolean	flgPresidente, String	desStato, Integer	stato, 
                                      String	note, Date  dataStato,String nuovo
                                    )
  {
    this.pratica = pratica;
    this.soggetto = soggetto;
    this.titoloStudio = titoloStudio;
    this.professione = professione;
    this.flgIscrizione = flgIscrizione;
    this.flgCompatibilita = flgCompatibilita;
    this.flgPresidente = flgPresidente;
    this.desStato = desStato;
    this.stato = stato;
    this.note = note;
    this.dataStato = dataStato;
    this.nuovoAttributo=nuovo;
  }


  public void setPratica(PraticaBean pratica)
  {
    this.pratica = pratica;
  }


  public PraticaBean getPratica()
  {
    return pratica;
  }


  public void setSoggetto(SoggettoBean soggetto)
  {
    this.soggetto = soggetto;
  }


  public SoggettoBean getSoggetto()
  {
    return soggetto;
  }


  public void setTitoloStudio(String titoloStudio)
  {
    this.titoloStudio = titoloStudio;
  }


  public String getTitoloStudio()
  {
    return titoloStudio;
  }


  public void setProfessione(String professione)
  {
    this.professione = professione;
  }


  public String getProfessione()
  {
    return professione;
  }


  public void setFlgIscrizione(Boolean flgIscrizione)
  {
    this.flgIscrizione = flgIscrizione;
  }


  public Boolean getFlgIscrizione()
  {
    return flgIscrizione;
  }


  public void setFlgCompatibilita(Boolean flgCompatibilita)
  {
    this.flgCompatibilita = flgCompatibilita;
  }


  public Boolean getFlgCompatibilita()
  {
    return flgCompatibilita;
  }


  public void setFlgPresidente(Boolean flgPresidente)
  {
    this.flgPresidente = flgPresidente;
  }


  public Boolean getFlgPresidente()
  {
    return flgPresidente;
  }


  public void setDesStato(String desStato)
  {
    this.desStato = desStato;
  }


  public String getDesStato()
  {
    return desStato;
  }


  public void setStato(String stato)
  { 
    this.stato = new Integer(stato);
  }

  public void setStato(Integer stato)
  {
    this.stato = stato;
  }


  public Integer getStato()
  {
    return stato;
  }


  public void setNote(String note)
  {
    this.note = note;
  }


  public String getNote()
  {
    return note;
  }


  public void setDataStato(Date dataStato)
  {
    this.dataStato = dataStato;
  }


  public Date getDataStato()
  {
    return dataStato;
  }

 public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    sb.append("Pratica = "+pratica.toString()+", ");
    sb.append("TitoloStudio = "+getTitoloStudio()+", ");
    sb.append("Professione = "+getProfessione()+", ");
    sb.append("FlgIscrizione = "+getFlgIscrizione()+", ");
    sb.append("FlgCompatibilita = "+getFlgCompatibilita()+", ");
    sb.append("FlgPresidente = "+getFlgPresidente()+", ");
    sb.append("DesStato = "+getDesStato()+", ");
    sb.append("Stato = "+getStato()+", ");
    sb.append("Note = "+getNote()+", ");
    sb.append("DataStato = "+getDataStato()+", ");
    sb.append("NuovoAttributo = "+getNuovoAttributo());
    sb.append(" ]");
    return sb.toString();
  }

  
  public static PraPresidentiScrutatoriBean test(){
    return new PraPresidentiScrutatoriBean( PraticaBean.test(), SoggettoBean.test(), "laurea àèìòù", 
                                            "impiegato", new Boolean(true), new Boolean(true), 
                                            new Boolean(true), "Aperta", new Integer(1),
                                            "nessuna", new Date(),"Carmelo");
  }


  public static void main(String[] args){
    
    System.out.println(PraPresidentiScrutatoriBean.test());
  }

    public String getNuovoAttributo() {
        return nuovoAttributo;
    }

    public void setNuovoAttributo(String newNuovoAttributo) {
        nuovoAttributo = newNuovoAttributo;
    }
  
}
