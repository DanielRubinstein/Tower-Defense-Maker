package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.Attribute.AttributeOwner;
import backEnd.Mode.GameModeType;
import backEnd.Mode.UserModeType;
import javafx.geometry.Point2D;

/**
 * This class is the implementation of the Tile Interface that holds the tiles information and attributes
 * @author Riley Nisbet
 *
 */

public class TileImpl extends Observable implements Tile, AttributeOwner{
	private final static String DEFAULT_ATTRIBUTES_PATH = "resources/defaultTileAttributes";
	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTES_PATH);
	private Point2D myLocation;
	private AccessPermissions myAccessPerm;
	private AttributeData myAttrData;
	private List<Observer> observers = new ArrayList<Observer>();
	
	public TileImpl(List<GameModeType> gameModeAccessPermissions, List<UserModeType> userModeAccessPermissions , Point2D location) throws FileNotFoundException{
		this.myLocation = location;
		this.myAccessPerm = new AccessPermissionsImpl(gameModeAccessPermissions, userModeAccessPermissions);
		this.myAttrData = new AttributeData(new HashMap<String,Attribute<?>>());
		AttributeFactory attrFact = new AttributeFactory();
		this.myAttrData = new AttributeData(new HashMap<String,Attribute<?>>());
		for (String key : attributeResources.keySet()){
			Attribute<?> myAttribute = attrFact.getAttribute(key);
			addAttribute(key, myAttribute);
		}

	}
	
	@Override
	public AccessPermissions getAccessPermissions(){
		return myAccessPerm;
	}
	
	@Override
	public AttributeData getMyAttributes(){
		return myAttrData;
	}
	
	@Override
	public void setAttributeData(AttributeData newAttrData){
		myAttrData = newAttrData;
	}
	
	@Override
	public Point2D getLocation(){
		return myLocation;
	}

	
	public void addAttribute(String name, Attribute<?> value) {
		myAttrData.addAttribute(attributeResources.getString(name), value);
		
	}

	@Override
	public Attribute<?> getAttribute(String name) {
		return myAttrData.get(attributeResources.getString(name));
	}

	@Override
	public boolean hasAttribute(String name) {
		return myAttrData.getAttributeMap().containsKey(attributeResources.getString(name));
	}

	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		myAttrData.get(attrName).setValue(newVal);
		notifyObservers();
		
	}
	
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addObserver(java.util.Observer)
	 */
	@Override
	public void addObserver(Observer obs){
		observers.add(obs);
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#notifyAllObservers()
	 */
	@Override
	public void notifyObservers(){
		for(Observer obs : observers){
			obs.update(this, null);
		}
	}



}
