<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:html="http://www.w3.org/1999/xhtml"
            xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:owl="http://www.w3.org/2002/07/owl#"
            xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
            xmlns:foaf="http://xmlns.com/foaf/spec/"
			xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
            xmlns:siima="http://siima.net/ontologies/2017/test/">
<!-- html_parser_all_owl.xsl -->
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">
	<rdf:RDF xmlns="http://data.ifs.tuwien.ac.at/aml/ontology#" xml:base="http://data.ifs.tuwien.ac.at/aml/ontology" xmlns:amlont="http://data.ifs.tuwien.ac.at/aml/ontology#">
	 	<xsl:comment>VPA: HTML2OWL Translation result using html_parser_all_owl.xsl</xsl:comment>
		<owl:Ontology rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:comment>----owl:Classes-----</xsl:comment>
			<xsl:text>&#xA;</xsl:text>
            <xsl:apply-templates select="amlallinfo" mode="class"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:comment>----owl:Properties-----</xsl:comment>
			<xsl:text>&#xA;</xsl:text>
			<xsl:apply-templates select="amlallinfo" mode="property"/>
	</rdf:RDF>
</xsl:template>

<xsl:template match="amlallinfo" mode="class">
<!-- NOTE: xml:base & xmlns declarations must be  in the folllowing rdf:RDF element-->
<!-- The ns-declarations in stylesheet root element are also copied to the result xml -->
	 
		<xsl:apply-templates select="div/div[@class='term']" mode="class"/>	
	
</xsl:template>

<xsl:template match="div" mode="class">
  <xsl:choose>
	<xsl:when test="./h3/span/@class='parrot-element-title parrot-class-title'"> <!--"@class='term'"> -->
		<xsl:element name="owl:Class">
			<xsl:attribute name="rdf:about">
				<xsl:value-of select="@about"/>
			</xsl:attribute>
			<xsl:element name="rdfs:subClassOf">
				<xsl:attribute name="rdf:resource">		
					<xsl:value-of select="./div/div/table/tbody/tr/td/span[@rel='rdfs:subClassOf']/@href"/>
				</xsl:attribute>
			</xsl:element>
			<xsl:element name="rdfs:label"><xsl:value-of select="./h3/span[@class='parrot-element-title parrot-class-title']"/></xsl:element>
			<xsl:element name="rdfs:comment">ID:<xsl:value-of select="@id"/></xsl:element>
			<xsl:element name="rdfs:comment">Description:<xsl:value-of select="./p[@class='description']"/></xsl:element>
			<xsl:element name="rdfs:comment">Type:<xsl:value-of select="./h3/span[@rel='rdf:type']"/></xsl:element>
		</xsl:element>
	</xsl:when>
	<!--xsl:otherwise>VPA:DIV:not class term</xsl:otherwise-->
 </xsl:choose>
</xsl:template>

<!-- PROPERTY MODE-->
<xsl:template match="amlallinfo" mode="property">

		<xsl:apply-templates select="div/div[@class='term']" mode="property"/>	
	
</xsl:template>

<xsl:template match="div" mode="property">
  <xsl:choose>
	<xsl:when test="./h3/span[@href='http://www.w3.org/2002/07/owl#ObjectProperty']">
		<xsl:element name="owl:ObjectProperty">
			<xsl:attribute name="rdf:about">
				<xsl:value-of select="@about"/>
			</xsl:attribute>
			<!--xsl:element name="rdfs:subClassOf">
				<xsl:attribute name="rdf:resource">		
					<xsl:value-of select="./div/div/table/tbody/tr/td/span[@rel='rdfs:subClassOf']/@href"/>
				</xsl:attribute>
			</xsl:element-->
			<xsl:element name="rdfs:label"><xsl:value-of select="./h3/span[@class='parrot-element-title parrot-property-title']"/></xsl:element>
			<xsl:element name="rdfs:comment">ID:<xsl:value-of select="@id"/></xsl:element>
			<xsl:element name="rdfs:comment">Description:<xsl:value-of select="./p[@class='description']"/></xsl:element>
			<xsl:element name="rdfs:comment">Type:<xsl:value-of select="./h3/span[@rel='rdf:type']/@href"/></xsl:element>
			<xsl:apply-templates select="./div/div/table/tbody/tr/td/span[@rel='rdfs:domain']" mode="property"/>
		</xsl:element>
	</xsl:when>
	<xsl:when test="./h3/span[@href='http://www.w3.org/2002/07/owl#DatatypeProperty']">
		<xsl:element name="owl:DatatypeProperty">
			<xsl:attribute name="rdf:about">
				<xsl:value-of select="@about"/>
			</xsl:attribute>
			<xsl:element name="rdfs:label"><xsl:value-of select="./h3/span[@class='parrot-element-title parrot-property-title']"/></xsl:element>
			<xsl:element name="rdfs:comment">ID:<xsl:value-of select="@id"/></xsl:element>
			<xsl:element name="rdfs:comment">Description:<xsl:value-of select="./p[@class='description']"/></xsl:element>
			<xsl:element name="rdfs:comment">Type:<xsl:value-of select="./h3/span[@rel='rdf:type']/@href"/></xsl:element>
			<xsl:apply-templates select="./div/div/table/tbody/tr/td/span[@rel='rdfs:range']" mode="property"/>
		</xsl:element>
	</xsl:when>
	<xsl:otherwise>VPA:DIV:not term</xsl:otherwise>
 </xsl:choose>
</xsl:template>

<xsl:template match="span[@rel='rdfs:domain']" mode="property">
	<xsl:element name="rdfs:domain">
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="@href"/>
			</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="span[@rel='rdfs:range']" mode="property">
	<xsl:element name="rdfs:range">
			<xsl:attribute name="rdf:resource">
				<xsl:value-of select="@href"/>
			</xsl:attribute>
	</xsl:element>
</xsl:template>

</xsl:stylesheet>