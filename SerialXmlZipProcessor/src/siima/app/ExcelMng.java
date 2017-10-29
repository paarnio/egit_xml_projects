/* ExcelMng
 * FROM: CourseScheduler.java
 * 205-03-23 from HP
 * Configuration in excel courses_real.xlsx 
 */

package siima.app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import siima.utils.ExcelToStringArray;


public class ExcelMng {
	private ExcelToStringArray ex2s;
	private String studentDataExcel;
	private Map<String, String> configuremap = null;
	private Map<String, String> codeperiodmap = null;
	private List<String> firstperiodevents = null;
	private List<String> doubleperiodevents = null;
	//New
	public static String mainInfoSheet = "MainInfo";
	public String taskFlowXmlFile;
	public String zipFilesSheet;
	public String resultsSheet;
	
	public ExcelMng(String excelfile){
		this.studentDataExcel = excelfile;
		this.ex2s = new ExcelToStringArray(excelfile);
		this.firstperiodevents = new ArrayList<String>();
		this.doubleperiodevents = new ArrayList<String>();
	}
	
	public void readMainInfo(){
		String taskFlowXmlFile = null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(mainInfoSheet));
		int colIdx = 1;
		String searchKey = "TaskFlowXmlFile";
		int rowIdx = this.ex2s.searchString(searchKey, colIdx, 10, 50);
		if(rowIdx>=0){
		 this.taskFlowXmlFile = this.ex2s.getCellValue(colIdx+1, rowIdx);
		}
		
		searchKey = "ZipFilesSheet";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, 10, 50);
		if(rowIdx>=0){
		 this.zipFilesSheet = this.ex2s.getCellValue(colIdx+1, rowIdx);
		}
		
		searchKey = "ResultsSheet";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, 10, 50);
		if(rowIdx>=0){
		 this.resultsSheet = this.ex2s.getCellValue(colIdx+1, rowIdx);
		}
		
	}
	
	
	public List<String> readSubmitZipNames(String sheetname, int firstcolind,
			int lastcolind, int firstrowind, int lastrowind){
		List<String> zips=null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(sheetname));
		this.ex2s.setCellArea(firstcolind, lastcolind, firstrowind, lastrowind); //
		zips = this.ex2s.toStringList(false);
		
		return zips;
	}
	public void writeTestcaseResults(List<String> results, String sheetname, int colind, int rowind){
		/* Writing all the testcase results of one student submit 
		 * into the students row in excel file 
		 */
		int sheetidx = this.ex2s.getSheetIndex(sheetname);
		this.ex2s.setSheetind(sheetidx);
		this.ex2s.writeStringListToColumnOrRowField(true,sheetidx, colind, rowind, results, true);		
		
	}
	
	public void writeOperErrorMsgs(List<String> results, String sheetname, int colind, int rowind){
		/* Writing all the operation error messages of one student submit 
		 * into the students row in excel file 
		 */
		int sheetidx = this.ex2s.getSheetIndex(sheetname);
		this.ex2s.setSheetind(sheetidx);
		this.ex2s.writeStringListToColumnOrRowField(true,sheetidx, colind, rowind, results, true);				
	}
	
	public void saveAndCloseResultsExcel(){
		this.ex2s.saveWorkbook(studentDataExcel);
		
	}
	
	/*
	 * GETTERS SETTERS
	 */
	
	public String getTaskFlowXmlFile() {
		return taskFlowXmlFile;
	}
	
	public String getZipFilesSheet() {
		return zipFilesSheet;
	}

	public String getResultsSheet() {
		return resultsSheet;
	}

	public static void main(String[] args) {
		
		ExcelMng mng = new ExcelMng("data/excel/test.xlsx");
		/*String[] sched = mng.readPredefinedSchedulesFromExcel("season", 1, "Sheet1", 1, 6, 10, 19);
		for(int i=0; i<sched.length ; i++)
		System.out.println("OUT" + sched[i]);*/
		
		List<String> zips = mng.readSubmitZipNames("Sheet1", 4, 5, 10, 13);
		for(String zip : zips) System.out.println("OUT" + zip);
		
		mng = new ExcelMng("data/excel/students.xlsx");
		mng.readMainInfo();
		System.out.println("MAININFO TaskFlowXmlFile: " + mng.getTaskFlowXmlFile());
		
	}

	
	
}
