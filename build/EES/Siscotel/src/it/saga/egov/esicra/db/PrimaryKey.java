package it.saga.egov.esicra.db;

import org.jdom.*;
import org.apache.log4j.Logger;

public class PrimaryKey implements Comparable {
    
    protected Logger logger;
    private String name ;
    private String columnName ;
    protected int ord ;
    
    public PrimaryKey(String name, String column , int ord){
        logger = Logger.getLogger(this.getClass());
        this.name=name;
        this.columnName=column;
        this.ord=ord;
    }

    public Element toXml() {
        Element element=new Element("primary-key");
        element.setAttribute("name",name.toUpperCase());
        element.setAttribute("column",columnName.toUpperCase());
        element.setAttribute("ord",Integer.toString(ord));
        return element;
    }
    
    /**
     * controllo non bloccante
     */
    public int compareTo(Object o){
        PrimaryKey c1 = this;
        PrimaryKey c2 = (PrimaryKey) o;
        if((c1.name.equalsIgnoreCase(c2.name))
            &&(c1.columnName.equalsIgnoreCase(c2.columnName))
            &&(c1.ord==c2.ord)
        ){
            return 0;
        }else{
            logger.error("PK differenti: "+c1.name+" <> "+c2.name);
        }
        return 0;
    }
    
    public static void main(String[] args) {
        PrimaryKey c1 = new PrimaryKey("tab1","id",1);
        PrimaryKey c2 = new PrimaryKey("tab2","id",1);
        PrimaryKey c3 = new PrimaryKey("tab1","id2",1);
        PrimaryKey c4 = new PrimaryKey("tab1","id",2);
        PrimaryKey c5 = new PrimaryKey("tab1","id",1);
        
        System.out.println("c1 == c2 "+c1.compareTo(c2));
        System.out.println("c1 == c3 "+c1.compareTo(c3));
        System.out.println("c1 == c4 "+c1.compareTo(c4));
        System.out.println("c1 == c5 "+c1.compareTo(c5));
        
    }
    
}
