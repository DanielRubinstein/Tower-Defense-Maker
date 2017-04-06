package backEnd.GameEngine;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

/**
 * 
 * @author alex salas
 *
 */
public class MoveBehavior implements Behavior {
	private ComponentListener myListener;
	private Map<String,Attribute<?>> myAttributes;
	
	public MoveBehavior(Map<String,Attribute<?>> initialAttributes){
		myAttributes=initialAttributes;
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

	public void updateAttributes(Map<String,Attribute<?>> updatedAttributes) {
		myAttributes=updatedAttributes;
	}

	
}
