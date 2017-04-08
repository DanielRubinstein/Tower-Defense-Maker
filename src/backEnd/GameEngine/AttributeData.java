package backEnd.GameEngine;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Daniel
 * Wrapper class for our list of attributes. We use this object as an observable.
 */

public class AttributeData extends Observable {
	
	private Map<String,Attribute<?>> myAttributes;
	
	public AttributeData(Map<String,Attribute<?>> initialAttributes){
		
	}

	
	public void addAttribute(String key, Attribute<?> toAdd){
		myAttributes.put(key, toAdd);
		notifyObservers();
	}
	
	public void replaceAttributes(Map<String,Attribute<?>> newAttributes){
		myAttributes=newAttributes;
		notifyObservers();
	}
	
	public Attribute<?> get(String key){
		return myAttributes.get(key);
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
	

}
