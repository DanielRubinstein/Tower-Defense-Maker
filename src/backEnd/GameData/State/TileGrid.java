package backEnd.GameData.State;

import backEnd.GameEngine.Attribute;
import javafx.geometry.Point2D;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class TileGrid {
	private int gridWidth;
	private int gridHeight;
	private Tile[][] tileGrid;
	
	public TileGrid(int width, int height){
		gridWidth = width;
		gridHeight = height;
		tileGrid = new Tile[width][height];
	}
	
	/**
	 * Get the tile at a given location
	 * @param location
	 * @return Tile at the given location
	 */
	public Tile getTileByLocation(Point2D location){
		return tileGrid[(int) location.getY()][(int) location.getX()];
	}
	
	/**
	 * Set the tile at a given location
	 * @param newTile
	 * @param location
	 */
	public void setTile(Tile newTile, Point2D location){
		tileGrid[(int) location.getY()][(int) location.getX()] = newTile;
	}

}