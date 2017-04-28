package backEnd.Mode;

import resources.constants.StringResourceBundle;

@SuppressWarnings("serial")
public class ModeException extends RuntimeException {
	
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	public ModeException(Mode mode, String message){
		super(String.format(strResources.getFromErrorMessages("Mode_Exception"), mode.getUserMode().toLowerCase() , message));
	}

}
