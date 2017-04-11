package backEnd.GameEngine;

import java.util.ArrayList;
import java.util.Map;

import com.sun.javafx.geom.Point2D;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
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
	private AttributeImpl<Integer> myHealth;
	private AttributeImpl<Point2D> myLocation;
	private AttributeImpl<Integer> moveAmount;
	private Map<String,AttributeImpl<?>> myAttributes;
	private AttributeImpl<Boolean> spawnsOnDeath;
	private AttributeImpl<Component> componentSpawnedOnDeath;
	
	public myAttributes(){
		myHealth.setValue(Constants.defaultHealth);
		myLocation.setValue(Constants.defaultLocation);
		moveAmount.setValue(Constants.moveAmount);
		spawnsOnDeath.setValue(true);
		componentSpawnedOnDeath.setValue(new Component()); //need to get correct thing from bank somehow
		myAttributes.put("HEALTH", myHealth);
		myAttributes.put("LOCATION", myLocation);
		myAttributes.put("MOVE_AMOUNT", moveAmount);
		myAttributes.put("SPAWNS_ON_DEATH", spawnsOnDeath);
		myAttributes.put("COMPONENT_SPAWNED_ON_DEATH", componentSpawnedOnDeath);
	}
	
	public Attribute<?> getAttribute(String type){
		if (myAttributes.containsKey(type)){
			return myAttributes.get(type);
		}
		else{
			throw new IllegalArgumentException();
	}
	}
		
		public void addAttribute(String key, AttributeImpl<?> toAdd){
			myAttributes.put(key, toAdd);
		}

}
