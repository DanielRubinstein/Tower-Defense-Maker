package backEnd.State;

import java.util.List;

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
	
	public TileGrid(int gridWidth, int gridHeight, int pointResolution_Width, int pointResolution_Height){
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
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
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public List<TileAttribute<?>> getTileAttributeAtPoint(Point2D point){
		Point2D tileLocation = getTileLocationFromPoint(point);
		return getTileByLocation(tileLocation).getTileAttributeList();
	}
	
	public Point2D getTileLocationFromPoint(Point2D point){
		double tileX = Math.ceil(point.getX()/gridWidth);
		double tileY = Math.ceil(point.getY()/gridHeight);
		return new Point2D(tileX,tileY);
		
	}

}
