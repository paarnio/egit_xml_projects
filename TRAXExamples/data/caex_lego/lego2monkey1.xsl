<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- Transform Lego_example_mod1.aml CAEXFile to jmonkey assembly commands defined by SS-->			
<!-- method text-->
<xsl:output method="text" indent="no"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="CAEXFile"/>
</xsl:template>

<xsl:template match="CAEXFile">
	<xsl:apply-templates select="//InternalLink"/>
</xsl:template>

<xsl:template match="InternalLink">
	<xsl:value-of select="./@Name"/>
</xsl:template>

</xsl:stylesheet>