/* XMLWellFormedCheck.java
 * Operator
 * See XMLValidationSimple project
 * Well-formed xml:
 * from: http://stackoverflow.com/questions/38255981/stax-well-formedness-check-of-xml
 */

package siima.app.operator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import siima.app.XSLTransformer;

public class XMLWellFormedCheck {
	private static final Logger logger=Logger.getLogger(XMLWellFormedCheck.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	//private XMLInputFactory factory;
	
	/* Constructor */
	public XMLWellFormedCheck(){
		//XMLInputFactory factory = XMLInputFactory.newInstance();
	}
	
	public void checkWellFormedness(String xmlFile){
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: invokeXSLTransform()");
		XMLInputFactory factory = XMLInputFactory.newInstance();
		File f = new File(xmlFile); //"data/tests/simple_not_wf.xml");
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(f);
			// Instantiate a reader parsing:
			XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
			while (reader.hasNext()) {
				// check to be implemented??
				reader.next();
			}

			inputStream.close();

		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		} catch (XMLStreamException e) {			
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			// e.printStackTrace();		
		} catch (IOException e) {
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("END: " + operErrorBuffer.toString());
	}

	
	
	
	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}

	public static void main(String[] args) {
		XMLWellFormedCheck wfchecker = new XMLWellFormedCheck();
		wfchecker.checkWellFormedness("data/tests/simple_not_wf.xml");

	}

}
