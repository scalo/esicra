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
                  <xsl:with-param name="nomeelemento" select="//pra:Pratica/IdServizio" />
               </xsl:call-template>

               <xsl:call-template name="elemento_alias">
                  <xsl:with-param name="nomeelemento" select="pra:IdAllegato" />

                  <xsl:with-param name="alias" select="'CodAllegato'" />
               </xsl:call-template>

               <xsl:element name="{'DesTipoAllegato'}">
                  <xsl:value-of select="'Documento'" />
               </xsl:element>

               <xsl:call-template name="elemento_alias">
                  <xsl:with-param name="nomeelemento" select="pra:Ref" />

                  <xsl:with-param name="alias" select="'NomeAllegato'" />
               </xsl:call-template>
            </xsl:element>
         </xsl:for-each>
      </xsl:element>
   </xsl:template>

</xsl:stylesheet>