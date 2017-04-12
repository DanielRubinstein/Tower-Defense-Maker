package backEnd.GameData.State;

import java.util.Collection;
import java.util.List;

import javafx.geometry.Point2D;

public interface ComponentGraph {

	int getGridWidth();

	int getGridHeight();


	/**
	 * Returns list of all components on the grid
	 * @return list of components
	 */
	List<ComponentImpl> getComponentList();


	/**
	 * Get the list of components at a given location
	 * @param location
	 * @return Component at the given location
	 */
	List<ComponentImpl> getComponentsByLocation(Point2D location);

	/**
	 * Get the list of components at a given Tile location
	 * @param Tile location
	 * @return Component at the given location
	 */
	List<ComponentImpl> getComponentsByTileLocation(Point2D tileLocation);

	/**
	 * Gets the List of all Components in the ComponentGraph
	 * @return a List of Components
	 */
	List<ComponentImpl> getAllComponents();
	
	/**
	 * Add a component to the list of components at a given location
	 * @param newComponent
	 * @param location
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
	 * @param radius
	 * @return Unordered list of Components 
	 */
	List<ComponentImpl> getComponentsWithinRadius(ComponentImpl centerComp, float radius);

	/**
	 * Returns list of components that lie at the nearest location (although if two locations are equidistant from the component,
	 * it arbitrarily chooses one).
	 * @param centerComp
	 * @return List of components at the nearest location
	 */
	List<ComponentImpl> getNearestComponents(ComponentImpl centerComp);

}