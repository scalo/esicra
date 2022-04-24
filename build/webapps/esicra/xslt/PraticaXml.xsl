<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_sch_soggetto.xsl" />
<xsl:include href="xslt_sog_giur.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_situazioneeco.xsl" />
<xsl:include href="xslt_dichiarazione.xsl" />
<xsl:include href="xslt_indirizzo.xsl" />
<xsl:include href="xslt_ztl_soggetto.xsl" />
<xsl:include href="xslt_ztl_fruitore.xsl" />
<xsl:include href="xslt_ztl_sog_indirizzo.xsl" />
<xsl:include href="xslt_ztl_veicolo.xsl" />
<xsl:include href="xslt_componentefamiglia.xsl" />
<xsl:include href="xslt_pagamento.xsl" /> 
<xsl:include href="xslt_recapito.xsl" />
<xsl:include href="xslt_allegato.xsl" />
<xsl:include href="xslt_datimorte.xsl"/>
<xsl:include href="xslt_datinascita.xsl"/>

<xsl:include href="xslt_trasporto.xsl" />
<xsl:include href="xslt_responsabile.xsl" />
<xsl:include href="xslt_sogeconomico.xsl" />
<xsl:include href="xslt_scu_indirizzo.xsl" />
<xsl:include href="xslt_classe.xsl" />
<xsl:include href="xslt_datiscuola.xsl" />

<xsl:include href="xslt_datipassocarrabile.xsl" />
<xsl:include href="xslt_giu_indirizzo.xsl" />

<xsl:include href="xslt_fognatura.xsl" />
<xsl:include href="xslt_datipermesso.xsl" />

<xsl:include href="xslt_cosap.xsl" />
<xsl:include href="xslt_recapito_cosap.xsl" />
<xsl:include href="xslt_professionista.xsl" />
<xsl:include href="xslt_albo.xsl" />
<xsl:include href="xslt_catasto.xsl" />

<xsl:include href="xslt_pubblicita.xsl" />
<xsl:include href="xslt_ubicazione.xsl" />

<xsl:include href="xslt_manifestazione.xsl" />

<xsl:include href="xslt_datisomministrazione.xsl" />

<xsl:include href="xslt_spettacolo.xsl" />

<xsl:include href="xslt_daticentro.xsl" />

<xsl:include href="xslt_datidac.xsl"/>
<xsl:include href="xslt_esercizio.xsl" /> 
<xsl:include href="xslt_eserciziocessa.xsl" />  
<xsl:include href="xslt_eserciziosub.xsl" />
<xsl:include href="xslt_esercizioamp.xsl" />
<xsl:include href="xslt_esercizioacc.xsl" />
<xsl:include href="xslt_cessazione.xsl" /> 
<xsl:include href="xslt_subentro.xsl" />
<xsl:include href="xslt_sospensione.xsl" />
<xsl:include href="xslt_ampliamento.xsl" />

<xsl:include href="xslt_aire.xsl" />

<xsl:include href="xslt_cpi.xsl" />

<xsl:include href="xslt_datiimpresa.xsl" />

<xsl:include href="xslt_label.xsl"/>

<xsl:include href="PraCertificato.xsl" />
<xsl:include href="PraPolizia.xsl" />
<xsl:include href="PraDichiarazioneCRD.xsl" />
<xsl:include href="PraDenunciaDAC.xsl"/>
<xsl:include href="PraPresidentiScrutatori.xsl" />
<xsl:include href="PraDenunciaMorte.xsl" />
<xsl:include href="PraDenunciaNascita.xsl" />
<xsl:include href="PraServizioScolastico.xsl" />
<xsl:include href="PraRichiestaSussidioScolastico.xsl"/>
<xsl:include href="PraPassoCarrabile.xsl"/>
<xsl:include href="PraAllaccioFognatura.xsl"/>
<xsl:include href="PraCosap.xsl"/>
<xsl:include href="PraInstallazionePubblicita.xsl"/>
<xsl:include href="PraManifestazione.xsl"/>
<xsl:include href="PraSomministrazione.xsl"/>
<xsl:include href="PraRichiestaAssistenzaSociale.xsl"/>
<xsl:include href="PraAutorizzazioneSpettacolo.xsl"/>
<xsl:include href="PraRichiestaIscrizioneCentri.xsl"/>
<xsl:include href="PraEdilizia.xsl"/>
<xsl:include href="PraRichiestaRIA.xsl"/>
<xsl:include href="PraRichiestaRLA.xsl"/>
<xsl:include href="PraRichiestaCPI.xsl"/>
<xsl:include href="PraPagamentoBolletta.xsl"/>

<xsl:variable name="output" select=" 'xml' " />
<xsl:template match="/">
            <xsl:apply-templates select="//pra:Pratica"/>
</xsl:template>
   
  <xsl:template match="//pra:Pratica" >
		 
      <xsl:if test="pra:IdServizio=100100 or pra:IdServizio=100150 or pra:IdServizio=100151 or pra:IdServizio=100153">
		    <xsl:call-template name="PraEdilizia" />
      </xsl:if>
     
	  <xsl:if test="pra:IdServizio=100103">
		    <xsl:call-template name="PraPagamentoBolletta" />
      </xsl:if>	 
      	  
	 <xsl:if test="pra:IdServizio=100111">
		    <xsl:call-template name="PraPolizia" />
      </xsl:if>
      	 
      <xsl:if test="pra:IdServizio=100112">
		     <xsl:call-template name="PraPolizia" />
      </xsl:if>
      	 
      <xsl:if test="pra:IdServizio=100113">
		     <xsl:call-template name="PraPolizia" />
      </xsl:if>
      	 
      <xsl:if test="/pra:IdServizio=100114">
		   <xsl:call-template name="PraDichiarazioneCRD" />
      </xsl:if>
      	 
      <xsl:if test="pra:IdServizio=100120">
		    <xsl:call-template name="PraRichiestaRLA" />
      </xsl:if>
      	 
		 <xsl:if test="pra:IdServizio=100121 or  pra:IdServizio=100122 or pra:IdServizio=100123 or pra:IdServizio=100124 or pra:IdServizio=100125 or pra:IdServizio=100126">
		  <xsl:call-template name="PraDenunciaDAC" />
     </xsl:if>		 
      	 
     <xsl:if test="pra:IdServizio=100128 or pra:IdServizio=100129">
		   <xsl:call-template name="PraPresidentiScrutatori" />
     </xsl:if>
		
		 <xsl:if test="pra:IdServizio=100130">
		  <xsl:call-template name="PraDenunciaMorte" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100132">
		   <xsl:call-template name="PraDenunciaNascita" />
     </xsl:if>	 
      	 
      	 <!-- specificare i certificati -->
		 <xsl:if test="pra:IdServizio=100134 or pra:IdServizio=100135 or pra:IdServizio=100137 or pra:IdServizio=100138 or pra:IdServizio=100139">
		  <xsl:call-template name="PraCertificato" />
     </xsl:if>
      	 
     <!-- specificare i certificati -->
		 <xsl:if test="//pra:IdServizio=100133">
		   <xsl:call-template name="PraEdilizia" />
     </xsl:if>	 
      	 
     <xsl:if test="pra:IdServizio=100141 or pra:IdServizio=100142 or pra:IdServizio=100143 or pra:IdServizio=100144 or pra:IdServizio=100147">
		   <xsl:call-template name="PraServizioScolastico" />
     </xsl:if>
      	       	 
     <xsl:if test="pra:IdServizio=100146">
		   <xsl:call-template name="PraRichiestaSussidioScolastico" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100148">
		   <xsl:call-template name="PraPassoCarrabile" />
     </xsl:if>
      
     <xsl:if test="pra:IdServizio=100152">
		   <xsl:call-template name="PraRichiestaCPI" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100154 or pra:IdServizio=100155">
		   <xsl:call-template name="PraAllaccioFognatura" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100131 or pra:IdServizio=100156 or pra:IdServizio=100157 or pra:IdServizio=100158">
		   <xsl:call-template name="PraCosap" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100159 or pra:IdServizio=100162">
		   <xsl:call-template name="PraInstallazionePubblicita" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100160">
		   <xsl:call-template name="PraManifestazione" />
     </xsl:if>
      	 
		 <xsl:if test="pra:IdServizio=100161">
		   <xsl:call-template name="PraSomministrazione" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100163 or pra:IdServizio=100164">
		   <xsl:call-template name="PraRichiestaAssistenzaSociale" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100165 or pra:IdServizio=100166">
		   <xsl:call-template name="PraAutorizzazioneSpettacolo" />
     </xsl:if>
      	       	 
     <xsl:if test="pra:IdServizio=100167">
		   <xsl:call-template name="PraRichiestaRIA" />
     </xsl:if>
      	 
     <xsl:if test="pra:IdServizio=100168 or pra:IdServizio=100145 ">
		   <xsl:call-template name="PraRichiestaIscrizioneCentri" />
     </xsl:if>
     
     <xsl:if test="pra:IdServizio=100176 or pra:IdServizio=100177 or pra:IdServizio=100178 or pra:IdServizio=100179 or pra:IdServizio=100180 or pra:IdServizio=100181">
        <xsl:call-template name="PraPagamentoBolletta" />
     </xsl:if>
     
  </xsl:template>


</xsl:stylesheet>
