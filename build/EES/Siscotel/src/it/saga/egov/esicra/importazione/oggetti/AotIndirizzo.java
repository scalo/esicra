package it.saga.egov.esicra.importazione.oggetti;

import it.saga.egov.esicra.EsicraConf;
import it.saga.siscotel.db.hibernate.HibernateUtil;
import java.io.*;
import java.math.*;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.hibernate.Query;
import org.jdom.*;
import it.saga.siscotel.db.*;
import java.util.*;

/**
 *  @TODO questi indirizzi hanno uno storico ????
 */
public class AotIndirizzo  extends ImportazioneFile {
    //private ViewObject otIndiceView;
    //private OtIndiceViewRowImpl otIndiceRow;
    private BigDecimal ot_pkid;
    //private ViewObject aotIndirizzoView;
    //private ViewObject areaview;
    //private CodAreaViewRowImpl areaRow;    
    private String codAot;
    private String codArea;
    
    public AotIndirizzo(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
        super(file,"ListaAotIndirizzo",new String []{"AotIndirizzo"},"AnaEstesaModule");
        versione = "1.0";
        /*
        otIndiceView = am.findViewObject("OtIndiceView");
        aotIndirizzoView = am.findViewObject("OtIndirizzoView");
        areaview = am.findViewObject("CodAreaView");
        
        if (otIndiceView==null){
           throw new ImportazioneException("Errore creazione OtIndiceView");        
        }
        if (aotIndirizzoView==null){
           throw new ImportazioneException("Errore creazione OtIndiceView");        
        }

        if (areaview==null){
           throw new ImportazioneException("Errore creazione CodAreaView");        
        }
        logger.info("ViewObject creati!!!");
        */
        
    }
    
      protected void  process(String str) throws Exception{
        Document doc = builder.build(new StringReader(str));
        Element aot = doc.getRootElement();
        String tipo = aot.getName();
        if (tipo.equalsIgnoreCase("AotIndirizzo")) {
            if (doc_letti >= doc_old) {
                processAot(aot);
                if (((doc_letti)%STEP)==0){
                    logger.debug("Documenti letti: "+doc_letti);
                    setInElaborazione(doc_letti);
                    committBlock();
                 }
            } else {
                doc_letti++;
            }
        } else{
          logger.warn(" Tipo Documento '"+tipo+"' non gestito");
        }
  }

    private void processAot(Element  ind) throws Exception{ 
        //OtIndirizzoViewRowImpl otIndirizzoRow = null;
        //String sb;
        List listaXmlInd;
        List listaOtInd ;
        doc_letti ++;
        try{
            codAot = CV.text(ind.getChild("CodAot",namespace)).toUpperCase();
            /*
            otIndiceView.setWhereClause("cod_aot = '"+codAot+"'");
            otIndiceView.executeQuery();
            otIndiceView.setWhereClause("");
            */
            OtIndice otIndice = (OtIndice) DbCommon.cercaRiga("it.saga.siscotel.db.OtIndice","cod_aot",codAot);
            if (otIndice!=null) {
               /* 
               otIndiceRow =(OtIndiceViewRowImpl) otIndiceView.next();
               ot_pkid = otIndiceRow.getPkid();
               sb =" fkid_ot = '"+ot_pkid+"' and dt_mod <> ?";
               aotIndirizzoView.setWhereClause(sb);
               aotIndirizzoView.setWhereClauseParam(0,dtmod);
               aotIndirizzoView.executeQuery();
               aotIndirizzoView.setWhereClause("");
               */ 
               //§ cerca se il record è già presente e quindi solo da aggiornare
               String q ="FROM OtIndirizzo ind  WHERE ind.otIndice = ? And ind.dtMod <> ?" ;
               Query query =session.createQuery(q);
               query.setEntity(0,otIndice);
               query.setTimestamp(1,dtmod);
               listaOtInd = query.list();
               listaXmlInd =  ind.getChildren("AotIndirizzoDati",namespace);
               Iterator iteXml = listaXmlInd.iterator();
               Iterator iteInd = listaOtInd.iterator();
               while (iteXml.hasNext()){
                   Element elem = (Element) iteXml.next();
                   OtIndirizzo otIndirizzoRow =null;
                   if (elem != null) {
                      //§ ATTENZIONE
                      //§ presuppone che gli indirizzi xml siano in ordine con quelli del db ???
                      if (iteInd.hasNext()){
                          otIndirizzoRow = (OtIndirizzo) iteInd.next();
                      } else {
                          //otIndirizzoRow = (OtIndirizzoViewRowImpl)(((OtIndirizzoViewImpl) aotIndirizzoView).createRow(new BigDecimal(STEP))); 
                          //otIndirizzoRow.setFkidOt(ot_pkid);
                          otIndirizzoRow = new OtIndirizzo();
                          otIndirizzoRow.setOtIndice(otIndice);
                      }
                      //§ inserisce area se c'è
                      /*
                      codArea = CV.text(ind.getChild("CodArea",namespace));
                       if(codArea != null) {
                          codArea = codArea.toUpperCase();
                          areaRow = (CodAreaViewRowImpl) Utilita.cercaRiga(areaview,codArea);
                          if(areaRow!=null){
                            otIndirizzoRow.setFkidArea(areaRow.getPkid());
                          }
                       }
                       */
                       otIndirizzoRow.setDesArea(CV.text(elem.getChild("DesArea",namespace)));                                               
                       otIndirizzoRow.setNumCiv(CV.intero(elem.getChild("CivicoNum",namespace)));                                               
                       otIndirizzoRow.setLetCiv(CV.text(elem.getChild("CivicoLet",namespace)));                                               
                       otIndirizzoRow.setColore(CV.text(elem.getChild("Colore",namespace)));                                               
                       otIndirizzoRow.setCorte(CV.text(elem.getChild("Corte",namespace)));                                               
                       otIndirizzoRow.setScala(CV.text(elem.getChild("Scala",namespace)));                                               
                       otIndirizzoRow.setPiano(CV.text(elem.getChild("Piano",namespace)));                                               
                       otIndirizzoRow.setEdificio(CV.text(elem.getChild("Edificio",namespace)));       
                       otIndirizzoRow.setDtMod(dtmod);
                       //am.getTransaction().postChanges();
                   }
                   otIndirizzoRow.setIdEnte(new Integer(id_ente));
                   session.saveOrUpdate(otIndirizzoRow);
                }
                
                //Utilita.annullaRecord(aotIndirizzoView,dtmod);
                //§ ANNULLA vecchi AotIndirizzi riferiti al soggetto
                q = "FROM OtIndirizzo ind  WHERE ind.otIndice = ? AND ind.dtMod = ?";
                query = session.createQuery(q);
                query.setEntity(0,otIndice);
                query.setTimestamp(1,dtmod);
                Iterator ite = query.iterate();
                while(ite.hasNext()){
                  Object obj = ite.next();
                  Date dt = (Date) HibernateUtil.getAttribute(obj,"DtMod");
                  long dt1 = dt.getTime();
                  long dt2 = dtmod.getTime();
                  if (dt1 != dt2) {
                    HibernateUtil.setAttribute(obj, "DtMod", null);
                  }
                  session.save(obj);
                }
            } else {
                logger.error("Attenzione!! cod = "+codAot+" non trovato !!!");
            }
           } catch(Exception e ){
              doc_saltati++;
              throw e;
        }
    }

 /* 
 public static void importaAotInd(AotIndirizzo aotInd){
    try{
      aotInd.parse();
    }catch(Exception e){
        String msg= e.getMessage();
        e.printStackTrace();
        logger.error(msg);
    }
 }
 */
 
  public static void main(String[] args) {
    //§ impostazioni per configurazione
    //--
    String xsltPath = "c://esicra/tomcat/webapps/siscotel/xslt";
    String xsdPath = "c://esicra/tomcat/webapps/siscotel/xsd/2.0";
    System.setProperty("esicra.xslt.dir", xsltPath);
    System.setProperty("esicra.xsd.dir", xsdPath);
    System.setProperty("esicra.id_ente", "8240");
    //--
    EsicraConf.configura();
    File file = new File(
            "c://esicra/test/siscotel/oin_A_20040317112600.XML");
    AotIndirizzo ind = null;
    try {
        ind = new AotIndirizzo(file);
        ind.parse();
    } catch (Exception e) {
        e.printStackTrace();
    }
    //System.out.println("*** FINE ****");
  }


}