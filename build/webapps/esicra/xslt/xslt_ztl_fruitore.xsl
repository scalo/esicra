<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">   

   <xsl:template name="FruitoreZTL">
       <xsl:param name="tag" />

       <xsl:element name="{'ListaSoggettoFruitore'}"> <!--messo per validazione-->
       <xsl:element name="{'SoggettoFruitore'}">
       <xsl:element name="{'Soggetto'}"> <!--messo per validazione-->

         <xsl:call-template name="elemento_alias">
            <xsl:with-param name="alias" select="'CodSoggetto'" />
            <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:CodiceFiscale" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:CodiceFiscale" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:Cognome" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:Nome" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:DataNascita" />
         </xsl:call-template>

         <xsl:element name="{'ComuneNascita'}">
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:Comune/pra:DesComune" />
            </xsl:call-template>

            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:Soggetto[@attr='soggettoFruitore']/pra:Comune/pra:DesProvincia" />
            </xsl:call-template>
         </xsl:element>
      </xsl:element>
      </xsl:element>
      </xsl:element>

   </xsl:template>
</xsl:stylesheet>