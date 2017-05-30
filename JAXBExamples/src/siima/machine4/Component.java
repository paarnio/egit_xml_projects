package siima.machine4;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "data" })
@XmlRootElement(name = "Component")
public class Component {

    protected String data;

	public String getData() {
		return data;
	}

	public void setData(String value) {
		this.data = value;
	}
	
	
	
}
