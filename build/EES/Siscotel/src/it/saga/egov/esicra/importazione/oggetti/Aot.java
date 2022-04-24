package it.saga.egov.esicra.importazione.oggetti;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import org.xml.sax.SAXException;
import it.saga.egov.esicra.importazione.*;
import org.jdom.*;
import it.saga.siscotel.db.*;

public class Aot  extends ImportazioneFile {
   
    private String codAot;
     
    public Aot(File file ) throws FileNotFoundException,SAXException,ImportazioneException{
        super(file,"ListaAot",new String []{"Aot"},"AnaEstesaModule");
        versione = "1.0";
    }
    
   protected void  process(String str) throws Exception {
        Document doc = builder.build(new StringReader(str));
        Element aot = doc.getRootElement();
        String tipo = aot.getName();
        if (tipo.equalsIgnoreCase("Aot")) {
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

  private void processAot(Element  aotXml) throws Exception{ 
        doc_letti ++;
        String codEcografico = "";
        String codCatasto = "";

        try{
            codAot = CV.text(aotXml.getChild("CodAot",namespace)).toUpperCase();                        
            OtIndice aotRow = (OtIndice) DbCommon.cercaRiga("it.saga.siscotel.db.OtIndice","codAot",codAot);
            if(aotRow==null){
              aotRow=new OtIndice();
              aotRow.setCodAot(codAot);
            }
            
            codEcografico = CV.text(aotXml.getChild("CodEcografico",namespace));
            if (codEcografico != null) {
                aotRow.setCodEcografico(codEcografico.toUpperCase());
            }

            codCatasto = CV.text(aotXml.getChild("CodCatasto",namespace));
            if (codCatasto != null) {
                aotRow.setCodCatasto(codCatasto.toUpperCase());
            }
            
            aotRow.setCodIstatComune(CV.text(aotXml.getChild("CodIstat",namespace)));
            aotRow.setDtMod(dtmod);
            aotRow.setIdEnte(new Integer(id_ente));
            session.save(aotRow);
        } catch(Exception e ){
                      doc_saltati++;
                      throw e;
        }
    }

  /*
    public static void importaAot(Aot aot){
      try{
        aot.parse();
      }catch(Exception e){
          String msg= e.getMessage();
          e.printStackTrace();
          logger.error(msg);
      }
    }
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
    File file = new File("c://esicra/test/siscotel/aot_A_20040317112600.XML");
    Aot sogg = null;
    try{
      Aot aot = new Aot(file);
      aot.parse();
    }catch(Exception e){
      e.printStackTrace();
    }
    //System.out.println("*** FINE ****");
  }


}