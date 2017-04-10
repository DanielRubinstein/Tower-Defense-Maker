package frontEnd;

import java.util.Collection;

import backEnd.GameData.UserAttribute;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.TileGrid;
import backEnd.Mode.ModeReader;
import javafx.beans.property.SimpleBooleanProperty;

public interface ViewReader {
	
	public ModeReader getMode();

	// play, pause, fast forward
	public String getRunStatus();
	
	public TileGrid getTileGrid();
	
	public ComponentGraph getComponentGraph();

	public Collection<UserAttribute> getUserAttributes();
	
	public SimpleBooleanProperty getAuth();
	
}
