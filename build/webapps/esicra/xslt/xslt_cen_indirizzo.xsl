<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">   

   <xsl:template name="IndirizzoCentro">
       <xsl:param name="tag" />
       <xsl:param name="selezione" />

       <xsl:element name="{'IndirizzoCentro'}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:SpecieArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:DesArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:NumCiv" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:LetCiv" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:Scala" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:Interno" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:Cap" />
         </xsl:call-template>

         <xsl:element name="{'Comune'}">
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:Comune/pra:DesComune" />
            </xsl:call-template>

            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Indirizzo/pra:Comune/pra:DesProvincia" />
            </xsl:call-template>
         </xsl:element>
      </xsl:element>

   </xsl:template>
</xsl:stylesheet>