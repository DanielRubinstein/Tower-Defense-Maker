package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Point2D;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;

/**
 * This is the Grid class that contains the Component Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class ComponentGraphImpl extends Observable implements ComponentGraph {
	private Map<Point2D, List<Component>> componentMap;
	private List<Component> myComponents;
	
	public ComponentGraphImpl(){
		componentMap = new HashMap<>();
	}
	
	
	public List<Component> getAllComponents(){
		myComponents=new ArrayList<Component>();
		for (Point2D myKey: componentMap.keySet()){
			for (Component myComponent: componentMap.get(myKey)){
				myComponents.add(myComponent);
			}
		}
		//if(!myComponents.isEmpty()) System.out.println(" ggggettting all components " + myComponents +
		//		"   " +myComponents.get(0).getAttribute("Position").getValue() + "   " + componentMap);
		return myComponents;
	}

	
//	@Override
//	public List<Component> getComponentList(){
//		List<Component> componentList = new ArrayList<Component>();
//		for (List<Component> n : componentMap.values()){
//			componentList.addAll(n);
//		}
//		return componentList;
//	}
	
	@Override
	public List<Component> getComponentsByScreenPosition(Point2D screenPosition){
		return componentMap.get(screenPosition);
	}
	
	@Override
	public List<Component> getComponentsByTileCorners(TileCorners tileCorners){
		List<Component> componentsOnTile = new ArrayList<Component>();
		for (Point2D componentGridPosition : componentMap.keySet()){
			if (componentGridPosition.getX() >= tileCorners.getMinX() 
					&& componentGridPosition.getX() <= tileCorners.getMaxX() 
					&& componentGridPosition.getY() > tileCorners.getMinY() 
					&& componentGridPosition.getY() < tileCorners.getMaxY()){
				componentsOnTile.addAll(componentMap.get(componentGridPosition));
			}
		}
		return componentsOnTile;
	}
	
	@Override
	public void addComponentToGrid(Component newComponent, Point2D screenPosition){

		List<Component> currList = componentMap.get(screenPosition);
		if(currList==null){
			currList= new ArrayList<Component>();
		}
		currList.add(newComponent);
		componentMap.put(screenPosition, currList);

		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void removeComponent(Component toRemove){
		if (!componentMap.containsKey(toRemove)){
			return;
		}
		Attribute<?> posAttribute= toRemove.getAttribute("Position");
		Point2D location = (Point2D) posAttribute.getValue();
		//System.out.println("removing comp at position " +location);

		//System.out.println( " befforeee " +componentMap);
		List<Component> currList = componentMap.get(location);
		if(currList==null){return;}
		currList.remove(toRemove);
		componentMap.put(location, currList);

		//System.out.println( " afterrrrr " +componentMap);
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

	@Override
	public void addAsObserver(Observer o) {
		this.addObserver(o);
		
	}

}
