package siima.machine2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import siima.foobar.FooBarAnyTypeReader;
//import siima.machine2.Component;
import siima.machine2.MachineType;
//import siima.machine4.Component;
//import siima.machine2.MachineType.Part;

public class Main2 {

	public static void main(String[] args) {
		
		try {

		JAXBContext jc = JAXBContext.newInstance("siima.machine2");
		Unmarshaller u = jc.createUnmarshaller();
		JAXBElement jelem = (JAXBElement) u.unmarshal(new FileInputStream("data/mach_part.xml")); //"data/mach_part.xml" //"data/mach_part_elem2.xml"
		MachineType mach = (MachineType) jelem.getValue();
		String ctrl = mach.getController();
		int snum = mach.getSeries();
		//part object
		Object part = mach.getPart();
		/* part as string
		String partstr=mach.getPart();
		System.out.println("Main2: machine part: ("  + partstr + ")");
		*/
		
		/*
		List<Object> cons = part.getContent();
		Object cont = null;
		if (cons != null)
			cont = cons.get(0);
		*/
		
		System.out.println("JaxbTool: machine contains: (" + ctrl + ":" + snum + ":" + part.toString() + ")");
		System.out.println("JaxbTool: part class name: (" + part.getClass().getName() + ")");
		/*
		if (cont != null) {
			System.out.println("JaxbTool: content class name: (" + cont.getClass().getName() + ")");

			if ("String".equals(cont.getClass().getSimpleName())) {

				System.out.println("JaxbTool: content string value: (" + ((String) cont) + ")");

			} else {
				System.out.println("JaxbTool: content value?: (" + cont.toString() + ")" + cont);
			}

		} */

		// create a Marshaller and marshal to a file
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(jelem, System.out);

		/* **** NEW SOLUTION TRY **** */

		FooBarAnyTypeReader anysolver = new FooBarAnyTypeReader();

		// ??EI TOIMI (johtuuko siitä, että Part on MachineTypen inner class??) JOHTUI !!!
		// Instance of "siima.machine4.MachineType$Part" is substituting "java.lang.Object", 
		// but "siima.machine4.MachineType$Part" is bound to an anonymous type.]
		Object newpart = anysolver.marshal(mach,3); //part is the 3. child of the machine
		System.out.println("newpart object: " + newpart.toString());
		
		/* Tämä ei toimi edes mach_part_elem2.xml
		 * java.util.ArrayList cannot be cast to org.w3c.dom.Node
		List<Object> contents = new ArrayList<Object>();
		contents.add(newpart);
		Component compo = (Component) anysolver.unmarshal(contents, null, Component.class);
		System.out.println("newpart content: " + compo.getData().toString());
		*/

	} catch (JAXBException je) {
		je.printStackTrace();
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}

	}

}
