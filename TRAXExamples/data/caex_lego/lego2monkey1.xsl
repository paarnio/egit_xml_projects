<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- lego2monkey1.xsl test-version-->
<!-- Transform Lego_example_mod1.aml CAEXFile to jmonkey assembly commands defined by SS-->			
<!-- method text-->
<xsl:output method="text" indent="no"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="CAEXFile"/>
</xsl:template>

<xsl:template match="CAEXFile">
    <xsl:apply-templates select="//InternalElement"/>
	<xsl:apply-templates select="//InternalLink"/>
</xsl:template>

<xsl:template match="InternalElement">
	<xsl:variable name="IEguid" select="./@ID"/>
	<xsl:variable name="IEname" select="./@Name"/>
	<xsl:for-each select="./ExternalInterface">
		<xsl:text>&#xA;&#xA;</xsl:text>
		<xsl:text>&#xA;NAME:ExternalInterface = </xsl:text>
		<xsl:value-of select="./@Name"/>
		<xsl:text>&#xA;</xsl:text>
		<xsl:variable name="IEinterface" select="./@Name"/>
		<xsl:variable name="CREATE" select="concat('CREATE ', $IEguid,':',$IEname,' ',$IEinterface)"/>
		<xsl:value-of select="$CREATE"/>
	</xsl:for-each>
	<xsl:text>&#xA;&#xA;----------&#xA;&#xA;</xsl:text>
</xsl:template>


<xsl:template match="InternalLink">
	<xsl:text>&#xA;NAME:InternalLink = </xsl:text>
	<xsl:value-of select="./@Name"/>
	<!-- Side A  -->
	<xsl:text>&#xA;LINK:RefPartnerSideA = </xsl:text>
	<xsl:variable name="Aside" select="./@RefPartnerSideA"/>
	<xsl:value-of select="$Aside"/>
	<xsl:text>&#xA;GUID:InternalElement:RefPartnerSideA = </xsl:text>
	<xsl:value-of select="substring-before($Aside,':')"/>
	<xsl:variable name="Aguid" select="substring-before($Aside,':')"/>
	<xsl:text>&#xA;NAME:ExternalInterface:RefPartnerSideA = </xsl:text>	
	<xsl:value-of select="substring-after($Aside,':')"/>
	<xsl:variable name="Ainterface" select="substring-after($Aside,':')"/>
	<xsl:text>&#xA;NAME:InternalElement:RefPartnerSideA = </xsl:text>
	<xsl:value-of select="..//InternalElement[@ID='c8d64b63-7f37-4353-8e66-9ef1bc72336e']/@Name"/><!-- [@ID=$Aguid] -->
	<xsl:text>&#xA;NAME:InternalElement:RefPartnerSideA = </xsl:text>
	<xsl:value-of select="/CAEXFile//InternalElement[@ID=string($Aguid)]/@Name"/>
	<xsl:variable name="Aname" select="/CAEXFile//InternalElement[@ID=string($Aguid)]/@Name"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:value-of select="$Aguid"/>
	<!-- Side B  -->
	<xsl:variable name="Bside" select="./@RefPartnerSideB"/>
	<xsl:variable name="Bguid" select="substring-before($Bside,':')"/>
	<xsl:variable name="Binterface" select="substring-after($Bside,':')"/>
	<xsl:variable name="Bname" select="/CAEXFile//InternalElement[@ID=string($Bguid)]/@Name"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:text>&#xA;</xsl:text>
	<!-- CONCAT COMMAND  -->
	<xsl:value-of select="concat('CONNECT ', $Aguid,':',$Aname,' ',$Ainterface ,' ', $Bguid,':',$Bname,' ',$Binterface)"/>
</xsl:template>

</xsl:stylesheet>