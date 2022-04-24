<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>
Hello World
</title>


<script language="C#" runat="server">
void Button1_Click(object Source, EventArgs e)
{
 if (Text1.Value == "")
 {
  Span1.InnerHtml = "Error: inserisci il nome del file";
  return;
 }
 if (File1.PostedFile != null)
 {
  try
   {
    File1.PostedFile.SaveAs("c:\\temp\\"+Text1.Value);
    Span1.InnerHtml = "File uploadato con successo in <b>c:\\temp\\" +     Text1.Value + "</b> sul Web server";
   }
  catch (Exception exc)
   {
    Span1.InnerHtml = "Errore nel salvataggio del file <b>c:\\temp\\"     + Text1.Value + "</b><br>" + exc.ToString();
   }
 }
}
</script>



</head>
<body>
<FONT face="Arial">
<CENTER>
<TABLE>
<TR>
<TD COLSPAN=2 ALIGN=center><H1>Downlaod File</H1></TD>
</TR>
<TR>
<form enctype="multipart/form-data" runat="server">
  Seleziona il file da inviare al server:
  <input id="File1" type="file" maxlength="30" runat="client"/><p>
  Salva con nome(senza percorso):
  <input id="Text1" type="text" runat="server"/><p>
  <span id="Span1" style="font: 8pt verdana;" runat="server" /><p>
  <input type="button" id="Button1" value="Upload"     OnServerClick="Button1_Click" runat="server">
</form>
</TABLE>

</CENTER>
</FONT>
</body>
</html>
