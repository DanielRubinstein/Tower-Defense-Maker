package backEnd.GameData.State;


/**
 * This interface allows other classes to interact with the state 
 * @author Riley Nisbet
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
	
}