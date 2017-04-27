package backEnd.GameData.State;

import java.util.Collection;
import java.util.Map;

import backEnd.GameEngine.EngineStatus;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
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

	void addAsObserver(SerializableObserver o);
	
	void updateState(State state);
	
	int getGridWidth();
	
	int getGridHeight();

	Collection<Component> getComponentsByTileGridPosition(Point2D value);

	Map<String, SpawnQueues> getSpawnQueues();

	boolean gameIsRunning();

	void setComponentGraph(ComponentGraph componentGraph);

	EngineStatus getEngineStatus();
}