package it.saga.egov.esicra.servlet;

import it.saga.egov.esicra.*;
import it.saga.egov.esicra.timer.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.*;
import java.io.File;
import javax.naming.*;

/**
 *  E' necessario definire nel web.xml <display-name>nome applicazione</display-name>
 *  in modo che il contesto possa essere recuperato per parametrizzare le chiamate alle servlet
 */
public class ContextListener implements ServletContextListener {
    
    private ServletContext context = null;
    private Logger logger;
    private Context esicraCtx;
    
    /**
     *  chiamato allo startup della web application
     *
     */
    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        //System.out.println("CONTESTO:"+context.getServletContextName());
        String nomeApplicazione = context.getServletContextName();
        System.gc();
        Runtime.getRuntime().gc();
        EsicraConf.configura();
        logger = EsicraConf.getLogger(this.getClass());
        try {
            esicraCtx = EsicraConf.getInitialContext(EsicraConf.PATH);
            logger.debug("contextInitialized: "+EsicraConf.PATH);
            // crea oggetto timer e lo registra attraverso in naming service jndi
            String pDelay = System.getProperty("esicra.timer.delay");
            long delay = EesTimer.DELAY;
            if (pDelay != null) {
                pDelay = pDelay.trim();
                try {
                    delay = Long.parseLong(pDelay);
                } catch (Exception e) {
                    // number format exception
                    delay = EesTimer.DELAY;
                }
            }
            EesTimerConf.loadProperties();
            EesTimer timer = new EesTimer(delay);
            esicraCtx.bind(EesTimer.JNDI_TIMER_NAME, timer);
            //logger.debug("registrazione timer");
            timer.carica();
            timer.start();
            logger.debug(EesTimer.JNDI_TIMER_NAME +"= "+timer+ " delay =" + delay + " ms");
            // imposta property per xslt e xsd
            String xsltPath = context.getRealPath("xslt");
            String version=System.getProperty("esicra.xmlschema.versione");
            if(version==null) version="2.0";
            logger.debug("Versione schemi xml "+version);
            String xsdPath = context.getRealPath("xsd"+File.separator+version);
            logger.debug("xsltPath="+xsltPath);
            logger.debug("xsdPath="+xsdPath);
            System.setProperty("esicra.xslt.dir",xsltPath);
            System.setProperty("esicra.xsd.dir",xsdPath);
            // imposta nome applicazione (contesto) se specificato in web.xml
            if(nomeApplicazione!=null)
              System.setProperty("esicra.context",nomeApplicazione);
            
        } catch (NamingException ne) {
            logger.error("Errore Naming");
            System.out.println(ne.toString());
        } catch (Exception e) {
            logger.error("Errore Generico :" + e);
            e.printStackTrace();
        }
    }

    /**
     *  chiamato allo shutdown della web application
     */
    public void contextDestroyed(ServletContextEvent event) {
        System.gc();
        Runtime.getRuntime().gc();
        try{
            esicraCtx = EsicraConf.getInitialContext(EsicraConf.PATH);
            EesTimer timer = EesTimerConf.getInstance();
            esicraCtx.unbind(EesTimerConf.JNDI_TIMER_NAME);
            logger.debug(EesTimerConf.JNDI_TIMER_NAME+" shutdown :"+timer);
            timer.taskMap.clear();
            timer=null;
        } catch (NamingException ne) {
            logger.error("Errore Naming JNDI");
            System.out.println(ne.toString());
        }catch(Exception e){
             System.out.println(e);
        }
    }
}
