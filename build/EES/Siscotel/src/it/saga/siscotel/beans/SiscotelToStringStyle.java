package it.saga.siscotel.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.builder.StandardToStringStyle;


public class SiscotelToStringStyle  {
  
  private static final ItStyle STYLE = new ItStyle();
    
  static {
      STYLE.setUseShortClassName(true);
      STYLE.setUseIdentityHashCode(false);
      STYLE.setArrayStart("[");
      STYLE.setArraySeparator(", ");
      STYLE.setArrayEnd("]");
      STYLE.setNullText("NULL");
      STYLE.setSizeStartText("%SIZE=");
      STYLE.setSizeEndText("%");
      STYLE.setSummaryObjectStartText("%");
      STYLE.setSummaryObjectEndText("%");
  }
  
  public static StandardToStringStyle getStyle(){
    return STYLE;
  }
  
}

class ItStyle extends StandardToStringStyle {
   protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
     if (value instanceof Date) {
       value = new SimpleDateFormat("dd/MM/yyyy").format(value);
     }
     buffer.append(value);
   }
}
