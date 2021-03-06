package backEnd.GameData.State;

import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeOwner;


/**
 * A tile is a position in the grid that can have properties (TileAttributes). GameEngine will have to access the TileAttributes
 * to check how the components can interact with it. Mode will have to be able to access its TileAttributes.
 * @author Riley Nisbet
 */

public interface Tile extends AttributeOwner, SerializableObservable {

	
	/**
	 * Replace the current AttributeData with newAttrData
	 * @param newAttrList
	 */
	void setAttributeData(AttributeData newAttrData);
	
	/**
	 * 
	 * @param name the name of the desired Attribute
	 * @return true if the Tile has the given Attribute, false otherwise
	 */
	boolean hasAttribute(String name);
	
}
