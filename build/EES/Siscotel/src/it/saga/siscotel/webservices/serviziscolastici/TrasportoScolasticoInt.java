package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.siscotel.beans.serviziscolastici.*;
import java.math.BigDecimal;
import java.util.Date;

public interface TrasportoScolasticoInt {

    public BigDecimal inserisciPraIscrizioneTrasportoScolastico(
        PraIscrizioneTrasportoBean pPraIscrizioneTrasporto)
        throws Exception;

    public BigDecimal inserisciPraRecessoTrasportoScolastico(
        PraRecessoTrasportoBean pPraRecessoTrasporto) throws Exception;

    public PraIscrizioneTrasportoBean[] cercaPraIscrizioneTrasportoScolastico(
        String pCodiceFiscale, BigDecimal pIdEnteDestinatario, BigDecimal pIdPratica,
        Integer pStato, Date pDataInizio, Date pDataFine)
        throws Exception;

    public PraRecessoTrasportoBean[] cercaPraRecessoTrasportoScolastico(String pCodiceFiscale,
        BigDecimal pIdEnteDestinatario, BigDecimal pIdPratica, Integer pStato, Date pDataInizio,
        Date pDataFine) throws Exception;
        
}
