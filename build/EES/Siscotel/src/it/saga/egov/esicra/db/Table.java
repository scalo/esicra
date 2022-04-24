package it.saga.egov.esicra.db;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import org.apache.log4j.*;
import org.apache.log4j.Logger;
import java.util.List;
import org.jdom.*;

public class Table  implements Comparable {
    
    protected String tableName;
    protected String schemaName;
    /**
     * Comment here
     * @label Tablecolumn0
     * @associates <{it.saga.esicra.db.Column}>
     */
    protected List columnList;
    
    private Logger logger;

    private CompareLogger compLog;
    
    /**
     * Comment here
     * @supplierCardinality 0..*
     * @associates <{it.saga.esicra.db.PrimaryKey}>
     */
    protected List primaryKeyList;
    
    /**
     * Comment here
     * @supplierCardinality 0..*
     * @associates <{it.saga.esicra.db.ForeignKey}>
     */
     
    protected List foreignKeyList;
    /**
     * Comment here
     * @supplierCardinality 0..*
     * @associates <{it.saga.esicra.db.Index}>
     */
    protected List indexList;
    
    public Table(String schemaName , String tableName ){
        logger = Logger.getLogger(this.getClass());
        compLog = CompareLogger.getLogger();
        this.tableName=tableName.toUpperCase();
        this.schemaName=schemaName.toUpperCase();
        columnList = new ArrayList();
        primaryKeyList = new ArrayList();
        foreignKeyList = new ArrayList();
        indexList = new ArrayList();
    }
    
    public String getName(){
        return tableName;
    }
    
    public Table(String schemaName , String tableName , DatabaseMetaData meta){
        logger = Logger.getLogger(this.getClass());
        compLog = CompareLogger.getLogger();
        this.tableName=tableName.toUpperCase();
        this.schemaName=schemaName.toUpperCase();
        columnList = new ArrayList();
        primaryKeyList = new ArrayList();
        foreignKeyList = new ArrayList();
        indexList = new ArrayList();
        // column
        try{
            // colonne
            ResultSet rs = meta.getColumns("",schemaName,tableName,"%");
            while(rs.next()){
                String columnName =(String) rs.getObject("COLUMN_NAME");
                int type = rs.getInt("DATA_TYPE");
                int size = rs.getInt("COLUMN_SIZE");
                // ocio x oracle
                //boolean notNull = rs.getBoolean("IS_NULLABLE");
                int pos = rs.getInt("ORDINAL_POSITION");
                logger.debug("Colonna: "+columnName);
                columnList.add(new Column(columnName,pos, type, size));
            }
            rs.close();
            // primary keys
            rs = meta.getPrimaryKeys("",schemaName,tableName);
            while(rs.next()){
                String columnName =rs.getString("COLUMN_NAME");
                int seq = rs.getInt("KEY_SEQ");
                String name = rs.getString("PK_NAME");
                primaryKeyList.add(new PrimaryKey(name,columnName,seq));
            }
            Collections.sort(primaryKeyList,new CompPk());
            rs.close();
            // foreign keys
            rs = meta.getImportedKeys("",schemaName,tableName);
            while(rs.next()){
                String name =rs.getString("FK_NAME");
                String foreignTable =rs.getString("PKTABLE_NAME");
                String localColumn =rs.getString("PKCOLUMN_NAME");
                String foreignColumn =rs.getString("FKCOLUMN_NAME");
                // Tipo 
                int seq = rs.getInt("KEY_SEQ");
                foreignKeyList.add(new ForeignKey(name,foreignTable,localColumn,foreignColumn,seq));
            }
            rs.close();
            //indexes
            rs = meta.getIndexInfo("",schemaName,tableName,true,false);
            while(rs.next()){
                String indexName = rs.getString("INDEX_NAME");
                String columnName =rs.getString("COLUMN_NAME");
                boolean unique = rs.getBoolean("NON_UNIQUE");
                String sort = rs.getString("ASC_OR_DESC");
                int pos =rs.getInt("ORDINAL_POSITION");
                // salta indici farloccchi di oracle 
                if(indexName!=null){
                    indexList.add(new Index(indexName,columnName,pos, unique,sort));
                }
            }
            rs.close();
        }catch(SQLException e){
            logger.debug("Errore SQL "+e);
        }
    }
    
    public Element toXml() {
        //ArrayList collection = new ArrayList();
        Element element =new Element("table");
        element.setAttribute("name",tableName.toUpperCase());
        //tabelle
        if(columnList==null) return null;
        Iterator ite = columnList.iterator();
        Column col=null;
        PrimaryKey pkey = null;
        ForeignKey fkey = null;
        Index idx =null;
        while(ite.hasNext()){
            col = (Column) ite.next();
            element.addContent(col.toXml());
            //collection.add(element);
            //sb.append(tab.toXml());
        }
        ite = primaryKeyList.iterator();
        while(ite.hasNext()){
            pkey = (PrimaryKey) ite.next();
            element.addContent(pkey.toXml());
        }
        ite = foreignKeyList.iterator();
        while(ite.hasNext()){
            fkey = (ForeignKey) ite.next();
            element.addContent(fkey.toXml());
        }
        ite = indexList.iterator();
        while(ite.hasNext()){
            idx = (Index) ite.next();
            element.addContent(idx.toXml());
        }
        return element;
    }
    
    public void  fromXml(Element  node){
        // columns
        List list = node.getChildren("column");
        Iterator ite = list.iterator();
        Element e =null;
        while(ite.hasNext()){
            e = (Element) ite.next();
            try{
                String name = e.getAttribute("name").getValue();
                int pos = e.getAttribute("pos").getIntValue();
                int type = e.getAttribute("type").getIntValue();
                int size = e.getAttribute("size").getIntValue();
                Column col = new Column(name,pos,type,size);
                columnList.add(col);
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        // foreign-key
        list = node.getChildren("foreign-key");
        ite = list.iterator();
        e =null;
        while(ite.hasNext()){
            e = (Element) ite.next();
            try{
                String name = e.getAttribute("name").getValue();
                String table = e.getAttribute("foreignTable").getValue();
                Element ref = e.getChild("reference");
                String local = ref.getAttribute("localColumn").getValue();
                String foreign = ref.getAttribute("foreignColumn").getValue();
                int ord = ref.getAttribute("ord").getIntValue();
                ForeignKey fk = new ForeignKey(name,table,local,foreign,ord);
                foreignKeyList.add(fk);
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        // primary-key
        list = node.getChildren("primary-key");
        ite = list.iterator();
        e =null;
        while(ite.hasNext()){
            e = (Element) ite.next();
            try{
                String name = e.getAttribute("name").getValue();
                String column = e.getAttribute("column").getValue();
                int ord = e.getAttribute("ord").getIntValue();
                PrimaryKey pk = new PrimaryKey(name,column,ord);
                primaryKeyList.add(pk);
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        Collections.sort(primaryKeyList,new CompPk());
        // index
        list = node.getChildren("index");
        ite = list.iterator();
        e =null;
        while(ite.hasNext()){
            e = (Element) ite.next();
            try{
                String name = e.getAttribute("name").getValue();
                String column = e.getAttribute("column").getValue();
                int pos = e.getAttribute("pos").getIntValue();
                Index idx = new Index(name,column,pos,false,"");
                indexList.add(idx);
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
    }
    
    public int compareTo(Object o){
        Table t1 = this;
        Table t2 = (Table) o;
        
        // columns
        if(t1.columnList.size()!=t2.columnList.size()){
            compLog.log("Numero colonne differente");
            return t1.columnList.size()-t2.columnList.size();
        }
        int len =t1.columnList.size();
        for (int i=0;i<len;i++){
            Column c1=(Column)t1.columnList.get(i);
            Column c2=(Column)t2.columnList.get(i);
            if(c1.compareTo(c2)!=0){
                return -1;
            }
        }
        // primary key
        if(t1.primaryKeyList.size()!=t2.primaryKeyList.size()){
            compLog.log("Numero chiavi primarie differente");
            //return t1.primaryKeyList.size()-t2.primaryKeyList.size();
        }
        len =t1.primaryKeyList.size();
        for (int i=0;i<len;i++){
            PrimaryKey pk1=(PrimaryKey)t1.primaryKeyList.get(i);
            PrimaryKey pk2=(PrimaryKey)t2.primaryKeyList.get(i);
            if(pk1.compareTo(pk2)!=0){
                return -1;
            }
        }
        // index
        if(t1.indexList.size()!=t2.indexList.size()){
            compLog.log("Numero indici differente");
            // return t1.indexList.size()-t2.indexList.size();
        }
        len=t1.indexList.size();
        for (int i=0;i<len;i++){
            Index idx1=(Index)t1.indexList.get(i);
            Index idx2=(Index)t2.indexList.get(i);
            if(idx1.compareTo(idx2)!=0){
                compLog.log("Indice  "+idx1);
                //return -1;
            }
        }
        // foreign key
        if(t1.foreignKeyList.size()!=t2.foreignKeyList.size()){
            compLog.log("Numero chiavi esterne differente  " + "table1="+t1.foreignKeyList.size()+
                " table2="+t2.foreignKeyList.size());
            return t1.foreignKeyList.size()-t2.foreignKeyList.size();
        }
        len =t1.foreignKeyList.size();
        for (int i=0;i<len;i++){
            ForeignKey fk1=(ForeignKey)t1.foreignKeyList.get(i);
            ForeignKey fk2=(ForeignKey)t2.foreignKeyList.get(i);
            if(fk1.compareTo(fk2)!=0){
                compLog.log("ForeignKey "+fk1.fkName +"<>"+fk2.fkName);
                //return -1;
            }
        }
        return 0;
    }    
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("name="+this.getName());
        sb.append(", ");
        sb.append("len="+columnList.size());
        sb.append(" cols=[");
        int len = columnList.size();
        for(int i = 0 ; i<len;i++){
            sb.append(((Column)columnList.get(i)).columnName);
            if(i!=len-1) sb.append(", ");
        }
        sb.append("]");
        sb.append("]");
        return sb.toString();
    }
    
    class CompPk implements Comparator {
        public int compare(Object o1, Object o2){
            PrimaryKey p1 = (PrimaryKey) o1;
            PrimaryKey p2 = (PrimaryKey) o2;
            return p1.ord-p2.ord;
        }
    };
    
    class CompFk implements Comparator {
        public int compare(Object o1, Object o2){
            ForeignKey p1 = (ForeignKey) o1;
            ForeignKey p2 = (ForeignKey) o2;
            return p1.ord-p2.ord;
        }
    };
     
}
