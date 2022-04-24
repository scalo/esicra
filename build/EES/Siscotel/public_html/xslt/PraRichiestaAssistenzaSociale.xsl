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

<xsl:template name="PraRichiestaAssistenzaSociale" match="//pra:PraRichiestaAssistenzaSociale">

	<xsl:element name="{'PraRichiestaAssistenzaSociale'}">

<!--SoggettoRichiedente-->
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoRichiedente']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoRichiedente'" />
        </xsl:call-template>
      </xsl:for-each>


<!--SoggettoFruitore-->      
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoFruitore']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoFruitore'" />
        </xsl:call-template>
      </xsl:for-each>


      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
      </xsl:call-template>

<!--DatiSituazioneEconomica-->  
<xsl:if test="$output = 'html' ">
			<h3>Situazione Economica</h3>
</xsl:if>	
      <xsl:call-template name="DatiSituazioneEco">
		  <xsl:with-param name="nomeelemento" select="'DatiSituazioneEconomica'" />
      </xsl:call-template>

<!--Dichiarazioni--> 
<xsl:if test="$output = 'html' ">
			<h3>Dichiarazioni</h3>
</xsl:if>	
      <xsl:call-template name="ListaDichiarazione" />

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodStrutturaOspitante" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesStrutturaOspitante" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Motivazione" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodDurata" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesDurata" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:FlgEsenzione" />
      </xsl:call-template>

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaAssistenzaSociale/pra:Stato" />
         <xsl:with-param name="alias" select="'CodStato'" />
      </xsl:call-template>
      
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaAssistenzaSociale/pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaAssistenzaSociale/pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaAssistenzaSociale/pra:Note" />
      </xsl:call-template>   
	
	</xsl:element>
	   
   </xsl:template>
</xsl:stylesheet>

