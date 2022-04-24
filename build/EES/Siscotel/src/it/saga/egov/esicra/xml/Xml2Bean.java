package it.saga.egov.esicra.xml;

/**
 *  Deserializza un Bean partendo dall'xml
 *  
 *  
 *  Il nome della classe è recuperato come attributo dell'elemento "class"
 *  Il mome dell'attributo della classe è memorizzato come attributo "attr"
 *  Per gli array di bean è previsto un attributo "array" contenente la dimensione
 *  dell'array.
 *  La deserializzazione funziona anche in molti casi in cui cambi la struttura
 *  del bean da deserializzare.
 *  <p>
 *  1) Aggiunta di nuovi attributi<p>
 *  2) Cancellazione di attributi <p>
 *  3) Modifica del tipo di un attributo <br>
 *      in questo caso è necessario mantenere il metodo di set per il vecchio tipo 
 *      che realizzi la logica di conversione al nuovo tipo dell'attributo
 *  <br><b>esempio:<b> se in un bean l' attributo <b>Stato</b> di tipo <i>String</i> viene 
 *     modificato in <i>Integer</i> occorre mantenere un metodo di set del tipo <br>
 *     <pre>
 *          public setStato(String stato){
 *              // il nuovo attributo è di tipo Integer
 *              this.stato= new Integer(stato);
 *          }
 *     </pre>
 *  
 *  @author OS
 */

import it.saga.egov.esicra.xml.test.*;

import java.lang.NoSuchMethodException;
import java.lang.reflect.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.sql.Date;

public class Xml2Bean  {

  SAXBuilder builder = null;
  Document doc=null;
  Object bean=null;
  
  /**
   *  Costruttore della classe , riceve in ingresso la stringa xml contenente i
   *  dati e l' oggetto bean (Vuoto)
   *  @param    xmlString stringa xml da deserializzare
   *  @param    bean , oggetto da cui ottenere la struttura
   */
  public Xml2Bean(String xmlString, Object bean) throws Exception  {
    // properties Sax factory oracle per JDOM
    //System.setProperty("javax.xml.parsers.SAXParserFactory","oracle.xml.jaxp.JXSAXParserFactory");
    System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl");
    
    // crea dom parser  
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try{
      builder = new SAXBuilder();
      builder.setValidation(false);
      builder.setIgnoringElementContentWhitespace(true); 
      factory.setValidating(false);
      StringReader reader = new StringReader(xmlString);
      InputSource source = new InputSource(reader);
      doc = builder.build(source);
      this.bean = toBean(doc.getRootElement(), bean);
    }catch(JDOMException e){
      e.printStackTrace();
      throw(e);
    }catch(IOException e){
      e.printStackTrace();
      throw(e);
    }catch(ClassNotFoundException e){
      e.printStackTrace();
      throw(e);
    }catch(NoSuchMethodException e){
      e.printStackTrace();
      throw(e);
    }
  }

  /**
   *  Metodo ricorsivo per il riempimento dei bean
   *  
   *  @param elem Elemento xml contenente i dati
   *  @param o Oggetto da riempire
   *  @return bean ripieno
   */
  Object toBean(Element elem, Object o) throws ClassNotFoundException,NoSuchMethodException{
    if(o==null)
      return null;
    Class classe = o.getClass();
    if(TipiSemplici.isPrimitive(classe)){
      // assegna il valore
      try{
        String val =elem.getText();
        Object obj = TipiSemplici.costruttoreSemplice(classe,val);
        return obj;
        //crea classe partendo dal costruttore
      }catch(Exception e){
        e.printStackTrace(); 
      }
    }
    // la classe è un Bean
    // cerca elemento xml associato
    //String tag = Bean2Xml.nomeClasse(classe);
    List list = elem.getChildren();
    Element nodo = null;
    for(int i=0;i<list.size();i++){
      nodo = (Element)list.get(i);
      String str = nodo.getName();
      String cls=nodo.getAttribute("class").getValue();
      Class cl = Class.forName(cls);
      //String arr=nodo.getAttribute("array").getValue();
      Attribute arr=nodo.getAttribute("array");
      Attribute aa = nodo.getAttribute("attr");
      String attr=null;
      if (aa!=null)
        attr=nodo.getAttribute("attr").getValue();
      Field fd =null;
      if (attr!=null)
        fd=cercaCampo(classe,attr);
      else 
        continue;
      /*
      try{
        if (attr!=null)
          fd = classe.getDeclaredField(attr);
        else
          continue;
      }catch(NoSuchFieldException e){
        e.printStackTrace();
      }
      */
      if(fd!=null){
          //Class cl = Class.forName(cls);
          Object ret=null;
          if(arr!=null){
              // legge dimensione array
              int len= Integer.parseInt(arr.getValue());
              // istanzia Array
              Object array = Array.newInstance(Class.forName(cls),len);
              // legge elementi child del nodo
              List childs = nodo.getChildren();
              for(int j=0;j<childs.size();j++){
                 Element ev = (Element) childs.get(j);
                  Object sel=null;
                 try{
                  sel = Class.forName(cls).newInstance();
                 }catch(Exception e){
                   e.printStackTrace();
                 }
                 sel=toBean(ev,sel);
                 Array.set(array,j,sel);
              }
              ret=array;
          }else{
            Object so = TipiSemplici.costruttoreSemplice(cl);
            ret = toBean(nodo,so);
          }   
          // setta il campo
          try{
          String nome = Bean2Xml.maiuscola(fd.getName());
          String method_name="set"+nome;
          Method m= classe.getMethod(method_name,new Class[]{ret.getClass()});
          Object nullo= m.invoke(o,new Object[]{ret});
          }catch(NoSuchMethodException me){
            throw(me);
          }catch(Exception e){
              e.printStackTrace();
          }
      }
    }
    return o;  
  }

  public String  toString(){
    return bean.toString();
  }
  
  /**
   *  Cerca il campo associato ad un particolare elemento xml
   *  ricercandolo nelle eventuali classi padre in caso di ereditarieta'
   *  @param  classe 
   *  @param  attributo
   *  @return  campo
   */
  Field cercaCampo(Class classe , String nomeAttr){
    try{
      if(classe.isInstance(new Object())){
        // il campo non è stato trovato
        return null;
      }
      Field field=classe.getDeclaredField(nomeAttr);
      return field;
    }catch(NoSuchFieldException e){
        //e.printStackTrace();
        // campo non trovato
        Class superclasse = classe.getSuperclass();
        return cercaCampo(superclasse,nomeAttr);
    }
  }
  
  /*
   *  Cerca la classe associata ad un particolare elemento xml
   *  @param  obj
   *  @return restituisce la classe
   */
  /*
  Class cercaClasse(Object obj,String nomeElem){
    Class classe=obj.getClass();
    Field[] fields = classe.getDeclaredFields();
    for(int i=0;i<fields.length;i++){
      Field field=fields[i];
      String nomeCampo = field.getName();
      Class tc = field.getType();
      // la classe non è un tipo semplice
      if(TipiSemplici.isPrimitive(tc)){
        if(nomeCampo.equalsIgnoreCase(nomeElem))
          return tc;
      }
      String nomeBean = Bean2Xml.nomeClasse(tc);
      if(nomeBean.equalsIgnoreCase(nomeElem)){
        try{
          return tc;
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    }
    return null;
  }
  */
  
  /**
   *  Classe statica di utilità per comporre il nome della classe bean
   */
  static String nomeClasse(String Nome){
    return  Nome+"Bean";
  }

  /**
   *  Rimuovi package , il prefisso e il suffisso dal nome della classe
   */
  private static String nomeElemento(String nomeClasse){
    int pos=nomeClasse.lastIndexOf('.');
    String nome= nomeClasse.substring(pos+1,nomeClasse.length());
    String str = nome.substring(3,nomeClasse.length()-4);
    return str;
  }

  public Object getBean(){
    return bean;
  }

  /**
   *    Test1 istanzia un bean di tipo PraPresidentiScrutatoriBean , lo serializza
   *    in xml e lo deserializza.
   */
  private static void test1() throws Exception{
  
    PraPresidentiScrutatoriBean bean = PraPresidentiScrutatoriBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST1:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST1:"+ x2b.getBean());
    
  }

  /**
   *    Test 2 (test deserializzazione) , viene letto un xml e deserializzato
   *    in un bean con un campo in più
   */
  private static void test2() throws Exception{
    String FILE_NAME="C:/oracle/oradev/jdev/mywork/Esicra/EES/Esicra/test/test2_xml2bean.xml";
    String line="";
    FileReader fr = new FileReader(new File(FILE_NAME));
    System.out.println("File:"+fr);
    LineNumberReader reader = new LineNumberReader(fr);
    StringBuffer sb = new StringBuffer();
    while((line=reader.readLine())!=null){
        sb.append(line+"\n");
    }
    System.out.println();
    String str = sb.toString();
    System.out.println("XML TEST2:\n"+str);
    PraPresidentiScrutatoriBean bean = new PraPresidentiScrutatoriBean();
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST2:"+ x2b.getBean());
  }

  /**
   *    Test 3 per (test deserializzazione) , viene letto un xml  privo di un elemento 
   *    e deserializzato in un bean , l'atributo mancante resta a null
   */  
  private static void test3() throws Exception{
    String FILE_NAME="C:/oracle/oradev/jdev/mywork/Esicra/EES/Esicra/test/test3_xml2bean.xml";
    String line="";
    FileReader fr = new FileReader(new File(FILE_NAME));
    System.out.println("File:"+fr);
    LineNumberReader reader = new LineNumberReader(fr);
    StringBuffer sb = new StringBuffer();
    while((line=reader.readLine())!=null){
        sb.append(line+"\n");
    }
    System.out.println();
    String str = sb.toString();
    System.out.println("XML TEST3:\n"+str);
    PraPresidentiScrutatoriBean bean = new PraPresidentiScrutatoriBean();
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST3:"+ x2b.getBean());
  }

  /**
   *    Test 4 per (test deserializzazione) , viene letto un xml in cui è stato cambiato
   *    il tipo ad un attributo . E' necessario prevedere un metodo di set che realizzi
   *    la conversione del vecchio elemento reso persistente
   */    
  private static void test4() throws Exception{
    String FILE_NAME="C:/oracle/oradev/jdev/mywork/Esicra/EES/Esicra/test/test4_xml2bean.xml";
    String line="";
    FileReader fr = new FileReader(new File(FILE_NAME));
    System.out.println("File:"+fr);
    LineNumberReader reader = new LineNumberReader(fr);
    StringBuffer sb = new StringBuffer();
    while((line=reader.readLine())!=null){
        sb.append(line+"\n");
    }
    System.out.println();
    String str = sb.toString();
    System.out.println("XML TEST3:\n"+str);
    PraPresidentiScrutatoriBean bean = new PraPresidentiScrutatoriBean();
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST3:"+ x2b.getBean());
  }

  /**
   *    Test5 su ereditarietà di bean semplice, istanzia un bean di tipo ComuneEstesoBean ,
   *    lo serializza in xml e lo deserializza.
   */
  private static void test5()throws Exception{
  
    ComuneEstesoBean bean =(ComuneEstesoBean) ComuneEstesoBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST5:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST5:"+ x2b.getBean());
  }  

  /**
   *    Test6 su ereditarietà di bean compless , istanzia un SoggettoEstesoiBean 
   *    lo serializza in xml e lo deserializza.
   */
  private static void test6()throws Exception{
  
    SoggettoEstesoBean bean =(SoggettoEstesoBean) SoggettoEstesoBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST6:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST6:"+ x2b.getBean());
  }

  /**
   *    Test7 su ereditarietà di bean compless , istanzia un PraPresidentiScrutatoriBean 
   *    lo serializza in xml e lo deserializza.
   */
  private static void test7()throws Exception{
  
    PraPresidentiScrutatoriBean bean = PraPresidentiScrutatoriEstesoBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST7:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST7:"+ x2b.getBean());
  }

  /**
   *    Test8 su RateBollettaBean 
   */
  private static void test8()throws Exception{
  
    RateBollettaBean bean = RateBollettaBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST8:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST8:"+ x2b.getBean());
  }

   /**
   *    Test9 su pagBollettaRate 
   */
  private static void test9()throws Exception{
  
    PagBollettaRataBean bean = PagBollettaRataBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST9:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST9:"+ x2b.getBean());
  }

     /**
   *    Test10 su LocalitaBeanNull
   */
  private static void test10()throws Exception{
  
    LocalitaBean bean = LocalitaBean.testNull();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST10:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST10:"+ x2b.getBean());
  }

  /**
   *    Test11 su RateBollettaBean , per Float e Double
   */
  private static void test11()throws Exception{
  
    RateBollettaBean bean = RateBollettaBean.test();
    Bean2Xml b2x = new Bean2Xml(bean);
    String str = b2x.toString();
    System.out.println("XML TEST11:\n"+ str);
    Xml2Bean x2b= new Xml2Bean(str,bean);
    System.out.println("BEAN TEST11:"+ x2b.getBean());
  }
  
  public static void main(String[] args) throws Exception{
    //Xml2Bean.test1();
    //Xml2Bean.test2();
    //Xml2Bean.test3();
    //Xml2Bean.test4();
    //Xml2Bean.test5();
    //Xml2Bean.test6();
    //Xml2Bean.test7();
    //Xml2Bean.test8();
    //Xml2Bean.test9();
    //Xml2Bean.test10();
    Xml2Bean.test11();
  }
}
