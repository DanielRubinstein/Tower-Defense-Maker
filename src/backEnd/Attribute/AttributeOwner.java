package backEnd.Attribute;

import backEnd.GameData.State.AccessPermissions;
import backEnd.GameEngine.AttributeData;

public interface AttributeOwner {
	
	/**
	 * @return AccessPermissions
	 */
	AccessPermissions getAccessPermissions();
	
	/**
	 * @return List of Attributes
	 */
	AttributeData getMyAttributes();
	
	/**
	 * @param attrType
	 * @return Attribute that corresponds to the String attrType
	 */
	Attribute<?> getAttribute(String attrType);
	
	/**
	 * Add a Attributes to the Tile of type attrType. String attrType has to be a Key from the tilDefaults properties file
	 * @param attrType
	 * @param newAttr
	 */
	void addAttribute(String attrType, Attribute<?> newAttr);
	

}
