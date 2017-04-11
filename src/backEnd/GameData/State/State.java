package backEnd.GameData.State;


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

	/**
	 * returns the shortest path in the Grid
	 * between a start and end point using BFS
	 */
	void calculateShortestPath();

	
}