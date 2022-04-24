package it.saga.egov.esicra.timer;

import java.io.*;
import org.apache.log4j.*;

/**
 *      restituisce la lista dei files con suffisso ok nella directory import 
 */
 
public class ListaProva  {

    public ListaProva() {
        //Logger.getRootLogger().getLevel().toString();
    }

    public static void main(String[] args) throws Exception{
        String dirName="C:\\esicra\\import";
        String archDir="C:\\esicra\\import\\arch";
        File[] elencoFile = null;
        File path = new File(dirName);
        FilenameFilter filter = new FilenameFilter() {
          public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".ok");
          }
        };
        elencoFile = path.listFiles(filter);
        for(int i=0 ; i<elencoFile.length;i++){
            File sorgente = elencoFile[i];
            File destinazione = new File(archDir,sorgente.getName());
            //sorgente.renameTo(destinazione);
            System.out.println(sorgente);
        }
    }
    
}
