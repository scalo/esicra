<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="DatiEspatrio">
		<xsl:element name="{'DatiEspatrio'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataEspatrio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Motivazione"/>
			</xsl:call-template>
			<xsl:call-template name="Comune">
				<xsl:with-param name="attr" select="'ComuneIscrizioneAIRE'"/>
				<xsl:with-param name="newtag" select="'ComuneIscrizioneAIRE'"/>
			</xsl:call-template>
			<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'IndirizzoEstero'"/>
				<xsl:with-param name="newtag" select="'IndirizzoEstero'"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodConsolato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DesConsolato"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
