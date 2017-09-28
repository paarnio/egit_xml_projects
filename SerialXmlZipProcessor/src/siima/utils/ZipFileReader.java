/*
 * See project: MiscAndZips ZipFileReader.java
 * from:https://stackoverflow.com/questions/36548755/read-zip-file-content-without-extracting-in-java
 */

package siima.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;


public class ZipFileReader {

	public StringBuffer textcontents = new StringBuffer();

	public InputStream getInputStreamFromZipFile(String fullPathInZip, ZipFile zip) {
		/* Simple way to do it */		
		InputStream inputstream = null;
		try {
			inputstream = zip.getInputStream(zip.getEntry(fullPathInZip));			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return inputstream;
	}
	
	public InputStream readXmlStreamFromZipFile(String filename, String dir, ZipFile zip) {
		Boolean dirOK = false;
		try {
			/* TEST: this Simple way to do it
			String filepath = dir + filename;
			InputStream inputstream = zip.getInputStream(zip.getEntry(filepath));
			*/
			for (Enumeration e = zip.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();

				if (entry.isDirectory()) { // Directory search
					if (entry.getName().equals(dir)) {
						dirOK = true;
						System.out.println("--+ Directory found: " + entry.getName());
					} else {
						dirOK = false;
					}

				} else if ((!entry.isDirectory()) && (dirOK)) { // File search
																// from the
																// correct
																// Directory
					if (entry.getName().endsWith(filename)) {

						System.out.println("--+ FOUND FILE " + entry.getName());
						InputStream inputstream = zip.getInputStream(entry);
						return inputstream;
					}
				}
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public void readAndCheckZipFileContent(ZipFile zip) { // orig. readZipFile
		try {
			for (Enumeration e = zip.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				if (!entry.isDirectory()) {
					if (entry.getName().endsWith("png")) {

						byte[] image = getImage(zip.getInputStream(entry));
						System.out.println("PNG image " + entry.getName() + " byte length:\n" + image.length);
						// do your thing
					} else if (entry.getName().endsWith("txt")) {
						StringBuilder out = getTxtFiles(zip.getInputStream(entry));
						// do your thing
						textcontents.append(out.toString());
						System.out.println("TXT FILE " + entry.getName() +" CONTENTS:\n" + out);
					} else if (entry.getName().endsWith("xml")) {
						StringBuilder out = getTxtFiles(zip.getInputStream(entry));
						// do your thing
						textcontents.append(out.toString());
						System.out.println("XML FILE " + entry.getName() +" CONTENTS:\n" + out);
					} else if (entry.getName().endsWith("xsl")) {
						StringBuilder out = getTxtFiles(zip.getInputStream(entry));
						// do your thing
						textcontents.append(out.toString());
						System.out.println("XSL FILE " + entry.getName() +" CONTENTS:\n" + out);
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public StringBuilder getTxtFiles(InputStream in) {
		StringBuilder out = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
		} catch (IOException e) {
			// do something, probably not a text file
			e.printStackTrace();
		}
		return out;
	}

	private byte[] getImage(InputStream in) {
		try {
			BufferedImage image = ImageIO.read(in); // just checking if the
													// InputStream belongs in
													// fact to an image
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			// do something, it is not a image
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		
			/*ZipFile zip = new ZipFile("./data/zips/test.zip");			
			ZipFileReader zipper = new ZipFileReader();
			zipper.readAndCheckZipFileContent(zip);
			*/
			
			try {
			 
			 String zipFilePath = "./data/zips/RoundU1_sub2_123000.zip";
			 String directoryInZip = "RoundU1_sub2_123000/exU1E1_1/src/";
			 String fileInZip = "plant_catalog.xml";
			 String fullPathInZip = directoryInZip + fileInZip;
			 
			ZipFile zip = new ZipFile("./data/zips/RoundU1_sub2_123000.zip");			
			ZipFileReader zipper = new ZipFileReader();
			InputStream inputstream = zipper.getInputStreamFromZipFile(fullPathInZip, zip);
			StringBuilder strb = zipper.getTxtFiles(inputstream);
			System.out.println("FILE CONTENTS:\n" + strb.toString());
			} catch (IOException e) {
			e.printStackTrace();
			}
			

	}

}
