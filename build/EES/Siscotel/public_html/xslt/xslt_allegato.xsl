<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">   
   <xsl:template name="ListaAllegato">

      <xsl:element name="{'ListaAllegato'}">
 
         <xsl:for-each select="//pra:Allegato/pra:Allegato">

            <xsl:element name="Allegato">
               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:IdPratica" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:IdAllegato" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:CodAllegato" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:DesAllegato" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Ref" />      
               </xsl:call-template>
            </xsl:element>
            <xsl:if test="$output = 'html' ">
			<br/>
		  </xsl:if>
         </xsl:for-each>
      </xsl:element>
   </xsl:template>

</xsl:stylesheet>
