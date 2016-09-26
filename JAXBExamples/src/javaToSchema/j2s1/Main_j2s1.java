/*
 * 
 * Java-to-Schema JAXB Examples
 * --------------------------
 * Create Marshal 	Demonstrates how to use the ObjectFactory class 
 * to create a Java content tree and marshal it to XML data. 
 * It also demonstrates how to add content to a JAXB List property.
 * 
 * FROM: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\samples\
 * SEE: https://docs.oracle.com/javase/tutorial/jaxb/intro/examples.html
 */

package javaToSchema.j2s1;


import java.io.File;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javaToSchema.j2s1.cardfile.BusinessCard;
import javaToSchema.j2s1.cardfile.Address;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.bind.ValidationEventLocator;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;

public class Main_j2s1 {
    public static void main(String[] args) throws Exception {
        final File f = new File("src/javaToSchema/j2s1/bcard.xml");

        // Illustrate two methods to create JAXBContext for j2s binding.
        // (1) by root classes newInstance(Class ...)
        JAXBContext context1 = JAXBContext.newInstance(javaToSchema.j2s1.cardfile.BusinessCard.class);

        // (2) by package, requires jaxb.index file in package cardfile.
        //     newInstance(String packageNames)
        JAXBContext context2 = JAXBContext.newInstance("javaToSchema.j2s1.cardfile");
        
        Marshaller m = context1.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(getCard(), System.out);

        // illustrate optional unmarshal validation.
        Marshaller m2 = context1.createMarshaller();
        m2.marshal(getCard(), new FileOutputStream(f));
        Unmarshaller um = context2.createUnmarshaller();
        um.setSchema(getSchema("schema1.xsd")); //"src/javaToSchema/j2s1/schema1.xsd"
        Object bce = um.unmarshal(f);
        m.marshal(bce, System.out);
    }

    /** returns a JAXP 1.3 schema by parsing the specified resource. */
    static Schema getSchema(String schemaResourceName) throws SAXException {
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        try {
            return sf.newSchema(javaToSchema.j2s1.Main_j2s1.class.getResource(schemaResourceName));
        } catch (SAXException se) {
            // this can only happen if there's a deployment error and the resource is missing.
            throw se;
        }
    }

    private static BusinessCard getCard() {
        return new BusinessCard("John Doe", "Sr. Widget Designer", "Acme, Inc.",
                new Address(null, "123 Widget Way", "Anytown", "MA", (short) 12345), "123.456.7890",
                null, "123.456.7891", "John.Doe@Acme.ORG");
    }
}
