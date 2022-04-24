package it.saga.egov.esicra.importazione;

import java.io.*;
import java.util.*;

public class DirectoryList {

   public static  File[] cartelle(File file) {
     File[] elencoFile = null;
     
     if (file.isDirectory()) {
         elencoFile = file.listFiles();
     }
         return elencoFile;
   }

   public static  File[] cartelle(File path, String filtro) {
     File[] elencoFile = null;
     final String filtro_lista = filtro.toLowerCase();
     FilenameFilter filter = new FilenameFilter() {
           public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.startsWith(filtro_lista);
            }
     };

     elencoFile = path.listFiles(filter);  
     return elencoFile;
   }

  public static void stampaListaFile(File file) {
      File[] elencoFile = cartelle(file);
      if (elencoFile.length > 0) {
          for (int i = 0; i < elencoFile.length; i++)
              if (elencoFile[i].isFile() ) {
                 System.out.println(elencoFile[i].toString());
               } else if (elencoFile[i].isDirectory()) {
                 stampaListaFile(elencoFile[i]);
               }
      }
  }
  
  public static void stampaListaFile(File file, String filtro) {
      File[] elencoFile = cartelle(file,filtro);
      if (elencoFile.length > 0) {
          for (int i = 0; i < elencoFile.length; i++)
              if (elencoFile[i].isFile() ) {
                 System.out.println(elencoFile[i].toString());
               } else if (elencoFile[i].isDirectory()) {
                 stampaListaFile(elencoFile[i], filtro);
               }
      }
  }



}