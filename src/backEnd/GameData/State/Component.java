package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.GameEngine.Behaviors.BehaviorFactory;
import backEnd.GameEngine.Engine.Coordinates;
import resources.constants.StringResourceBundle;

public class Component implements AttributeOwner, SerializableObservable {
	/**
	 * Any object on the grid is a component.
	 * 
	 * @author Daniel
	 */
	//TODO: do we need this behavior thing still? the file is empty
	private final static String BEHAVIOR_PATH = "resources/behaviorNames";
	private final static ResourceBundle behaviorResources = ResourceBundle.getBundle(BEHAVIOR_PATH);
	private static final StringResourceBundle strResources = new StringResourceBundle();

	private AttributeData myAttributes;
	private Map<String, Behavior> myBehaviors;
	private AccessPermissions myAccessPermissions;
	private String myType;
	private List<SerializableObserver> observers;
	private long ID;
	private Coordinates previousMovement;

	
	public Component() throws FileNotFoundException{
		this(new AttributeData(),new AccessPermissionsImpl());
	}
	

	public long printID(){
		return ID;
	}
	
	public Component(AttributeData attributes, AccessPermissions accessPermissions) throws FileNotFoundException {
		observers = new ArrayList<SerializableObserver>();
		previousMovement=new Coordinates(0,0);
		ID = System.nanoTime();
		System.out.println(ID + "   ");
		System.out.println("creating component " + this);
		myAttributes = attributes;
		myBehaviors = new HashMap<>();
		AttributeFactoryReader attributeFactory = new AttributeFactoryImpl();
		BehaviorFactory bf = new BehaviorFactory(this); // add a real component
		for (String key : behaviorResources.keySet()) {
			String value = behaviorResources.getString(key);
			Attribute<?> myAttribute = attributeFactory.getAttribute(key); // FIXME THIS- HOW
																// DOES OUR
																// FACTORY
																// GENERATE
																// ATTRIBUTES?
			myAttributes.addAttribute(key, myAttribute);
			myBehaviors.put(key, bf.getBehavior(key));
		}

		for (String key : strResources.getKeysFromDefaultTileAttributes()) {
			Attribute<?> myAttribute = attributeFactory.getAttribute(key);
			addAttribute(key, myAttribute);
		}
		//setupBehaviorObserving();
		this.myAccessPermissions = accessPermissions;
	}

	public AccessPermissions getAccessPermissions() {
		return myAccessPermissions;
	}

	/*
	 
	
	public void setupBehaviorObserving() {
		for (String b : myBehaviors.keySet()) {
			Behavior currentBehavior = myBehaviors.get(b);
			myAttributes.addObserver(currentBehavior);
		}

	}
	*/

	/**
	 * When the engines call behaviors (for the behavior to be executed), it
	 * does so in Component via this method.
	 * 
	 * @param behaviorType
	 * @return
	 */
	public Behavior getBehavior(String behaviorType) {
		return myBehaviors.get(behaviorType);
	}

	@Override
	public <T> Attribute<T> getAttribute(String attributeType) {
		return myAttributes.get(attributeType);
	}

	public AttributeData getMyAttributes() {
		return myAttributes;
	}

	public void addAttributeData(AttributeData attributes) {
		myAttributes = attributes;
	}

	@Override
	public void addAttribute(String attrType, Attribute<?> newAttr) {
		myAttributes.addAttribute(attrType, newAttr);
	}

	/**
	 * 
	 * @param type the type of this Component, i.e. Tower or Enemy
	 */
	public void setMyType(String type) {
		myType = type;
	}

	/**
	 * 
	 * @return the type of this Component, i.e. Tower or Enemy
	 */
	public String getMyType() {
		return myType;
	}

	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		Attribute<T> attrToSet = myAttributes.<T>get(attrName);
		attrToSet.setValue(newVal);
		notifyObservers();
	}

	@Override
	public void addObserver(SerializableObserver obs) {
		System.out.println(observers);
		observers.add(obs);
	}

	
	public boolean containsAttribute(String key){
		return myAttributes.containsAttribute(key);
	}
	
	private void notifyObservers() {
		for (SerializableObserver obs : observers) {
			obs.update(this, null);
		}
	}

	@Override
	public List<SerializableObserver> getAndClearObservers() {
		List<SerializableObserver> currObservers = observers;
		observers = new ArrayList<SerializableObserver>();
		return currObservers;
	}
	
	public void setPreviousMovement(Coordinates myPreviousMovement){
		previousMovement=myPreviousMovement;
	}

	public Coordinates getPreviousMovement(){
		return previousMovement;
	}
	
	@Override
	public void setObserverList(List<SerializableObserver> observers) {
		this.observers = observers;
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
	
	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
	
}