<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">   
   <xsl:template name="SoggettoEconomico">

      <xsl:element name="{'ListaSoggettoEconomico'}">
         <xsl:for-each select="//pra:SoggettoEconomico/pra:SoggettoEconomico">
            <xsl:if test="$output = 'html' ">
			<h4>Soggetto Economico</h4>
			</xsl:if>	
            <xsl:element name="SoggettoEconomico">
               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Cognome" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Nome" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:RedditoLordo" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:FonteReddito" />
               </xsl:call-template>
            </xsl:element>

         </xsl:for-each>
      </xsl:element>

   </xsl:template>
</xsl:stylesheet>