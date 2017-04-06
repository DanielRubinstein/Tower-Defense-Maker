package backEnd.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;

/**
 * This is the Grid class that contains the Component Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class ComponentGraph {
	private int gridWidth;
	private int gridHeight;
	private Map<Point2D,List<Component>> componentGraph;
	
	public ComponentGraph(int width, int height){
		gridWidth = width;
		gridHeight = height;
	}
	
	/**
	 * Get the list of components at a given location
	 * @param location
	 * @return Tile at the given location
	 */
	public List<Component> getComponentsByLocation(Point2D location){
		return componentGraph.get(location);
	}
	
	/**
	 * Add a component to the list of components at a given location
	 * @param newComponent
	 * @param location
	 */
	public void addComponentToGrid(Component newComponent, Point2D location){
		List<Component> currList = componentGraph.get(location);
		currList.add(newComponent);
		componentGraph.put(location, currList);
	}
	
	/**
	 * Remove a component from the grid
	 * @param toRemove
	 */
	public void removeComponent(Component toRemove){
		Attribute<?> posAttribute= toRemove.getAttribute("Position");
		Point2D location = (Point2D) posAttribute.getValue();
		List<Component> currList = componentGraph.get(location);
		currList.remove(toRemove);
		componentGraph.put(location, currList);
	}
	
	/**
	 * Returns unordered list of components that lie within a certain radius from a central Component
	 * @param centerComp
	 * @param radius
	 * @return Unordered list of Components 
	 */
	public List<Component> getComponentsWithinRadius(Component centerComp, float radius){
		Point2D centerLoc = (Point2D) centerComp.getAttribute("Position").getValue();
		ArrayList<Component> componentsWithinRadius = new ArrayList<Component>();
		for (Point2D loc : componentGraph.keySet()){
			double distance = Math.sqrt(Math.pow(centerLoc.x - loc.x, 2) + Math.pow(centerLoc.y - loc.y, 2));
			if (distance < radius){
				componentsWithinRadius.addAll(componentGraph.get(loc));
			}
		}
	}

}
