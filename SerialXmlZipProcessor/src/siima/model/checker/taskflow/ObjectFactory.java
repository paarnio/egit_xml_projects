//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.20 at 05:34:28 PM EEST 
//


package siima.model.checker.taskflow;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the siima.model.checker.taskflow package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CheckerTaskFlow_QNAME = new QName("", "checkerTaskFlow");
    private final static QName _Comment_QNAME = new QName("", "comment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: siima.model.checker.taskflow
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckerTaskFlowType }
     * 
     */
    public CheckerTaskFlowType createCheckerTaskFlowType() {
        return new CheckerTaskFlowType();
    }

    /**
     * Create an instance of {@link TestCaseType }
     * 
     */
    public TestCaseType createTestCaseType() {
        return new TestCaseType();
    }

    /**
     * Create an instance of {@link FlowType }
     * 
     */
    public FlowType createFlowType() {
        return new FlowType();
    }

    /**
     * Create an instance of {@link OperationType }
     * 
     */
    public OperationType createOperationType() {
        return new OperationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckerTaskFlowType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "checkerTaskFlow")
    public JAXBElement<CheckerTaskFlowType> createCheckerTaskFlow(CheckerTaskFlowType value) {
        return new JAXBElement<CheckerTaskFlowType>(_CheckerTaskFlow_QNAME, CheckerTaskFlowType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "comment")
    public JAXBElement<String> createComment(String value) {
        return new JAXBElement<String>(_Comment_QNAME, String.class, null, value);
    }

}
