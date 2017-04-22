package backEnd.GameData.State;

import java.util.List;
import java.util.Observer;

import backEnd.Attribute.AttributeOwnerReader;
import javafx.beans.Observable;
import javafx.geometry.Point2D;

/**
 * 
 * @author Riley Nisbet, Christian Martindale
 *
 */
public interface TileGrid{

	/**
	 * Get the tile at a given location
	 * @param column
	 * @param row TODO
	 * @return Tile at the given location
	 */
	Tile getTileByGridPosition(int column, int row);
	
	void setTileByScreenPosition(Tile newTile, Point2D position);

	/**
	 * Set the tile at a given location
	 * @param newTile
	 * @param location
	 */
	void setTileByGridPosition(Tile newTile, int column, int row);
	
	/**
	 * @return grid width
	 */
	int getNumColsInGrid();
	
	/**
	 * @return grid height
	 */
	int getNumRowsInGrid();
	
	/**
	 * return a List of all tiles in the Grid
	 */
	List<Tile> getAllTiles();

	Tile getTileByScreenLocation(Point2D location);

	void setTileSize(double tileWidth, double tileHeight);

	double getTileWidth();

	double getTileHeight();
	
	void addAsObserver(Observer o);

	boolean contains(AttributeOwnerReader newAttrOwn);

	Object getMap();

	void saveAndClearTileObservers();

	void setTileObservers();

	List<Observer> getObservers();

	void clearObservers();

	void setObservers(List<Observer> observersave);

	TileGridInstantiator getInstantiator();


}