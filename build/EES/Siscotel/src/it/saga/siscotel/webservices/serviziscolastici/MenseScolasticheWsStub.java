package it.saga.siscotel.webservices.serviziscolastici;

import java.util.Vector;
import java.net.URL;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.Constants;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.transport.http.SOAPHTTPConnection;
import org.apache.soap.util.xml.QName;
import it.saga.siscotel.beans.base.*;
import it.saga.siscotel.beans.serviziscolastici.*;


public class MenseScolasticheWsStub implements MenseScolasticheInt {

    public MenseScolasticheWsStub() {
        m_httpConnection = new SOAPHTTPConnection();
        _setMaintainSession(true);
        m_soapMappingRegistry = new SOAPMappingRegistry();

        BeanSerializer beanSer = new BeanSerializer();

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_base_PraticaBean"), PraticaBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_serviziscolastici_PraIscrizioneMensaBean"), PraIscrizioneMensaBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_base_DatiPraticaBean"), DatiPraticaBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_base_DatiSoggettoBean"), DatiSoggettoBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_base_DatiIndirizzoBean"), DatiIndirizzoBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_serviziscolastici_DatiScuolaBean"), DatiScuolaBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_serviziscolastici_PraRecessoMensaBean"), PraRecessoMensaBean.class, beanSer, beanSer);

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.webservices.serviziscolastici/MenseScolasticheWs.xsd" ,"it_saga_siscotel_beans_base_RecapitoBean"), RecapitoBean.class, beanSer, beanSer);

    }

    public java.math.BigDecimal inserisciPraIscrizioneMensaScolastica(PraIscrizioneMensaBean param0) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWs/inserisciPraIscrizioneMensaScolastica";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", PraIscrizioneMensaBean.class, param0, null));
        Response response = makeSOAPCallRPC("inserisciPraIscrizioneMensaScolastica", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.math.BigDecimal)returnValue.getValue();

    }

    public PraIscrizioneMensaBean[] cercaPraIscrizioneMensaScolastica(java.lang.String param0, java.math.BigDecimal param1, java.math.BigDecimal param2, java.lang.Integer param3, java.util.Date param4, java.util.Date param5) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWs/cercaPraIscrizioneMensaScolastica";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", java.lang.String.class, param0, null));
        params.add(new Parameter("param1", java.math.BigDecimal.class, param1, null));
        params.add(new Parameter("param2", java.math.BigDecimal.class, param2, null));
        params.add(new Parameter("param3", java.lang.Integer.class, param3, null));
        params.add(new Parameter("param4", java.util.Date.class, param4, null));
        params.add(new Parameter("param5", java.util.Date.class, param5, null));
        Response response = makeSOAPCallRPC("cercaPraIscrizioneMensaScolastica", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (PraIscrizioneMensaBean[])returnValue.getValue();

    }

    public PraRecessoMensaBean[] cercaPraRecessoMensaScolastica(java.lang.String param0, java.math.BigDecimal param1, java.math.BigDecimal param2, java.lang.Integer param3, java.util.Date param4, java.util.Date param5) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWs/cercaPraRecessoMensaScolastica";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", java.lang.String.class, param0, null));
        params.add(new Parameter("param1", java.math.BigDecimal.class, param1, null));
        params.add(new Parameter("param2", java.math.BigDecimal.class, param2, null));
        params.add(new Parameter("param3", java.lang.Integer.class, param3, null));
        params.add(new Parameter("param4", java.util.Date.class, param4, null));
        params.add(new Parameter("param5", java.util.Date.class, param5, null));
        Response response = makeSOAPCallRPC("cercaPraRecessoMensaScolastica", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (PraRecessoMensaBean[])returnValue.getValue();

    }

    public java.math.BigDecimal inserisciPraRecessoMensaScolastica(PraRecessoMensaBean param0) throws Exception {
        String soapActionURI = "it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWs/inserisciPraRecessoMensaScolastica";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("param0", PraRecessoMensaBean.class, param0, null));
        Response response = makeSOAPCallRPC("inserisciPraRecessoMensaScolastica", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.math.BigDecimal)returnValue.getValue();

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

    private String m_serviceID = "it.saga.siscotel.webservices.serviziscolastici.MenseScolasticheWs";
    private String m_soapURL = "http://localhost:8080/esicra/servlet/soaprouter";
    private SOAPHTTPConnection m_httpConnection = null;
    private SOAPMappingRegistry m_soapMappingRegistry = null;
    private String m_version = "1.0";

}
