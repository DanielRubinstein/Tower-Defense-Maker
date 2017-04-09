package backEnd.GameEngine;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameEngine.Behaviors.Behavior;


public class Component {
	
	private final static String DEFAULT_ATTRIBUTE_PATH = "resources/componentDefaults";
	private final static String BEHAVIOR_PATH = "resources/behaviorNames";
	private final static ResourceBundle behaviorResources = ResourceBundle.getBundle(BEHAVIOR_PATH);
	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTE_PATH);
	private AttributeData myAttributes;
	private Map<String, Behavior> myBehaviors;
	
	
	
	public Component(){
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

	/**
	 * adds an attribute to the List of Attributes
	 * @return 
	 */
	//public abstract void addAttribute(Attribute toAdd);
	
}