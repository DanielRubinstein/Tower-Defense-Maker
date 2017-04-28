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

public class ComponentImpl implements AttributeOwner, SerializableObservable, Component {
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

	
	public ComponentImpl() throws FileNotFoundException{
		this(new AttributeData(),new AccessPermissionsImpl());
	}
	

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#printID()
	 */
	@Override
	public long printID(){
		return ID;
	}
	
	public ComponentImpl(AttributeData attributes, AccessPermissions accessPermissions) throws FileNotFoundException {
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

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getAccessPermissions()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getBehavior(java.lang.String)
	 */
	@Override
	public Behavior getBehavior(String behaviorType) {
		return myBehaviors.get(behaviorType);
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getAttribute(java.lang.String)
	 */
	@Override
	public <T> Attribute<T> getAttribute(String attributeType) {
		return myAttributes.get(attributeType);
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getMyAttributes()
	 */
	@Override
	public AttributeData getMyAttributes() {
		return myAttributes;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addAttributeData(backEnd.Attribute.AttributeData)
	 */
	@Override
	public void addAttributeData(AttributeData attributes) {
		myAttributes = attributes;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addAttribute(java.lang.String, backEnd.Attribute.Attribute)
	 */
	@Override
	public void addAttribute(String attrType, Attribute<?> newAttr) {
		myAttributes.addAttribute(attrType, newAttr);
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setMyType(java.lang.String)
	 */
	@Override
	public void setMyType(String type) {
		myType = type;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getMyType()
	 */
	@Override
	public String getMyType() {
		return myType;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setAttributeValue(java.lang.String, T)
	 */
	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		Attribute<T> attrToSet = myAttributes.<T>get(attrName);
		attrToSet.setValue(newVal);
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addObserver(backEnd.GameData.State.SerializableObserver)
	 */
	@Override
	public void addObserver(SerializableObserver obs) {
		System.out.println(observers);
		observers.add(obs);
	}

	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#containsAttribute(java.lang.String)
	 */
	@Override
	public boolean containsAttribute(String key){
		return myAttributes.containsAttribute(key);
	}
	
	private void notifyObservers() {
		for (SerializableObserver obs : observers) {
			obs.update(this, null);
		}
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getAndClearObservers()
	 */
	@Override
	public List<SerializableObserver> getAndClearObservers() {
		List<SerializableObserver> currObservers = observers;
		observers = new ArrayList<SerializableObserver>();
		return currObservers;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setPreviousMovement(backEnd.GameEngine.Engine.Coordinates)
	 */
	@Override
	public void setPreviousMovement(Coordinates myPreviousMovement){
		previousMovement=myPreviousMovement;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getPreviousMovement()
	 */
	@Override
	public Coordinates getPreviousMovement(){
		return previousMovement;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setObserverList(java.util.List)
	 */
	@Override
	public void setObserverList(List<SerializableObserver> observers) {
		this.observers = observers;
	}


	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getObservers()
	 */
	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#clearObservers()
	 */
	@Override
	public void clearObservers() {
		observers.clear();
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setObservers(java.util.List)
	 */
	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
	
}