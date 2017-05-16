/*
 * JaxbTool.java XS:ANYTYPE PROBLEM SOLVED
 * ---------------------------------------
 * SOlVING PROBLEM WITH xs:anyType element which jaxb binds to Object.
 * Using schema machine2.xsd (option2) and jaxb classes in siima.machine2 package.
 * SOLUTION: Changing the type of the field Object part to String part in MachineType.java
 * SEE: http://stackoverflow.com/questions/3488141/xsd-anytype-and-jaxb
 * 
 * The jaxb classes are genereted using xjc tool in:
 * C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\anytype_test
 * 
 */

package siima.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import primer.po1.PurchaseOrderType;
import primer.po1.USAddress;
import siima.machine4.Component;
//import siima.machine.Machine;
//import siima.machine2.MachineType; //OPTION 2
//import siima.machine3.MachineType; //OPTION 3
import siima.machine4.MachineType; //OPTION 4
import siima.machine4.MachineType.Part;

public class JaxbTool {
	public static void main(String[] args) {
		try {
			// TESTING WITH TWO SCHEMA VERSIONS machine and machine2
			// create a JAXBContext capable of handling classes generated into
			// the siima.machine package OR siima.machine2

			JAXBContext jc = JAXBContext.newInstance("siima.machine4"); // "siima.machine"
																		// "siima.machine2"
																		// "siima.machine3"
																		// "siima.machine4"

			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();

			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the siima.machine package.

			// OPTION 1 genereted by machine.xsd

			// Machine mach = (Machine)u.unmarshal( new FileInputStream(
			// "data/mach_part.xml" ) ); //machine.xsd

			/* OPTION 2 genereted by machine2.xsd TOIMII with String content */

			JAXBElement jelem = (JAXBElement) u.unmarshal(new FileInputStream("data/mach_part_elem.xml")); // "data/mach_part.xml"
																										// data/mach_part_elem.xml
			MachineType mach = (MachineType) jelem.getValue();

			/*
			 * OPTION 3 genereted by machine2.xsd BUT jaxb classes modified and
			 * extended Extended (mach_part_elem.xml) (EI TOIMI)
			 * 
			 * JAXBElement jelem = (JAXBElement)u.unmarshal( new
			 * FileInputStream( "data/mach_part_elem.xml" ) ); MachineType mach
			 * = (MachineType)jelem.getValue();
			 */
			String ctrl = mach.getController();
			int snum = mach.getSeries();
			// Object part = mach.getPart();
			// OPTION 3 String part= (String) mach.getPart();
			// OPTION 4:
			Part part = mach.getPart();
			List<Object> cons = part.getContent();
			Object content = null;
			if (cons != null)
				content = cons.get(0);

			System.out.println("JaxbTool: machine contains: (" + ctrl + ":" + snum + ":" + part.toString() + ")");
			System.out.println("JaxbTool: part class name: (" + part.getClass().getName() + ")");
			if (content != null) {
				System.out.println("JaxbTool: content class name: (" + content.getClass().getName() + ")");

				if ("String".equals(content.getClass().getSimpleName())) {
					// IF: data/mach_part.xml														
					System.out.println("JaxbTool: content string value: (" + ((String) content) + ")");

				} else { // IF: data/mach_part_elem.xml (<Part><Component>COMPONENT</Component></Part>)
					System.out.println("JaxbTool: content value?: (" + content.toString()+ ")" + content ); //[Component: null]
					//Component comp = (Component)content; //com.sun.org.apache.xerces.internal.dom.ElementNSImpl cannot be cast to siima.machine4.Component
					//System.out.println("JaxbTool: Component value?: (" + comp.getContent() + ")" + content );
				}

			}

			// create a Marshaller and marshal to a file
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// m.marshal( jelem, System.out );

		} catch (JAXBException je) {
			je.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
