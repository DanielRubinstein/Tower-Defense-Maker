package backEnd.Attribute;

import resources.constants.StringResourceBundle;

/**
 * @author Derek
 *
 */
public class AttributeTypeException extends RuntimeException{
	
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	AttributeTypeException(){
		super(strResources.getFromErrorMessages("Incorrect_Type_T"));
	}

}
