package backEnd.Data;

import java.io.File;

@SuppressWarnings("serial")
public class GameFileException extends Exception {
	public GameFileException() {
		super("Please Select A Valid Game File (Extension: .xml)");
	}

	public GameFileException(File file) {
		super(String.format("Could not load selected file (%s).\n\nPlease select a valid game file (Extension: .xml)", file.getAbsolutePath()));
	}	
}
