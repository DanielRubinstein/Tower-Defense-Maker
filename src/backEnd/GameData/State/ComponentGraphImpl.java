package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwnerReader;

/**
 * This is the Grid class that contains the Component Grid and all of the
 * relevant getters/setters for other modules to use to access the Grid
 * 
 * @author Riley Nisbet
 *
 */

public class ComponentGraphImpl implements ComponentGraph {
	private Map<Point2D, List<Component>> componentMap;
	private List<Component> myComponents;
	private List<SerializableObserver> observers;
	private List<List<SerializableObserver>> compObserverList;

	public ComponentGraphImpl() {
		this(new HashMap<>());
	}

	public ComponentGraphImpl(HashMap<Point2D, List<Component>> fromXML) {
		componentMap = fromXML;
		observers = new ArrayList<SerializableObserver>();
		myComponents = new ArrayList<Component>();
		for(List<Component> cList : componentMap.values()){
			for(Component c: cList){
				myComponents.add(c);
			}
		}
	}

	public Collection<Component> getAllComponents() {

		/*
		myComponents = new ArrayList<Component>();
		for (List<Component> list : componentMap.values()) {
			myComponents.addAll(list);
		}
		*/

		return myComponents;
	}

	
	public Map<Point2D, List<Component>> getComponentMap() {
		return componentMap;
	}
	

	@Override
	public List<Component> getComponentsByScreenPosition(Point2D screenPosition) {
		List<Component> atLocation = new ArrayList<Component>();
		for(Component c : myComponents){
			if(c.getAttribute("Position").getValue() == screenPosition){
				atLocation.add(c);
			}
		}
		return atLocation;
	}

	
	@Override
	public List<Component> getComponentsByTileCorners(TileCorners tileCorners) {
		List<Component> componentsOnTile = new ArrayList<Component>();
		
		for(Component c : myComponents){
			Point2D loc = (Point2D)c.getAttribute("Position").getValue();
			if (loc.getX() >= tileCorners.getMinX()
					&& loc.getX() <= tileCorners.getMaxX()
					&& loc.getY() > tileCorners.getMinY()
					&& loc.getY() < tileCorners.getMaxY()) {
				componentsOnTile.add(c);
			}
		}
		
		/*
		for (Point2D componentGridPosition : componentMap.keySet()) {
			if (componentGridPosition.getX() >= tileCorners.getMinX()
					&& componentGridPosition.getX() <= tileCorners.getMaxX()
					&& componentGridPosition.getY() > tileCorners.getMinY()
					&& componentGridPosition.getY() < tileCorners.getMaxY()) {
				componentsOnTile.addAll(componentMap.get(componentGridPosition));
			}
		}
		*/
		return componentsOnTile;
	}
	

	@Override
	public void addComponentToGrid(Component newComponent, Point2D screenPosition) {
		/*
		List<Component> currList = componentMap.get(screenPosition);
		if (currList == null) {
			currList = new ArrayList<Component>();
		}
		currList.add(newComponent);
		componentMap.put(newComponent.<Point2D>getAttribute("Position").getValue(), currList);
		*/
		myComponents.add(newComponent);
		newComponent.setAttributeValue("Position", screenPosition);
		//newComponent.getAttribute("Position").setValue(screenPosition);
		notifyObservers(newComponent);
	}

	@Override
	public void removeComponent(Component toRemove) {
		/*
		Point2D location = toRemove.<Point2D>getAttribute("Position").getValue();
		System.out.print("location in removemethod is " + location);
		List<Component> currList = componentMap.get((Point2D)toRemove.getAttribute("Position").getValue());
		if(currList == null){
			System.out.println("SOMETHING WRONG");
			return;
		}
		currList.remove(toRemove);
		componentMap.put((Point2D)toRemove.getAttribute("Position").getValue(), currList);
		
		
		
		for (Point2D location: componentMap.keySet()){
			if (componentMap.get(location).contains(toRemove)){
				List<Component> currList = componentMap.get((Point2D)toRemove.getAttribute("Position").getValue());
				currList.remove(toRemove);
				componentMap.put((Point2D)toRemove.getAttribute("Position").getValue(), currList);
				myComponents.remove(toRemove);

			}
		}
		*/
		
		myComponents.remove(toRemove);
		//System.out.println("removed in component graph");
		notifyObservers(toRemove);
	}

	@Override
	public List<Component> getComponentsWithinRadius(Component centerComp, double radius){
		Point2D centerLoc = centerComp.<Point2D>getAttribute("Position").getValue();
		ArrayList<Component> componentsWithinRadius = new ArrayList<Component>();
		for(Component c : myComponents){
			Point2D myLoc = (Point2D)c.getAttribute("Position").getValue();
			double distance = Math.sqrt(Math.pow(centerLoc.getX() - myLoc.getX(), 2) + Math.pow(centerLoc.getY() - myLoc.getY(), 2));
			if (distance < radius) {
				componentsWithinRadius.add(c);	
			}
		}
		
			/*
			for (Point2D loc : componentMap.keySet()) {
				double distance = Math.sqrt(Math.pow(centerLoc.getX() - loc.getX(), 2) + Math.pow(centerLoc.getY() - loc.getY(), 2));
				if (distance < radius) {
					componentsWithinRadius.addAll(componentMap.get(loc));
					componentsWithinRadius.remove(centerComp);//don't add yourself
				}
			}
			*/
		componentsWithinRadius.remove(centerComp);//don't add yourself
		return componentsWithinRadius;
	}

	/*
	@Override
	public List<Component> getNearestComponents(Component centerComp) {
		List<Point2D> locations = new ArrayList<Point2D>(componentMap.keySet());
		Point2D centerLoc = centerComp.<Point2D>getAttribute("Position").getValue();
		SortComponents_Distance sorter = new SortComponents_Distance();
		List<Point2D> sortedLocations = sorter.nearToFar(centerLoc, locations);
		return componentMap.get(sortedLocations.get(0));
	}
	*/

	@Override
	public void addObserver(SerializableObserver o) {
		observers.add(o);

	}

	@Override
	public boolean contains(AttributeOwnerReader c) {
		return myComponents.contains(c);
	}

	@Override
	public void saveAndClearObservers() {
		compObserverList = new ArrayList<List<SerializableObserver>>();

		for (int i = 0; i < myComponents.size(); i++) {
			compObserverList.add(myComponents.get(i).getAndClearObservers());
		}
	}

	@Override
	public void setComponentObservers() {

		for (int i = 0; i < myComponents.size(); i++){
			//System.out.println("in componentGraphImpl, SerializableObserver list is "+compObserverList.get(i));
			myComponents.get(i).setObserverList(compObserverList.get(i));
		}

	}

	@Override
	public void clearComponents(){
		
		for(Component c : myComponents){
			removeComponent(c);
		}
		
		/*
		List<Component> list = new ArrayList<Component>();
		for (Point2D x : componentMap.keySet())
		{
			for (Component y : componentMap.get(x))
			{
				list.add(y);
			}
		}
		
		for (Component x : list)
		{
			removeComponent(x);
		}
		*/
	}

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers.clear();
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
	
	private void notifyObservers(Object obj){
		for (SerializableObserver o : observers){
			o.update(null, obj);
		}
	}
	
	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}

}