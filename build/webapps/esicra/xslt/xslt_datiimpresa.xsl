<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	
	<xsl:template name="DatiImpresa">
		
		<xsl:element name="{'DatiImpresa'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DescrizioneImpresa"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:PartitaIVA"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumeroTelefono"/>
			</xsl:call-template>
					
			<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'Indirizzo'"/>
				<xsl:with-param name="newtag" select="'Indirizzo'"/>
			</xsl:call-template>
			
			<xsl:element name="{'IscrizioneCCIA'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:NumeroIscrizioneCCIA"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:SedeCCIA"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:DataIscrizione"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:CodiceISTATAttivita"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:ClasseTrasporto"/>
				</xsl:call-template>
			</xsl:element>
			<xsl:element name="{'Contratto'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Contratto/pra:TipoContratto"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Contratto/pra:CodContratto"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Contratto/pra:DataInizioContratto"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Contratto/pra:DataFineContratto"/>
				</xsl:call-template>
			</xsl:element>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>
