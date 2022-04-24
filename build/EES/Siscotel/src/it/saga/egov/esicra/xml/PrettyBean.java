package it.saga.egov.esicra.xml;

/**
 *  Classe per stampare un bean in formato leggibile
 */
public class PrettyBean  {
 
  public static String format(Object o){
     return format(o.toString());
  }
 
  public static String format(String str){
    StringBuffer sb = new StringBuffer();
    Pila stack = new Pila();
    int nc=0;
    int wc =0 ;
    int t= 0 ;
    for(int i=0 ;i<str.length();i++){
      char c = str.charAt(i);
      if(c=='['||c=='{'){
        stack.push(wc);
        wc=0;
        nc++;
        wc++;
        sb.append(c);
      }else{
        if(c==']'||c=='}'){
          nc -= wc ;
          wc=stack.pop();
          sb.append('\n');
          sb.append(tab(nc));
          sb.append(c);
        }else if(c==','){
          nc -= wc ;
          sb.append(",\n");
          sb.append(tab(nc));
          wc=0;
        }
        else{
          nc++;
          wc++;
          sb.append(c);
        }
      }
      
    }
    return sb.toString();
  }
  
   private static String tab(int n){
      StringBuffer sb = new StringBuffer();
      for(int i=0;i<n;i++)
        sb.append(' ');
      return sb.toString();
   }
  
  public static void main(String[] args) {
    String ex1 = "[ CodiceFiscale=PNLGNN77H8763HJZ, Cognome=Pinoli, Nome=Gianni Giovanni, Sesso=M, DataNascita=1960-05-20, ComuneNascita= [ CodIstatComune = null, DesComune = MILANO, DesProvincia = MI ] , LocalitaNasciat=[codIstatStato=null,desStato=null,desLocalita=null,desContea=], Residenza= [ SpecieArea = null, DesArea = Via Ugo Fosnco, NumCiv = 12, Lettera Civica = B, Scala = Sc1, Interno = Int1, Cap = 99999, DataInizio = null, DataFine = null, Comune =  [ CodIstatComune = null, DesComune = COMUNEAMICO, DesProvincia = BG ] , Localita = [codIstatStato=null,desStato=null,desLocalita=null,desContea=null] ] , IdEnte=8240 ]";
    String ex2 ="[ pepperon [a , b ,c , d , e ]  pippo, pluto = { ciccio , pasticcio=[bao , miao , ciao]} , paperino , carmelo=[panino,pizza,mozzarella]]";
    System.out.println(PrettyBean.format(ex2));
  }
  
}
