package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

public class ComponentGraphImpl implements ComponentGraph {
	private int gridWidth;
	private int gridHeight;
	private int pointResWidth;
	private int pointResHeight;
	private Map<Point2D, List<Component>> componentMap;
	private List<Component> myComponents;
	
	public ComponentGraphImpl(int width, int height, int pointResWidth, int pointResHeight){
		this.gridWidth = width;
		this.gridHeight = height;
		this.pointResWidth = pointResWidth;
		this.pointResHeight = pointResHeight;
		componentMap = new HashMap<>();
	}
	
	@Override
	public int getGridWidth(){
		return gridWidth;
	}
	
	public List<Component> getAllComponents(){
		myComponents=new ArrayList<Component>();
		for (Point2D myKey: componentMap.keySet()){
			for (Component myComponent: componentMap.get(myKey)){
				myComponents.add(myComponent);
			}
		}
		return myComponents;
	}

	
	@Override
	public int getGridHeight(){
		return gridHeight;
	}
	
	@Override
	public List<Component> getComponentList(){
		List<Component> componentList = new ArrayList<Component>();
		for (List<Component> n : componentMap.values()){
			componentList.addAll(n);
		}
		return componentList;
	}
	
	@Override
	public List<Component> getComponentsByLocation(Point2D location){
		return componentMap.get(location);
	}
	
	@Override
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
	
	@Override
	public void addComponentToGrid(Component newComponent, Point2D location){
		List<Component> currList = componentMap.get(location);
		currList.add(newComponent);
		componentMap.put(location, currList);
	}
	
	@Override
	public void removeComponent(Component toRemove){
		Attribute<?> posAttribute= toRemove.getAttribute("Position");
		Point2D location = (Point2D) posAttribute.getValue();
		List<Component> currList = componentMap.get(location);
		currList.remove(toRemove);
		componentMap.put(location, currList);
	}
	
	@Override
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
	
	@Override
	public List<Component> getNearestComponents(Component centerComp){
		List<Point2D> locations = new ArrayList<Point2D>(componentMap.keySet());
		Point2D centerLoc = (Point2D) centerComp.getAttribute("Position").getValue();
		SortComponents_Distance sorter = new SortComponents_Distance();
		List<Point2D> sortedLocations = sorter.nearToFar(centerLoc, locations);
		return componentMap.get(sortedLocations.get(0));
	}

}
