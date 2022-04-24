<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:pra="http://www.saga.it/egov/esicra/esportazione/xml/esicra_pra">

   <xsl:template name="ListaClasse">

      <xsl:element name="{'ListaClasse'}">
         <xsl:for-each select="//pra:DatiScuola/pra:ClasseScuola/pra:ClasseScuola">
           <xsl:element name="Classe">
           
             <xsl:call-template name="elemento_alias">
	       <xsl:with-param name="alias" select="'Classe'" />
               <xsl:with-param name="nomeelemento" select="pra:CodClasse" />
             </xsl:call-template>
         
             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:TipoClasse" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:SezioneClasse" />
             </xsl:call-template>

             <xsl:call-template name="elemento">
               <xsl:with-param name="nomeelemento" select="pra:AnnoScolastico" />
             </xsl:call-template>
             
           <xsl:call-template name="elemento">
              <xsl:with-param name="nomeelemento" select="pra:FlgFruizioneServizioMensa" />
           </xsl:call-template>

      <xsl:element name="{'ListaGiorniRitornoPomeridiano'}">
		 
         <xsl:for-each select="pra:String[@attr='ListaGiorniRitornoPomeridiano']">
             <xsl:call-template name="elemento_alias">
	           <xsl:with-param name="alias" select="'GiornoRitornoPomeridiano'" />
               <xsl:with-param name="nomeelemento" select="pra:java.lang.String" />
             </xsl:call-template>

        <xsl:if test="$output = 'html' ">
			<br/><br/>
	    </xsl:if>
         </xsl:for-each>
      </xsl:element>

           </xsl:element>
         </xsl:for-each>
      </xsl:element>        

   </xsl:template>
</xsl:stylesheet>

