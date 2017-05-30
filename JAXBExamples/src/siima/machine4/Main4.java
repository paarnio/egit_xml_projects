/* Main4.java
 * TOIMII!!!!
 * 
 * See. MainFool.java TOIMII
 * 
 */

package siima.machine4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import siima.foobar.FooBarAnyTypeReader;
import siima.machine4.MachineType.Part;

public class Main4 {
	
	public static void main(String[] args) {
		try {
			MachineType.Part part = new MachineType.Part();
			part.type = "Component";
		    
		    Node partnode=null;
			
			JAXBContext context = JAXBContext.newInstance("siima.machine4");
			
			 DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();		    
			 Document document = db.parse(new File("data/mach_part_elem4.xml"));
			 System.out.println("FIRST CHILD IS ROOT:" + document.getFirstChild().getNodeName());			    
			 NodeList docnodes = document.getFirstChild().getChildNodes();
			 for (int i=0; i< docnodes.getLength(); i++){
				 Node node = docnodes.item(i);
				 System.out.println(i + ". CHILD: " + node.getNodeName());
				 if("Part".equals(node.getNodeName())) partnode = node;
			 }
			 if(partnode!=null){
				 System.out.println("PART NODE FOUND: " + partnode.getNodeName());
				 System.out.println("PARTs FIRST CHILD: " + partnode.getFirstChild().getNodeName());
				 
				 NodeList nodes = partnode.getChildNodes();
				 //
				 part.content = IntStream.range(0, nodes.getLength()).mapToObj(nodes::item).collect(Collectors.toList());
				 
				 FooBarAnyTypeReader anysolver = new FooBarAnyTypeReader();
				 Component compo = anysolver.unmarshal(part.content, null, Component.class);
					
				 System.out.println("Component's content: " + compo.getData());
			 }
			

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
