package backEnd.Attribute;

import java.util.Collection;

public interface Attribute<T>{

	/**
	 * Set new value to the attribute
	 * @param newVal
	 */
	public void setValue(T newVal);

	/**
	 * Get the current attribute value
	 */
	public T getValue();
	
	
	public String getValueAsString();
	
	public Collection<String> getPossibleValues();

}
