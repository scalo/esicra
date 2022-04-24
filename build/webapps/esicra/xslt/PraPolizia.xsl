<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_ztl_soggetto.xsl" />
<xsl:include href="xslt_ztl_fruitore.xsl" />
<xsl:include href="xslt_ztl_sog_indirizzo.xsl" />
<xsl:include href="xslt_ztl_veicolo.xsl" />
-->

   <xsl:template name ="PraPolizia" match="//pra:PraPermessoZTL|//pra:PraContrassegno|//pra:PraPermessoCircolazione">
	<xsl:element name="{'PraPolizia'}">
      
<!--Soggetto-->
	 <xsl:if test="$output = 'html' ">
			<h2><xsl:value-of select="'Soggetto'"/></h2>
	 </xsl:if>
      <xsl:call-template name="SoggettoZTL">
            <xsl:with-param name="tag" select="'Soggetto'" />
      </xsl:call-template>

<!--Fruitore-->
 <xsl:if test="$output = 'html' ">
			<h2><xsl:value-of select="'Fruitore'"/></h2>
 </xsl:if>
 <xsl:if test="//pra:Soggetto[@attr='soggettoFruitore']/pra:Cognome !='' ">
      <xsl:call-template name="FruitoreZTL">
            <xsl:with-param name="tag" select="'Soggetto'" />
      </xsl:call-template>
 </xsl:if>

<!--Indirizzo-->

      <xsl:call-template name="SogIndirizzo">
            <xsl:with-param name="tag" select="'Indirizzo'" />
      </xsl:call-template>

<!--Recapito-->
      <xsl:call-template name="SogIndirizzo">
            <xsl:with-param name="tag" select="'RecapitoSoggetto'" />
      </xsl:call-template>
      

<!--Patente-->
	 <xsl:if test="//pra:PolPatente/pra:CategoriaPatente/pra:NumeroPatente !=''">
	 <xsl:if test="$output = 'html' ">
			<h2><xsl:value-of select="'Petente'"/></h2>
	 </xsl:if>
     <xsl:element name="{'Patente'}">
         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:PolPatente/pra:CategoriaPatente" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:PolPatente/pra:NumeroPatente" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:PolPatente/pra:DataRilascio" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:PolPatente/pra:DataScadenza" />
         </xsl:call-template>

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:PolPatente/pra:DesEnteRilascio" />
         </xsl:call-template>
      </xsl:element>
      </xsl:if>	


<!--riprende pratica-->
      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:CompFamigliaPatentati" />

         <xsl:with-param name="alias" select="'NumPatentati'" />
      </xsl:call-template>

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:CompFamigliaMaggiorenni" />

         <xsl:with-param name="alias" select="'NumMaggiorenni'" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PolPermesso/pra:DesTipo" />
      </xsl:call-template>

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:Pratica/pra:DataPratica" />

         <xsl:with-param name="alias" select="'DataRichiesta'" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PolPermesso/pra:Motivazione" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PolPermesso/pra:DataInizioValidita" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PolPermesso/pra:DataFineValidita" />
      </xsl:call-template>

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:NumAutoveicoliUso" />

         <xsl:with-param name="alias" select="'NumAuto'" />
      </xsl:call-template>

      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:NumMotoveicoliUso" />

         <xsl:with-param name="alias" select="'NumMoto'" />
      </xsl:call-template>

<!--ListaVeicolo-->
	  <xsl:if test="$output = 'html' ">
			<h2><xsl:value-of select="'Veicoli'"/></h2>
	 </xsl:if>	
	  <xsl:if test="//pra:PolVeicolo/pra:PolVeicolo/pra:Targa !='' ">
        <xsl:call-template name="ListaVeicolo" />
	  </xsl:if>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodZona" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesZona" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:Percorso" />
      </xsl:call-template>
	
	<xsl:if test="$output = 'html' ">
			<h2><xsl:value-of select="'Dati Pratica'"/></h2>
   </xsl:if>
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

      </xsl:element>
	
      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="pra:Stato" />
          <xsl:with-param name="alias" select="'CodStato'" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:Note" />
      </xsl:call-template>
	</xsl:element>
<!--Fine PraPolizia-->
   </xsl:template>
</xsl:stylesheet>

