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
-->

<xsl:template name="PraDichiarazioneCRD"  match="//pra:PraDichiarazioneCRD">

<xsl:element name="{'PraDichiarazioneCRD'}">

<!--Richiedente-->

<xsl:call-template name="SchedaSoggetto">
  <xsl:with-param name="nodo" select="'//pra:SchedaSoggetto'" /> 
  <xsl:with-param name="newtag" select="'Richiedente'" />
  <xsl:with-param name="attr" select="'richiedente'" />
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

<xsl:element name="{'ListaNucleoFamiliare'}">
<xsl:if test="$output = 'html' ">
			<h2>Nucleo Familiare</h2>
</xsl:if> 
<xsl:for-each select="//pra:ComponenteFamiglia[@attr='listaNucleoFamiliare']/pra:ComponenteFamiglia">
        <xsl:call-template name="ComponenteFamiglia">
          <xsl:with-param name="tag" select="'ComponenteFamiglia'" />
        </xsl:call-template>
</xsl:for-each>
</xsl:element>


<xsl:call-template name="IndirizzoCRD">
					<xsl:with-param name="attr" select="'indirizzoEmigrazione'"/>
					<xsl:with-param name="newtag" select="'IndirizzoEmigrazione'"/>
</xsl:call-template>

<xsl:call-template name="IndirizzoCRD">
					<xsl:with-param name="attr" select="'indirizzoImmigrazione'"/>
					<xsl:with-param name="newtag" select="'IndirizzoImmigrazione'"/>
</xsl:call-template>

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:FlgApr4" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodStato" />
      </xsl:call-template>

  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Note" />
      </xsl:call-template>   


  </xsl:element>
</xsl:template>
  
<xsl:template name="IndirizzoCRD">

       <xsl:param name="attr" />
       <xsl:param name="newtag" />
       
       <xsl:for-each select="//pra:Indirizzo[@attr=$attr]">
       <xsl:if test="$output = 'html' ">
       <xsl:variable name="label">
		   <xsl:call-template name="decodeLabel">
			     <xsl:with-param name="str" select="$newtag" />
			</xsl:call-template>
	   </xsl:variable>
			 <h3><xsl:value-of select="$label" /></h3>
			 </xsl:if>
       <xsl:element name="{$newtag}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:SpecieArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DesArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:NumCiv" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:LetCiv" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Scala" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Interno" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Cap" />
         </xsl:call-template>

         <xsl:element name="{'Comune'}">
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Comune/pra:CodIstatComune" />
            </xsl:call-template>
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Comune/pra:DesComune" />
            </xsl:call-template>
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Comune/pra:DesProvincia" />
            </xsl:call-template>
         </xsl:element>
      </xsl:element>
      </xsl:for-each> 

   </xsl:template>
   
</xsl:stylesheet>

