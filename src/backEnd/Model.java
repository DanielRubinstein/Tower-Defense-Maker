package backEnd;

import java.util.function.Consumer;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.Mode.Mode;
import data.DataController;

/**
 * Interface to define methods that the front end needs.
 * @author Tim
 *
 */
public interface Model extends ModelReader, ModelSecurity{
	
	/**
	 * 
	 * @return the GameProcessController that owns the Engines
	 */
	GameProcessController getGameProcessController();
	
	PlayerStatusModifier getModifiablePlayerStatus();

	GameData getGameData();

	DataController getDataController();

	Mode getMode();

	Consumer<Object> getGameLoader();

	BankController getBankController();

}
