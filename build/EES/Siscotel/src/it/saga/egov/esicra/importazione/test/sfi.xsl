<?xml version="1.0" encoding="windows-1252" ?>

<xsl:stylesheet  version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		xmlns="http://www.saga.it/egov/esicra/importazione/xml/esicra_import"
		xmlns:import="http://www.saga.it/egov/esicra/importazione/xml/esicra_import" 
>

 <xsl:output method="xml" version="1.0" indent="yes"/>
  
 <xsl:template match="/">
 
    <xsl:element name="ListaSoggettoFis" > 
    <xsl:attribute name="xsi:schemaLocation">
		<xsl:value-of select="'http://www.saga.it/egov/esicra/importazione/xml/esicra_import ListaSoggetto.xsd'"/>
	</xsl:attribute>
      <xsl:for-each select="//import:ListaSoggetto/import:Soggetto">
        <xsl:element name="SoggettoFis">
        <!-- aggiungere progressivo -->
		<xsl:attribute name="progressivo">
			<xsl:value-of select="@progressivo" />
		</xsl:attribute> 
			<xsl:element name="CodSoggetto">
				<xsl:value-of select="//import:CodSoggetto" />
			</xsl:element>
			<xsl:element name="Provenienza">
				<xsl:value-of select="//import:Provenienza" />
			</xsl:element>
			<xsl:element name="Cognome">
				<xsl:value-of select="//import:Cognome" />
			</xsl:element>
			<xsl:element name="Nome">
				<xsl:value-of select="//import:Nome" />
			</xsl:element>
			<xsl:element name="AltriNomi">
				<xsl:value-of select="//import:AltriNomi" />
			</xsl:element>
			<xsl:element name="Sesso">
				<xsl:value-of select="//import:Sesso" />
			</xsl:element>
			<xsl:element name="DataNascita">
				<xsl:value-of select="//import:DataNascita" />
			</xsl:element>
			<xsl:element name="PrecisioneDtNascita">
				<xsl:value-of select="//import:PrecisioneDtNascita" />
			</xsl:element>
			<xsl:element name="PartitaIva">
				<xsl:value-of select="//import:PartitaIva" />
			</xsl:element>
			<xsl:element name="Note">
				<xsl:value-of select="//import:Note" />
			</xsl:element>
			<xsl:copy-of select="import:ComuneNascita"  />
			<xsl:copy-of select="import:LocalitaNascita" />
        </xsl:element>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
