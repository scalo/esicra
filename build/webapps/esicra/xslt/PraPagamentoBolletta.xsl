<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

   <xsl:template name="PraPagamentoBolletta"  match="/">
         <xsl:apply-templates select="//pra:PraPagamentoBolletta" />
   </xsl:template>
 
 <xsl:template  match="//pra:PraPagamentoBolletta">

<xsl:element name="{'PraPagamentoBolletta'}"> 

<xsl:for-each select="//pra:Soggetto[@attr='SoggettoPagante']">
      <xsl:call-template name="Soggetto" >
        <xsl:with-param name="tag" select="'Richiedente'" />
      </xsl:call-template>
 </xsl:for-each>
      
<!--SoggettoFruitoreFisico-->
 <xsl:if test="//pra:Soggetto/pra:Cognome != ''">
      <xsl:for-each select="//pra:Soggetto[@attr='BeneficiarioFisico']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'BeneficiarioFisico'" />
        </xsl:call-template>
      </xsl:for-each>
 </xsl:if>
 
 <!--SoggettoGiuridicoFruitore -->
 <xsl:if test="//pra:SoggettoGiuridico/pra:RagioneSociale != ''">
 <xsl:for-each select="//pra:SoggettoGiuridico[@attr='BeneficiarioGiuridico']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'BeneficiarioGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>
 </xsl:if>

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:CodBolletta" />
</xsl:call-template>      

<!--Pratica-->  
<xsl:call-template name="Pratica" /> 

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:CodStato" />
 <xsl:with-param name="alias" select="'CodStato'" />
</xsl:call-template>      

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:DesStato" />
</xsl:call-template>

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:DataStato" />
</xsl:call-template>   

<xsl:call-template name="elemento">
 <xsl:with-param name="nomeelemento" select="pra:Note" />
</xsl:call-template> 

</xsl:element>
</xsl:template>

</xsl:stylesheet>

