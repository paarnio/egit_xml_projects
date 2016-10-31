/*
 * from:http://docs.oracle.com/javase/7/docs/api/javax/xml/validation/package-summary.html
 * TOIMII
 * MUTTA HUOMAA, että tuo toinen koodi on parempi: 
 * PAREMPI => com.journaldev.xml.XMLValidation.java
 * 
 * 
 * VALIDATION FINDS SEVERAL ERRORS FROM XML FILE:
 * 
 * ERROR 2:
 * -------
 * Finds an error:org.xml.sax.SAXParseException; cvc-id.1: There is no ID/IDREF binding for IDREF 'H1008-N1-1'.
 * 
 * REASON in xsd-file
 * "ItemID" type="xsd:IDREF" references-> name="Identifier" type="xsd:string" NOT xsd:ID.
 * VPA: korjaus: type="xsd:ID"
 * 
 * <xsd:attributeGroup name="ItemReferenceGroup">
		<xsd:attribute name="ItemID" type="xsd:IDREF" use="optional"/>
		<xsd:attribute name="TagName" type="xsd:string" use="optional"/>
		<xsd:attribute name="Name" type="xsd:string" use="optional"/>
		<xsd:attribute name="ItemURI" type="xsd:anyURI" use="optional"/>
		<xsd:attribute name="PersistentIDIdentifier" type="xsd:string" use="optional"/>
		<xsd:attribute name="PersistentIDContext" type="xsd:string" use="optional"/>
	</xsd:attributeGroup>
	<xsd:element name="PersistentID" type="PersistentID"/>
	<xsd:complexType name="PersistentID">
		<xsd:annotation>
	<!-- VPA: tämän vuoksi se ei voi olla ID, koska se ei ole yksikäsitteinen ??? -->
			<xsd:documentation>This Element holds the persistent Identifier for a PlantItem. 
			There can be more than one PersistentID and if so the Context must be used so that 
			<!-- =================================== -->
			they can be separately identified</xsd:documentation>
		</xsd:annotation>
		<xsd:attribute name="Identifier" type="xsd:string" use="required"/>
		<xsd:attribute name="Context" type="xsd:string" use="optional"/>
	</xsd:complexType>
 * 
 * ERROR 3:
 * -------
 * '855' is not a valid value for 'NCName'.
 * 
 */
package siima.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLValidator {

	public static void main(String[] args) {
		// parse an XML document into a DOM tree
	    DocumentBuilder parser;
		try {
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
	    Document document = parser.parse(new File("data/xml/proteus/dexpi_best_practice.proteus.xml"));

	    // create a SchemaFactory capable of understanding WXS schemas
	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

	    // load a WXS schema, represented by a Schema instance
	    Source schemaFile = new StreamSource(new File("data/xsd/proteus_schemas/ProteusP&IDProfileSchema_3.6.0draft.xsd"));
	    Schema schema = factory.newSchema(schemaFile);

	    // create a Validator instance, which can be used to validate an instance document
	    Validator validator = schema.newValidator();

	    // validate the DOM tree
	    //try {
	        validator.validate(new DOMSource(document));
	    
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
