package backEnd.GameEngine;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javafx.geometry.Point2D;

/**
 * 
 * @author alex salas
 *
 */
public class MoveBehavior implements Behavior {
	private AttributeData myAttributes;
	
	public MoveBehavior(){
	}
	
	@Override
	public void execute() {
		Attribute<Point2D> coord = (Attribute<Point2D>)myAttributes.get("Point");
		coord.setValue(new Point2D(0,0));
		myAttributes.put("Point",coord);
		
		switch (myAttributes.get("MoveDirection")) {
		case "LEFT":
			myAttributes.put("Point", (myAttributes.get("Point")[0]-1, myAttributes.get("Point")[1]));
		case "RIGHT":
		case "UP":
		case "DOWN":
			
		default: throw new IllegalArgumentException(); //FIX THIS	
		}
		
		
	}

	
	@Override
	public void update(Observable newData, Object arg) {
		myAttributes = (AttributeData) newData;
	}

	
}
