package backEnd.GameData.State;

import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;

public interface ComponentGraph {


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
	 * @param newComponent
	 * @param location
	 * 
	 */
		
	void addComponentToGrid(Component newComponent, Point2D location);

	/**
	 * Remove a component from the grid
	 * @param toRemove
	 */
	void removeComponent(Component toRemove);

	/**
	 * Returns unordered list of components that lie within a certain radius from a central Component
	 * @param centerComp
	 * @param d
	 * @return Unordered list of Components 
	 */
	List<Component> getComponentsWithinRadius(Component centerComp, double d);

	/**
	 * Returns list of components that lie at the nearest location (although if two locations are equidistant from the component,
	 * it arbitrarily chooses one).
	 * @param centerComp
	 * @return List of components at the nearest location
	 */
	List<Component> getNearestComponents(Component centerComp);
	
	void addAsObserver(Observer o);

	boolean contains(AttributeOwnerReader c);

}