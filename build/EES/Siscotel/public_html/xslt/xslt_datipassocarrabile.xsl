<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	
	<xsl:template name="AutorizzazioneEdilizia">
		<xsl:element name="{'AutorizzazioneEdilizia'}">
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Fruitore"/>
		</xsl:call-template>
		
		<xsl:element name="{'Comune'}">
			<xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Comune/pra:CodIstatComune" />
            </xsl:call-template>
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Comune/pra:DesComune" />
            </xsl:call-template>
			<xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Comune/pra:DesProvincia" />
            </xsl:call-template>
         </xsl:element>
		
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Numero"/>
		</xsl:call-template>
		
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:DataRilascio"/>
		</xsl:call-template>
		
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Tipo"/>
		</xsl:call-template>
		
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:DataInizioValidita"/>
		</xsl:call-template>
		
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:DataFineValidita"/>
		</xsl:call-template>
		
		<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DatiPermesso/pra:Comune/pra:IdServizio"/>
		</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="DatiPassoCarrabile">
		<xsl:element name="{'DatiPassoCarrabile'}">
		<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'Ubicazione'"/>
				<xsl:with-param name="newtag" select="'Ubicazione'"/>
		</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Accesso_A_Raso"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataAccesso_A_Raso"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Larghezza"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:Profondita"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:CodTipoAccesso"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="//pra:DesTipoAccesso"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
</xsl:stylesheet>
