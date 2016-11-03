<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:html="http://www.w3.org/1999/xhtml"
            xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
            xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
            xmlns:foaf="http://xmlns.com/foaf/spec/"
            xmlns:foo="http://example.com/foo#"
            xmlns:siima="http://siima.net/test/">
<xsl:output method="xml" indent="yes"/>
<xsl:template match="/">
    <rdf:RDF>
            <xsl:apply-templates/>
    </rdf:RDF>
</xsl:template>

<xsl:template match="Equipment">
<xsl:variable name="component"><xsl:value-of select="@ComponentName"/></xsl:variable>
<xsl:variable name="compclass"><xsl:value-of select="@ComponentClass"/></xsl:variable>
    <rdf:Description rdf:about="http://siima.net/test/{$component}">
        <rdf:type rdf:resource="http://siima.net/test/{$compclass}"/><!-- VPA: tässä ei toimi?? -->
        <siima:name>
            <!-- <xsl:value-of select="@ComponentName"/> -->
			<xsl:value-of select="$component"/>
        </siima:name>
		<xsl:apply-templates select="GenericAttributes/GenericAttribute"/>
    </rdf:Description>
</xsl:template>

<xsl:template match="GenericAttribute">
<xsl:variable name="attr"><xsl:value-of select="@Name"/></xsl:variable>
<xsl:element name="siima:{$attr}"><xsl:value-of select="@Name"/></xsl:element>
</xsl:template>
</xsl:stylesheet>