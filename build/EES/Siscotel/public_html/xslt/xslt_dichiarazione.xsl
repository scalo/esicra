<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="ListaDichiarazione">
	<xsl:if test="//pra:DichiarazioneAggiuntiva/pra:DichiarazioneAggiuntiva != ''">
      <xsl:element name="{'ListaDichiarazioniAggiuntive'}">
         <xsl:for-each select="//pra:DichiarazioneAggiuntiva/pra:DichiarazioneAggiuntiva">
           <xsl:element name="DichiarazioneAggiuntiva">
         
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:CodDichiarazione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:DesDichiarazione" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:FlgSottoscritta" />
           </xsl:call-template>

           </xsl:element>
         </xsl:for-each>
      </xsl:element>        
     </xsl:if>
   </xsl:template>
</xsl:stylesheet>

