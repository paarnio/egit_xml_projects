/* XMLToRDFParser.java
 * Generic XML to RDF transformer in Java
 * Below is the source code for a generic XML to RDF transformer, written in the
 * Java programming language. It takes any XML file as input and provides an RDF
 * model as output. It does not make any assumptions of the source format, so it is
 * guaranteed to work on any kind of XML, though the quality of the output depends
 * on how well the source data translates into triple-based data.
 * Joonas Laitio D-työ liite A
 */

package siima.transform;


import java.io.File ;
import java.io.FileReader ;
import java.util.Stack ;
import org.apache.xerces.parsers.SAXParser ;
import org.xml.sax.Attributes ;
import org.xml.sax.InputSource ;
import org.xml.sax.SAXException ;
import org.xml.sax.helpers.DefaultHandler ;
import com.hp.hpl.jena.rdf.model.Model ;
import com.hp.hpl.jena.rdf.model.ModelFactory ;
import com.hp.hpl.jena.rdf.model.Resource ;
import com.hp.hpl.jena.vocabulary.RDF ;
import com.hp.hpl.jena.vocabulary.RDFS ;
/**
*
* A generic XML to RDF transformer.
* Depends on Apache Xerces for XML and
* the Jena framework for RDF manipulation
*
* @author Joonas Laitio
*/
public class XMLToRDFParser extends DefaultHandler
{
/**
* Controls if empty elements or elements containing only whitespace should
* be generated into empty literal triples in the RDF output.
*/
private static final boolean makeEmptyLiteralTriples = false ;
private final String NS;
private Model m;
private Stack < Resource > elementStack = new Stack < Resource >() ;
private int serial = 0;
private String lastOpened ;
private StringBuilder lastValue = null ;
private XMLToRDFParser( String prefix , String uri )
{
this.m = ModelFactory.createDefaultModel();
this.m.setNsPrefix( prefix , uri );
this.NS = uri ;
}
@Override
public void startElement( String uri , String localName ,
String qName , Attributes attributes )

throws SAXException
{
this.lastOpened = qName ;
if( serial % 10000 == 0)
System.out.print(".");
if( serial % 1000000 == 0)
System.out.println();
// Create a new resource for this element(if this element happens
// to contain only a text node , no triples are ever made for this
// resource )
Resource r = m.getResource(NS + qName
+ Long.toHexString( System.currentTimeMillis())
+ Long.toHexString(++ serial ));
if( attributes.getLength() > 0)
{
for(int i = 0 ; i < attributes.getLength() ; i ++)
m.add(r, m.getProperty(NS + attributes.getLocalName(i)),
attributes.getValue(i));
}
this.elementStack.push(r);
this.lastValue = new StringBuilder();
}
@Override
public void characters( char[] ch , int start , int length )
throws SAXException
{
String value = new String(ch , start , length );
if( value.trim().isEmpty())
return ;
this.lastValue.append( value );
}
@Override
public void endElement( String uri , String localName ,
String qName )
throws SAXException
{
if( lastOpened.equals( qName ) && lastValue != null &&
this.elementStack.size() > 1)
{
// Literal value
this.elementStack.pop();
String value = this.lastValue.toString();
if(! value.trim().isEmpty() || makeEmptyLiteralTriples )
m.add( this.elementStack.peek() , m.getProperty(NS + qName ), value );
this.lastValue = null ;
}
else if(! lastOpened.equals( qName ) && this.elementStack.size() > 1)
{
// Object value
Resource was = this.elementStack.pop();
m.add( this.elementStack.peek() , m.getProperty(NS + "has_" + qName ),
was );
m.add(was , RDF.type , m.getResource(NS + qName ));
}
else if( this.elementStack.size() <= 1)
this.elementStack.pop();
this.lastValue = new StringBuilder();
}

private Model getModel()
{
return this.m;
}

/**
* Transforms an XML file into RDF
*
* @param file The XML file
* @param prefix Desired namespace prefix for the document(e.g."seco - core ")
* @param uri The URI corresponding to the above namespace prefix
*(e.g." http :// www.yso.fi/ onto /seco - core /")
* @return Jena Model of the generated RDF
*/
public static Model parse( File file , String prefix , String uri )
{
XMLToRDFParser parser = new XMLToRDFParser( prefix , uri );
try
{
InputSource data = new InputSource( new FileReader( file ));
SAXParser saxParser = new SAXParser();
saxParser.setContentHandler( parser );
saxParser.parse( data );
}
catch( Exception e) {
e.printStackTrace();
}
System.out.println();
parser.getModel().setNsPrefix("rdf", RDF.getURI());
parser.getModel().setNsPrefix("rdfs", RDFS.getURI());
return parser.getModel();
}
}
