package it.saga.egov.esicra.db;

import java.util.List;
import org.jdom.*;
import org.apache.log4j.Logger;

public class Index implements Comparable {

    /**
     * Comment here
     * @supplierCardinality 0..*
     * @label Indexcolumn0
     * @associates <{it.saga.esicra.db.Column}>
     */
    
    protected List columnList;
    
    private String indexName;
    
    private String columnName;
    
    private String sort;
    
    private boolean unique;
    
    private int pos;
    
    protected Logger logger;
    
    public Index(String indexName , String columnName, int pos,  boolean unique, String sort){
        logger = Logger.getLogger(this.getClass());
        this.indexName=indexName;
        this.columnName=columnName;
        this.pos=pos;
        this.unique=unique;
        this.sort=sort;
    }
    
    public Element toXml() {
        Element element =new Element("index");
        element.setAttribute("name",indexName.toUpperCase());
        element.setAttribute("column",columnName.toUpperCase());
        element.setAttribute("pos",Integer.toString(pos));
        /* 
         *      CONTROLLARE MEGLIO 
         *      sort non supportato
         * 
         */
        //element.setAttribute("sort",sort==null?"":sort);
        //element.setAttribute("unique",String.valueOf(unique));
        return element;
    }
    
    public int compareTo(Object o){
        Index idx1 = this;
        Index idx2 = (Index) o;
        if((idx1.indexName.equalsIgnoreCase(idx2.indexName))
            &&(idx1.columnName.equalsIgnoreCase(idx2.columnName))
            &&(idx1.pos==idx2.pos)
        ){
            return 0;
        }else{
            logger.warn("Indici differenti: "+idx1.indexName+" <> "+idx2.indexName);
        }
        return 0;
    }
}

