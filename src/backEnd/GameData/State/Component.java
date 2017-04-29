package backEnd.GameData.State;

import java.util.List;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.GameEngine.Engine.Coordinates;

public interface Component extends AttributeOwner{

	long printID();

	AccessPermissions getAccessPermissions();

	/**
	 * When the engines call behaviors (for the behavior to be executed), it
	 * does so in Component via this method.
	 * 
	 * @param behaviorType
	 * @return
	 */
	Behavior getBehavior(String behaviorType);

	<T> Attribute<T> getAttribute(String attributeType);

	AttributeData getMyAttributes();

	void addAttributeData(AttributeData attributes);

	void addAttribute(String attrType, Attribute<?> newAttr);

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

	void addObserver(SerializableObserver obs);

	boolean containsAttribute(String key);

	List<SerializableObserver> getAndClearObservers();

	void setPreviousMovement(Coordinates myPreviousMovement);

	Coordinates getPreviousMovement();

	void setObserverList(List<SerializableObserver> observers);

	List<SerializableObserver> getObservers();

	void clearObservers();

	void setObservers(List<SerializableObserver> observersave);

	int compareTo(Object o);

}
