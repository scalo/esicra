package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.siscotel.beans.serviziscolastici.*;
import java.math.BigDecimal;
import java.util.Date;

public interface CentriSportiviRicreativiInt {
    public BigDecimal inserisciPraIscrizioneCentro(PraIscrizioneCentroBean pPraIscrizioneCentro)
        throws Exception;

    public BigDecimal inserisciPraRecessoCentro(PraRecessoCentroBean pPraRecessoCentro)
        throws Exception;

    public PraIscrizioneCentroBean[] cercaPraIscrizioneCentro(String pCodiceFiscale,
        BigDecimal pIdEnteDestinatario, BigDecimal pIdPratica, Integer pStato, Date pDataInizio,
        Date pDataFine) throws Exception;

    public PraRecessoCentroBean[] cercaPraRecessoCentro(String pCodiceFiscale,
        BigDecimal pIdEnteDestinatario, BigDecimal pIdPratica, Integer pStato, Date pDataInizio,
        Date pDataFine) throws Exception;
}
