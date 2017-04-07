package backEnd.GameEngine;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Note: we have different attributes in GameEngine.Attributes package. Ex:
 * position attribute, health attribute... each of these will have a different
 * type for its value so we use generics in the interface
 */
public class Attribute<T> {

	/*
	 * Added by Miguel
	 * 
	 * This is how I will present the name of the attribute and the value to
	 * display in the popup that shows up when the user clicks a tile. Leave
	 * this here for now. I did this in Slogo but I'm not sure how it works for
	 * generics.
	 */
	private final SimpleStringProperty nameProp = null;
	private final SimpleDoubleProperty valueProp = null;

	T value;

	/**
	 * set new value to the attribute (used by Behavior)
	 * 
	 * @param newVal
	 */
	public void setValue(T newVal) {
		value = newVal;
	}

	/**
	 * used by Behavior, which needs to know the current Attribute value
	 * 
	 * @param currentVal
	 */
	public T getValue() {
		return value;
	}

}
