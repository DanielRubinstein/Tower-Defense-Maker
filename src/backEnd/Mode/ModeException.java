package backEnd.Mode;

import frontEnd.CustomJavafxNodes.ErrorDialog;
import resources.constants.StringResourceBundle;

@SuppressWarnings("serial")
public class ModeException{
	
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	public ModeException(Mode mode, String message){
		ErrorDialog err = new ErrorDialog();
		err.create(strResources.getFromErrorMessages("Mode_Error_Header"), 
			String.format(strResources.getFromErrorMessages("Mode_Exception"), mode.getUserMode().toLowerCase() , message));
	}

}
