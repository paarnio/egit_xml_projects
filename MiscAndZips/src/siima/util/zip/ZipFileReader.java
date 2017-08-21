/*
 * from:https://stackoverflow.com/questions/36548755/read-zip-file-content-without-extracting-in-java
 * 
 */
package siima.util.zip;

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

	public void readZipFile(ZipFile zip) {
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
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private StringBuilder getTxtFiles(InputStream in) {
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
		 try {
			ZipFile zip = new ZipFile("./data/zips/test.zip");
			
			ZipFileReader zipper = new ZipFileReader();
			zipper.readZipFile(zip);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
