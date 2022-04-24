package it.saga.egov.esicra.xml;

import java.lang.reflect.*;
import java.util.*;

public class FindUsedBeans 
{
  private static HashSet set;
  
  public FindUsedBeans()
  {
  }
  
  private static void checkBean()
  { 
    HashMap hmMethod = new HashMap();
    HashMap hmConst = new HashMap();
    
    try
    {
      Iterator item = set.iterator();
      
      while(item.hasNext())
      { 
        Class obj = Class.forName(item.next().toString());
        Field[] arrField = obj.getDeclaredFields();  
  
        for(int k=0; k<arrField.length; k++)
        {
          if(!arrField[k].isAccessible())
          {
            StringBuffer sb = new StringBuffer(arrField[k].getName());
            sb.setCharAt(0,Character.toUpperCase(sb.charAt(0)));
          
            String getter = "get" + sb;
            String setter = "set" + sb;
            
            hmMethod.put(getter,arrField[k].getName());
            hmMethod.put(setter,arrField[k].getName());          
          }
        }
  
        Constructor[] arrCons = obj.getConstructors();
        for(int j=0; j<arrCons.length; j++)
        {
           if(arrCons[j].getParameterTypes().length == 0)        
            hmConst.put(obj.getName(),arrCons[j].getName());
        }
        
        Method[] arr = obj.getDeclaredMethods();
            
        for(int i=0; i<arr.length; i++)
        {
          Method method = ((Method)arr[i]);
          
          if(hmMethod.get(method.getName()) != null)
            hmMethod.remove(method.getName());
        }
      }
      
      if(hmConst.size() != set.size())
      {
        System.out.println("Verifica costruttore fallita!!!");
      }
      
      if(hmMethod.size() > 0)
      {
        System.out.println("Verifica Bean fallita!!!");
        
        Iterator itemKey = hmMethod.keySet().iterator();
        
        while(itemKey.hasNext())
        {
          Object key = itemKey.next();
          
          System.out.println("-> " + hmMethod.get(key) + " => " + key);
        }      
      }
    }
    catch(Exception err)
    {
      err.printStackTrace();
    }
  }
  
  public static void printListBeans(Class[] arrObj)
  { 
    HashSet hs = new HashSet();
    int count = 0;
    
    for(int i=0; i<arrObj.length; i++)
    {
      hs.addAll(Find(arrObj[i]));
    }

    Iterator iter = hs.iterator();
  
    while(iter.hasNext())
    {
      System.out.println("-> (" + count + ") = " + iter.next());
      count++;
    } 
  }  

  public static void printListBeans(Class obj)
  {
    int count = 0;
    
    Find(obj);
    checkBean();
    
    Iterator iter = set.iterator();

    while(iter.hasNext())
    {
      System.out.println("-> (" + count + ") = " + iter.next());
      count++;
    }    
  } 
    
  private static Set Find(Class obj)
  {
    if(set == null)
      set = new HashSet();
    
    set.add(obj.getName());
      
    try
    {
     Method[] arr = obj.getDeclaredMethods();
  
      for(int i=0; i<arr.length; i++)
      {
        Class rClass = arr[i].getReturnType();

        if(rClass.getName().endsWith("Bean") && !rClass.getName().equals(obj.getName()))
        {
          set.add(rClass.getName());
          Find(Class.forName(rClass.getName()));   
        }
      }
    }
    catch(Exception err)
    {
      System.out.println("Error: " + err.getMessage());  
    }
    
    return set;
  }
}
