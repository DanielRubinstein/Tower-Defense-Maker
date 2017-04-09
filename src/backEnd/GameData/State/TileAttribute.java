package backEnd.GameData.State;

public interface TileAttribute<T> {

	/**
	 * Set new value to the attribute
	 * @param newVal
	 */
	void setValue(T newVal);

	/**
	 * Get the current attribute value
	 */
	T getValue();

	/**
	 * Set new type to the attribute
	 * @param type
	 */
	void setType(TileAttributeType type);

	/**
	 * Get the attribute type
	 */
	TileAttributeType getType();

}