<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">
	<xsl:template name="elemento">
		
		<xsl:param name="nomeelemento"/>
		<xsl:for-each select="$nomeelemento">
			<xsl:if test="$output = 'xml' ">
				<xsl:element name="{name()}">
					<xsl:apply-templates select="."/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="$output = 'html' and normalize-space(text()) != '' ">
				<b><xsl:call-template name="decodeLabel">
                <xsl:with-param name="str" select="name()" />
				  </xsl:call-template>:</b>
				  <!--
				<xsl:apply-templates select="."/>
				-->
				<xsl:call-template name="decodeValue">
					<xsl:with-param name="val" select="."/>
				</xsl:call-template>
				<br/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!--Elemento generico con alias-->
	<xsl:template name="elemento_alias">
		<xsl:param name="nomeelemento"/>
		<xsl:param name="alias"/>
		<xsl:for-each select="$nomeelemento">
			<xsl:if test="$output = 'xml' ">
				<xsl:element name="{$alias}">
					<xsl:apply-templates select="."/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="$output = 'html'  and normalize-space(text()) != ''">
				<b><xsl:call-template name="decodeLabel">
                <xsl:with-param name="str" select="$alias" />
				  </xsl:call-template>:</b>
				<xsl:call-template name="decodeValue">
					<xsl:with-param name="val" select="."/>
				</xsl:call-template>
				<br/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
   
   <xsl:template name="decodeValue">
		<xsl:param name="val"/>
 			
			<xsl:choose>
				<xsl:when test="$val='1' and name()='Sesso'">
						<xsl:text>Maschile</xsl:text>
				</xsl:when>
				<xsl:when test="$val='2' and name()='Sesso'">
						<xsl:text>Femminile</xsl:text>
				</xsl:when>
				<xsl:when test="$val='1' and name()='Canale'">
						<xsl:text>Portale</xsl:text>
				</xsl:when>
				<xsl:when test="$val='2' and name()='Canale'">
						<xsl:text>Call Center</xsl:text>
				</xsl:when>
				<xsl:when test="$val='3' and name()='Canale'">
						<xsl:text>Chiosco</xsl:text>
				</xsl:when>
				<xsl:when test="$val='4' and name()='Canale'">
						<xsl:text>Wireless</xsl:text>
				</xsl:when>
				<xsl:when test="$val='5' and name()='Canale'">
						<xsl:text>Backoffice</xsl:text>
				</xsl:when>
				<xsl:when test="$val='true'">
						<xsl:text>Si</xsl:text>
				</xsl:when>
				<xsl:when test="$val='false'">
				    	<xsl:text>No</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$val"/>
				</xsl:otherwise>
			</xsl:choose>
		
	</xsl:template>
   	
	
</xsl:stylesheet>
