package it.saga.siscotel.db.test;

import java.util.Map;
import java.util.HashMap;

public abstract class Validate  {
  
  public static final Integer ERR_NAME_ENTER = new Integer(1);
  public static final Integer ERR_SEX_ENTER = new Integer(2);
  public static final Integer ERR_SEX_INVALID = new Integer(3);
  public static final Integer ERR_FLOAT_ENTER = new Integer(4);
  public static final Integer ERR_FLOAT_INVALID = new Integer(5);
  public static final Integer ERR_FLOAT_NUM_ONLY = new Integer(6);
 
 // Holds error messages for the properties
        Map errorCodes = new HashMap();
    
        // Maps error codes to textual messages.
        // This map must be supplied by the object that instantiated this bean.
        Map msgMap;
        public void setErrorMessages(Map msgMap) {
            this.msgMap = msgMap;
        }
  
}