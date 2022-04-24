<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="DatiDichiarazioneNascita">
		<xsl:element name="{'DatiDichiarazioneNascita'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Numero"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Data"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NomeCentroNascita"/>
			</xsl:call-template>
			<xsl:element name="{'ComuneCentroNascita'}">
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
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CognomeDelegato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NomeDelegato"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumAltraDichiarazione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:OrdineDiNascita"/>
			</xsl:call-template>
			<xsl:for-each select="pra:DatiNascita">
				<xsl:element name="DatiNascita">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:NomeNato"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DataNascita"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:OraNascita"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Sesso"/>
				</xsl:call-template>
				<xsl:for-each select="pra:SchedaSoggetto[@attr='Madre']/pra:Soggetto">
				  <xsl:if test="//pra:SchedaSoggetto[@attr='Madre']/pra:Soggetto/pra:Cognome"> 
					<xsl:call-template name="Soggetto">
						<xsl:with-param name="tag" select="'Madre'"/>
					</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="pra:SchedaSoggetto[@attr='Padre']/pra:Soggetto">
				  <xsl:if test="//pra:SchedaSoggetto[@attr='Padre']/pra:Soggetto/pra:Cognome">
					<xsl:call-template name="Soggetto">
						<xsl:with-param name="tag" select="'Padre'"/>
					</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
