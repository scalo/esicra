<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

   <xsl:template name="PraIscrizioneMensa" match="/">
         <xsl:apply-templates select="//pra:PraIscrizioneMensa" />
   </xsl:template>

<xsl:template  match="//pra:PraIscrizioneMensa">
	
<xsl:element name="PraIscrizioneMensa">

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
			<h3><xsl:value-of select="'Mensa'"/></h3>
	</xsl:if>	
	<xsl:call-template name="elemento_alias">
		<xsl:with-param name="nomeelemento" select="//pra:Mensa" />
		<xsl:with-param name="alias" select="'Dati Mensa'"/>
    </xsl:call-template>
    
<!--ListaEsenzioni-->
<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Lista Esenzioni'"/></h3>
</xsl:if>	
<xsl:element name="{'ListaEsenzioni'}">
				<xsl:for-each select="//pra:String[@attr = 'listaEsenzioni'] " >
					<xsl:call-template name="elemento_alias">
					<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
					<xsl:with-param name="alias" select="'Esenzione'"/>
				</xsl:call-template>
				</xsl:for-each>
</xsl:element>

<!--ListaAgevolazioni-->
<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Lista Agevolazioni'"/></h3>
</xsl:if>	
<xsl:element name="{'ListaAgevolazioni'}">
				<xsl:for-each select="//pra:String[@attr = 'listaAgevolazioni'] " >
					<xsl:call-template name="elemento_alias">
					<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
					<xsl:with-param name="alias" select="'Agevolazione'"/>
				</xsl:call-template>
				</xsl:for-each>
</xsl:element>

</xsl:element>
</xsl:template>
</xsl:stylesheet>

