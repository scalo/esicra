package it.saga.egov.esicra.log;

import java.io.*;

public class ViewLog {

    public static String[] getLogFiles() {
        String logdir = System.getProperty("esicra.log");
        if (logdir != null) {
            logdir = logdir.trim();
        }
        /*
        else{
           logdir="c:/esicra/log";  // dir di test
        }
        */
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                String name=file.getName();
                char c = name.charAt(name.length()-1);
                if(name.endsWith("log")||(name.indexOf("log")>=0 && Character.isDigit(c)))
                    return true;
                return false;
            }
        };
        File dir = new File(logdir);
        if (dir != null) {
            File files[] = dir.listFiles(fileFilter);
            int len = files.length;
            String[] array = new String[len];
            for(int i=0;i<len;i++)
                array[i]=new String(files[i].getName());
            return array;
        }
        //System.out.println("logdir null!");
        return null;
    }

    public static String getLog(String logfile) {
        LineNumberReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            String logdir = System.getProperty("esicra.log");
            if ((logdir != null) && (logfile != null) && (logfile.length()>0)){
                logdir = logdir.trim();
                File dir = new File(logdir);

                File file = new File(logdir, logfile);
                FileReader fr = new FileReader(file);
                reader = new LineNumberReader(fr);
                boolean eof = false;
                String line = "";
                while (!eof) {
                    line = reader.readLine();
                    if (line != null) {
                        sb.append(line + "\n");
                    } else {
                        eof = true;
                    }
                }
            }

            /*
            else{
               logdir="c:/esicra/log";
            }
            */
        } catch (Exception e) {
            System.out.println("Errore I/O :"+e);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] list = ViewLog.getLogFiles();
        for (int i = 0; i < list.length; i++) {
            System.out.println(i + " " + list[i]);
        }
        System.out.println("**************************");
        System.out.println(ViewLog.getLog("esicra.log"));
        System.out.println("**************************");
    }
}
