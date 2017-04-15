package backEnd.GameData.State;

import java.util.Observer;

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
	
	int getPointResolutionWidth();
	
	int getPointResolutionHeight();

	
}