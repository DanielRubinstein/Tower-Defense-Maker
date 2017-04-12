package backEnd.Attribute;

import backEnd.GameData.State.AccessPermissions;

public interface AttributeOwner extends AttributeOwnerReader{
	
	/**
	 * Add a Attributes to the Tile of type attrType. String attrType has to be a Key from the tilDefaults properties file
	 * @param attrType
	 * @param newAttr
	 */
	void addAttribute(String attrName, Attribute<?> newAttr);
	
	
	/**
	 * @return AccessPermissions
	 */
	AccessPermissions getAccessPermissions();
}
