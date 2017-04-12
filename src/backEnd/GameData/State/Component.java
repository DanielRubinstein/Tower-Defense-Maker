package backEnd.GameData.State;

import java.util.Observer;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.GameEngine.Behaviors.Behavior;

public interface Component {

	AccessPermissions getAccessPermissions();

	void setupBehaviorObserving();

	/**
	 * When the engines call behaviors (for the behavior to be executed), it does so in Component via this method.
	 * @param behaviorType
	 * @return
	 */
	Behavior getBehavior(String behaviorType);

	Attribute<?> getAttribute(String attributeType);

	AttributeData getMyAttributes();

	void addAttributeData(AttributeData attributes);

	/**
	 * 
	 * @param type the type of this Component, i.e. Tower or Enemy
	 */
	void setMyType(String type);

	/**
	 * 
	 * @return the type of this Component, i.e. Tower or Enemy
	 */
	String getMyType();

	<T> void setAttributeValue(String attrName, T newVal);

}