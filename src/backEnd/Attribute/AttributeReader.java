package backEnd.Attribute;

import java.util.Collection;

public interface AttributeReader<T> {
	/**
	 * Get the current attribute value
	 */
	public T getValue();
	
	public Collection<T> getEditParameters();
	
	public String getName();
}
