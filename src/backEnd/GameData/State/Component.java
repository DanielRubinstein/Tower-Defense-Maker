package backEnd.GameData.State;

import java.util.Map;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.BehaviorFactory;
import backEnd.GameEngine.Behaviors.Behavior;


public class Component implements AttributeOwner {
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
	
	public Component(){
		this(new AccessPermissionsImpl()); //TODO so will we actually use this if we always need to pass in an
		//AttributeData? Component always needs to contain an AttributeData!
	}
	
	public Component(AttributeData attributes){
		this(new AccessPermissionsImpl());
		myAttributes=attributes;
	}
	
	public Component(AccessPermissions accessPermissions){
		Component dummyComponent=new Component();
		AttributeFactory af=new AttributeFactory();
		BehaviorFactory bf=new BehaviorFactory(dummyComponent); //add a real component
		for (String key: behaviorResources.keySet()){
			String value=behaviorResources.getString(key);
			Attribute<?> myAttribute= af.getAttribute(key); //FIX THIS- HOW DOES OUR FACTORY GENERATE ATTRIBUTES?
			myAttributes.addAttribute(key, myAttribute);
			myBehaviors.put(key, bf.getBehavior(key));
			}
		setupBehaviorObserving();
		this.myAccessPermissions = accessPermissions;
	}
	
	public AccessPermissions getAccessPermissions(){
		return myAccessPermissions;
	}

	public void setupBehaviorObserving(){
		for (String b: myBehaviors.keySet()){
			Behavior currentBehavior=myBehaviors.get(b);
			myAttributes.addObserver(currentBehavior);
		}
		
	}
	
	/**
	 * When the engines call behaviors (for the behavior to be executed), it does so in Component via this method.
	 * @param behaviorType
	 * @return
	 */
	public Behavior getBehavior(String behaviorType){
		return myBehaviors.get(behaviorType);
	}
	
	public Attribute<?> getAttribute(String attributeType){
		return myAttributes.get(attributeType);
	}
	
	
	public AttributeData getMyAttributes(){
		return myAttributes;
	}
	
	public void addAttributeData(AttributeData attributes){
		myAttributes=attributes;
	}

	@Override
	public void addAttribute(String attrType, Attribute<?> newAttr) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param type the type of this Component, i.e. Tower or Enemy
	 */
	public void setMyType(String type){
		myType = type;
	}
	
	/**
	 * 
	 * @return the type of this Component, i.e. Tower or Enemy
	 */
	public String getMyType(){
		return myType;
	}
	
	/**
	 * adds an attribute to the List of Attributes
	 * @return 
	 */
	//public abstract void addAttribute(Attribute toAdd);
	
}