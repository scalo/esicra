<%@ page contentType="text/html;charset=windows-1252" errorPage="EesMenu.jsp.jsp"%>
<html>
<head>
<link rel=stylesheet href="esicra.css" type="text/css">
<title>
Esicra Login
</title>
<script type="text/javascript" language="Javascript">
var JSver = "1.0"
</script>
<script type="text/javascript" Language="JavaScript1.1">
JSver = "1.1"
</script>
<script type="text/javascript" Language="JavaScript1.2">
JSver = "1.2"
</script>
<script type="text/javascript" Language="JavaScript1.3">
JSver = "1.3"
</script>
<script type="text/javascript" Language="JavaScript1.4">
JSver = "1.4"
</script>
<script type="text/javascript" Language="JavaScript1.5">
JSver = "1.5"
</script>

<script type="text/javascript">
  
  function cookiesTest() {
    document.cookie = "test=cookiesEnabled";
    var pos = document.cookie.indexOf("test=");
    //alert(document.cookie);
    if( pos == -1 )
    {
        return false;
    }
    return true;
  }
  
</script>
</head>
<body>
<!-- titolo -->
<br>
<TABLE border="0" width="100%" cellpadding="" cellspacing="0" align="center" class="HelpStile" >
  <TR class="Titolo" height="20"> 
    <TD>&nbsp;&nbsp;Login</TD>
  </TR>
  <TR class="HelpRigaSezione" >
  <TD height="1"></TD>
  </TR>   
  <TR  class="HelpRigaTitoloSezione" height="20">
    <TD class="HelpRigaTitoloRight">&nbsp;&nbsp;</TD>
  </TR> 
</TABLE>
<br/>
<br/>
<br/>
<form action="j_security_check" method="post">
    <table align="center" class="box" cellpadding="10">
        <tr><td class="etichetta">User Name:</td><td> <input type="text" name="j_username" class="campi"/></td></tr>
        <tr><td class="etichetta">Password:</td><td><input type="password" name="j_password" class="campi"/></td></tr>
        <tr align="center">
        <td colspan=2 ><input type="submit" value="Login" class="pulsante"/></td>
        </tr>
    </table>
</form>
<br/>
<br/>

<table align=center class="footer">
  <tr>
    <td align="center">
    	<b>Javascript: </b><script>if(JSver.length>0)document.write(JSver)</script><noscript>No</noscript>,
    </td>	
    <td align="center">
    	<b>Cookies: </b><script>if(cookiesTest()) document.write("Si"); else document.write("No");</script><noscript>Non Testabile</noscript>,
    </td>
    <td align="center">
    	<b>Java: </b>
    	<script>
      try{
        var test=new java.lang.String("Si");
        document.write(test);
      }catch(e){
        //alert(e);
        document.write("No");
      }
      </script><noscript>Non Testabile</noscript>
    </td>
  </tr>
</table>

</body>
</html>
