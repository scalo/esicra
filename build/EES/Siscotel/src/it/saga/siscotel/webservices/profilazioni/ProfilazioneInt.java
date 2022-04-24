package it.saga.siscotel.webservices.profilazioni;

import java.math.BigDecimal;

public interface ProfilazioneInt {

    public String[] cercaListaProfilazione(BigDecimal pIdLista, BigDecimal pIdEnte)
        throws Exception;

    public Boolean inserisciProfilazione(BigDecimal idLista, BigDecimal idEnte, String valore)
        throws Exception;
}
