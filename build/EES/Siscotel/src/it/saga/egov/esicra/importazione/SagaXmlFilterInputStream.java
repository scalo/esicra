package it.saga.egov.esicra.importazione;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;

/**
 *      Classe che risolve il problema dell'xml restituito
 *      dalle esportazioni di SICRA, le esportazioni di sicra possono produrre
 *      xml NON BEN FORMATO , non vengono filtrati i caratteri di escape come & e < , > 
 *      che eventualmente  presenti nel testo creando un file xml causano errori al parser sax
 *      La classe gestisce solo il carattere di escape &
 */
public class SagaXmlFilterInputStream extends FilterInputStream {

    public SagaXmlFilterInputStream(InputStream in) {
        super(in);
        //this.in=in;
    }

    public int read() throws IOException {
        int c = in.read();
        if (c != -1) {
            if(c=='&'){
                return 'E';
            }
        }
        return c;
    }

    public int read(byte[] b) throws IOException {
        int len;
        len = in.read(b, 0, b.length);
        if (len != -1) {
            for(int i=0;i<len;i++){
                if(b[i]=='&')
                    b[i]='E';
            }
        }
        return len;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        len = in.read(b, off, len);
        if (len != -1) {
            for(int i=off;i<off+len;i++){
                if(b[i]=='&')
                    b[i]='E';
            }
        }
        return len;
    }

    public static void main(String[] args) {
        /*
        try{
            String str = "CIP & CIOP";
            SagaXmlFilterInputStream sis = new SagaXmlFilterInputStream( 
                new StringBufferInputStream(str));
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = sis.read(buf)) >= 0) {
                System.out.write(buf, 0, count);
            }
            sis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        */
    }
}

