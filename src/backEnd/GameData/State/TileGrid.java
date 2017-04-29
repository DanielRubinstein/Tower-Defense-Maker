package backEnd.GameData.State;

import java.util.Collection;
import java.util.List;

import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;

/**
 * 
 * @author Riley Nisbet, Christian Martindale
 *
 */
public interface TileGrid extends SerializableObservable{
	
	void setTileByScreenPosition(Tile newTile, Point2D position);

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
	Collection<Tile> getAllTiles();

	Tile getTileByScreenPosition(Point2D location);

	void setTileSize(double tileWidth, double tileHeight);

	double getTileWidth();

	double getTileHeight();

	boolean contains(AttributeOwnerReader newAttrOwn);

	Object getMap();
	
	void saveAndClearTileObservers();

	void setTileObservers();
	
	TileGridInstantiator getInstantiator();

	void setNumCols(int numColsInGrid);

	void setNumRows(int numRowsInGrid);

	boolean atMiddleXOfTile(Point2D currentLocation);

	boolean atMiddleYOfTile(Point2D currentLocation);

}