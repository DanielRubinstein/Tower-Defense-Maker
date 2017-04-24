package backEnd;

import java.util.List;

import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerReader;
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
	
	LevelProgressionControllerEditor getLevelProgressionController();
	
	List<RuleReader> getRulesList();

}
