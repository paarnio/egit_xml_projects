/*
 * JaxbTool.java XS:ANYTYPE PROBLEM SOLVED
 * ---------------------------------------
 * SOlVING PROBLEM WITH xs:anyType element which jaxb binds to Object.
 * Using schema machine2.xsd (option2) and jaxb classes in siima.machine2 package.
 * SOLUTION: Changing the type of the field Object part to String part in MachineType.java
 * SEE: http://stackoverflow.com/questions/3488141/xsd-anytype-and-jaxb
 * 
 * The jaxb classes are genereted using xjc tool in:
 * C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\vpa_work\anytype_test
 * 
 */

package siima.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import primer.po1.PurchaseOrderType;
import primer.po1.USAddress;
import siima.machine.Machine;
//import siima.machine2.MachineType; //OPTION 2
import siima.machine3.MachineType; //OPTION 3

public class JaxbTool {
	 public static void main( String[] args ) {
	        try {
	        	// TESTING WITH TWO SCHEMA VERSIONS machine and machine2
	            // create a JAXBContext capable of handling classes generated into
	            // the siima.machine package OR siima.machine2
	        	
	            JAXBContext jc = JAXBContext.newInstance( "siima.machine3" ); //"siima.machine" "siima.machine2" "siima.machine3"
	            
	            // create an Unmarshaller
	            Unmarshaller u = jc.createUnmarshaller();
	            
	            // unmarshal a po instance document into a tree of Java content
	            // objects composed of classes from the siima.machine package.
	            
	            //OPTION 1 genereted by machine.xsd
	            
	            //Machine mach = (Machine)u.unmarshal( new FileInputStream( "data/mach_part.xml" ) ); //machine.xsd
	            
	            /*OPTION 2 genereted by machine2.xsd  TOIMII with String content */
	            
	            JAXBElement jelem = (JAXBElement)u.unmarshal( new FileInputStream( "data/mach_part.xml" ) ); //"data/mach_part.xml" data/mach_part_elem.xml
	            MachineType mach = (MachineType)jelem.getValue(); 
	             
	            /*OPTION 3 genereted by machine2.xsd BUT jaxb classes modified and extended Extended (mach_part_elem.xml) (EI TOIMI)
	            
	            JAXBElement jelem = (JAXBElement)u.unmarshal( new FileInputStream( "data/mach_part_elem.xml" ) );
	            MachineType mach = (MachineType)jelem.getValue(); 
				*/
	            String ctrl=mach.getController();
	            int snum = mach.getSeries();
	            //Object part = mach.getPart(); 
	            String part= (String) mach.getPart();
	            
	            System.out.println("JaxbTool: machine contains: (" + ctrl + ":" + snum + ":" + part.toString() + ")" );
	            System.out.println("JaxbTool: part class name: (" + part.getClass().getName()+ ")" );
	                       
	            // create a Marshaller and marshal to a file
	            Marshaller m = jc.createMarshaller();
	            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
	           // m.marshal( jelem, System.out );
	            
	        } catch( JAXBException je ) {
	            je.printStackTrace();
	        } catch( IOException ioe ) {
	            ioe.printStackTrace();
	        }
	    }

}
