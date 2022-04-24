package it.saga.egov.esicra.importazione;

/**
 *      Classe di test per la conversione UTF nell' XML
 */
public class UtfTest extends ImportParser {

    public UtfTest() throws Exception  {
        super(null);
    }

    public void process(String doc) throws Exception{
        //nop
    }

    public void statistica(){
        //nop
    }

    public static void main(String[] args) {
        String orig="è là SOCIETÀ < àòè &";
        char c;
        for(int i=0;i<orig.length();i++){
            c=orig.charAt(i);
            System.out.print(c+"="+(int)c+"     ");
            if(needsEscape(c)){
                String s=toXmlEncoding(c);
                System.out.println("esc="+s);
            }else
                System.out.println(c);
        }
        System.out.println();
    }
    
}
