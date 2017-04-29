package backEnd.Attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.javafx.geom.Point2D;

import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;

/**
 * 
 * @author Daniel Wrapper class for our list of attributes. We use this object
 *         as an SerializableObservable.
 */

public class AttributeData implements SerializableObservable, AttributeDataReader {
	// only callee can cast
	// Map<Class<T>, Attribute
	// private Map<Class<?>,List<Attribute<?>>> myAttributes;

	private Map<String, Attribute<?>> myAttributes;
	//private Map<Attribute<?>, Class<?>> myAttributeTypes;
	private List<SerializableObserver> observers;
	
	

	public AttributeData() { // create an empty AttributeData
		this(new HashMap<String, Attribute<?>>());
	}

	public AttributeData(Map<String, Attribute<?>> initialAttributes) {
		this.myAttributes = initialAttributes;
		observers = new ArrayList<SerializableObserver>();
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
	 * Part of the SerializableObservable interface
	 */
	@Override
	public void addObserver(SerializableObserver o) {
		addObserver(o);
		o.update(this, null);
	}

	private void notifyObservers() {
		for (SerializableObserver o : observers){
			o.update(this, null);
		}
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

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers = null;
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}

	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}

	@Override
	public <T> AttributeReader<T> getAttributeReader(String key) {
		try{
			AttributeReader<T> myAttr = (AttributeReader<T>) myAttributes.get(key);
			return myAttr;
		} catch(ClassCastException e){
			throw new AttributeTypeException();
		}
	}

	@Override
	public Collection<AttributeReader<?>> getAllAttributeReaders() {
		Collection<AttributeReader<?>> myAttReaders = new ArrayList<AttributeReader<?>>();
		for(AttributeReader<?> myAtt : myAttributes.values()){
			myAttReaders.add(myAtt);
		}
		return myAttReaders;
	}
}
