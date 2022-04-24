package it.saga.egov.esicra.importazione.oggetti;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import java.math.*;
import org.hibernate.Query;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;
import java.util.*;

public class AotProvenienza  extends ImportazioneFile {
    private Vector ServiziFile = new Vector();  
    private Hashtable serProvenienzaCache ;
    
    public AotProvenienza(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
        super(file,"ListaAotProvenienza",new String []{"AotProvenienza"},"AnaEstesaModule");
        versione = "1.0";
        /*
        otIndiceView = am.findViewObject("OtIndiceView");
        aotProvenienzaView = am.findViewObject("OtProvenienzaView");
         
        if (otIndiceView==null){
           throw new ImportazioneException("Errore creazione OtIndiceView");        
        }

        if (aotProvenienzaView==null){
           throw new ImportazioneException("Errore creazione OtIndiceView");        
        }

        logger.info("ViewObject creati!!!");
        */
        serProvenienzaCache = new Hashtable();
    }
    
    protected void  process(String str) throws Exception {
    
        Document doc = builder.build(new StringReader(str));
        Element aotProv = doc.getRootElement();
        String tipo = aotProv.getName();
        if (tipo.equalsIgnoreCase("AotProvenienza")) {
            if (doc_letti >= doc_old) {
                processAotProvenienza(aotProv);
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

  private void processAotProvenienza(Element  provenienza) throws Exception{ 
    List oggetti;
    String codAot;
    BigDecimal db_pkid;
    //OtIndiceViewRowImpl otIndiceRow;
    //String sb ;
    BigDecimal pkidProvenienza=new BigDecimal("0");
    try{
        doc_letti++;
        String codProvenienza = CV.text(provenienza.getChild("CodProvenienza",namespace));
        if (codProvenienza != null){
            codProvenienza = codProvenienza.toUpperCase();
            //§ cerca provenienza
            SerProvenienza otProv =null;
            otProv = (SerProvenienza) serProvenienzaCache.get(codProvenienza);
            //SerProvenienza otProv = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","cod_provenienza",codProvenienza);
            //pkidProvenienza = Utilita.getPkidProvenienza(codProvenienza);
            if(otProv==null){
              otProv = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","cod_provenienza",codProvenienza);
              serProvenienzaCache.put(codProvenienza,otProv);
            }    
                //otProv != null           
                //§ ????
                /*
                if (ServiziFile.indexOf(pkidProvenienza)==-1) {
                    ServiziFile.addElement(pkidProvenienza);
                }
                */
                oggetti = provenienza.getChildren("CodAot",namespace);

                Iterator iterator = oggetti.iterator();
                while (iterator.hasNext()){
                    db_pkid = null;
                    Element bb = (Element) iterator.next();
                    codAot = bb.getText().toUpperCase();
                    /*
                    otIndiceView.setWhereClause("cod_aot = '"+codAot+"'");
                    otIndiceView.executeQuery();
                    otIndiceView.setWhereClause("");
                    if(otIndiceView.hasNext()) {
                        otIndiceRow = (OtIndiceViewRowImpl)otIndiceView.next();
                        db_pkid = otIndiceRow.getPkid();
                    }
                    */
                    OtIndice otIndice = (OtIndice) DbCommon.cercaRiga("it.saga.siscotel.db.OtIndice","cod_aot",codAot);
                    if (otIndice == null) {
                        logger.warn("Oggetto: "+codAot+" non trovato....");
                    } else {
                        /*
                        sqlStr = "fkid_ot = '"+db_pkid+"' and fkid_provenienza = '"+pkidProvenienza+"'";
                        aotProvenienzaView.setWhereClause(sqlStr);
                        aotProvenienzaView.executeQuery();
                        aotProvenienzaView.setWhereClause("");
                        */
                        //§  aggiunto nor null
                         
                         String q ="FROM OtProvenienza ind  WHERE ind.otIndice = ? AND ind.serProvenienza=? AND ind.dtMod is not null" ;
                         Query query =session.createQuery(q);
                         query.setEntity(0,otIndice);
                         query.setEntity(1,otProv);
                         Iterator ite = query.iterate();
                         OtProvenienza otProvenienzaRow =null;
                         if (ite.hasNext()) {
                            // relazione già presente , aggiorna solo dtmod
                            otProvenienzaRow = (OtProvenienza)ite.next();
                            otProvenienzaRow.setDtMod(dtmod);
                         } else {
                            // inserisce nuova relazione
                            //otProvenienzaRow = (OtProvenienzaViewRowImpl) (((OtProvenienzaViewImpl) aotProvenienzaView).createRow(new BigDecimal(STEP)));
                            otProvenienzaRow = new OtProvenienza();
                            otProvenienzaRow.setDtMod(dtmod);
                            otProvenienzaRow.setIdEnte(new Integer(id_ente));
                            otProvenienzaRow.setSerProvenienza(otProv);
                            otProvenienzaRow.setOtIndice(otIndice);
                            //otProvenienzaRow.setFkidOt(db_pkid);
                            //otProvenienzaRow.setFkidProvenienza(pkidProvenienza);
                          }
                          session.saveOrUpdate(otProvenienzaRow);
                          session.flush();
                          session.evict(otIndice);
                    }
                    
                }
                //am.getTransaction().commit();
        }else{
                logger.error("ATTENZIONE !!! Codice Provenienza: "+codProvenienza+" non codificato !");
         }
    } catch(Exception e){
          logger.error(e);
          doc_saltati++;
          throw e;
    }
  }

 public static void importaAotProvenienza(AotProvenienza aotProv){
    try{
      aotProv.parse();
    }catch(Exception e){
        String msg= e.getMessage();
        e.printStackTrace();
        logger.error(msg);
    }
 }

/**
    ????????
    ottimizzazione ???
**/
public void statistica () {
  
  super.statistica();

  Enumeration serv = ServiziFile.elements();
  String codProvenienza;
  String sb;
  while (serv.hasMoreElements()) {
    /*
      codProvenienza = serv.nextElement().toString();
        sb = " fkid_provenienza = '"+codProvenienza+"' and dt_mod <> ?";
        aotProvenienzaView.setWhereClause(sb);
        aotProvenienzaView.setWhereClauseParam(0, dtmod);
        aotProvenienzaView.executeQuery();
        while(aotProvenienzaView.hasNext()){
              otProvenienzaRow = (OtProvenienzaViewRowImpl) aotProvenienzaView.next();
              otProvenienzaRow.setDtMod(null);
        }
      am.getTransaction().commit();
    */
  }
 }

 /**
  *   metodo di test
  */
  public static void main(String[] args){
    //§ impostazioni per configurazione
    //--
    String xsltPath = "c://esicra/tomcat/webapps/siscotel/xslt";
    String xsdPath =  "c://esicra/tomcat/webapps/siscotel/xsd/2.0";
    System.setProperty("esicra.xslt.dir",xsltPath);
    System.setProperty("esicra.xsd.dir",xsdPath);
    System.setProperty("esicra.id_ente","8240");
    //--
    EsicraConf.configura();
    File file = new File("c://esicra/test/siscotel/aop_A_20040317112600.XML");
    AotProvenienza test = null;
    try{
      test = new AotProvenienza(file);
      test.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
    //System.out.println("*** FINE ****");
  }
  
}
