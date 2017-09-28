/* Transformer.java
 * 2017-09-29 TOIMII
 * (See orig idea VPA: XMLTransformExample.java in TRAXExamples project)
 * 
 * FROM: http://stackoverflow.com/questions/23864828/provide-trax-parser-example-in-java-xml-to-xml-xslt-transformation
 * JAVADOC: http://docs.oracle.com/javase/6/docs/api/javax/xml/transform/package-summary.html
 * ------------------------
 * TODO: Which of these Sources to use: 
 * 
 * javax.xml.transform.Source
 * javax.xml.transform.stax.StAXSource
 * 
 */
package siima.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class SerialXSLTransformer {
	
	private TransformerFactory factory;
	
	private Source xml;	
	private Source xsl;
	
	private Templates template;	
	private Transformer transformer;
	
	public SerialXSLTransformer(){
		
		factory = TransformerFactory.newInstance();
		
	}
	
	public boolean invokeXSLTransform(OutputStream outputstream, List<String> params, List<String> values) {

		/* Note: params and values can be null
		 * (?) whenever you need to execute this transformation, create // a new
		 * Transformer instance from the Templates instance
		 * 
		 */
		boolean ok = false;
		try {
			//Transformer transformer;
			transformer = template.newTransformer();

			if ((params != null) && (values != null) && (params.size() == values.size())) {
				// transformer.setParameter("MinSalary", "2000");
				for(int i=0; i<params.size(); i++){
					transformer.setParameter(params.get(i), values.get(i));
				}							
			}
			Result result = new StreamResult(outputstream); // ("data/out2.xml");
			transformer.transform(xml, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean createNewTemplate(InputStream xslinput, String xsluri) {
		/*
		 * process the XSLT stylesheet into a Templates instance // with our
		 * TransformerFactory instance // whenever you need to execute this
		 * transformation, create // a new Transformer instance from the
		 * Templates instance
		 */
		boolean ok = false;
		try {
			
			setXSLSource(xslinput, xsluri);
			template = factory.newTemplates(xsl);
			if(template!=null)ok = true;
		} catch (TransformerConfigurationException e) {

			e.printStackTrace();
		}

		return ok;
	}
	
	public boolean setXMLSource(InputStream xmlinput, String xmluri){
		/* IF xmluri==null OPT1
		 * OPT1: StreamSource(xmlinput)
		 * 
		 * OPT2: StreamSource(xmlinput, xmluri)
		 * This constructor allows the systemID to be set in addition to the input stream, 
		 * which allows relative URIs to be processed. 
		 * Parameters:inputStream - A valid InputStream reference to an XML stream.
		 * systemId - Must be a String that conforms to the URI syntax.
		 */
		boolean ok = false;
		if(xmluri!=null){
			xml = new StreamSource(xmlinput, xmluri);
		} else {
			xml = new StreamSource(xmlinput);
		}
		if(xml!=null) ok = true;
		return ok;
	}
	
	private boolean setXSLSource(InputStream xslinput, String xsluri){
		/* NOTE: Private
		 * IF xsluri==null OPT1
		 * OPT1: StreamSource(xmlinput)
		 * 
		 * OPT2: StreamSource(xmlinput, xmluri)
		 * This constructor allows the systemID to be set in addition to the input stream, 
		 * which allows relative URIs to be processed. 
		 * Parameters:inputStream - A valid InputStream reference to an XML stream.
		 * systemId - Must be a String that conforms to the URI syntax.
		 */
		boolean ok = false;
		if(xsluri!=null){
			xsl = new StreamSource(xslinput, xsluri);
		} else {
			xsl = new StreamSource(xslinput);
		}
		if(xsl!=null) ok = true;
		return ok;
	}

	  public static void main(String[] args) throws Exception {
		  
		/*
		 * Source xsl = new StreamSource("data/input.xsl"); Transformer
		 * transformer = factory.newTransformer(xsl); Source xml = new
		 * StreamSource("data/data.xml"); Result result = new
		 * StreamResult("data/out.xml");
		 */
		  
		  InputStream xmlinput = new FileInputStream("data/tmp/data.xml");
		  InputStream xslinput = new FileInputStream("data/tmp/input.xsl");
		  OutputStream resultoutput = new FileOutputStream("data/tmp/out.xml");
		  
		  SerialXSLTransformer serTrans = new SerialXSLTransformer();
		  serTrans.setXMLSource(xmlinput, null);
		  serTrans.createNewTemplate(xslinput, null);
		  
		  //Setting xsl params
		  List<String> params = new ArrayList<String>(); 
		  List<String> values = new ArrayList<String>();
		  params.add("MinSalary");
		  values.add("2000");
		  
		  serTrans.invokeXSLTransform(resultoutput, params, values);
		  
	  }
}
