package it.saga.siscotel.esicra.anagrafeestesa.webservice.test;

import java.util.Vector;
import java.net.URL;
import java.util.Properties;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.Constants;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.util.xml.QName;
import org.apache.soap.transport.http.SOAPHTTPConnection;


public class HelloWorldWsProxy {

    public HelloWorldWsProxy() {
        m_httpConnection = new SOAPHTTPConnection();
        _setMaintainSession(true);

    }

    public java.lang.String sayHello(java.lang.String nome) throws Exception {
        String soapActionURI = "";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("nome", java.lang.String.class, nome, null));
        Response response = makeSOAPCallRPC("sayHello", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.String)returnValue.getValue();

    }

    private Response makeSOAPCallRPC(String methodName, Vector params, String encodingStyleURI, String soapActionURI) throws Exception {
        Call call  = new Call();
        call.setSOAPTransport(m_httpConnection);
        if (m_soapMappingRegistry != null)
            call.setSOAPMappingRegistry(m_soapMappingRegistry);
        call.setTargetObjectURI(m_serviceID);
        call.setMethodName(methodName);
        call.setEncodingStyleURI(encodingStyleURI);
        call.setParams(params);

        Response response = call.invoke(new URL(m_soapURL), soapActionURI);
        if (response.generatedFault()) {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }
        return response;

    }

    public String _getSoapURL() {
        return m_soapURL;

    }

    public void _setSoapURL(String soapURL) {
        m_soapURL = soapURL;

    }

    public String _getServiceID() {
        return m_serviceID;

    }

    public void _setServiceID(String serviceID) {
        m_serviceID = serviceID;

    }

    public SOAPMappingRegistry _getSOAPMappingRegistry() {
        return m_soapMappingRegistry;

    }

    public void _setSOAPMappingRegistry(SOAPMappingRegistry soapMappingRegistry) {
        m_soapMappingRegistry = soapMappingRegistry;

    }

    public boolean _getMaintainSession() {
        return m_httpConnection.getMaintainSession();

    }

    public void _setMaintainSession(boolean maintainSession) {
        m_httpConnection.setMaintainSession(maintainSession);

    }
    
    // non utilizzati con SOAPHTTPConnection;
    /*
    public Properties _getTransportProperties() {
        return m_httpConnection.getProperties();

    }

    public void _setTransportProperties(Properties properties) {
        m_httpConnection.setProperties(properties);

    }
    */
    
    public String _getVersion() {
        return m_version;

    }


    private String m_serviceID = "it.saga.siscotel.esicra.anagrafeestesa.webservice.test.HelloWorldWs";
    private String m_soapURL = "http://localhost:880/siscotel/servlet/rpcrouter";
    private SOAPHTTPConnection m_httpConnection = null;
    private SOAPMappingRegistry m_soapMappingRegistry = null;
    private String m_version = "1.0";

}
