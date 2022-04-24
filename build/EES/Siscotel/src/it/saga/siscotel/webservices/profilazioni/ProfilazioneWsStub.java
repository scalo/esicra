package it.saga.siscotel.webservices.profilazioni;

import java.util.Vector;
import java.net.URL;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.transport.http.SOAPHTTPConnection;
 
 
public class ProfilazioneWsStub implements ProfilazioneInt {

    public ProfilazioneWsStub() {
        m_httpConnection = new SOAPHTTPConnection();
        _setMaintainSession(true);
    }

    public java.lang.String[] cercaListaProfilazione(java.math.BigDecimal param0, java.math.BigDecimal param1) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.profilazioni.ProfilazioneWs/cercaListaProfilazione";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", java.math.BigDecimal.class, param0, null));
        params.add(new Parameter("param1", java.math.BigDecimal.class, param1, null));
        Response response = makeSOAPCallRPC("cercaListaProfilazione", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.String[])returnValue.getValue();

    }

    public java.lang.Boolean inserisciProfilazione(java.math.BigDecimal param0, java.math.BigDecimal param1, java.lang.String param2) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.profilazioni.ProfilazioneWs/inserisciProfilazione";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", java.math.BigDecimal.class, param0, null));
        params.add(new Parameter("param1", java.math.BigDecimal.class, param1, null));
        params.add(new Parameter("param2", java.lang.String.class, param2, null));
        Response response = makeSOAPCallRPC("inserisciProfilazione", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Boolean)returnValue.getValue();

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


    private String m_serviceID = "it.saga.siscotel.webservices.profilazioni.ProfilazioneWs";
    private String m_soapURL = "http://localhost:8080/esicra/servlet/soaprouter";
    private SOAPHTTPConnection m_httpConnection = null;
    private SOAPMappingRegistry m_soapMappingRegistry = null;
    private String m_version = "1.0";

}
