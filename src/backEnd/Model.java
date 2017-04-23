package backEnd;

import backEnd.Bank.BankController;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.Mode.ModeReader;

/**
 * Interface to define methods that the front end needs.
 * @author Tim
 *
 */
public interface Model extends ModelReader{
	
	/**
	 * 
	 * @return the GameProcessController that owns the Engines
	 */
	GameProcessController getGameProcessController();
	
	PlayerStatusModifier getModifiablePlayerStatus();
	
	
	

}
