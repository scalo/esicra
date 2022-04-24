<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

  <xsl:template name="DatiSoggetto">
        <xsl:param name="tag" />
        <xsl:param name="attr" />
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
                   <xsl:variable name="label">
		   <xsl:call-template name="decodeLabel">
			     <xsl:with-param name="str" select="$tag" />
			</xsl:call-template>
	   </xsl:variable>
			<h3><xsl:value-of select="$label" /></h3>
			<blockquote>
			<xsl:call-template name="DatiSoggettoBase" >
				<xsl:with-param name="tag" select="$tag" />
				<xsl:with-param name="attr" select="$attr" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="DatiSoggettoBase" >
				<xsl:with-param name="tag" select="$tag" />
				<xsl:with-param name="attr" select="$attr" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>
  
   <xsl:template name="DatiSoggettoBase">
       
       <xsl:param name="tag" />
       <xsl:param name="attr" />
       
       <xsl:for-each select="//pra:DatiSoggetto[@attr=$attr]">
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
            <xsl:with-param name="nomeelemento" select="pra:Sesso" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DataNascita" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:LuogoNascita" />
         </xsl:call-template>


         <!--Indirizzo  Recapito-->
         <xsl:call-template name="DatiIndirizzo">
            <xsl:with-param name="tag" select="'DatiIndirizzo'" />
            <xsl:with-param name="selezione" select="'DatiIndirizzo'" />
         </xsl:call-template>

      </xsl:element>
      
	</xsl:for-each>

   </xsl:template>
</xsl:stylesheet>
