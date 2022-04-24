<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="DatiResponsabile">

      <xsl:element name="{'DatiPersonaResponsabile'}">
           
             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:DatiPersonaResponsabile/pra:CognomeResponsabile" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:DatiPersonaResponsabile/pra:NomeResponsabile" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:DatiPersonaResponsabile/pra:TelAbitazioneResponsabile" />
             </xsl:call-template>
             
             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:DatiPersonaResponsabile/pra:TelLavoroResponsabile" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:DatiPersonaResponsabile/pra:TelCellulareResponsabile" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:DatiPersonaResponsabile/pra:AltroTelefonoResponsabile" />
             </xsl:call-template>

      </xsl:element>        
   </xsl:template>
</xsl:stylesheet>

