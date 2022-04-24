package it.saga.siscotel.db;

import java.math.BigDecimal;

/**
 * VIndiceOggettoIndId 
 */
 
public class VIndiceOggettoIndId  implements java.io.Serializable {

    // Fields    

    private BigDecimal aotPkid;
    private BigDecimal indPkid;

    public BigDecimal getAotPkid() {
        return this.aotPkid;
    }
    
    public void setAotPkid(BigDecimal aotPkid) {
        this.aotPkid = aotPkid;
    }
    
    public BigDecimal getIndPkid() {
        return this.indPkid;
    }
    
    public void setIndPkid(BigDecimal indPkid) {
        this.indPkid = indPkid;
    }
    
    // Constructors

    /** default constructor */
    public VIndiceOggettoIndId() {
    }
    
    /** constructor with id */
    public VIndiceOggettoIndId(BigDecimal aotPkid,BigDecimal indPkid) {
        this.aotPkid = aotPkid;
        this.indPkid=indPkid;
    }

}