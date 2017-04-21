<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- CAEXLego2monkey2.xsl version 1-->
<!-- Transform Lego_example_mod1.aml CAEXFile to jmonkey assembly commands defined by SS-->			
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
		<xsl:text>&#xA;</xsl:text>
		<!--
		<xsl:text>&#xA;NAME:ExternalInterface = </xsl:text>
		<xsl:value-of select="./@Name"/>		
		<xsl:text>&#xA;</xsl:text>
		-->
		<xsl:variable name="IEinterface" select="./@Name"/>
		<xsl:variable name="CREATE" select="concat('CREATE ', $IEguid,':',$IEname,' ',$IEinterface)"/>
		<xsl:value-of select="$CREATE"/>
	</xsl:for-each>
	<xsl:text>&#xA;</xsl:text>
</xsl:template>


<xsl:template match="InternalLink">
	<xsl:text>&#xA;</xsl:text>
	<!-- Side A  -->
	<!--xsl:text>&#xA;LINK:RefPartnerSideA = </xsl:text-->
	<xsl:variable name="Aside" select="./@RefPartnerSideA"/>
	<xsl:variable name="Aguid" select="substring-before($Aside,':')"/>
	<xsl:variable name="Ainterface" select="substring-after($Aside,':')"/>
	<xsl:variable name="Aname" select="/CAEXFile//InternalElement[@ID=string($Aguid)]/@Name"/>
	<!-- Side B  -->
	<xsl:variable name="Bside" select="./@RefPartnerSideB"/>
	<xsl:variable name="Bguid" select="substring-before($Bside,':')"/>
	<xsl:variable name="Binterface" select="substring-after($Bside,':')"/>
	<xsl:variable name="Bname" select="/CAEXFile//InternalElement[@ID=string($Bguid)]/@Name"/>
	<xsl:text>&#xA;</xsl:text>
	<!-- CONCAT COMMAND  -->
	<xsl:variable name="CONNECT" select="concat('CONNECT ', $Aguid,':',$Aname,' ',$Ainterface ,' ', $Bguid,':',$Bname,' ',$Binterface)"/>
	<xsl:value-of select="$CONNECT"/>
</xsl:template>

</xsl:stylesheet>