<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="AperturaSub">

         <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiSubentro">
           <xsl:element name="DatiSubentro">
         
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:CausaSubentro" />
           </xsl:call-template>
           
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:Denominazione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:PartitaIVA" />
           </xsl:call-template>

           </xsl:element>
         </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

