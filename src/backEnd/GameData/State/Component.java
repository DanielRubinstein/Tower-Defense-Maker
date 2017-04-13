package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.BehaviorFactory;
import backEnd.GameEngine.Behaviors.Behavior;

public class Component extends Observable implements AttributeOwner {
	/**
	 * Any object on the grid is a component.
	 * 
	 * @author Daniel
	 */

	private final static String DEFAULT_ATTRIBUTE_PATH = "resources/defaultComponentAttributes";
	private final static String BEHAVIOR_PATH = "resources/behaviorNames";
	private final static ResourceBundle behaviorResources = ResourceBundle.getBundle(BEHAVIOR_PATH);
	private final static String DEFAULT_ATTRIBUTES_PATH = "resources/defaultTileAttributes";

	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTES_PATH);
	private AttributeData myAttributes;
	private Map<String, Behavior> myBehaviors;
	private AccessPermissions myAccessPermissions;
	private String myType;
	private List<Observer> observers = new ArrayList<Observer>();
	public Component(AttributeData attributes) throws FileNotFoundException {
		this(attributes, new AccessPermissionsImpl());
	}

	public Component(){
		
	}
	
	public Component(AttributeData attributes, AccessPermissions accessPermissions) throws FileNotFoundException {
		myAttributes = attributes;
		myBehaviors = new HashMap<>();
		AttributeFactory af = new AttributeFactory();
		BehaviorFactory bf = new BehaviorFactory(this); // add a real component
		for (String key : behaviorResources.keySet()) {
			String value = behaviorResources.getString(key);
			Attribute<?> myAttribute = af.getAttribute(key); // FIX THIS- HOW
																// DOES OUR
																// FACTORY
																// GENERATE
																// ATTRIBUTES?
			myAttributes.addAttribute(key, myAttribute);
			System.out.println(" $$$$ " + key);
			myBehaviors.put(key, bf.getBehavior(key));
		}

		AttributeFactory attrFact = new AttributeFactory();
		for (String key : attributeResources.keySet()) {
			System.out.println("# attribute ky " +key);
			Attribute<?> myAttribute = attrFact.getAttribute(key);
			addAttribute(key, myAttribute);
		}
		setupBehaviorObserving();
		this.myAccessPermissions = accessPermissions;
	}

	public AccessPermissions getAccessPermissions() {
		return myAccessPermissions;
	}

	public void setupBehaviorObserving() {
		for (String b : myBehaviors.keySet()) {
			Behavior currentBehavior = myBehaviors.get(b);
			myAttributes.addObserver(currentBehavior);
		}

	}

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

	public Attribute<?> getAttribute(String attributeType) {
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		((Attribute<T>) myAttributes.get(attrName)).setValue(newVal);
		notifyObservers();
	}

	@Override
	public void addObserver(Observer obs) {
		observers.add(obs);
	}

	@Override
	public void notifyObservers() {
		for (Observer obs : observers) {
			obs.update(this, null);
		}
	}
	@Override
	public void addAsListener(Observer o) {
		addObserver(o);
		//addObserver(o);
	}

	/**
	 * adds an attribute to the List of Attributes
	 * 
	 * @return
	 */
	// public abstract void addAttribute(Attribute toAdd);

}
