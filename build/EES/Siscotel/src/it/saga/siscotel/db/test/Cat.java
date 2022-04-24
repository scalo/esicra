package it.saga.siscotel.db.test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/*

CREATE TABLE cat
(
  pkid numeric(22) NOT NULL,
  name varchar(16),
  sex char(1),
  weight float8,
  mother_pkid numeric(22),
  CONSTRAINT cat_pkid PRIMARY KEY (pkid)
)

*/
public class Cat extends Validate {
    private BigDecimal pkid;
    private String name;
    private char sex;
    private float weight;
    private Cat mother;
    private Set kittens = new HashSet();

    public Cat() {
    }

    public BigDecimal getPkid() {
        return pkid;
    }

    public void setPkid(BigDecimal pkid) {
        this.pkid = pkid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    void setMother(Cat mother) {
        this.mother = mother;
    }

    public Cat getMother() {
        return mother;
    }

    void setKittens(Set kittens) {
        this.kittens = kittens;
    }

    public Set getKittens() {
        return kittens;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("( pkid=" + getPkid() + ",");
        sb.append("name=" + getName() + ",");
        sb.append("sex=" + getSex() + ",");
        sb.append("weight=" + getWeight() + ")");
        return sb.toString();
    }

    // validazione lato server 
    public boolean isValid() {
        // Clear all errors
        errorCodes.clear();

        // Validate name
        if (name.length() == 0) {
            errorCodes.put("name", ERR_NAME_ENTER);
        }

        // Validate sex
        if (sex == 'A') {
            errorCodes.put("sex", ERR_SEX_ENTER);
        } else if ((sex != 'M') || (sex != 'F')) {
            errorCodes.put("sex", ERR_SEX_INVALID);
        }

        // Validate weight 
        if (weight == 0f) {
            errorCodes.put("weight", ERR_FLOAT_INVALID);
        }

        // If no errors, form is valid
        return errorCodes.size() == 0;
    }
}
