package backEnd.GameData.State;

import java.util.List;

import javafx.geometry.Point2D;

/**
 * 
 * @author Riley Nisbet, Christian Martindale
 *
 */
public interface TileGrid {

	/**
	 * Get the tile at a given location
	 * @param location
	 * @return Tile at the given location
	 */
	Tile getTileByLocation(Point2D location);

	/**
	 * Set the tile at a given location
	 * @param newTile
	 * @param location
	 */
	void setTile(Tile newTile, Point2D location);
	
	/**
	 * Get Tile by x and y coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	Tile getTileByCoord(int x, int y);
	
	/**
	 * @return grid width
	 */
	int getMyWidth();
	
	/**
	 * @return grid height
	 */
	int getMyHeight();
	
	/**
	 * return a List of all tiles in the Grid
	 */
	List<Tile> getAllTiles();


}