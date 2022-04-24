<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
<!--
<xsl:include href="xslt_sogeconomico.xsl" />
-->
   <xsl:template name="DatiSituazioneEco">
   
        <xsl:param name="nomeelemento" />		
		
      <xsl:element name="{$nomeelemento}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:CodPraticaBO" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:IdPraticaFO" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:DataPratica" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:TotRedditoFamiliare" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:TotDetrazioni" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:DataScadenzaISEE" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiSituazioneEconomica/pra:ISEE" />
         </xsl:call-template>

<!--Lista SoggettoEconomico-->
         <xsl:call-template name="SoggettoEconomico" />
      </xsl:element>

   </xsl:template>
</xsl:stylesheet>

