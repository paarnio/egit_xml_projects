/* Main4B.java
 * TOIMII!!!!
 * Kutsuu myös anysolver.marshal(mach,3) metodia!!
 * 
 * See also Main4.java
 * See. MainFool.java TOIMII
 * 
 */
package siima.machine4;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import siima.foobar.FooBarAnyTypeReader;
import siima.machine4.MachineType.Part;

public class Main4B {

	public static void main(String[] args) {
		try {

			JAXBContext jc = JAXBContext.newInstance("siima.machine4");
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement jelem = (JAXBElement) u.unmarshal(new FileInputStream("data/mach_part_elem4.xml"));
			MachineType mach = (MachineType) jelem.getValue();
			String ctrl = mach.getController();
			int snum = mach.getSeries();
			Part part = mach.getPart();
			List<Object> cons = part.getContent();
			Object cont = null;
			if (cons != null)
				cont = cons.get(0);

			System.out.println("Main4B: machine contains: (" + ctrl + ":" + snum + ":" + part.toString() + ")");
			System.out.println("Main4B: part class name: (" + part.getClass().getName() + ")");
			if (cont != null) {
				System.out.println("Main4B: content class name: (" + cont.getClass().getName() + ")");

				if ("String".equals(cont.getClass().getSimpleName())) {

					System.out.println("Main4B: content string value: (" + ((String) cont) + ")");

				} else {
					System.out.println("JaxbTool: content value?: (" + cont.toString() + ")" + cont);
				}

			}

			// create a Marshaller and marshal to a file
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jelem, System.out);

			/* **** NEW SOLUTION TRY **** */

			FooBarAnyTypeReader anysolver = new FooBarAnyTypeReader();

			// ??EI TOIMI (johtuuko siitä, että Part on MachineTypen inner class??) JOHTUI !!!
			// Instance of "siima.machine4.MachineType$Part" is substituting "java.lang.Object", 
			// but "siima.machine4.MachineType$Part" is bound to an anonymous type.]
			part.content = anysolver.marshal(mach,3); //part is the 3. child of the machine

			Component compContent = anysolver.unmarshal(part.content, null, Component.class);

			System.out.println("CONTENT: " + compContent.getData());

		} catch (JAXBException je) {
			je.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
