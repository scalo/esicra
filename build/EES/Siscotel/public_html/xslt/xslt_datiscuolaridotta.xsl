<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
  
   <xsl:template name="DatiScuolaRidotta">


     <xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:Scuola" />
     </xsl:call-template>

     <xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:Classe" />
     </xsl:call-template>
     
     <xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:DatiScuola/pra:AnnoScolastico" />
     </xsl:call-template>
      
    </xsl:template>
</xsl:stylesheet>

