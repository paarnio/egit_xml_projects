package siima.models.jaxb.caex;

/* --- Note package
 * This needs to be in jaxb model package, because 
 * newbasic.additionalInformation is a protected property
 * containing no setter method.
 */
 
public class TEMP_Helpper {
	public static CAEXBasicObject insertCopyContent(CAEXBasicObject original){	
		CAEXBasicObject newbasic = new CAEXBasicObject();
		newbasic.additionalInformation = original.getAdditionalInformation();		
		return newbasic;
	}
}
