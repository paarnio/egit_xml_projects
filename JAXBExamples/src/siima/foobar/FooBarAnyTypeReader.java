/* FooBarAnyTypeReader.java
 * 
 * unmarshalling xs:anyType content TOIMII!!
 * 
 * TESTING SOLUTION IN:
 * https://stackoverflow.com/questions/36901915/jax-ws-jaxb-and-unmarshalling-mixed-xsanytype
 * 
 * JAXB Classes created in:
 * C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\anytype_test
 * 
 */
package siima.foobar;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.w3c.dom.NodeList;

public class FooBarAnyTypeReader {
	
	/* SOLUTION method*/
	public static List<Object> marshal(Object value) { //EI TOIMI???
	    try {
	        Class<?> type = value.getClass();
	        //System.out.println("Type before: " + type.getName());
	        if (type.isAnonymousClass())
	            type = type.getSuperclass();
	        ///System.out.println("Type after: " + type.getName());
	        
	        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        Marshaller marshaller = JAXBContext.newInstance(type).createMarshaller(); //orig.
	        //VPA TEST: Marshaller marshaller = JAXBContext.newInstance(Foo.class, type).createMarshaller();
	        marshaller.marshal(new JAXBElement<>(QName.valueOf("root"), Object.class, value), document); // orig
	        

	        NodeList nodes = document.getDocumentElement().getChildNodes();
	        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item).collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	/* SOLUTION method TOIMII*/
	public static <T> T unmarshal(List<Object> content, Map<QName, String> attributes, Class<T> type) {
	    try {
	        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        document.appendChild(document.createElement("root"));

	        if (attributes != null)
	            attributes.forEach((q, s) -> document.getDocumentElement().setAttribute(q.getLocalPart(), s));

	        if (content != null)
	            content.forEach(o -> document.getDocumentElement().appendChild(document.importNode((Node) o, true)));

	        Unmarshaller unmarshaller = JAXBContext.newInstance(type).createUnmarshaller();
	        return unmarshaller.unmarshal(document, type).getValue();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	public static void main(String[] args) {
		/* Code snippet by Questioner
		 * I can marshall a Foo object to its root-less form using Document.getChildNodes(),
		 *  but I cannot figure out how to unmarshal response's content list 
		 *  (which is ArrayList<ElementNSImpl> after proxy is done with it) to strongly-typed Bar?
		 */
		
		Foo foo = new Foo();
	    foo.type = "Bar";

	    Bar bar = new Bar();
	    bar.id = 123;
	    try {
	    JAXBContext context = JAXBContext.newInstance(Foo.class, Bar.class);
	    Marshaller marshaller = context.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	    //MARSHAL BAR
	    marshaller.marshal(bar, document);

	    System.out.println("FIRST CHILD:" + document.getFirstChild().getNodeName());
	    
	    NodeList nodes = document.getFirstChild().getChildNodes();
	    //INPUT BAR INTO FOO.content
	    foo.content = IntStream.range(0, nodes.getLength()).mapToObj(nodes::item).collect(Collectors.toList());

	    StringWriter writer = new StringWriter();
	    //MARSHAL FOO
			marshaller.marshal(foo, writer);
			
			System.out.println(writer); //TOIMII
			
			/* HERE HE HAD PROBLEMS ???
			Unmarshaller unmarshaller = context.createUnmarshaller();
		    unmarshaller.unmarshal(foo.content, Bar.class);
		    */
			
		/* ******* BY SOLUTION PROVIDER ***** */
			/*Foo newfoo = new Foo();
		    newfoo.type = "Bar";

		    Bar newbar = new Bar();
		    newbar.id = 456;*/
	
			FooBarAnyTypeReader anysolver = new FooBarAnyTypeReader();
			
			//EI TOIMI
			//newfoo.content = anysolver.marshal(newbar);
			
			Bar contentbar = anysolver.unmarshal(foo.content, null, Bar.class);
			
			System.out.println("CONTENT: " + contentbar.getId());
			
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	}

}
