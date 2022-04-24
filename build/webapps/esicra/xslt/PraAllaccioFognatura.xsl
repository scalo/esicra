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
<xsl:include href="xslt_fognatura.xsl" />
<xsl:include href="xslt_datipermesso.xsl" />
-->

   <xsl:template  match="/">
         <xsl:apply-templates select="//pra:PraAllaccioFognatura" />
   </xsl:template>
 
 <xsl:template name="PraAllaccioFognatura" match="//pra:PraAllaccioFognatura">

<xsl:element name="{'PraAllaccioFognatura'}"> 

<!--Richiedente-->
<xsl:for-each select="//pra:Soggetto[@attr='richiedente']">
      <xsl:call-template name="Soggetto" >
        <xsl:with-param name="tag" select="'Richiedente'" />
      </xsl:call-template>
 </xsl:for-each>
 
 <!--RichiedenteGiuridico -->
 <xsl:for-each select="//pra:SoggettoGiuridico[@attr='richiedenteGiuridico']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'RichiedenteGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
  </xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodSettoreAttivita" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:SettoreAttivita" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodTipoAttivita" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TipoAttivita" />
</xsl:call-template>

<xsl:for-each select="//pra:UnitaImmobiliare[@attr='unitaImmobiliare']">
<xsl:call-template name="UnitaImmobiliare" /> 
</xsl:for-each>

<xsl:for-each select="//pra:DatiPermesso[@attr='estremiPermesso']">
<xsl:if test="$output = 'html' and  //pra:DatiPermesso[@attr='estremiPermesso']/pra:Numero !=''">
			<h3><xsl:value-of select="'Estremi Permesso'"/></h3>
</xsl:if>	
<xsl:call-template name="DatiPermesso" >
	<xsl:with-param name="tag" select="'EstremiPermesso'"/>
</xsl:call-template> 
</xsl:for-each>

<xsl:for-each select="//pra:DatiPermesso[@attr='estremiCondono']">
<xsl:if test="$output = 'html' and  //pra:DatiPermesso[@attr='estremiCondono']/pra:Numero !=''">
		<h3><xsl:value-of select="'Estremi Condono'"/></h3>
</xsl:if>	
<xsl:call-template name="DatiPermesso" >
	<xsl:with-param name="tag" select="'EstremiCondono'"/>
</xsl:call-template>
</xsl:for-each>

<xsl:for-each select="//pra:DatiUnitaImmobiliare[@attr='datiUnitaImmobiliareDomestica']">
<xsl:call-template name="DatiUnitaImmobiliare" >
	<xsl:with-param name="tag" select="'DatiUnitaImmobiliareDomestica'"/>
</xsl:call-template>
</xsl:for-each>

<xsl:for-each select="//pra:DatiUnitaImmobiliare[@attr='datiUnitaImmobiliareDomestica']">
<xsl:call-template name="DatiUnitaImmobiliare" >
	<xsl:with-param name="tag" select="'DatiUnitaImmobiliareProduttiva'"/>
</xsl:call-template>
</xsl:for-each>

<xsl:for-each select="//pra:Dati[@attr='listaDatiAllaccio']">
<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Allaccio'"/></h3>
</xsl:if>	
<xsl:call-template name="DatiVari" >
	<xsl:with-param name="tag" select="'DatiAllaccio'"/>
</xsl:call-template>
</xsl:for-each>

<xsl:for-each select="//pra:Dati[@attr='listaDatiApproviggionamento']">
<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Approvvigionamento'"/></h3>
</xsl:if>	
<xsl:call-template name="DatiVari" >
	<xsl:with-param name="tag" select="'DatiApprovvigionamento'"/>
</xsl:call-template>
</xsl:for-each>

<!--Pratica-->  
<xsl:call-template name="Pratica" /> 

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="//pra:CodStato" />
</xsl:call-template>      

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="//pra:DesStato" />
</xsl:call-template>

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="//pra:DataStato" />
</xsl:call-template>   

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="//pra:Note" />
</xsl:call-template> 

</xsl:element>
</xsl:template>

</xsl:stylesheet>

