//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.02 at 10:31:22 AM EEST 
//


package siima.model.checker.taskflow;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for testCaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="testCaseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="stuDir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stuFile1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stuFile2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="refDir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="refFile1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="refFile2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="operation" type="{}operationType"/&gt;
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
@XmlType(name = "testCaseType", propOrder = {
    "points",
    "stuDir",
    "stuFile1",
    "stuFile2",
    "refDir",
    "refFile1",
    "refFile2",
    "operation",
    "comment"
})
public class TestCaseType {

    @XmlElement(required = true)
    protected BigInteger points;
    @XmlElement(required = true)
    protected String stuDir;
    @XmlElement(required = true)
    protected String stuFile1;
    @XmlElement(required = true)
    protected String stuFile2;
    @XmlElement(required = true)
    protected String refDir;
    @XmlElement(required = true)
    protected String refFile1;
    @XmlElement(required = true)
    protected String refFile2;
    @XmlElement(required = true)
    protected OperationType operation;
    protected String comment;

    /**
     * Gets the value of the points property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoints(BigInteger value) {
        this.points = value;
    }

    /**
     * Gets the value of the stuDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuDir() {
        return stuDir;
    }

    /**
     * Sets the value of the stuDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuDir(String value) {
        this.stuDir = value;
    }

    /**
     * Gets the value of the stuFile1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuFile1() {
        return stuFile1;
    }

    /**
     * Sets the value of the stuFile1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuFile1(String value) {
        this.stuFile1 = value;
    }

    /**
     * Gets the value of the stuFile2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuFile2() {
        return stuFile2;
    }

    /**
     * Sets the value of the stuFile2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuFile2(String value) {
        this.stuFile2 = value;
    }

    /**
     * Gets the value of the refDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefDir() {
        return refDir;
    }

    /**
     * Sets the value of the refDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefDir(String value) {
        this.refDir = value;
    }

    /**
     * Gets the value of the refFile1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFile1() {
        return refFile1;
    }

    /**
     * Sets the value of the refFile1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFile1(String value) {
        this.refFile1 = value;
    }

    /**
     * Gets the value of the refFile2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFile2() {
        return refFile2;
    }

    /**
     * Sets the value of the refFile2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFile2(String value) {
        this.refFile2 = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link OperationType }
     *     
     */
    public OperationType getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationType }
     *     
     */
    public void setOperation(OperationType value) {
        this.operation = value;
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