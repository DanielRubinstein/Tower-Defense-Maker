package frontEnd;

import backEnd.Bank.BankControllerReader;
import backEnd.GameData.State.*;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import backEnd.Mode.ModeReader;
import frontEnd.Facebook.FacebookInteractor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public interface ViewReader {

	// play, pause, fast forward
	public SimpleStringProperty getRunStatus();
	
	public SimpleBooleanProperty getBooleanAuthorModeProperty();
	
	public Stage getAppStage();
	
	public void reportError(Exception e);
	
	LevelProgressionControllerReader getLevelProgressionController();
	
	PlayerStatusReader getPlayerStatus();

	SimpleStringProperty getStringGameModeProperty();

	SimpleStringProperty getStringLevelModeProperty();

	FacebookInteractor getFb();
	
	public ModeReader getModeReader();
	
	BankControllerReader getBankControllerReader();
	
}
