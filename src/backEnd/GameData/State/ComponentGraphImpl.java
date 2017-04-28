package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;
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
		
		return componentsOnTile;
	}
	

	@Override
	public void addComponentToGrid(Component newComponent, Point2D screenPosition) {
		myComponents.add(newComponent);
		newComponent.setAttributeValue("Position", screenPosition);
		notifyObservers(newComponent);
	}

	@Override
	public void removeComponent(Component toRemove) {
		myComponents.remove(toRemove);
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
		componentsWithinRadius.remove(centerComp);//don't add yourself
		return componentsWithinRadius;
	}


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
			myComponents.get(i).setObserverList(compObserverList.get(i));
		}

	}

	@Override
	public void clearComponents(){
		
		for(Component c : myComponents){
			removeComponent(c);
		}
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