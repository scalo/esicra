<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="DatiDichiarazioneMorte">
		<xsl:element name="{'DatiDichiarazioneMorte'}">
			<xsl:for-each select="./pra:SchedaSoggetto[@attr='Defunto']/pra:Soggetto">
				<xsl:call-template name="Soggetto">
					<xsl:with-param name="tag" select="'Defunto'"/>
				</xsl:call-template>
			</xsl:for-each>
			<xsl:for-each select="pra:Indirizzo[@attr='LuogoMorte']">
			<xsl:call-template name="Indirizzo">
				<xsl:with-param name="attr" select="'LuogoMorte'"/>
				<xsl:with-param name="newtag" select="'LuogoMorte'"/>
			</xsl:call-template>
		   </xsl:for-each>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DescrizioneLuogoMorte"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:DataMorte"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="./pra:OraMorte"/>
			</xsl:call-template>
			
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
