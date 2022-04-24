<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="Pagamento">

     <xsl:element name="{'Pagamenti'}">
    	<xsl:for-each select="//pra:Pagamento/pra:Pagamento">

           <xsl:element name="{'Pagamento'}">
			  
              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:IdPratica" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:CodTipoPagamento" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:CodEnteTipoPagamento" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:DesTipoPagamento" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:CausalePagamento" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:DataPagamento" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:DataRegistrazione" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:ImportoPagato" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:ImportoDaPagare" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:ImportoTotale" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:IdCanale" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:DesCanale" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:Distinta" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:Autorizzazione" />
              </xsl:call-template>

              <xsl:call-template name="elemento">
                 <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Pagamento/pra:Pagamento/pra:NumRata" />
              </xsl:call-template>
           </xsl:element>
			<xsl:if test="$output = 'html' ">
			<br/><br/>
       	</xsl:if>
         </xsl:for-each>
     </xsl:element>

   </xsl:template>
</xsl:stylesheet>

