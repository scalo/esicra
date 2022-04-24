<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>
	<!--
	<xsl:include href="xslt_elemento.xsl"/>
	<xsl:include href="xslt_sch_soggetto.xsl"/>
	<xsl:include href="xslt_sog_indirizzo.xsl"/>
	<xsl:include href="xslt_sog_giur.xsl"/>
	<xsl:include href="xslt_pratica.xsl"/>
	<xsl:include href="xslt_datidac.xsl"/>
    -->
	<xsl:template match="/">
		<xsl:apply-templates select="//pra:PraDenunciaDAC"/>
	</xsl:template>
	<xsl:template name="PraDenunciaDAC" match="//pra:PraDenunciaDAC">
       <xsl:element name="{'PraDenunciaDAC'}">
		<!--SchedaSoggettoRichiedente-->
		<xsl:call-template name="SchedaSoggetto">
			<xsl:with-param name="nodo" select="'//pra:SchedaSoggetto'"/>
			<xsl:with-param name="attr" select="'soggetto'"/>
			<xsl:with-param name="newtag" select="'SoggettoRichiedente'"/>
		</xsl:call-template>
		<!--SoggettoGiuridicoRichiedente-->
		<xsl:for-each select="//pra:SoggettoGiuridico">
			<xsl:call-template name="SoggettoGiuridico">
				<xsl:with-param name="tag" select="'SoggettoGiuridicoRichiedente'"/>
			</xsl:call-template>
		</xsl:for-each>
		<xsl:call-template name="elemento">
			<xsl:with-param name="nomeelemento" select="//pra:CodDenuncia"/>
		</xsl:call-template>
		<xsl:call-template name="elemento">
			<xsl:with-param name="nomeelemento" select="//pra:DesDenuncia"/>
		</xsl:call-template>
		<!--DatiDenunciaDAC-->
		<xsl:if test="$output = 'html' ">
			<h2>Dati Denuncia</h2>
		</xsl:if>
		<xsl:call-template name="DatiDAC"/>
		<!--Pratica-->
		<xsl:call-template name="Pratica"/>
		<xsl:call-template name="elemento_alias">
			<xsl:with-param name="nomeelemento" select="//pra:Stato"/>
			<xsl:with-param name="alias" select="'CodStato'"/>
		</xsl:call-template>
		<xsl:call-template name="elemento">
			<xsl:with-param name="nomeelemento" select="//pra:DesStato"/>
		</xsl:call-template>
		<xsl:call-template name="elemento">
			<xsl:with-param name="nomeelemento" select="//pra:DataStato"/>
		</xsl:call-template>
		<xsl:call-template name="elemento">
			<xsl:with-param name="nomeelemento" select="pra:Note"/>
		</xsl:call-template>
         </xsl:element>
	</xsl:template>
</xsl:stylesheet>
