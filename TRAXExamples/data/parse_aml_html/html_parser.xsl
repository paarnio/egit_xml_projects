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
            <xsl:apply-templates select="amlclassinfo"/>
</xsl:template>

<xsl:template match="amlclassinfo">
	<xsl:element name="siima:parseresults">
	<xsl:apply-templates select="div/div"/>
	</xsl:element>
</xsl:template>

<xsl:template match="div">
 <xsl:element name="siima:div">
 <xsl:choose>
	<xsl:when test="@class='term'">
		<xsl:element name="siima:amlclass">
		<xsl:value-of select="@about"/>
		</xsl:element>
		<xsl:element name="siima:type">
		<xsl:apply-templates select="./h3/span[@rel='rdf:type']"/>
		</xsl:element>
		<xsl:element name="siima:subclassof">
		<xsl:apply-templates select="./div/div/table/tbody/tr/td/span[@rel='rdfs:subClassOf']"/>
		</xsl:element>
	</xsl:when>
	<xsl:otherwise>VPA:DIV:not term</xsl:otherwise>
 </xsl:choose>
 </xsl:element>
</xsl:template>

<xsl:template match="span">
 <!--xsl:element name="siima:span"-->
		<xsl:value-of select="@href"/>
 <!--/xsl:element-->
</xsl:template>



<!--
<xsl:template match="amlclassinfo">
	 <rdf:RDF>
	 	<xsl:comment>VPA: XML2RDF Translation result using proteus_sample_rdf.xsl</xsl:comment>
	 	<xsl:comment>VPA: Check this by RDF Validator: https://www.w3.org/RDF/Validator</xsl:comment>
	<rdf:Description rdf:about="http://siima.net/test/PlantModel">
        
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
    	
        <rdf:type rdf:resource="http://siima.net/test/Equipment/{$hcompclass}"/>
        <siima:name>
            
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
-->

</xsl:stylesheet>