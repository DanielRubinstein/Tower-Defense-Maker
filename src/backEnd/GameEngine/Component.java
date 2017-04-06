package backEnd.GameEngine;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class Component {
	
	private ComponentListener myListener;
	private final static String DEFAULT_ATTRIBUTE_PATH = "resources/componentDefaults";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTE_PATH);
	private Map<String,Attribute<?>> myAttributes;
	private Map<String, Behavior> myBehaviors;
	
	
	
	public Component(){
		AttributeFactory af=new AttributeFactory();
		BehaviorFactory bf=new BehaviorFactory();
		for (String key: myResources.keySet()){
			String value=myResources.getString(key);
			Attribute<?> myAttribute= af.getAttribute(key, value);
			myAttributes.put(key, myAttribute);
			myBehaviors.put(key, bf.getBehavior(key));
			}
	}
		
	
	public void addAttribute(String key, Attribute<?> toAdd){
		myAttributes.put(key, toAdd);
		myListener.updateAttributes(myAttributes);
	}
	
	public void replaceAttributes(Map<String,Attribute<?>> newAttributes){
		myAttributes=newAttributes;
		myListener.updateAttributes(myAttributes);
	}
	
	/**
	 * When the engines call behaviors (for the behavior to be executed), it does so in Component via this method.
	 * @param behaviorType
	 * @return
	 */
	public Behavior getBehavior(String behaviorType){
		return myBehaviors.get(behaviorType);
	}
	
	/**
	 * Get the attribute that the behavior modifies.
	 * @param attributeType
	 * @return
	 */
	public Attribute<?> getAttribute(String attributeType){
		return myAttributes.get(attributeType);
	}
	
	/**
	 * adds an attribute to the List of Attributes
	 * @return 
	 */
	//public abstract void addAttribute(Attribute toAdd);
	
	 /**
     * Change the shape of the cell
     * @param cellShape new cell shape
     */
	
	
}
