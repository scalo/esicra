package it.saga.egov.esicra.timer.servlet.test;

public class ConversioneStringa  {
  public ConversioneStringa() {
  }

  public static boolean[] giorniSettimana(String settimana){
    boolean array[]=new boolean[7];
    for(int i=0;i<settimana.length();i++){
      char[] c = new char[1];
      c[0] = settimana.charAt(i);
      int n  = Integer.parseInt(new String(c));
      array[n]=true;
    }
    return array;
  }

  public static void main(String[] args) {
    String s="0256";
    System.out.println(s);
    boolean array[] = ConversioneStringa.giorniSettimana(s);
    for(int i=0;i<7;i++){
      System.out.println(i+" "+array[i]);
    }

  }
  
  
}