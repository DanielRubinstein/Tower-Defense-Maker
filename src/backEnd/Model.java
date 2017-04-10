package backEnd;

import backEnd.GameData.State.State;
import backEnd.Mode.ModeReader;

/**
 * Interface to define methods that the front end needs.
 * @author Tim
 *
 */
public interface Model {

	/**
	 * 
	 * @return current State
	 */
	public State getState();
	/**
	 * 
	 * @return mode of the user
	 */
	public ModeReader getMode();
	
}