<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />
<xsl:include href="xslt_sog_giur.xsl" />
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_indirizzo.xsl"/>
<xsl:include href="xslt_datipassocarrabile.xsl" />
-->
   <xsl:template name="PraPassoCarrabile" match="/">
         <xsl:apply-templates select="//pra:PraPassoCarrabile" />
   </xsl:template>
 
 <xsl:template match="//pra:PraPassoCarrabile">

<xsl:element name="{'PraPassoCarrabile'}"> 

<!--Richiedente-->
<xsl:for-each select="//pra:Soggetto[@attr='Richiedente']">
      <xsl:call-template name="Soggetto" >
        <xsl:with-param name="tag" select="'Richiedente'" />
      </xsl:call-template>
 </xsl:for-each>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
      </xsl:call-template>
      
<!--SoggettoFruitoreFisico--> 
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoFruitoreFisico']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoFruitoreFisico'" />
        </xsl:call-template>
      </xsl:for-each>
 
 <!--SoggettoGiuridicoFruitore -->
 <xsl:for-each select="//pra:SoggettoGiuridico[@attr='SoggettoFruitoreGiuridico']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'SoggettoFruitoreGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>


<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TipoRichiesta" />
</xsl:call-template>

 <xsl:if test="$output = 'html' and //pra:DatiPermesso/pra:Fruitore/text() != ''">
			<h3><xsl:value-of select="'Dati Permesso'"/></h3>
	 </xsl:if>      
<xsl:call-template name="AutorizzazioneEdilizia"/>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataInizioCantiere" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataFineCantiere" />
</xsl:call-template>

<xsl:for-each select="pra:DatiPassoCarrabile">
	<xsl:if test="$output = 'html' and pra:DatiPassoCarrabile/text() ">
				<h2><xsl:value-of select="'Dati Passo Carrabile'"/></h2>
	</xsl:if>      
	<xsl:call-template name="DatiPassoCarrabile"/>
</xsl:for-each>

<!--Pratica-->  
<xsl:call-template name="Pratica" /> 

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:DesStato" />
</xsl:call-template>

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:CodStato" />
</xsl:call-template>      

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:DataStato" />
</xsl:call-template>   

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:Note" />
</xsl:call-template> 

</xsl:element>
</xsl:template>

</xsl:stylesheet>

