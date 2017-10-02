package siima.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import siima.model.checker.taskflow.CheckerTaskFlowType;
import siima.model.checker.taskflow.TestCaseType;


public class TestCaseContainer {

	private CheckerTaskFlowType taskflow;
	
	
	public CheckerTaskFlowType getTaskflow() {
		return taskflow;
	}

	public CheckerTaskFlowType loadCheckerTaskFlowModel(String taskFlowXmlFile) {
		CheckerTaskFlowType tflow=null;
		try {
			// create a JAXBContext capable of handling classes generated into
			// the primer.po package
			JAXBContext jc = JAXBContext.newInstance("siima.model.checker.taskflow");
			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();
			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			JAXBElement poe = (JAXBElement) u.unmarshal(new FileInputStream(taskFlowXmlFile)); //"data/taskflow/taskflow1.xml"));
			this.taskflow = (CheckerTaskFlowType) poe.getValue();
			tflow = this.taskflow;
		} catch (JAXBException je) {
			je.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return tflow;
	}

	public static void main(String[] args) {
		String taskFlowXmlFile = "data/taskflow/taskflow1.xml";
		
		TestCaseContainer tcc = new TestCaseContainer();
		CheckerTaskFlowType taskflow = tcc.loadCheckerTaskFlowModel(taskFlowXmlFile);
		
		String szip = taskflow.getStuZip();
		System.out.println("TestCaseContainer StuZip: " + szip);
		
		List<TestCaseType> cases = taskflow.getTestCase();
		System.out.println("POINTS: " + cases.get(0).getPoints());

	}

}
