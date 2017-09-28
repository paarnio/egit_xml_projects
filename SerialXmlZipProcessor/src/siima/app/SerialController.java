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
	
	public SerialController(){
		xslTransformer = new SerialXSLTransformer();
	}

	public boolean runTransform(String resultFilePath,  List<String> params, List<String> values ) {
		//TODO: params delivery
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
	
	public boolean loadStreamsFromZipAndPrepare(String zipFilePath,  String directoryInZip,  String xslfileInZip, String xmlfileInZip ) {
		boolean ok = false;
		 String fullXSLPathInZip = directoryInZip + xslfileInZip;
		 String fullXMLPathInZip = directoryInZip + xmlfileInZip;
		 try {
			ZipFile zip = new ZipFile(zipFilePath);
			InputStream xslInputstream = zipper.getInputStreamFromZipFile(fullXSLPathInZip, zip);
			InputStream xmlInputstream = zipper.getInputStreamFromZipFile(fullXMLPathInZip, zip);
			
			ok = prepareXSLTransform(xslInputstream, xmlInputstream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return ok;
	}
	
	
	public boolean invokeXSLTransform(OutputStream outputstream, List<String> params, List<String> values) {
		boolean ok = false;
		if(prepared){ 
			ok = xslTransformer.invokeXSLTransform(outputstream, params, values);
		}
		return ok;
	}
	
	private boolean prepareXSLTransform(InputStream xslinput, InputStream xmlinput){
		boolean ok = false;
		ok = xslTransformer.createNewTemplate(xslinput, null);
		ok = xslTransformer.setXMLSource(xmlinput, null);
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
			 
			 String resultFilePath = "./data/zips/result.xml";
			 
			 SerialController ctrl = new SerialController();
			 ctrl.loadStreamsFromZipAndPrepare(zipFilePath, directoryInZip, xslfileInZip, xmlfileInZip);
			 ctrl.runTransform(resultFilePath,  null,null);
					

	}
}
