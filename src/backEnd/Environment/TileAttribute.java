package backEnd.Environment;

public interface TileAttribute <T> {
	
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
