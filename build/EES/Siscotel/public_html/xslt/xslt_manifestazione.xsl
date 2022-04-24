<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	
	<xsl:template name="DatiEvento">
		<xsl:element name="{'DatiEvento'}">
		<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'UbicazioneEvento'"/>
				<xsl:with-param name="newtag" select="'UbicazioneEvento'"/>
		</xsl:call-template>
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DenominazioneEvento"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataInizialeEvento"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataFinaleEvento"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:OraInizialeEvento"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:OraFinaleEvento"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TipoEvento"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NoteEvento"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="DichiarazioneAggiuntiva">
	<xsl:element name="{'ListaDichiarazioneAggiuntiva'}">
			<xsl:for-each select="//pra:DichiarazioneAggiuntiva/pra:DichiarazioneAggiuntiva">
				<xsl:element name="{'DichiarazioneAggiuntiva'}">
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:CodDichiarazione"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:DesDichiarazione"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:FlgSottoscritta"/>
					</xsl:call-template>
					<xsl:if test="$output = 'html' ">
					<br/>
					</xsl:if>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	

</xsl:stylesheet>
