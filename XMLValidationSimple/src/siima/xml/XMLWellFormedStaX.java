/*
 * Well-formed xml:
 * from: http://stackoverflow.com/questions/38255981/stax-well-formedness-check-of-xml
 * 
 * LOGGER: see file ./log/log.out
 * For Logger See my: ModelSpinManager.java in ContextMngBySPIN project
 * http://www.tutorialspoint.com/log4j/
 * logger example: https://www.tutorialspoint.com/log4j/log4j_sample_program.htm
 */
package siima.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class XMLWellFormedStaX {
	
	private static final Logger logger=Logger.getLogger(XMLWellFormedStaX.class.getName()); //(org.apache.log4j.Logger.class.getName());
	 /* Get actual class name to be printed on */
	  // static Logger log = Logger.getLogger(XMLWellFormedStaX.class.getName());
	

	public static void main(String[] args) {
		StringBuffer errMessageBuff = new StringBuffer();
		
		System.out.println("LOGGER is " + logger.getName());
		System.out.println("APPENDER is " + logger.getAppender("FILE"));
		
		 logger.debug("Hello this is a debug message"); // TÄMÄ EI LOGGAA
	     logger.info("Hello this is an info message");
	     
	     
	     
		XMLInputFactory factory = XMLInputFactory.newInstance();
		File f = new File("data/xml/wellform/simple_not_wf.xml");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {

			// e.printStackTrace();
			//System.out.println("Exception: " + e.getMessage());
			errMessageBuff.append("\nMYERRBUFF: " + e.getMessage());
			logger.log(Level.ERROR, e.getMessage());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("END: " + errMessageBuff.toString());
	}

}
