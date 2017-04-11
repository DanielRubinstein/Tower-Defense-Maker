package backEnd.Attribute;

import java.util.Arrays;
import java.util.Collection;

/**
 * Note: we have different attributes in GameEngine.Attributes package. Ex: position attribute, health attribute...
 * 	each of these will have a different type for its value so we use generics in the interface
 * @author Christian Martindale, Daniel
 */
public class AttributeImpl<T> implements Attribute<T>{

	T value;
	
	/**
	 * set new value to the attribute (used by Behavior)
	 * @param newVal
	 */
	public void setValue(T newVal){
		value = newVal;
	}
	
	/**
	 * used by Behavior, which needs to know the current Attribute value
	 * @param currentVal
	 */
	public T getValue(){
		return value;
	}

	@Override
	public String getValueAsString() {
		// TODO this may have to change
		return value.toString();
	}

	@Override
	public Collection<String> getPossibleValues() {
		// TODO how the fuck are we setting this
		return Arrays.asList("hey", "sup", "nm", "hbu");
	}
	
	
	
}
