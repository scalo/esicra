package it.saga.egov.esicra.importazione;

import it.saga.egov.esicra.EsicraConf;
import java.io.*;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 *    Classe per la validazione xml con Xerces
 */
public class JAXPValidator {

    private static Logger logger = null;
    
    public boolean validateSchema(String SchemaUrl, String XmlDocumentUrl) {
      boolean res=false;
      try{
        FileInputStream fis=  new FileInputStream(XmlDocumentUrl);
        res = validateSchema(SchemaUrl, fis);
      }catch(FileNotFoundException fe){
        logger.error(fe);
      }
      return res;
    }
    
    public boolean validateSchema(String SchemaUrl, InputStream XmlDocument) {
        boolean result = false;
        logger = EsicraConf.configuraLogger(this.getClass());
        try {
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(true);
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
                SchemaUrl);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Validator handler = new Validator();
            builder.setErrorHandler(handler);
            builder.parse(XmlDocument);
            if (handler.validationError == true) {
                logger.error("Errore validazione documento XML:" +
                    handler.validationError + " " +
                    handler.saxParseException.getMessage());
            } else {
                //log troppo prolisso
                //logger.debug ("Documento XML Valido");
                result=true;
            }
        } catch (java.io.IOException ioe) {
            logger.error("IOException " + ioe.getMessage());
        } catch (SAXException e) {
            logger.error("SAXException" + e.getMessage());
        } catch (ParserConfigurationException e) {
            logger.error(
                "ParserConfigurationException  " +
                e.getMessage());
        }
        return result;
    }

    private class Validator extends DefaultHandler {
        public boolean validationError = false;
        public SAXParseException saxParseException = null;

        public void error(SAXParseException exception)
            throws SAXException {
            validationError = true;
            saxParseException = exception;
        }

        public void fatalError(SAXParseException exception)
            throws SAXException {
            validationError = true;
            saxParseException = exception;
        }

        public void warning(SAXParseException exception)
            throws SAXException {
        }
    }
    
    public static void main(String[] argv) {
        System.setProperty("esicra.conf","c:/esicra/conf");
        EsicraConf.configura();
        String SchemaUrl = "C:/test/ListaStatoCivile.xsd";
        String XmlDocumentUrl = "C:/test/A.XML";
        JAXPValidator validator = new JAXPValidator();
        boolean res = validator.validateSchema(SchemaUrl, XmlDocumentUrl);
        System.out.println("Documento valido ? "+res);
    }
}
