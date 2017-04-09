package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import javafx.geometry.Point2D;

/**
 * This is the Grid class that contains the Component Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class ComponentGraph {
	private int gridWidth;
	private int gridHeight;
	private int pointResWidth;
	private int pointResHeight;
	private Map<Point2D,List<Component>> componentMap;
	
	public ComponentGraph(int width, int height, int pointResWidth, int pointResHeight){
		this.gridWidth = width;
		this.gridHeight = height;
		this.pointResWidth = pointResWidth;
		this.pointResHeight = pointResHeight;
	}
	
	public int getGridWidth(){
		return gridWidth;
	}
	
	public int getGridHeight(){
		return gridHeight;
	}
	
	/**
	 * Returns list of all components on the grid
	 * @return list of components
	 */
	public List<Component> getComponentList(){
		List<Component> componentList = new ArrayList<Component>();
		for (List<Component> n : componentMap.values()){
			componentList.addAll(n);
		}
		return componentList;
	}
	
	/**
	 * Get the list of components at a given location
	 * @param location
	 * @return Component at the given location
	 */
	public List<Component> getComponentsByLocation(Point2D location){
		return componentMap.get(location);
	}
	
	/**
	 * Get the list of components at a given Tile location
	 * @param Tile location
	 * @return Component at the given location
	 */
	public List<Component> getComponentsByTileLocation(Point2D tileLocation){
		double leftXPoint = Math.floor(tileLocation.getX()/gridWidth * pointResWidth);
		double rightXPoint = Math.floor(tileLocation.getX()/gridWidth * pointResWidth) + gridWidth/pointResWidth;
		double topYPoint = Math.floor(tileLocation.getY()/gridHeight * pointResHeight);
		double botYPoint = Math.floor(tileLocation.getY()/gridHeight * pointResHeight) + gridHeight/pointResHeight;
		List<Component> componentsAtLocation = new ArrayList<Component>();
		for (Point2D n : componentMap.keySet()){
			if (n.getX() >= leftXPoint & n.getX() <= rightXPoint & n.getY() > topYPoint & n.getY() < botYPoint){
				componentsAtLocation.addAll(componentMap.get(n));
			}
		}
		return componentsAtLocation;
	}
	
	/**
	 * Add a component to the list of components at a given location
	 * @param newComponent
	 * @param location
	 */
	public void addComponentToGrid(Component newComponent, Point2D location){
		List<Component> currList = componentMap.get(location);
		currList.add(newComponent);
		componentMap.put(location, currList);
	}
	
	/**
	 * Remove a component from the grid
	 * @param toRemove
	 */
	public void removeComponent(Component toRemove){
		Attribute<?> posAttribute= toRemove.getAttribute("Position");
		Point2D location = (Point2D) posAttribute.getValue();
		List<Component> currList = componentMap.get(location);
		currList.remove(toRemove);
		componentMap.put(location, currList);
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
		for (Point2D loc : componentMap.keySet()){
			double distance = Math.sqrt(Math.pow(centerLoc.getX() - loc.getX(), 2) + Math.pow(centerLoc.getY() - loc.getY(), 2));
			if (distance < radius){
				componentsWithinRadius.addAll(componentMap.get(loc));
			}
		}
		return componentsWithinRadius;
	}
	
	/**
	 * Returns list of components that lie at the nearest location (although if two locations are equidistant from the component,
	 * it arbitrarily chooses one).
	 * @param centerComp
	 * @return List of components at the nearest location
	 */
	public List<Component> getNearestComponents(Component centerComp){
		List<Point2D> locations = new ArrayList<Point2D>(componentMap.keySet());
		Point2D centerLoc = (Point2D) centerComp.getAttribute("Position").getValue();
		SortComponents_Distance sorter = new SortComponents_Distance();
		List<Point2D> sortedLocations = sorter.nearToFar(centerLoc, locations);
		return componentMap.get(sortedLocations.get(0));
	}

}
