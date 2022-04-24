<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">


   <xsl:template name="RecapitoRidotto">

  <xsl:for-each select="//pra:Pratica/pra:Recapito">   
      <xsl:element name="{'Recapito'}">
      <xsl:element name="{'Indirizzo'}">

<!--Indirizzo del recapito-->
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:SpecieArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:DesArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:NumCiv" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:LetCiv" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:Scala" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:Interno" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:Cap" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:Comune" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiIndirizzo/pra:Provincia" />
         </xsl:call-template>
         
      </xsl:element>
      
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Presso" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Telefono" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Email" />
         </xsl:call-template>

      </xsl:element>
      </xsl:for-each>
   </xsl:template>
</xsl:stylesheet>

