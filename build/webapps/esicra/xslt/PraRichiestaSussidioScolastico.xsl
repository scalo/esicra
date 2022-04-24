<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>
	
<!--
	<xsl:include href="xslt_elemento.xsl"/>
	<xsl:include href="xslt_soggetto.xsl"/>
	<xsl:include href="xslt_pratica.xsl"/>
	<xsl:include href="xslt_responsabile.xsl"/>
	<xsl:include href="xslt_situazioneeco.xsl"/>
	<xsl:include href="xslt_dichiarazione.xsl"/>
	-->
	<xsl:template  match="/">
		<xsl:apply-templates select="//pra:PraRichiestaSussidioScolastico"/>
	</xsl:template>
	<xsl:template name="PraRichiestaSussidioScolastico"  match="//pra:PraRichiestaSussidioScolastico">
		<xsl:element name="{'PraRichiestaSussidioScolastico'}">
			<!--SoggettoRichiedente-->
			<xsl:for-each select="//pra:Soggetto[@attr='SoggettoRichiedente']">
				<xsl:call-template name="Soggetto">
					<xsl:with-param name="tag" select="'SoggettoRichiedente'"/>
				</xsl:call-template>
			</xsl:for-each>
			<!--SoggettoFruitore-->
			<xsl:for-each select="//pra:Soggetto[@attr='SoggettoFruitore']">
				<xsl:call-template name="Soggetto">
					<xsl:with-param name="tag" select="'SoggettoFruitore'"/>
				</xsl:call-template>
			</xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente"/>
			</xsl:call-template>
			<!--Pratica-->
			<xsl:call-template name="Pratica"/>
			<!--DatiSituazioneEco-->
			<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Situazione Economica'"/></h3>
			</xsl:if>
			<xsl:call-template name="DatiSituazioneEco">
				<xsl:with-param name="nomeelemento" select="'DatiSituazioneEconomica'"/>
			</xsl:call-template>
			<!--Dichiarazioni-->
			<xsl:call-template name="ListaDichiarazione"/>
			<xsl:if test="$output = 'html' ">
				<h3><xsl:value-of select="'Dichiarazioni'"/></h3>
			</xsl:if>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Motivazione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:CodSussidio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesSussidio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:CodIstituto"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesIstituto"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:AnnoScolastico"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Classe"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Sezione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:MediaVoti"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:CreditoScolastico"/>
			</xsl:call-template>
			<xsl:if test="$output = 'html' ">
				<br/><br/>
			</xsl:if>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesStato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento_alias">
				<xsl:with-param name="nomeelemento" select="//pra:Stato"/>
				<xsl:with-param name="alias" select="'CodStato'"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DataStato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Note"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
