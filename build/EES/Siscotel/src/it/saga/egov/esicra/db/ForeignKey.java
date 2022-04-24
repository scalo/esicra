package it.saga.egov.esicra.db;

import org.jdom.*;
import org.apache.log4j.*;

public class ForeignKey implements Comparable {
    
    protected String fkName ;
    
    protected String localColumn;
    
    protected String foreignColumn;
    
    protected String foreignTable;
    
    protected int ord ;
    
    private Logger logger = null;

    public ForeignKey(String fkName, String foreignTable, String localColumn ,String foreignColumn, int seq){
        logger = Logger.getLogger(this.getClass());
        this.fkName=fkName;
        this.foreignTable=foreignTable;
        this.localColumn=localColumn;
        this.foreignColumn=foreignColumn;
        this.ord=seq;
    }

    public Element toXml() {
        Element element =new Element("foreign-key");
        element.setAttribute("name",fkName.toUpperCase());
        element.setAttribute("foreignTable",foreignTable.toUpperCase());
        Element ref = new Element("reference");
        ref.setAttribute("localColumn",localColumn.toUpperCase());
        ref.setAttribute("foreignColumn",foreignColumn.toUpperCase());
        ref.setAttribute("ord",Integer.toString(ord));
        element.addContent(ref);
        return element;
    }
    
    public int compareTo(Object o){
        ForeignKey f1 = this;
        ForeignKey f2 = (ForeignKey) o;
        if( (f1.fkName.equalsIgnoreCase(f2.fkName))
            &&(f1.foreignTable.equalsIgnoreCase(f2.foreignTable))
            &&(f1.localColumn.equalsIgnoreCase(f2.localColumn))
            &&((f1.foreignColumn.equalsIgnoreCase(f2.foreignColumn))
            &&(f1.ord==f2.ord))
        ){
            return 0;
        }
        logger.error("FK differenti: "+f1.fkName+" <> "+f2.fkName);
        return -1;
    }
}