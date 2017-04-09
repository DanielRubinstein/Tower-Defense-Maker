package frontEnd;

import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.TileGrid;
import backEnd.Mode.ModeReader;

public interface ViewReader {
	
	public ModeReader getMode();

	// play, pause, fast forward
	public String getRunStatus();
	
	public TileGrid getTileGrid();
	
	public ComponentGraph getComponentGraph();
	
}
