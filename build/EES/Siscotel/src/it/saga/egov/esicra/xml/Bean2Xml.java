package it.saga.egov.esicra.xml;

/**
 *  Converte un bean complesso in un documento xml
 *  
 *  Il nome della classe è recuperato come attributo dell'elemento "class"
 *  Il mome dell'attributo della classe è memorizzato come attributo "attr"
 *  Per gli array di bean è previsto un attributo "array" contenente la dimensione
 *  dell'array.
 *  
 *  @author OS
 *  
 *  NB
 *  
 *  
 */

import it.saga.egov.esicra.xml.test.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.text.*;


public class Bean2Xml  {

  private DocumentBuilder builder;
  private DOMImplementation impl ;
  private Document doc;
  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  private String ENCODING="UTF-8";

  public Bean2Xml(Object bean,String enc) throws Exception {
    this(bean);
    ENCODING=enc;
  }
  
  public Bean2Xml(Object bean) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try{
      builder = factory.newDocumentBuilder();
      doc = builder.newDocument();
      Element root = toXml("root",bean);
      // Namespace
      root.setAttribute("xmlns","http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra");
      root.setAttribute("class",bean.getClass().getName());
      root.setAttribute("attr","root");
      doc.appendChild(root);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public  Element toXml(String nomeAttr, Object bean) throws NoSuchMethodException  {
    if(bean==null) return null;
    Class classe = bean.getClass();
    // trovato array 
    //Element lista = doc.createElement(capitalizza(nomeElemento));
    if(classe.isArray()){
      // se tipo semplice 
      String nome_elem =nomeClasse(bean.getClass());
     //String pop = bean.getClass().getComponentType().getName();
      Element lista = doc.createElement(nome_elem);
      lista.setAttribute("class",bean.getClass().getComponentType().getName());
      int len= Array.getLength(bean);
      lista.setAttribute("array",Integer.toString(len));
      if(nomeAttr!=null)
        lista.setAttribute("attr",nomeAttr);
      for(int i=0;i<len;i++){
         Object o = Array.get(bean,i); 
         Element item=null;
         if(o!=null){
           Class cb = o.getClass();
           String id="["+Integer.toString(i)+"]";
           Element elem=null;
           if(TipiSemplici.isPrimitive(cb)){
              elem=doc.createElement(cb.getName());
              if(cb.getName().equals("java.util.Date"))
                elem.appendChild(doc.createTextNode(sdf.format(o)));
              else
               elem.appendChild(doc.createTextNode(o.toString()));
           }else{
              elem = toXml(null,o);
           }elem.setAttribute("class",cb.getName());
           lista.appendChild(elem);
         }
      }
      return lista;
    }
    Element item=null;
    //Element element = doc.createElement(capitalizza(nomeElemento));
    Element element = doc.createElement(nomeClasse((bean.getClass())));
    element.setAttribute("class",bean.getClass().getName());
    if (nomeAttr!=null)
      element.setAttribute("attr",nomeAttr);
    
    // recupera attributi della superclasse (escluso Object)
    // prevedere la ricorsione
    Field[] fields =caricaCampi(classe);
    /*
    Class superclasse = classe.getSuperclass();
    Field[] fields = classe.getDeclaredFields();
    if((superclasse!=null)&&!(superclasse.isInstance(new Object()))){
      Field[] campiSuperclasse=superclasse.getDeclaredFields();
      // aggiungi campi alla lista
      Field[] temp = new Field[fields.length+campiSuperclasse.length];
      
      for(int j=0;j<campiSuperclasse.length;j++){
        temp[j]=campiSuperclasse[j];
      }
      int step=campiSuperclasse.length;
      for(int j=0;j<fields.length;j++){
        temp[step+j]=fields[j];
      }
      //System.arraycopy(fields,0,temp,temp.length,step+fields.length);
      fields=temp;
    }
    */
    for(int i=0;i<fields.length;i++){
      Field field=fields[i];
      int modifier = field.getModifiers();
      if(Modifier.isStatic(modifier)) continue;
      Class tc = field.getType();
      //tc.getName();
      if(TipiSemplici.isPrimitive(tc)){
        // toglie il package al nome
        try{
          String nome = field.getName();
          item=doc.createElement(maiuscola(nome));
          String method_name="get"+maiuscola(nome);
          Method m = classe.getMethod(method_name,null);
          Object o = m.invoke(bean,null);
          if(o!=null){  
            // se il tipo è data
            if (field.getType().getName().equals("java.util.Date")){
              item.setAttribute("class",field.getType().getName());
              item.setAttribute("attr",field.getName());
              item.appendChild(doc.createTextNode(sdf.format(o)));
            }
            /*
            else
            if (field.getType().getName().equals("java.lang.Boolean")){
              item.setAttribute("class",field.getType().getName());
              item.setAttribute("attr",field.getName());
              item.appendChild(doc.createTextNode("PIPPO"));
            }
            */
            else{ 
              item.appendChild(doc.createTextNode(o.toString()));
              if(field.getType().isArray())
                item.setAttribute("class",field.getType().getComponentType().getName());
              else
                item.setAttribute("class",field.getType().getName());
              item.setAttribute("attr",field.getName());
            }
            element.appendChild(item);
          }
        }catch(NoSuchMethodException me){
            throw(me);
        }catch(Exception e){
              e.printStackTrace();
        }
      }else{
        try{
          String nome = maiuscola(field.getName());
          String method_name="get"+nome;
          Method m= classe.getMethod(method_name,null);
          Object o= m.invoke(bean,null);
          // a bean nullo corrisponde elemento nullo che non va inserito
          Element e = toXml(field.getName(),o);
          if (e!=null) element.appendChild(e);
        }catch(NoSuchMethodException me){
            throw(me);
        }catch(Exception e){
              e.printStackTrace();
        }
      }
    }
    return element;
  }

  private Field[] caricaCampi(Class classe){
    Class superclasse = classe.getSuperclass();
    Field[] fields = classe.getDeclaredFields();
    if((superclasse!=null)&&!(superclasse.isInstance(new Object()))){
      //Field[] campiSuperclasse=superclasse.getDeclaredFields();
      Field[] campiSuperclasse=caricaCampi(superclasse);
      // aggiungi campi alla lista
      Field[] temp = new Field[fields.length+campiSuperclasse.length];
      for(int j=0;j<campiSuperclasse.length;j++){
        temp[j]=campiSuperclasse[j];
      }
      int step=campiSuperclasse.length;
      for(int j=0;j<fields.length;j++){
        int modifier = fields[j].getModifiers();
        if(!Modifier.isStatic(modifier)){
          temp[step+j]=fields[j];
        }
      }
      //System.arraycopy(fields,0,temp,temp.length,step+fields.length);
      fields=temp;
    }
    return fields;
  }
  
  /**
   *  Trasformazione di doc xml attraverso API jaxp
   */
  public String toString(){
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try{
      DOMSource domSource = new DOMSource(doc);
      StreamResult streamResult = new StreamResult(out);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer serializer = tf.newTransformer();
      // specificare il codepage ed indentazione
      serializer.setOutputProperty(OutputKeys.ENCODING,ENCODING);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");
      serializer.transform(domSource, streamResult);
      return out.toString(ENCODING);
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
  }

  /**
   *  Rimuove il package , il prefisso e il suffisso (Bean) dal nome completo
   *  della classe
   */
   static String nomeClasse(Class tc){
    String nome_elemento_completo="";
    if(tc.isArray())
        nome_elemento_completo= tc.getComponentType().getName();
    else
        nome_elemento_completo = tc.getName();
    // se e'  array
    int pos=nome_elemento_completo.lastIndexOf('.');
    String nome_elemento= nome_elemento_completo.substring(pos+1,nome_elemento_completo.length());
    return rimuoviSuffisso(nome_elemento);
  }
  
  /**
   *  Maiuscola la prima lettera della stringa
   */
  protected static String maiuscola(String str){
    StringBuffer sb = new StringBuffer(str);
    String nome = sb.toString();
    sb.setCharAt(0,Character.toUpperCase(sb.charAt(0)));
    return sb.toString();
  }

  /**
   *  Rimuove suffisso Bean alla classe
   */
   private static String rimuoviSuffisso(String str){
    int pos=str.indexOf("Bean");
    if(pos>0)
      return str.substring(0,pos);
    return str;
   }

   public static void test1() throws Exception{
    System.out.println(nomeClasse( Class.forName("it.saga.egov.esicra.xml.Bean2Xml")));
    //SoggettoBean b = SoggettoBean.test();
    PraPresidentiScrutatoriBean b = PraPresidentiScrutatoriBean.test();
    //PraPermessoZTLBean b = PraPermessoZTLBean.test();
    Bean2Xml b2x = new Bean2Xml(b);
    System.out.println(b2x);
   }

   public static void test2() throws Exception {
    ComuneEstesoBean b = (ComuneEstesoBean)ComuneEstesoBean.test();
    Bean2Xml b2x = new Bean2Xml(b);
    System.out.println(b2x);
   }

   public static void test3() throws Exception{
    SoggettoEstesoBean b = (SoggettoEstesoBean)SoggettoEstesoBean.test();
    Bean2Xml b2x = new Bean2Xml(b);
    System.out.println(b2x);
   }

  public static void test4() throws Exception{
    String enc="Cp1252";
    PraPresidentiScrutatoriBean b = PraPresidentiScrutatoriBean.test();
    Bean2Xml b2x = new Bean2Xml(b,enc);
    // scrive il file
    File file = new File("c:/Tipo_"+enc+".xml");
    try{
      FileWriter fw = new FileWriter(file);
      fw.write(b2x.toString());
      fw.close();
    }catch(Exception e){
      e.printStackTrace();
    }
    System.out.println(b2x);
   }


  public static void test5() throws Exception{
    String enc="ISO-8859-1";
    PraPresidentiScrutatoriBean b = PraPresidentiScrutatoriBean.test();
    Bean2Xml b2x = new Bean2Xml(b,enc);
    // scrive il file
    File file = new File("c:/Tipo_"+enc+".xml");
    try{
      FileWriter fw = new FileWriter(file);
      fw.write(b2x.toString());
      fw.close();
    }catch(Exception e){
      e.printStackTrace();
    }
    System.out.println(b2x);
   }
  
  
  /**
   *  Aggiunge  suffisso Bean alla classe
   */
  
  public static void main(String[] args) throws Exception{
    //Bean2Xml.test1();
    //Bean2Xml.test2();
    //Bean2Xml.test3();
    //Bean2Xml.test4();
    Bean2Xml.test5();
  }
}
