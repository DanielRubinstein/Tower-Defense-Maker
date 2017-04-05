package backEnd.Environment;

public interface TileAttribute <T> {
	
	/**
	 * Set new value to the attribute
	 * @param newVal
	 */
	public void setValue(T newVal);
	
	/**
	 * Get the current attribute value
	 * @param currentVal
	 */
	public void getValue();

}
