<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="Albo">

           <xsl:element name="IscrizioneAlbo">

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:IscrizioneAlbo/pra:NumeroIscrizioneAlbo " />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:IscrizioneAlbo/pra:IdAlbo" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:IscrizioneAlbo/pra:CodAlbo" />
             </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:IscrizioneAlbo/pra:DesAlbo" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:IscrizioneAlbo/pra:ProvinciaAlbo" />
           </xsl:call-template>

           </xsl:element>

   </xsl:template>
</xsl:stylesheet>

