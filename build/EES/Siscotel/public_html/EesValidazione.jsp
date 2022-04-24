<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*" %>
<%@ page import="it.saga.egov.esicra.*" %>
<%@ page import="it.saga.egov.esicra.importazione.*" %>

<%@ page import="java.lang.reflect.*" %>
<html>
<head>
<link rel=stylesheet href="timer.css" type="text/css">
<title>
Esicra Login
</title>
</head>
<body>

<%  
    EsicraConf.configura();
    String dirName  = System.getProperty("esicra.import.dir");
    File dir = new File(dirName);
    ElencoFilesImportazione elenco = new ElencoFilesImportazione();
    String doc="";
    String error="";
    String sigla="";
    String nomefile=request.getParameter("nomefile");
    if(nomefile!=null&&nomefile.length()>0){
        File sorgente = new File(dir, nomefile);
        sigla = elenco.getSigla(nomefile);
        Class classe = elenco.getClasseImportazione(sigla);
        out.println("sorgente "+sorgente);
        out.println("sorgente "+classe);
    
        Constructor costruttore = classe.getDeclaredConstructor(
            new Class[]{ sorgente.getClass() });
        out.println("costruttore "+costruttore);
        // istanza la classe tramite reflection 
    
        ImportParser parser = (ImportParser )costruttore.newInstance(
            new Object[]{sorgente});
        out.println(parser);    
        /*
        Validatore validatore = new Validatore();
        String progressivo=request.getParameter("progressivo");
        if(progressivo!=null && progressivo.length()==0){
            progressivo=(String)request.getSession().getAttribute("progressivo");
        }else{
            request.getSession().setAttribute("progressivo",progressivo);
        }
        int prog=0;
        try{
            prog=Integer.parseInt(progressivo);
        }catch(Exception e){
            prog=0;
        }
        if(prog>0){
            doc=validatore.cerca(prog);
            errore=validatore.errore();
        }
        /* */
    }
%>
<h1 align="center">
Esicra Validazione Xml
</h1>
<br/><br/><br/>
<form action="EesValidazione.jsp" method="post">
<table >
<tr>
    <td>
    <table><tr>
    <td>
    <b>File importazione: </b>
     <select  name="nomefile">
        <option selected ></option>
        <%  
            String[] lista = elenco.getListaFiles();
            String selected="";
            for(int i=0;i<lista.length;i++){
                if(lista[i].equals(nomefile)){
                    selected="selected";
                }else {
                    selected="";
                }
                out.println("<option "+selected+">"+lista[i]+ "</option>");          
            }
        %>
    </select>
    </td>
    <td>
    <b>Progressivo : </b><input type="text" value="">
    </td>
    <td> <input type="submit" value="Cerca"></td>
    <td> <input type="submit" value="Valida"></td>
    </tr>
    </table>
    </td>
</tr>
<tr>
<td>
    Nomefile: <%=nomefile %> 
    Sigla: <%=sigla%>
</td>
</tr>
<tr><td>
    <textarea name="xmldoc" cols=100 rows=20 wrap=off readonly="false" >
    <%=doc%>
    </textarea>
    </td>
</tr>
<tr><td>
    <textarea name="message"  cols=100 rows=6 wrap=off readonly="true" >
        <%=error%>
    </textarea>
    </td>
</tr>
</table>
</form>
</body>
</html>
