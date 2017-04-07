package backEnd.State;

public class TileAttribute <T> {
	
	private TileAttributeType type;
	private T value;
	
	public TileAttribute(TileAttributeType type, T value){
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Set new value to the attribute
	 * @param newVal
	 */
	public void setValue(T newVal){
		value = newVal;
	}
	
	/**
	 * Get the current attribute value
	 */
	public T getValue(){
		return value;
	}
	
	/**
	 * Set new type to the attribute
	 * @param type
	 */
	public void setType(TileAttributeType type){
		this.type = type;
	}
	
	/**
	 * Get the attribute type
	 */
	public TileAttributeType getType(){
		return type;
	}
}
