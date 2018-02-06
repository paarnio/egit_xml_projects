package siima.trax;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class PlainTransform {
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
		  * data/parse_aml_html/html_parser_all_owl.xsl
		  * data/parse_aml_html/aml_html_all_in_one.xml
		  * data/parse_aml_html/parse_results_all_owl.xml
		  * 
		  * data/caex_lego/lego2monkey1.xsl
		  * data/caex_lego/Lego_example_mod1.aml
		  * data/caex_lego/parse_results.xml
		  * 
		  * data/caex_lego/CAEXLego2monkey2.xsl
		  * data/caex_lego/Lego_example_mod1.aml
		  * data/caex_lego/CAEXLego2monkey2_results.xml
		  * 
		  */
	        
	        Source xsl = new StreamSource("data/merge/merge_data_ab.xsl"); //data/caex_lego/filtering_asp_models.xsl
	        Transformer transformer = factory.newTransformer(xsl);
	        //Source xml 
	        Source xml = new StreamSource("data/merge/data_a.xml"); //data/caex_lego/legotower_asp_models.xml
	        
	        Result result = new StreamResult("data/merge/merge_data_result.xml");//data/caex_lego/filtering_results.xml
	        
	        transformer.transform(xml, result);
	        System.out.println("PARSING: ---- Transformation done: See parse results folder-folder. ----");

	}

}
