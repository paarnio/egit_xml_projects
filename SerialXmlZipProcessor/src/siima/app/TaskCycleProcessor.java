package siima.app;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import siima.model.checker.taskflow.CheckerTaskFlowType;
import siima.model.checker.taskflow.FlowType;
import siima.model.checker.taskflow.OperationType;
import siima.model.checker.taskflow.TestCaseType;

public class TaskCycleProcessor {

	private ExcelMng excel_mng = new ExcelMng("data/excel/students.xlsx");
	private TestCaseContainer test_cc = new TestCaseContainer();
	private TransformController trans_ctrl = new TransformController();
	
	public TaskCycleProcessor(String studentsExcel){
		excel_mng = new ExcelMng("data/excel/students.xlsx");
	}
	
	public void runTaskCycles() {
		/*
		 *  
		 */
		String resultFileDir = "data/zips";
		
		String taskFlowXmlFile = "data/taskflow/taskflow2.xml";
		List<String> zips = readSubmitZipNames();
		List<TestCaseType> testcases = readTestCases(taskFlowXmlFile);
		System.out.println("\n********** runTaskCycles() ********\n");
		
		int submitcnt =0;
		/* --- Student Submit Loop --- */
		for (String zip : zips) {
			submitcnt++;
			System.out.println("+ Submit Loop #" + submitcnt);
			System.out.println("  Submit zip: " + zip);
			
		
		/* --- TestCase Loop --- */
		for (TestCaseType tcase : testcases) {
			System.out.println("--+ TestCase Loop --- ");
			String rdir= tcase.getRefDir();
			String rfile1= tcase.getRefFile1();
			String rfile2= tcase.getRefFile2();
			String sdir= tcase.getStuDir();
			String sfile1= tcase.getStuFile1();
			String sfile2= tcase.getStuFile2();
			
			//Channels
			String studentChannel001 = null;
			String studentChannel002 = null;
			String referenceChannel001 = null;
			String referenceChannel002 = null;
			String mergeChannel001 = null;
			String mergeChannel002 = null;
			
			Map<String,String> channelMap = new HashMap<String,String>();
			channelMap.put("stuC001", studentChannel001);
			channelMap.put("stuC002", studentChannel002);
			channelMap.put("refC001", referenceChannel001);
			channelMap.put("refC002", referenceChannel002);
			channelMap.put("merC001", mergeChannel001);
			channelMap.put("merC002", mergeChannel002);
			

			List<FlowType> flows = tcase.getFlow();
			for (FlowType flow : flows) {
				System.out.println("--+--+ Flow Loop --- ");
				System.out.println("       Flow type: " + flow.getType());
				System.out.println("       Flow name: " + flow.getName());
				
				List<OperationType> operations = flow.getOperation();
				for (OperationType oper : operations) {
					System.out.println("--+--+--+ Operation Loop --- ");
					System.out.println("          Operation type: " + oper.getType());
					System.out.println("          Operation name: " + oper.getName());
					System.out.println("          Operation return channel: " + oper.getReturn());
					
					String par1 =oper.getPar1();
					String par2 =oper.getPar2();
					String returnChannel = oper.getReturn();

						/* --- Flow Branch --- */						
						String flowType = flow.getType();	
						if("studentFlow".equals(flowType)){
						
						/* --- Operation Branch --- */
							String operationType = oper.getType();
							switch (operationType) {
							case "XSLTransform": {
								System.out.println("................ XSLTransform ");
								//Student files
								String fullXSLPathInZip = sdir + sfile1;
								String fullXMLPathInZip = sdir + sfile2;
									
								System.out.println("                 XSL file: " + fullXSLPathInZip);
								System.out.println("                 XML file: " + fullXMLPathInZip);
								
								String zippath = "data/zips/RoundU1/" + zip;
								String[] splits = zip.split(".zip");
								String resultFilePath = resultFileDir + "/" + returnChannel + "_student_" + splits[0] + ".xml";
								trans_ctrl.prepareXSLTransformWithImputStreams(zippath, fullXSLPathInZip, zippath, fullXMLPathInZip);		
								//OPTION File
								//trans_ctrl.runTransformToFile(resultFilePath,  null,null);
								//System.out.println("                 resultfile: " + resultFilePath);
								
								//OPTION String
								ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
								String retStr = trans_ctrl.runTransformToString(resultOutputStream,  null,null);

								switch (returnChannel) {
								case "stuC001":
									studentChannel001 = retStr;
									break;
								case "stuC002":
									studentChannel002 = retStr;
									break;
								}
								
							}
								break;
							case "XSDValidation": {
								System.out.println("................ XSDValidation ");
							}
								break;
							}
		
						} else if("referenceFlow".equals(flowType)){
							
							/* --- Operation Branch --- */
							String operationType = oper.getType();
							switch (operationType) {
							case "XSLTransform": {
								System.out.println("................ XSLTransform ");
								//Reference files
								String fullXSLPathInZip = rdir + rfile1;
								String fullXMLPathInZip = rdir + rfile2;
									
								System.out.println("                 XSL file: " + fullXSLPathInZip);
								System.out.println("                 XML file: " + fullXMLPathInZip);
								
								String zippath = "data/zips/RoundU1/" + zip;
								String[] splits = zip.split(".zip");
								String resultFilePath = resultFileDir + "/" + returnChannel + "_reference_" + splits[0] + ".xml";
								trans_ctrl.prepareXSLTransformWithImputStreams(zippath, fullXSLPathInZip, zippath, fullXMLPathInZip);		
								//OPTION File
								//trans_ctrl.runTransformToFile(resultFilePath,  null,null);
								//System.out.println("                 resultfile: " + resultFilePath);
								
								//OPTION String
								ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
								String retStr = trans_ctrl.runTransformToString(resultOutputStream,  null,null);
								
								switch (returnChannel) {
								case "refC001":
									referenceChannel001 = retStr;
									break;
								case "refC002":
									referenceChannel002 = retStr;
									break;
								}
								
							}
								break;
							case "XSDValidation": {
								System.out.println("................ XSDValidation ");
							}
								break;
							}

							
						} else if("mergeFlow".equals(flowType)){
							//TODO: par1 & par2 Change switch case -> Map
							System.out.println("\n==================================");
							System.out.println(".............mergeFlow ...........");
							//System.out.println("==================================\n");
							/* --- Operation Branch --- */
							String operationType = oper.getType();
							
							switch (operationType) {
							case "StringCompare": {
								System.out.println("................ StringCompare ");
								String arg1str = null;
								String arg2str = null;
							/*	*/
								switch (par1) {
								case "refC001":
									arg1str = referenceChannel001;
									break;
								case "refC002":
									arg1str = referenceChannel002;
									break;
								case "stuC001":
									arg1str = studentChannel001;
									break;
								case "stuC002":
									arg1str = studentChannel002;
									break;
								}
							
								//arg1str = channelMap.get(par1);
								System.out.println("==??????" + channelMap.get(par1) + "?????");
								
								switch (par2) {
								case "refC001":
									arg2str = referenceChannel001;
									break;
								case "refC002":
									arg2str = referenceChannel002;
									break;
								case "stuC001":
									arg2str = studentChannel001;
									break;
								case "stuC002":
									arg2str = studentChannel002;
									break;
								}
								
								//if (referenceChannel001.equals(studentChannel001))
								String retStr = null;
								if (arg1str.equals(arg2str)){
									retStr = "EQUAL";
									//System.out.println("=============== EQUAL =============\n");
								} else {
									retStr = "NOT_EQUAL";
									//System.out.println("?????? NOT EQUAL ??????\n");
								}
								
								System.out.println("===============" + retStr + "=============\n");
								
								switch (returnChannel) {
								case "merC001":
									mergeChannel001 = retStr;
									break;
								case "merC002":
									mergeChannel002 = retStr;
									break;
								}
								
							}
								break;
							case "NA": {
								System.out.println("................NA??? ");
							}
								break;
							}
							
						}
						
					
					}
				}
			}// TestCase Loop --- 
		}//Student zip loop
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
		String taskFlowXmlFile = "data/taskflow/taskflow2.xml";
		String resultFilePath1 = "./data/zips/result_test1.xml";
		
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
		Integer points = cases.get(0).getPoints();
		System.out.println("TEST CASE POINTS: " + points);
		String rdir= cases.get(0).getRefDir();
		String rfile1= cases.get(0).getRefFile1();
		String rfile2= cases.get(0).getRefFile2();
		String sdir= cases.get(0).getStuDir();
		String sfile1= cases.get(0).getStuFile1();
		String sfile2= cases.get(0).getStuFile2();
		List<FlowType> flows = cases.get(0).getFlow();
		List<OperationType> operations = flows.get(0).getOperation();
		OperationType oper = operations.get(0);
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
		String zippath = "data/zips/RoundU1/" + zips.get(0);
		ctrl.prepareXSLTransformWithImputStreams(zippath, fullXSLPathInZip, zippath, fullXMLPathInZip);		
		ctrl.runTransformToFile(resultFilePath1,  null,null);
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
