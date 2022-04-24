package it.saga.egov.esicra.xml.test;
import java.text.*;
import java.math.*;
import java.util.*;
/**
 *  Bean di test Soggetto che estende soggetto bean e utilizza come comunedinascita
 *  un comune esteso
 */
public class SoggettoEstesoBean extends SoggettoBean {

    private ComuneEstesoBean ComuneNascita;

    public SoggettoEstesoBean() {
        super();
    }

    public SoggettoEstesoBean(
        String pCodiceFiscale,
        String pCognome,
        String pNome,
        String pSesso,
        Date pDataNascita,
        ComuneBean pComune,
        LocalitaBean pLocalita,
        IndirizzoBean pIndirizzo,
        BigDecimal pIdEnte
        ){
            super(pCodiceFiscale,pCognome,pNome,pSesso,pDataNascita,null,pLocalita,pIndirizzo,pIdEnte);
            setComuneNascita((ComuneEstesoBean)pComune);
        }
        
    public ComuneBean getComuneNascita() {
        return (ComuneEstesoBean)ComuneNascita;
    }

    public void setComuneNascita(ComuneEstesoBean newComuneNascita) {
        ComuneNascita = (ComuneEstesoBean)newComuneNascita;
    }

     public static SoggettoBean test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SoggettoEstesoBean b=null;
        try{
        b= new SoggettoEstesoBean(
          "BCCNNA70M41A794K",
          "Bacca",
          "",
          "F",
          sdf.parse("1970-07-26"),
          (ComuneEstesoBean)ComuneEstesoBean.test(),
          null,
          IndirizzoBean.test(),
          new BigDecimal("16024")
         );
        }catch(Exception e){
            
        }
        return b;
     }
}