<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_indirizzo.xsl"/>
<xsl:include href="xslt_elemento.xsl" />
<xsl:include href="xslt_sog_giur.xsl" />
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_datisomministrazione.xsl" />
-->

   <xsl:template name="PraSomministrazione" match="/">
         <xsl:apply-templates select="//pra:PraSomministrazione" />
   </xsl:template>
 
 <xsl:template match="//pra:PraSomministrazione">

<xsl:element name="{'PraSomministrazione'}"> 

<!--Richiedente-->
<xsl:for-each select="//pra:Soggetto[@attr='Richiedente']">
      <xsl:call-template name="Soggetto" >
        <xsl:with-param name="tag" select="'Richiedente'" />
      </xsl:call-template>
 </xsl:for-each>

<xsl:call-template name="elemento">
    <xsl:with-param name="nomeelemento" select="//pra:TitolaritaRichiedente" />
</xsl:call-template>
      
<xsl:if test="//pra:Soggetto[@attr='SoggettoFisico']/pra:Cognome !=''">
      <!--SoggettoFruitoreFisico--> 
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoFisico']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoFruitoreFisico'" />
        </xsl:call-template>
      </xsl:for-each>
  </xsl:if>
  
 <!--SoggettoGiuridicoFruitore -->
 <xsl:if test="//pra:SoggettoGiuridico[@attr='SoggettoGiuridico']/text() !=''">
 <xsl:for-each select="//pra:SoggettoGiuridico[@attr='SoggettoGiuridico']">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'SoggettoFruitoreGiuridico'" />
        </xsl:call-template>
 </xsl:for-each>
 </xsl:if>

<!--ListaProduzioneAlimenti-->
<xsl:if test="$output = 'html' ">
			<h3>Elenco Produzione Alimenti</h3>
</xsl:if>	
<xsl:element name="ListaProduzioneAlimenti">
			<xsl:for-each select="//pra:DatiTipologia[@attr='ListaProduzioneAlimenti']/pra:DatiTipologia">
				<xsl:call-template name="DatiProduzioneAlimenti"/>
			</xsl:for-each>
			<xsl:if test="$output = 'html' ">
				<br/>
			</xsl:if>	
</xsl:element>      
     
<!--ListaConfezionamentoAlimenti-->
<xsl:if test="$output = 'html' ">
			<h3>Elenco Confezionamento Alimenti</h3>
</xsl:if>	
<xsl:element name="ListaConfezionamentoAlimenti">
			<xsl:for-each select="//pra:DatiTipologia[@attr='ListaTipologiaAlimenti']/pra:DatiTipologia">
				<xsl:call-template name="DatiConfezionamentoAlimenti"/>
			</xsl:for-each>
			<xsl:if test="$output = 'html' ">
				<br/>
			</xsl:if>
</xsl:element>      
      
<!--ListaTipoEsercizioAlimenti-->
<xsl:if test="$output = 'html' ">
			<h3>Elenco Tipologie Esercizio</h3>
</xsl:if>	
<xsl:element name="ListaTipoEsercizioAlimenti">
			<xsl:for-each select="//pra:DatiTipologia[@attr='ListaTipoEsercizioAlimenti']/pra:DatiTipologia">
				<xsl:call-template name="DatiTipoEsercizioAlimenti"/>
			</xsl:for-each>
			<xsl:if test="$output = 'html' ">
				<br/>
			</xsl:if>
</xsl:element>      
  
<!--DepositoAlimenti-->
<xsl:if test="$output = 'html' ">
			<h3>Deposito Alimenti</h3>
</xsl:if>	
<xsl:element name="DepositoAlimenti">
	<xsl:for-each select="//pra:DepositoAlimenti">
			<xsl:call-template name="DepositoAlimenti"/>
	</xsl:for-each>
</xsl:element>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodCarattereEsercizio" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesCarattereEsercizio" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataInizioDisponibilta" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:PersonaDiRiferimento" />
</xsl:call-template>

<!-- DatiIscrizioneREC -->
<xsl:if test="$output = 'html' ">
			<h3>Dati Iscrizione REC</h3>
</xsl:if>	
<xsl:call-template name="DatiIscrizioneREC" /> 


<!--Pratica-->  
      <xsl:call-template name="Pratica" /> 

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DesStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:CodStato" />
      </xsl:call-template>      

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DataStato" />
      </xsl:call-template>   

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:Note" />
      </xsl:call-template> 

	</xsl:element>
	
   </xsl:template>

</xsl:stylesheet>

