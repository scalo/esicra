<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="Sospensione">

         <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiSospensione">
           <xsl:element name="DatiSospensione">
         
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DesAttivita" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DataInizioSospensione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DataFineSospensione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:Note" />
           </xsl:call-template>

           </xsl:element>
         </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

