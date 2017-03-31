package backEnd.GameEngine;

import java.util.List;

public abstract class Component {
	List<Attribute<?>> myAttributes;
	List<Behavior> myBehaviors;

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
}