<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>
	<!--	
	<xsl:include href="xslt_elemento.xsl"/>
	<xsl:include href="xslt_soggetto.xsl"/>
	<xsl:include href="xslt_indirizzo.xsl"/>
	<xsl:include href="xslt_pratica.xsl"/>
	<xsl:include href="xslt_datimorte.xsl"/>
	-->
	<xsl:template name="PraDenunciaMorte" match="//pra:PraPrenotazioneMorte ">
		<xsl:element name="PraDenunciaMorte">
			<!--SoggettoDenunciante-->
			<h2>Dati Soggetto</h2>
			<xsl:for-each select="//pra:SchedaSoggetto[@attr='Dichiarante']/pra:Soggetto">
				<xsl:call-template name="Soggetto">
					<xsl:with-param name="tag" select="'Dichiarante'"/>
				</xsl:call-template>
			</xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:TitolaritaDichiarante"/>
			</xsl:call-template>
			<!--Dichiarazioni-->
			<xsl:element name="ListaDatiDichiarazione">
			<xsl:if test="$output = 'html' ">
			<h2>Dati Dichiarazione</h2>
			</xsl:if>
			
			<xsl:for-each select="//pra:DatiDichiarazioneMorte/pra:DatiDichiarazioneMorte">
				<xsl:call-template name="DatiDichiarazioneMorte"/>
			</xsl:for-each>
			</xsl:element>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DisponibilitaSelezionata"/>
			</xsl:call-template>
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
				<xsl:with-param name="nomeelemento" select="//pra:Stato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesStato"/>
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
