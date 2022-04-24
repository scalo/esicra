<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />
<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_situazioneeco.xsl" />
<xsl:include href="xslt_dichiarazione.xsl" />
-->

<xsl:template name ="PraCertificato" match="//pra:PraCertificato">

<xsl:element name="{'PraCertificato'}">

<!--SoggettoRichiedente-->
      <xsl:for-each select="//pra:Soggetto[@attr='soggetto']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoRichiedente'" />
        </xsl:call-template>
      </xsl:for-each>

<!--SoggettoFruitore-->
	  
      <xsl:for-each select="//pra:Soggetto[@attr='soggettoCertificato']">
      <xsl:if test="//pra:Soggetto[@attr='soggettoCertificato']/pra:Cognome">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoCertificato'" />
        </xsl:call-template>
      </xsl:if>
      </xsl:for-each>
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
      </xsl:call-template>

<!--Pratica-->

      <xsl:call-template name="Pratica" />

<!--dati certificato-->  
<xsl:element name="Certificato">
       	<xsl:if test="$output = 'html' ">
			<h2>Certificato</h2>
       	</xsl:if> 
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:IdTipoCertificato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesTipoCertificato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Bollo" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DirittiSegreteria" />
      </xsl:call-template>

	 <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:RichiedibilePerAltri" />
      </xsl:call-template>
	
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CopiaAtto" />
      </xsl:call-template>
      
       <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataCertificatoStorico" />
      </xsl:call-template>
	
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Uso" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Motivazione" />
      </xsl:call-template>

</xsl:element>

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:PraCertificato/pra:Stato" />
		<xsl:with-param name="alias" select="'CodStato'" />
      </xsl:call-template>

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraCertificato/pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraCertificato/pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraCertificato/pra:Note" />
      </xsl:call-template>   


</xsl:element>

   </xsl:template>
</xsl:stylesheet>

