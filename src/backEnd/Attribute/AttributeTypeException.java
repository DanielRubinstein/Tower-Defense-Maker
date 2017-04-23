package backEnd.Attribute;

public class AttributeTypeException extends RuntimeException{
	
	private static final String ERROR_MESSAGE = "Incorrect type T used to access Attribute";
	
	AttributeTypeException(){
		super(ERROR_MESSAGE);
	}

}
