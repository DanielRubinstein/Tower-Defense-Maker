package backEnd.State;

import backEnd.State.ComponentGraph;
import backEnd.GameEngine.Attribute;

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
	
	/**
	 * Add a ComponentName->FilePath pairing in the ImageMap
	 * @param componentName
	 * @param filePath
	 */
	void addToImageMap(Attribute<String> componentName, String filePath);
	
	/**
	 * Get the File Path that corresponds to the given Component Name
	 * @param componentName
	 * @return file path
	 */
	String getFilePathFromMap(Attribute<String> componentName);
	
}