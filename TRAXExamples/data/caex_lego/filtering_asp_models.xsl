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
    <xsl:apply-templates select="./model"/>
	
</xsl:template>

<xsl:template match="model">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:for-each select="./literal[@predicate='level']">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:value-of select="./arg[@num=1]"/>
		<xsl:text>:</xsl:text>
		<xsl:value-of select="./arg[@num=0]"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:for-each>
</xsl:template>


</xsl:stylesheet>