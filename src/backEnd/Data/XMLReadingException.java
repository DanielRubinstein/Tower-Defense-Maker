package backEnd.Data;

import java.io.File;

@SuppressWarnings("serial")
public class XMLReadingException extends RuntimeException {
	public XMLReadingException() {
		super("Please Select A Valid Game File (Extension: .xml)");
	}

	public XMLReadingException(File file) {
		super(String.format("Could not load selected file (%s).\n\nPlease select a valid game file (Extension: .xml)", file.getAbsolutePath()));
	}	
}
