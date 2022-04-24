package it.saga.egov.esicra.soap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.soap.*;
import org.apache.soap.encoding.*;
import org.apache.soap.util.xml.QName;
import org.apache.soap.encoding.soapenc.*;

/**
 *  Override della default deserialisation di apache soap 2.3.1 .
 *  I tipi soap che di default vengono mappati in tipi primitivi java  vengono
 *  adesso mappati con i wrapper java (come in oracle apache soap )
 *  
 */
public class WrapperSoapMappingRegistry extends SOAPMappingRegistry {

    public WrapperSoapMappingRegistry() {
        super();
        registerWrapperMappings();
    }

    public WrapperSoapMappingRegistry(String schemaURI) {
        super(SOAPMappingRegistry.getBaseRegistry(""), schemaURI);
        registerWrapperMappings();
    }

    private void registerWrapperMappings() {
        IntObjectDeserializer intDes = new IntObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "int"),
            java.lang.Integer.class,
            null, intDes);
        BooleanObjectDeserializer boolDes = new BooleanObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "boolean"),
            java.lang.Boolean.class,
            null, boolDes);
        LongObjectDeserializer longDes = new LongObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "long"),
            java.lang.Long.class,
            null, longDes);
        DoubleObjectDeserializer doubleDes = new DoubleObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "double"),
            java.lang.Double.class,
            null, doubleDes);
        FloatObjectDeserializer floatDes = new FloatObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "float"),
            java.lang.Float.class,
            null, floatDes);
        ShortObjectDeserializer shortDes = new ShortObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "short"),
            java.lang.Short.class,
            null, shortDes);
        ByteObjectDeserializer byteDes = new ByteObjectDeserializer();
        mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName(Constants.NS_URI_CURRENT_SCHEMA_XSD , "byte"),
            java.lang.Byte.class,
            null, byteDes);
    }
    
    public static void main(String[] args) throws Exception {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            //  0123456789 0 123456789
      sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
      
      Date dt = sdf.parse("1970-12-12T00:00:00.000Z");
      
      System.out.println("A DAY:"+sdf.format(dt));
      dt = new Date(System.currentTimeMillis());
      System.out.println("NOW:"+sdf.format(dt));
    }
    
}
