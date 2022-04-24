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
<xsl:include href="xslt_pubblicita.xsl" />
-->


   <xsl:template name="PraInstallazionePubblicita" match="/">
         <xsl:apply-templates select="//pra:PraInstallazionePubblicita" />
   </xsl:template>
 
 <xsl:template match="//pra:PraInstallazionePubblicita">

<xsl:element name="{'PraInstallazionePubblicita'}"> 

<!--Richiedente-->
<xsl:for-each select="//pra:Soggetto[@attr='Richiedente']">
      <xsl:call-template name="Soggetto" >
        <xsl:with-param name="tag" select="'Richiedente'" />
      </xsl:call-template>
 </xsl:for-each>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
      </xsl:call-template>
      
 
 <!--SoggettoGiuridicoFruitore -->
 <xsl:for-each select="//pra:SoggettoGiuridico[@attr='SoggettoFruitoreGiuridico']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'SoggettoFruitoreGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>

<xsl:for-each select="//pra:TipoPubblicita[@attr='DatiPubblicita']">
	<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Pubblicità'"/></h3>
	</xsl:if>	
	<xsl:call-template name="TipoPubblicita" /> 
</xsl:for-each>


<xsl:for-each select="//pra:PeriodoInstallazione">
	<xsl:if test="$output = 'html' ">
		<h3><xsl:value-of select="'Periodo Installazione'"/></h3>
	</xsl:if>	
	<xsl:call-template name="PeriodoInstallazione" /> 
</xsl:for-each>


<xsl:for-each select="//pra:EventoInstallazione[@attr='DatiEvento']">
	<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Evento Installazione'"/></h3>
	</xsl:if>	
<xsl:call-template name="EventoInstallazione" /> 
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

