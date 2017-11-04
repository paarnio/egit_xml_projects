/* AppMainController.java
 * 
 * 
 */
package siima.app;

import org.apache.log4j.Logger;

public class AppMainController {
	private static final Logger logger=Logger.getLogger(AppMainController.class.getName());
	private String studentDataExcel = "data/excel/students_U1.xlsx"; //students.xlsx
	private TaskCycleProcessor taskCycleProcessor;
	
	/*
	 * CONSTRUCTOR
	 * 
	 */
	public AppMainController(){
		
		taskCycleProcessor = new TaskCycleProcessor(this.studentDataExcel);
	}
	
	public void initApplication(){
		
		taskCycleProcessor.initProcessor();
	}
	
	public void invokeCheckingProcess(){
		
		taskCycleProcessor.runTaskCycles();
		String result = taskCycleProcessor.getChannelStringValue("merC001");
		System.out.println("CHECKING RESULT: " + result);
	}
	
	public static void main(String[] args) {
		
		AppMainController app = new AppMainController();
		app.initApplication();
		app.invokeCheckingProcess();
		
	}
	
}
