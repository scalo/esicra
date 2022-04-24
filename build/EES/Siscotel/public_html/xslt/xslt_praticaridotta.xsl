<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<xsl:template name="PraticaRidotta">
       <xsl:variable name="tag" select="'Dati Pratica'"/>
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
			<h3><xsl:value-of select="$tag" /></h3>
			<blockquote>
			<xsl:call-template name="PraticaBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="PraticaBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>

   <xsl:template name="PraticaBase">

      <xsl:element name="{'Pratica'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:IdPratica" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:IdEnteDestinatario" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:DataPratica" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Oggetto" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:IdServizio" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:NomeUtente" />
         </xsl:call-template>
         
        <!--Recapito-->
        <xsl:if test="//pra:Recapito/pra:DatiIndirizzo/pra:DesArea  != '' ">
        		<xsl:if test="$output = 'html' ">
        			<h3>Recapito</h3>
               	</xsl:if>
                 <xsl:call-template name="RecapitoRidotto" />
        </xsl:if>
	</xsl:element>
   </xsl:template>
</xsl:stylesheet>

