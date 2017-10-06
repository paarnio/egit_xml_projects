/*
 * based on: diff_match_patch_test.java
 * loaded: C:\programs\java\google_code\diff_match_patch_20121119\diff_match_patch_20121119\java
 * from:
 * https://code.google.com/archive/p/google-diff-match-patch/wikis/LineOrWordDiffs.wiki
 */
package siima.utils;

public class Testing_diff_match_patch {
	
	private diff_match_patch dmp;
	  private diff_match_patch.Operation DELETE = diff_match_patch.Operation.DELETE;
	  private diff_match_patch.Operation EQUAL = diff_match_patch.Operation.EQUAL;
	  private diff_match_patch.Operation INSERT = diff_match_patch.Operation.INSERT;

	  protected void setUp() {
	    // Create an instance of the diff_match_patch object.
	    dmp = new diff_match_patch();
	  }

	  //  DIFF TEST FUNCTIONS


	  public void testDiffCommonPrefix() {
	    // Detect any common prefix.
	    System.out.println("diff_commonPrefix: Null case., 0," + dmp.diff_commonPrefix("abc", "xyz"));

	    System.out.println("diff_commonPrefix: Non-null case., 4," + dmp.diff_commonPrefix("1234abcdef", "1234xyz"));

	    System.out.println("diff_commonPrefix: Whole case., 4," + dmp.diff_commonPrefix("1234", "1234xyz"));
	  }
	  
	  
	  
	public static void main(String[] args) {
		Testing_diff_match_patch dmp = new Testing_diff_match_patch();
		dmp.setUp();
		dmp.testDiffCommonPrefix();

	}

}
