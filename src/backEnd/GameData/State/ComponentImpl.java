package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.*;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.BehaviorFactory;
import backEnd.GameEngine.Behaviors.Behavior;


public class ComponentImpl extends Observable implements AttributeOwner, Component {
	/**
	 * Any object on the grid is a component.
	 * @author Daniel
	 */
	
	private final static String DEFAULT_ATTRIBUTE_PATH = "resources/componentDefaults";
	private final static String BEHAVIOR_PATH = "resources/behaviorNames";
	private final static ResourceBundle behaviorResources = ResourceBundle.getBundle(BEHAVIOR_PATH);
	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTE_PATH);
	private AttributeData myAttributes;
	private Map<String, Behavior> myBehaviors;
	private AccessPermissions myAccessPermissions;
	private String myType;
	private List<Observer> observers = new ArrayList<Observer>();


	
	public ComponentImpl(AttributeData attributes) throws FileNotFoundException{
		this(attributes, new AccessPermissionsImpl());
	}
	
	public ComponentImpl(AttributeData attributes, AccessPermissions accessPermissions) throws FileNotFoundException{
		myAttributes=attributes;
		AttributeFactory af=new AttributeFactory();
		BehaviorFactory bf=new BehaviorFactory(this); //add a real component
		for (String key: behaviorResources.keySet()){
			String value=behaviorResources.getString(key);
			Attribute<?> myAttribute= af.getAttribute(key); //FIX THIS- HOW DOES OUR FACTORY GENERATE ATTRIBUTES?
			myAttributes.addAttribute(key, myAttribute);
			myBehaviors.put(key, bf.getBehavior(key));
			}
		setupBehaviorObserving();
		this.myAccessPermissions = accessPermissions;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getAccessPermissions()
	 */
	@Override
	public AccessPermissions getAccessPermissions(){
		return myAccessPermissions;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setupBehaviorObserving()
	 */
	@Override
	public void setupBehaviorObserving(){
		for (String b: myBehaviors.keySet()){
			Behavior currentBehavior=myBehaviors.get(b);
			myAttributes.addObserver(currentBehavior);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getBehavior(java.lang.String)
	 */
	@Override
	public Behavior getBehavior(String behaviorType){
		return myBehaviors.get(behaviorType);
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getAttribute(java.lang.String)
	 */
	@Override
	public Attribute<?> getAttribute(String attributeType){
		return myAttributes.get(attributeType);
	}
	
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getMyAttributes()
	 */
	@Override
	public AttributeData getMyAttributes(){
		return myAttributes;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addAttributeData(backEnd.Attribute.AttributeData)
	 */
	@Override
	public void addAttributeData(AttributeData attributes){
		myAttributes=attributes;
	}



	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setMyType(java.lang.String)
	 */
	@Override
	public void setMyType(String type){
		myType = type;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getMyType()
	 */
	@Override
	public String getMyType(){
		return myType;
	}

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#setAttributeValue(java.lang.String, T)
	 */
	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		myAttributes.get(attrName).setValue(newVal);
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

	
	
	

	
	/**
	 * adds an attribute to the List of Attributes
	 * @return 
	 */
	//public abstract void addAttribute(Attribute toAdd);
	
}