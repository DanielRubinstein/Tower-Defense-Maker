package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeOwner;
import javafx.geometry.Point2D;

/**
 * This class is the implementation of the Tile Interface that holds the tiles
 * information and attributes
 * 
 * @author Riley Nisbet
 *
 */

public class TileImpl implements Tile, AttributeOwner, SerializableObservable {
	private final static String DEFAULT_ATTRIBUTES_PATH = "resources/defaultTileAttributes";
	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTES_PATH);
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
	
	public TileImpl(AccessPermissions aP,Point2D position )  throws FileNotFoundException {
		//System.out.println("executing Constructor for TileImpl");
		this.myAccessPerm = aP;
		this.myAttrData = new AttributeData(new HashMap<String, Attribute<?>>());
		AttributeFactoryReader attrFact = new AttributeFactoryImpl();
		this.myAttrData = new AttributeData(new HashMap<String, Attribute<?>>());
		for (String key : attributeResources.keySet()) {
			Attribute<?> myAttribute = attrFact.getAttribute(key);
			addAttribute(key, myAttribute);
		}
		this.setAttributeValue("Position", position);
		
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
		myAttrData.addAttribute(attributeResources.getString(name), value);
		notifyObservers();

	}

	@Override
	public Attribute<?> getAttribute(String name) {;
		
		return myAttrData.get(attributeResources.getString(name));
	}

	@Override
	public boolean hasAttribute(String name) {
		return myAttrData.containsAttribute(attributeResources.getString(name));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		myAttrData.<T>get(attrName).setValue(newVal);
		notifyObservers();
	}

	@Override
	public void addObserver(SerializableObserver obs) {
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

}
