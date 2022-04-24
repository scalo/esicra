package it.saga.egov.esicra.db;

import java.sql.*;
import org.jdom.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *      Aggiungere precisione dei decimal
 */
public class Column implements Comparable {
    
    protected static Logger logger;
    protected int size;
    protected int type;
    protected boolean notNull;
    protected String columnName;
    protected int pos;
    
    public Column(String columnName ,int pos,  int type,int  size){
        logger = Logger.getLogger(this.getClass());    
        this.columnName=columnName;
        this.pos=pos;
        this.size=size;
        this.type=type;
        //this.notNull=notNull;
        //this.primaryKey=primaryKey;
        //this.unique = unique;
    }
    
    public Element toXml() {
    
        Element element =new Element("column");
        element.setAttribute("name",columnName.toUpperCase());
        element.setAttribute("pos",Integer.toString(pos));
        element.setAttribute("type",Integer.toString(type));
        element.setAttribute("des",TypeList.getTypeName(type));
        element.setAttribute("size",Integer.toString(size));
        return element;
        
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("name="+this.columnName+",");
        sb.append("pos="+this.pos+",");
        sb.append("type="+this.type+",");
        sb.append("des="+TypeList.getTypeName(this.type)+",");
        sb.append("size="+this.size);
        sb.append("]");
        return sb.toString();
    }
    
    public int compareTo(Object o){
        Column c1 = this;
        Column c2 = (Column) o;
        if((c1.size==c2.size)
            &&(c1.columnName.equalsIgnoreCase(c2.columnName))
            &&(c1.type==c2.type)
            &&(c1.pos==c2.pos)
        ){
            return 0;
        }else{
            // compatibility
            if(TypeList.typeCompatibility(c1,c2)){
                return 0;
            }
        }
        logger.error("Type mismatch  col="+c1.columnName);
        logger.error("c1="+c1);
        logger.error("c2="+c2);
        return -1;
    }
    
    public static void main(String[] args) {
        Column c1 = new Column("name",1,11,10);
        Column c2 = new Column("name",1,11,10);
        Column c3 = new Column("memo",1,1,10);
        Column c4 = new Column("name",1,2,10);
        Column c5 = new Column("name",1,11,2);
        Column c6 = new Column("nome",2,11,10);
        Column p1 = new Column("date",1,93,4);
        Column o1 = new Column("date",1,91,4);
        Column p2 = new Column("int",1,4,4);
        Column o2 = new Column("int",1,3,22);
        Column p3 = new Column("number",1,3,4);
        Column o3 = new Column("number",1,2,4);
        
        System.out.println("c1 == c2 "+c1.compareTo(c2));
        System.out.println("c1 == c3 "+c1.compareTo(c3));
        System.out.println("c1 == c4 "+c1.compareTo(c4));
        System.out.println("c1 == c5 "+c1.compareTo(c5));
        System.out.println("c1 == c6 "+c1.compareTo(c6));
        System.out.println("(date-timestamp) p1 == o1 "+o1.compareTo(p1)); 
        System.out.println("(decimal-int4)   p2 == o2 "+o2.compareTo(p2));
        System.out.println("(decimal-number) p3 == o3 "+o3.compareTo(p3));

    }
    
}
