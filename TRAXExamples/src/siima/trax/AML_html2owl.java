package siima.trax;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class AML_html2owl {

	public static void main(String[] args) throws TransformerException {
		 TransformerFactory factory = TransformerFactory.newInstance();
		 /*
		  * data/parse_aml_html/html_parser_2.xsl
		  * data/parse_aml_html/aml_html_classes_p1.xml
		  * data/parse_aml_html/parse_results_2.xml
		  * 
		  * data/parse_aml_html/html_parser_properties2_owl.xsl
		  * data/parse_aml_html/aml_html_properties_p1.xml
		  * data/parse_aml_html/parse_results_properties_owl.xml
		  * 
		  * data/merge/merge_data_ab.xsl
		  * data/merge/data_a.xml
		  * data/merge/merge_data_result.xml
		  * 
		  * 
		  */
	        
	        Source xsl = new StreamSource("data/parse_aml_html/html_parser_all_owl.xsl"); //
	        Transformer transformer = factory.newTransformer(xsl);
	        //Source xml 
	        Source xml = new StreamSource("data/parse_aml_html/aml_html_all_in_one.xml"); //
	        
	        Result result = new StreamResult("data/parse_aml_html/parse_results_all_owl.xml");
	        
	        transformer.transform(xml, result);
	        System.out.println("HTML PARSING: ---- Transformation done: See parse_aml_html-folder. ----");

	}

}
