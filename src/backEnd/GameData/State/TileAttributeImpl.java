package backEnd.GameData.State;

public class TileAttributeImpl <T> implements TileAttribute<T> {
	
	private TileAttributeType type;
	private T value;
	
	public TileAttributeImpl(TileAttributeType type, T value){
		this.type = type;
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.TileAttribute#setValue(T)
	 */
	@Override
	public void setValue(T newVal){
		value = newVal;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.TileAttribute#getValue()
	 */
	@Override
	public T getValue(){
		return value;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.TileAttribute#setType(backEnd.GameData.State.TileAttributeType)
	 */
	@Override
	public void setType(TileAttributeType type){
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.TileAttribute#getType()
	 */
	@Override
	public TileAttributeType getType(){
		return type;
	}
}
