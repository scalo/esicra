package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AlboBean implements Serializable
{
  public final static String STATO_ATTIVO = "ATTIVO";
  public final static String STATO_SCADUTO = "SCADUTO";
  public final static String STATO_NON_ATTIVO = "NON ATTIVO";
  public final static String STATO_CHIUSO = "CHIUSO";

  public final static Integer COD_STATO_CHIUSO = new Integer(1);
  public final static Integer COD_STATO_APERTO = new Integer(0);

  //MANDATORY - chiave univoca, per l'inserimento valorizzata a NULL
  private BigDecimal idAlbo;  
  
  //MANDATORY - 
  private BigDecimal idEnte;  
  
  //indica se un albo è definitivamente chiuso
  private Integer flgChiuso;
  
  // descrizione dell'albo
  private String desAlbo;     
  
  //MANDATORY - 
  private String desEnte;     
  
  //note - 
  private String note;
  
  //indica l'inizio di validità dell'albo
  private Date dataInizioValidita;
  
  //indica la fine di validità dell'albo
  private Date dataFineValidita;

  public AlboBean(BigDecimal idAlbo, 
                  BigDecimal idEnte, 
                  String desAlbo, 
                  String desEnte,
                  String note,
                  Date dataInizioValidita,
                  Date dataFineValidita,
                  Integer flgChiuso)
  {
    this.idAlbo = idAlbo;
    this.idEnte = idEnte;
    this.desAlbo = desAlbo;
    this.desEnte = desEnte;
    this.note = note;
    this.dataInizioValidita = dataInizioValidita;
    this.dataFineValidita = dataFineValidita;
    this.flgChiuso = flgChiuso;
  }

  public AlboBean()
  {
  
  }
  
  public BigDecimal getIdAlbo()
  {
    return this.idAlbo;
  }
  public void setIdAlbo(BigDecimal idAlbo)
  {
    this.idAlbo = idAlbo;
  }

  public BigDecimal getIdEnte()
  {
    return this.idEnte;
  }
  public void setIdEnte(BigDecimal idEnte)
  {
    this.idEnte = idEnte;
  }

  public String getDesAlbo()
  {
    return this.desAlbo;
  }
  public void setDesAlbo(String desAlbo)
  {
    this.desAlbo = desAlbo;
  }

  public String getDesEnte()
  {
    return this.desEnte;
  }
  public void setDesEnte(String desEnte)
  {
    this.desEnte = desEnte;
  }

  public String getNote()
  {
    return this.note;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public Date getDataInizioValidita()
  {
    return this.dataInizioValidita;
  }
  public void setDataInizioValidita(Date dataInizioValidita)
  {
    this.dataInizioValidita = dataInizioValidita;
  }

  public Date getDataFineValidita()
  {
    return this.dataFineValidita;
  }
  public void setDataFineValidita(Date dataFineValidita)
  {
    this.dataFineValidita = dataFineValidita;
  }

  public Integer getFlgChiuso()
  {
    return this.flgChiuso;
  }
  public void setFlgChiuso(Integer flgChiuso)
  {
    this.flgChiuso = flgChiuso;
  }
  
  /*
   * il metodo restituisce lo stato dell'albo
   * in funzione della DATA INIZIO e FINE VALIDITA'
   */
  public String getDesStato()
  {
    String stato = null;
    
    Date now = new Date();
    
    if(this.dataInizioValidita != null && this.dataFineValidita != null && this.flgChiuso != null)
    {
      if(this.flgChiuso.equals(COD_STATO_CHIUSO))
      {
        stato = STATO_CHIUSO;
      }
      else
      {
        if(now.before(this.dataInizioValidita))
          stato = STATO_NON_ATTIVO;
        else if(now.after(this.dataFineValidita))
          stato = STATO_SCADUTO;
        else if(now.after(this.dataInizioValidita) && now.before(this.dataFineValidita))
          stato = STATO_ATTIVO;
      }
    }
    
    return stato;
  }

  public static AlboBean test()
  {
    AlboBean test = new AlboBean();
    
    Date date = new Date(System.currentTimeMillis());
    
    test.setDataFineValidita(date);
    test.setDataInizioValidita(date);
    test.setDesAlbo("TEST ALBO");
    test.setDesEnte("TEST ENTE");
    test.setIdAlbo(new BigDecimal(100100));
    test.setIdEnte(new BigDecimal(8240));
    test.setNote("TEST NOTE");
    
    return test;
  }
  
  public String toString()
  {
    String strReturn = ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
    return strReturn.substring(0,strReturn.length()-1) + ",DesStato=" + this.getDesStato() + "]";
  }
}
