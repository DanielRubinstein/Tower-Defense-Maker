package backEnd.State;
import com.sun.javafx.geom.Point2D;
import backEnd.Environment.Tile;
import backEnd.GameEngine.Attribute;
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
	}
	
	/**
	 * Get the tile at a given location
	 * @param location
	 * @return Tile at the given location
	 */
	public Tile getTileByLocation(Point2D location){
		return tileGrid[(int) location.y][(int) location.x];
	}
	
	/**
	 * Get the tile at a given location
	 * @param x the xCoord
	 * @param y the yCoord
	 * @return Tile at the given location
	 */
	public Tile getTileByCoord(int x, int y){
		return tileGrid[x][y];
	}
	
	/**
	 * Set the tile at a given location
	 * @param newTile
	 * @param location
	 */
	public void setTile(Tile newTile, Point2D location){
		tileGrid[(int) location.y][(int) location.x] = newTile;
	}
	
	/**
	 * 
	 * @return the number of Tiles in the TileGrid
	 */
	public int getMyWidth(){
		return gridWidth;
	}
	
	/**
	 * 
	 * @return the number of Tiles in the TileGrid
	 */
	public int getMyHeight(){
		return gridHeight;
	}
}