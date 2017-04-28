package data;

import java.io.File;

import resources.constants.StringResourceBundle;

@SuppressWarnings("serial")
public class XMLReadingException extends Exception {
	
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	public XMLReadingException() {
		super(strResources.getFromErrorMessages("Select_Valid_Game"));
	}

	public XMLReadingException(File file) {
		super(String.format(strResources.getFromErrorMessages("Could_Not_Load_File"), file.getAbsolutePath()));
	}	
}
