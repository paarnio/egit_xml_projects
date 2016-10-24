/* XMLToRDF.java
 * main method: XML file transformed to RDF file by using XMLToRDFParser.java
 * Generic XML to RDF transformer in Java
 * Below is the source code for a generic XML to RDF transformer, written in the
 * Java programming language. It takes any XML file as input and provides an RDF
 * model as output. It does not make any assumptions of the source format, so it is
 * guaranteed to work on any kind of XML, though the quality of the output depends
 * on how well the source data translates into triple-based data.
 * Joonas Laitio D-työ liite A
 * 
 * 
 * Jena Java Api:http://incubator.apache.org/jena/documentation/javadoc/jena/index.html
 * 
 */


package siima.main;

import java.io.File;
import java.io.FileWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFWriter;

import siima.transform.XMLToRDFParser;

public class XMLToRDF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		File xmlfile = new File("./data/agriplan3.xml");
		Model rdfmod=XMLToRDFParser.parse(xmlfile, "agri", "http://www.siima.org/ns/2012/");
		
		RDFWriter wr=rdfmod.getWriter();
		//System.out.println("RESULT:\n" + wr.toString());
		try {
		FileWriter fr = new FileWriter(new File("./data/agriplan3.rdf"));
		wr.write(rdfmod, fr, null);
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
