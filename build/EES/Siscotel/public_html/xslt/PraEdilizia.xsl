<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
<xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_sog_giur.xsl" />
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_indirizzo.xsl" />
<xsl:include href="xslt_professionista.xsl" />
<xsl:include href="xslt_catasto.xsl" />
<xsl:include href="xslt_ubicazione.xsl" />
-->

   <xsl:template name="PraEdilizia" match="//pra:PraEdilizia">

	<xsl:element name="{'PraEdilizia'}">

<!--SoggettoRichiedente-->
      <xsl:for-each select="//pra:Soggetto[@attr='SoggettoRichiedente']">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'SoggettoRichiedente'" />
        </xsl:call-template>
      </xsl:for-each>

<!--ListaSoggettoCommittenteFisico-->
	 <xsl:if test="pra:Soggetto[@attr='ListaCommittenteFisico']/pra:Soggetto  != '' ">
	 <xsl:if test="$output = 'html' ">
			<h3>Committente Fisico</h3>
	</xsl:if>		
      <xsl:element name="{'ListaCommittenteFisico'}">
      <xsl:for-each select="//pra:Soggetto[@attr='ListaCommittenteFisico']/pra:Soggetto/pra:Cognome">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'CommittenteFisico'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
      </xsl:if>

<!--ListaSoggettoCommittenteGiuridico-->
	 <xsl:if test="pra:SoggettoGiuridico[@attr='ListaCommittenteGiuridico']/pra:SoggettoGiuridico/pra:RagioneSociale != ''">	 
	 <xsl:if test="$output = 'html' ">
			<h3>Committente Giuridico</h3>
	</xsl:if>	
      <xsl:element name="{'ListaCommittenteGiuridico'}">
      <xsl:for-each select="//pra:SoggettoGiuridico[@attr='ListaCommittenteGiuridico']/pra:SoggettoGiuridico">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'CommittenteGiuridico'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
      </xsl:if>

<!--ListaProgettistaFisico-->
	 <xsl:if test="pra:Professionista[@attr='DirettoreLavori']/pra:ProfessionistaFisico/pra:Soggetto/pra:Cognome != '' ">
	  <xsl:if test="$output = 'html' ">
			<h3>Progettista Fisico</h3>
	</xsl:if>	
      <xsl:element name="{'ListaProgettistaFisico'}">
      <xsl:for-each select="//pra:Professionista[@attr='ListaProgettistaFisico']/pra:Professionista">
        <xsl:call-template name="Professionista">
          <xsl:with-param name="tag" select="'ProgettistaFisico'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
      </xsl:if>

<!--ListaProgettistaGiuridico-->
	<xsl:if test="//pra:Professionista[@attr='ListaProgettistaGiuridico']/pra:ProfessionistaGiuridico/pra:RagioneSociale != '' ">
	  <xsl:if test="$output = 'html' ">
			<h3>Progettista Giuridico</h3>
	</xsl:if>	
      <xsl:element name="{'ListaProgettistaGiuridico'}">
      <xsl:for-each select="//pra:Professionista[@attr='ListaProgettistaGiuridico']/pra:Professionista">
        <xsl:call-template name="Professionista">
          <xsl:with-param name="tag" select="'ProgettistaGiuridico'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
    </xsl:if>

<!--DirettoreLavori-->
	 <xsl:if test="//pra:ProfessionistaFisico[@attr='DirettoreLavori']/pra:Soggetto/pra:Cognome != '' ">
	 <xsl:if test="$output = 'html' ">
			<h3>Direttore Lavori</h3>
	</xsl:if>	
      <xsl:for-each select="//pra:ProfessionistaFisico[@attr='DirettoreLavori']">	  
        <xsl:call-template name="Professionista">
          <xsl:with-param name="tag" select="'DirettoreLavori'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:if>


<!--ListaEsecutoreLavoriFisico-->
	<xsl:if test="pra:Soggetto[@attr='ListaEsecutoreLavoriFisico']/pra:Soggetto  != '' ">
	  <xsl:if test="$output = 'html' ">
			<h3>Esecutori Lavori</h3>
	  </xsl:if>		
      <xsl:element name="{'ListaEsecutoreLavoriFisico'}">
      <xsl:for-each select="//pra:Soggetto[@attr='ListaEsecutoreLavoriFisico']/pra:Soggetto">
        <xsl:call-template name="Soggetto">
          <xsl:with-param name="tag" select="'EsecutoreLavoriFisico'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
     </xsl:if>


<!--ListaEsecutoreLavoriGiuridico-->
	 <xsl:if test="//pra:SoggettoGiuridico[@attr='ListaEsecutoreLavoriGiuridico']/pra:SoggettoGiuridico  != ''">
      <xsl:element name="{'ListaEsecutoreLavoriGiuridico'}">
      <xsl:if test="$output = 'html' ">
			<h3>Esecutori Lavori</h3>
	  </xsl:if>		
      <xsl:for-each select="//pra:SoggettoGiuridico[@attr='ListaEsecutoreLavoriGiuridico']/pra:SoggettoGiuridico">
        <xsl:call-template name="SoggettoGiuridico">
          <xsl:with-param name="tag" select="'EsecutoreLavoriGiuridico'" />
        </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
     </xsl:if>

<!--ListaUbicazioneIntervento-->
	<xsl:if test="//pra:Indirizzo[@attr='ListaUbicazioneIntervento'] /pra:Indirizzo!= '' ">
	<xsl:if test="$output = 'html' ">
			<h3>Ubicazione Intervento</h3>
	</xsl:if>	
      <xsl:element name="{'ListaUbicazioneIntervento'}">
      <xsl:for-each select="//pra:Indirizzo">      
         <xsl:call-template name="Ubicazione">
            <xsl:with-param name="newtag" select="'UbicazioneIntervento'" />
          </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
      </xsl:if>

<!--ListaRiferimentiCatastali-->
	<xsl:if test="//pra:InfoCatasto[@attr='ListaRiferimentiCatastali']/pra:InfoCatasto != ''">
	 	<xsl:if test="$output = 'html' ">
			<h3>Riferimenti Catastali</h3>
	    </xsl:if>		
      <xsl:element name="{'ListaRiferimentiCatastali'}">
      <xsl:for-each select="//pra:InfoCatasto[@attr='ListaRiferimentiCatastali']/pra:InfoCatasto">      
         <xsl:call-template name="Catasto">
            <xsl:with-param name="newtag" select="'RiferimentiCatastali'" />
         </xsl:call-template>
      </xsl:for-each>
      </xsl:element>
      </xsl:if>
     
     <xsl:if test="$output = 'html' ">
			<h3>Dati Intervento</h3>
	 </xsl:if>	
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:FlgResponsabileProcedimento" />
      </xsl:call-template>

	   <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodFiscaleCommittenteResponsabile" />
      </xsl:call-template>	

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodFiscaleProgettistaResponsabile" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:IdTipoOpera" />
      </xsl:call-template>
      
       <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesTipoOpera" />
      </xsl:call-template>
      
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:NumConcessione" />
      </xsl:call-template>
      
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DataFineLavori" />
      </xsl:call-template>
	
<!--Pratica-->
      <xsl:call-template name="Pratica" />	
	 
      <xsl:call-template name="elemento_alias">
         <xsl:with-param name="nomeelemento" select="//pra:Stato" />
         <xsl:with-param name="alias" select="'CodStato'" />
      </xsl:call-template>
      
      <xsl:if test="//pra:DesStato !=''">
      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:DesStato[text()!='']" />
      </xsl:call-template>
      </xsl:if>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:DataStato" />
      </xsl:call-template>

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="pra:Note" />
      </xsl:call-template>

	</xsl:element>

   </xsl:template>
</xsl:stylesheet>

