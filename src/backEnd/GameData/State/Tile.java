package backEnd.GameData.State;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.AttributeData;
import javafx.geometry.Point2D;


/**
 * A tile is a position in the grid that can have properties (TileAttributes). GameEngine will have to access the TileAttributes
 * to check how the components can interact with it. Mode will have to be able to access its TileAttributes.
 * @author Riley Nisbet
 */

public interface Tile {
	
	/**
	 * @param attrType
	 * @return Attribute that corresponds to the String attrType
	 */
	Attribute<?> getAttribute(String attrType);
	
	/**
	 * Add a Attributes to the Tile of type attrType. String attrType has to be a Key from the tilDefaults properties file
	 * @param attrType
	 * @param newAttr
	 */
	void addAttribute(String attrType, Attribute<?> newAttr);
	
	/**
	 * @return List of Attributes
	 */
	AttributeData getMyAttributes();
	
	/**
	 * Replace the current AttributeData with newAttrData
	 * @param newAttrList
	 */
	void setAttributeData(AttributeData newAttrData);
	
	/**
	 * @return AccessPermissions object for this Tile
	 */
	AccessPermissions getAccessPermissions();
	
	/**
	 * @return Tile location
	 */
	Point2D getLocation();
	/**
	 * 
	 * @param name the name of the desired Attribute
	 * @return true if the Tile has the given Attribute, false otherwise
	 */
	boolean hasAttribute(String name);
	
}
