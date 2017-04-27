package backEnd.Attribute;

public interface Attribute<T> extends AttributeReader<T> {
	/**
	 * Set new value to the attribute
	 * @param newVal
	 */
	void setValue(T newVal);

	/**
	 * @return Copy of Attribute
	 */
	Attribute copy();
}
