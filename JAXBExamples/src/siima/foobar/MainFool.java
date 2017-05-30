package siima.foobar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class MainFool {

	public static void main(String[] args) {
/*
 * TÄMÄKIN TOIMIII mallia otettu:   
 * FooBarAnyTypeReader's main-method TOIMII
 * 
 */
		 
		try {
			Foo foo = new Foo();
		    foo.type = "Bar";;
			
			JAXBContext context = JAXBContext.newInstance("siima.foobar"); //Foo.class, Bar.class); //"siima.foobar");
			/*
			Unmarshaller u = context.createUnmarshaller();
			Foo foo = (Foo) u.unmarshal(new FileInputStream("data/foobar.xml"));			
			System.out.println("FOO's ATTRIBUTE: " + foo.getType());
			
			Marshaller marshaller = context.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			 */
		    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();		    
		    Document document = db.parse(new File("data/foobar.xml"));
		    /*
		    document.getDocumentElement().normalize();
			System.out.println("Root element :" + document.getDocumentElement().getNodeName());
		    */
		    System.out.println("FIRST CHILD:" + document.getFirstChild().getNodeName());
		    System.out.println("FIRST CHILD's FIRST CHILD: " + document.getFirstChild().getFirstChild().getNodeName());
		    
		    NodeList nodes = document.getFirstChild().getChildNodes();
		    //INPUT BAR INTO FOO.content
		    foo.content = IntStream.range(0, nodes.getLength()).mapToObj(nodes::item).collect(Collectors.toList());
		   
			FooBarAnyTypeReader anysolver = new FooBarAnyTypeReader();
			Bar contentbar = anysolver.unmarshal(foo.content, null, Bar.class);
			
			System.out.println("BAR ID: " + contentbar.getId());
			
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
