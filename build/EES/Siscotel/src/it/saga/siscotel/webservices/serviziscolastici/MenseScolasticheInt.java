package it.saga.siscotel.webservices.serviziscolastici;

import it.saga.siscotel.beans.serviziscolastici.*;
import java.math.BigDecimal;
import java.util.Date;

public interface MenseScolasticheInt {

    public BigDecimal inserisciPraIscrizioneMensaScolastica(
        PraIscrizioneMensaBean pPraIscrizioneMensa) throws Exception;

    public BigDecimal inserisciPraRecessoMensaScolastica(PraRecessoMensaBean pPraRecessoMensa)
        throws Exception;

    public PraIscrizioneMensaBean[] cercaPraIscrizioneMensaScolastica(String pCodiceFiscale,
        BigDecimal pIdEnteDestinatario, BigDecimal pIdPratica, Integer pStato, Date pDataInizio,
        Date pDataFine) throws Exception;

    public PraRecessoMensaBean[] cercaPraRecessoMensaScolastica(String pCodiceFiscale,
        BigDecimal pIdEnteDestinatario, BigDecimal pIdPratica, Integer pStato, Date pDataInizio,
        Date pDataFine) throws Exception;
}
