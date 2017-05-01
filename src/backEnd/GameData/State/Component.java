package backEnd.GameData.State;

import java.util.List;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.Engine.Coordinates;

public interface Component extends AttributeOwner{

	long printID();

	AccessPermissions getAccessPermissions();

	<T> Attribute<T> getAttribute(String attributeType);

	AttributeData getMyAttributes();

	void addAttributeData(AttributeData attributes);

	void addAttribute(String attrType, Attribute<?> newAttr);

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
