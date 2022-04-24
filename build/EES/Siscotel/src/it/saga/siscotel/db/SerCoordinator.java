package it.saga.siscotel.db;

import java.util.*;


/**
 * SerCoordinator 
 */
public class SerCoordinator  implements java.io.Serializable {

    // Fields    

     private java.math.BigDecimal pkid;
     private String nomeTabella;
     private Integer ordine;
     private Integer flBidir;
     private Integer flAllinea;
     private Integer flCancellabile;
     private Date dtMod;
     private Integer idEnte;


    // Constructors

    /** default constructor */
    public SerCoordinator() {
    }
    
    /** constructor with id */
    public SerCoordinator(java.math.BigDecimal pkid) {
        this.pkid = pkid;
    }
   
    
    

    // Property accessors

    /**
     * 
     */
    public java.math.BigDecimal getPkid() {
        return this.pkid;
    }
    
    public void setPkid(java.math.BigDecimal pkid) {
        this.pkid = pkid;
    }

    /**
     * 
     */
    public String getNomeTabella() {
        return this.nomeTabella;
    }
    
    public void setNomeTabella(String nomeTabella) {
        this.nomeTabella = nomeTabella;
    }

    /**
     * 
     */
    public Integer getOrdine() {
        return this.ordine;
    }
    
    public void setOrdine(Integer ordine) {
        this.ordine = ordine;
    }

    /**
     * 
     */
    public Integer getFlBidir() {
        return this.flBidir;
    }
    
    public void setFlBidir(Integer flBidir) {
        this.flBidir = flBidir;
    }

    /**
     * 
     */
    public Integer getFlAllinea() {
        return this.flAllinea;
    }
    
    public void setFlAllinea(Integer flAllinea) {
        this.flAllinea = flAllinea;
    }

    /**
     * 
     */
    public Integer getFlCancellabile() {
        return this.flCancellabile;
    }
    
    public void setFlCancellabile(Integer flCancellabile) {
        this.flCancellabile = flCancellabile;
    }

    /**
     * 
     */
    public Date getDtMod() {
        return this.dtMod;
    }
    
    public void setDtMod(Date dtMod) {
        this.dtMod = dtMod;
    }

    /**
     * 
     */
    public Integer getIdEnte() {
        return this.idEnte;
    }
    
    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }



}