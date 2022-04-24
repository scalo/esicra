<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

  <xsl:template name="EsercizioCessa">

  <xsl:for-each select="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiCessazione/pra:Esercizio">

      <xsl:element name="{'Esercizio'}">
      <xsl:element name="{'DatiEsercizio'}">
      
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiEsercizio/pra:NomeTitolare" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiEsercizio/pra:CognomeTitolare" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DatiEsercizio/pra:CodiceFiscaleTitolare" />
         </xsl:call-template>

<!--IndirizzoEsercizio-->
        <xsl:for-each select="pra:DatiEsercizio">
          <xsl:call-template name="Indirizzo">
            <xsl:with-param name="attr" select="'IndirizzoEsercizio'" />
            <xsl:with-param name="newtag" select="'IndirizzoEsercizio'" />
          </xsl:call-template>
        </xsl:for-each>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Superficie" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:FlgAltraAttivita" />
         </xsl:call-template>
      </xsl:element> <!--Fine DatiEsercizio--> 

<!--ListaDatiSettoreMerceologico-->
  <xsl:for-each select="//pra:Esercizio/pra:DatiSettoreMerceologico">
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
  </xsl:for-each>



      </xsl:element>

  </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

