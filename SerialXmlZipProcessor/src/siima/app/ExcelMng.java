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
	
	public ExcelMng(){
		
		this.ex2s = new ExcelToStringArray("data/excel/test.xlsx");
		this.firstperiodevents = new ArrayList<String>();
		this.doubleperiodevents = new ArrayList<String>();
	}

	public String[] readPredefinedSchedulesFromExcel(String season, int runnumber, String sheetname, int firstcolind,
			int lastcolind, int firstrowind, int lastrowind) {
		String[] fixedscheds;
		String[] periods;
		List<String> predefinedslots = new ArrayList<String>();
		int readrows = lastrowind - firstrowind + 1;
		
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(sheetname)); // ("Configure"));
		this.ex2s.setCellArea(firstcolind, lastcolind, firstrowind, lastrowind); //
		fixedscheds = this.ex2s.toStringArray(); //NOTE: Lukee vain ekan sarakkeen
		
		/* --- Mapping property values --- */
		
		//this.ex2s.setCellArea(firstcolind + 1, lastcolind + 1, firstrowind,	lastrowind); //
		//periods = this.ex2s.toStringArray();
		
		for (int i = 0; i < readrows; i++) { 
			if ((!"null".equalsIgnoreCase(fixedscheds[i]))
					&& (!"null".equalsIgnoreCase(fixedscheds[i])) && fixedscheds[i]!=null && fixedscheds[i]!=null) {
				predefinedslots.add(fixedscheds[i]);
			}
		}	
		
		
		return predefinedslots.toArray(new String[predefinedslots.size()]);
	}
	
	public static void main(String[] args) {
		
		ExcelMng mng = new ExcelMng();
		String[] sched = mng.readPredefinedSchedulesFromExcel("season", 1, "Sheet1", 1, 6, 10, 19);
		for(int i=0; i<sched.length ; i++)
		System.out.println("OUT" + sched[i]);
	}
	
}
