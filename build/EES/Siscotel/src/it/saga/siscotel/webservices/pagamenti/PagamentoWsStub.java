package it.saga.siscotel.webservices.pagamenti;

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
import it.saga.siscotel.beans.base.*;

public class PagamentoWsStub implements PagamentoInt {

    public PagamentoWsStub() {
        m_httpConnection = new SOAPHTTPConnection();
        _setMaintainSession(true);
        m_soapMappingRegistry = new SOAPMappingRegistry();

        BeanSerializer beanSer = new BeanSerializer();

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.pagamenti/PagamentoWs.xsd" ,"it_saga_siscotel_beans_base_PagamentoBean"), PagamentoBean.class, beanSer, beanSer);


    }

    public PagamentoBean[] cercaPagamento( java.math.BigDecimal param1, java.math.BigDecimal param2, java.util.Date param3, java.util.Date param4) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.pagamenti.PagamentoWs/cercaPagamento";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param1", java.math.BigDecimal.class, param1, null));
        params.add(new Parameter("param2", java.math.BigDecimal.class, param2, null));
        params.add(new Parameter("param3", java.util.Date.class, param3, null));
        params.add(new Parameter("param4", java.util.Date.class, param4, null));
        Response response = makeSOAPCallRPC("cercaPagamento", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (PagamentoBean[])returnValue.getValue();

    }

    public java.lang.Boolean registraPagamento(java.math.BigDecimal param0, java.math.BigDecimal param1, PagamentoBean[] param2) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.pagamenti.PagamentoWs/registraPagamento";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", java.math.BigDecimal.class, param0, null));
        params.add(new Parameter("param1", java.math.BigDecimal.class, param1, null));
        params.add(new Parameter("param2", PagamentoBean[].class, param2, null));
        Response response = makeSOAPCallRPC("registraPagamento", params, encodingStyleURI, soapActionURI);
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


    private String m_serviceID = "it.saga.siscotel.webservices.pagamenti.PagamentoWs";
    private String m_soapURL = "http://localhost:8080/esicra/servlet/soaprouter";
    private SOAPHTTPConnection m_httpConnection = null;
    private SOAPMappingRegistry m_soapMappingRegistry = null;
    private String m_version = "1.0";

}
