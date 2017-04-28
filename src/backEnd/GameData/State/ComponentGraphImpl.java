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
	private Map<Point2D, List<ComponentImpl>> componentMap;
	private List<ComponentImpl> myComponents;
	private List<SerializableObserver> observers;
	private List<List<SerializableObserver>> compObserverList;

	public ComponentGraphImpl() {
		this(new HashMap<>());
	}

	public ComponentGraphImpl(HashMap<Point2D, List<ComponentImpl>> fromXML) {
		componentMap = fromXML;
		observers = new ArrayList<SerializableObserver>();
		myComponents = new ArrayList<ComponentImpl>();
	}

	public Collection<ComponentImpl> getAllComponents() {

		myComponents = new ArrayList<ComponentImpl>();
		for (List<ComponentImpl> list : componentMap.values()) {
			myComponents.addAll(list);
		}

		return myComponents;
	}

	public Map<Point2D, List<ComponentImpl>> getComponentMap() {
		return componentMap;
	}

	@Override
	public List<ComponentImpl> getComponentsByScreenPosition(Point2D screenPosition) {
		return componentMap.get(screenPosition);
	}

	@Override
	public List<ComponentImpl> getComponentsByTileCorners(TileCorners tileCorners) {
		List<ComponentImpl> componentsOnTile = new ArrayList<ComponentImpl>();
		for (Point2D componentGridPosition : componentMap.keySet()) {
			if (componentGridPosition.getX() >= tileCorners.getMinX()
					&& componentGridPosition.getX() <= tileCorners.getMaxX()
					&& componentGridPosition.getY() > tileCorners.getMinY()
					&& componentGridPosition.getY() < tileCorners.getMaxY()) {
				componentsOnTile.addAll(componentMap.get(componentGridPosition));
			}
		}
		return componentsOnTile;
	}

	@Override
	public void addComponentToGrid(ComponentImpl newComponent, Point2D screenPosition) {

		List<ComponentImpl> currList = componentMap.get(screenPosition);
		if (currList == null) {
			currList = new ArrayList<ComponentImpl>();
		}
		currList.add(newComponent);
		componentMap.put(screenPosition, currList);
		myComponents.add(newComponent);
		newComponent.getAttribute("Position").setValue(screenPosition);
		notifyObservers(newComponent);
	}

	@Override
	public void removeComponent(ComponentImpl toRemove) {
		Point2D location = toRemove.<Point2D>getAttribute("Position").getValue();
		List<ComponentImpl> currList = componentMap.get(location);
		if (currList == null) {
			return;
		}
		currList.remove(toRemove);
		componentMap.put(location, currList);
		myComponents.remove(toRemove);
		notifyObservers(toRemove);
	}

	@Override
	public List<ComponentImpl> getComponentsWithinRadius(ComponentImpl centerComp, double radius){
		Point2D centerLoc = centerComp.<Point2D>getAttribute("Position").getValue();
		ArrayList<ComponentImpl> componentsWithinRadius = new ArrayList<ComponentImpl>();
		if (componentMap.keySet().size() != 0) {
			for (Point2D loc : componentMap.keySet()) {
				double distance = Math.sqrt(Math.pow(centerLoc.getX() - loc.getX(), 2) + Math.pow(centerLoc.getY() - loc.getY(), 2));
				if (distance < radius) {
					componentsWithinRadius.addAll(componentMap.get(loc));
					componentsWithinRadius.remove(centerComp);//don't add yourself
				}
			}
		}
		return componentsWithinRadius;
	}

	@Override
	public List<ComponentImpl> getNearestComponents(ComponentImpl centerComp) {
		List<Point2D> locations = new ArrayList<Point2D>(componentMap.keySet());
		Point2D centerLoc = centerComp.<Point2D>getAttribute("Position").getValue();
		SortComponents_Distance sorter = new SortComponents_Distance();
		List<Point2D> sortedLocations = sorter.nearToFar(centerLoc, locations);
		return componentMap.get(sortedLocations.get(0));
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
			System.out.println("in componentGraphImpl, SerializableObserver list is "+compObserverList.get(i));
			myComponents.get(i).setObserverList(compObserverList.get(i));
		}

	}

	@Override
	public void clearComponents()
	{
		List<ComponentImpl> list = new ArrayList<ComponentImpl>();
		for (Point2D x : componentMap.keySet())
		{
			for (ComponentImpl y : componentMap.get(x))
			{
				list.add(y);
			}
		}
		
		for (ComponentImpl x : list)
		{
			removeComponent(x);
		}
	}

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers = null;
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

}