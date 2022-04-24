<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">


<xsl:include href="PraIscrizioneMensa.xsl"/>
<xsl:include href="PraRecessoMensa.xsl"/>
<xsl:include href="PraIscrizioneTrasporto.xsl"/>
<xsl:include href="PraRecessoTrasporto.xsl"/>
<xsl:include href="PraIscrizioneCentro.xsl"/>
<xsl:include href="PraRecessoCentro.xsl"/>
<xsl:include href="xslt_datisoggetto.xsl" />
<xsl:include href="xslt_datiindirizzo.xsl" />
<xsl:include href="xslt_elemento.xsl" />
<xsl:include href="xslt_label.xsl" />
<xsl:include href="xslt_praticaridotta.xsl" />
<xsl:include href="xslt_recapitoridotto.xsl" />
<xsl:include href="xslt_datiscuolaridotta.xsl" />
 
<xsl:variable name="output" select=" 'html' " />
  
  
  <xsl:template match="/">
       <html>
	  <link rel="stylesheet" type="text/css" href="pratica.css"/>	   
       <body>
       <font face="Arial" size="2">
            <xsl:apply-templates select="//pra:Pratica"/>
       </font>
       </body></html>
   </xsl:template>

  <xsl:template match="//pra:Pratica">
      
      	  <xsl:if test="pra:IdServizio=100141">
		   <H1><B>Richiesta Iscrizione Mense Scolastiche</B></H1><br/>
		   <xsl:call-template name="PraIscrizioneMensa" />
      	 </xsl:if>
      	 
      	 <xsl:if test="pra:IdServizio=210046">
		   <H1><B>Domanda  Recesso Mensa Scolastica</B></H1><br/>
		   <xsl:call-template name="PraRecessoMensa" />
      	 </xsl:if>
      	 
      	 <xsl:if test="pra:IdServizio=100142">
		   <H1><B>Domanda Iscrizione Trasporto Scolastico</B></H1><br/>
		   <xsl:call-template name="PraIscrizioneTrasporto" />
      	 </xsl:if>
      	 
      	 <xsl:if test="pra:IdServizio=210047">
		   <H1><B>Domanda  Recesso Trasporto Scolastico</B></H1><br/>
		   <xsl:call-template name="PraRecessoTrasporto" />
      	 </xsl:if>
      	 
      	 <xsl:if test="pra:IdServizio=100168">
		   <H1><B>Domanda Iscrizione Centro Sportivo Ricreativo</B></H1><br/>
		   <xsl:call-template name="PraIscrizioneCentro" />
      	 </xsl:if>
      	 
      	 <xsl:if test="pra:IdServizio=210048">
		   <H1><B>Domanda  Recesso Centro Sportivo Ricreativo</B></H1><br/>
		   <xsl:call-template name="PraRecessoCentro" />
      	 </xsl:if>
      
  </xsl:template>


</xsl:stylesheet>
