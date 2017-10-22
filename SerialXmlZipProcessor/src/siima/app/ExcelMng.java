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
	private Map<String, String> configuremap = null;
	private Map<String, String> codeperiodmap = null;
	private List<String> firstperiodevents = null;
	private List<String> doubleperiodevents = null;
	
	public ExcelMng(String excelfile){
		
		this.ex2s = new ExcelToStringArray(excelfile);
		this.firstperiodevents = new ArrayList<String>();
		this.doubleperiodevents = new ArrayList<String>();
	}

	public List<String> readSubmitZipNames(String sheetname, int firstcolind,
			int lastcolind, int firstrowind, int lastrowind){
		List<String> zips=null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(sheetname));
		this.ex2s.setCellArea(firstcolind, lastcolind, firstrowind, lastrowind); //
		zips = this.ex2s.toStringList(false);
		
		return zips;
	}
	public void writeCheckResults(List<String> results, String sheetname, int colind, int rowind){
		int sheetidx = this.ex2s.getSheetIndex(sheetname);
		this.ex2s.setSheetind(sheetidx);
		this.ex2s.writeStringListToColumnField(sheetidx, colind, rowind, results, true);
		
		
	}
	
	public static void main(String[] args) {
		
		ExcelMng mng = new ExcelMng("data/excel/test.xlsx");
		/*String[] sched = mng.readPredefinedSchedulesFromExcel("season", 1, "Sheet1", 1, 6, 10, 19);
		for(int i=0; i<sched.length ; i++)
		System.out.println("OUT" + sched[i]);*/
		
		List<String> zips = mng.readSubmitZipNames("Sheet1", 4, 5, 10, 13);
		for(String zip : zips) System.out.println("OUT" + zip);
		
	}
	
}
