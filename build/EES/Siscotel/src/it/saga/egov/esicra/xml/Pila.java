package it.saga.egov.esicra.xml;

  /**
   * 
   * TODO controlli sui limiti
   * 
   */
  public class Pila{
    
    final static int MAX = 1000;
    int[] pila ;
    
    int lev; 
    
    public Pila(int nMax){
      pila = new int[nMax];
      lev=0;
    }
    
    public Pila(){
      this(MAX);
    }
    
    public void push(int v){
      pila[lev]=v;
      lev ++;
    }
    
    public int pop(){
      int v = pila[--lev];
      return v;
    }
    
    public static void main(String[] args) {
      Pila p = new Pila();
      p.push(4);
      p.push(5);
      p.push(6);
      p.push(9);
      System.out.println(p.pop());
      System.out.println(p.pop());
      System.out.println(p.pop());
      System.out.println(p.pop());
    }
    
    
  }