<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<!--
<xsl:template name="Recapito">
       <xsl:variable name="tag" select="'Recapito'"/>
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
			<h2><xsl:value-of select="$tag" /></h2>
			<blockquote>
			<xsl:call-template name="RecapitoBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="RecapitoBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>
-->

   <xsl:template name="Recapito">

  <xsl:for-each select="//pra:Pratica/pra:Recapito">   
      <xsl:element name="{'Recapito'}">
      <xsl:element name="{'Indirizzo'}">

<!--Indirizzo del recapito-->
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:SpecieArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:DesArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:NumCiv" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:LetCiv" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Scala" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Interno" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Cap" />
         </xsl:call-template>

         <xsl:element name="{'Comune'}">
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Comune/pra:DesComune" />
            </xsl:call-template>

            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Comune/pra:DesProvincia" />
            </xsl:call-template>
         </xsl:element>
         <xsl:if test="//pra:Localita != ''">
         <xsl:element name="{'Localita'}">
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Localita/pra:CodIstatStato" />
            </xsl:call-template>

            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Localita/pra:DesStato" />
            </xsl:call-template>

            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Indirizzo/pra:Localita/pra:DesLocalita" />
            </xsl:call-template>
         </xsl:element>
         </xsl:if>
         
      </xsl:element>
      
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Recapito/pra:Presso" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Recapito/pra:Telefono" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Recapito/pra:Email" />
         </xsl:call-template>

      </xsl:element>
      </xsl:for-each>
   </xsl:template>
</xsl:stylesheet>

