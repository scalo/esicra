<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<!--
<xsl:include href="xslt_sog_indirizzo.xsl" />
 -->
 
  <xsl:template name="Soggetto">
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
			<xsl:call-template name="SoggettoBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="SoggettoBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>
  
   <xsl:template name="SoggettoBase">
       <xsl:param name="tag" />
        <xsl:element name="{$tag}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:CodiceFiscale" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Cognome" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Nome" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DataNascita" />
         </xsl:call-template>

         <xsl:element name="{'LuogoNascita'}">
           <xsl:element name="{'ComuneNascita'}">

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

  <!--Indirizzo Soggetto-->
         <xsl:call-template name="SogIndirizzo">
            <xsl:with-param name="tag" select="'Residenza'" />
            <xsl:with-param name="selezione" select="'Residenza'" />
         </xsl:call-template>

      </xsl:element>

   </xsl:template>
</xsl:stylesheet>