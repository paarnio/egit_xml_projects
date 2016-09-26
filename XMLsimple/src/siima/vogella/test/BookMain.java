/* JAXB tutorial
 * from:http://www.vogella.com/tutorials/JAXB/article.html#jaxb
 * "If you run the BookMain an XML file will be created from the input objects. 
 * Afterwards the file is read again and the objects are re-created based on the XML file." 
 * 
 * @XmlRootElement(namespace = "namespace") Define the root element for an XML tree.
 * @XmlType(propOrder = { "field2", "field1",.. }) 	Allows to define the order 
 * in which the fields are written in the XML file.
 * @XmlElement(name = "neuName") 	Define the XML element which will be used. 
 * Only need to be used if the neuNeu is different then the JavaBeans Name. 
 * 
 */

package siima.vogella.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import siima.vogella.jaxb.Book;
import siima.vogella.jaxb.Bookstore;

public class BookMain {

  private static final String BOOKSTORE_XML = "./xml/jaxb/bookstore-jaxb.xml";

  public static void main(String[] args) throws JAXBException, IOException {

    ArrayList<Book> bookList = new ArrayList<Book>();

    // create books
    Book book1 = new Book();
    book1.setIsbn("978-0060554736");
    book1.setName("The Game");
    book1.setAuthor("Neil Strauss");
    book1.setPublisher("Harpercollins");
    bookList.add(book1);

    Book book2 = new Book();
    book2.setIsbn("978-3832180577");
    book2.setName("Feuchtgebiete");
    book2.setAuthor("Charlotte Roche");
    book2.setPublisher("Dumont Buchverlag");
    bookList.add(book2);

    // create bookstore, assigning book
    Bookstore bookstore = new Bookstore();
    bookstore.setName("Fraport Bookstore");
    bookstore.setLocation("Frankfurt Airport");
    bookstore.setBookList(bookList);

    // create JAXB context and instantiate marshaller
    JAXBContext context = JAXBContext.newInstance(Bookstore.class);
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    // Write to System.out
    m.marshal(bookstore, System.out);

    // Write to File
    m.marshal(bookstore, new File(BOOKSTORE_XML));

    // get variables from our xml file, created before
    System.out.println();
    System.out.println("Output from our XML File: ");
    Unmarshaller um = context.createUnmarshaller();
    Bookstore bookstore2 = (Bookstore) um.unmarshal(new FileReader(BOOKSTORE_XML));
    ArrayList<Book> list = bookstore2.getBooksList();
    for (Book book : list) {
      System.out.println("Book: " + book.getName() + " from "
          + book.getAuthor());
    }
  }
} 