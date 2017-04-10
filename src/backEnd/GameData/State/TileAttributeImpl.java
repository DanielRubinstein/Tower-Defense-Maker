package backEnd.GameData.State;

public class TileAttributeImpl <T> implements TileAttribute<T> {
	
	private TileAttributeType type;
	private T value;
	
	public TileAttributeImpl(TileAttributeType type, T value){
		this.type = type;
		this.value = value;
	}
	
	@Override
	public void setValue(T newVal){
		value = newVal;
	}
	
	@Override
	public T getValue(){
		return value;
	}
	
	@Override
	public void setType(TileAttributeType type){
		this.type = type;
	}
	
	@Override
	public TileAttributeType getType(){
		return type;
	}
}
