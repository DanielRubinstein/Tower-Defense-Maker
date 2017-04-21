package backEnd;

import backEnd.Bank.BankController;
import backEnd.GameData.PlayerStatus.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.Mode.ModeReader;
import javafx.beans.property.SimpleStringProperty;

public interface ModelReader {
	/**
	 * 
	 * @return current State
	 */
	State getState();
	
	/**
	 * 
	 * @return mode of the user
	 */
	ModeReader getModeReader();
	

	BankController getBankController();
	
	SimpleStringProperty getEngineStatus();
	
	PlayerStatusReader getPlayerStatusReader();
}
