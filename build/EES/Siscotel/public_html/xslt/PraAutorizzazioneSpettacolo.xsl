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
<xsl:include href="xslt_spettacolo.xsl" />
-->

   <xsl:template name="PraAutorizzazioneSpettacolo" match="/">
         <xsl:apply-templates select="//pra:PraAutorizzazioneSpettacolo" />
   </xsl:template>
 
 <xsl:template match="//pra:PraAutorizzazioneSpettacolo">

<xsl:element name="PraAutorizzazioneSpettacolo"> 

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
 <xsl:for-each select="//pra:SoggettoGiuridico[@attr='SoggettoFruitore']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'SoggettoFruitoreGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>

<xsl:if test="$output = 'html' ">
			<h3>Dati Autorizzazione Attività</h3>
</xsl:if>	
<xsl:for-each select="//pra:DatiAutorizzazioneAttivita">
<xsl:call-template name="DatiAutorizzazioneAttivita" /> 
</xsl:for-each>

<xsl:if test="$output = 'html' ">
			<h3>Dati Richiesta Autorizzazione</h3>
</xsl:if>	
<xsl:for-each select="//pra:DatiRichiestaAutorizzazione">
<xsl:call-template name="DatiRichiestaAutorizzazione" /> 
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

