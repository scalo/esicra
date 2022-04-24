<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

<!--
<xsl:include href="xslt_esercizio.xsl" /> 
<xsl:include href="xslt_eserciziocessa.xsl" />  
<xsl:include href="xslt_eserciziosub.xsl" />
<xsl:include href="xslt_esercizioamp.xsl" />
<xsl:include href="xslt_esercizioacc.xsl" />

<xsl:include href="xslt_cessazione.xsl" /> 
<xsl:include href="xslt_subentro.xsl" />
<xsl:include href="xslt_sospensione.xsl" />
<xsl:include href="xslt_ampliamento.xsl" />
<xsl:include href="xslt_indirizzo.xsl" />
-->

 <xsl:template name="DatiDAC">
    
    <xsl:for-each select="//pra:DatiDenunciaDAC">

      <xsl:element name="{'DatiDenunciaDAC'}">

<xsl:choose>

     <xsl:when test="//pra:DatiDenunciaDAC/pra:Esercizio/pra:DatiEsercizio/pra:NomeTitolare !=''">
		  	<xsl:if test="$output = 'html' ">
			<h3><xsl:value-of select="'Dati Esercizio'"/></h3>
			</xsl:if>
           <xsl:call-template name="Esercizio" />
     </xsl:when>

	 <xsl:when test="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiCessazione/pra:Esercizio/pra:DatiEsercizio/pra:NomeTitolare !=''">
		  	<xsl:if test="$output = 'html' ">
			<h3>Cessazione Esercizio</h3>
			</xsl:if>
           <xsl:call-template name="EsercizioCessa" />
           <xsl:call-template name="Cessazione" />
         </xsl:when>

	 <xsl:when test="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiSubentro/pra:Esercizio/pra:DatiEsercizio/pra:NomeTitolare !=''">
			<xsl:if test="$output = 'html' ">
			<h3>Subentro Esercizio</h3>
			</xsl:if>		  
           <xsl:call-template name="EsercizioSub" />
           <xsl:call-template name="AperturaSub" />
     </xsl:when>

	 <xsl:when test="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiAmpliamento/pra:Esercizio/pra:DatiEsercizio/pra:NomeTitolare !=''"> 
	      <xsl:if test="$output = 'html' ">
			<h3>Ampliamento Esercizio</h3>
			</xsl:if>
           <xsl:call-template name="EsercizioAmp" />
           <xsl:call-template name="Ampliamento" />
         </xsl:when>

	 <xsl:when test="//pra:DatiDenunciaDAC/pra:TipoEsercizio/pra:DatiEsercizio/pra:DatiEsercizio/pra:NomeTitolare !=''">
	    <xsl:if test="$output = 'html' ">
		 	<h3>Dati Esercizi Accorpati</h3>
		</xsl:if>         
         <xsl:element name="{'DatiEserciziAccorpati'}">

         <xsl:call-template name="elemento">
            <xsl:with-param name="nomeelemento" select="//pra:DatiDenunciaDAC/pra:TipoEsercizio/pra:NumEsercizi" />
         </xsl:call-template>

         <xsl:element name="{'ListaEsercizioAcc'}">
          	<xsl:if test="$output = 'html' ">
		 	  <h4>Esercizio</h4>
		   </xsl:if>         
           <xsl:call-template name="EsercizioAcc" />
         </xsl:element>
         </xsl:element>
     </xsl:when>

	 <xsl:when test="//pra:DatiDenunciaDAC/pra:Comunicazione/pra:DatiSospensione/pra:DataInizioSospensione !=''">         
           <!--xsl:call-template name="Esercizio" /-->
			<xsl:if test="$output = 'html' ">
				<h3>Sospensione Esercizio</h3>
			</xsl:if>         
           <xsl:call-template name="Sospensione" />
     </xsl:when>

</xsl:choose>


      </xsl:element> <!--{'DatiDenunciaDAC'}-->

    </xsl:for-each>

   </xsl:template>
</xsl:stylesheet>

