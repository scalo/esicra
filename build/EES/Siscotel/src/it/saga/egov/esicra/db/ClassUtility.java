package it.saga.egov.esicra.db;

import org.apache.log4j.Logger;
import java.net.*;
import  org.jdom.*;

public class ClassUtility  {
    
    private static Logger logger = Logger.getLogger(ClassUtility.class);
    
    public ClassUtility() {
    }

    private static Class classExist(String classname){
        try{
            return Class.forName(classname);
        }catch(ClassNotFoundException e){
            //logger.debug("class not found");
            return null;
        }
    }
    
    private static String getLocation(Class clz){
        //System.out.println("clz="+clz);
        try{
            URL url = clz.getProtectionDomain().getCodeSource().getLocation();
            //System.out.println("url="+url);
            String location = url.toString();
            if(location.startsWith("jar")) {
                url = ((java.net.JarURLConnection)url.openConnection()).getJarFileURL();
                location = url.toString();
            } 
            if(location.startsWith("file")) {
                java.io.File file = new java.io.File(url.getFile());
                return file.getAbsolutePath();
            } else {
                return url.toString();
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        return "unknown location";
    }
    
    public static String probeClass (String classname ){
        //logger.info("probeClass");
        String res="";
        Class clz = classExist(classname);
        //System.out.println("clz="+clz);
        if(clz==null){
            
        }else{
            String location = getLocation(clz);
            if(location==null){
                
            }
            res="classname="+classname+" --> "+location;
        }
        return res;
    }
    
    public static void main(String[] args) {
        System.out.println(probeClass("java.sql.Date"));
        System.out.println(probeClass("org.apache.log4j.Logger"));
        System.out.println(probeClass("org.jdom.Comment"));
    }
    
}
