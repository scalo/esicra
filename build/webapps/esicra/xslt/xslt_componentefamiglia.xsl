<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<xsl:template name="ComponenteFamiglia">
       <xsl:param name="tag" />
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
			<h3><xsl:value-of select="'Componente'" /></h3>
			<blockquote>
			<xsl:call-template name="ComponenteFamigliaBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="ComponenteFamigliaBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>
	
	<xsl:template name="ComponenteFamigliaBase">
		<xsl:element name="{'ComponenteFamiglia'}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumeroOrdine"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodRelazione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DesRelazione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodiceFiscale"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Cognome"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Nome"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Sesso"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataNascita"/>
			</xsl:call-template>
			<xsl:call-template name="Comune">
				<xsl:with-param name="attr" select="'comuneNascita'"/>
				<xsl:with-param name="newtag" select="'ComuneNascita'"/>
			</xsl:call-template>
			<xsl:for-each select="pra:Atto[@attr='attoNascita']">
			   <xsl:if test="$output = 'html' ">
				<h4>Atto di Nascita</h4>
				</xsl:if>
				<xsl:call-template name="AttoNascita"/>
			</xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodStatoCivile"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DesStatoCivile"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataStatoCivile"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DataDivorzio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Professione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TitoloStudio"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CartaIdentita"/>
			</xsl:call-template>
			
			<xsl:element name="{'ListaCittadinanza'}">
				<xsl:if test="$output = 'html' and pra:String[@attr='listaCittadinanza']/text() != '' ">
				<h4>Cittadinanza</h4>
				</xsl:if>
				<xsl:for-each select="pra:String[@attr='listaCittadinanza']">
					<xsl:call-template name="elemento_alias">
						<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
						<xsl:with-param name="alias" select="'Cittadinanza'"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
			
			<xsl:element name="{'ListaPensione'}">
				<xsl:if test="$output = 'html' and pra:Pensione[@attr='listaPensione']/text() != ''">
				<h4>Pensioni</h4>
				</xsl:if>
				<xsl:for-each select="pra:Pensione[@attr='listaPensione']">
					<xsl:call-template name="Pensione"/>
				</xsl:for-each>
			</xsl:element>	
			
			<xsl:element name="{'ListaPatente'}">
				<xsl:if test="$output = 'html' and pra:PolPatente[@attr='listaPatente'] != '' ">
				<h4>Patenti</h4>
				</xsl:if>
				<xsl:for-each select="pra:PolPatente[@attr='listaPatente']">
					<xsl:call-template name="Patente"/>
				</xsl:for-each>
			</xsl:element>		
			
			<xsl:element name="{'ListaVeicolo'}">
				<xsl:if test="$output = 'html' and  pra:PolVeicolo[@attr='listaVeicolo']/text() != '' ">
				<h4>Veicoli</h4>
				</xsl:if>
				<xsl:for-each select="pra:PolVeicolo[@attr='listaVeicolo']">
					<xsl:call-template name="Veicolo"/>
				</xsl:for-each>
			</xsl:element>	
			
		</xsl:element>
		
	</xsl:template>
	<xsl:template name="Comune">
		<xsl:param name="attr"/>
		<xsl:param name="newtag"/>
		<xsl:for-each select="./pra:Comune[@attr=$attr]">
			<xsl:element name="{$newtag}">
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
	</xsl:template>
	
	<xsl:template name="AttoNascita">
		<xsl:element name="{'AttoNascita'}">
			<xsl:call-template name="Comune">
				<xsl:with-param name="attr" select="*"/>
				<xsl:with-param name="newtag" select="'Comune'"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Anno"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Numero"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Parte"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Serie"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Volume"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:Ufficio"/>
			</xsl:call-template>
		</xsl:element>
	</xsl:template>

	<xsl:template name="Pensione">
		<xsl:for-each select="pra:Pensione">
			<xsl:element name="{'Pensione'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DesPensione"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:CodEnte"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DesEnte"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:NumeroPensione"/>
				</xsl:call-template>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>
	
		<xsl:template name="Patente">
		<xsl:for-each select="pra:PolPatente">
			<xsl:element name="{'Patente'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:CategoriaPatente"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:NumeroPatente"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DataRilascio"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DataScadenza"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DesEnteRilascio"/>
				</xsl:call-template>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>
	
		<xsl:template name="Veicolo">
		<xsl:for-each select="pra:PolVeicolo">
			<xsl:element name="{'Veicolo'}">
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Targa"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:CodTipoVeicolo"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:DesTipoVeicolo"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Marca"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Modello"/>
				</xsl:call-template>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>
