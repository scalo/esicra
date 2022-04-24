<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/importazione/xml/esicra_pra">   
   <xsl:template name="ListaVeicolo">

      <xsl:element name="{'ListaVeicolo'}">
         <xsl:for-each select="//pra:ListaPolVeicolo/pra:PolVeicolo">
            <xsl:call-template name="Veicolo"/>
         </xsl:for-each>
      </xsl:element>

   </xsl:template>
   
   <xsl:template name="Veicolo">
		   <xsl:element name="Veicolo">
               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Targa" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:CodTipoVeicolo" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:DesTipoVeicolo" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Marca" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Modello" />
               </xsl:call-template>
            </xsl:element>
             <xsl:if test="$output = 'html' ">
			         <b>Permesso Richiest</b>:
            </xsl:if>
            <xsl:element name="{'RichiestoPermesso'}">
               <xsl:if test="position() = 1">
                  <xsl:value-of select="'S'" />
               </xsl:if>

               <xsl:if test="position() &gt; 1">
                  <xsl:value-of select="'N'" />
               </xsl:if>
            </xsl:element>
           <xsl:if test="$output = 'html' ">
			         <br/><br/>
            </xsl:if>
      
   </xsl:template>
</xsl:stylesheet>
