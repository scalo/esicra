<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_situazioneeco.xsl" />
<xsl:include href="xslt_cpi.xsl" />
<xsl:include href="xslt_indirizzo.xsl" /> 
-->

<xsl:template name="PraRichiestaCPI" match="//pra:PraRichiestaCPI">

<xsl:element name="{'PraRichiestaCPI'}">

<!--Soggetto-->
<xsl:for-each select="//pra:Soggetto[@attr='SoggettoRichiedente']">
	 <xsl:call-template name="Soggetto">
     <xsl:with-param name="tag" select="'SoggettoRichiedente'" />
     </xsl:call-template>
</xsl:for-each>

<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:TitolaritaDichiarante" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="/pra:PraRichiestaCPI/pra:CodRichiesta" />
</xsl:call-template>		
		
<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:DesRichiesta" />
</xsl:call-template>

<xsl:if test="$output = 'html' ">
		<h2>Dati Impresa</h2>
</xsl:if>	
<xsl:for-each select="//pra:DatiImpresa">
	<xsl:call-template name="DatiImpresaCPI"/>
</xsl:for-each>

<xsl:if test="$output = 'html' ">
		<h3>Dati Richiesta</h3>
</xsl:if>	
<xsl:for-each select="//pra:DatiRichiestaCPI">
	<xsl:call-template name="DatiRichiestaCPI"/>
</xsl:for-each>

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodStato" />
      </xsl:call-template>

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Note" />
      </xsl:call-template>   


</xsl:element>

   </xsl:template>
</xsl:stylesheet>

