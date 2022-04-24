package it.saga.egov.esicra.db;
import java.sql.DatabaseMetaData;
import java.util.*;
import java.sql.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.jdom.*;

public class Schema implements Comparable  {
    
    /**
     * Comment here
     * @supplierCardinality 0..*
     * @associates <{it.saga.esicra.db.Table}>
     * @label Schematable0
     */
    protected List tableList;
    
    protected String schemaName = null;
    
    protected Logger logger;

    protected CompareLogger compLog;

    public Schema(String schemaName ){
        logger = Logger.getLogger(this.getClass());
        compLog= CompareLogger.getLogger();
        //logger.setLevel(Level.toLevel("ERROR"));
        //Logger.getRootLogger().getEffectiveLevel().toString();
        //if (schemaName!=null) schemaName=schemaName.toUpperCase();
        this.schemaName = schemaName;
        tableList = new ArrayList();
    }
    
    public Schema(String schemaName , DatabaseMetaData meta){
        compLog= CompareLogger.getLogger();
        logger = Logger.getLogger(this.getClass());
        //if (schemaName!=null) schemaName=schemaName.toUpperCase();
        this.schemaName = schemaName;
        //tabelle  dello schema
        tableList = new ArrayList();
        try{
            ResultSet rs = meta.getTables("",schemaName,null,new String[]{"TABLE"});
            while(rs.next()){
                String tableName =(String) rs.getObject("TABLE_NAME");
                logger.debug("Tabella: "+tableName);
                Table t = new Table(schemaName, tableName, meta);
                tableList.add(t);
            }
            //System.out.println(tableList);
            CompTab compTab = new CompTab();
            Collections.sort(tableList,compTab);
            int len = tableList.size();
            StringBuffer sb = new StringBuffer();
            sb.append("tableList=[");
            for(int i = 0 ; i<len;i++){
                sb.append(((Table)tableList.get(i)).getName());
                if(i!=len) sb.append(", ");
            }
            sb.append("]");
            logger.debug(sb.toString());
            rs.close();
        }catch(SQLException e){
            logger.debug("Errore SQL "+e);
        }
    }
    
    public Element toXml() {
        ArrayList collection = new ArrayList();
        Element element =new Element("schema");
        element.setAttribute("name",schemaName.toUpperCase());
        //tabelle
        if(tableList==null) return null;
        Iterator ite = tableList.iterator();
        Table tab=null;
        while(ite.hasNext()){
            tab = (Table) ite.next();
            element.addContent(tab.toXml());
        }
        return element;
    }
    
    public void  fromXml(Element  node){
        List list = node.getChildren("table");
        Iterator ite = list.iterator();
        Element e =null;
        while(ite.hasNext()){
            e = (Element) ite.next();
            String name = e.getAttribute("name").getValue();
            Table tab = new Table(schemaName,name);
            tab.fromXml(e);
            tableList.add(tab);
        }
    }
    
    public int compareTo(Object o){
        Schema s1 = this;
        Schema s2 = (Schema) o;
        int len1 = s1.tableList.size();
        int len2 = s2.tableList.size();
        logger.debug("schema 1 tabelle: "+ len1);
        logger.debug("schema 2 tabelle: "+ len2);
        // compare names
        if(!s1.schemaName.equalsIgnoreCase(s2.schemaName)){
            compLog.log("Schemi differenti "+s1.schemaName +" "+s2.schemaName);
            //return -1;
        }
        if(len1!=len2){
            compLog.log("numero tabelle differenti #1="+len1+" #2="+len2);
            return len1-len2;
        }
        for(int i=0;i<len1;i++){
            Table t1 = (Table)s1.tableList.get(i);
            Table t2 = (Table)s2.tableList.get(i);
            if(t1.compareTo(t2)!=0){
                compLog.log("Tabelle: "+t1.getName() +" <> "+t2.getName());
                compLog.log("Table1="+t1);
                compLog.log("Table2="+t2);
                return -1;
            }
        }
        return 0;
    }
    
    public String getSchemaName() {
        return schemaName;
    }
    
    
   class CompTab implements Comparator {
        public int compare(Object o1, Object o2){
            Table t1 = (Table) o1;
            Table t2 = (Table) o2;
            int res = t1.tableName.compareTo(t2.tableName);
            return res;
        }
   }
}

