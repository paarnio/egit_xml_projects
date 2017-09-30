/* SerialController.java
 * 
 * 
 * Note: Streams are normally read only one time.
 * But they can be saved in a byte array to avoid reading 
 * again from the same file
 * See: https://stackoverflow.com/questions/9501237/read-stream-twice
 * 
 */
package siima.app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipFile;

import siima.utils.ZipFileReader;

public class SerialController {
	
	private ZipFileReader zipper = new ZipFileReader();
	private SerialXSLTransformer xslTransformer;
	private boolean prepared = false;
	
	/* Constructor */
	public SerialController(){
		xslTransformer = new SerialXSLTransformer();
	}

	/***
	 * 
	 * @param resultFilePath
	 * @param params
	 * @param values
	 * @return
	 */
	public boolean runTransform(String resultFilePath,  List<String> params, List<String> values ) {
		boolean ok = false;
		OutputStream outputstream;
		try {
			outputstream = new FileOutputStream(resultFilePath);
			invokeXSLTransform(outputstream, params, values); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return ok;
	}
	/**
	 * 
	 * @param zipFilePath
	 * @param directoryInZip
	 * @param fileInZip
	 * @param XSL_or_XML
	 * @return
	 */
	public boolean loadAndSaveInputStream(String zipFilePath, String directoryInZip, String fileInZip,
			String XSL_or_XML) {
		/*
		 * Param XSL_or_XML allowed values: "XSL" OR "XML"
		 * 
		 */
		boolean ok = false;
		String fullXSLPathInZip = directoryInZip + fileInZip;

		try {
			if (("XSL".equalsIgnoreCase(XSL_or_XML)) || ("XML".equalsIgnoreCase(XSL_or_XML))) {
				ZipFile zip = new ZipFile(zipFilePath);
				InputStream inputstream = zipper.readInputStreamFromZipFile(fullXSLPathInZip, zip,
						XSL_or_XML.toLowerCase());// "xsl");
				if (inputstream == null) {
					// Let's try to find the file from every directory in the
					// zip
					inputstream = zipper.readInputStreamFromZipFile(fileInZip, null, zip);
				}
				if (inputstream != null) {
					// xslTransformer.saveXslInputStreamAsByteArray(xslInputstream);
					ok = xslTransformer.saveInputStreamAsByteArray(inputstream, XSL_or_XML.toUpperCase()); // "XSL");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	
	
	/***
	 * 
	 * @param xslZipFilePath
	 * @param fullXSLPathInZip
	 * @param xmlZipFilePath
	 * @param fullXMLPathInZip
	 * @return
	 */
	public boolean prepareXSLTransformWithImputStreams(String xslZipFilePath,  String fullXSLPathInZip, String xmlZipFilePath, String fullXMLPathInZip) {
		/* IF zipfile and file path in zip is defined, 
		 * Stream (XSL and XML) is loaded from Zip file. 
		 * Else IF filepath == null, it's inputStream is loaded 
		 * from previously saved BAInputStream 
		 * */
		boolean ok = false;
		InputStream xslInputstream;
		InputStream xmlInputstream;
		
		try {
			
			// XSL
			if ((xslZipFilePath != null) && (fullXSLPathInZip != null)) {
				ZipFile xslzip = new ZipFile(xslZipFilePath);
				xslInputstream = zipper.readInputStreamFromZipFile(fullXSLPathInZip, xslzip, "xsl");
				if (xslInputstream == null) { // Let's try to find the file from
												// every directory in zip
					xslInputstream = zipper.readInputStreamFromZipFile(fullXSLPathInZip, null, xslzip);
				}
			} else {				
				xslInputstream = xslTransformer.getSavedBAInputStream("XSL");
			}
			
			// XML
			if ((xmlZipFilePath != null) && (fullXMLPathInZip != null)) {
				ZipFile xmlzip = new ZipFile(xmlZipFilePath);
				xmlInputstream = zipper.readInputStreamFromZipFile(fullXMLPathInZip, xmlzip, "xml");
				if (xmlInputstream == null) { // Let's try to find the file from
												// every directory in xmlzip
					xmlInputstream = zipper.readInputStreamFromZipFile(fullXMLPathInZip, null, xmlzip);
				}
			} else {				
				xmlInputstream = xslTransformer.getSavedBAInputStream("XML");
			}
			if ((xslInputstream != null) && (xmlInputstream != null)) {
				ok = prepareXSLTransform(xslInputstream, xmlInputstream);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	/***
	 * 
	 * @param outputstream
	 * @param params
	 * @param values
	 * @return
	 */
	public boolean invokeXSLTransform(OutputStream outputstream, List<String> params, List<String> values) {
		boolean ok = false;
		if(prepared){ 
			ok = xslTransformer.invokeXSLTransform(outputstream, params, values);
		}
		return ok;
	}
	
	/***
	 * 
	 * @param xslinput
	 * @param xmlinput
	 * @return
	 */
	private boolean prepareXSLTransform(InputStream xslinput, InputStream xmlinput){
		boolean ok = false;
		ok = xslTransformer.createNewTemplate(xslinput, null);
		ok = xslTransformer.setTransformerXMLSource(xmlinput, null);
		if(ok) prepared = true;
		
		return ok;
	}
	
	
	
	
	public static void main(String[] args) {
			/* try {
			 
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
			*/
			 String zipFilePath = "./data/zips/RoundU1_sub2_123000.zip";
			 String directoryInZip = "RoundU1_sub2_123000/exU1E1_1/src/";
			 String xslfileInZip = "plant_catalog.xsl";
			 String xmlfileInZip = "plant_catalog.xml";
			 String fullXMLPathInZip = directoryInZip + xmlfileInZip;
			 String fullXSLPathInZip = directoryInZip + xslfileInZip;
			 
			 String resultFilePath1 = "./data/zips/result1.xml";
			 String resultFilePath2 = "./data/zips/result2.xml";
			 
			 
			 SerialController ctrl = new SerialController();
			 /* OPT1: Loading XML & XSL from Zip every time */
			 //ctrl.loadStreamsFromZipAndPrepare(zipFilePath, directoryInZip, xslfileInZip, xmlfileInZip);
			 ctrl.prepareXSLTransformWithImputStreams(zipFilePath, fullXSLPathInZip, zipFilePath, fullXMLPathInZip);
			 ctrl.runTransform(resultFilePath1,  null,null);
			 System.out.println("Option 1: resultfile: " + resultFilePath1);
			 
			 /* OPT2: Using the same presaved XSL and Loading XML from Zip every time */
			 ctrl.loadAndSaveInputStream(zipFilePath, directoryInZip, xslfileInZip, "XSL");
			 //ctrl.loadXmlStreamFromZipUseStoredXslAndPrepare(zipFilePath, directoryInZip, xmlfileInZip);
			 ctrl.prepareXSLTransformWithImputStreams(null, null, zipFilePath, fullXMLPathInZip);
			 
			 ctrl.runTransform(resultFilePath2,  null,null);
			 System.out.println("Option 2: resultfile: " + resultFilePath2);
			 
			 String resultFilePath22 = "./data/zips/result22.xml";
			 //ctrl.loadXmlStreamFromZipUseStoredXslAndPrepare(zipFilePath, directoryInZip, xmlfileInZip);
			 ctrl.prepareXSLTransformWithImputStreams(null, null, zipFilePath, fullXMLPathInZip);
			 ctrl.runTransform(resultFilePath22,  null,null);
			 
			 String resultFilePath23 = "./data/zips/result23.xml";
			 //ctrl.loadXmlStreamFromZipUseStoredXslAndPrepare(zipFilePath, directoryInZip, xmlfileInZip);
			 ctrl.prepareXSLTransformWithImputStreams(null, null, zipFilePath, fullXMLPathInZip);
			 ctrl.runTransform(resultFilePath23,  null,null);
			 
			 String resultFilePath24 = "./data/zips/result24.xml";
			 //ctrl.loadXmlStreamFromZipUseStoredXslAndPrepare(zipFilePath, directoryInZip, xmlfileInZip);
			 ctrl.prepareXSLTransformWithImputStreams(null, null, zipFilePath, fullXMLPathInZip);
			 ctrl.runTransform(resultFilePath24,  null,null);

	}
	/*
	 * REPLACED OLD METHODS
	 */
	public boolean wwwwloadAndSaveXslStream(String zipFilePath, String directoryInZip, String xslfileInZip) {
		/* REPLACED by loadAndSaveInputStream() */
		boolean ok = false;
		String fullXSLPathInZip = directoryInZip + xslfileInZip;
		try {
			ZipFile zip = new ZipFile(zipFilePath);
			InputStream xslInputstream = zipper.readInputStreamFromZipFile(fullXSLPathInZip, zip, "xsl");
			if (xslInputstream == null) { 
				// Let's try to find the file from every directory in the zip
				xslInputstream = zipper.readInputStreamFromZipFile(xslfileInZip, null, zip);
			}
			if (xslInputstream != null) {
				//xslTransformer.saveXslInputStreamAsByteArray(xslInputstream);
				ok = xslTransformer.saveInputStreamAsByteArray(xslInputstream, "XSL");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	
	
	public boolean wwwwloadXmlStreamFromZipUseStoredXslAndPrepare(String zipFilePath,  String directoryInZip,  String xmlfileInZip ) {
		/* NOTE: replaced by prepareXSLTransformWithImputStreams()
		 * XSL Stream is read from stored ByteArray Stream
		 * ONLY XML Stream is loaded from Zip file */
		boolean ok = false;
		 //String fullXSLPathInZip = directoryInZip + xslfileInZip;
		 String fullXMLPathInZip = directoryInZip + xmlfileInZip;
		 try {
			ZipFile zip = new ZipFile(zipFilePath);
			/*InputStream xslInputstream = zipper.getInputStreamFromZipFile(fullXSLPathInZip, zip);
			if(xslInputstream==null){ //Let's try to find the file from every directory in zip
				xslInputstream = zipper.readXmlStreamFromZipFile(xslfileInZip, null, zip);
			}*/
			//XSL
			InputStream xslInputstream = xslTransformer.getSavedBAInputStream("XSL");
			//XML
			InputStream xmlInputstream = zipper.readInputStreamFromZipFile(fullXMLPathInZip, zip, "xml");
			if(xmlInputstream==null){ //Let's try to find the file from every directory in zip
				xmlInputstream = zipper.readInputStreamFromZipFile(xmlfileInZip, null, zip);
			}
			if((xslInputstream!=null)&&(xmlInputstream!=null)){
			ok = prepareXSLTransform(xslInputstream, xmlInputstream);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return ok;
	}

	
	
	public boolean wwwwloadStreamsFromZipAndPrepare(String zipFilePath,  String directoryInZip,  String xslfileInZip, String xmlfileInZip ) {
		/* NOTE: replaced by prepareXSLTransformWithImputStreams()
		 * Both XSL and XML Streams are loaded from Zip files 
		 **/
		boolean ok = false;
		 String fullXSLPathInZip = directoryInZip + xslfileInZip;
		 String fullXMLPathInZip = directoryInZip + xmlfileInZip;
		 try {
			ZipFile zip = new ZipFile(zipFilePath);
			//XSL
			InputStream xslInputstream = zipper.readInputStreamFromZipFile(fullXSLPathInZip, zip, "xsl");
			if(xslInputstream==null){ //Let's try to find the file from every directory in zip
				xslInputstream = zipper.readInputStreamFromZipFile(xslfileInZip, null, zip);
			}
			//XML
			InputStream xmlInputstream = zipper.readInputStreamFromZipFile(fullXMLPathInZip, zip, "xml");
			if(xmlInputstream==null){ //Let's try to find the file from every directory in zip
				xmlInputstream = zipper.readInputStreamFromZipFile(xmlfileInZip, null, zip);
			}
			if((xslInputstream!=null)&&(xmlInputstream!=null)){
			ok = prepareXSLTransform(xslInputstream, xmlInputstream);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return ok;
	}
}
