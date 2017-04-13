package backEnd.Attribute;

import java.util.Observer;

public interface AttributeOwnerReader {
	/**
	 * @return List of Attributes
	 */
	AttributeData getMyAttributes();
	
	/**
	 * @param attrType
	 * @return Attribute that corresponds to the String attrType
	 */
	Attribute<?> getAttribute(String attrName);
	
	void addAsListener(Observer o);
}
