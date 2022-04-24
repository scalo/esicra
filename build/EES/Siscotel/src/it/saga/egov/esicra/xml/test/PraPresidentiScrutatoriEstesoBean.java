package it.saga.egov.esicra.xml.test;
import java.util.Date;

public class PraPresidentiScrutatoriEstesoBean extends PraPresidentiScrutatoriBean {

    
    private SoggettoEstesoBean soggetto;

    public PraPresidentiScrutatoriEstesoBean() {
        
    }

    public PraPresidentiScrutatoriEstesoBean( PraticaBean pratica, SoggettoBean soggetto, String	titoloStudio,
                                      String	professione, Boolean	flgIscrizione, Boolean	flgCompatibilita,
                                      Boolean	flgPresidente, String	desStato, Integer	stato, 
                                      String	note, Date  dataStato,String nuovo
                                    )
  {
    super(pratica,null,titoloStudio,professione,flgIscrizione,flgCompatibilita,
        flgPresidente,desStato,stato,note,dataStato,nuovo);
    this.soggetto=(SoggettoEstesoBean)soggetto;
  }
    
    public static PraPresidentiScrutatoriBean test(){
        
        PraPresidentiScrutatoriEstesoBean b= new PraPresidentiScrutatoriEstesoBean( PraticaBean.test(), SoggettoEstesoBean.test(), "laurea àèìòù", 
                                            "impiegato", new Boolean(true), new Boolean(true), 
                                            new Boolean(true), "Aperta", new Integer(1),
                                            "nessuna", new Date(),"Carmelo");
        return b;
  }

    public SoggettoBean getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoEstesoBean newSoggetto) {
        soggetto = (SoggettoEstesoBean) newSoggetto;
    }
    
}