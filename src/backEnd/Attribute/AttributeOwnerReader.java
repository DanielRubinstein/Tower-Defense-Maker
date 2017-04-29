package backEnd.Attribute;

import backEnd.GameData.State.SerializableObserver;

public interface AttributeOwnerReader {
	/**
	 * @return List of Attributes
	 */
	//AttributeData getMyAttributes();
	
	/**
	 * @param attrType
	 * @return Attribute that corresponds to the String attrType
	 */
	<T> AttributeReader<T> getAttribute(String attrName);
	
	void addObserver(SerializableObserver o);
}
