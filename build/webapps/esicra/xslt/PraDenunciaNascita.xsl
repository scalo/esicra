<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>
	<!--
	<xsl:include href="xslt_elemento.xsl"/>
	<xsl:include href="xslt_soggetto.xsl"/>
	<xsl:include href="xslt_indirizzo.xsl"/>
	<xsl:include href="xslt_pratica.xsl"/>
	<xsl:include href="xslt_datinascita.xsl"/>
    -->
	<xsl:template name="PraDenunciaNascita" match="//pra:PraPrenotazioneNascita ">
		<xsl:element name="PraDenunciaNascita">
			<xsl:if test="$output = 'html' ">
			<h2>Dati Soggetto</h2>
			</xsl:if>
			<!--SoggettoDenunciante-->
			<xsl:for-each select="//pra:Soggetto[@attr='Dichiarante']">
				<xsl:call-template name="Soggetto">
					<xsl:with-param name="tag" select="'Dichiarante'"/>
				</xsl:call-template>
			</xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:CodDichiarazione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesDichiarazione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:TitolaritaDichiarante"/>
			</xsl:call-template>
			<xsl:if test="$output = 'html' ">
			<h2>Dati Dichiarazione</h2>
			</xsl:if>
			<!--Dichiarazioni-->
			<xsl:element name="ListaDatiDichiarazione">
			<xsl:for-each select="//pra:DatiDichiarazioneNascita/pra:DatiDichiarazioneNascita">
				<xsl:if test="$output = 'html' ">
				<h3>Dichiarazione di Nascita</h3>
				</xsl:if>
				<xsl:call-template name="DatiDichiarazioneNascita"/>
			</xsl:for-each>
			</xsl:element>
			<!-- Data Appuntamento -->
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DataAppuntamento"/>
			</xsl:call-template>
			<!-- Ora Appuntamento -->
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:OraAppuntamento"/>
			</xsl:call-template>
			<!--Pratica-->
			<xsl:call-template name="Pratica"/>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesStato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:CodStato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Note"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DataStato"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
