<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<xsl:template name="SchedaSoggetto">
       <xsl:param name="nodo" />	
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
			<xsl:call-template name="SchedaSoggettoBase" >
				<xsl:with-param name="nodo" select="$nodo" />
				<xsl:with-param name="attr" select="$attr" />
				<xsl:with-param name="newtag" select="$newtag" />
			</xsl:call-template>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="SchedaSoggettoBase" >
				<xsl:with-param name="nodo" select="$nodo" />
				<xsl:with-param name="attr" select="$attr" />
				<xsl:with-param name="newtag" select="$newtag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>


   <xsl:template name="SchedaSoggettoBase">
       <xsl:param name="nodo" />	
       <xsl:param name="attr" />	
       <xsl:param name="newtag" />	

   <xsl:for-each select="//pra:SchedaSoggetto[@attr=$attr]">

       <xsl:element name="{$newtag}">
       <xsl:element name="{'Soggetto'}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:CodiceFiscale" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:Cognome" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:Nome" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:DataNascita" />
         </xsl:call-template>

         <xsl:element name="{'LuogoNascita'}">
           <xsl:element name="{'ComuneNascita'}">

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:Comune/pra:CodIstatComune" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:Comune/pra:DesComune" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
                <xsl:with-param name="nomeelemento" select="pra:Soggetto/pra:Comune/pra:DesProvincia" />
             </xsl:call-template>
           </xsl:element>
         </xsl:element>

  <!--Indirizzo Soggetto Scheda-->
	   <xsl:for-each select="pra:Soggetto">
             <xsl:call-template name="SogIndirizzo">
               <xsl:with-param name="tag" select="'Residenza'" />
               <xsl:with-param name="selezione" select="'Residenza'" />
             </xsl:call-template>
           </xsl:for-each>
         </xsl:element> <!--Soggetto-->          
         
         <xsl:element name="{'PermessoSoggiorno'}">
           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:ComuneQuestura" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:ProvinciaQuestura" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:Tipo" />
           </xsl:call-template>

           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:NumeroPermesso" />
           </xsl:call-template>           

           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:DataRilascio" />
           </xsl:call-template>  

           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:DataRinnovo" />
           </xsl:call-template> 

           <xsl:call-template name="elemento">
             <xsl:with-param name="nomeelemento" select="pra:PermessoSoggiorno/pra:DataScadenza" />
           </xsl:call-template> 
           
         </xsl:element>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:StatoCivile" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Cittadinanza" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="pra:Certificabile" />
         </xsl:call-template>

       </xsl:element> 

  </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>
