package frontEnd;

import backEnd.Mode.ModeReader;

public interface ViewReader {

	public ModeReader getMode();

	// play, pause, fast forward
	public String getRunStatus();
	
}
