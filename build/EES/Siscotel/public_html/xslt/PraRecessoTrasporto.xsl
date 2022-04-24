<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

   <xsl:template name="PraRecessoTrasporto" match="/">
         <xsl:apply-templates select="//pra:PraRecessoTrasporto" />
   </xsl:template>

<xsl:template  match="//pra:PraRecessoTrasporto">
	
<xsl:element name="PraRecessoTrasporto">

    <!--DatiSoggettoRichiedente-->
    
          <xsl:call-template name="DatiSoggetto" >
            <xsl:with-param name="attr" select="'soggettoRichiedente'" />
            <xsl:with-param name="tag" select="'SoggettoRichiedente'" />
          </xsl:call-template>
    
    <!--DatiSoggettoFruitore--> 
    <xsl:call-template name="DatiSoggetto" >
            <xsl:with-param name="attr" select="'soggettoFruitore'" />
            <xsl:with-param name="tag" select="'SoggettoFruitore'" />
     </xsl:call-template>
     
    <!--Titolarita Richiedente -->
	
	<xsl:call-template name="elemento">
		<xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
    </xsl:call-template>
	
    
    <!--Dati Pratica-->  
     <xsl:call-template name="PraticaRidotta" >
            <xsl:with-param name="tag" select="'DatiPratica'" />
     </xsl:call-template>  

<!--DatiScuola-->
	 <xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Scuola'"/></h3>
	 </xsl:if>
	 <xsl:call-template name="DatiScuolaRidotta" >
            <xsl:with-param name="tag" select="'DatiScuola'" />
     </xsl:call-template>

<!--Mensa-->
	<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Percorso'"/></h3>
	</xsl:if>	
	<xsl:call-template name="elemento_alias">
		<xsl:with-param name="nomeelemento" select="//pra:Percorso" />
		<xsl:with-param name="alias" select="'Dati Percorso'"/>
    </xsl:call-template>

</xsl:element>
</xsl:template>
</xsl:stylesheet>

