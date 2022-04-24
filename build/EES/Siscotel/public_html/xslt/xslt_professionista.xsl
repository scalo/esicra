<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<!--
<xsl:include href="xslt_sog_indirizzo.xsl"/> /
<xsl:include href="xslt_albo.xsl" />
-->

   <xsl:template name="Professionista">
       <xsl:param name="tag" />


   <xsl:element name="{$tag}">

<!--Soggetto-->
   <xsl:for-each select="pra:Soggetto">
     <xsl:call-template name="Soggetto">
       <xsl:with-param name="tag" select="'Soggetto'" />
         <xsl:with-param name="selezione" select="'Soggetto'" />
       </xsl:call-template>
   </xsl:for-each>

<!--Albo-->
      <xsl:call-template name="Albo" />

      <xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="pra:PartitaIVA" />
      </xsl:call-template>

   </xsl:element>

  </xsl:template>
</xsl:stylesheet>