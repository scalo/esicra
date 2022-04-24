package it.saga.egov.esicra.esportazione;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;

/**
 *
 *  Classe per testare le funzionalita dell' implementazione JAXP utilizzata
 *      oracle non implementa stream in e out
 *      apache xalan si
 *
 */
public class TrAXFeatureTester {

    /*
     *  nomi delle classi implemtntazioni del transformer
     */
    static String TRANSFORMER_ORACLE ="oracle.xml.jaxp.JXSAXTransformerFactory";
    static String TRANSFORMER_XALAN  ="org.apache.xalan.processor.TransformerFactoryImpl";

    public static void main(String[] args) {
        System.setProperty("javax.xml.transform.TransformerFactory", TRANSFORMER_XALAN);
        System.out.println("** TRAX "+System.getProperty("javax.xml.transform.TransformerFactory")+" **");
        TransformerFactory xformFactory = TransformerFactory.newInstance();

        String name = xformFactory.getClass().getName();

        if (xformFactory.getFeature(DOMResult.FEATURE)) {
            System.out.println(name + " supports DOM output.");
        } else {
            System.out.println(name + " does not support DOM output.");
        }
        if (xformFactory.getFeature(DOMSource.FEATURE)) {
            System.out.println(name + " supports DOM input.");
        } else {
            System.out.println(name + " does not support DOM input.");
        }

        if (xformFactory.getFeature(SAXResult.FEATURE)) {
            System.out.println(name + " supports SAX output.");
        } else {
            System.out.println(name + " does not support SAX output.");
        }
        if (xformFactory.getFeature(SAXSource.FEATURE)) {
            System.out.println(name + " supports SAX input.");
        } else {
            System.out.println(name + " does not support SAX input.");
        }

        if (xformFactory.getFeature(StreamResult.FEATURE)) {
            System.out.println(name + " supports stream output.");
        } else {
            System.out.println(name + " does not support stream output.");
        }
        if (xformFactory.getFeature(StreamSource.FEATURE)) {
            System.out.println(name + " supports stream input.");
        } else {
            System.out.println(name + " does not support stream input.");
        }

        if (xformFactory.getFeature(SAXTransformerFactory.FEATURE)) {
            System.out.println(name + " returns SAXTransformerFactory " +
                "objects from TransformerFactory.newInstance().");
        } else {
            System.out.println(name + " does not use SAXTransformerFactory.");
        }
        if (xformFactory.getFeature(SAXTransformerFactory.FEATURE_XMLFILTER)) {
            System.out.println(name + " supports the newXMLFilter() methods.");
        } else {
            System.out.println(name +
                " does not support the newXMLFilter() methods.");
        }
    }
}
