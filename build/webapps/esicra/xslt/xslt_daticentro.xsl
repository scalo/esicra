<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<xsl:include href="xslt_cen_indirizzo.xsl" />

   <xsl:template name="DatiCentro">

      <xsl:element name="{'DatiCentro'}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:CodCentro" />
         </xsl:call-template>

         <xsl:call-template name="elemento_alias">
            <xsl:with-param name="alias" select="'DesCentro'" />
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Denominazione" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:TipoCentro" />
         </xsl:call-template>

<!--Indirizzo del centro-->
         <xsl:call-template name="IndirizzoCentro">
            <xsl:with-param name="nomeelemento" select="'IndirizzoCentro'" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:DataInizio" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:DataFine" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:TipoTrasporto" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Retta" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:NumeroDisponibile" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiCentro/pra:Note" />
         </xsl:call-template>
        
      </xsl:element>
    </xsl:template>
</xsl:stylesheet>

