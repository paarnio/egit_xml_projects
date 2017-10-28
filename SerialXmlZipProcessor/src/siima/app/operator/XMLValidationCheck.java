/* XMLValidationCheck.java
 * From project XMLValidationSimple
 * See: com.journaldev.xml.XMLValidation.java
 * See: http://www.journaldev.com/895/how-to-validate-xml-against-xsd-in-java
 * TOIMII HYVÄ!!!
 * !!! Benefit of using java XML validation API is that 
 * we don’t need to parse the file and there are no third party APIs used.
 *  
 * !!! Benefit of using java XML validation API is that 
 * we don’t need to parse the file and there are no third party APIs used.
 * 
 * Notice that Employee XSD contains two root element and namespace also,
 */
package siima.app.operator;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import siima.app.TaskCycleProcessor;

public class XMLValidationCheck {
	private static final Logger logger=Logger.getLogger(XMLValidationCheck.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	
public boolean validateXMLSchema(String xsdPath, String xmlPath){ // orig. static
	
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
        	logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }


	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}


	public static void main(String[] args) {
		XMLValidationCheck valid = new XMLValidationCheck();
		// Original
	      System.out.println("EmployeeRequest.xml validates against Employee.xsd? "+valid.validateXMLSchema("data/tests/valid/Employee.xsd", "data/tests/valid/EmployeeRequest.xml"));
	      System.out.println("EmployeeResponse.xml validates against Employee.xsd? "+valid.validateXMLSchema("data/tests/valid/Employee.xsd", "data/tests/valid/EmployeeResponse.xml"));
	      System.out.println("employee.xml validates against Employee.xsd? "+valid.validateXMLSchema("data/tests/valid/Employee.xsd", "data/tests/valid/employee.xml"));
	     
	}

}
