package backEnd.GameEngine;

/**
 * Note: we have different attributes in GameEngine.Attributes package. Ex: position attribute, health attribute...
 * 	each of these will have a different type for its value so we use generics in the interface
 */
public interface Attribute<T> {

	/**
	 * set new value to the attribute (used by Behavior)
	 * @param newVal
	 */
	public void setValue(T newVal);
	
	/**
	 * used by Behavior, which needs to know the current Attribute value
	 * @param currentVal
	 */
	public void getValue(T currentVal);
	

}
