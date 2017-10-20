//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.20 at 05:34:28 PM EEST 
//


package siima.model.checker.taskflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for checkerTaskFlowType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkerTaskFlowType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="stuZip" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="refZip" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="testCase" type="{}testCaseType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}comment" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkerTaskFlowType", propOrder = {
    "stuZip",
    "refZip",
    "testCase",
    "comment"
})
public class CheckerTaskFlowType {

    @XmlElement(required = true)
    protected String stuZip;
    @XmlElement(required = true)
    protected String refZip;
    protected List<TestCaseType> testCase;
    protected String comment;

    /**
     * Gets the value of the stuZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuZip() {
        return stuZip;
    }

    /**
     * Sets the value of the stuZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuZip(String value) {
        this.stuZip = value;
    }

    /**
     * Gets the value of the refZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefZip() {
        return refZip;
    }

    /**
     * Sets the value of the refZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefZip(String value) {
        this.refZip = value;
    }

    /**
     * Gets the value of the testCase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the testCase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestCase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TestCaseType }
     * 
     * 
     */
    public List<TestCaseType> getTestCase() {
        if (testCase == null) {
            testCase = new ArrayList<TestCaseType>();
        }
        return this.testCase;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

}
