<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
  <xsl:template name="DatiPermesso">
		<xsl:param name="tag"/>
		<xsl:element name="{$tag}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Fruitore"/>
			</xsl:call-template>
			<xsl:if test="pra:Comune/pra:DesComune  != '' ">
			<xsl:element name="{'Comune'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Comune/pra:CodIstatComune"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Comune/pra:DesComune"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Comune/pra:DesProvincia"/>
				</xsl:call-template>
			</xsl:element>
			</xsl:if>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Numero"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataRilascio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Tipo"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataInizioValidita"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataFineValidita"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:IdServizio"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
 </xsl:stylesheet>
