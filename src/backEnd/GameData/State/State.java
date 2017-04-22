package backEnd.GameData.State;

import java.util.Collection;
import java.util.Map;
import java.util.Observer;

import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
import javafx.geometry.Point2D;

/**
 * This interface allows other classes to interact with the state 
 * @author Riley Nisbet, Christian Martindale
 *
 */

public interface State {
	
	/**
	 * @return the grid of Tiles
	 */
	TileGrid getTileGrid();
	
	/**
	 * @return the graph of Components
	 */
	ComponentGraph getComponentGraph();

	
	void addAsObserver(Observer o);
	
	void updateState(State state);
	
	int getGridWidth();
	
	int getGridHeight();

	Collection<Component> getComponentsByTileGridPosition(Point2D value);

	Map<String, SpawnQueue> getSpawnQueues();
}