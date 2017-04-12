package backEnd;

import backEnd.Bank.BankController;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.GameProcessController;
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
	public ModeReader getModeReader();
	
	public BankController getBankController();
	
	public GameProcessController getGameProcessCotroller();
}

