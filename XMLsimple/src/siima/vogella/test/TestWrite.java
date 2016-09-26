/*
 * from:http://www.vogella.com/tutorials/JavaXML/article.html
 * 
 * Writing:
 * 
 * <?xml version="1.0"?>
 * <config>
	<mode>1</mode>
	<unit>901</unit>
	<current>0</current>
	<interactive>0</interactive>
   </config>
 * 
 * NOTE:  For another (more complex) example of using Stax, 
 * please see Reading and creating RSS feeds via Java (with Stax):
 * http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 */

package siima.vogella.test;

import siima.vogella.stax.writer.StaxWriter;

public class TestWrite {

	  public static void main(String[] args) {
	    StaxWriter configFile = new StaxWriter();
	    configFile.setFile("./xml/stax/config2.xml");
	    try {
	      configFile.saveConfig();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	} 