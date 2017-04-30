package backEnd;

import java.util.List;

import backEnd.Bank.BankControllerReader;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
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
	

	BankControllerReader getBankControllerReader();
	
	SimpleStringProperty getEngineStatus();

	PlayerStatusReader getPlayerStatusReader();
	
	List<RuleReader> getRulesList();
	
	LevelProgressionControllerEditor getLevelProgressionController();

}
