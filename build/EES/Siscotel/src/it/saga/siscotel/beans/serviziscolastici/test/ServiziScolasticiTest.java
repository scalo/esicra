package it.saga.siscotel.beans.serviziscolastici.test;

import it.saga.egov.esicra.xml.PrettyBean;
import it.saga.siscotel.beans.serviziscolastici.*;

public class ServiziScolasticiTest{

  public static void main(String[] args) {
  
  
    System.out.println(PrettyBean.format(PraIscrizioneMensaBean.test())); 
    System.out.println(PrettyBean.format(PraRecessoMensaBean.test())); 
    System.out.println(PrettyBean.format(PraIscrizioneTrasportoBean.test())); 
    System.out.println(PrettyBean.format(PraRecessoTrasportoBean.test())); 
    System.out.println(PrettyBean.format(PraIscrizioneCentroBean.test())); 
    System.out.println(PrettyBean.format(PraRecessoTrasportoBean.test())); 
  }

}
