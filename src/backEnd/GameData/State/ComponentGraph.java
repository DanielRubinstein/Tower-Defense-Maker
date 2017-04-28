package backEnd.GameData.State;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;

public interface ComponentGraph extends SerializableObservable{


	/**
	 * Get the list of components at a given location
	 * @param location
	 * @return Component at the given location
	 */
	List<ComponentImpl> getComponentsByScreenPosition(Point2D location);

	Map<Point2D, List<ComponentImpl>> getComponentMap();
	/**
	 * Get the list of components at a given Tile location
	 * @param Tile location
	 * @return Component at the given location
	 */

	List<ComponentImpl> getComponentsByTileCorners(TileCorners tileCorners);

	/**
	 * Gets the List of all Components in the ComponentGraph
	 * @return a List of Components
	 */

	Collection<ComponentImpl> getAllComponents();
	
	/**
	 * Add a component to the list of components at a given location
	 * @param newComponent
	 * @param location
	 * 
	 */
		
	void addComponentToGrid(ComponentImpl newComponent, Point2D location);

	/**
	 * Remove a component from the grid
	 * @param toRemove
	 */
	void removeComponent(ComponentImpl toRemove);

	/**
	 * Returns unordered list of components that lie within a certain radius from a central Component
	 * @param centerComp
	 * @param d
	 * @return Unordered list of Components 
	 */
	List<ComponentImpl> getComponentsWithinRadius(ComponentImpl centerComp, double d);

	/**
	 * Returns list of components that lie at the nearest location (although if two locations are equidistant from the component,
	 * it arbitrarily chooses one).
	 * @param centerComp
	 * @return List of components at the nearest location
	 */
	List<ComponentImpl> getNearestComponents(ComponentImpl centerComp);

	boolean contains(AttributeOwnerReader c);

	void saveAndClearObservers();

	void clearComponents();

	void setComponentObservers();

}