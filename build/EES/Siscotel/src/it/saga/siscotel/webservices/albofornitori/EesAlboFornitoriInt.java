package it.saga.siscotel.webservices.albofornitori;

import it.saga.siscotel.beans.albofornitori.AlboBean;
import it.saga.siscotel.beans.albofornitori.CategoriaBean;
import it.saga.siscotel.beans.albofornitori.FornitoreBean;
import it.saga.siscotel.beans.albofornitori.FornitoreRidottoBean;
import it.saga.siscotel.beans.albofornitori.PraAccreditamentoFornitoreAlboBean;
import it.saga.siscotel.beans.albofornitori.PraAccreditamentoIterBean;
import it.saga.siscotel.beans.albofornitori.StatoBean;

import java.math.BigDecimal;
import java.util.Date;

public interface EesAlboFornitoriInt
{

  // - Richieste Accreditamento
  public BigDecimal inserisciPraAccreditamentoFornitoreAlbo(PraAccreditamentoFornitoreAlboBean praAccreditamentoFornitore) throws Exception;
  
  public PraAccreditamentoFornitoreAlboBean[] cercaPraAccreditamentoFornitore(BigDecimal idEnte,
                                                                              String codiceFiscale,
                                                                              String partitaIva,
                                                                              BigDecimal idPratica,
                                                                              BigDecimal codStato,
                                                                              BigDecimal idAlbo, 
                                                                              Date dataInizio,
                                                                              Date dataFine ) throws Exception;

  public PraAccreditamentoIterBean[]cercaPraAccreditamentoIter(BigDecimal idPratica ) throws Exception;
 
  public Boolean cambiaStatoPraAccreditamento(PraAccreditamentoIterBean iterPratica ) throws Exception;

  public StatoBean[] listaStatoIter(BigDecimal pIdEnte) throws Exception;
  
  // - GESTIONE ALBO - 
  public BigDecimal creaAlbo(AlboBean albo ) throws Exception;

  public AlboBean[] cercaAlbo(BigDecimal idEnte) throws Exception;

  public Boolean rinnovaAlbo(AlboBean albo) throws Exception;

  public Boolean chiudiAlbo(BigDecimal idAlbo) throws Exception;

  public Boolean eliminaAlbo(BigDecimal idAlbo) throws Exception;

  // - GESTIONE FORNITORI - 
  public BigDecimal inserisciFornitoreInAlbo(BigDecimal idAlbo,
                                             BigDecimal[] idCategorie, 
                                             FornitoreBean fornitore ) throws Exception;


  public FornitoreRidottoBean[] cercaFornitore(BigDecimal idEnte,
                                               BigDecimal idAlbo,
                                               BigDecimal idStato,
                                               BigDecimal idCategoria) throws Exception;

  public FornitoreBean cercaDettagliFornitore(BigDecimal idFornitore) throws Exception;

  public Boolean cambiaStatoFornitore(BigDecimal idAlbo,
                                      BigDecimal idFornitore,
                                      BigDecimal idCategoria,
                                      BigDecimal idNuovoStato ) throws Exception;

  public Boolean AccreditaFornitore(BigDecimal idRichiesta,
                                    BigDecimal[] idCategoria) throws Exception;

  public StatoBean[] listaStatoFornitore(BigDecimal pIdEnte) throws Exception;
  
  public Boolean eliminaFornitore(BigDecimal idFornitore) throws Exception;
  
  public CategoriaBean[] cercaCategorieFornitoreAlbo(BigDecimal idAlbo, BigDecimal idFornitore) throws Exception;
  
  //9.	Gestione Categorie  
  public BigDecimal inserisciCategoria(CategoriaBean categoria ) throws Exception;
  
  public CategoriaBean[] cercaCategorie(BigDecimal idEnte) throws Exception;

  public Boolean eliminaCategoria(BigDecimal idCategoria ) throws Exception;

  public Boolean aggiornaCategoria(CategoriaBean categoria) throws Exception;

}
