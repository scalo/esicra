package it.saga.egov.esicra.importazione.soggetto;

import it.saga.egov.esicra.EsicraConf;
import it.saga.egov.esicra.importazione.*;
import it.saga.siscotel.db.*;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import java.sql.Timestamp;
import java.util.Iterator;
import org.hibernate.Query;
import org.jdom.*;
import org.xml.sax.SAXException;
import java.io.*;

/**
  Inserisce AnaIndirizzo
**/
public class Indirizzo extends ImportazioneFile {
    
    private TerComune comuneImport;

    public Indirizzo(File file)
        throws FileNotFoundException, SAXException, ImportazioneException {
        super(file, "ListaIndirizzo", new String[] { "Indirizzo" },
            "SoggettoModule");
        versione = "1.0";
        if (System.getProperty("esicra.id_istat") != null) {
            String cod = System.getProperty("esicra.id_istat");
            //§ carica il comune
            comuneImport = (TerComune) DbCommon.cercaRiga("it.saga.siscotel.db.TerComune",
                    "cod_comune", cod);
        } else {
            comuneImport = null;
            logger.warn("CODICE ISTAT COMUNE NON VALORIZZATO !!!!!!!");
        }
        
    }

    protected void process(String str) throws Exception {
        Document doc = builder.build(new StringReader(str));
        Element indirizzo = doc.getRootElement();
        String tipo = indirizzo.getName();
        if (tipo.equalsIgnoreCase("Indirizzo")) {
            if (doc_letti >= doc_old) {
                processIndirizzo(indirizzo);
                if (((doc_letti) % STEP) == 0) {
                    logger.debug("Documenti letti: " + doc_letti);
                    setInElaborazione(doc_letti);
                    committBlock();
                }
            } else {
                doc_letti++;
            }
        } else {
            logger.warn(" Tipo Documento '" + tipo + "' non gestito");
        }
    }

    private void processIndirizzo(Element indirizzoXml)
        throws Exception {
        try {
            String codSoggetto = CV.text(indirizzoXml.getChild("CodSoggetto",
                        namespace));
            if (codSoggetto != null) {
                codSoggetto = codSoggetto.toUpperCase();
            }
            //§ cosa é il tipo ?
            String tipo = CV.text(indirizzoXml.getChild("Tipo", namespace));
            doc_letti++;
            AnaSoggetto sogg = (AnaSoggetto) DbCommon.cercaRigaCorrente("it.saga.siscotel.db.AnaSoggetto",
                    "cod_soggetto", codSoggetto);
            if (sogg != null) {
                //BigDecimal pkid = sogg.getPkid();
                // annulla vecchi indirizzi
                /*
                indirizzoview.setWhereClauseParam(0,fkidSoggetto);
                indirizzoview.setWhereClauseParam(1,CV.text(indirizzoXml.getChild("Tipo",namespace)));
                indirizzoview.executeQuery();
                indirizzoview.setWhereClause("");
                Utilita.annullaRecord(indirizzoview,dtmod);
                */
                //DbCommon.annullaRecordStorico("it.saga.siscotel.db.AnaIndirizzo","pkid", pkid, dtmod);
                
                String q = "FROM AnaIndirizzo sog  WHERE sog.anaSoggetto = ? ";
                Query query = session.createQuery(q);
                query.setEntity(0,sogg);
                Iterator ite = query.iterate();
                while(ite.hasNext()){
                  Object obj = ite.next();
                  Timestamp dt = (Timestamp) HibernateUtil.getAttribute(obj,"DtMod");
                  if(dt != null){
                    long dt1 = dt.getTime();
                    long dt2 = dtmod.getTime();
                    if (dt1 != dt2) {
                        HibernateUtil.setAttribute(obj, "DtMod", null);
                    }
                  }
                  
                 }
                //§ nuovo indirizzo
                //AggIndirizzoViewRowImpl indirizzo = (AggIndirizzoViewRowImpl) (((AggIndirizzoViewImpl)indirizzoview).createRow(new BigDecimal(STEP)));
                AnaIndirizzo indirizzo = new AnaIndirizzo();
                //indirizzo.setFkidSoggetto(fkidSoggetto);
                indirizzo.setAnaSoggetto(sogg);
                indirizzo.setTipo(CV.text(indirizzoXml.getChild("Tipo",
                            namespace)));
                indirizzo.setNumCiv(CV.intero(indirizzoXml.getChild("NumCiv",
                            namespace)));
                indirizzo.setLetCiv(CV.text(indirizzoXml.getChild("LetCiv",
                            namespace)));
                indirizzo.setCorte(CV.text(indirizzoXml.getChild("Corte",
                            namespace)));
                indirizzo.setScala(CV.text(indirizzoXml.getChild("Scala",
                            namespace)));
                indirizzo.setInterno(CV.text(indirizzoXml.getChild("Interno",
                            namespace)));
                indirizzo.setPiano(CV.text(indirizzoXml.getChild("Piano",
                            namespace)));
                indirizzo.setEdificio(CV.text(indirizzoXml.getChild(
                            "Edificio", namespace)));
                indirizzo.setContea(CV.text(indirizzoXml.getChild("Contea",
                            namespace)));
                indirizzo.setPresso(CV.text(indirizzoXml.getChild("Presso",
                            namespace)));
                indirizzo.setTelefono(CV.text(indirizzoXml.getChild(
                            "Telefono", namespace)));
                indirizzo.setFax(CV.text(indirizzoXml.getChild("Fax", namespace)));
                indirizzo.setEmail(CV.text(indirizzoXml.getChild("EMail",
                            namespace)));
                indirizzo.setDtIni(CV.data(indirizzoXml.getChild("DataInizio",
                            namespace)));
                indirizzo.setDtFin(CV.data(indirizzoXml.getChild("DataFine",
                            namespace)));
                indirizzo.setDtMod(dtmod);
                String codArea = CV.text(indirizzoXml.getChild("CodArea",
                            namespace));
                if (codArea != null) {
                    codArea = codArea.toUpperCase();
                    //CodAreaViewRowImpl areaRow = (CodAreaViewRowImpl) Utilita.cercaRiga(areaview,codArea);
                    TerArea area = (TerArea) DbCommon.cercaRiga("it.saga.siscotel.db.TerArea",
                            "cod_area", codArea);
                    if (area != null) {
                        //indirizzo.setFkidArea(areaRow.getPkid());
                        indirizzo.setTerArea(area);
                    }
                }
                indirizzo.setDesArea(CV.text(indirizzoXml.getChild("DesArea",
                            namespace)));
                Element comuneXml = indirizzoXml.getChild("Comune", namespace);
                Element localitaXml = indirizzoXml.getChild("Localita",
                        namespace);
                if (comuneXml != null) {
                    indirizzo.setCap(CV.text(comuneXml.getChild("Cap", namespace)));
                    String codComune = CV.text(comuneXml.getChild("CodComune",
                                namespace));
                    if (codComune != null) {
                        if ((comuneImport != null) &&
                                codComune.equals(comuneImport.getCodComune()
                                                                 .toString())) {
                            //§ comune principale
                            //indirizzo.setFkidComune(comuneimport);
                            indirizzo.setTerComune(comuneImport);
                        } else {
                            // altro comune
                            //CodComuneViewRowImpl row = (CodComuneViewRowImpl) Utilita.cercaRiga(comuneview,codComune);
                            TerComune comune = (TerComune) DbCommon.cercaRiga("it.saga.siscotel.db.TerComune",
                                    "cod_comune", codComune);
                            if (comune != null) {
                                //indirizzo.setFkidComune(row.getPkid());
                                indirizzo.setTerComune(comune);
                            }
                        }
                    }
                    indirizzo.setDesComune(CV.text(comuneXml.getChild(
                                "DesComune", namespace)));
                    indirizzo.setDesProvincia(CV.text(comuneXml.getChild(
                                "DesProvincia", namespace)));
                    indirizzo.setContea(null);
                    indirizzo.setDesConsolato(null);
                    indirizzo.setDesLocalita(null);
                    indirizzo.setDesStato(null);
                    //§ non erano impostati !!!

                    /*
                    indirizzo.setFkidConsolato(null);
                    indirizzo.setFkidLocalita(null);
                    indirizzo.setFkidStato(null);
                    */
                } else if (localitaXml != null) {
                    // localita
                    String codLocalita = CV.text(localitaXml.getChild(
                                "CodLocalita", namespace));
                    if (codLocalita != null) {
                        //CodLocalitaViewRowImpl row = (CodLocalitaViewRowImpl) Utilita.cercaRiga(localitaview,codLocalita);
                        TerLocalita localita = (TerLocalita) DbCommon.cercaRiga("it.saga.siscotel.db.TerLocalita",
                                "cod_localita", codLocalita);
                        if (localita != null) {
                            //indirizzo.setFkidLocalita(row.getPkid());  
                            indirizzo.setTerLocalita(localita);
                        }
                    }
                    indirizzo.setDesLocalita(CV.text(localitaXml.getChild(
                                "DesLocalita", namespace)));
                    indirizzo.setDesStato(CV.text(localitaXml.getChild(
                                "DesStato", namespace)));
                    indirizzo.setContea(CV.text(localitaXml.getChild("Contea",
                                namespace)));
                    indirizzo.setCap(CV.text(localitaXml.getChild("Cap",
                                namespace)));
                    String codStato = CV.text(localitaXml.getChild("CodStato",
                                namespace));
                    if (codStato != null) {
                        //CodStatoViewRowImpl row = (CodStatoViewRowImpl) Utilita.cercaRiga(statoview,codStato);
                        TerStato stato = (TerStato) DbCommon.cercaRiga("it.saga.siscotel.db.TerStato",
                                "cod_stato", codStato);
                        if (stato != null) {
                            //indirizzo.setFkidStato(row.getPkid());
                            indirizzo.setTerStato(stato);
                        }
                    }
                    /*
                    indirizzo.setFkidComune(null);
                    indirizzo.setDesComune(null);
                    indirizzo.setDesProvincia(null);
                    */
                } else {
                    logger.warn("Comune o Localita mancanti");
                }

                //am.getTransaction().postChanges();
                // campo standard
                indirizzo.setIdEnte(new Integer(id_ente));
                indirizzo.setDtMod(getDtMod());
                session.save(indirizzo);
            } else {
                logger.error("Codice Soggetto non trovato:" + codSoggetto +
                    " Progressivo: " + progressivo);
                doc_saltati++;
            }
        } catch (Exception e) {
            doc_saltati++;
            System.out.println(e);
            throw e;
        }
    }

    public static void main(String[] args) {
        //§ impostazioni per configurazione
        //--
        String xsltPath = "c://esicra/tomcat/webapps/siscotel/xslt";
        String xsdPath = "c://esicra/tomcat/webapps/siscotel/xsd/2.0";
        System.setProperty("esicra.xslt.dir", xsltPath);
        System.setProperty("esicra.xsd.dir", xsdPath);
        System.setProperty("id_ente", "8240");
        //--
        EsicraConf.configura();
        File file = new File(
                "c://esicra/test/siscotel/IND_A_20040317112600.XML");
        Indirizzo ind = null;
        try {
            ind = new Indirizzo(file);
            ind.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("*** FINE ****");
    }
}
