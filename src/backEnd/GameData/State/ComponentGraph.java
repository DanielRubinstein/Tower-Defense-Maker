package backEnd.GameData.State;

import java.util.Collection;
import java.util.List;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;

public interface ComponentGraph extends SerializableObservableGen<Component>, Comparable{


	/**
	 * Get the list of components at a given location
	 * @param location
	 * @return Component at the given location
	 */
	List<Component> getComponentsByScreenPosition(Point2D location);

	/**
	 * Get the list of components at a given Tile location
	 * @param Tile location
	 * @return Component at the given location
	 */

	List<Component> getComponentsByTileCorners(TileCorners tileCorners);

	/**
	 * Gets the List of all Components in the ComponentGraph
	 * @return a List of Components
	 */
	List<Component> getAllComponents();

	
	/**
	 * Add a component to the list of components at a given location
	 * @param component
	 * @param location
	 * 
	 */	
	void addComponentToGrid(Component component, Point2D location);

	/**
	 * Remove a component from the grid
	 * @param component
	 */
	void removeComponent(Component component);

	/**
	 * Returns unordered list of components that lie within a certain radius from a central Component
	 * @param centerComp
	 * @param d
	 * @return Unordered list of Components 
	 */
	List<Component> getComponentsWithinRadius(Component centerComp, double d);

	boolean contains(AttributeOwnerReader c);

	/**
	 * Necessary for serialization to XML.
	 * saves out Observers and then clears them off Components to allow for 
	 * XStream to function correctly. 
	 */
	void saveAndClearObservers();

	/**
	 * deletes all components from the ComponentGraph
	 */
	void clearComponents();

	/**
	 * Sets Observers on Components for use in frontend-backend communication
	 */
	void setComponentObservers();
	
	Collection<AttributeOwner> getAllAttributeOwners();

}