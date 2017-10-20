package siima.app;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
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
	
	/* Directory and File references 
	String rdir;
	String rfile1;
	String rfile2;
	String sdir;
	String sfile1;
	String sfile2;
	*/
	
	private List<String> dirList = new ArrayList<String>();
	private List<String> fileList = new ArrayList<String>();
	private Map<String,Integer> dirKeyIndexMap = new HashMap<String,Integer>();
	private Map<String,Integer> fileKeyIndexMap = new HashMap<String,Integer>();
	
	/* Channels 
	String studentChannel001 = "";
	String studentChannel002 = "";
	String referenceChannel001 = "";
	String referenceChannel002 = "";
	String mergeChannel001 = "";
	String mergeChannel002 = "";
	*/
	
	private List<String> channelList = new ArrayList<String>();
	private Map<String,Integer> channelKeyIndexMap = new HashMap<String,Integer>();
	
	/* Constructor */
	public TaskCycleProcessor(String studentsExcel){
		excel_mng = new ExcelMng("data/excel/students.xlsx");
		
		dirKeyIndexMap.put("stuDir1", 0); 
		dirKeyIndexMap.put("stuDir2", 1);
		dirKeyIndexMap.put("refDir1", 2); 
		dirKeyIndexMap.put("refDir2", 3);
		
		fileKeyIndexMap.put("stuFile1", 0);
		fileKeyIndexMap.put("stuFile2", 1);
		fileKeyIndexMap.put("refFile1", 2);
		fileKeyIndexMap.put("refFile2", 3);
		
		channelKeyIndexMap.put("stuC001", 0); //studentChannel
		channelKeyIndexMap.put("stuC002", 1);
		channelKeyIndexMap.put("refC001", 2); //referenceChannel
		channelKeyIndexMap.put("refC002", 3);
		channelKeyIndexMap.put("merC001", 4); //mergeChannel
		channelKeyIndexMap.put("merC002", 5);
		
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
	}
	
	public String getChannelStringValue(String channelKey){
		/* 
		 **/
		String value = null;
		Integer channelIdx = channelKeyIndexMap.get(channelKey);
		value = channelList.get(channelIdx.intValue());
		return value;	
	}
	
	public void setChannelStringValue(String channelKey, String value){
		/* 
		 **/
		Integer channelIdx = channelKeyIndexMap.get(channelKey);
		channelList.set(channelIdx.intValue(), value);
			
	}
	
	public String operParamFilePathValue(String parameter){
		/* Getting Operation parameter's target value
		 * now only filepath build
		 * e.g. parameter= 'stuDir1/stuFile1'
		 **/
		String value = null;
							
		String[] ppath = parameter.split("/");
		if(ppath.length>1){ // filepath?
			String dirKey = ppath[0];
			String fileKey = ppath[1];
			System.out.println("          Operation parameter 1 keys: " + dirKey + "/" + fileKey);					
			Integer dirIdx = dirKeyIndexMap.get(dirKey);
			Integer fileIdx = fileKeyIndexMap.get(fileKey);
			System.out.println("          Operation parameter 1 Indexes: " + dirIdx + "/" + fileIdx);	
			
			if(dirList.get(dirIdx.intValue()).endsWith("/")){
				 value = dirList.get(dirIdx.intValue()) + fileList.get(fileIdx.intValue());
				} else {
					 value = dirList.get(dirIdx.intValue()) + "/" + fileList.get(fileIdx.intValue());					
				}
			
		} else {// Channel?		
		}
		
		
		return value;
	}
	
	
	public void runTaskCycles() {
		/*
		 *  TODO: read reference files from a reference ZIP
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
			
			String sdir1= tcase.getStuDir1();
			String sdir2= tcase.getStuDir2();
			String sfile1= tcase.getStuFile1();
			String sfile2= tcase.getStuFile2();
			
			String rdir1= tcase.getRefDir1();
			String rdir2= tcase.getRefDir2();
			String rfile1= tcase.getRefFile1();
			String rfile2= tcase.getRefFile2();
			 
			
			 dirList.add(sdir1);
			 dirList.add(sdir2); //for stuDir2
			 dirList.add(rdir1);
			 dirList.add(rdir2); //for refDir2
			 
			 fileList.add(sfile1);
			 fileList.add(sfile2);
			 fileList.add(rfile1);
			 fileList.add(rfile2);
			 
			/*Channels
			 studentChannel001 = "";
			 studentChannel002 = "";
			 referenceChannel001 = "";
			 referenceChannel002 = "";
			 mergeChannel001 = "";
			 mergeChannel002 = "";
			*/
			 
			/* impossible
			Map<String,String> channelMap = new HashMap<String,String>();
			channelMap.put("stuC001", studentChannel001);
			channelMap.put("stuC002", studentChannel002);
			channelMap.put("refC001", referenceChannel001);
			channelMap.put("refC002", referenceChannel002);
			channelMap.put("merC001", mergeChannel001);
			channelMap.put("merC002", mergeChannel002);
			*/

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
								String fullXSLPathInZip = operParamFilePathValue(par1);
								String fullXMLPathInZip = operParamFilePathValue(par2);
								
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
								/*
								switch (returnChannel) {
								case "stuC001":
									studentChannel001 = retStr; //String.valueOf(retStr.toCharArray());
									break;
								case "stuC002":
									studentChannel002 = retStr;
									break;
								}
								*/
								setChannelStringValue(returnChannel, retStr);
								
								//System.out.println("????????MAP TEST stuC001: " + channelMap.get("stuC001"));
								
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
								//String fullXSLPathInZip = rdir + rfile1;
								//String fullXMLPathInZip = rdir + rfile2;
								
								String fullXSLPathInZip = operParamFilePathValue(par1);
								String fullXMLPathInZip = operParamFilePathValue(par2);
									
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
								/*
								switch (returnChannel) {
								case "refC001":
									referenceChannel001 = retStr;
									break;
								case "refC002":
									referenceChannel002 = retStr;
									break;
								} */
								setChannelStringValue(returnChannel, retStr);
								
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
							/*	
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
							*/
								arg1str = getChannelStringValue(par1);
								//arg1str = channelMap.get(par1);
								//System.out.println("==??????" + channelMap.get(par1) + "?????");
								/*
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
								*/
								arg2str = getChannelStringValue(par2);
								
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
								/*
								switch (returnChannel) {
								case "merC001":
									mergeChannel001 = retStr;
									break;
								case "merC002":
									mergeChannel002 = retStr;
									break;
								} */
								setChannelStringValue(returnChannel, retStr);
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
		String rdir1= cases.get(0).getRefDir1();
		String rfile1= cases.get(0).getRefFile1();
		String rfile2= cases.get(0).getRefFile2();
		String sdir1= cases.get(0).getStuDir1();
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
		String fullXSLPathInZip = sdir1 + sfile1;
		String fullXMLPathInZip = sdir1 + sfile2;
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
