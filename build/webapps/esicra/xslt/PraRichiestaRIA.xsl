<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_indirizzo.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_componenteFamiglia.xsl" />
<xsl:include href="xslt_aire.xsl" />
-->

<xsl:template name="PraRichiestaRIA" match="//pra:PraRichiestaRIA">

<xsl:element name="{'PraRichiestaRIA'}">

<!--Richiedente-->

<xsl:call-template name="SchedaSoggetto">
  <xsl:with-param name="nodo" select="'//pra:SchedaSoggetto'" /> 
  <xsl:with-param name="newtag" select="'Richiedente'" />
  <xsl:with-param name="attr" select="'Richiedente'" />
</xsl:call-template>


<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:TipoAggregazioneFamiliare" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodFiscaleIntestatarioFamiglia" />
</xsl:call-template>		
		
<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:CognomeIntestatarioFamiglia" />
</xsl:call-template>

<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:NomeIntestatarioFamiglia" />
</xsl:call-template>

<xsl:if test="$output = 'html' ">
			<h2>Nucleo Familiare</h2>
</xsl:if>	
<xsl:element name="{'ListaNucleoFamiliare'}">
 <xsl:for-each select="//pra:ComponenteFamiglia[@attr='NucleoFamiliare']/pra:ComponenteFamiglia">
        <xsl:call-template name="ComponenteFamiglia">
          <xsl:with-param name="tag" select="'ComponenteFamiglia'" />
        </xsl:call-template>
 </xsl:for-each>
 </xsl:element>


 <xsl:for-each select="//pra:DatiEspatrio">
	 <xsl:if test="$output = 'html' ">
			<h3>Dati Espatrio</h3>
	</xsl:if>	
<xsl:call-template name="DatiEspatrio" />
</xsl:for-each>

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:FlgApr4" />
  </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaRIA/pra:CodStato" />
      </xsl:call-template>

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaRIA/pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaRIA/pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraRichiestaRIA/pra:Note" />
      </xsl:call-template>   


</xsl:element>

   </xsl:template>
</xsl:stylesheet>

