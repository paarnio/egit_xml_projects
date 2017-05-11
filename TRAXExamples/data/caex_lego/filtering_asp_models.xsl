<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- filtering_asp_models.xsl test-version-->
<!-- -->			
<!-- method xml-->
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="aspmodels"/>
</xsl:template>

<xsl:template match="aspmodels">
    <xsl:apply-templates select="./model" mode="mainbranch" />
	<xsl:apply-templates select="./model" mode="subbranch" />

	
</xsl:template>

<xsl:template match="model" mode="koe1">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="./literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<xsl:value-of select="$Level"/>
		<xsl:text>:</xsl:text>		
		<xsl:value-of select="$LEGO"/>
		<xsl:text>:</xsl:text>		
		<xsl:value-of select="$MBRBool"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="model" mode="mainbranch">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="./literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<xsl:choose>			
			<xsl:when test="$MBRBool">
			<xsl:text>&#xA;</xsl:text>
			<xsl:text>MAIN-BRANCH:</xsl:text>			
			<xsl:value-of select="$Level"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$LEGO"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$MBRBool"/>
		</xsl:when>
		</xsl:choose>
		
	</xsl:for-each>
</xsl:template>

<xsl:template match="model" mode="subbranch">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="./literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<xsl:choose>			
			<xsl:when test="not($MBRBool)">
			<xsl:text>&#xA;</xsl:text>
			<xsl:text>SUB-BRANCH:</xsl:text>			
			<xsl:value-of select="$Level"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$LEGO"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$MBRBool"/>
		</xsl:when>
		</xsl:choose>
		
	</xsl:for-each>
</xsl:template>

</xsl:stylesheet>