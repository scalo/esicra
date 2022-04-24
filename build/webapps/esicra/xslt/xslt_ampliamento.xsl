<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="Ampliamento">

         <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiAmpliamento">
           <xsl:element name="DatiAmpliamento">
         
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DimAmpliamento" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DimRiduzione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:SuperficieComplessiva" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DimensioneFinale" />
           </xsl:call-template>

           </xsl:element>
         </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

