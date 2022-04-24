 package it.saga.siscotel.webservices.albofornitori;

import it.saga.egov.esicra.EsicraConf;
import it.saga.siscotel.beans.albofornitori.AlboBean;
import it.saga.siscotel.beans.albofornitori.CategoriaBean;
import it.saga.siscotel.beans.albofornitori.FornitoreBean;
import it.saga.siscotel.beans.albofornitori.FornitoreRidottoBean;
import it.saga.siscotel.beans.albofornitori.FornitureBean;
import it.saga.siscotel.beans.albofornitori.PraAccreditamentoFornitoreAlboBean;
import it.saga.siscotel.beans.albofornitori.PraAccreditamentoIterBean;
import it.saga.siscotel.beans.albofornitori.StatoBean;
import it.saga.siscotel.db.hibernate.*;
import it.saga.siscotel.db.*;

import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.*;
import org.apache.soap.*;
import org.hibernate.*;

public class EesAlboFornitoriWs implements EesAlboFornitoriInt
{
  protected static Logger logger;
  private Session session ;
  
  public EesAlboFornitoriWs()
  {
    EsicraConf.configura();

    if(logger==null)
    {
      logger= EsicraConf.configuraLogger(this.getClass());
        
        if(logger==null)
        {
          logger.warn("-> Logger non configurato correttamente");
        }
    } 
      
    session = HibernateUtil.currentSession();
  }
  
  // ----  RICHIESTE ACCREDITAMENTO ----
  
  /**
   * Forniti i dati che caratterizzano la richiesta dei accreditamento viene 
   * creata fisicamente tale richiesta nella base dati centralizzata.
   * 
   * @param praAccreditamentoFornitore
   * @return BigDecimal identificativo della richiesta di accreditamento (CHIAVE)
   */
  public BigDecimal inserisciPraAccreditamentoFornitoreAlbo(PraAccreditamentoFornitoreAlboBean praAccreditamentoFornitore) throws SOAPException
  { 
    logger.info("-> Inizio metodo inserisciPraAccreditamentoFornitore");
    
    Transaction tx = null;
    BigDecimal idAccreditamentoFor = null;
    
    if(praAccreditamentoFornitore == null)
    {
      logger.error("--> ERROR: PraAccreditamentoFornitoreAlboBean IS NULL");
      throw new SOAPException("SOAP-ENV:Client","PraAccreditamentoFornitoreAlboBean IS NULL");        
    }
    
    logger.info("-->" + praAccreditamentoFornitore.toString());
  
    try
    { 
      tx = session.beginTransaction();        
      
      //Caricamento ALBO
      Albo albo = (Albo)session.get(Albo.class,praAccreditamentoFornitore.getIdAlbo());
      
      if(albo != null)
      {
        Richiesteaccreditamento pojoRichiestaAcc = new Richiesteaccreditamento(); 
        pojoRichiestaAcc.setDatapresentazione(praAccreditamentoFornitore.getDataPresentazione());
        pojoRichiestaAcc.setIdEnteDestinatario(praAccreditamentoFornitore.getIdEnte());
        pojoRichiestaAcc.setIdEnteRichiedente(praAccreditamentoFornitore.getIdEnteRichiedente());
        pojoRichiestaAcc.setPartiva(praAccreditamentoFornitore.getDatiFornitore().getPartitaIva());
        pojoRichiestaAcc.setCodfiscalerichiedente(praAccreditamentoFornitore.getDatiFornitore().getCodiceFiscale());
        pojoRichiestaAcc.setDenominazione(praAccreditamentoFornitore.getDatiFornitore().getNome() + " " + 
                                          praAccreditamentoFornitore.getDatiFornitore().getCognome());
        pojoRichiestaAcc.setAlbo(albo);  
        pojoRichiestaAcc.setDtMod(new Date());
      
        HashSet hsrRchiestaAccIter = new HashSet();
        Richiesteaccreditamentoiter pojoRicAcciter = new Richiesteaccreditamentoiter();
        pojoRicAcciter.setDataultimostato(new Date());
        pojoRicAcciter.setIdEnte(praAccreditamentoFornitore.getIdEnte());
        pojoRicAcciter.setRichiesteaccreditamento(pojoRichiestaAcc);
        pojoRicAcciter.setResponsabile(praAccreditamentoFornitore.getAccreditamentoIter()[0].getResponsabile());
        hsrRchiestaAccIter.add(pojoRicAcciter);
        pojoRichiestaAcc.setRichiesteaccreditamentoiters(hsrRchiestaAccIter);
        pojoRichiestaAcc.setDtMod(new Date());
        
        Statoiter stato = (Statoiter)session.get(Statoiter.class,new BigDecimal(1));
        pojoRicAcciter.setStatoiter(stato);
        
        Fornitori pojoFornitore = new Fornitori();
        pojoFornitore.setCap(praAccreditamentoFornitore.getDatiFornitore().getCap());
        pojoFornitore.setCitta(praAccreditamentoFornitore.getDatiFornitore().getCitta());
        pojoFornitore.setCivico(praAccreditamentoFornitore.getDatiFornitore().getCivico());
        pojoFornitore.setCodfiscale(praAccreditamentoFornitore.getDatiFornitore().getCodiceFiscale()); 
        pojoFornitore.setCodfornitore(praAccreditamentoFornitore.getDatiFornitore().getCodFornitore());
        pojoFornitore.setCognome(praAccreditamentoFornitore.getDatiFornitore().getCognome());
        pojoFornitore.setEmail(praAccreditamentoFornitore.getDatiFornitore().getEmail());
        pojoFornitore.setFax(praAccreditamentoFornitore.getDatiFornitore().getFax());
        pojoFornitore.setIdEnte(praAccreditamentoFornitore.getDatiFornitore().getIdEnte());
        pojoFornitore.setNome(praAccreditamentoFornitore.getDatiFornitore().getNome());
        pojoFornitore.setPartiva(praAccreditamentoFornitore.getDatiFornitore().getPartitaIva());
        pojoFornitore.setProvincia(praAccreditamentoFornitore.getDatiFornitore().getProvincia());
        pojoFornitore.setTelefono(praAccreditamentoFornitore.getDatiFornitore().getCap());
        pojoFornitore.setVia(praAccreditamentoFornitore.getDatiFornitore().getCap());
        pojoFornitore.setDtMod(new Date());
        
        pojoRichiestaAcc.setFornitori(pojoFornitore);
        
        HashSet hsrRchiestaAcc = new HashSet();
        hsrRchiestaAcc.add(pojoRichiestaAcc);
        pojoFornitore.setRichiesteaccreditamentos(hsrRchiestaAcc);
        
        FornitureBean[] arrForniture = praAccreditamentoFornitore.getDatiForniture();
        
        HashSet hsrForniture = null;
        
        if(arrForniture != null)
        { 
          hsrForniture = new HashSet();
                  
          for(int i=0; i<arrForniture.length; i++)
          {
            Forniture pojoFornitura = new Forniture();
            pojoFornitura.setAnno(arrForniture[i].getAnno());
            pojoFornitura.setCommittente(arrForniture[i].getCommittente());
            pojoFornitura.setDesfornitura(arrForniture[i].getDesFornitura());
            pojoFornitura.setImporto(arrForniture[i].getImporto());
               
            Categorie pojoCategoria = (Categorie)session.get(Categorie.class,arrForniture[i].getCategoria().getIdCategoria());
            
            if(pojoCategoria != null)
              pojoFornitura.setCategorie(pojoCategoria);
        
            pojoFornitura.setRichiesteaccreditamento(pojoRichiestaAcc);
            hsrForniture.add(pojoFornitura);
          }
        }
         
        CategoriaBean[] arrCategorie = praAccreditamentoFornitore.getDatiCategorie();
       
        Categorieric pojoCatRic = new Categorieric();
        
        if(arrCategorie != null)
        {
          for(int j=0; j<arrCategorie.length; j++)
          {
            Categorie categoria = (Categorie)session.get(Categorie.class,arrCategorie[j].getIdCategoria());
            
            if(categoria != null)
              pojoCatRic.setCategorie(categoria);
          }
        }
       
        pojoCatRic.setRichiesteaccreditamento(pojoRichiestaAcc);
        
        pojoRichiestaAcc.setFornitures(hsrForniture); 
        
        session.save(pojoRichiestaAcc);
        session.save(pojoRicAcciter);
        session.save(pojoFornitore);
        session.save(pojoCatRic);
        
        session.flush();
        tx.commit();
        
        idAccreditamentoFor = pojoRichiestaAcc.getPkrichiesteaccreditamento();
      }
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    
    logger.info("-> Fine metodo creaAlbo");
     
    return idAccreditamentoFor;
  }
  
  /**
   * Il servizio implementa la ricerca delle pratiche di richiesta 
   * accreditamento secondo le modalità sotto elencate:
   *<ul>
   *  <li>
   *      E' necessario specificare l'identificativo (IdEnte) dell'ente 
   *      destinatario della pratica.
   *  </li>
   *  <li>
   *      E' consentito pilotare la ricerca specificando anche l'identificativo 
   *      della pratica (IdPratica), il codice ficale del richiedente o la partita IVA.
   *  </li>
   *  <li>
   *       E' consentito limitare la ricerca alle sole pratiche che si trovano in 
   *       un determinato stato (codStato)
   *  </li>
   *  <li>
   *      E' consentito fifinire ulteriromente la ricerca specificando la data di 
   *      inizio (dataInizio) e la di fine (dataFine) o entrambe per limitare la
   *      ricerca ad un intervallo di tempo.
   *  </li>
   *  <li>
   *      I parametri di ricerca non utilizzati devono essere attualizzati con il 
   *      valore NULL.
   *  </li>
   *</ul>
   * <P>
   * @param idEnte
   * @param codiceFiscale
   * @param idPratica
   * @param idStato
   * @param idAlbo
   * @param dataInizio
   * @param dataFine
   * @return Lista di dati di tipo PraAccreditamentoFornitoreAlboBean
   */
  public PraAccreditamentoFornitoreAlboBean[] cercaPraAccreditamentoFornitore(BigDecimal idEnte,
                                                                              String codiceFiscale,
                                                                              String partitaIva,
                                                                              BigDecimal idPratica,
                                                                              BigDecimal idStato,
                                                                              BigDecimal idAlbo, 
                                                                              Date dataInizio,
                                                                              Date dataFine ) throws SOAPException
  {
    logger.info("-> Inizio metodo cercaPraAccreditamentoFornitore");
    
    PraAccreditamentoFornitoreAlboBean[] arrPraAccFornitoreAlbo = null;
    if(idEnte == null)
    {
      logger.error("--> ERROR: idEnte IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idEnte IS NULL");      
    }
    
    logger.info("--> paramentri passati idEnte = " + idEnte);
    logger.info("--> paramentri passati codiceFiscale = " + codiceFiscale);
    logger.info("--> paramentri passati partitaIva = " + partitaIva);    
    logger.info("--> paramentri passati stato = " + idStato);    
    logger.info("--> paramentri passati idAlbo = " + idAlbo);
    logger.info("--> paramentri passati dataInizio = " + dataInizio);    
    logger.info("--> paramentri passati dataFine = " + dataFine); 
  
    try
    { 
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("SELECT obj1 ");
      strQuery.append("FROM Richiesteaccreditamento as obj1, ");
      strQuery.append("Richiesteaccreditamentoiter as obj2 ");
      strQuery.append("WHERE obj1 = obj2.richiesteaccreditamento AND ");
      strQuery.append("obj1.idEnteDestinatario = " + idEnte  + " ");
      
      if(codiceFiscale != null)
        strQuery.append("AND obj1.codfiscalerichiedente = " + codiceFiscale  + " ");  
     
      if(partitaIva != null)
        strQuery.append("AND obj1.partiva = " + partitaIva + " "); 

      if(idAlbo != null)
        strQuery.append("AND obj1.albo.pkalbo = " + idAlbo + " "); 

      if(idStato != null)
        strQuery.append("AND obj2.Statoiter.pkstatoiter = " + idStato + " "); 

      if(dataInizio != null)
        strQuery.append("AND obj1.dtMod >= ? "); 

      if(dataFine != null)
        strQuery.append("AND obj1.dtMod <= ? "); 
    
      Query query = session.createQuery(strQuery.toString());   
      
      if(dataInizio != null && dataFine != null)
      {
        query.setDate(0,dataInizio);
        query.setDate(1,dataFine);
      }
      else if(dataInizio != null)
        query.setDate(0,dataInizio);      
      else if(dataFine != null)
        query.setDate(0,dataFine);
      
      List lista = query.list();
      
      if(lista != null)
      {
        arrPraAccFornitoreAlbo = new PraAccreditamentoFornitoreAlboBean[lista.size()];
        
        for(int i=0; i<lista.size(); i++)
        {
          Richiesteaccreditamento pojoRichiestaAcc = (Richiesteaccreditamento)lista.get(i);
          PraAccreditamentoFornitoreAlboBean item = new PraAccreditamentoFornitoreAlboBean();

          Entealbo enteDest = (Entealbo)session.get(Entealbo.class,new Integer(pojoRichiestaAcc.getIdEnteDestinatario().intValue()));
          Entealbo enteRic = (Entealbo)session.get(Entealbo.class,new Integer(pojoRichiestaAcc.getIdEnteRichiedente().intValue()));
          
          item.setIdRichiesta(pojoRichiestaAcc.getPkrichiesteaccreditamento());
          item.setCognomeRichiedente(pojoRichiestaAcc.getFornitori().getCognome());
          item.setNomeRichiedente(pojoRichiestaAcc.getFornitori().getNome());
          item.setIdAlbo(pojoRichiestaAcc.getAlbo().getPkalbo());
          item.setIdEnte(pojoRichiestaAcc.getIdEnteDestinatario());
          item.setIdEnteRichiedente(pojoRichiestaAcc.getIdEnteRichiedente());
          item.setDataPresentazione(pojoRichiestaAcc.getDatapresentazione());
          item.setDesEnteDestinatario(enteDest.getDesEnte());
          item.setDesEnteRichiedente(enteRic.getDesEnte());
          
          FornitoreBean fornitore = new FornitoreBean();
          fornitore.setCap(pojoRichiestaAcc.getFornitori().getCap());
          fornitore.setCitta(pojoRichiestaAcc.getFornitori().getCitta());
          fornitore.setCivico(pojoRichiestaAcc.getFornitori().getCivico());
          fornitore.setCodFornitore(pojoRichiestaAcc.getFornitori().getCodfornitore());
          fornitore.setCodiceFiscale(pojoRichiestaAcc.getFornitori().getCodfiscale());
          fornitore.setCognome(pojoRichiestaAcc.getFornitori().getCognome());
          fornitore.setDesEnte(enteDest.getDesEnte());
          fornitore.setEmail(pojoRichiestaAcc.getFornitori().getEmail());
          fornitore.setFax(pojoRichiestaAcc.getFornitori().getFax());
          fornitore.setIdEnte(pojoRichiestaAcc.getFornitori().getIdEnte());
          fornitore.setIdFornitore(pojoRichiestaAcc.getFornitori().getPkfornitore());
          fornitore.setNome(pojoRichiestaAcc.getFornitori().getNome());
          fornitore.setPartitaIva(pojoRichiestaAcc.getFornitori().getPartiva());
          fornitore.setProvincia(pojoRichiestaAcc.getFornitori().getProvincia());
          fornitore.setTelefono(pojoRichiestaAcc.getFornitori().getTelefono());
          fornitore.setTitolarita(pojoRichiestaAcc.getFornitori().getTitolaritarif());
          fornitore.setVia(pojoRichiestaAcc.getFornitori().getVia());
          fornitore.setDenominazione(pojoRichiestaAcc.getDenominazione());
          
          item.setDatiFornitore(fornitore);
                              
          if(pojoRichiestaAcc.getFornitures() != null && !pojoRichiestaAcc.getFornitures().isEmpty())
          {
            Iterator listaForniture = pojoRichiestaAcc.getFornitures().iterator();
            FornitureBean[] arrForniture = new FornitureBean[pojoRichiestaAcc.getFornitures().size()];
            
            int j=0;
            
            while(listaForniture.hasNext())
            {
              Forniture pojoFornitura = (Forniture)listaForniture.next();
              
              FornitureBean itemFornitura = new FornitureBean();
              CategoriaBean itemCategoria = new CategoriaBean();
              
              itemCategoria.setDesCategoria(pojoFornitura.getCategorie().getDescategoria());
              itemCategoria.setFlgEco(pojoFornitura.getCategorie().getFlgEco());
              itemCategoria.setIdAlbo(pojoFornitura.getCategorie().getAlbo().getPkalbo());
              itemCategoria.setIdEnte(pojoFornitura.getCategorie().getIdEnte());
              
              itemFornitura.setAnno(pojoFornitura.getAnno());
              itemFornitura.setCommittente(pojoFornitura.getCommittente());
              itemFornitura.setDesFornitura(pojoFornitura.getDesfornitura());
              itemFornitura.setIdForniture(pojoFornitura.getPkforniture());
              itemFornitura.setImporto(pojoFornitura.getImporto());
              itemFornitura.setCategoria(itemCategoria);
              
              arrForniture[j] = itemFornitura;
              j++;
            }
            
            item.setDatiForniture(arrForniture);         
          }
          
          if(pojoRichiestaAcc.getCategorierics() != null)
          {
            Iterator listaCategorie = pojoRichiestaAcc.getCategorierics().iterator();
            CategoriaBean[] arrCategorie = new CategoriaBean[pojoRichiestaAcc.getCategorierics().size()];
           
            int k=0;
            
            while(listaCategorie.hasNext())
            {
              Categorieric pojoCategoriaRic = (Categorieric)listaCategorie.next();
                
              CategoriaBean itemCategoria = new CategoriaBean();
              
              itemCategoria.setDesCategoria(pojoCategoriaRic.getCategorie().getDescategoria());
              itemCategoria.setFlgEco(pojoCategoriaRic.getCategorie().getFlgEco());
              itemCategoria.setIdAlbo(pojoCategoriaRic.getCategorie().getAlbo().getPkalbo());
              itemCategoria.setIdEnte(pojoCategoriaRic.getCategorie().getIdEnte());
              
              arrCategorie[k] = itemCategoria;
              k++;
            }
            
            item.setDatiCategorie(arrCategorie);
          }
          
          if(pojoRichiestaAcc.getRichiesteaccreditamentoiters().isEmpty())
          {
            Iterator listaRicAccIter = pojoRichiestaAcc.getRichiesteaccreditamentoiters().iterator();
            PraAccreditamentoIterBean[] arrPraAccIter = new PraAccreditamentoIterBean[pojoRichiestaAcc.getRichiesteaccreditamentoiters().size()];
            
            int z =0;
            while(listaRicAccIter.hasNext())
            {
              Richiesteaccreditamentoiter pojoRicAccIter = (Richiesteaccreditamentoiter)listaRicAccIter.next();            
              
              PraAccreditamentoIterBean itemPraAccIter = new PraAccreditamentoIterBean();
              StatoBean itemStato = new StatoBean();
              
              itemStato.setCodStato(pojoRicAccIter.getStatoiter().getCodStato());
              itemStato.setIdEnte(pojoRicAccIter.getStatoiter().getIdEnte());
              itemStato.setIdStato(pojoRicAccIter.getStatoiter().getPkstatoiter());
              itemStato.setStato(pojoRicAccIter.getStatoiter().getDesStato());
              
              itemPraAccIter.setDataStato(pojoRicAccIter.getDataultimostato());
              itemPraAccIter.setIdRichiestaIter(pojoRicAccIter.getPkiter());
              itemPraAccIter.setNoteStato(pojoRicAccIter.getNotestato());
              itemPraAccIter.setResponsabile(pojoRicAccIter.getResponsabile());
              itemPraAccIter.setStato(itemStato);
              
              arrPraAccIter[z] = itemPraAccIter;
              z++;
            }
            item.setAccreditamentoIter(arrPraAccIter);
          }
                    
          arrPraAccFornitoreAlbo[i]= item;
        } 
      }      
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE HibernateException " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    
    logger.info("-> Fine metodo cercaPraAccreditamentoFornitore");
    return arrPraAccFornitoreAlbo;
  }
  
  /**
   * Il servizio implementa la ricerca delle movimentazioni avvenute sulle pratiche di
   * richiesta accreditamento secondo le modalità:
   * <ul>
   *  <li>   E' necessario specificare l'identificativo della pratica (idPratica) </li>
   *  <li>  sarà sempre presente almeno uno stato di iter di pratica avvitata </li>
   * </ul>
   * @param idPratica
   * @return lista di dati di tipo PraAccreditamentoIterBean
   */
  public PraAccreditamentoIterBean[]cercaPraAccreditamentoIter(BigDecimal idPratica ) throws SOAPException
  {
    logger.info("-> Inizio cercaPraAccreditamentoIter");
    PraAccreditamentoIterBean[] arrPraRicAccIter = null;
    
    if(idPratica != null)
    {
      logger.error("--> ERROR: idPratica IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idPratica IS NULL");        
    }
    
    logger.info("--> Parametri passati idPratica=" + idPratica);    
    
    try
    {
      Richiesteaccreditamento pojoRicAcc = (Richiesteaccreditamento)session.get(Richiesteaccreditamento.class,idPratica); 
      
      if(pojoRicAcc != null)
      {
        Set listaStati = pojoRicAcc.getRichiesteaccreditamentoiters();
        Iterator iter = listaStati.iterator();
        
        arrPraRicAccIter = new PraAccreditamentoIterBean[listaStati.size()]; 
        
        int i=0;
        while(iter.hasNext())        
        {
          Richiesteaccreditamentoiter pojoRicAccIter = (Richiesteaccreditamentoiter)iter.next();
          
          PraAccreditamentoIterBean praAccIter = new PraAccreditamentoIterBean();
          StatoBean stato = new StatoBean();
          
          stato.setCodStato(pojoRicAccIter.getStatoiter().getCodStato());
          stato.setIdEnte(pojoRicAccIter.getStatoiter().getIdEnte());
          stato.setIdStato(pojoRicAccIter.getStatoiter().getPkstatoiter());
          stato.setStato(pojoRicAccIter.getStatoiter().getDesStato());
          
          praAccIter.setDataStato(pojoRicAccIter.getDataultimostato());
          praAccIter.setIdRichiestaIter(pojoRicAccIter.getPkiter());
          praAccIter.setNoteStato(pojoRicAccIter.getNotestato());
          praAccIter.setResponsabile(pojoRicAccIter.getResponsabile());
          praAccIter.setStato(stato);
          
          arrPraRicAccIter[i] = praAccIter;
          i++;
        }
      
      }
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE HibernateException " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }    
    
    logger.info("-> Fine cercaPraAccreditamentoIter");
    return arrPraRicAccIter;
  }
  
  /**
   * Il servizio implementa il cambio di stato di una pratica di richiesta
   * di accreditamento secondo le modalità sotto elencate:
   * <ul>
   *  <li> E' necessario specificare il bean identificativo della pratica (PraAccreditamentoIterBean)</li>
   * </ul>
   * @param iterPratica
   * @return
   */
  public Boolean cambiaStatoPraAccreditamento(PraAccreditamentoIterBean iterPratica ) throws SOAPException
  {
    logger.info("-> Inizio metodo cambiaStatoPraAccreditamento");
    
    Boolean flag = new Boolean("false");
    Transaction tx = null;
    
    if(iterPratica == null)
    {
      logger.error("--> ERROR: PraAccreditamentoIterBean IS NULL");
      throw new SOAPException("SOAP-ENV:Client","PraAccreditamentoIterBean IS NULL");       
    }
    
    if(iterPratica.getIdRichiestaAcc() == null)
    {
      logger.error("--> ERROR: IdRichiestaAcc IS NULL");
      throw new SOAPException("SOAP-ENV:Client","IdRichiestaAcc IS NULL");       
    }
    
    logger.info("-> parametri passati PraAccreditamentoIterBean: " + iterPratica.toString());
    
    try
    {
      //Caricamento della richiesta di accreditamento
      
      Richiesteaccreditamento pojoRicAcc = (Richiesteaccreditamento)session.get(Richiesteaccreditamento.class,iterPratica.getIdRichiestaAcc());
      
      if(pojoRicAcc != null)
      {
        tx = session.beginTransaction();
       
        Richiesteaccreditamentoiter pojoRicAccIter = new Richiesteaccreditamentoiter();
        Statoiter stato = new Statoiter();
       
        stato.setCodStato(iterPratica.getStato().getCodStato());
        stato.setDesStato(iterPratica.getStato().getStato());
        stato.setIdEnte(iterPratica.getStato().getIdEnte());
               
        pojoRicAccIter.setDataultimostato(iterPratica.getDataStato());
        pojoRicAccIter.setDtMod(new Date());
        pojoRicAccIter.setIdEnte(pojoRicAcc.getIdEnteDestinatario());
        pojoRicAccIter.setNotestato(iterPratica.getNoteStato());
        pojoRicAccIter.setResponsabile(iterPratica.getResponsabile());
        pojoRicAccIter.setStatoiter(stato);        
        pojoRicAccIter.setRichiesteaccreditamento(pojoRicAcc);
                              
        Set hsAcc = new HashSet();
        hsAcc.add(pojoRicAccIter);
        pojoRicAcc.setRichiesteaccreditamentoiters(hsAcc);
        
        session.saveOrUpdate(pojoRicAcc);
        session.flush();
        tx.commit();
      }
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
        
    logger.info("-> Fine metodo cambiaStatoPraAccreditamento");
    
    return flag;
  }
  
  /**
   * Il servizio ritorna l'elenco degli stati di iter di accreditamento in uso
   * presso un ente, in mancanza di una profilazione dell'ente o idEnte = null
   * vengono restituiti gli stati di default.
   * <P>
   * @param pIdEnte
   * @return StatoBean[]
   */
  public StatoBean[] listaStatoIter(BigDecimal pIdEnte) throws SOAPException
  {
    logger.info("-> Inizio metodo listaStatoIter");
    StatoBean[] arrStatoBean = null;
    
    try
    {
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("SELECT new it.saga.siscotel.beans.albofornitori.StatoBean(");
      strQuery.append("obj.pkstatofornitore, ");
      strQuery.append("obj.idEnte, ");
      strQuery.append("obj.codStato, ");
      strQuery.append("obj.desStato) ");
      strQuery.append("FROM Statoiter as obj WHERE ");
      
      if(pIdEnte == null)
        strQuery.append("obj.idEnte=0");
      
      if(pIdEnte != null)
        strQuery.append("obj.idEnte=" + pIdEnte);
      
      Query query = session.createQuery(strQuery.toString());   
      List lista = query.list();   
      
      if(lista != null)
      { 
        arrStatoBean = new StatoBean[lista.size()];
      
        for(int i=0; i<lista.size(); i++)
        {
          arrStatoBean[i] = (StatoBean)lista.get(i);
        }
      }
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE HibernateException " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    logger.info("-> Inizio metodo listaStatoIter");
    
    return arrStatoBean;
  }
  
  // ---- GESTIONE ALBO ---- 
  
  /**
   * Il servizio implementa la creazione di un nuovo albo presso un ente.
   * L'albo viene creato con il proprio periodo di validità compreso tre
   * le due date (Inizio e Fine validità ) del bean AlboBean.
   * Consultando lo stato dell'albo si individuano le seguenti possibilità:
   * <ul>
   *  <li> Albo ATTIVO : se la data corrente è compresa tra le due; </li>
   *  <li> Albo SCADUDO : se la data corrente è oltre la data finale; </li>
   *  <li> Albo CHIUSO : se la data corrente è precedente la data iniziale o si
   *                     è effettuata una chiusura forzata con l'apposito servizio. </li>
   * </ul>
   *                   
   * @param albo
   * @return identificativo univoco dell'albo creato
   */
  public BigDecimal creaAlbo(AlboBean albo) throws SOAPException  
  {
    logger.info("-> Inizio metodo creaAlbo");
    
    BigDecimal idAlbo = null;
    Transaction tx = null;
    
    if(albo == null)
    {
      logger.error("--> ERROR: AlboBean IS NULL");
      throw new SOAPException("SOAP-ENV:Client","albo IS NULL");     
    }
    
    if(albo.getIdEnte() == null)
    {
      logger.error("--> ERROR: idEnte IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idEnte IS NULL");      
    }

    if(albo.getDataFineValidita() == null)
    {
      logger.error("--> ERROR: dataFineValidita IS NULL");
      throw new SOAPException("SOAP-ENV:Client","dataFineValidita IS NULL");      
    }

    if(albo.getDataInizioValidita() == null)
    {
      logger.error("--> ERROR: dataInizioValidita IS NULL");
      throw new SOAPException("SOAP-ENV:Client","dataInizioValidita IS NULL");      
    }

    if(albo.getDesAlbo() == null)
    {
      logger.error("--> ERROR: desAlbo IS NULL");
      throw new SOAPException("SOAP-ENV:Client","desAlbo IS NULL");      
    }
       
    logger.info("--> parametri passati AlboBean: " + albo.toString());
    
    try
    {
      tx = session.beginTransaction();
      
      Albo pojoAlbo = new Albo();
      pojoAlbo.setDesAlbo(albo.getDesAlbo());
      pojoAlbo.setNote(albo.getNote());      
      pojoAlbo.setIdEnte(albo.getIdEnte());
      pojoAlbo.setFlgChiuso(AlboBean.COD_STATO_APERTO);
      pojoAlbo.setDtMod(new Date());
      
      Alboiter pojoAlboIter = new Alboiter();
      pojoAlboIter.setDatafinevalidita(albo.getDataFineValidita());
      pojoAlboIter.setDatainiziovalidita(albo.getDataInizioValidita());
      pojoAlboIter.setAlbo(pojoAlbo);
      
      Set set = new HashSet();
      set.add(pojoAlboIter);
      pojoAlbo.setAlboiters(set);
      
      session.save(pojoAlbo);
      session.flush();
      
      tx.commit();  
      
      idAlbo = pojoAlbo.getPkalbo();
      logger.info("--> idAlbo = " + idAlbo);
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    
    logger.info("-> Fine metodo creaAlbo");
    
    return idAlbo;
  }
  
  /**
   * Il servizio ritorna l'elenco degli albi presenti nell'aggregazione.
   * 
   * @param idEnte
   * @return lista degli albi che soddisfano i criteri impostati
   */
  public AlboBean[] cercaAlbo(BigDecimal idEnte) throws SOAPException
  {
    logger.info("-> Inizio metodo cercaAlbo");
    
    AlboBean[] arrAlboBean = null;
    
    if(idEnte == null)
    {
      logger.error("--> ERROR: idEnte IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idEnte IS NULL");      
    }
    
    logger.info("--> parametro passato idEnte = " + idEnte);
    
    try
    {
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("SELECT new it.saga.siscotel.beans.albofornitori.AlboBean(");
      strQuery.append("obj1.pkalbo,obj1.idEnte,obj1.desAlbo,obj3.desEnte,obj1.note,");
      strQuery.append("obj2.datainiziovalidita,obj2.datafinevalidita,obj1.flgChiuso) ");
      strQuery.append("FROM Albo as obj1, Alboiter as obj2, Entealbo obj3 ");
      strQuery.append("WHERE obj1.pkalbo = obj2.albo.pkalbo AND ");
      strQuery.append("obj1.idEnte = obj3.idEnte AND ");
      strQuery.append("obj1.idEnte=" + idEnte);
      
      Query query = session.createQuery(strQuery.toString());
      List lista = query.list();
      
      if(lista != null)
      {
        logger.info("--> Trovati N." + lista.size() + " elementi");
        arrAlboBean = new AlboBean[lista.size()];
        
        for(int i=0; i<lista.size(); i++)
        {
          arrAlboBean[i] = (AlboBean)lista.get(i);
          logger.info("--> " + ((AlboBean)lista.get(i)).toString());
        }
      }

    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());         
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());         
    }
        
    logger.info("-> Fine metodo cercaAlbo");
    
    return arrAlboBean;  
  }
  
  /**
   * Il servizio implementa il rinnovo di un albo, vengono inseriti i dati relativi a:
   * <ul>
   *  <li> Data Inizio Validità </li>
   *  <li> Data Fine Validità </li>
   * </ul>
   * <BR>
   *  Sono quindi obbligatori i campi:
   *  <ul>
   *    <li> IdAlbo </li>
   *    <li> DataInizioValidita </li>
   *    <li> DataFineValidita </li>
   *  </ul>
   *  <BR>
   *  <B>NOTA</B>
   *  Viene inserita una nuova riga nella tabella ALBOITER, in questo caso
   *  si garantisce la continuità tra il nuovo e il vecchi albo.
   *  
   * @param albo
   * @return TRUE in caso di operazione conclusa correttamente, FALSE in caso di errore
   */
  public Boolean rinnovaAlbo(AlboBean albo) throws SOAPException
  {
    logger.info("-> Inizio metodo rinnovaAlbo()");
    
    Boolean flag = new Boolean("false");
    
    Transaction tx = null;
    
    if(albo == null)
    {
      logger.error("--> ERROR: AlboBean IS NULL");
      throw new SOAPException("SOAP-ENV:Client","AlboBean IS NULL");        
    }
    if(albo.getIdAlbo() == null)
    {
      logger.error("--> ERROR: IdAlbo IS NULL");
      throw new SOAPException("SOAP-ENV:Client","IdAlbo IS NULL");        
    }

    if(albo.getDataInizioValidita() == null)
    {
      logger.error("--> ERROR: DataInizioValidita IS NULL");
      throw new SOAPException("SOAP-ENV:Client","DataInizioValidita IS NULL");        
    }

    if(albo.getDataFineValidita() == null)
    {
      logger.error("--> ERROR: DataFineValidita IS NULL");
      throw new SOAPException("SOAP-ENV:Client","DataFineValidita IS NULL");        
    }
    
    logger.info("--> " + albo.toString());
    
    try
    {
      tx = session.beginTransaction();
      
      Albo pojoAlbo = (Albo)session.get(Albo.class, albo.getIdAlbo());
      pojoAlbo.setDesAlbo(albo.getDesAlbo());
      session.saveOrUpdate(pojoAlbo);
    
      Alboiter pojoAlboIter= new Alboiter();
      pojoAlboIter.setDatainiziovalidita(albo.getDataInizioValidita());
      pojoAlboIter.setDatafinevalidita(albo.getDataFineValidita());
      pojoAlboIter.setAlbo(pojoAlbo);      
      
      session.save(pojoAlboIter);
      session.flush();
      
      tx.commit();
      flag = new Boolean("true");
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
        
    logger.info("-> Fine metodo rinnovaAlbo()");
    
    return flag;
  }
  
  /**
   * Il servizio permette di chiudere un albo, si tratta di una eliminazione 
   * a livello logico e permette di definire uno stato nel quale non si permette
   * l'accreditamento di nuovi fornitori.
   * 
   * @param idAlbo
   * @return TRUE nel caso l'operazione sia conclusa con successo, FALSE altrimenti
   */
  public Boolean chiudiAlbo(BigDecimal idAlbo) throws SOAPException
  {
    logger.info("-> Inizio metodo chiudiAlbo()");
    
    Boolean flag = new Boolean("false");
    Transaction tx = null;
    
    if(idAlbo == null)
    {
      logger.error("--> ERROR: idAlbo IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idAlbo IS NULL");       
    }
    
    logger.info("--> parametro passato idAlbo = " + idAlbo);
    
    try
    {
      tx = session.beginTransaction();
      Albo pojo_albo = (Albo)session.get(Albo.class,idAlbo);
        
      pojo_albo.setFlgChiuso(AlboBean.COD_STATO_CHIUSO);
      
      session.saveOrUpdate(pojo_albo);      
      session.flush();
      
      tx.commit();
      flag = new Boolean("true");
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    
    logger.info("-> Fine metodo chiudiAlbo()");
    
    return flag;    
  }
  
  /**
   * Il servizio permette di eliminare fisicamente un albo (e le relative categorie) 
   * dal database. L'eliminazione può avere luogo solo nel caso in cui non siano presenti 
   * fornitori collegati.
   * 
   * @param idAlbo
   * @return
   */
  public Boolean eliminaAlbo(BigDecimal idAlbo) throws SOAPException
  {
    logger.info("-> Inizio metodo eliminaAlbo");
    
    Boolean flag = new Boolean("false"); 
    Transaction tx = null;
    
    if(idAlbo == null)
    {
      logger.error("--> ERROR: idAlbo IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idAlbo IS NULL");       
    }

    logger.info("--> parametro passato idAlbo = " + idAlbo);
    
    try
    {
      tx = session.beginTransaction();
      
      //Cancellazione ALBO
      Albo pojo_albo = (Albo)session.get(Albo.class,idAlbo);
      session.delete(pojo_albo);
      session.flush();
      //Cancellazione Richieste Accreditamento
      
      //Cancellazione Relazione ALBO FORNITORI CATASTO STATO
      
      tx.commit();
      flag = new Boolean("true");
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    
    return flag;       
  }


  // - GESTIONE FORNITORI - 
  
  /**
   * Il servizio implementa l'inserimento diretto di un fornitore nell'albo, senza
   * passare dell'iter di accreditamento. E' necessario specificare l'identificativo dell'albo
   * 
   * @param idAlbo
   * @param fornitore
   * @return identificativo uunivoco del record creato
   */
  public BigDecimal inserisciFornitoreInAlbo(BigDecimal idAlbo,
                                             BigDecimal[] idCategorie, 
                                             FornitoreBean fornitore ) throws SOAPException
  {
    logger.info("-> Inizio metodo inserisciFornitoreInAlbo");
    
    BigDecimal idFornitore = null;
    Transaction tx = null;
    
    if(idAlbo == null)
    {
      logger.error("--> ERROR: idAlbo IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idAlbo IS NULL");       
    }

    if(idCategorie == null && idCategorie.length == 0)
    {
      logger.error("--> ERROR: idCategorie IS NULL or length = 0");
      throw new SOAPException("SOAP-ENV:Client","idCategorie IS NULL or length = 0");       
    }

    if(fornitore == null)
    {
      logger.error("--> ERROR: fornitore IS NULL");
      throw new SOAPException("SOAP-ENV:Client","fornitore IS NULL");      
    }
    
    logger.info("--> parametri passati idAlbo = " + idAlbo);
    logger.info("--> parametri passati FornitoreBean = " + fornitore.toString());
    
    try
    {
      tx = session.beginTransaction();
      
      //Caricamento dello stato
      Statofornitore statoAttivo = (Statofornitore)session.get(Statofornitore.class,FornitoreBean.STATO_FORNITORE_ATTIVO);
        
      //Caricamento dell'albo
      Albo pojoAlbo = (Albo)session.get(Albo.class,idAlbo);
      
      if(pojoAlbo == null)
      {
        logger.error("--> ERROR: ALBO non trovato");
        throw new SOAPException("SOAP-ENV:Client","ALBO non trovato");         
      }
      
      //Caricamento delle categorie
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("FROM Categorie as obj WHERE ");
      
      for(int i=0; i<idCategorie.length; i++)
      {
        strQuery.append("obj.pkcategoria = " + idCategorie[i] + " OR ");
      }
                
      Query query = session.createQuery(strQuery.substring(0,strQuery.length()-4));
      logger.debug("--> Query ricerca categorie: " + query.getQueryString());
      
      List lista = query.list();
            
      if(lista != null)
      {

        Fornitori pojoFornitore = new Fornitori();
              
        pojoFornitore.setCap(fornitore.getCap().toString());
        pojoFornitore.setCivico(fornitore.getCivico().toString());
        pojoFornitore.setCodfiscale(fornitore.getCodiceFiscale());
        pojoFornitore.setCodfornitore(fornitore.getCodFornitore());
        pojoFornitore.setEmail(fornitore.getEmail());
        pojoFornitore.setFax(fornitore.getFax());
        pojoFornitore.setIdEnte(fornitore.getIdEnte());
        pojoFornitore.setPartiva(fornitore.getPartitaIva());
        pojoFornitore.setProvincia(fornitore.getProvincia());
        pojoFornitore.setTelefono(fornitore.getTelefono());
        pojoFornitore.setTitolaritarif(fornitore.getTitolarita());
        pojoFornitore.setVia(fornitore.getVia());
        pojoFornitore.setCitta(fornitore.getCitta());           
        pojoFornitore.setDenominazione(fornitore.getDenominazione());
        pojoFornitore.setNome(fornitore.getNome());
        pojoFornitore.setCognome(fornitore.getCognome());
                
        HashSet item = new HashSet();
        
        for(int j=0; j<lista.size(); j++)
        {
          AlboForCatStato pojoAlboForCatStato = new AlboForCatStato();
          pojoAlboForCatStato.setCategorie((Categorie)lista.get(j));
          pojoAlboForCatStato.setFornitori(pojoFornitore);
          pojoAlboForCatStato.setAlbo(pojoAlbo);
          pojoAlboForCatStato.setStatofornitore(statoAttivo);
          pojoAlboForCatStato.setDataultimostato(new Date());
                    
          item.add(pojoAlboForCatStato);
        }

        pojoFornitore.setAlboForCatStatos(item);
              
        session.save(pojoFornitore);
        session.flush();
        
        tx.commit();
        idFornitore = pojoFornitore.getPkfornitore();
      }
    }
    catch(HibernateException err)
    {
      tx.rollback();
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());      
    }
    catch(Exception err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());        
    }
    
    logger.info("-> Fine metodo inserisciFornitoreInAlbo");
    
    return idFornitore;
  }
  
  /**
   * Il servizio implementa la ricerca dei fornitori e del relativo stato secondo
   * le modalità elencate:
   * <ul>
   *  <li> 
   *    E' consentito pilotare la ricerca specificando l'identificativo dell'ente 
   *    dell'albo o della categoria.
   *  </li>
   *  <li>
   *    E' consentito limitare la ricerca ai soli fornitori che si trovano in un 
   *    determinato stato.
   *  </li>
   * I parametri opzionali non utilizzati devono essere NULL
   * 
   * @param idEnte
   * @param idAlbo
   * @param idStato
   * @param idCategoria
   * @return lista di beans di tipo FornitoreRidottoBean
   */
  public FornitoreRidottoBean[] cercaFornitore(BigDecimal idEnte,
                                               BigDecimal idAlbo,
                                               BigDecimal idStato,
                                               BigDecimal idCategoria) throws SOAPException
  {
    logger.info("-> Inizio metodo cercaFornitore()");
    
    FornitoreRidottoBean[] arrFornitoreRid = null;
    
    try
    {
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("SELECT ");
      strQuery.append("obj1.pkfornitore,");
      strQuery.append("obj1.idEnte,");
      strQuery.append("obj1.denominazione,");
      strQuery.append("obj2.categorie.descategoria,");
      strQuery.append("obj2.albo.desAlbo,");
      strQuery.append("obj3.desEnte,");
      strQuery.append("obj2.statofornitore.pkstatofornitore,");
      strQuery.append("obj2.statofornitore.idEnte,");
      strQuery.append("obj2.statofornitore.desStato ");                      
      strQuery.append("FROM Fornitori as obj1, AlboForCatStato as obj2, Entealbo as obj3 ");
      strQuery.append("WHERE obj2.fornitori = obj1 AND ");
      strQuery.append("obj1.idEnte = obj3.idEnte");
      
      if(idEnte != null)
        strQuery.append(" AND obj1.idEnte = " + idEnte);  

      if(idAlbo != null)
        strQuery.append(" AND obj2.albo.pkalbo = " + idAlbo); 

      if(idStato != null)
        strQuery.append(" AND obj2.statofornitore.pkstatofornitore = " + idStato); 

      if(idCategoria != null)
        strQuery.append(" AND obj2.categorie.pkcategoria = " + idCategoria); 
            
      Query query = session.createQuery(strQuery.toString());
      logger.debug("--> query ricerca: " + query.getQueryString());
      
      List listaFornitori = query.list();

      if(listaFornitori != null)
      {
        arrFornitoreRid = new FornitoreRidottoBean[listaFornitori.size()];
        
        for(int i=0; i<listaFornitori.size(); i++)
        {
          Object[] arrobj = (Object[])listaFornitori.get(i);
          
          FornitoreRidottoBean item = new FornitoreRidottoBean();
          StatoBean stato = new StatoBean();
          
          item.setIdFornitore((BigDecimal)arrobj[0]);
          item.setIdEnte((BigDecimal)arrobj[1]);
          item.setDenominazione((String)arrobj[2]);          
          item.setDesCategoria((String)arrobj[3]);
          item.setDesAlbo((String)arrobj[4]);
          item.setDesEnte((String)arrobj[5]);
          stato.setIdStato((BigDecimal)arrobj[6]);
          stato.setIdEnte((BigDecimal)arrobj[7]);
          stato.setStato((String)arrobj[8]);
          item.setDatiStato(stato);          
          arrFornitoreRid[i] = item;          
        }
      }      
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    
    logger.info("-> Fine metodo cercaFornitore()");
    
    return arrFornitoreRid;
  }
  
  /**
   * Il servizio implementa la ricerca dei dettagli di un fornitore dato 
   * l'identificativo, i dati sono validi nell'ambito di un ente.
   *
   * @param idFornitore
   * @return FornitoreBean dati relativi al fornitore
   */
  public FornitoreBean cercaDettagliFornitore(BigDecimal idFornitore) throws SOAPException
  {
    logger.info("-> Inizio metodo cercaDettagliFornitore()");
    
    FornitoreBean fornitore = null;
    
    if(idFornitore == null)
    {
      logger.error("--> ERROR: idFornitore is NULL");
      throw new SOAPException("SOAP-ENV:Client","idFornitore is NULL"); 
    }
    
    logger.info("--> paramentro passato idFornitore = " + idFornitore);
    
    try
    {
       
      StringBuffer strQuery  = new StringBuffer();
      strQuery.append("SELECT new it.saga.siscotel.beans.albofornitori.FornitoreBean(");
      strQuery.append("obj.pkfornitore, ");
      strQuery.append("obj.codfiscale, ");
      strQuery.append("obj.codfornitore, ");
      strQuery.append("obj.partiva, ");
      strQuery.append("obj.titolaritarif, ");
      strQuery.append("obj.idEnte, ");
      strQuery.append("obj.civico, ");
      strQuery.append("obj.cap, ");
      strQuery.append("obj.nome, ");
      strQuery.append("obj.cognome, ");
      strQuery.append("obj.via, ");
      strQuery.append("obj.provincia, ");
      strQuery.append("obj.telefono, ");
      strQuery.append("obj.fax, ");
      strQuery.append("obj.email, ");
      strQuery.append("obj3.desEnte, ");
      strQuery.append("obj.citta, ");
      strQuery.append("obj.denominazione) ");
      strQuery.append("FROM Fornitori as obj, Entealbo as obj3 ");
      strQuery.append("WHERE obj3.idEnte = obj.idEnte ");
      strQuery.append("AND obj.pkfornitore = " + idFornitore);
           
      Query query = session.createQuery(strQuery.toString());
      fornitore = (FornitoreBean)query.uniqueResult();
      
      if(fornitore == null)
      {
        logger.error("--> Fornitore non trovato");
        throw new SOAPException("SOAP-ENV:Client","nessun fornitore trovato con idFornitore=" + idFornitore);        
      }
        
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    
    return fornitore;
  }
  
  /**
   * Il servizio implementa il cambio di stato di un fornitore relativamente ad un
   * albo e ad una categoria.
   * 
   * @param idAlbo
   * @param idFornitore
   * @param idCategoria
   * @param idNuovoStato
   * @return TRUE in casol'operazione è conclusa con successo, FALSE altrimenti
   */
  public Boolean cambiaStatoFornitore(BigDecimal idAlbo,
                                      BigDecimal idFornitore,
                                      BigDecimal idCategoria,
                                      BigDecimal idNuovoStato )throws SOAPException
  {
    logger.info("-> Inizio metodo cambiaStatoFornitore");
    Boolean flag = new Boolean("false");
    Transaction tx = null;
    
    if(idAlbo == null)
    {
      logger.error("--> ERROR: idAlbo is NULL");
      throw new SOAPException("SOAP-ENV:Client","idAlbo is NULL");      
    }

    if(idFornitore == null)
    {
      logger.error("--> ERROR: idFornitore is NULL");
      throw new SOAPException("SOAP-ENV:Client","idFornitore is NULL");      
    }
    
    if(idCategoria == null)
    {
      logger.error("--> ERROR: idCategoria is NULL");
      throw new SOAPException("SOAP-ENV:Client","idCategoria is NULL");      
    }

    if(idNuovoStato == null)
    {
      logger.error("--> ERROR: idNuovoStato is NULL");
      throw new SOAPException("SOAP-ENV:Client","idNuovoStato is NULL");      
    }
    
    logger.info("-> parametri passati idAlbo = " + idAlbo);
    logger.info("-> parametri passati idFornitore = " + idFornitore);
    logger.info("-> parametri passati idCategoria = " + idCategoria);
    logger.info("-> parametri passati idNuovoStato = " + idNuovoStato);
    
    try
    {
      //caricamento nuovo stato
      Statofornitore newStato = (Statofornitore)session.get(Statofornitore.class,idNuovoStato);

      StringBuffer strQuery = new StringBuffer();
      strQuery.append("FROM AlboForCatStato as obj ");
      strQuery.append("WHERE ");
      strQuery.append("obj.fornitori.pkfornitore = " + idFornitore + " ");
      strQuery.append("AND obj.albo.pkalbo = " + idAlbo + " ");
      strQuery.append("AND obj.categorie.pkcategoria = " + idCategoria);
      
      Query query = session.createQuery(strQuery.toString());
      AlboForCatStato pojoAlboForCatStato = (AlboForCatStato)query.uniqueResult();
      
      if(newStato != null && pojoAlboForCatStato != null)
      {
        tx = session.beginTransaction();
        pojoAlboForCatStato.setStatofornitore(newStato);
        pojoAlboForCatStato.setDtMod(new Date());
        
        session.saveOrUpdate(pojoAlboForCatStato);
        session.flush();
        
        tx.commit();
        
        flag = new Boolean("true");
      }
    }
    catch(HibernateException err)
    {
      tx.rollback();
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    catch(Exception err)
    {
      tx.rollback();
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }    
    
    return flag;
  }
  
  /**
   * Il servizio implementa l'accreditamento di un fornitore relativamente 
   * alla richiesta fatta per una albo e per una lista di categorie.
   * Le categorie devono essere specificate in quanto si prevede possano essere
   * un sottoinsieme di quelle della richiesta.
   * E' necessario specificare l'identificativo della richiesta e l'elenco 
   * degli identificativi di categoria validi nell'ente (idCategoria[])
   * @param idRichiesta
   * @param idCategoria
   * @return TRUE se l'operazione è andata a buon fine false altrimenti
   */
  public Boolean AccreditaFornitore(BigDecimal idRichiesta,
                                    BigDecimal[] idCategoria) throws SOAPException
  {
    logger.info("Inizio metodo AccreditaFornitore");
    Transaction tx = null;
    Boolean flag = new Boolean("false");
    
    if(idRichiesta == null)
    {
      logger.error("--> ERRORE il parametro idRichiesta is null");
      throw new SOAPException("SOAP-ENV:Client","idRichiesta IS NULL");       
    }

    if(idCategoria == null)
    {
      logger.error("--> ERRORE il parametro idCategoria[] is null");
      throw new SOAPException("SOAP-ENV:Client","idCategoria[] IS NULL");       
    }    
    
    for(int i=0; i<idCategoria.length; i++)
    {
      logger.info("-> (" + i + ") " + idCategoria[i]);
    }
    
    try
    {
      //Recupero della richiesta
      Richiesteaccreditamento pojoRichiestaAccc = (Richiesteaccreditamento)session.get(Richiesteaccreditamento.class,idRichiesta);
      
      if(pojoRichiestaAccc != null)
      {
        tx = session.beginTransaction();
        
        for(int j=0; j<idCategoria.length; j++)
        {
          AlboForCatStato pojoAlboForCat = new AlboForCatStato();
          
          pojoAlboForCat.setFornitori(pojoRichiestaAccc.getFornitori());
          pojoAlboForCat.setAlbo(pojoRichiestaAccc.getAlbo());
          
          //Caricamento categorie
          Categorie pojoCategorie = (Categorie)session.get(Categorie.class,idCategoria[j]);
          pojoAlboForCat.setCategorie(pojoCategorie);
          
          //pojoAlboForCat.setDataultimostato();
          
          pojoAlboForCat.setDtMod(new Date());
          pojoAlboForCat.setNotestato(pojoRichiestaAccc.getNote());
          
          //caricamento dello stato
          Statofornitore pojoStatoFornitore = (Statofornitore)session.get(Statofornitore.class,FornitoreBean.STATO_FORNITORE_ATTIVO);
          pojoAlboForCat.setStatofornitore(pojoStatoFornitore);
          
          session.save(pojoAlboForCat);
          session.flush();
          
          tx.commit();
          flag = new Boolean("true");
        }
      }
    
    }
    catch(HibernateException err)
    {
      tx.rollback();
      logger.error("--> ERRORE  HibernateException" + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }    
    catch(Exception err)
    {
      tx.rollback();
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    
    logger.info("Fine metodo AccreditaFornitore");
    
    return flag;
  }
  
  /**
   * Il servizio ritorna l'elenco degli stati di iter in uso presso un ente,
   * in mancanza di una profilazione dell'ente vengono restituiti i valori di 
   * default.
   * 
   * @param pIdEnte se passato a NULL il metodo restituisce i valori di default
   * @return lista di stati
   */
  public StatoBean[] listaStatoFornitore(BigDecimal pIdEnte) throws SOAPException
  {
    logger.info("-> Inizio metodo listaStatoFornitore");
    logger.info("--> parametri passati IdEnte=" + pIdEnte);
    
    StatoBean[] arrStatoBean = null;
    
    try
    {
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("SELECT new it.saga.siscotel.beans.albofornitori.StatoBean(");
      strQuery.append("obj.pkstatofornitore, ");
      strQuery.append("obj.idEnte, ");
      strQuery.append("obj.codStato, ");
      strQuery.append("obj.desStato) ");
      strQuery.append("FROM Statofornitore as obj WHERE ");
      
      if(pIdEnte == null)
        strQuery.append("obj.idEnte=0");
      
      if(pIdEnte != null)
        strQuery.append("obj.idEnte=" + pIdEnte);
      
      Query query = session.createQuery(strQuery.toString());   
      List lista = query.list();   
      
      if(lista != null)
      { 
        arrStatoBean = new StatoBean[lista.size()];
      
        for(int i=0; i<lista.size(); i++)
        {
          arrStatoBean[i] = (StatoBean)lista.get(i);
        }
      }
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE HibernateException " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }      
    
    logger.info("-> Fine metodo listaStatoIter"); 
    
    return arrStatoBean;
  }
  
  /**
   * Il servizio permette di eliminare un fornitore dato il suo identificativo
   * univoco (idFornitore)
   * @param idFornitore
   * @return
   * @throws Exception
   */
  public Boolean eliminaFornitore(BigDecimal idFornitore) throws Exception
  {
    logger.info("-> Inizio metodo eliminaFornitore");
    
    Transaction tx = null;
    Boolean flag = new Boolean("false");
    
    if(idFornitore == null)
    {
      logger.error("--> ERRORE il parametro idFornitore is null");
      throw new SOAPException("SOAP-ENV:Client","idFornitore IS NULL");       
    }
    
    try
    {
      Fornitori pojoFornitore = (Fornitori)session.get(Fornitori.class,idFornitore);
      
      if(pojoFornitore != null)
      {
        tx = session.beginTransaction();
        
        session.delete(pojoFornitore);
        session.flush();
        
        tx.commit();
        flag = new Boolean("true");
      }
      else
      {
        logger.error("--> Fornitore non trovato");
        throw new SOAPException("SOAP-ENV:Client","Fornitore non trovato");        
      }
      
    }
    catch(HibernateException err)
    {
      tx.rollback();
      logger.error("--> ERRORE  HibernateException" + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }    
    catch(Exception err)
    {
      tx.rollback();
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    
    return flag;
  }
  
  /**
   * Il servizio permette di cercare il fornitore dato il suo identificativo
   * univoco (idFornitore) e quello dell'albo (idAlbo)
   * 
   * @param idAlbo
   * @param idFornitore
   * @return
   * @throws Exception
   */
  public CategoriaBean[] cercaCategorieFornitoreAlbo(BigDecimal idAlbo, BigDecimal idFornitore) throws Exception
  {
    logger.info("-> Inizio metodo cercaCategorieFornitoreAlbo");
    CategoriaBean[] arrCategorie = null;
    
    if(idAlbo == null)
    {
      logger.error("--> ERRORE il parametro idAlbo is null");
      throw new SOAPException("SOAP-ENV:Client","idAlbo IS NULL");      
    }

    if(idFornitore == null)
    {
      logger.error("--> ERRORE il parametro idFornitore is null");
      throw new SOAPException("SOAP-ENV:Client","idFornitore IS NULL");      
    }
    
    logger.info("--> parametri passati idAlbo=" + idAlbo);
    logger.info("--> parametri passati idFornitore=" + idFornitore);
    
    try
    {
      StringBuffer strQuery = new StringBuffer();
      strQuery.append("SELECT obj.categorie ");
      strQuery.append("FROM AlboForCatStato as obj ");
      strQuery.append("WHERE obj.albo.pkalbo = " + idAlbo + " AND ");
      strQuery.append("obj.fornitori.pkfornitore = " + idFornitore);
      
      Query query = session.createQuery(strQuery.toString());
      List lista = query.list();
      
      if(lista != null)
      {
        arrCategorie = new CategoriaBean[lista.size()];
        
        for(int i=0; i<lista.size(); i++)
        {
          CategoriaBean item = new CategoriaBean();
          Categorie categoria = (Categorie)lista.get(i);
          
          item.setDesCategoria(categoria.getDescategoria());
          item.setFlgEco(categoria.getFlgEco());
          item.setIdAlbo(categoria.getAlbo().getPkalbo());
          item.setIdCategoria(categoria.getPkcategoria());
          item.setIdEnte(categoria.getIdEnte());
          
          arrCategorie[i] = item; 
        }
      }
    }
    catch(HibernateException err)
    {
      logger.error("--> ERRORE  HibernateException" + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    
    logger.info("-> Fine metodo cercaCategorieFornitoreAlbo");
    return arrCategorie;
    
  }

  // -- GESTIONE CATEGORIE  --
  
  /**
   * Il servizio implementa l'inserimento della categoria in uso presso un ente
   * secondo le modalità sotto elencate.
   * E' necessario specificare l'indentificazione dell'ente nel bean CategoriaBean
   *  <P>
   * @param categoria
   * @return identificativo univoco del record creato
   */
  public BigDecimal inserisciCategoria(CategoriaBean categoria) throws SOAPException
  {
    logger.info("-> Inizio metodo inserisciCategoria()");
    
    BigDecimal idCategoria = null;
    Transaction tx = null;
    
    if(categoria == null)
    {
      logger.error("--> ERRORE il parametro CategoriaBean is null");
      throw new SOAPException("SOAP-ENV:Client","CategoriaBean IS NULL");
    }
    
    if(categoria.getIdEnte() == null)
    {
      logger.error("--> ERRORE il campo idEnte is null");
      throw new SOAPException("SOAP-ENV:Client","idEnte IS NULL");     
    }

    if(categoria.getIdAlbo() == null)
    {
      logger.error("--> ERRORE il campo idAlbo is null");
      throw new SOAPException("SOAP-ENV:Client","idAlbo IS NULL");     
    }
    
    logger.info("--> " + categoria.toString());
    
    try
    {
      tx = session.beginTransaction();
      
      Albo pojoAlbo = (Albo)session.get(Albo.class,categoria.getIdAlbo());
      
      if(pojoAlbo != null)
      {
        Categorie pojoCategoria = new Categorie();
        pojoCategoria.setDescategoria(categoria.getDesCategoria());
        pojoCategoria.setFlgEco(categoria.getFlgEco());
        pojoCategoria.setIdEnte(categoria.getIdEnte());
        pojoCategoria.setAlbo(pojoAlbo);
        
        session.save(pojoCategoria);
        session.flush();
        
        idCategoria = pojoCategoria.getPkcategoria();
        
        tx.commit();
      }
      else
      {
        logger.error("--> ERRORE ALBO non trovato ");
        throw new SOAPException("SOAP-ENV:Client","ALBO non trovato");        
      }
    }
    catch(HibernateException err)
    {
      tx.rollback();      
      logger.error("--> ERRORE transazione fallita " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    catch(Exception err)
    {
      tx.rollback();
      logger.error("--> ERRORE generico " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }
    
    return idCategoria;
  }
  
  /**
   * Il servizio ritorna l'elenco delle categorie in uso presso un ente.
   * E' necessario specificare l'identificativo dell'ente.
   * 
   * @param idEnte
   * @return lista di tutte le categorie.
   */
  public CategoriaBean[] cercaCategorie(BigDecimal idEnte) throws SOAPException
  {
    logger.info("-> Inizio cercaCategorie()");
    
    CategoriaBean[] arrCategoria = null;
    
    if(idEnte == null)
    {
      logger.error("--> ERRORE idEnte is NULL");      
      throw new SOAPException("SOAP-ENV:Client","idEnte IS NULL");
    }
    
    logger.info("--> parametro passato idEnte = " + idEnte);    
    
    try
    {
      StringBuffer strQuery = new StringBuffer();

      strQuery.append("SELECT new it.saga.siscotel.beans.albofornitori.CategoriaBean(");
      strQuery.append("cat.pkcategoria,cat.idEnte,cat.albo.pkalbo,cat.descategoria,cat.flgEco)");
      strQuery.append(" FROM Categorie as cat WHERE cat.idEnte = " + idEnte);
       
      Query query = session.createQuery(strQuery.toString());
      
      List lista = query.list();
      
      if(lista != null)
      {
        arrCategoria = new CategoriaBean[lista.size()];
        
        for(int i=0; i<lista.size(); i++)
        {
          arrCategoria[i] = (CategoriaBean)lista.get(i);
        }
      }
    }
    catch(Exception err)
    {
      logger.error("--> ERRORE: " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());
    }   
    
    return arrCategoria;
  }
  
  /**
   * Il servizio permette di eliminare fisicamente un albo dal database. 
   * L'eliminazione può avere luogo solo nel caso in cui non siamo presenti
   * fornitori collegati.
   * 
   * @param idCategoria
   * @return ritorna: TRUE se l'operazione è andata a buon fine, FALSE altrimenti
   */
  public Boolean eliminaCategoria(BigDecimal idCategoria) throws SOAPException
  {
    logger.info("-> Inizio metodo eliminaCategoria");
    
    Transaction tx = null;
    Boolean flag = new Boolean("false");
    
    if(idCategoria == null)
    {
      logger.error("--> ERRORE idCategoria IS NULL");
      throw new SOAPException("SOAP-ENV:Client","idCategoria IS NULL");       
    }
    
    logger.info("--> parametri passati idCategoria=" + idCategoria);
    
    try
    {
      tx = session.beginTransaction();
      
      Categorie pojoCategoria = (Categorie)session.get(Categorie.class,idCategoria);
      
      if(pojoCategoria != null)
      {
        session.delete(pojoCategoria);
        session.flush();
        
        tx.commit();
        flag = new Boolean("true");
      }
    }
    catch(HibernateException err)
    {
      tx.rollback();
      logger.error("--> ERRORE: " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());    
    }
    catch(Exception err)
    {
      tx.rollback();
      logger.error("--> ERRORE: " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());   
    }
    
    logger.info("-> Fine metodo eliminaCategoria");
    return flag;
  }

  /**
   * Il servizio implementa l'aggiornamento della descrizione della categoria in uso
   * presso un ente secondo le modalità sotto elencate.
   * 
   * E' necessario specificare l'identificativo presente in CategoriaBean
   * @param categoria
   * @return TRUE se l'operazione è andata a buon fine, FALSE altrimenti
   */
  public Boolean aggiornaCategoria(CategoriaBean categoria) throws SOAPException
  {
    logger.info("-> Inizio aggiornaCategoria()");
    
    Boolean flag = new Boolean("false");
    
    Transaction tx = null;
    
    if(categoria == null)
    {
      logger.error("--> ERRORE CategoriaBean IS NULL");
      throw new SOAPException("SOAP-ENV:Client","CategoriaBean IS NULL");      
    }
    
    if(categoria.getIdCategoria() == null)
    {
      logger.error("--> ERRORE IdCategoria IS NULL");
      throw new SOAPException("SOAP-ENV:Client","IdCategoria IS NULL");      
    }
    
    logger.info("--> " + categoria.toString());
    
    try
    {
      tx = session.beginTransaction();
    
      Categorie pojo_categoria = (Categorie)session.get(Categorie.class,categoria.getIdCategoria());
      pojo_categoria.setDescategoria(categoria.getDesCategoria());
        
      session.saveOrUpdate(pojo_categoria);  
      
      tx.commit();
      flag = new Boolean("true");
    }
    catch(HibernateException err)
    {
      tx.rollback();
      logger.error("--> ERRORE: " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());    
    }
    catch(Exception err)
    {
      tx.rollback();
      logger.error("--> ERRORE: " + err.getMessage());
      throw new SOAPException("SOAP-ENV:Client",err.getMessage());   
    }
    
    logger.info("-> Fine aggiornaCategoria()");    
    return flag;    
  }  
}
