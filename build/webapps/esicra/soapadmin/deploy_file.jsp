<%@ page import="java.io.*, java.util.*, org.w3c.dom.*" %>
<%@ page import="org.apache.soap.Constants,org.apache.commons.fileupload.*, org.apache.soap.util.*, org.apache.soap.util.xml.*, org.apache.soap.server.*" %>

<h1>Deploy a Service from File</h1>

<% 
  String configFilename = config.getInitParameter(Constants.CONFIGFILENAME);
  if (configFilename == null)
    configFilename = application.getInitParameter(Constants.CONFIGFILENAME);

ServiceManager serviceManager =
  org.apache.soap.server.http.ServerHTTPUtils.getServiceManagerFromContext(application, configFilename);

if (!request.getMethod().equals ("POST")) { 
%>

<form action="deploy_file.jsp" enctype="multipart/form-data" method="POST">
    <table border="1" width="100%">
        <tr>
        <td><h2>File Descriptor</h2></td>
        <td><input name="dd" type="file" size="100"/></td>
        </tr>
    </table>
    <p><input type="submit" value="Deploy" /></p>
</form>

<%
} else {

  String contentType = request.getContentType();
  //System.out.println("Content type is :: " +contentType);
  if ((contentType != null) && contentType.startsWith("multipart/form-data")) {
    DeploymentDescriptor dd = null;
    DiskFileUpload  upload = new DiskFileUpload ();
    FileItem item = (FileItem) upload.parseRequest(request).get(0);
    String str= item.getString();
    StringReader sr = new StringReader(str);
    //System.out.println(sr.toString());
    dd = DeploymentDescriptor.fromXML(sr);
    try{
      serviceManager.deploy (dd);
    }catch(Exception e){
      out.println(e);
    }
    out.println ("<p>Service <b>" + dd.getID () + "</b> deployed.</p>");
  }
}

%>
