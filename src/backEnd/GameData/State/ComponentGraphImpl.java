package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;

/**
 * This is the Grid class that contains the Component Grid and all of the
 * relevant getters/setters for other modules to use to access the Grid
 * 
 * @author Riley Nisbet
 *
 */

public class ComponentGraphImpl implements ComponentGraph {
	private List<Component> myComponents;
	private List<SerializableObserver> observers;
	private List<SerializableObserverGen<Component>> observersGen;
	private List<List<SerializableObserver>> compObserverList;
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();


	public ComponentGraphImpl() {
		this(new ArrayList<>());
	}

	public ComponentGraphImpl(List<Component> fromXML) {
		myComponents = fromXML;
		observers = new ArrayList<SerializableObserver>();
		observersGen = new ArrayList<>();

	}


	public List<Component> getAllComponents() {
		return myComponents;
	}
	

	@Override
	public List<Component> getComponentsByScreenPosition(Point2D screenPosition) {
		List<Component> atLocation = new ArrayList<Component>();
		for(Component c : myComponents){
			if(c.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue().equals(screenPosition)){
				atLocation.add(c);
			}
		}
		return atLocation;
	}

	
	@Override

	public List<Component> getComponentsByTileCorners(TileCorners tileCorners) {
		List<Component> componentsOnTile = new ArrayList<Component>();
		
		for(Component c : myComponents){
			Point2D loc = c.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
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
		newComponent.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), screenPosition);
		notifyObservers(newComponent);
	}

	@Override
	public void removeComponent(Component toRemove) {
		myComponents.remove(toRemove);
		notifyObservers(toRemove);
	}

	@Override
	public List<Component> getComponentsWithinRadius(Component centerComp, double radius){
		Point2D centerLoc = centerComp.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
		ArrayList<Component> componentsWithinRadius = new ArrayList<Component>();
		for(Component c : myComponents){
			Point2D myLoc = c.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
			double distance = Math.sqrt(Math.pow(centerLoc.getX() - myLoc.getX(), 2) + Math.pow(centerLoc.getY() - myLoc.getY(), 2));
			if (distance < radius) {
				componentsWithinRadius.add(c);	
			}
		}
		componentsWithinRadius.remove(centerComp);//don't add yourself
		Collections.reverse(componentsWithinRadius);
		return componentsWithinRadius;
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
	public void clearObservers() {
		observers.clear();
	}

	private void notifyObservers(Component obj){
		for (SerializableObserver o : observers){
			o.update(null, obj);
		}
		for (SerializableObserverGen<Component> o : observersGen){
			o.update(null, obj);
		}
	}

	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}

	@Override
	public Collection<AttributeOwner> getAllAttributeOwners() {
		Collection<AttributeOwner> myAOs = new ArrayList<AttributeOwner>();
		for(AttributeOwner ao : myComponents){
			myAOs.add(ao);
		}
		return myAOs;
	}

	@Override
	public void addObserver(SerializableObserverGen<Component> o) {
		observersGen.add(o);
	}

	@Override
	public List<SerializableObserverGen<Component>> getObserversGen() {
		return observersGen;
	}

	@Override
	public void setObserversGen(List<SerializableObserverGen<Component>> observersave) {
		observersGen = observersave;
		
	}

}
