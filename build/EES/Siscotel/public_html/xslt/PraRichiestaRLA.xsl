<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
   <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<!--
<xsl:include href="xslt_elemento.xsl" />   
<xsl:include href="xslt_soggetto.xsl" />
<xsl:include href="xslt_pratica.xsl" />
<xsl:include href="xslt_situazioneeco.xsl" />
<xsl:include href="xslt_datiimpresa.xsl" />
<xsl:include href="xslt_indirizzo.xsl" />
-->

<xsl:template name="PraRichiestaRLA" match="//pra:PraRichiestaRLA">

<xsl:element name="{'PraRichiestaRLA'}">

<!--Soggetto-->
<xsl:for-each select="//pra:Soggetto[@attr='SoggettoDichiarante']">
	 <xsl:call-template name="Soggetto">
     <xsl:with-param name="tag" select="'SoggettoDichiarante'" />
     </xsl:call-template>
</xsl:for-each>

<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:TitolaritaDichiarante" />
</xsl:call-template>

<xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="/pra:PraRichiestaRLA/pra:CodRichiesta" />
</xsl:call-template>		
		
<xsl:call-template name="elemento">
        <xsl:with-param name="nomeelemento" select="//pra:DesRichiesta" />
</xsl:call-template>

<xsl:if test="$output = 'html' and //pra:DatiRichiestaRLA[@attr='DatiRichiestaRLA']/text() != ''">
		<h3>Dati Licenza Richiesta</h3>

<xsl:for-each select="//pra:DatiRichiestaRLA[@attr='DatiRichiestaRLA']">
	<xsl:call-template name="DatiRichiestaRLA">
		<xsl:with-param name="newtag" select="'DatiRichiestaRLA'" />
	</xsl:call-template>
</xsl:for-each>
</xsl:if>	

<xsl:if test="$output = 'html' and //pra:DatiRichiestaRLA[@attr='DatiRLAPosseduta']/text() != '' ">
		<h3>Dati Licenza Posseduta</h3>

<xsl:for-each select="//pra:DatiRichiestaRLA[@attr='DatiRLAPosseduta']">
	<xsl:call-template name="DatiRichiestaRLA">
		<xsl:with-param name="newtag" select="'DatiRLAPosseduta'" />
	</xsl:call-template>
</xsl:for-each>
</xsl:if>	

<xsl:if test="$output = 'html' and //pra:ListaMerciTrasportate ">
		<h3>Elenco merci Trasportate</h3>
<xsl:call-template name="MerceRLA" />
</xsl:if>	

<!--Pratica-->  
      <xsl:call-template name="Pratica" />

      <xsl:call-template name="elemento">
         <xsl:with-param name="nomeelemento" select="//pra:CodStato" />
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

	<xsl:template name="DatiRichiestaRLA">
		<xsl:param name="newtag" />
		<xsl:element name="{$newtag}">
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodRichiesta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:DesRichiesta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodTipoRichiesta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TipoRichiesta"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:NumeroLicenza"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:TipoSostituzione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodTipoSostituzione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CausaSostituzione"/>
			</xsl:call-template>
			<xsl:call-template name="elemento">
				<xsl:with-param name="nomeelemento" select="pra:CodCausaSostituzione"/>
			</xsl:call-template>
			<xsl:if test="$output = 'html' ">
			<h4>Elenco Veicoli</h4>
			</xsl:if>	
			<xsl:for-each select="pra:VeicoloRLA[@attr='DatiVeicoli']">
					<xsl:element name="ListaVeicoloRLA">
					<xsl:for-each select="pra:VeicoloRLA">
						<xsl:call-template name="VeicoloRLA"/>
					</xsl:for-each>
					</xsl:element>
			</xsl:for-each>
			<xsl:if test="$output = 'html' ">
			<h3>Dati Impresa</h3>
			</xsl:if>	
			<xsl:for-each select="pra:DatiImpresa">
				<xsl:call-template name="DatiImpresa"/>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="VeicoloRLA">
			<xsl:element name="{'VeicoloRLA'}">
				<xsl:for-each select="pra:PolVeicolo">
					<xsl:call-template name="VeicoloRLABase" />
				</xsl:for-each>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Carrozzeria"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:TipoVeicolo"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:Portata"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:PortataMassima"/>
				</xsl:call-template>
				<xsl:call-template name="elemento">
					<xsl:with-param name="nomeelemento" select="pra:PortataPotenziale"/>
				</xsl:call-template>
				</xsl:element>
				<xsl:if test="$output = 'html' ">
				<br/>
				</xsl:if>	
	</xsl:template>
	
	<xsl:template name="VeicoloRLABase">
		   <xsl:element name="DatiVeicolo">
               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Targa" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:CodTipoVeicolo" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:DesTipoVeicolo" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Marca" />
               </xsl:call-template>

               <xsl:call-template name="elemento">
                  <xsl:with-param name="nomeelemento" select="pra:Modello" />
               </xsl:call-template>
           </xsl:element>
       </xsl:template>	
		
<xsl:template name="MerceRLA">
			<xsl:element name="{'ListaMerciTrasportate'}">
				<xsl:for-each select="//pra:String" >
					<xsl:call-template name="elemento_alias">
					<xsl:with-param name="nomeelemento" select="pra:java.lang.String"/>
					<xsl:with-param name="alias" select="'MerceTrasportata'"/>
				</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
	</xsl:template>		
		
</xsl:stylesheet>

