package frontEnd;

import backEnd.Bank.BankControllerReader;
import backEnd.GameData.State.*;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import backEnd.Mode.ModeReader;
import frontEnd.Facebook.FacebookInteractor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

/**
 * This interface defines the View, determines how Front End should be controlled and how the Front End
 * and Model interact. This interface has less permissions and is at the highest level of the View hierarchy.
 * @author Tim, Miguel
 *
 */
public interface ViewReader {

	// play, pause, fast forward
	public SimpleStringProperty getRunStatus();
	
	public SimpleBooleanProperty getBooleanAuthorModeProperty();
	
	public Stage getAppStage();
	
	public void reportError(Exception e);
	
	public LevelProgressionControllerReader getLevelProgressionController();
	
	public PlayerStatusReader getPlayerStatus();

	public SimpleStringProperty getStringGameModeProperty();

	public SimpleStringProperty getStringLevelModeProperty();

	public FacebookInteractor getFb();
	
	public ModeReader getModeReader();
	
	public BankControllerReader getBankControllerReader();
	
}
