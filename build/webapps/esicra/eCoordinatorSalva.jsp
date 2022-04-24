<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>

<!--
  
  CONTROLLER
  
  forse è meglio usare le jsp standard invece che jstl
  

<script type="text/javascript">
  alert("azione=<c:out value="${param.azione}" />");
</script>

-->

<hib:session>

  <c:if test='${param.azione=="elimina"}' >
    <!--
    <script type="text/javascript">
      alert("elimina <c:out value="${tabella}" /> ");
    </script>
    -->
    <hib:load 
    var="tabella"
    classname="it.saga.siscotel.db.SerCoordinator"
    value="${param.pkid}"
    />
    <hib:delete target="${tabella}" />
  </c:if>
  
  <%
    //pageContext.setAttribute("timestamp",new Long(System.currentTimeMillis()));
    
  %>
  
  <jsp:useBean id="timestamp" class="java.util.Date" />
  
  <c:if test='${param.azione=="crea"}' >
      <jsp:useBean id="tabella" class="it.saga.siscotel.db.SerCoordinator" />
      <c:set target="${tabella}" property="nomeTabella" value="${param.nomeTabella}" />
      <c:set target="${tabella}" property="ordine" value="${param.ordine}" />
      <c:set target="${tabella}" property="flBidir" value="${param.flBidir}" />
      <c:set target="${tabella}" property="flAllinea" value="${param.flAllinea}" />
      <c:set target="${tabella}" property="flCancellabile" value="${param.flCancellabile}" />
      <c:set target="${tabella}" property="dtMod" value="${timestamp}" />
      <hib:save target="${tabella}" />
  </c:if>
  
  <c:if test='${param.azione=="modifica"}' >
    
    <hib:load 
      var="tabella"
      classname="it.saga.siscotel.db.SerCoordinator"
      value="${param.pkid}"
    />

    <!--
    <script  type="text/javascript">
      alert("salva");
    </script>
    -->
    <c:set target="${tabella}" property="nomeTabella" value="${param.nomeTabella}" />
    <c:set target="${tabella}" property="ordine" value="${param.ordine}" />
    <c:set target="${tabella}" property="flBidir" value="${param.flBidir}" />
    <c:set target="${tabella}" property="flAllinea" value="${param.flAllinea}" />
    <c:set target="${tabella}" property="flCancellabile" value="${param.flCancellabile}" />
    <hib:save target="${tabella}" />
  </c:if>
  
  <script type="text/javascript">
      <!-- ritorna alla pagina principale -->
      location.href = "eCoordinator.jsp";
  </script>

</hib:session>

</body>
</html>