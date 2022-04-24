package it.saga.egov.esicra.xml.test;

public class ComuneEstesoBean extends ComuneBean {
    private String Regione;

    public ComuneEstesoBean() {
        super();
    }

    public ComuneEstesoBean(Integer codIstatComune, String desComune,
        String desProvincia, String regione) {
        super(codIstatComune, desComune, desProvincia);
        this.Regione = regione;
    }

    public String getRegione() {
        return Regione;
    }

    public void setRegione(String newRegione) {
        Regione = newRegione;
    }

    public String toString() {
        return new String("[" + "codIstatComune=" + getCodIstatComune() + "," +
            "desComune=" + getDesComune() + "," + "desProvincia=" +
            getDesProvincia() + "," + "regione=" + getRegione() + "]");
    }

    public static ComuneBean test() {
        return new ComuneEstesoBean(new Integer(19097), "Soncino", "Cremona",
            "Lombardia");
    }

    public static void main(String[] args) {
        System.out.println(ComuneEstesoBean.test());
    }
}
