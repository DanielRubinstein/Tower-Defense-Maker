package backEnd.GameEngine;

import java.util.List;

public abstract class Component {
	List<Attribute<?>> myAttributes;
	List<Behavior> myBehaviors;
	
	

	
	public abstract void addAttribute(Attribute<?> toAdd);
	
	public abstract void replaceAttributes(List<Attribute<?>> newAttributes);
	
	/**
	 * When the engines call behaviors (for the behavior to be executed), it does so in Component via this method.
	 * @param behaviorType
	 * @return
	 */
	public abstract Behavior getBehavior(String behaviorType);
	
	/**
	 * Get the attribute that the behavior modifies.
	 * @param attributeType
	 * @return
	 */
	public abstract Attribute<?> getAttribute(String attributeType);
	
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
