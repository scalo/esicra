package it.saga.siscotel.db;

import java.math.*;


public class VSoggettoProvenienzaId  implements java.io.Serializable {

    // Fields    

    private BigDecimal id;
    private String cod;

    public BigDecimal getId() {
        return this.id;
    }
    
    public void setCod(String cod) {
        this.cod = cod;
    }
    
     public String getCod() {
        return this.cod;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    // Constructors

    /** default constructor */
    public VSoggettoProvenienzaId() {
    }
    
    /** constructor with id */
    public VSoggettoProvenienzaId(BigDecimal id,String cod) {
        this.id = id;
        this.cod=cod;
    }


}