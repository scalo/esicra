<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!-- header -->

<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR > 
    <TD class="HelpRigaTitolo">&nbsp;&nbsp;<c:out value="${param.titolo_pagina}" /></TD>
  </TR>
  <TR class="HelpRigaSezione"  >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" >
    <TD class="HelpRigaTitoloRight" > <c:out value="${param.titolo_contenuto}" />&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>
<br/>
