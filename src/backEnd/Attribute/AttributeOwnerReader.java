package backEnd.Attribute;

import backEnd.GameData.State.SerializableObserver;

public interface AttributeOwnerReader {
	/**
	 * @return List of Attributes
	 */
	AttributeDataReader getMyAttributes();
	
	/**
	 * @param attrType
	 * @return Attribute that corresponds to the String attrType
	 */
	<T> AttributeReader<T> getAttributeReader(String attrName);
	
	void addObserver(SerializableObserver o);
}
