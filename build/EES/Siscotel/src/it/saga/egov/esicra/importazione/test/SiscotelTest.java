package it.saga.egov.esicra.importazione.test;

import it.saga.egov.esicra.EsicraConf;
import it.saga.egov.esicra.importazione.soggetto.*;
import it.saga.egov.esicra.importazione.oggetti.*;
import java.io.File;

public class SiscotelTest  {
   
   public static void test(){

     try{
     
        SoggettoFisico testSF = new SoggettoFisico(new File("c://esicra/test/siscotel/SFI_A_20040317112600.XML"));
        testSF.parse();
        SoggettoGiuridico testSG = new SoggettoGiuridico(new File("c://esicra/test/siscotel/SGI_A_20040317112600.XML"));
        testSG.parse();
        Indirizzo testInd = new Indirizzo(new File("c://esicra/test/siscotel/IND_A_20040317112600.XML"));
        testInd.parse();
        Aot testAot = new Aot(new File("c://esicra/test/siscotel/aot_A_20040317112600.XML"));
        testAot.parse();
        AotIdentificativo testAotId = new AotIdentificativo(new File("c://esicra/test/siscotel/oic_A_20040317112600.XML"));
        testAotId.parse();
        AotIndirizzo testAotInd = new AotIndirizzo(new File("c://esicra/test/siscotel/oin_A_20040317112600.XML"));
        testAotInd.parse();
        AllineaProvenienza  testProv = new AllineaProvenienza(new File("c://esicra/test/siscotel/ASP_A_20040317112600.XML"));
        testProv.parse(); 
        
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
    System.setProperty("id_ente","8240");
    //--
    EsicraConf.configura();
    test();
    System.out.println("*** FINE ****");
  }
  
}
