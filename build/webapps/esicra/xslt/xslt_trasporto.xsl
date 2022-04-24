<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<!--xsl:include href="xslt_responsabile.xsl" /-->

   <xsl:template name="DatiTrasporto">

      <xsl:element name="{'DatiTrasporto'}">
           
             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiTrasporto/pra:Partenza" />
             </xsl:call-template>
         
             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiTrasporto/pra:Arrivo" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="//pra:DatiTrasporto/pra:TipoPercorso" />
             </xsl:call-template>

             <!--DatiResponsabile-->  
             <xsl:call-template name="DatiResponsabile" />
             
      </xsl:element>        

   </xsl:template>
</xsl:stylesheet>

