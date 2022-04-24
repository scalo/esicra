package it.saga.siscotel.webservices.pagamenti;

import it.saga.siscotel.beans.base.PagamentoBean;
import java.math.BigDecimal;
import java.util.Date;

public interface PagamentoInt {

    public Boolean registraPagamento(BigDecimal pIdPratica, BigDecimal pIdEnteDestinatario,
        PagamentoBean[] pPagamento) throws Exception;
    
    public PagamentoBean[] cercaPagamento(BigDecimal idEnteDestinatario, BigDecimal idPratica, Date dataInizio,
        Date dataFine) throws Exception;
}
