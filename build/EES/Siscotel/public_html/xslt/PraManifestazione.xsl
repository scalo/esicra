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
<xsl:include href="xslt_manifestazione.xsl" />
-->

   <xsl:template name="PraManifestazione" match="/">
         <xsl:apply-templates select="//pra:PraManifestazione" />
   </xsl:template>
 
 <xsl:template match="//pra:PraManifestazione">

<xsl:element name="{'PraManifestazione'}"> 

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

<xsl:for-each select="//pra:DatiEvento">
<xsl:if test="$output = 'html' ">
		<h2><xsl:value-of select="'Dati Evento'"/></h2>
</xsl:if>	
<xsl:call-template name="DatiEvento" />
</xsl:for-each>

<xsl:for-each select="//pra:DichiarazioneAggiuntiva[@attr='ListaDichiarazioneAggiuntiva']">
<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dichiarazioni Aggiuntive'"/></h3>
</xsl:if>
<xsl:call-template name="DichiarazioneAggiuntiva" /> 
</xsl:for-each>

<!--Pratica-->  
<xsl:call-template name="Pratica" /> 

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:CodStato" />
</xsl:call-template>      

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:DesStato" />
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

