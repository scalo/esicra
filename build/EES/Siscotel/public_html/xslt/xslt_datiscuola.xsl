<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
  
  <!--
   <xsl:include href="xslt_scu_indirizzo.xsl" />
   <xsl:include href="xslt_classe.xsl" />
   -->
   <xsl:template name="DatiScuola">

      <xsl:element name="{'DatiScuola'}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:Denominazione" />
         </xsl:call-template>

<!--Indirizzo della scuola-->
		<xsl:if test="$output = 'html' ">
			<h4><xsl:value-of select="'Indirizzo Scuola'"/></h4>
	    </xsl:if>
         <xsl:call-template name="IndirizzoScuola" />

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:TipoScuola" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:NumeroDisponibile" />
         </xsl:call-template>

<!--Classi-->
		<xsl:if test="$output = 'html' ">
			<h4><xsl:value-of select="'Elenco classi'"/></h4>
	    </xsl:if>
         <xsl:call-template name="ListaClasse" />

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:Note" />
         </xsl:call-template>
        
      </xsl:element>
      
    </xsl:template>
</xsl:stylesheet>

