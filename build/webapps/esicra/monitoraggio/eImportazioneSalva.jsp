<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib"%>

<!--
  
  CONTROLLER
  
-->

<hib:session>

<hib:load 
  var="flusso"
  classname="it.saga.siscotel.db.SerImport"
  value="${param.pkid}"
/>

<c:if test='${param.azione=="elimina"}' >
    <!--
    <script type="text/javascript">
      alert("elimina <c:out value="${flusso}" /> ");
    </script>
    -->
    <hib:delete target="${flusso}" />
</c:if>
</hib:session>

<script type="text/javascript">
    <!-- ritorna alla pagina principale -->
    location.href = "eImportazioni.jsp";
</script>


