<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" encoding="UTF-8"/>
	
	<xsl:template name="decodeLabel" >
		<xsl:param name="str"/>
		
		<xsl:variable name="str_A">		
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="concat($str,' ')"/>
			<xsl:with-param name="substring" select="'A'"/>
			<xsl:with-param name="replacement" select="' A'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_B">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_A"/>
			<xsl:with-param name="substring" select="'B'"/>
			<xsl:with-param name="replacement" select="' B'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_C">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_B"/>
			<xsl:with-param name="substring" select="'C'"/>
			<xsl:with-param name="replacement" select="' C'"/>
		</xsl:call-template>
		</xsl:variable>
       <xsl:variable name="str_D">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_C"/>
			<xsl:with-param name="substring" select="'D'"/>
			<xsl:with-param name="replacement" select="' D'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_E">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_D"/>
			<xsl:with-param name="substring" select="'E'"/>
			<xsl:with-param name="replacement" select="' E'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_F">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_E"/>
			<xsl:with-param name="substring" select="'F'"/>
			<xsl:with-param name="replacement" select="' F'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_G">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_F"/>
			<xsl:with-param name="substring" select="'G'"/>
			<xsl:with-param name="replacement" select="' G'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_I">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_G"/>
			<xsl:with-param name="substring" select="'I'"/>
			<xsl:with-param name="replacement" select="' I'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_L">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_I"/>
			<xsl:with-param name="substring" select="'L'"/>
			<xsl:with-param name="replacement" select="' L'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_M">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_L"/>
			<xsl:with-param name="substring" select="'M'"/>
			<xsl:with-param name="replacement" select="' M'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_N">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_M"/>
			<xsl:with-param name="substring" select="'N'"/>
			<xsl:with-param name="replacement" select="' N'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_O">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_N"/>
			<xsl:with-param name="substring" select="'O'"/>
			<xsl:with-param name="replacement" select="' O'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_P">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_O"/>
			<xsl:with-param name="substring" select="'P'"/>
			<xsl:with-param name="replacement" select="' P'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_Q">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_P"/>
			<xsl:with-param name="substring" select="'Q'"/>
			<xsl:with-param name="replacement" select="' Q'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_R">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Q"/>
			<xsl:with-param name="substring" select="'R'"/>
			<xsl:with-param name="replacement" select="' R'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_S">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_R"/>
			<xsl:with-param name="substring" select="'S'"/>
			<xsl:with-param name="replacement" select="' S'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_T">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_S"/>
			<xsl:with-param name="substring" select="'T'"/>
			<xsl:with-param name="replacement" select="' T'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_U">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_T"/>
			<xsl:with-param name="substring" select="'U'"/>
			<xsl:with-param name="replacement" select="' U'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_V">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_U"/>
			<xsl:with-param name="substring" select="'V'"/>
			<xsl:with-param name="replacement" select="' V'"/>
		</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="str_Z">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_V"/>
			<xsl:with-param name="substring" select="'Z'"/>
			<xsl:with-param name="replacement" select="' Z'"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Des">
	    <xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Z"/>
			<xsl:with-param name="substring" select="' Des '"/>
			<xsl:with-param name="replacement" select="' Descrizione '"/>
		</xsl:call-template>
		</xsl:variable>
       
       <xsl:variable name="str_Cod">
       	<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Des"/>
			<xsl:with-param name="substring" select="'Cod '"/>
			<xsl:with-param name="replacement" select="'Codice '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Id">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Cod"/>
			<xsl:with-param name="substring" select="' Id '"/>
			<xsl:with-param name="replacement" select="' Identificativo '"/>
		</xsl:call-template>
		</xsl:variable>		
		
		<xsl:variable name="str_Num">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Id"/>
			<xsl:with-param name="substring" select="'Num '"/>
			<xsl:with-param name="replacement" select="'Numero '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Flg">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Num"/>
			<xsl:with-param name="substring" select="'Flg '"/>
			<xsl:with-param name="replacement" select="'Flag '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Tel">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Flg"/>
			<xsl:with-param name="substring" select="' Tel '"/>
			<xsl:with-param name="replacement" select="' Telefono '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Civ">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Tel"/>
			<xsl:with-param name="substring" select="'Civ '"/>
			<xsl:with-param name="replacement" select="'Civico '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Let">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Civ"/>
			<xsl:with-param name="substring" select="'Let '"/>
			<xsl:with-param name="replacement" select="'Lettera '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Sup">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Let"/>
			<xsl:with-param name="substring" select="'Sup '"/>
			<xsl:with-param name="replacement" select="'Superficie '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Tot">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Sup"/>
			<xsl:with-param name="substring" select="'Tot '"/>
			<xsl:with-param name="replacement" select="'Totale '"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_IVA">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Tot"/>
			<xsl:with-param name="substring" select="' IV A'"/>
			<xsl:with-param name="replacement" select="' IVA'"/>
		</xsl:call-template>
		</xsl:variable>	
		
		<xsl:variable name="str_CCIA">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_IVA"/>
			<xsl:with-param name="substring" select="'C C I A'"/>
			<xsl:with-param name="replacement" select="'CCIA'"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_ISSE">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_CCIA"/>
			<xsl:with-param name="substring" select="'I S E E'"/>
			<xsl:with-param name="replacement" select="'ISSE'"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_REC">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_ISSE"/>
			<xsl:with-param name="substring" select="'R E C'"/>
			<xsl:with-param name="replacement" select="'REC'"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Bo">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_REC"/>
			<xsl:with-param name="substring" select="' Bo'"/>
			<xsl:with-param name="replacement" select="' Backoffice'"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_BO">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_Bo"/>
			<xsl:with-param name="substring" select="'B O'"/>
			<xsl:with-param name="replacement" select="' Backoffice'"/>
		</xsl:call-template>
		</xsl:variable>
		
				<xsl:variable name="str_FO">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_BO"/>
			<xsl:with-param name="substring" select="'F O'"/>
			<xsl:with-param name="replacement" select="' Frontoffice'"/>
		</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="str_Prg">
		<xsl:call-template name="replace-substring">
			<xsl:with-param name="original" select="$str_FO"/>
			<xsl:with-param name="substring" select="' Prg'"/>
			<xsl:with-param name="replacement" select="' Piano Regolatore'"/>
		</xsl:call-template>
		</xsl:variable>		
		
		<xsl:value-of select="$str_Prg" />

	</xsl:template>
	
	<xsl:template name="replace-substring">
		<xsl:param name="original"/>
		<xsl:param name="substring"/>
		<xsl:param name="replacement" select="''"/>
		<xsl:variable name="first">
			<xsl:choose>
				<xsl:when test="contains($original, $substring)">
					<xsl:value-of select="substring-before($original, $substring)"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$original"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="middle">
			<xsl:choose>
				<xsl:when test="contains($original, $substring)">
					<xsl:value-of select="$replacement"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="last">
			<xsl:choose>
				<xsl:when test="contains($original, $substring)">
					<xsl:choose>
						<xsl:when test="contains(substring-after($original, $substring),$substring)">
							<xsl:call-template name="replace-substring">
								<xsl:with-param name="original">
									<xsl:value-of select="substring-after($original, $substring)"/>
								</xsl:with-param>
								<xsl:with-param name="substring">
									<xsl:value-of select="$substring"/>
								</xsl:with-param>
								<xsl:with-param name="replacement">
									<xsl:value-of select="$replacement"/>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-after($original, $substring)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:value-of select="concat($first, $middle, $last)"/>
	</xsl:template>
	
	<xsl:template match="text">
	   <p>
		<xsl:call-template name="decodeLabel">
			<xsl:with-param name="str" select=".">
		 </xsl:with-param>
		</xsl:call-template>
		</p>
	</xsl:template>
</xsl:stylesheet>
