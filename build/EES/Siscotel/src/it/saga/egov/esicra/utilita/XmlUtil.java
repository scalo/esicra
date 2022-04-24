package it.saga.egov.esicra.utilita;

import org.jdom.*;

public class XmlUtil  {
org.jdom.Namespace namespace=null;
public XmlUtil() {
    }

public XmlUtil(org.jdom.Namespace Namespace) {
   namespace=Namespace;
    }


// Ritorna il contenuto di un tag XML
   public String leggiCampo(org.jdom.Element element) {
      String ris = "";
      if (element != null) {
          if (element.getText() != null){
            ris = element.getText().trim();
          }
      }
      return ris;
    }

    // Ricevuto un elemnto IndirizzoBean compila una stringa contenente i dati dell'indirizzo
    public String leggiIndirizzoCompleto(org.jdom.Element element){
        String ind = "";
        ind = leggiComuneLocalita(element);    
        ind = ind+ " - ";
        if (!leggiCampo(element.getChild("Cap",namespace)).equals("")){
            ind = ind+ leggiCampo(element.getChild("Cap",namespace));
        }
        ind = ind+ " - ";
        if (!leggiCampo(element.getChild("SpecieArea",namespace)).equals("")){
            ind = ind+ leggiCampo(element.getChild("SpecieArea",namespace));
        }
        if (!leggiCampo(element.getChild("DesArea",namespace)).equals("")){
            ind = ind+ " "+ leggiCampo(element.getChild("DesArea",namespace));
        }
        if (!leggiCampo(element.getChild("NumCiv",namespace)).equals("")){
            ind = ind+ ", "+leggiCampo(element.getChild("NumCiv",namespace));
        }
        if (!leggiCampo(element.getChild("LetCiv",namespace)).equals("")){
            ind = ind+ "/"+ leggiCampo(element.getChild("LetCiv",namespace));
        }
        if (!leggiCampo(element.getChild("Scala",namespace)).equals("")){
            ind = ind+ "Sc."+ leggiCampo(element.getChild("Scala",namespace));
        }
        if (!leggiCampo(element.getChild("Interno",namespace)).equals("")){
            ind = ind+ "Int."+ leggiCampo(element.getChild("Interno",namespace));
        }

        return ind;        
    }


    public String leggiComuneLocalita(org.jdom.Element element){
        String ris = "";
        org.jdom.Element localita = null;
        org.jdom.Element comune = null;
        if (element.getChild("Comune",namespace) != null) {
            comune = element.getChild("Comune",namespace);
            ris = leggiCampo(comune.getChild("DesComune",namespace));
             if (!leggiCampo(comune.getChild("DesProvincia",namespace)).equals("")) {
                        ris = ris+" ("+leggiCampo(comune.getChild("DesProvincia",namespace))+")";
             }
        }
        if (ris == null || ris.trim().equals("")){
                if(element.getChild("Localita",namespace)!= null){
                    localita = element.getChild("Localita",namespace);
                  ris = leggiCampo(localita.getChild("DesLocalita",namespace));
                  ris = ris+" - "+leggiCampo(localita.getChild("DesStato",namespace));
                  if (localita.getChild("DesContea",namespace)!= null) {
                      ris = ris+ " - "+leggiCampo(localita.getChild("DesContea",namespace));
                  }
                }
              }
        return ris;            
    }
 
}