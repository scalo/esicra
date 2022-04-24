<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="DatiAutorizzazioneAttivita">
		<xsl:element name="{'DatiAutorizzazioneAttivita'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NullaOsta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Licenza"/>
			</xsl:call-template>
			<xsl:for-each select="./pra:Comune[@attr='ComuneRilascioLicenza']">
				<xsl:element name="{'ComuneRilascioLicenza'}">
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:CodIstatComune"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:DesComune"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:DesProvincia"/>
					</xsl:call-template>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<xsl:template name="DatiRichiestaAutorizzazione">
		<xsl:element name="{'DatiRichiestaAutorizzazione'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Evento"/>
			</xsl:call-template>
			<xsl:call-template name="Indirizzo">
					<xsl:with-param name="attr" select="'Ubicazione'"/>
					<xsl:with-param name="newtag" select="'Ubicazione'"/>
				</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataInizio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataFine"/>
			</xsl:call-template>
			<xsl:if test="pra:DataRilascio != ''">
				<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataRilascio"/>
			</xsl:call-template>
			</xsl:if>
			<xsl:element name="{'ListaTipoAttrazione'}">
				<xsl:for-each select="pra:String[@attr='ListaTipoAttrazione']">
						<xsl:call-template name="elemento_alias">
							<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
							<xsl:with-param name="alias" select="'TipoAttrazione'" />
						</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
