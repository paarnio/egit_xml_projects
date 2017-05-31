/* Main2B.java
 * TOIMII!!!!
 * Toimii sekä XML datalla: data/mach_part.xml (jossa vain stringi)
 * että "data/mach_part_elem2.xml (jossa lapsielementti <data>)
 * 
 */
package siima.machine2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import siima.foobar.FooBarAnyTypeReader;
import siima.machine4.Component;

public class Main2B {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {

			JAXBContext jc = JAXBContext.newInstance("siima.machine2");
			Unmarshaller u = jc.createUnmarshaller();
			//BOTH XML DOCs are OK "data/mach_part.xml" | "data/mach_part_elem2.xml"
			JAXBElement jelem = (JAXBElement) u.unmarshal(new FileInputStream("data/mach_part.xml")); 
			MachineType mach = (MachineType) jelem.getValue();
			String ctrl = mach.getController();
			int snum = mach.getSeries();
			//part object
			Object part = mach.getPart();
					
			System.out.println("JaxbTool: machine contains: (" + ctrl + ":" + snum + ":" + part.toString() + ")");
			System.out.println("JaxbTool: part class name: (" + part.getClass().getName() + ")");
			
			// THIS is only for checking: create a normal Marshaller and marshal to console
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jelem, System.out);

			/* **** NEW SOLUTION using SPECIAL METHODS **** */

			FooBarAnyTypeReader anysolver = new FooBarAnyTypeReader();

			List<Object> newpart = anysolver.marshal(mach,3); //part is the 3. child of the machine
			System.out.println("newpart object: " + newpart.toString());
			
			/*** -----MAIN2B------ IN CASE: "data/mach_part.xml"  ***/
			
			String[] valueStruct =  newpart.toString().split(": ");
			String valuestring=null;
			if("[[#text".equals(valueStruct[0])){ // IN CASE: "data/mach_part.xml"
				valuestring=valueStruct[1].split("]]")[0];
				System.out.println("Main2B: object has a string value: " + valuestring);
			}
			
						
			/*** -----MAIN2B------ IN CASE: "data/mach_part_elem2.xml"  ***/
			
			if ("[[data".equals(valueStruct[0])) { // Object contains Component
													// type elements

				// -- USING FooBarAnyTypeReader
				Component compo = (Component) anysolver.unmarshal(newpart, null, Component.class);
				System.out.println("compo content: " + compo.getData().toString());

				/* -- OR Directly with the same code simplified (TOIMII) */

				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				document.appendChild(document.createElement("root"));

				if (newpart != null)
					newpart.forEach(
							o -> document.getDocumentElement().appendChild(document.importNode((Node) o, true)));
				
				Unmarshaller unmarshaller = JAXBContext.newInstance(Component.class).createUnmarshaller();
				Component newcompo = unmarshaller.unmarshal(document, Component.class).getValue();
				System.out.println("newcompo content: " + newcompo.getData().toString());

			}
			

		} catch (JAXBException je) {
			je.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}


	}

}
