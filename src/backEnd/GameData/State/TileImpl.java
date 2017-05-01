package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeReader;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

/**
 * This class is the implementation of the Tile Interface that holds the tiles
 * information and attributes
 * 
 * @author Riley Nisbet
 *
 */

public class TileImpl implements Tile, AttributeOwner, SerializableObservable {
	private final static StringResourceBundle strResources = new StringResourceBundle();
	private AccessPermissions myAccessPerm;
	private AttributeData myAttrData;
	private List<SerializableObserver> observers = new ArrayList<SerializableObserver>();;
	
	public TileImpl() throws FileNotFoundException{
		this(new AccessPermissionsImpl(), new Point2D(0,0));
	}
	
	public TileImpl(List<String> userModeAccessPermissions, List<String> gameModeAccessPermissions, List<String> levelModeAccessPermissions,
			Point2D position) throws FileNotFoundException {
		this(new AccessPermissionsImpl(userModeAccessPermissions, gameModeAccessPermissions, levelModeAccessPermissions), position);
	}
	
	public TileImpl(AccessPermissions accessPermissions,Point2D position )  throws FileNotFoundException {
		this.myAccessPerm = accessPermissions;
		this.myAttrData = new AttributeData(new HashMap<String, Attribute<?>>());
		AttributeFactoryReader attrFact = new AttributeFactoryImpl();
		this.myAttrData = new AttributeData(new HashMap<String, Attribute<?>>());
		for (String key : strResources.getKeysFromDefaultTileAttributes()) {
			Attribute<?> myAttribute = attrFact.getAttribute(key);
			addAttribute(key, myAttribute);
		}
		this.setAttributeValue(strResources.getFromAttributeNames("Position"), position);
		
		}
	
	@Override
	public AccessPermissions getAccessPermissions() {
		return myAccessPerm;
	}

	@Override
	public AttributeData getMyAttributes() {
		return myAttrData;
	}

	@Override
	public void setAttributeData(AttributeData newAttrData) {
		myAttrData = newAttrData;
		notifyObservers();
	}

	@Override
	public void addAttribute(String name, Attribute<?> value) {
		myAttrData.addAttribute(strResources.getFromAttributeNames(name), value);
		notifyObservers();

	}

	@SuppressWarnings("unchecked")
	@Override
	public Attribute<?> getAttribute(String name) {;
		return myAttrData.get(strResources.getFromAttributeNames(name));
	}
	
	@Override
	public <T> AttributeReader<T> getAttributeReader(String name) {;
		return myAttrData.get(strResources.getFromAttributeNames(name));
	}

	@Override
	public boolean hasAttribute(String name) {
		return myAttrData.containsAttribute(strResources.getFromAttributeNames(name));
	}

	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		myAttrData.<T>get(attrName).setValue(newVal);
		notifyObservers();
	}

	@Override
	public void addObserver(SerializableObserver obs) {
		if (observers == null){
			observers = new ArrayList<SerializableObserver>();
		}
		observers.add(obs);
	}

	private void notifyObservers() {
		for (SerializableObserver obs : observers) {
			obs.update(this, null);
		}
	}

	@Override
	public List<SerializableObserver> getAndClearObservers() {
		List<SerializableObserver> currObservers = new ArrayList<SerializableObserver>();
		for (SerializableObserver o : observers){
			currObservers.add(o);
		}
		observers = new ArrayList<SerializableObserver>();
		return currObservers;
	}

	@Override
	public void setObserverList(List<SerializableObserver> observers) {
		this.observers = observers;
		notifyObservers();
	}

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers = new ArrayList<SerializableObserver>();
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}

	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}

	@Override
	public boolean contains(String attName) {
		return myAttrData.containsAttribute(attName);
	}

	@Override
	public void removeAttribute(String attrName) {
		myAttrData.remove(attrName);
		
	}

	@Override
	public AccessPermissionsReader getAccessPermissionsReader() {
		return this.getAccessPermissionsReader();
	}

}
