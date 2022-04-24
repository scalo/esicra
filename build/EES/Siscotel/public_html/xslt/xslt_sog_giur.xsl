<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">   

<!--
<xsl:include href="xslt_giu_indirizzo.xsl" />
-->

<xsl:template name="SoggettoGiuridico">
       <xsl:param name="tag" />
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
       <xsl:variable name="label">
		   <xsl:call-template name="decodeLabel">
			     <xsl:with-param name="str" select="$tag" />
			</xsl:call-template>
	   </xsl:variable>
			<h3><xsl:value-of select="$label" /></h3>
			<blockquote>
			<xsl:call-template name="SoggettoGiuridicoBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="SoggettoGiuridicoBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>
  
  <xsl:template name="SoggettoGiuridicoBase">
  <xsl:param name="tag" />
  <xsl:if test="$output = 'html' ">
		<h3>Soggetto Giuridico</h3>
  </xsl:if>
       <xsl:element name="{$tag}" >
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:RagioneSociale" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:PartitaIva" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:IdEnte" />
         </xsl:call-template>

  <!--Indirizzo Soggetto Giuridico-->
         <xsl:call-template name="GiuIndirizzo">
            <xsl:with-param name="newtag" select="'IndirizzoSede'" />
         </xsl:call-template>

  <!--Scheda Sogg Rappresentante Legale -->
      <xsl:if test="//pra:SchedaSoggetto[@attr='RappresentanteLegale']/pra:Cognome != ''">
      <xsl:call-template name="SchedaSoggetto" >
        <xsl:with-param name="nodo" select="'pra:SchedaSoggetto'" /><!-- non usato-->
        <xsl:with-param name="attr" select="'RappresentanteLegale'" />
        <xsl:with-param name="newtag" select="'RappresentanteLegale'" />
      </xsl:call-template>
      </xsl:if>

  <!--IscrizioneCCIA-->
       <xsl:element name="{'IscrizioneCCIA'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:NumeroIscrizioneCCIA" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:SedeCCIA" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:IscrizioneCCIA/pra:DataIscrizione" />
         </xsl:call-template>         
      </xsl:element>
      
      </xsl:element>

   </xsl:template>
</xsl:stylesheet>
