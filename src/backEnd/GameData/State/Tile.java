package backEnd.GameData.State;

import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.AttributeData;
import javafx.geometry.Point2D;


/**
 * A tile is a position in the grid that can have properties (TileAttributes). GameEngine will have to access the TileAttributes
 * to check how the components can interact with it. Mode will have to be able to access its TileAttributes.
 * @author Riley Nisbet
 */

public interface Tile extends AttributeOwner {

	
	/**
	 * Replace the current AttributeData with newAttrData
	 * @param newAttrList
	 */
	void setAttributeData(AttributeData newAttrData);
	
	
	
	/**
	 * @return Tile location
	 */
	Point2D getLocation();

	
}
