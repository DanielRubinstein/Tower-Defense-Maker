package backEnd.State;

import java.util.List;

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
	TileAttribute<?> getAttribute(TileAttributeType type);
	
	/**
	 * Add a TileAttribute to the Tile
	 * @param newAttr
	 */
	void addTileAttribute(TileAttribute<?> newAttr);
	
	/**
	 * @return List of TileAttributes
	 */
	List<TileAttribute<?>> getTileAttributeList();
	
	/**
	 * Replace the current TileAttribute list with newAttrList
	 * @param newAttrList
	 */
	void setTileAttributeList(List<TileAttribute<?>> newAttrList);
	
	boolean hasTileAttributeType(TileAttributeType type);
	
	void addAccessPermission(AccessParty newParty);
	
	boolean permitsAccess(AccessParty party);
	
}
