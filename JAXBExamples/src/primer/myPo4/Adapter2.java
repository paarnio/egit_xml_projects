//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.27 at 05:36:48 PM EEST 
//


package primer.myPo4;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, Short>
{


    public Short unmarshal(String value) {
        return (javax.xml.bind.DatatypeConverter.parseShort(value));
    }

    public String marshal(Short value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printShort(value));
    }

}
