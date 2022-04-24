package it.saga.egov.esicra.importazione;

import java.io.File;
import org.xml.sax.SAXException; 
import java.io.IOException;

/**
 *      Classe Generica per validare un flusso xml di importazione, basata su
 *      ImportParser
 *      
 */
public class Validatore extends ImportParser {

    private String documento="";
    private int numSel=0;
    
    /**
     *      @param file nome del file da validare
     *      @param nomeLista nome dell'elemento che definisce lista dei sottodocumenti
     *      @param nomeElementi array degli elementi possibili della lista.
     *  
     */
    public Validatore(File file , String nomeLista, String[] nomeElementi) throws Exception{
        super(file);
        for(int i=0;i<nomeElementi.length;i++){
            elementi.add(nomeElementi[i]);   
        }
        listaElementi=nomeLista;
        // imposta il numero di versione minimo
        versione = "2.0";
        // cmq imposta la validazione a true
        validazione=true;
    }

    protected void process(String doc) throws Exception{
        System.out.println(doc+"\n");
    }

    public void statistica(){
        System.out.println("--------------------------------------------------");
        System.out.println("Elaborati:"+elaborati());
        System.out.println("Rigettati:"+rigettati());
        System.out.println("Non validi:"+getNonValidi());
    }

    /**
     *  override  per evitare il commitBlock() su db
     **/
    public void endDocument() throws SAXException {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Errore nella chiusura del file " +
                e.getMessage());
        }
    }
    /**
     *     Aggiunto codice per la ricerca di un particolare progressivo
     *     memorizzato nella variabile documento
     */
    public void endElement(String namespaceURI, String localName, String rawName)
        throws SAXException {
        if (localName.equals(listaElementi)) {
            //nop
        } else if (elementi.contains(localName)) {
            doc.append("</" + localName + ">\n");
            // gestisce il documento
            //System.out.println("progr:"+progressivo);
            //controllo
            if(progressivo==numSel){
                documento=doc.toString();
                return;
            }
        } else {
            //chiudi elemento principale documento
            doc.append("</" + localName + ">\n");
        }
    }

    public String cerca(int num) throws Exception{
        numSel=num;
        parse();
        return documento;
    }

    public String errore(){
        return ultimoErrore;
    }
    
    public static void main(String[] args) throws Exception {
        System.setProperty("esicra.conf","c:/esicra/conf");
        System.setProperty("esicra.test.dir","c:/esicra/import");
        String testDirName = System.getProperty("esicra.test.dir");
        File testDir = new File(testDirName);
        System.out.println("testDirName: " + testDir.getAbsolutePath());
        System.setProperty("esicra.xsd.dir","c:/esicra/tomcat/webapps/siscotel/xsd/2.0");
        String schemaDirName = System.getProperty("esicra.xsd.dir");
        File schemaDir = new File(schemaDirName);
        System.out.println("schedaDirName: " + schemaDir.getAbsolutePath());
        File testFile = new File(testDir, "LISTASOG_TEST.XML");
        System.out.println("file: " + testFile.getAbsolutePath());
        Validatore validatore = new Validatore(testFile,"ListaSoggetto",
            new String[]{"Soggetto"});
        if (testDir == null) {
            System.out.println("testDir non trovata");
            return;
        }
        if (testFile == null) {
            System.out.println("testFile non trovato");
            return;
        }
        System.out.println("Elementi:"+validatore.elementi);
        System.out.println("Lista Elementi:"+validatore.listaElementi);
        //validatore.parse();
        int n=3;
        String str = validatore.cerca(n);
        System.out.println("----------------------Elemento "+n+"------------------");
        System.out.println(str);
        validatore.statistica();
    }
        
}
