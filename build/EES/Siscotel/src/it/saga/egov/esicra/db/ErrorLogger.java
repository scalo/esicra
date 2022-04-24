package it.saga.egov.esicra.db;

public class ErrorLogger  {
    
    private static StringBuffer sb ;
    
    private static ErrorLogger instance=null;
    
    public ErrorLogger() {
        sb = new StringBuffer();
    }
    
    public static ErrorLogger getLogger(Class claz){
        if(instance==null)
            instance=new ErrorLogger();
        return instance;
    }
    
    public void error(Object msg){
        sb.append("ERROR"+msg.toString()+"\n");
    }
    
    public void debug(Object msg){
        //sb.append("DEBUG"+msg.toString()+"\n");
    }
    
    public String getLog(){
        return sb.toString();
    }
}
