<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="DatiImpresaCPI">
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
	<xsl:template name="DatiRichiestaCPI">
		<xsl:element name="{'DatiRichiestaCPI'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodRichiesta"/>
			</xsl:call-template>
			<xsl:element name="{'ListaDatiOpereImpianti'}">
				<xsl:call-template name="DatiOpereImpianti"/>
			</xsl:element>
			<xsl:element name="{'DatiAttivitaCPI'}">
				<xsl:for-each select="pra:DatiAttivitaCPI">
					<xsl:call-template name="DatiOpereImpianti">
						<xsl:with-param name="nomeelemento" select="pra:CodAttivita"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:DesAttivita"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:NumeroDecAttivitaPrincipale"/>
					</xsl:call-template>
					<xsl:element name="{'ListaAttivitaSecondarie'}">
						<xsl:for-each select="pra:String[@attr='ListaAttivitaSecondarie']">
							<xsl:call-template name="elemento_alias">
								<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
								<xsl:with-param name="alias" select="'AttivitaSecondaria'"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:element>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:Telefono"/>
					</xsl:call-template>
					<xsl:call-template name="Indirizzo">
						<xsl:with-param name="attr" select="'Indirizzo'"/>
						<xsl:with-param name="newtag" select="'Indirizzo'"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template name="DatiOpereImpianti">
		<xsl:for-each select="pra:DatiOpereImpianti[@attr='ListaDatiOpereImpianti']/pra:DatiOpereImpianti">
			<xsl:element name="{'DatiOpereImpianti'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:CodOpereImpianti"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DesOpereImpianti"/>
				</xsl:call-template>
					<xsl:element name="{'ListaDataOperaImpianti'}">
					
					<xsl:for-each select="pra:Date">
					
						<xsl:call-template name="elemento_alias">
							<xsl:with-param name="nomeelemento" select="pra:java.util.Date"/>
							<xsl:with-param name="alias" select="'DataOperaImpianti'"/>
						</xsl:call-template>
					
				</xsl:for-each>
				</xsl:element>
				<xsl:element name="{'ListaNumeroProtocollo'}">
					<xsl:for-each select="pra:String">
						<xsl:call-template name="elemento_alias">
							<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
							<xsl:with-param name="alias" select="'NumeroProtocollo'"/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:element>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
