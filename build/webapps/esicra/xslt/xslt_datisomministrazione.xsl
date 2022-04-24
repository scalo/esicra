<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="DatiProduzioneAlimenti">
		<xsl:element name="{'DatiProduzioneAlimenti'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:CodTipologia"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DesTipologia"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template name="DatiConfezionamentoAlimenti">
		<xsl:element name="{'DatiConfezionamentoAlimenti'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:CodTipologia"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DesTipologia"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template name="DatiTipoEsercizioAlimenti">
		<xsl:element name="{'DatiTipoEsercizioAlimenti'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:CodTipologia"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DesTipologia"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	<xsl:template name="DepositoAlimenti">
		<xsl:element name="{'UbicazioneDeposito'}">
			<xsl:if test="'./pra:Indirizzo/pra:Comune' !='' ">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="./pra:Indirizzo/pra:Comune"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="'./pra:Indirizzo/pra:Localita' !='' ">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="/pra:Indirizzo/pra:Localita"/>
				</xsl:call-template>
			</xsl:if>
		</xsl:element>
		<xsl:element name="{'ListaTipologiaAlimenti'}">
			<xsl:for-each select="./pra:DatiTipologia[@attr='ListaTipologiaAlimenti']/pra:DatiTipologia">
				<xsl:element name="{'DatiTipologia'}">
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="./pra:CodTipologia"/>
					</xsl:call-template>
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="./pra:DesTipologia"/>
					</xsl:call-template>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<xsl:template name="DatiIscrizioneREC">
		<xsl:element name="{'DatiIscrizioneREC'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:SedeRECLegale"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:NumeroRECLegale"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DataIscrizioneRECLegale"/>
			</xsl:call-template>
			<!--Somministratore-->
			<xsl:for-each select="//pra:Soggetto[@attr='Somministratore']">
				<xsl:call-template name="Soggetto">
					<xsl:with-param name="tag" select="'Somministratore'"/>
				</xsl:call-template>
			</xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:SedeRECSomministratore"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:NumeroRECSomministratore"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DataIscrizioneRECSomministratore"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
