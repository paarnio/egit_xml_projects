package siima.app;

import java.math.BigInteger;
import java.util.List;

import siima.model.checker.taskflow.CheckerTaskFlowType;
import siima.model.checker.taskflow.OperationType;
import siima.model.checker.taskflow.TestCaseType;

public class TaskCycleProcessor {

	private ExcelMng excel_mng = new ExcelMng("data/excel/students.xlsx");
	private TestCaseContainer test_cc = new TestCaseContainer();
	private TransformController trans_ctrl = new TransformController();
	
	public TaskCycleProcessor(String studentsExcel){
		excel_mng = new ExcelMng("data/excel/students.xlsx");
	}
	
	public void runTaskCycles(){
		String taskFlowXmlFile = "data/taskflow/taskflow1.xml";
		List<TestCaseType> testcases = readTestCases(taskFlowXmlFile);
		
		List<String> zips = readSubmitZipNames();
		/* --- Task Loop --- */
		for(TestCaseType tcase : testcases){
			
			System.out.println("--- Task Loop --- ");
			
			/* --- Submit Loop --- */	
			for(String zip : zips){
				
				System.out.println("--- Submit Loop --- ");
			}
			
		}
		
		
	}
	
	public List<String> readSubmitZipNames(){
		String sheetname = "Sheet1";
		List<String> zips;
		ExcelMng mng = getExcel_mng();
		//zips = mng.readPredefinedSchedulesFromExcel("NA", 1, sheetname, 4, 5, 10, 13);
		zips = mng.readSubmitZipNames(sheetname, 4, 5, 10, 13);
		return zips;
	}
	
	public List<TestCaseType> readTestCases(String taskFlowXmlFile){
		List<TestCaseType> cases = null;
		// Testcase Info by unmarshalling taskflow1.xml
		TestCaseContainer tcc = getTest_cc();
		CheckerTaskFlowType taskflow = tcc.loadCheckerTaskFlowModel(taskFlowXmlFile);
		cases = taskflow.getTestCase();
		return cases;
	}
	
	public static void main(String[] args) {
		String studentsExcel = "data/excel/students.xlsx";
		String taskFlowXmlFile = "data/taskflow/taskflow1.xml";
		String resultFilePath1 = "./data/zips/result1.xml";
		
		String sheetname = "Sheet1";
		TaskCycleProcessor cycle_pros = new TaskCycleProcessor(studentsExcel);
		
		//Submit zip file names from excel
		ExcelMng mng = cycle_pros.getExcel_mng();
		/*String[] zips = mng.readPredefinedSchedulesFromExcel("NA", 1, sheetname, 4, 5, 10, 13);
		for(int i=0; i<zips.length ; i++)
		System.out.println("STUDENT SUBMITS: " + zips[i]);
		*/
		List<String> zips = cycle_pros.readSubmitZipNames();
		for ( String zip : zips) System.out.println("STUDENT SUBMIT: " + zip);
		
		// Testcase Info by unmarshalling taskflow1.xml
		TestCaseContainer tcc = cycle_pros.getTest_cc();
		CheckerTaskFlowType taskflow = tcc.loadCheckerTaskFlowModel(taskFlowXmlFile);
		List<TestCaseType> cases = taskflow.getTestCase();
		BigInteger points = cases.get(0).getPoints();
		System.out.println("TEST CASE POINTS: " + points);
		String rdir= cases.get(0).getRefDir();
		String rfile1= cases.get(0).getRefFile1();
		String rfile2= cases.get(0).getRefFile2();
		String sdir= cases.get(0).getStuDir();
		String sfile1= cases.get(0).getStuFile1();
		String sfile2= cases.get(0).getStuFile2();
		OperationType oper = cases.get(0).getOperation();
		String name =oper.getName();
		String par1 =oper.getPar1();
		String par2 =oper.getPar2();
		System.out.println("TEST CASE OPERATION: " + name);		
		//If operation name is 'XSLTransform' 
		String fullXSLPathInZip = sdir + sfile1;
		String fullXMLPathInZip = sdir + sfile2;
		System.out.println("STUDENT XSLFILE: " + fullXSLPathInZip);
		//Prepare and run transform
		TransformController ctrl = cycle_pros.getTrans_ctrl();
		String zippath = "data/zips/Round1/" + zips.get(0);
		ctrl.prepareXSLTransformWithImputStreams(zippath, fullXSLPathInZip, zippath, fullXMLPathInZip);		
		ctrl.runTransform(resultFilePath1,  null,null);
		System.out.println("Option 1: resultfile: " + resultFilePath1);
		
		//Run Cycles
		cycle_pros.runTaskCycles();
		 
	}

	public ExcelMng getExcel_mng() {
		return excel_mng;
	}

	public TestCaseContainer getTest_cc() {
		return test_cc;
	}

	public TransformController getTrans_ctrl() {
		return trans_ctrl;
	}

	
}
