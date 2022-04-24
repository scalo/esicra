<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="UnitaImmobiliare">
	
		<xsl:element name="{'UnitaImmobiliare'}">
			<xsl:if test="$output = 'html' ">
			<h2>Unità Immobiliare</h2>
			 </xsl:if>
			<xsl:element name="{'InfoCatasto'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:CodTipologiaCatasto"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:DesTipologiaCatasto"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:Sezione"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:Foglio"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:Mappale"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:Subalterno"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:InfoCatasto/pra:ZonaPrg"/>
				</xsl:call-template>
			</xsl:element>
			<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'ubicazione'"/>
				<xsl:with-param name="newtag" select="'Ubicazione'"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="DatiUnitaImmobiliare">
	  <xsl:param name="tag"/>
		<xsl:if test="$output = 'html' ">
			<h3>Dati Unità Immobiliare</h3>
		</xsl:if>
		<xsl:element name="{$tag}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumAppartamenti"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:SupTotaleAbitazioni"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumAbitantiAddetti"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumServiziIgienici"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumCucine"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumLavanderie"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TotSupCoperta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TotSupScoperta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TotSupScopertaImpermeabilizzata"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:SupUffici"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:SupAttivitaProduttive"/>
			</xsl:call-template>
			<xsl:if test="pra:DataRilascio != ''">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DataRilascio"/>
				</xsl:call-template>
			</xsl:if>
		</xsl:element>
		<xsl:if test="$output = 'html' ">
			<br/><br/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="DatiVari">
		
		<xsl:param name="tag"/>
		<xsl:element name="Lista{$tag}">
			<xsl:for-each select="pra:Dati">
				<xsl:element name="{$tag}">
					<xsl:call-template name="elemento">
						<xsl:with-param name="nomeelemento" select="pra:DescrizioneTipoDato"/>
					</xsl:call-template>
					<xsl:if test="$output = 'html' ">
							<br/>
					</xsl:if>
					<xsl:for-each select="pra:Valore[@attr='listaValori']">
						<xsl:element name="ListaValori">
						<xsl:for-each select="pra:Valore">
							<xsl:call-template name="elemento">
								<xsl:with-param name="nomeelemento" select="pra:DesValore"/>
							</xsl:call-template>
							<xsl:call-template name="elemento">
								<xsl:with-param name="nomeelemento" select="pra:Valore"/>
							</xsl:call-template>
							<xsl:call-template name="elemento">
								<xsl:with-param name="nomeelemento" select="pra:UnitaMisura"/>
							</xsl:call-template>
							<xsl:if test="$output = 'html' ">
							<br/>
							</xsl:if>
							</xsl:for-each>
						</xsl:element>
					</xsl:for-each>
				</xsl:element>
			</xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataInizio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataFine"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>
	
	
</xsl:stylesheet>
