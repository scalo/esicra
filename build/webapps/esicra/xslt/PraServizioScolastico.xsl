<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_sog_indirizzo.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_trasporto.xsl" />
<xsl:include href="xslt_responsabile.xsl" />
<xsl:include href="xslt_situazioneeco.xsl" />
<xsl:include href="xslt_datiscuola.xsl" />
-->


   <xsl:template name="PraServizioScolastico" match="/">
         <xsl:apply-templates select="//pra:PraServizioScolastico" />
   </xsl:template>

   <xsl:template  match="//pra:PraServizioScolastico">
	
	<xsl:element name="PraServizioScolastico">
	
<!--SchedaSoggettoRichiedente-->
      <xsl:call-template name="SchedaSoggetto" >
        <xsl:with-param name="nodo" select="'//pra:SchedaSoggetto'" /> <!--non usato-->
        <xsl:with-param name="attr" select="'SoggettoRichiedente'" />
        <xsl:with-param name="newtag" select="'SoggettoRichiedente'" />
      </xsl:call-template>

<!--SchedaSoggettoFruitore--> 
<xsl:if test="//pra:SchedaSoggetto[@attr='SoggettoFruitore']/pra:Soggetto/pra:Cognome   !=''">    
      <xsl:call-template name="SchedaSoggetto" >
        <xsl:with-param name="nodo" select="'//pra:SchedaSoggetto'" />
        <xsl:with-param name="attr" select="'SoggettoFruitore'" />
        <xsl:with-param name="newtag" select="'SoggettoFruitore'" />
      </xsl:call-template>
</xsl:if>
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
      </xsl:call-template>

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

<!--DatiScuola-->
	 <xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Scuola'"/></h3>
	 </xsl:if>
      <xsl:call-template name="DatiScuola" />

<!--DatiMensa-->
    <xsl:if test="pra:DatiMensa/pra:TipoMensa !=''">
	  <xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati  Mensa'"/></h3>
	 </xsl:if>	
      <xsl:element name="{'DatiTipoMensa'}">
        <xsl:call-template name="elemento">
           <xsl:with-param name="nomeelemento" select="//pra:DatiTipoMensa/pra:TipoMensa" />
        </xsl:call-template>

        <xsl:call-template name="elemento">
           <xsl:with-param name="nomeelemento" select="//pra:DatiTipoMensa/pra:TipoDieta" />
        </xsl:call-template>

        <xsl:call-template name="elemento">
           <xsl:with-param name="nomeelemento" select="//pra:DatiTipoMensa/pra:FlgEsenzionePagamento" />
        </xsl:call-template>
      </xsl:element>
    </xsl:if>
    
<!--DatiEsenzione-->
<xsl:if test="//pra:DatiSituazioneEconomica/pra:SoggettoEconomico != ''">
     <xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Esenzione'"/></h3>
	 </xsl:if>
      <xsl:call-template name="DatiSituazioneEco" >
		  <xsl:with-param name="nomeelemento" select="'DatiSituazioneEconomica'" />
      </xsl:call-template>
</xsl:if>     
<!--DatiTrasporto -->
<xsl:if test="//pra:DatiTrasporto/pra:Partenza/text() != ''">
	 <xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Trasporto'"/></h3>
	 </xsl:if>
      <xsl:call-template name="DatiTrasporto" />
</xsl:if>

<!--DatiResponsabile-->
<xsl:if test="//pra:DatiTrasporto/pra:DatiPersonaResponsabile/text() != ''">
	  <xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Responsabile'"/></h3>
	 </xsl:if>
      <xsl:call-template name="DatiResponsabile" />
</xsl:if>
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraServizioScolastico/pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraServizioScolastico/pra:Stato" />
      </xsl:call-template>      

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraServizioScolastico/pra:DataStato" />
      </xsl:call-template>   

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PraServizioScolastico/pra:Note" />
      </xsl:call-template>   

	</xsl:element>

   </xsl:template>
</xsl:stylesheet>

