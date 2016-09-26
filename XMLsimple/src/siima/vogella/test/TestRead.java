/*
 * from: http://www.vogella.com/tutorials/JavaXML/article.html
 * 
 * 
 */

package siima.vogella.test;

import java.util.List;

import siima.vogella.stax.reader.Item;
import siima.vogella.stax.reader.StaXParser;

public class TestRead {
  public static void main(String args[]) {
    StaXParser read = new StaXParser();
    List<Item> readConfig = read.readConfig("./xml/stax/config.xml");
    for (Item item : readConfig) {
      System.out.println(item);
    }
  }
} 

