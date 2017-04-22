package backEnd.Attribute;

import java.util.List;
import java.util.Observer;

import backEnd.GameData.State.AccessPermissions;

public interface AttributeOwner extends AttributeOwnerReader {

	/**
	 * Add a Attributes to the Tile of type attrType. String attrType has to be
	 * a Key from the tilDefaults properties file
	 * 
	 * @param attrType
	 * @param newAttr
	 */
	void addAttribute(String attrName, Attribute<?> newAttr);

	/**
	 * @return AccessPermissions
	 */
	AccessPermissions getAccessPermissions();

	<T> void setAttributeValue(String attrName, T newVal);
	
	/**
	 * Clear observers
	 * @return list of observers
	 */
	public List<Observer> getAndClearObservers();
	
	/**
	 * Set observers
	 */
	public void setObserverList(List<Observer> observers);
}
