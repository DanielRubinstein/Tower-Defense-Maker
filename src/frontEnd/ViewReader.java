package frontEnd;

import java.util.Collection;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

public interface ViewReader {

	// play, pause, fast forward
	public String getRunStatus();
	
	public SimpleBooleanProperty getBooleanAuthorModeProperty();

	public Collection<Tile> getTilePresets();

	public Collection<Component> getComponentPresets();
	
	public Stage getAppStage();
	
	public void reportError(Exception e);
	
}
