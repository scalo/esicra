package it.saga.egov.esicra.esportazione;

import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import java.io.File;
/**
 *    Realizzazione di un UriResolver , per permettere all'xslt master
 *    di localizzare i files xslt definiti in include
 */
public class XsltUriResolver implements URIResolver {

    String base_path;

    public XsltUriResolver() {
        this.base_path = System.getProperty("esicra.xslt.dir");
    }

    public Source resolve(String href, String base) {
        StringBuffer path = new StringBuffer(this.base_path);
        //path.append(File.separator+"xml"+File.separator);
        path.append(File.separator+href);
        File file = new File(path.toString());
        // per debug
        //System.out.println(path.toString());
        if (file.exists()) {
            return new StreamSource(file);
        }
        return null;
    }
}
