/*
 * Source Files from: 
 * http://stackoverflow.com/questions/10499281/using-jena-for-creating-rdf-from-xml-file
 * 
 * See also GRDDL:
 * https://www.w3.org/TR/grddl-primer/
 */
package siima.trax;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MyXML2RDF {

	   public static void main(String[] args) throws Exception {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xsl = new StreamSource("data/xml2rdf/websitecritic.xsl");
	        Transformer transformer = factory.newTransformer(xsl);
	        Source xml = new StreamSource("data/xml2rdf/websitecritic.xml");
	        Result result = new StreamResult("data/xml2rdf/results/websitecritic.rdf");

	        /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	        System.out.println("Input min salary:");
	        String minSalary = reader.readLine();
	        transformer.setParameter("MinSalary", minSalary);
	        */
	        transformer.transform(xml, result);
	   }
	
	
}
