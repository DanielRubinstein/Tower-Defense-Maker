package backEnd.Attribute;

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
}
