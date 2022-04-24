package it.saga.egov.esicra.importazione.test;

import java.io.*;
import java.util.Date;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import org.xml.sax.InputSource;

public class Trasformazione {
    
    private static String TRANSFORMER_XALAN  = "org.apache.xalan.processor.TransformerFactoryImpl";

    public Trasformazione() {
    }

    public static void xsl(String inFilename, String outFilename, String xslFilename) {
        try {
            System.setProperty("javax.xml.transform.TransformerFactory",TRANSFORMER_XALAN);
            SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            Templates template = factory.newTemplates(new StreamSource(
                        new FileInputStream(xslFilename)));
            Transformer xformer = template.newTransformer();
            FileInputStream in=  new FileInputStream(inFilename);
            FileOutputStream out = new FileOutputStream(outFilename);
            SAXSource source = new SAXSource(new InputSource(in));
            Result result = new StreamResult(out);
            xformer.transform(source, result);
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (TransformerConfigurationException e) {
            System.out.println(e);
        } catch (TransformerException e) {
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
            System.out.println("ERRORE col=" + col + " line=" + line);
            System.out.println("publicId=" + publicId + "  systemId=" +
                systemId);
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void testMini(){
      System.out.println("*******************testMini*******************");
      System.out.println("START:"+new Date(System.currentTimeMillis()));
      String input = "c://esicra/test/siscotel/prova/SOG.XML";
      String output = "c://esicra/test/siscotel/prova/SFI.XML";
      String xslt = "c://esicra/test/siscotel/prova/sfi.xsl";
      Trasformazione.xsl(input, output, xslt);
      System.out.println("STOP:"+new Date(System.currentTimeMillis()));
    }
     
     public static void testMaxi(){
      System.out.println("*******************testMax*******************");
      System.out.println("START:"+new Date(System.currentTimeMillis()));
      String input = "c://esicra/test/siscotel/stress/SOG_T_20050411091153.XML";
      String output = "c://esicra/test/siscotel/stress/SFI_T_20050411091153.XML";
      String xslt = "c://esicra/test/siscotel/stress/sfi.xsl";
      Trasformazione.xsl(input, output, xslt);
      System.out.println("STOP:"+new Date(System.currentTimeMillis()));
    }
    
    public static void main(String[] args) throws Exception {
        testMini();
        testMaxi();
    }
}
