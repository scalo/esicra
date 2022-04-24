<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<!--
<xsl:include href="xslt_pagamento.xsl" /> 
<xsl:include href="xslt_recapito.xsl" />
<xsl:include href="xslt_allegato.xsl" />
-->

<xsl:template name="Pratica">
       <xsl:variable name="tag" select="'Dati Pratica'"/>
       <xsl:choose>
       <xsl:when test="$output = 'html' ">
			<h2><xsl:value-of select="$tag" /></h2>
			<blockquote>
			<xsl:call-template name="PraticaBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
			</blockquote>
       </xsl:when>
       <xsl:otherwise>
		  <xsl:call-template name="PraticaBase" >
				<xsl:with-param name="tag" select="$tag" />
			</xsl:call-template>
       </xsl:otherwise>
       </xsl:choose>
  </xsl:template>

   <xsl:template name="PraticaBase">

      <xsl:element name="{'Pratica'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:IdPratica" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:IdEnteDestinatario" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:DataPratica" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Oggetto" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:IdServizio" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:NoteUtente" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:CodPraticaBo" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:TipoNotifica" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:CodTipoConsegna" />
         </xsl:call-template>
         
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:DesTipoConsegna" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:CodModalitaAllegato" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:DesModalitaAllegato" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:Canale" />
         </xsl:call-template>

<!--Pagamento-->
		
		<xsl:if test="pra:Pagamento/pra:Pagamento/pra:IdPratica  != '' ">
			<xsl:if test="$output = 'html' ">
			<h3>Pagamenti</h3>
       	</xsl:if>
			 <xsl:call-template name="Pagamento" />
	    </xsl:if>

<!--Recapito-->
<xsl:if test="pra:Recapito/pra:Indirizzo/pra:DesArea  != '' ">
		<xsl:if test="$output = 'html' ">
			<h3>Recapito</h3>
       	</xsl:if>
         <xsl:call-template name="Recapito" />
</xsl:if>
		
<!--Allegato-->
      <xsl:if test="pra:Allegato[@attr='listaAllegato']/pra:Allegato/pra:DesAllegato !=''">
           <xsl:if test="$output = 'html' ">
			<h3>Elenco Allegati</h3>
		  </xsl:if>
         <xsl:call-template name="ListaAllegato" />
      </xsl:if>
 </xsl:element>

   </xsl:template>
</xsl:stylesheet>

