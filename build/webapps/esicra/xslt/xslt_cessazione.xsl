<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="Cessazione">

         <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiCessazione">
           <xsl:element name="DatiCessazione">
         
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DataCessazione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:CausaCessazione" />
           </xsl:call-template>

           </xsl:element>
         </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

