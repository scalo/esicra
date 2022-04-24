package it.saga.egov.esicra.importazione.oggetti;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;
import org.hibernate.Query;
import java.util.*;

public class AotIdentificativo  extends ImportazioneFile {

    private String codAot;
    private Element aotIdEle;
    
    public AotIdentificativo(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
        super(file,"ListaAotIdentificativo",new String []{"AotIdentificativo"},"AnaEstesaModule");
        versione = "1.0";
        /*
        otIndiceView = am.findViewObject("OtIndiceView");
        otIdentificativoView = am.findViewObject("OtIdentificativoView");
        if (otIndiceView==null){
           throw new ImportazioneException("Errore creazione OtIndiceView");        
        }
        if (otIdentificativoView==null){
           throw new ImportazioneException("Errore creazione OtIdentificativoView");        
        }
        */
    }
    
    protected void  process(String str) throws Exception{
        Document doc = builder.build(new StringReader(str));
        Element otIdentificativo = doc.getRootElement();
        String tipo = otIdentificativo.getName();
        if (tipo.equalsIgnoreCase("AotIdentificativo")) {
            if (doc_letti >= doc_old) {
                processAotIdentificativo(otIdentificativo);
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

    private void processAotIdentificativo(Element  otIdentificativo) throws Exception{ 
        //OtIdentificativoViewRowImpl otIdentificativoRow;
        //OtIdentificativoViewRowImpl otIdentificativoRowTemp;
        doc_letti ++;
        //String sb;
        List listaIde;
        
        try{
            codAot = CV.text(otIdentificativo.getChild("CodAot",namespace)).toUpperCase();
            /*
            sb = "cod_aot = '"+codAot+"' and dt_mod <> ?";
            otIndiceView.setWhereClause(sb);
            otIndiceView.setWhereClauseParam(0, dtmod);
            otIndiceView.executeQuery();
            otIndiceView.setWhereClause("");
            */
            String q ="FROM OtIndice ind  WHERE ind.codAot = ? AND ind.dtMod <> ?" ;
             Query query =session.createQuery(q);
             query.setString(0,codAot);
             query.setTimestamp(1,dtmod);
             Iterator ite = query.iterate();
             OtIndice otIndiceRow ;
             if (ite.hasNext()) {
                otIndiceRow =(OtIndice) ite.next();
                /*
                ot_pkid = otIndiceRow.getPkid();
                sb = " fkid_ot = '"+ot_pkid+"' and dt_mod <> ?";
                otIdentificativoView.setWhereClause(sb);
                otIdentificativoView.setWhereClauseParam(0, dtmod);
                otIdentificativoView.executeQuery();
                otIdentificativoView.setWhereClause("");
                */
               q="FROM OtIdentificativo ide  WHERE  ide.otIndice= ? AND ide.dtMod <> ?" ;
               query =session.createQuery(q);
               query.setEntity(0,otIndiceRow);
               query.setTimestamp(1,dtmod);
               Iterator iteIde = query.iterate();
               listaIde = otIdentificativo.getChildren("AotIdentificativoDati",namespace);
               if (listaIde != null) {
                    Iterator itListaIde = listaIde.iterator();
                    OtIdentificativo otIdentificativoRow ;
                    while (itListaIde.hasNext()){
                        aotIdEle = (Element) itListaIde.next();
                        if (iteIde.hasNext()) {
                            otIdentificativoRow = (OtIdentificativo) iteIde.next();
                        } else {
                            otIdentificativoRow = new OtIdentificativo(); 
                            //otIdentificativoRow.setFkidOt(ot_pkid);
                            otIdentificativoRow.setOtIndice(otIndiceRow);
                        }
                        otIdentificativoRow.setDtMod(dtmod);
                        otIdentificativoRow.setIdEnte(new Integer(id_ente));
                        otIdentificativoRow.setTipoCatasto(CV.text(aotIdEle.getChild("TipoCatasto",namespace)));    
                        otIdentificativoRow.setDesIdentificativo(CV.text(aotIdEle.getChild("Tipo",namespace)));    
                        otIdentificativoRow.setSezione(CV.text(aotIdEle.getChild("Sezione",namespace)));    
                        otIdentificativoRow.setFoglio(CV.text(aotIdEle.getChild("Foglio",namespace)));    
                        otIdentificativoRow.setMappale(CV.text(aotIdEle.getChild("Mappale",namespace)));    
                        otIdentificativoRow.setSub(CV.text(aotIdEle.getChild("Sub",namespace)));    
                        otIdentificativoRow.setProtAnno(CV.intero(aotIdEle.getChild("Anno",namespace)));    
                        otIdentificativoRow.setProtNum(CV.text(aotIdEle.getChild("Protocollo",namespace)));    
                        session.save(otIdentificativoRow);
                    }
                }
                //am.getTransaction().postChanges();
              
                //§ ANNULLA RECORDS
                //Utilita.annullaRecord(otIdentificativoView, dtmod);
            } else {
                logger.error("Attenzione!! cod = "+codAot+" non trovato !!!");
            }

        } catch(Exception e ){
                      doc_saltati++;
                      throw e;
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
    File file = new File("c://esicra/test/siscotel/oic_A_20040317112600.XML");
    AotIdentificativo test = null;
    try{
      test = new AotIdentificativo(file);
      test.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
    //System.out.println("*** FINE ****");
  }
  



}