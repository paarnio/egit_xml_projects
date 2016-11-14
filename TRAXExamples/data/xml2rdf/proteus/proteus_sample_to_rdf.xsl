<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:html="http://www.w3.org/1999/xhtml"
            xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
            xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
            xmlns:foaf="http://xmlns.com/foaf/spec/"
            xmlns:foo="http://example.com/foo#"
            xmlns:siima="http://siima.net/test/">
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="PlantModel"/>
</xsl:template>

<xsl:template match="PlantModel">
	 <rdf:RDF>
	 	<xsl:comment>VPA: XML2RDF Translation result using proteus_sample_rdf.xsl</xsl:comment>
	 	<xsl:comment>VPA: Check this by RDF Validator: https://www.w3.org/RDF/Validator</xsl:comment>
	<rdf:Description rdf:about="http://siima.net/test/PlantModel">
        <rdf:type rdf:resource="http://siima.net/test/PlantModelClass"/><!-- VPA: tässä ei toimi?? -->
        <siima:hasInformation>
           <xsl:apply-templates select="./PlantInformation"/>
        </siima:hasInformation>
        <xsl:for-each select="./Equipment"> 
         <siima:hasEquipment>
           <xsl:apply-templates select="."/>
        </siima:hasEquipment>
        </xsl:for-each>
		<xsl:apply-templates select="GenericAttributes/GenericAttribute"/>
    </rdf:Description>
      </rdf:RDF>
</xsl:template>

<xsl:template match="PlantInformation">
		<xsl:variable name="origin"><xsl:value-of select="@OriginatingSystem"/></xsl:variable> 
		<rdf:Description rdf:about="http://siima.net/test/PlantInformation/{$origin}">
			<rdf:type rdf:resource="http://siima.net/test/PlantInformation"/>		
		</rdf:Description>
</xsl:template>

<xsl:template match="Equipment">
	<xsl:variable name="component"><xsl:value-of select="@ComponentName"/></xsl:variable>
	<xsl:variable name="hcompclass"><xsl:value-of select="@ComponentClass"/></xsl:variable>
    <rdf:Description rdf:about="http://siima.net/test/Equipment/{$component}">
    	<!-- VPA: tässä seuraavassa ei toimi var name: compclass?? 
    	Ilmeisesti koska 4 ekaa kirjainta olisivat samoja edeltävän var. kanssa-->
        <rdf:type rdf:resource="http://siima.net/test/Equipment/{$hcompclass}"/>
        <siima:name>
            <!-- <xsl:value-of select="@ComponentName"/> -->
			<xsl:value-of select="$component"/>
        </siima:name>
		<xsl:apply-templates select="GenericAttributes/GenericAttribute"/>
			<xsl:apply-templates select="./Nozzle"/>	
    </rdf:Description>
</xsl:template>

<xsl:template match="GenericAttribute">
	<xsl:variable name="attr"><xsl:value-of select="@Name"/></xsl:variable>
	<xsl:variable name="attvalue"><xsl:value-of select="@Value"/></xsl:variable>
	<xsl:element name="siima:{$attr}"><xsl:value-of select="$attvalue"/></xsl:element>
</xsl:template>

<xsl:template match="Nozzle">
	<xsl:variable name="nname"><xsl:value-of select="@ComponentName"/></xsl:variable>
	<xsl:variable name="tag"><xsl:value-of select="@TagName"/></xsl:variable>
	<siima:hasNozzle>
	<rdf:Description rdf:about="http://siima.net/test/Nozzle/{$nname}_{$tag}">
			<rdf:type rdf:resource="http://siima.net/test/Nozzle"/>		
	</rdf:Description>
	</siima:hasNozzle>
</xsl:template>

</xsl:stylesheet>