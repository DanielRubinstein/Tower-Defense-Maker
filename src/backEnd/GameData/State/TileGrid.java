package backEnd.GameData.State;

import javafx.geometry.Point2D;

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

}