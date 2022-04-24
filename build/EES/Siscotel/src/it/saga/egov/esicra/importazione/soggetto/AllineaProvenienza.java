package it.saga.egov.esicra.importazione.soggetto;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import java.util.*;
import org.hibernate.Query;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;

/**
 *    TODO vedere come funziona l'algoritmo con marco
 *    
 */
public class AllineaProvenienza extends ImportazioneFile {

  private List soggetti;  
  private String codSoggetto;
  /**  ??  */
  //private Vector ServiziFile = new Vector();    

      
  public AllineaProvenienza(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
      super(file,"ListaAnaProvenienza", new String[] {"AnaProvenienza"},"SoggettoModule");
      versione = "1.0";
      //§ pulisce cache provenienza
      DbCommon.serProvenienzaCache.clear();
  }
  
  public void process(String strDoc) throws Exception {
    Document doc = builder.build(new StringReader(strDoc));
    Element provenienza = doc.getRootElement();
    String tipo = provenienza.getName();
    if(tipo.equals("AnaProvenienza")){
      if (doc_letti >= doc_old) {
          processProvenienza(provenienza);
          if (((doc_letti)%STEP)==0){
              logger.debug("Documenti letti: "+doc_letti);
              setInElaborazione(doc_letti);
              committBlock();
           }
        } else {
           doc_letti++;
        }
    }else{
       logger.warn(" Tipo Documento '"+tipo+"' non gestito");
    }
  }

  private void processProvenienza(Element provenienza) throws Exception {
    //BigDecimal pkidProvenienza=null;
    try{
        String codProvenienza = CV.text(provenienza.getChild("CodProvenienza",namespace));
        SerProvenienza prov=null;
        if (codProvenienza != null) {
            codProvenienza = codProvenienza.toUpperCase();
            //pkidProvenienza = Utilita.getPkidProvenienza(codProvenienza);
            // cerca provenienza
            prov = (SerProvenienza) DbCommon.serProvenienzaCache.get(codProvenienza);
            if (prov==null){
              prov = (SerProvenienza) DbCommon.cercaRiga("it.saga.siscotel.db.SerProvenienza","codProvenienza",codProvenienza);
              DbCommon.serProvenienzaCache.put(codProvenienza,prov);
            }
            if (prov != null){
                soggetti = provenienza.getChildren("CodSoggetto",namespace);
                Iterator iterator = soggetti.iterator();
                while (iterator.hasNext()){
                    doc_letti++;
                    Element bb = (Element) iterator.next();
                    codSoggetto = bb.getText().toUpperCase();
                    AnaSoggetto sogg = (AnaSoggetto) DbCommon.cercaRiga("it.saga.siscotel.db.AnaSoggetto","codSoggetto",codSoggetto);
                    if (sogg == null) {
                        logger.warn("Soggetto: "+codSoggetto+" non trovato....");
                    }else {
                      // aggiorna provenienza sogg
                      DbCommon.aggiornaProvenienzaSogg(sogg,codProvenienza.toString(),getDtMod(),id_ente);
                      puliziaProvenienza();
                    }
                    session.save(sogg);
                }   
                //am.getTransaction().commit();
            } else {
              logger.error("ATTENZIONE!!! Provenienza non codificata: "+codProvenienza);
            }
        } else {
            logger.error("ATTENZIONE!!! Provenienza non specificata");
        }
    } catch(Exception e){
          logger.error(e);
          doc_saltati++;
          throw e;
    }
  }

public void puliziaProvenienza(){
    String update ="update AnaSoggettoProv set dtMod = null where dtMod <> :curDt";
    Query query = session.createQuery(update);
    query.setTimestamp("curDt",getDtMod());
    int n = query.executeUpdate();
}

/**
 *  ?? boh ??  
 */
public void statistica () {
    super.statistica();
    /*
    AnaSoggettoProvViewRowImpl soggRow;
    Enumeration serv = ServiziFile.elements();
    String codProvenienza;
    String sb;
    while (serv.hasMoreElements()){
        codProvenienza = serv.nextElement().toString();
        logger.info("Attendere... Aggiornamento provenienza - cod: "+codProvenienza);
        sb = " fkid_provenienza = '"+codProvenienza+"' and dt_mod <> ?";
        soggProvenienza.setWhereClause(sb);
        soggProvenienza.setWhereClauseParam(0, dtmod);
        soggProvenienza.executeQuery();
        while(soggProvenienza.hasNext()){
            soggRow = (AnaSoggettoProvViewRowImpl) soggProvenienza.next();
            soggRow.setDtMod(null);
        }
        //am.getTransaction().commit();
    }
    */
 }
 
 public static void test(){
   try{
      AllineaProvenienza test = new AllineaProvenienza(new File("c://esicra/test/siscotel/ASP_A_20040317112600.XML"));
      test.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
 }
 
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
    test();
    //System.out.println("*** FINE ****");
  }

}

