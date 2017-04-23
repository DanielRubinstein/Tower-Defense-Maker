package backEnd.Attribute;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.sun.javafx.geom.Point2D;

/**
 * 
 * @author Daniel Wrapper class for our list of attributes. We use this object
 *         as an observable.
 */

public class AttributeData extends Observable {
	// only callee can cast
	// Map<Class<T>, Attribute
	// private Map<Class<?>,List<Attribute<?>>> myAttributes;

	private Map<String, Attribute<?>> myAttributes;
	//private Map<Attribute<?>, Class<?>> myAttributeTypes;
	
	

	public AttributeData() { // create an empty AttributeData
		myAttributes = new HashMap<String, Attribute<?>>();
	}

	public AttributeData(Map<String, Attribute<?>> initialAttributes) {
		this.myAttributes = initialAttributes;
	}

	public void addAttribute(String key, Attribute<?> toAdd){
		myAttributes.put(key, toAdd);
		notifyObservers();
	}

	public void replaceAttributes(Map<String, Attribute<?>> newAttributes) {
		myAttributes = newAttributes;
		notifyObservers();
	}

	public <T> Attribute<T> get(String key) {
		try{
			Attribute<T> myAttr = (Attribute<T>) myAttributes.get(key);
			return myAttr;
		} catch(ClassCastException e){
			throw new AttributeTypeException();
		}
	
	}

	//public Map<String, Attribute<?>> getAttributeMap() {
		//return myAttributes;
	//}

	public boolean containsAttribute(String key) { // use this for debugging
		return myAttributes.containsKey(key);
	}

	/**
	 * Part of the observable interface
	 */
	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, null);
	}

	/**
	 * Part of the observable interface
	 */
	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}

	/**
	 * @return Copy
	 */
	public AttributeData copy() {
		Set<String> attributeNames = myAttributes.keySet();
		Map<String, Attribute<?>> attributesCopy = new HashMap<String, Attribute<?>>();
		for (String name : attributeNames) {
			Attribute attributeCopy = myAttributes.get(name).copy();
			attributesCopy.put(new String(name), attributeCopy);
		}
		return new AttributeData(attributesCopy);
	}
	
	public Collection<Attribute<?>> values(){
		return myAttributes.values();
	}
}
