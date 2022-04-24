<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="
it.saga.siscotel.db.*,
it.saga.siscotel.db.test.*,
it.saga.siscotel.db.hibernate.*,
java.util.*,
it.saga.egov.esicra.*,
org.hibernate.*,
org.hibernate.cfg.* "
%>

<H1>Hibernate Test Rattuso</H1>

<%
Session ss = HibernateUtil.currentSession();
    
    Transaction tx = ss.beginTransaction();
    try{
      String q = "from VIndiceSoggetto";
      Query query = ss.createQuery(q);
      System.out.println(query.getQueryString());
      Iterator ite = query.iterate();
      VIndiceSoggetto tc = null;
      out.println("<OL>");
      while(ite.hasNext()){
        tc = (VIndiceSoggetto) ite.next();
        out.println("<LI>");
        out.println(tc.getCognome()+" "+tc.getDenominazione()+" "+ tc.getDataNascita());
        out.println("</LI>");
      }
      out.println("</OL>");
      HibernateUtil.closeSession();
    }catch(Exception e){
      out.println(e);
    }

%>

