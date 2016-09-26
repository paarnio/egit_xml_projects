/* XMLTransformExample.java
 * 2016-07-02 TOIMII
 * 
 * "I need to write code in Jave using TrAX for transform one XML to another XML.
 * First XML contain information about employees (Name, Salary). 
 * The second XML should contain only those employees 
 * who has salary more than X, X given from keyboard."
 * 
* FROM: http://stackoverflow.com/questions/23864828/provide-trax-parser-example-in-java-xml-to-xml-xslt-transformation
* 
* JAVADOC: http://docs.oracle.com/javase/6/docs/api/javax/xml/transform/package-summary.html
*/

package siima.trax;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class XMLTransformExample {

    public static void main(String[] args) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xsl = new StreamSource("data/input.xsl");
        Transformer transformer = factory.newTransformer(xsl);
        Source xml = new StreamSource("data/data.xml");
        Result result = new StreamResult("data/out.xml");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input min salary:");
        String minSalary = reader.readLine();
        transformer.setParameter("MinSalary", minSalary);
        transformer.transform(xml, result);
        
        /* VPA extension example from:
         * http://www.onjava.com/pub/a/onjava/2001/07/02/trax.html
         * TOIMII
         * "A very common case is that the same transformation is applied multiple times 
         * to different Sources, perhaps in different threads. 
         * A more efficient approach in this case is to process the transformation stylesheet once, 
         * and save this object for successive transformations. 
         * This is achieved through the use of the TraX Templates interface."
         */
        Result result2 = new StreamResult("data/out2.xml");
        // process the XSLT stylesheet into a Templates instance
        // with our TransformerFactory instance
        Templates t = factory.newTemplates(xsl);
        // whenever you need to execute this transformation, create 
        //  a new Transformer instance from the Templates instace
     Transformer trans = t.newTransformer();
     trans.setParameter("MinSalary", "2000");
     trans.transform(xml, result2);
        
    }
}