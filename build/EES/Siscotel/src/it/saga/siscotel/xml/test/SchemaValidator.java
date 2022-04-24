package it.saga.siscotel.xml.test;

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.FileInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SchemaValidator {

    private String xmlFile = "";
    private String xsdFile = "";

    public SchemaValidator(String xmlFile, String xsdFile) {
        this.xmlFile = xmlFile;
        this.xsdFile = xsdFile;
    }

    public boolean saxSchemaValidator() {
        boolean status = false;

        SchemaValidator testXml = new SchemaValidator(this.xmlFile, this.xsdFile);

        try {
            status = testXml.process();
        } catch (SAXParseException e) {
            System.err.println("Validation failed\n" + e.getMessage() +
                "\nline " + e.getLineNumber() + ", column " +
                e.getColumnNumber() + "\n");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.out.println(status);
        }

        return status;
    }

    private boolean process() throws SAXParseException, SAXException, Exception {
        boolean status = true;
        final String factoryImpl = System.getProperty(
                "javax.xml.parsers.SAXParserFactory");

        if (factoryImpl == null) {
            System.setProperty("javax.xml.parsers.SAXParserFactory",
                "org.apache.xerces.jaxp.SAXParserFactoryImpl");
        }

        final SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        //factory.setFeature("http://xml.org/sax/features/validation", true);
        //factory.setFeature("http://apache.org/xml/features/validation/schema",true);
        //factory.setFeature("http://apache.org/xml/features/validation/schema-full-check ing", true);
        final SAXParser parser = factory.newSAXParser();
        parser.setProperty("http://apache.org/xml/properties/schema/external-noNamespac eSchemaLocation",
            xsdFile);

        parser.parse(new FileInputStream(xmlFile),
            new DefaultHandler() {
                public void error(SAXParseException e)
                    throws SAXException {
                    final boolean status = false;
                    throw e;
                }
            });

        return status;
    }

    public boolean domSchemaValidator(String docFile, String xsdFile) {
        boolean flag = false;

        try {
            DOMParser parser = new DOMParser();
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
                xsdFile);

            //ErrorChecker errors = new ErrorChecker();
            //parser.setErrorHandler(errors);
            parser.parse(docFile);
            flag = true;
        } catch (Exception e) {
            flag = false;
            System.out.print("Problem parsing the file.");
        }

        return flag;
    }
}
