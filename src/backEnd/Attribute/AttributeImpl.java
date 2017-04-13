package backEnd.Attribute;

import java.util.List;

/**
 * Note: we have different attributes in GameEngine.Attributes package. Ex: position attribute, health attribute...
 * 	each of these will have a different type for its value so we use generics in the interface
 * @author Christian Martindale, Daniel
 */
public class AttributeImpl<T> implements Attribute<T>{

	private T myValue;
	private List<T> myParamList;
	private String myName;
	
	public AttributeImpl(List<T> paramList, String name){
		myParamList = paramList;
		myName = name;
	}
	
	
	/**
	 * set new value to the attribute (used by Behavior)
	 * @param newVal
	 */
	@Override
	public void setValue(T newVal){
		myValue = newVal;
	}
	
	/**
	 * used by Behavior, which needs to know the current Attribute value
	 * @param currentVal
	 */
	@Override
	public T getValue(){
		return myValue;
	}

	@Override
	public List<T> getEditParameters() {
		return myParamList;
	}

	@Override
	public String getName() {
		return myName;
	}


	@Override
	public Attribute copy() {
		Attribute<T> attributeCopy = new AttributeImpl<>(myParamList, new String(myName));
		attributeCopy.setValue(myValue);
		return attributeCopy;
	}

	
}
