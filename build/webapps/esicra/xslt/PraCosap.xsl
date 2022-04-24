<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
<xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />
<!--
<xsl:include href="xslt_elemento.xsl" />
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_sog_giur.xsl" />
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_professionista.xsl" />
<xsl:include href="xslt_indirizzo.xsl" />
<xsl:include href="xslt_catasto.xsl" />
<xsl:include href="xslt_cosap.xsl" />
<xsl:include href="xslt_datipermesso.xsl" />
<xsl:include href="xslt_recapito_cosap.xsl" />
-->
  <xsl:template    match="/">
	<xsl:call-template name="PraCosap" />
  </xsl:template>  
  
   <xsl:template name="PraCosap" match="//pra:PraCosap">
	<xsl:element name="{'PraCosap'}">
    
<!--SoggettoRichiedente-->
	<xsl:for-each select="//pra:SchedaSoggetto[@attr='Richiedente']">
        <xsl:call-template name="SchedaSoggetto">
        <xsl:with-param name="attr" select="'Richiedente'" />
          <xsl:with-param name="newtag" select="'Richiedente'" />
        </xsl:call-template>
    </xsl:for-each>
     
<!--
<xsl:call-template name="RecapitoCosap" />
-->

<!--SoggettoFruitoreFisico-->
  <xsl:if test="//pra:SchedaSoggetto[@attr='FruitoreFisico']/pra:Soggetto/pra:Cognome  != ''">
	<xsl:call-template name="SchedaSoggetto">
		<xsl:with-param name="nodo" select="//pra:SchedaSoggetto[@attr='FruitoreFisico']" />
	   <xsl:with-param name="attr" select="'FruitoreFisico'" />
	  <xsl:with-param name="newtag" select="'SoggettoFruitoreFisico'" />
	</xsl:call-template>
 </xsl:if>
 
 <!--SoggettoFruitoreGiuridico-->
<xsl:if test="//pra:SoggettoGiuridico/pra:PartitaIva  != ''">
      <xsl:for-each select="//pra:SoggettoGiuridico[@attr='FruitoreGiuridico']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'SoggettoFruitoreGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>
</xsl:if>
	
<xsl:if test="//pra:AttivitaCommerciale/pra:CodSettoriMerceologici  != ''">
	 <xsl:if test="$output = 'html' ">
		<h3>Attivita Commerciale</h3>
	  </xsl:if>
	 <xsl:for-each select="//pra:AttivitaCommerciale">
	        <xsl:call-template name="AttivitaCommerciale" />
	</xsl:for-each>
</xsl:if>
	
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodTipoPermesso" />
      </xsl:call-template>
      
       <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesTipoPermesso" />
      </xsl:call-template>

	  <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodTipoRichiesta" />
      </xsl:call-template>

	<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesTipoRichiesta" />
      </xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DataInizio" />
      </xsl:call-template>

		<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DataFine" />
      </xsl:call-template>
     
      <xsl:if test="$output = 'html' ">
		<h3>AreaOccupata</h3>
	  </xsl:if> 
	<xsl:if test="//pra:SuoloCosap/pra:Indirizzo/pra:DesArea  != '' ">
	 <xsl:for-each select="//pra:SuoloCosap">
      <xsl:call-template name="SuoloCosap">
          <xsl:with-param name="tag" select="'AreaOccupata'" />
      </xsl:call-template>
      </xsl:for-each>
	</xsl:if>
        
       
	<xsl:for-each select="//pra:DatiPermesso[@attr='DatiPermesso']">
		 <xsl:if test="$output = 'html' ">
		<h3>Dati Permesso</h3>
	  </xsl:if> 
		<xsl:call-template name="DatiPermesso">
          <xsl:with-param name="tag" select="'DatiPermesso'" />
      </xsl:call-template>
      </xsl:for-each>
     
    
      <xsl:if test="//pra:DurataTemporaneaPratica  != '' ">
		  <xsl:if test="$output = 'html' ">
		 <h3>DurataTemporanea Pratica</h3>
	    </xsl:if>  
		<xsl:call-template name="DurataTemporaneaPratica">
          <xsl:with-param name="tag" select="'DurataTemporaneaPratica'" />
      </xsl:call-template>
      </xsl:if>
	
		<xsl:call-template name="Pratica">
          <xsl:with-param name="tag" select="'Pratica'" />
      </xsl:call-template>
	
      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:Stato" />
          <xsl:with-param name="alias" select="'CodStato'" />
      </xsl:call-template>
      
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Note" />
      </xsl:call-template>

	</xsl:element>

   </xsl:template>
</xsl:stylesheet>

