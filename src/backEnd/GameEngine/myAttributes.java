package backEnd.GameEngine;

import java.util.ArrayList;
import java.util.Map;

import com.sun.javafx.geom.Point2D;

import resources.Constants;

/**
 * 
 * @author Daniel
 * Class that contains a component's attributes. Dummy class for now; we will delete this when
 * we are able to get attribute info from frontend.
 *
 */

//TODO: DELETE myAttributes class when frontend is tied in

public class myAttributes {
	private Attribute<Integer> myHealth;
	private Attribute<Point2D> myLocation;
	private Attribute<Integer> moveAmount;
	private Map<String,Attribute<?>> myAttributes;
	
	public myAttributes(){
		myHealth.setValue(Constants.defaultHealth);
		myLocation.setValue(Constants.defaultLocation);
		moveAmount.setValue(Constants.moveAmount);
		myAttributes.put("HEALTH", myHealth);
		myAttributes.put("LOCATION", myLocation);
		myAttributes.put("MOVE_AMOUNT", moveAmount);
	}
	
	public Attribute<?> getAttribute(String type){
		if (myAttributes.containsKey(type)){
			return myAttributes.get(type);
		}
		else{
			throw new IllegalArgumentException();
	}
	}
		
		public void addAttribute(String key, Attribute<?> toAdd){
			myAttributes.put(key, toAdd);
		}

}
