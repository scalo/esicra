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

<xsl:template name="PraPresidentiScrutatori" match="//pra:PraPresidentiScrutatori">

<xsl:element name="{'PraPresidentiScrutatori'}">

<!--Soggetto-->
      <xsl:for-each select="//pra:Soggetto[@attr='soggetto']">
      <xsl:if test=" $output = 'html' ">
		   <h2>Richiedente</h2>
      </xsl:if>
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'Soggetto'" />
        </xsl:call-template>
      </xsl:for-each>

<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:TitoloStudio" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Professione" />
</xsl:call-template>		
		
<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:FlgIscrizione" />
</xsl:call-template>

<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:FlgCompatibilita" />
</xsl:call-template>

<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:FlgPresidente" />
</xsl:call-template>


<!--Pratica-->
	  
      <xsl:call-template name="Pratica" />

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:Stato" />
         <xsl:with-param name="alias" select="'CodStato'" />
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

