package it.saga.siscotel.esicra.anagrafeestesa.webservice.stubs;

import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.transport.http.SOAPHTTPConnection;
import org.apache.soap.util.xml.QName;
import java.net.URL;
import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import java.util.Vector;
import java.math.BigDecimal;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.ProvenienzaBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoProvenienzaBean;
import it.saga.siscotel.srvfrontoffice.beans.base.ComuneBean;
import it.saga.siscotel.srvfrontoffice.beans.base.LocalitaBean;
import it.saga.siscotel.srvfrontoffice.beans.base.IndirizzoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.SoggettoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.PermessoSoggiornoBean;
import it.saga.siscotel.srvfrontoffice.beans.base.SchedaSoggettoBean;
import it.saga.siscotel.srvfrontoffice.beans.commercio.IscrizioneCCIABean;
import it.saga.siscotel.srvfrontoffice.beans.base.SoggettoGiuridicoBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.IdentificativiCatastaliBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoBean;
import it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.SoggettoProvenienzaBean;
import java.util.Properties;
import it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaInt;

/*
 * sostituito  OracleSOAPHTTPConnection con SOAPHTTPConnection , è stato necessario
 * commentare 2 metodi dello stub
 * 
 */

public class EesAnagrafeEstesaWsStub implements EesAnagrafeEstesaInt {
    public EesAnagrafeEstesaWsStub() {
        m_httpConnection = new SOAPHTTPConnection();
        m_smr = new SOAPMappingRegistry();

        BeanSerializer beanSer = new BeanSerializer();
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_anagrafeestesa_ProvenienzaBean"), it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.ProvenienzaBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_anagrafeestesa_OggettoProvenienzaBean"), it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoProvenienzaBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_ComuneBean"), it.saga.siscotel.srvfrontoffice.beans.base.ComuneBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_LocalitaBean"), it.saga.siscotel.srvfrontoffice.beans.base.LocalitaBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_IndirizzoBean"), it.saga.siscotel.srvfrontoffice.beans.base.IndirizzoBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_SoggettoBean"), it.saga.siscotel.srvfrontoffice.beans.base.SoggettoBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_PermessoSoggiornoBean"), it.saga.siscotel.srvfrontoffice.beans.base.PermessoSoggiornoBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_SchedaSoggettoBean"), it.saga.siscotel.srvfrontoffice.beans.base.SchedaSoggettoBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_commercio_IscrizioneCCIABean"), it.saga.siscotel.srvfrontoffice.beans.commercio.IscrizioneCCIABean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_base_SoggettoGiuridicoBean"), it.saga.siscotel.srvfrontoffice.beans.base.SoggettoGiuridicoBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_anagrafeestesa_IdentificativiCatastaliBean"), it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.IdentificativiCatastaliBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_anagrafeestesa_OggettoBean"), it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.OggettoBean.class, beanSer, beanSer);
        m_smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://it.saga.siscotel.esicra.anagrafeestesa.webservice.base/IEesAnagrafeEstesaWs.xsd", "it_saga_siscotel_srvfrontoffice_beans_anagrafeestesa_SoggettoProvenienzaBean"), it.saga.siscotel.srvfrontoffice.beans.anagrafeestesa.SoggettoProvenienzaBean.class, beanSer, beanSer);
    }

    public String endpoint = "http://127.0.0.1:8080/esicra/servlet/soaprouter";
    private SOAPHTTPConnection m_httpConnection = null;
    private SOAPMappingRegistry m_smr = null;

    public OggettoProvenienzaBean cercaOggettoProvenienza(String codiceEcografico, String codProvenienza) throws Exception {
        OggettoProvenienzaBean returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaOggettoProvenienza");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("codiceEcografico", java.lang.String.class, codiceEcografico, null));
        params.addElement(new Parameter("codProvenienza", java.lang.String.class, codProvenienza, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (OggettoProvenienzaBean)result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoGiuridicoBean cercaSoggettoGiuridicoIndicePI(String partitaIva) throws Exception {
        SoggettoGiuridicoBean returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoGiuridicoIndicePI");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("partitaIva", java.lang.String.class, partitaIva, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoGiuridicoBean)result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public OggettoBean[] cercaOggettoIndiceIC(BigDecimal idEnte, String sezione, String foglio, String mappale, String sub, Integer anno, String protocollo, String tipoCatasto) throws Exception {
        OggettoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaOggettoIndiceIC");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("sezione", java.lang.String.class, sezione, null));
        params.addElement(new Parameter("foglio", java.lang.String.class, foglio, null));
        params.addElement(new Parameter("mappale", java.lang.String.class, mappale, null));
        params.addElement(new Parameter("sub", java.lang.String.class, sub, null));
        params.addElement(new Parameter("anno", java.lang.Integer.class, anno, null));
        params.addElement(new Parameter("protocollo", java.lang.String.class, protocollo, null));
        params.addElement(new Parameter("tipoCatasto", java.lang.String.class, tipoCatasto, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (OggettoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public OggettoBean cercaOggettoIndiceCE(String codiceEcografico) throws Exception {
        OggettoBean returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaOggettoIndiceCE");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("codiceEcografico", java.lang.String.class, codiceEcografico, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (OggettoBean)result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoGiuridicoBean[] cercaSoggettoGiuridicoIndiceId(String denominazione, BigDecimal idEnte, String desArea, Integer numCiv, String letCiv) throws Exception {
        SoggettoGiuridicoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoGiuridicoIndiceId");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("denominazione", java.lang.String.class, denominazione, null));
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("desArea", java.lang.String.class, desArea, null));
        params.addElement(new Parameter("numCiv", java.lang.Integer.class, numCiv, null));
        params.addElement(new Parameter("letCiv", java.lang.String.class, letCiv, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoGiuridicoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoProvenienzaBean cercaSoggettoProvenienza(String codiceFiscale, String codProvenienza) throws Exception {
        SoggettoProvenienzaBean returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoProvenienza");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("codiceFiscale", java.lang.String.class, codiceFiscale, null));
        params.addElement(new Parameter("codProvenienza", java.lang.String.class, codProvenienza, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoProvenienzaBean)result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoBean[] cercaSoggettoFisicoIndiceId(String cognome, String nome, BigDecimal idEnte, String desArea, Integer numCiv, String letCiv) throws Exception {
        SoggettoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoFisicoIndiceId");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("cognome", java.lang.String.class, cognome, null));
        params.addElement(new Parameter("nome", java.lang.String.class, nome, null));
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("desArea", java.lang.String.class, desArea, null));
        params.addElement(new Parameter("numCiv", java.lang.Integer.class, numCiv, null));
        params.addElement(new Parameter("letCiv", java.lang.String.class, letCiv, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoBean[] cercaSoggettoFisicoIndiceInd(BigDecimal idEnte, String desArea, Integer numCiv, String letCiv) throws Exception {
        SoggettoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoFisicoIndiceInd");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("desArea", java.lang.String.class, desArea, null));
        params.addElement(new Parameter("numCiv", java.lang.Integer.class, numCiv, null));
        params.addElement(new Parameter("letCiv", java.lang.String.class, letCiv, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoBean[] cercaSoggettoFisicoIndiceNome(String nome, BigDecimal idEnte, String desArea, Integer numCiv, String letCiv) throws Exception {
        SoggettoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoFisicoIndiceNome");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("nome", java.lang.String.class, nome, null));
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("desArea", java.lang.String.class, desArea, null));
        params.addElement(new Parameter("numCiv", java.lang.Integer.class, numCiv, null));
        params.addElement(new Parameter("letCiv", java.lang.String.class, letCiv, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoGiuridicoBean[] cercaSoggettoGiuridicoIndiceEnte(BigDecimal idEnte, String desArea, Integer numCiv, String letCiv) throws Exception {
        SoggettoGiuridicoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoGiuridicoIndiceEnte");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("desArea", java.lang.String.class, desArea, null));
        params.addElement(new Parameter("numCiv", java.lang.Integer.class, numCiv, null));
        params.addElement(new Parameter("letCiv", java.lang.String.class, letCiv, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoGiuridicoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public OggettoBean[] cercaOggettoIndiceInd(BigDecimal idEnte, String desArea, Integer numCiv, String letCiv) throws Exception {
        OggettoBean[] returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaOggettoIndiceInd");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("idEnte", java.math.BigDecimal.class, idEnte, null));
        params.addElement(new Parameter("desArea", java.lang.String.class, desArea, null));
        params.addElement(new Parameter("numCiv", java.lang.Integer.class, numCiv, null));
        params.addElement(new Parameter("letCiv", java.lang.String.class, letCiv, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (OggettoBean[])result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }

    public SoggettoBean cercaSoggettoFisicoIndiceCF(String codiceFiscale) throws Exception {
        SoggettoBean returnVal = null;

        URL endpointURL = new URL(endpoint);
        Call call = new Call();
        call.setSOAPTransport(m_httpConnection);
        call.setTargetObjectURI("it.saga.siscotel.esicra.anagrafeestesa.webservice.base.EesAnagrafeEstesaWs");
        call.setMethodName("cercaSoggettoFisicoIndiceCF");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        Vector params = new Vector();
        params.addElement(new Parameter("codiceFiscale", java.lang.String.class, codiceFiscale, null));
        call.setParams(params);

        call.setSOAPMappingRegistry(m_smr);

        Response response = call.invoke(endpointURL, "");

        if (!response.generatedFault()) {
            Parameter result = response.getReturnValue();
            returnVal = (SoggettoBean)result.getValue();
        }
        else {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }

        return returnVal;
    }
    
    public void setMaintainSession(boolean maintainSession) {
        m_httpConnection.setMaintainSession(maintainSession);
    }

    public boolean getMaintainSession() {
        return m_httpConnection.getMaintainSession();
    }
    
    /*
    public void setTransportProperties(Properties props) {
        m_httpConnection.setProperties(props);
    }

    public Properties getTransportProperties() {
        return m_httpConnection.getProperties();
    }
    */
}
