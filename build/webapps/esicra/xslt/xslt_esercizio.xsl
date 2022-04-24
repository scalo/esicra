<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

  <xsl:template name="Esercizio">
  <xsl:element name="{'Esercizio'}">

  <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Esercizio">

  <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Esercizio/pra:DatiEsercizio">

      <xsl:element name="{'DatiEsercizio'}">
      
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
      </xsl:element>
    </xsl:for-each> <!--DatiEsercizio-->

<!--ListaDatiSettoreMerceologico-->
  <xsl:for-each select="pra:DatiSettoreMerceologico [@attr='ListaDatiSettoriMerceologici']">
  <xsl:element name="{'ListaDatiSettoreMerceologico'}">
  <xsl:for-each select="pra:DatiSettoreMerceologico">
      <xsl:element name="{'DatiSettoreMerceologico'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DesGenereSettore" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:CodGenereSettore" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Superficie" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:FlgTabellaSpeciale" />
         </xsl:call-template>
      </xsl:element>
  </xsl:for-each>
  </xsl:element>      
  </xsl:for-each>
   
      <xsl:for-each select="pra:DatiCarattereEsercizio">
      <xsl:if test="//pra:FlgStagionale != ''">
      <xsl:element name="{'DatiCarattereEsercizio'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:FlgStagionale" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DataInizio" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DataFine" />
         </xsl:call-template>
      </xsl:element>
      </xsl:if>
      </xsl:for-each>

  <xsl:for-each select="pra:DatiCentroCommerciale">
  <xsl:if test="//pra:FlgAppartenenzaCentro != ''">
      <xsl:element name="{'DatiCentroCommerciale'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:FlgAppartenenzaCentro" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Denominazione" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:NumeroProvvedimento" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DesEnteCertificante" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DataProvvedimento" />
         </xsl:call-template>
      </xsl:element>
  </xsl:if>
  
  </xsl:for-each> <!--Dati Centro-->

  </xsl:for-each> <!--Esercizio-->

      </xsl:element>
  </xsl:template>
</xsl:stylesheet>

