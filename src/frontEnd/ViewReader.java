package frontEnd;

import java.util.Collection;

import backEnd.GameData.UserAttribute;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.Mode.ModeReader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

public interface ViewReader {

	// play, pause, fast forward
	public String getRunStatus();
	
	public TileGrid getTileGrid();
	
	public ComponentGraph getComponentGraph();

	public Collection<UserAttribute> getUserAttributes();
	
	public SimpleBooleanProperty getBooleanAuthorModeProperty();

	public Collection<Tile> getTilePresets();

	public Collection<ComponentImpl> getComponentPresets();
	
	public Stage getAppStage();
	
}
