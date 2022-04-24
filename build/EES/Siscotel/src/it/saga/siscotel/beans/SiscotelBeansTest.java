package it.saga.siscotel.beans;

import it.saga.egov.esicra.xml.FindUsedBeans;
import it.saga.siscotel.beans.base.PraticaBean;
import it.saga.siscotel.beans.serviziscolastici.*;

public class SiscotelBeansTest  {
  
  
  public static void main(String[] args) {
    PraticaBean p = new PraticaBean();
    Class[] cls = {PraIscrizioneMensaBean.class};
    FindUsedBeans.printListBeans(cls);
  }
  
  
}