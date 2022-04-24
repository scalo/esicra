<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">   
   
<xsl:template name="Indirizzo">
		
       <xsl:param name="attr" />	
       <xsl:param name="newtag" />	
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
       <xsl:variable name="label">
		   <xsl:call-template name="decodeLabel">
			     <xsl:with-param name="str" select="$newtag" />
			</xsl:call-template>
		</xsl:variable>
			<h3><xsl:value-of select="$label" /></h3>
			<xsl:call-template name="IndirizzoBase" >
				<xsl:with-param name="attr" select="$attr" />
				<xsl:with-param name="newtag" select="$newtag" />
			</xsl:call-template>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="IndirizzoBase" >
				<xsl:with-param name="attr" select="$attr" />
				<xsl:with-param name="newtag" select="$newtag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>
 
   <xsl:template name="IndirizzoBase">
       <xsl:param name="attr" />
       <xsl:param name="newtag" />
       
       <xsl:for-each select="pra:Indirizzo[@attr=$attr and position()=1]">

       <xsl:element name="{$newtag}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:SpecieArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:DesArea" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:NumCiv" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:LetCiv" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Scala" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Interno" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Cap" />
         </xsl:call-template>

         <xsl:element name="{'Comune'}">
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Comune/pra:CodIstatComune" />
            </xsl:call-template>
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Comune/pra:DesComune" />
            </xsl:call-template>
            <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Comune/pra:DesProvincia" />
            </xsl:call-template>
         </xsl:element>
      </xsl:element>
      </xsl:for-each> 

   </xsl:template>
</xsl:stylesheet>
