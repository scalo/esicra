<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="TipoPubblicita">
		<xsl:element name="{'DatiPubblicita'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Tipo"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Messaggio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Larghezza"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Altezza"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Area"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Forma"/>
			</xsl:call-template>
			<xsl:call-template name="Ubicazione">
				<xsl:with-param name="newtag" select="'Ubicazione'"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template name="PeriodoInstallazione">
		<xsl:element name="{'DatiPeriodo'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:Tipo"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DataInizio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DataFine"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template name="EventoInstallazione">
		<xsl:element name="{'DatiEvento'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DesEvento"/>
			</xsl:call-template>
			<xsl:call-template name="Ubicazione">
				<xsl:with-param name="newtag" select="'Ubicazione'"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DataInizio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DataFine"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
