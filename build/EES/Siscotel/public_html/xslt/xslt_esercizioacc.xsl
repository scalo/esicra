<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

  <xsl:template name="EsercizioAcc">

  <xsl:for-each select="//pra:DatiDenunciaDAC/pra:TipoEsercizio/pra:DatiEsercizio/pra:DatiEsercizio">

      <xsl:element name="{'EsercizioAcc'}">
      
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:NomeTitolare" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:CognomeTitolare" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:CodiceFiscaleTitolare" />
         </xsl:call-template>

<!--IndirizzoEsercizio-->

      <xsl:call-template name="Indirizzo">
        <xsl:with-param name="attr" select="'IndirizzoEsercizio'" />
        <xsl:with-param name="newtag" select="'IndirizzoEsercizio'" />
      </xsl:call-template>


         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Superficie" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:FlgAltraAttivita" />
         </xsl:call-template>
      </xsl:element> <!--Esercizio-->

  </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

