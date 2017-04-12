package backEnd.Attribute;

public interface Attribute<T> extends AttributeReader<T> {
	/**
	 * Set new value to the attribute
	 * @param newVal
	 */
	public <T> void setValue(T newVal);
}
