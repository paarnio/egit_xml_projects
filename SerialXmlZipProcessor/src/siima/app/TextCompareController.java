/*
 * 
 * /https://code.google.com/archive/p/google-diff-match-patch/wikis/LineOrWordDiffs.wiki
 */
package siima.app;

import java.util.LinkedList;

import siima.utils.diff_match_patch;
import siima.utils.diff_match_patch.Diff;
import siima.utils.diff_match_patch.Operation;

public class TextCompareController {

	private diff_match_patch dmp;
	private diff_match_patch.Operation DELETE = diff_match_patch.Operation.DELETE;
	private diff_match_patch.Operation EQUAL = diff_match_patch.Operation.EQUAL;
	private diff_match_patch.Operation INSERT = diff_match_patch.Operation.INSERT;

	  protected void setUp() {
	    // Create an instance of the diff_match_patch object.
	    dmp = new diff_match_patch();
	  }

	  public boolean runDiffMain(String textlines1, String textlines2) {		  
		  /* 
		   * textlines1: student text
		   * textlines2: reference text
		   * 
		   * The data structure representing a diff is a Linked list of Diff objects:
		   * {Diff(Operation.DELETE, "Hello"), Diff(Operation.INSERT, "Goodbye"),
		   *  Diff(Operation.EQUAL, " world.")}
		   * which means: delete "Hello", add "Goodbye" and keep " world."
		   */
		  boolean isequal = true;
		  System.out.println("\n---TextCompareController: runDiffMain()");
		  LinkedList<Diff> diffslist = dmp.diff_main(textlines1, textlines2);
		  dmp.diff_cleanupSemantic(diffslist);
		  
		  for (Diff diff : diffslist){
			  Operation op = diff.operation;
			  String txt = diff.text;
			  if(!"EQUAL".equals(op.name())){
				  isequal=false;
				  System.out.println("runDiffMain(): op: " + op.name() + " diff.text: " + txt);
			  }
		  }
		  return isequal;
	  }
	  
	  
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
