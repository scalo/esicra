package it.saga.egov.esicra.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import java.net.*;

/**
 *  Servlet per l'http download di file implementato nella doGet()
 *  ultilizzando il mime application/binary
 *  
 *  parametri :
 *  
 *  file: nome del file codificato come Url (java URLEncoder)
 *  dir: directory di download
 *  
 *  se il parametro dir non è specificato di default rimanda alla dir degli
 *  aggiornamenti software
 */

public class DownloadServlet extends HttpServlet {

    protected Logger logger;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger=Logger.getLogger(this.getClass());
        String filename = req.getParameter("file");
        logger.debug("QUERY:"+req.getQueryString()+" from "+req.getRemoteAddr());
        // decodifica il parametro per gestire i caratteri di escape 
        filename=URLDecoder.decode(filename);
        if (filename == null) {
            logger.error("Download file nullo :"+filename);
            return;
        }
        String export_dir=req.getParameter("dir");
        if (export_dir == null) {
            export_dir=System.getProperty("maad.repository");
            //System.out.println("download from maad.repository="+export_dir);
        }
        if (export_dir == null) {
            logger.error("Directory Download nulla :"+export_dir);
            return;
        }
        export_dir = export_dir.trim();
        File dir = new File(export_dir);
        File file = new File(dir,filename);
        if (!file.exists()) {
            logger.error("Download file inesistente: " + file);
            return;
        }
        FileInputStream fis = null;
        OutputStream fos = null;
        resp.setContentType("application/binary");
        resp.setHeader("Content-Disposition", "attachment; filename="+file.getName());
        try {
            fis = new FileInputStream(file);
            fos = resp.getOutputStream();
            int curByte = -1;
            while ((curByte = fis.read()) != -1)
                fos.write(curByte);
            fos.flush();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }
}
