package backEnd.Attribute;

import java.util.List;

public interface AttributeReader<T> {
	/**
	 * Get the current attribute value
	 */
	public T getValue();
	
	public List<T> getEditParameters();
	
	public AttributeType getAttributeType();
	
	public String getName();
}
