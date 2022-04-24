<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />
<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_datiscuola.xsl" />
<xsl:include href="xslt_situazioneeco.xsl" />
<xsl:include href="xslt_daticentro.xsl" />
-->

   <xsl:template name="PraRichiestaIscrizioneCentri" match="//pra:PraRichiestaIscrizioneCentri">
	
	<xsl:element name="{'PraRichiestaIscrizioneCentri'}">

<!--SoggettoRichiedente-->
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoRichiedente']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoRichiedente'" />
        </xsl:call-template>
      </xsl:for-each>


<!--SoggettoFruitore-->      
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoFruitore']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoFruitore'" />
        </xsl:call-template>
      </xsl:for-each> 

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
      </xsl:call-template>



<!--DatiScuola-->
	   <xsl:if test="$output = 'html' ">
			<h3>Dati Scuola</h3>
	  </xsl:if>		
      <xsl:call-template name="DatiScuola" />

<!--DatiCentro--> 
	  <xsl:if test="$output = 'html' ">
			<h3>Dati Centro Sportivo</h3>
	  </xsl:if>		
      <xsl:call-template name="DatiCentro" />

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:FlgEsenzione" />
      </xsl:call-template>

<!--DatiEsenzione-->
	  <xsl:if test="$output = 'html' ">
			<h3>Dati Esenzione</h3>
	  </xsl:if>		
      <xsl:call-template name="DatiSituazioneEco" >
			  <xsl:with-param name="nomeelemento" select="'DatiEsenzione'" />
      </xsl:call-template>

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:Stato" />
         <xsl:with-param name="alias" select="'CodStato'" />
      </xsl:call-template>      
      
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataStato" />
      </xsl:call-template>   

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:Note" />
      </xsl:call-template>   
	
		</xsl:element>
	
   </xsl:template>
</xsl:stylesheet>

