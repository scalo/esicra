package it.saga.siscotel.beans.albofornitori;

import it.saga.siscotel.beans.SiscotelToStringStyle;

import java.io.Serializable;

import java.math.*;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FornitoreBean implements Serializable
{ 
  //MANDATORY - chiave univoca, per l'inserimento è valorizzata a NULL
  private BigDecimal idFornitore;
  
  //Codice Fiscale soggetto Fisico
  private String codiceFiscale;
  
  //Codice del fornitore
  private String codFornitore;

  //Partita IVA
  public String partitaIva;
  
  //Ruolo persona fisica di riferimento
  public String titolarita;
  
  //MANDATORY - identificativo dell'ente
  private BigDecimal idEnte;

  //Indirizzo
  private String civico;
  
  private String cap;
  
  //Nome del soggetto Fisico
  private String nome;
  
  //Cognome del soggetto Fisico
  private String cognome;
  
  private String denominazione;
  
  private String via;
  
  private String provincia;
  
  private String telefono;
  
  private String fax;
  
  private String email;
  
  private String desEnte;
  
  private String citta;
  
  public static BigDecimal STATO_FORNITORE_ATTIVO = new BigDecimal(1);
  public static BigDecimal STATO_FORNITORE_SOSPESO = new BigDecimal(2);
    
  public FornitoreBean(BigDecimal idFornitore,
                       String codiceFiscale,
                       String codFornitore,
                       String partitaIva,
                       String titolarita,
                       BigDecimal idEnte,
                       String civico,
                       String cap,
                       String nome,
                       String cognome,
                       String via,
                       String provincia,
                       String telefono,
                       String fax,
                       String email,
                       String desEnte,
                       String citta,
                       String denominazione)
  {
    this.idFornitore = idFornitore;
    this.codiceFiscale = codiceFiscale;
    this.codFornitore = codFornitore;
    this.partitaIva = partitaIva;
    this.titolarita = titolarita;
    this.idEnte = idEnte;
    this.civico = civico;
    this.cap = cap;
    this.nome = nome;
    this. cognome = cognome;
    this.via = via;
    this.provincia = provincia;
    this.telefono = telefono;
    this.fax = fax;
    this.email = email;
    this.desEnte = desEnte;
    this.citta = citta;
    this.denominazione = denominazione;
  
  }

  public FornitoreBean()
  {
  
  }
  
  public BigDecimal getIdFornitore()
  {
    return this.idFornitore;
  }
  public void setIdFornitore(BigDecimal idFornitore)
  {
    this.idFornitore = idFornitore;
  }

  public String getCodiceFiscale()
  {
    return this.codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale)
  {
    this.codiceFiscale = codiceFiscale;
  }

  public String getCodFornitore()
  {
    return this.codFornitore;
  }
  public void setCodFornitore(String codFornitore)
  {
    this.codFornitore = codFornitore;
  }

  public String getPartitaIva()
  {
    return this.partitaIva;
  }
  public void setPartitaIva(String partitaIva)
  {
    this.partitaIva = partitaIva;
  }

  public String getTitolarita()
  {
    return this.titolarita;
  }
  public void setTitolarita(String titolarita)
  {
    this.titolarita = titolarita;
  }

  public BigDecimal getIdEnte()
  {
    return this.idEnte;
  }
  public void setIdEnte(BigDecimal idEnte)
  {
    this.idEnte = idEnte;
  }

  public String getCivico()
  {
    return this.civico;
  }
  public void setCivico(String civico)
  {
    this.civico = civico;
  }

  public String getCap()
  {
    return this.cap;
  }
  public void setCap(String cap)
  {
    this.cap = cap;
  }

  public String getVia()
  {
    return this.via;
  }
  public void setVia(String via)
  {
    this.via = via;
  }

  public String getProvincia()
  {
    return this.provincia;
  }
  public void setProvincia(String provincia)
  {
    this.provincia= provincia;
  }

  public String getTelefono()
  {
    return this.telefono;
  }
  public void setTelefono(String telefono)
  {
    this.telefono= telefono;
  }

  public String getFax()
  {
    return this.fax;
  }
  public void setFax(String fax)
  {
    this.fax= fax;
  }

  public String getEmail()
  {
    return this.email;
  }
  public void setEmail(String email)
  {
    this.email= email;
  }

  public String getDesEnte()
  {
    return this.desEnte;
  }
  public void setDesEnte(String desEnte)
  {
    this.desEnte= desEnte;
  }   

  public String getNome()
  {
    return this.nome;
  }
  public void setNome(String nome)
  {
    this.nome= nome;
  } 

  public String getCognome()
  {
    return this.cognome;
  }
  public void setCognome(String cognome)
  {
    this.cognome= cognome;
  }

  public String getCitta()
  {
    return this.citta;
  }
  public void setCitta(String citta)
  {
    this.citta= citta;
  }

  public String getDenominazione()
  {
    return this.denominazione;
  }
  public void setDenominazione(String denominazione)
  {
    this.denominazione = denominazione;
  }

  public static FornitoreBean testSoggettoGiuridico()
  {
    FornitoreBean bean = new FornitoreBean();

    bean.setCap("25020");
    bean.setCodFornitore("AABBCC");
    bean.setCivico("32");
    bean.setPartitaIva("123456789012");
    bean.setCognome("TEST COGNOME");
    bean.setDesEnte("TEST DESC ENTE");
    bean.setEmail("TEST@EMAIL.IT");
    bean.setFax("1234567890");
    bean.setIdEnte(new BigDecimal(8240));
    bean.setIdFornitore(new BigDecimal(1));
    bean.setNome("TEST NOME");
    bean.setProvincia("TEST PROV");
    bean.setTelefono("123456789");
    bean.setVia("TEST VIA");
    bean.setTitolarita("LEGALE RAPPRESENTANTE");
    bean.setCitta("CITTA'");
    
    return bean;
    
  }

  public static FornitoreBean testSoggettoFisico()
  {
    FornitoreBean bean = new FornitoreBean();
    bean.setCap("25020");
    bean.setCivico("32");
    bean.setCodiceFiscale("1234567890123456");
    bean.setCodFornitore("AABBCC");
    bean.setCognome("TEST COGNOME");
    bean.setDesEnte("TEST DESC ENTE");
    bean.setEmail("TEST@EMAIL.IT");
    bean.setFax("1234567890");
    bean.setIdEnte(new BigDecimal(8240));
    bean.setIdFornitore(new BigDecimal(1));
    bean.setNome("TEST NOME");
    bean.setProvincia("TEST PROV");
    bean.setTelefono("123456789");
    bean.setVia("TEST VIA");
    bean.setCitta("CITTA'");
    bean.setDenominazione("TEST DENOMINAZIONE");
    
    return bean;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this,SiscotelToStringStyle.getStyle());
  }
        
}
