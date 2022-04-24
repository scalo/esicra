package it.saga.egov.esicra.db;

public class CompareLogger  {
    
    private static StringBuffer sb = new StringBuffer() ;
    
    private static CompareLogger instance=null;
    
    public CompareLogger() {
        sb = new StringBuffer();
    }
    
    public static CompareLogger getLogger(){
        if(instance==null)
            instance=new CompareLogger();
        return instance;
    }
    
    public void log(Object msg){
        sb.append(msg.toString()+"\n");
    }
    
    public String getLog(){
        return sb.toString();
    }

    public void clear(){
      sb = new StringBuffer();
    }
    
    public static void main(String[] args) {
      CompareLogger cl = CompareLogger.getLogger();
      cl.log("pippo");
      cl.log("pluto");
      System.out.println(cl.getLog());
    }
    
}
