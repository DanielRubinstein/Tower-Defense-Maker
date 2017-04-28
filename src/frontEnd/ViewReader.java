package frontEnd;

import java.util.Collection;

import backEnd.GameData.State.*;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import frontEnd.Facebook.FacebookInteractor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public interface ViewReader {

	// play, pause, fast forward
	public SimpleStringProperty getRunStatus();
	
	public SimpleBooleanProperty getBooleanAuthorModeProperty();

	public Collection<Tile> getTilePresets();

	public Collection<ComponentImpl> getComponentPresets();
	
	public Stage getAppStage();
	
	public void reportError(Exception e);
	
	LevelProgressionControllerReader getLevelProgressionController();
	
	PlayerStatusReader getPlayerStatus();

	SimpleStringProperty getStringGameModeProperty();

	SimpleStringProperty getStringLevelModeProperty();

	FacebookInteractor getFb();
	
}
