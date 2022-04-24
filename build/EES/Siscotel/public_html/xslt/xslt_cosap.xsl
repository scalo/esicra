<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	
	<xsl:template name="AttivitaCommerciale">
		<xsl:element name="{'AttivitaCommerciale'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodSettoriMerceologici"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DesSettoriMerceologici"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodTipoCommercio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DesTipoCommercio"/>
			</xsl:call-template>
			<xsl:for-each select="pra:DatiPermesso[@attr='AutorizzazioneAttivita']">
			<xsl:call-template name="DatiPermesso">
				<xsl:with-param name="tag" select="'AutorizzazioneAttivita'"/>
			</xsl:call-template>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="DurataTemporaneaPratica">
		<xsl:element name="{'ListaDurataTemporaneaPratica'}">
		   <xsl:for-each select="//pra:DurataTemporaneaPratica/pra:DurataTemporaneaPratica">
			  <xsl:element name="{'DurataTemporaneaPratica'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:OraInizio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:OraFine"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Giorno"/>
			</xsl:call-template>
			</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="SuoloCosap">
		<xsl:param name="tag"/>
		<xsl:element name="{$tag}">
			<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'Immobile'"/>
				<xsl:with-param name="newtag" select="'Immobile'"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Lunghezza"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Larghezza"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Totale"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
</xsl:stylesheet>
