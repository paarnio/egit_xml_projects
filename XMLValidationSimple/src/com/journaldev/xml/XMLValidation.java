/* com.journaldev.xml.XMLValidation.java
 * FROM: http://www.journaldev.com/895/how-to-validate-xml-against-xsd-in-java
 * TOIMII HYVÄ!!!
 * !!! Benefit of using java XML validation API is that 
 * we don’t need to parse the file and there are no third party APIs used.
 *  
 * !!! Benefit of using java XML validation API is that 
 * we don’t need to parse the file and there are no third party APIs used.
 * 
 * Notice that Employee XSD contains two root element and namespace also,
 */

package com.journaldev.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XMLValidation {

    public static void main(String[] args) {
       // Original
      System.out.println("EmployeeRequest.xml validates against Employee.xsd? "+validateXMLSchema("data/xsd/journaldev_schema/Employee.xsd", "data/xml/journaldev/EmployeeRequest.xml"));
      System.out.println("EmployeeResponse.xml validates against Employee.xsd? "+validateXMLSchema("data/xsd/journaldev_schema/Employee.xsd", "data/xml/journaldev/EmployeeResponse.xml"));
      System.out.println("employee.xml validates against Employee.xsd? "+validateXMLSchema("data/xsd/journaldev_schema/Employee.xsd", "data/xml/journaldev/employee.xml"));
      // VPA additions
      System.out.println("=========VPA: proteus===========");
      System.out.println("proteus.xml validates against P&ID proteus.xsd? "+validateXMLSchema("data/xsd/proteus_schemas/ProteusP&IDProfileSchema_3.6.0draft.xsd", "data/xml/proteus/dexpi_best_practice.proteus.xml"));
      System.out.println("proteus.xml validates against the full proteus.xsd? "+validateXMLSchema("data/xsd/proteus_schemas/ProteusSchema_3.6.0_draft.xsd", "data/xml/proteus/dexpi_best_practice.proteus.xml"));
      }
    
    public static boolean validateXMLSchema(String xsdPath, String xmlPath){
        
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
}